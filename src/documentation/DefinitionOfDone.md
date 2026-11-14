Applies to every user story. A story is Done only when all 7 criteria are met. A spike is done when its question is answered and documented in Jira or Confluence.

Works on every team member's computer: a fresh git clone of main, set up only by following the README, starts the app and the main scenario works.

All acceptance criteria of the story are met.

./mvnw verify (Windows: .\mvnw.cmd verify) is green locally and in CI: BUILD SUCCESS, all tests pass.

The work was done on a feature/SCRUM-xx-... branch, every commit message starts with the Jira key, and the pull request was approved by at least 1 other member before merging into main.

The repository contains no passwords or other secrets and no absolute paths; code, comments and database objects are in English.

Database changes are only in the SQL scripts (initDB / seedDB / resetDB), the database generates all IDs and no manual steps in the database are needed.

README / documentation is updated, and the story and all its subtasks are in Done in Jira.