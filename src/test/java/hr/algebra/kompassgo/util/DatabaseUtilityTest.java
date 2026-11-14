package hr.algebra.kompassgo.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;


class DatabaseUtilityTest {

    private long count(Statement st, String table) throws Exception {
        try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table)) {
            rs.next();
            return rs.getLong(1);
        }
    }

    @Test
    void initCreatesSchemaAndSeedsOnlyOnce() throws Exception {
        DatabaseUtility.init();
        DatabaseUtility.init(); // a second start must not duplicate the data

        Connection conn = DatabaseUtility.getConnection();
        try (Statement st = conn.createStatement()) {
            assertEquals(3, count(st, "AppUser"));
            assertEquals(3, count(st, "Location"));
            assertEquals(5, count(st, "Activity"));

            try (ResultSet rs = st.executeQuery(
                    "SELECT PasswordHash, Role FROM AppUser WHERE Email = 'admin@kompassgo.com'")) {
                assertTrue(rs.next());
                assertEquals("ADMIN", rs.getString("Role"));
                String hash = rs.getString("PasswordHash");
                assertTrue(hash.startsWith("$2a$"), "password must be a BCrypt hash accepted by jBCrypt");
                assertEquals(60, hash.length());
            }

            // an ID must not be set manually
            assertThrows(SQLException.class, () -> st.executeUpdate(
                    "INSERT INTO AppUser (ID, FirstName, LastName, Email, PasswordHash) VALUES (99, 'X', 'Y', 'x@y.com', 'h')"));

            // the activity is linked to the right user and location even though the seed sets no IDs
            try (ResultSet rs = st.executeQuery("SELECT u.Email, l.Name FROM Activity a "
                    + "JOIN AppUser u ON u.ID = a.UserID JOIN Location l ON l.ID = a.LocationID "
                    + "WHERE a.Name = 'Workout'")) {
                assertTrue(rs.next());
                assertEquals("ana@kompassgo.com", rs.getString(1));
                assertEquals("Gym", rs.getString(2));
            }

            DatabaseUtility.resetDatabase(conn);
            assertEquals(3, count(st, "AppUser"));
            assertEquals(5, count(st, "Activity"));
        }
    }
}