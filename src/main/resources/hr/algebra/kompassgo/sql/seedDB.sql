
INSERT INTO AppUser (FirstName, LastName, Email, PasswordHash, Role, TravelMode)
VALUES ('Admin', 'System', 'admin@kompassgo.com', '$2a$10$fYpDDe8WkUE8SsT/CgYxfuRwEEQAE6FE9uLm8wdiI96c6L8nRVGRy',
        'ADMIN', 'PUBLIC_TRANSPORT'),
       ('Ana', 'Horvat', 'ana@kompassgo.com', '$2a$10$mSd6eqmUOnayZY09hhpkLep8SNnUkN8Fe5nWCRzyO.cCSvtnHy7e6', 'USER',
        'BICYCLE'),
       ('Marko', 'Kovač', 'marko@kompassgo.com', '$2a$10$mSd6eqmUOnayZY09hhpkLep8SNnUkN8Fe5nWCRzyO.cCSvtnHy7e6', 'USER',
        'CAR');

INSERT INTO Location (UserID, Name, Address)
SELECT u.ID, v.Name, v.Address
FROM (VALUES ('ana@kompassgo.com', 'University', 'Unska 3, Zagreb'),
             ('ana@kompassgo.com', 'Gym', 'Savska cesta 41, Zagreb'),
             ('marko@kompassgo.com', 'Office', 'Ilica 242, Zagreb')) AS v(Email, Name, Address)
         JOIN AppUser u ON u.Email = v.Email;

INSERT INTO Activity (UserID, LocationID, Name, DurationMin, IsFixed, StartTime, Priority, IsOptional, Deadline)
SELECT u.ID,
       l.ID,
       v.Name,
       v.DurationMin,
       v.IsFixed,
       v.StartTime,
       v.Priority,
       v.IsOptional,
       v.Deadline
FROM (VALUES ('ana@kompassgo.com', 'University', 'Programming lecture', 90, TRUE, TIMESTAMP '2026-11-17 10:00:00',
              'HIGH', FALSE, CAST(NULL AS DATE)),
             ('ana@kompassgo.com', 'Gym', 'Workout', 60, FALSE, CAST(NULL AS TIMESTAMP), 'MEDIUM', TRUE,
              CAST(NULL AS DATE)),
             ('ana@kompassgo.com', CAST(NULL AS VARCHAR), 'Exam study', 120, FALSE, CAST(NULL AS TIMESTAMP), 'HIGH',
              FALSE, DATE '2026-11-20'),
             ('marko@kompassgo.com', 'Office', 'Team meeting', 45, TRUE, TIMESTAMP '2026-11-17 09:00:00', 'HIGH', FALSE,
              CAST(NULL AS DATE)),
             ('marko@kompassgo.com', CAST(NULL AS VARCHAR), 'Grocery shopping', 30, FALSE, CAST(NULL AS TIMESTAMP),
              'LOW', TRUE, CAST(NULL AS DATE))) AS v(Email, LocationName, Name, DurationMin, IsFixed, StartTime,
                                                     Priority, IsOptional, Deadline)
         JOIN AppUser u ON u.Email = v.Email
         LEFT JOIN Location l ON l.UserID = u.ID AND l.Name = v.LocationName;