package hr.algebra.kompassgo.util;

import hr.algebra.kompassgo.model.entity.Activity;
import hr.algebra.kompassgo.model.entity.User;
import hr.algebra.kompassgo.model.enums.Priority;
import hr.algebra.kompassgo.model.enums.Role;
import hr.algebra.kompassgo.model.enums.TravelMode;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// checks that every seed row maps to the model (enums and dates match the tables)
class ModelMappingTest {

    @Test
    void seedRowsMapToModel() throws Exception {
        DatabaseUtility.init();
        try (Statement st = DatabaseUtility.getConnection().createStatement()) {
            List<User> users = new ArrayList<>();
            try (ResultSet rs = st.executeQuery("SELECT * FROM AppUser ORDER BY ID")) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("ID"), rs.getString("FirstName"), rs.getString("LastName"),
                            rs.getString("Email"), rs.getString("PasswordHash"), rs.getString("Role"),
                            rs.getString("TravelMode"), rs.getTimestamp("CreatedAt").toLocalDateTime()));
                }
            }
            assertEquals(Role.ADMIN, users.get(0).getRole());
            assertTrue(users.get(0).isAdmin());
            assertEquals(TravelMode.BICYCLE, users.get(1).getTravelMode());
            assertFalse(users.get(0).toString().contains("$2a$"));

            List<Activity> activities = new ArrayList<>();
            try (ResultSet rs = st.executeQuery("SELECT * FROM Activity ORDER BY ID")) {
                while (rs.next()) {
                    Timestamp start = rs.getTimestamp("StartTime");
                    Date deadline = rs.getDate("Deadline");
                    activities.add(new Activity(rs.getInt("ID"), rs.getInt("UserID"),
                            rs.getObject("LocationID", Integer.class), rs.getString("Name"), rs.getInt("DurationMin"),
                            rs.getBoolean("IsFixed"), start == null ? null : start.toLocalDateTime(),
                            rs.getString("Priority"), rs.getBoolean("IsOptional"),
                            deadline == null ? null : deadline.toLocalDate()));
                }
            }
            assertEquals(5, activities.size());
            assertEquals(Priority.HIGH, activities.get(0).getPriority());
            assertEquals(activities.get(0).getStartTime().plusMinutes(90), activities.get(0).getEndTime());
            assertNull(activities.get(2).getLocationId());
        }
        assertEquals("Public transport", TravelMode.PUBLIC_TRANSPORT.toString());
        assertEquals(Role.USER, new User("A", "B", "a@b.com", "x").getRole());
    }
}
