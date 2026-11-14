# KompassGo

KompassGo is a desktop application for students that builds a weekly schedule from their activities (fixed and flexible), including travel time between locations.
Team 02 project for the *Software Engineering* course (Algebra).

## Technologies and versions

| Technology | Version | Purpose |
|---|---|---|
| Java (JDK) | 25 | Language and runtime |
| JavaFX | 25.0.5 | User interface (FXML screens) |
| Maven | Maven Wrapper (`mvnw` / `mvnw.cmd`) | Build and dependencies |
| H2 | 2.3.232 | Embedded database (file mode) |
| jBCrypt | 0.4 | Password hashing |
| JUnit | 5.12.1 | Tests |

Versions are defined in `pom.xml`. If they change there, update this table too.

## Required tools

- **JDK 25** (`java -version` must show 25)
- **Git**
- **IntelliJ IDEA** (recommended)
- **Scene Builder** for editing FXML screens

Maven does **not** need to be installed: the project contains the Maven Wrapper.

## Getting started (fresh clone)

### 1. Clone the repository
Always clone the `main` branch. Do not copy the folder or download a ZIP.

```bash
git clone https://github.com/pbanda1/KompassGo.git
cd KompassGo
```

### 2. Set JAVA_HOME
The Maven Wrapper needs `JAVA_HOME` to point to JDK 25.

**Windows (PowerShell), current terminal only:**
```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-25.0.2"
```

**Windows, permanently:** *System Properties → Environment Variables → New*: name `JAVA_HOME`, value = your JDK 25 folder. Restart the terminal / IntelliJ afterwards.

**macOS / Linux:**
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 25)   # macOS
```

### 3. Build and run the tests
```bash
# Windows
.\mvnw.cmd verify

# macOS / Linux
./mvnw verify
```
Expected result: `Tests run: X, Failures: 0, Errors: 0` and **BUILD SUCCESS**.

### 4. Run the application
```bash
# Windows
.\mvnw.cmd javafx:run

# macOS / Linux
./mvnw javafx:run
```
From IntelliJ: **Maven → Plugins → javafx → javafx:run**.

## Database

- H2 in file mode, JDBC URL: `jdbc:h2:./data/raspored;AUTO_SERVER=TRUE`
- The database is **created automatically** on first start in the `data/` folder (tables + seed data). No manual steps.
- The `data/` folder is **not** in Git, so every member has their own local database.
- Tests use a separate in-memory database (`jdbc:h2:mem:test`) and never touch your local data.
- To start from scratch: close the app and delete the `data/` folder.

### Viewing the database in IntelliJ (optional)
1. **Database** tool window → **+ → Data Source → H2**
2. Connection type: **Embedded**
3. URL (use **your own absolute path** to the project):
   ```
   jdbc:h2:file:C:/path/to/KompassGo/data/raspored;AUTO_SERVER=TRUE
   ```
4. User: `sa`, password: empty → **Test Connection**

The data source is a personal IntelliJ setting (`.idea/` is not in Git), so every member sets it up once on their own computer.

## Configuration and secrets

- Database settings are in `src/main/resources/hr/algebra/kompassgo/data/app-config.xml`.
- The repository contains **no passwords or secrets**. Seed user passwords are stored only as BCrypt hashes.

## Project structure

```
src/main/java/hr/algebra/kompassgo
├── App.java              # entry point
├── exception/            # custom exceptions
├── model/entity/         # User, Location, Activity
├── model/enums/          # Role, TravelMode, Priority
├── poco/                 # AppConfig
└── util/                 # database and config utilities
src/main/resources/hr/algebra/kompassgo
├── data/app-config.xml   # database configuration
└── sql/                  # initDB.sql, seedDB.sql, resetDB.sql
src/test/java             # JUnit tests
Documentation/            # DoD, DoR, module development workflow
```

## Team workflow

- Work on a `feature/SCRUM-xx-short-name` branch, never directly on `main`.
- Every commit message starts with the Jira key, e.g. `SCRUM-7 add registration form`.
- Open a pull request to `main`; CI must be green and at least 1 team member must approve it.
- See [Documentation/ModuleDevelopmentWorkflow.md](Documentation/ModuleDevelopmentWorkflow.md), [Definition of Done](Documentation/DefinitionOfDone.md) and [Definition of Ready](Documentation/DefinitionOfReady.md).
