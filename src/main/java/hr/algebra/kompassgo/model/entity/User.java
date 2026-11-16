package hr.algebra.kompassgo.model.entity;
import hr.algebra.kompassgo.model.enums.Role;
import hr.algebra.kompassgo.model.enums.TravelMode;
import java.time.LocalDateTime;
import java.util.Objects;


public class User extends BaseEntity {


    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Role role;
    private TravelMode travelMode;
    private LocalDateTime createdAt;

    public User() {
    }

    // READ FROM DB: enums are stored as text (VARCHAR) and converted with valueOf
    public User(int id, String firstName, String lastName, String email, String passwordHash,
                String role, String travelMode, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = Role.valueOf(role);
        this.travelMode = TravelMode.valueOf(travelMode);
        this.createdAt = createdAt;
    }

    // REGISTRATION: a new user is always USER and uses public transport until changed in the profile
    public User(String firstName, String lastName, String email, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = Role.USER;
        this.travelMode = TravelMode.PUBLIC_TRANSPORT;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // two users are equal if they have the same e-mail (UNIQUE in the database)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return Objects.equals(email, other.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    // passwordHash is intentionally left out so it never ends up in the logs
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + getFullName() + "', email='" + email
                + "', role=" + role.name() + ", travelMode=" + travelMode.name() + "}";
    }
}
