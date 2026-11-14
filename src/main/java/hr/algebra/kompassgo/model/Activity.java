package hr.algebra.kompassgo.model;

import hr.algebra.kompassgo.model.enums.Priority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class Activity {

    private int id;
    private int userId;
    private Integer locationId;     // may be null (activity without a location)
    private String name;
    private int durationMin;
    private boolean fixed;
    private LocalDateTime startTime; // required only for a fixed activity
    private Priority priority;
    private boolean optional;
    private LocalDate deadline;      // may be null

    public Activity() {
        this.priority = Priority.MEDIUM;
    }

    // READ FROM DB: priority is stored as text
    public Activity(int id, int userId, Integer locationId, String name, int durationMin, boolean fixed,
                    LocalDateTime startTime, String priority, boolean optional, LocalDate deadline) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.name = name;
        this.durationMin = durationMin;
        this.fixed = fixed;
        this.startTime = startTime;
        this.priority = Priority.valueOf(priority);
        this.optional = optional;
        this.deadline = deadline;
    }

    // NEW FLEXIBLE ACTIVITY: medium priority by default, not optional
    public Activity(int userId, String name, int durationMin) {
        this();
        this.userId = userId;
        this.name = name;
        this.durationMin = durationMin;
    }

    // NEW FIXED ACTIVITY
    public Activity(int userId, String name, int durationMin, LocalDateTime startTime) {
        this(userId, name, durationMin);
        this.fixed = true;
        this.startTime = startTime;
    }

    /** End time of a fixed activity, null for a flexible one. */
    public LocalDateTime getEndTime() {
        return startTime == null ? null : startTime.plusMinutes(durationMin);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getLocationId() { return locationId; }
    public void setLocationId(Integer locationId) { this.locationId = locationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDurationMin() { return durationMin; }
    public void setDurationMin(int durationMin) { this.durationMin = durationMin; }

    public boolean isFixed() { return fixed; }
    public void setFixed(boolean fixed) { this.fixed = fixed; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public boolean isOptional() { return optional; }
    public void setOptional(boolean optional) { this.optional = optional; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity other)) return false;
        return id == other.id && userId == other.userId && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name);
    }

    @Override
    public String toString() {
        return "Activity{id=" + id + ", name='" + name + "', durationMin=" + durationMin
                + ", fixed=" + fixed + ", startTime=" + startTime + ", priority=" + priority.name() + "}";
    }
}