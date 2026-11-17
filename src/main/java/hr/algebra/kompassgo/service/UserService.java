package hr.algebra.kompassgo.service;

import hr.algebra.kompassgo.exception.EmailAlreadyExistsException;
import hr.algebra.kompassgo.model.entity.User;
import hr.algebra.kompassgo.repository.UserRepository;
import hr.algebra.kompassgo.util.PasswordUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

// Business logic for users: registration (SCRUM-7) and login (SCRUM-8).
public final class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private static UserService instance;

    private final UserRepository userRepository = UserRepository.getInstance();

    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }
    public User register(String firstName, String lastName, String email, String plainPassword) {
        requireNotBlank(firstName, "First name");
        requireNotBlank(lastName, "Last name");
        requireNotBlank(email, "E-mail");
        requireNotBlank(plainPassword, "Password");

        String normalizedEmail = email.trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }

        String passwordHash = PasswordUtility.hash(plainPassword);
        User user = new User(firstName.trim(), lastName.trim(), normalizedEmail, passwordHash);
        userRepository.save(user);

        log.info("User registered: {}", normalizedEmail);
        return user;
    }


    public Optional<User> authenticate(String email, String plainPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> PasswordUtility.verify(plainPassword, user.getPasswordHash()));
    }

    private static void requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be empty");
        }
    }
}