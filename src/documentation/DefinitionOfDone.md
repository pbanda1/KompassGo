# Definition of Done (DoD)

A user story is Done only when all of the following are true:

## Code
- [ ] All acceptance criteria are met.
- [ ] No passwords, keys or other secrets are in the repository.
- [ ] The code follows the agreed package structure (`model/entity`, `model/enums`,
  `util`, `exception`, `poco`, ...).

## Tests and build
- [ ] New logic is covered by tests where it makes sense (JUnit 5, `src/test/java`).
- [ ] `./mvnw verify` (Windows: `.\mvnw.cmd verify`) passes locally: **BUILD SUCCESS**,
  0 failures, 0 errors.
- [ ] The CI pipeline on the pull request is green.
- [ ] The application starts on everyone's local machine and the feature has been tried by hand "by everyone in the team".

## Git and review
- [ ] Work was done on a `feature/SCRUM-xx-short-name` branch, never directly on `main`.
- [ ] Every commit message starts with the Jira key (e.g. `SCRUM-37 ...`).
- [ ] A pull request into `main` was opened and approved by **at least 1** other team member.
- [ ] The pull request is merged and the feature branch is deleted.

## Database
- [ ] Schema changes are in `initDB.sql`, and `resetDB.sql` / `seedDB.sql` are updated to match.
- [ ] The database generates all IDs (`GENERATED ALWAYS AS IDENTITY`); no IDs are inserted by hand.

## Documentation and Jira
- [ ] README / documentation is updated if setup or usage changed.
- [ ] All subtasks are in **Done** and the story is moved to **Done** in Jira.
- [ ] The Product Owner has accepted the story (in the sprint review at the latest).