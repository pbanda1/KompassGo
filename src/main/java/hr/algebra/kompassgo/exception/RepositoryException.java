package hr.algebra.kompassgo.exception;

// Thrown by repository classes when a database query (SELECT, INSERT, UPDATE, DELETE) fails.
public class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
