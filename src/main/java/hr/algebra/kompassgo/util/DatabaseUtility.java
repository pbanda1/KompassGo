package hr.algebra.kompassgo.util;

import hr.algebra.kompassgo.exception.DatabaseException;
import hr.algebra.kompassgo.poco.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseUtility {

    private static final Logger log = LoggerFactory.getLogger(DatabaseUtility.class);

    private static final String INIT_DB_PATH = "/hr/algebra/kompassgo/sql/initDB.sql";
    private static final String SEED_DB_PATH = "/hr/algebra/kompassgo/sql/seedDB.sql";
    private static final String RESET_DB_PATH = "/hr/algebra/kompassgo/sql/resetDB.sql";


    private static Connection instance;

    private DatabaseUtility() {
    }


    public static synchronized Connection getConnection() {
        try {
            if (instance == null || instance.isClosed()) {
                AppConfig config = AppConfigUtility.getConfig();
                instance = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
                log.info("Connected to database {}", config.getUrl());
            }
            return instance;
        } catch (SQLException e) {
            String msg = "Unsuccessful connection to database";
            log.error(msg, e);
            throw new DatabaseException(msg, e);
        }
    }


    public static void init() {
        Connection conn = getConnection();
        if (!schemaExists(conn)) {
            initSchema(conn);
            seedDatabase(conn);
        } else {
            log.info("Database already initialised");
        }
    }

    public static boolean schemaExists(Connection conn) {
        String sql = "SELECT COUNT(*) FROM AppUser";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void execSQL(Connection conn, String sqlPath) {
        try (InputStream in = DatabaseUtility.class.getResourceAsStream(sqlPath)) {
            if (in == null) {
                throw new DatabaseException("SQL script not found: " + sqlPath);
            }
            String script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            try (Statement stmt = conn.createStatement()) {
                for (String sql : removeComments(script).split(";")) { // svaka naredba završava sa ;
                    String trimmed = sql.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            String msg = "Error whilst executing SQL script " + sqlPath;
            log.error(msg, e);
            throw new DatabaseException(msg, e);
        }
    }

    public static void initSchema(Connection conn) {
        execSQL(conn, INIT_DB_PATH);
        log.info("Database schema created");
    }

    public static void seedDatabase(Connection conn) {
        execSQL(conn, SEED_DB_PATH);
        log.info("Seed data inserted");
    }


    public static void resetDatabase(Connection conn) {
        execSQL(conn, RESET_DB_PATH);
        seedDatabase(conn);
        log.info("Database reset");
    }


    private static String removeComments(String script) {
        StringBuilder sb = new StringBuilder();
        for (String line : script.split("\\R")) {
            if (!line.trim().startsWith("--")) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }
}