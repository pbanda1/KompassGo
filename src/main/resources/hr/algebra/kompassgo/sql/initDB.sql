
DROP TABLE IF EXISTS Activity;
DROP TABLE IF EXISTS Location;
DROP TABLE IF EXISTS AppUser;


CREATE TABLE AppUser (
                         ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         FirstName VARCHAR(50) NOT NULL,
                         LastName VARCHAR(50) NOT NULL,
                         Email VARCHAR(100) NOT NULL UNIQUE,
                         PasswordHash VARCHAR(60) NOT NULL,
                         Role VARCHAR(10) NOT NULL DEFAULT 'USER',
                         TravelMode VARCHAR(20) NOT NULL DEFAULT 'PUBLIC_TRANSPORT',
                         CreatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT CK_AppUser_Role CHECK (Role IN ('USER', 'ADMIN')),
                         CONSTRAINT CK_AppUser_TravelMode CHECK (TravelMode IN ('WALKING', 'BICYCLE', 'PUBLIC_TRANSPORT', 'CAR'))
);

CREATE TABLE Location (
                          ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          UserID INT NOT NULL,
                          Name VARCHAR(100) NOT NULL,
                          Address VARCHAR(200),
                          CONSTRAINT FK_Location_AppUser FOREIGN KEY (UserID) REFERENCES AppUser(ID) ON DELETE CASCADE
);

CREATE TABLE Activity (
                          ID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          UserID INT NOT NULL,
                          LocationID INT,
                          Name VARCHAR(150) NOT NULL,
                          DurationMin INT NOT NULL,
                          IsFixed BOOLEAN NOT NULL DEFAULT FALSE,
                          StartTime TIMESTAMP,
                          Priority VARCHAR(10) NOT NULL DEFAULT 'MEDIUM',
                          IsOptional BOOLEAN NOT NULL DEFAULT FALSE,
                          Deadline DATE,
                          CONSTRAINT FK_Activity_AppUser FOREIGN KEY (UserID) REFERENCES AppUser(ID) ON DELETE CASCADE,
                          CONSTRAINT FK_Activity_Location FOREIGN KEY (LocationID) REFERENCES Location(ID) ON DELETE SET NULL,
                          CONSTRAINT CK_Activity_DurationMin CHECK (DurationMin > 0),
                          CONSTRAINT CK_Activity_Priority CHECK (Priority IN ('LOW', 'MEDIUM', 'HIGH')),
                          CONSTRAINT CK_Activity_IsFixed CHECK (IsFixed = FALSE OR StartTime IS NOT NULL)
);