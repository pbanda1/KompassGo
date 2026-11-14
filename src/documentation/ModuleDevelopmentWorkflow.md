# Module Development Workflow

Every module (epic) is built in the same order, **story by story**.
A story is done only when all steps are completed and all acceptance criteria are met
(see [Definition of Done](DefinitionOfDone.md)).

## Technology

| Area | Tool |
|------|------|
| Language | Java 25 |
| UI | JavaFX (FXML + Scene Builder) |
| Build | Maven (wrapper: `mvnw` / `mvnw.cmd`) |
| Database | H2 (JDBC) |
| Tests | JUnit 5 |
| Version control and CI | GitHub + GitHub Actions |

## Steps

### 1. Preparation
The whole team reads the story, its acceptance criteria and subtasks.
Open questions are resolved before work starts (see [Definition of Ready](DefinitionOfReady.md)).

### 2. Jira: take a subtask
Take a subtask (**Assign to me**) and move it to **In Progress**.

### 3. Create a branch
Create a branch from an up-to-date `main`, named after the Jira key:

```bash
git checkout main
git pull
git checkout -b feature/SCRUM-7-registration
```

### 4. Database `backend`
Add the new table or column to the SQL scripts: `initDB.sql`, and `seedDB.sql` / `resetDB.sql` if needed.
The database generates all IDs.

### 5. Model and DAO `backend`
Java model class in `model/entity` and a DAO class with JDBC queries.

### 6. Service `backend`
Business logic and checks: validation, rules, algorithm.

### 7. Tests `backend`
JUnit tests for the service and DAO in `src/test/java`. Locally this must pass:

```bash
.\mvnw.cmd verify
```

### 8. Screen `frontend`
FXML made in Scene Builder and a controller that calls the service.

### 9. Manual check
Run the app and go through all acceptance criteria:

```bash
.\mvnw.cmd javafx:run
```

### 10. Pull request
Commit (the message starts with the Jira key), push the branch and open a PR to `main`:

```bash
git add .
git commit -m "SCRUM-7 add registration form"
git push -u origin feature/SCRUM-7-registration
```

CI must be green and **at least one** team member reviews the code.

### 11. Merge
Merge into `main` and delete the branch.

> **Note:** anything pushed after the merge needs a **new** pull request.

### 12. Jira: close the work
Move the subtask to **Done**. The story goes to **Done** when all its subtasks are finished
and the acceptance criteria are checked.

When all stories of an epic are done, the epic is closed and the module is shown to the team
and the teacher at the **Sprint Review**.

## Jira conventions

| Convention | Rule |
|------------|------|
| **Layer** | Label `frontend` (JavaFX screens, FXML, controllers) or `backend` (database, DAO, logic, tests). |
| **Priority** | **High**: the story can't work without it. **Medium**: validation and tests. **Low**: extras that can wait. |
| **Spike** | A task with the label `spike` and a title starting with `SPIKE:`. |
