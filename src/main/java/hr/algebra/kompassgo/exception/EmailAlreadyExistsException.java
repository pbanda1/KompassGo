package hr.algebra.kompassgo.exception;


public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("E-mail is already registered: " + email);
    }
}