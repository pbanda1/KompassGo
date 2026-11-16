package hr.algebra.kompassgo.repository;

import hr.algebra.kompassgo._interface.ICrud;
import hr.algebra.kompassgo.exception.RepositoryException;
import hr.algebra.kompassgo.model.entity.User;
import hr.algebra.kompassgo.util.DatabaseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserRepository implements ICrud<User, Integer> {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private static final UserRepository INSTANCE = new UserRepository();

    /// STATEMENTS////
    private static final String SELECT_USER = """
            SELECT ID, FirstName, LastName, Email, PasswordHash, Role, TravelMode, CreatedAt
            FROM AppUser
            """;

    private static final String EXISTS_BY_EMAIL = """
            SELECT COUNT(*) FROM AppUser
            WHERE LOWER(Email) = LOWER(?)
            """;

    private static final String INSERT_USER = """
            INSERT INTO AppUser (FirstName, LastName, Email, PasswordHash, Role, TravelMode)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String UPDATE_USER = """
            UPDATE AppUser
            SET FirstName = ?, LastName = ?, Email = ?, Role = ?, TravelMode = ?
            WHERE ID = ?
            """;

    private static final String DELETE_USER = """
            DELETE FROM AppUser
            WHERE ID = ?
            """;

    private UserRepository() {
    }

    /// FUNCTIONS////
    public static UserRepository getInstance() {
        return INSTANCE;
    }


    public boolean existsByEmail(String email) {
        try (PreparedStatement ps = DatabaseUtility.getConnection().prepareStatement(EXISTS_BY_EMAIL)) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            String msg = "Error while checking e-mail";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = DatabaseUtility.getConnection()
                .prepareStatement(SELECT_USER + " ORDER BY LastName, FirstName");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            String msg = "Error while fetching users";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
        return users;
    }


    @Override
    public Optional<User> findById(Integer id) {
        try (PreparedStatement ps = DatabaseUtility.getConnection()
                .prepareStatement(SELECT_USER + " WHERE ID = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            String msg = "Error while finding user by id";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        try (PreparedStatement ps = DatabaseUtility.getConnection()
                .prepareStatement(SELECT_USER + " WHERE LOWER(Email) = LOWER(?)")) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            String msg = "Error while finding user by e-mail";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
        return Optional.empty();
    }

    // FROM DB TO JAVA OBJECT: one row of AppUser -> one User
    private User mapRow(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        return new User(
                rs.getInt("ID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("PasswordHash"),
                rs.getString("Role"),
                rs.getString("TravelMode"),
                createdAt != null ? createdAt.toLocalDateTime() : null
        );
    }

    @Override
    public void save(User user) {
        try (PreparedStatement ps = DatabaseUtility.getConnection()
                .prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFirstName().trim());
            ps.setString(2, user.getLastName().trim());
            ps.setString(3, user.getEmail().trim().toLowerCase());
            ps.setString(4, user.getPasswordHash());
            ps.setString(5, user.getRole().name());
            ps.setString(6, user.getTravelMode().name());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            log.info("User saved: {}", user.getEmail());
        } catch (SQLException e) {
            String msg = "Error while saving user";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
    }


    @Override
    public void update(User user) {
        try (PreparedStatement ps = DatabaseUtility.getConnection().prepareStatement(UPDATE_USER)) {
            ps.setString(1, user.getFirstName().trim());
            ps.setString(2, user.getLastName().trim());
            ps.setString(3, user.getEmail().trim().toLowerCase());
            ps.setString(4, user.getRole().name());
            ps.setString(5, user.getTravelMode().name());
            ps.setInt(6, user.getId());

            if (ps.executeUpdate() == 0) {
                throw new RepositoryException("User not found, id = " + user.getId());
            }
            log.info("User updated: id = {}", user.getId());
        } catch (SQLException e) {
            String msg = "Error while updating user";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement ps = DatabaseUtility.getConnection().prepareStatement(DELETE_USER)) {
            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new RepositoryException("User not found, id = " + id);
            }
            log.info("User deleted: id = {}", id);
        } catch (SQLException e) {
            String msg = "Error while deleting user";
            log.error(msg, e);
            throw new RepositoryException(msg, e);
        }
    }

}
