
Module development steps
Every module (epic) is built in the same order, story by story. A story is done only when all steps are completed and all acceptance criteria are met (see DoD.md).

Technology: Java 25, JavaFX (FXML + Scene Builder), Maven (wrapper), H2 database (JDBC), JUnit 5, GitHub + GitHub Actions.

Steps
Preparation: the whole team reads the story, its acceptance criteria and subtasks; open questions are resolved before work starts (see DoR.md).
Jira: a member takes a subtask (Assign to me) and moves it to In Progress.
Branch: create a branch from an up-to-date main, named after the Jira key, e.g. feature/SCRUM-7-registration.
git checkout main
git pull
git checkout -b feature/SCRUM-7-registration
Database (backend): new table or column in the SQL scripts (initDB.sql, and seedDB.sql / resetDB.sql if needed). The database generates all IDs.
Model and DAO (backend): Java model class (model/entity) and a DAO class with JDBC queries.
Service (backend): business logic and checks (validation, rules, algorithm).
Tests (backend): JUnit tests for the service and DAO in src/test/java; .\mvnw.cmd verify passes locally.
Screen (frontend): FXML made in Scene Builder and a controller that calls the service.
Manual check: run the app (.\mvnw.cmd javafx:run) and go through all acceptance criteria.
Pull request: commit (message starts with the Jira key, e.g. SCRUM-7 add registration form), push the branch and open a PR to main. CI must be green and at least one team member reviews the code.
Merge: merge into main and delete the branch. Anything pushed after the merge needs a new PR.
Jira: subtask to Done; the story goes to Done when all its subtasks are finished and the acceptance criteria are checked.
When all stories of an epic are done, the epic is closed, and the module is shown to the team and the teacher at the Sprint Review.

Jira conventions
Layer: label frontend (JavaFX screens, FXML, controllers) or backend (database, DAO, logic, tests).
Priority: High for anything the story can't work without, Medium for validation and tests, Low for extras that can wait.
Spike: a task with the label spike and a title starting with SPIKE:.