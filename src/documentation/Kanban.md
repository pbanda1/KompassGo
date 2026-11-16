# Kanban Workflow – KompassGo

This document defines the columns of the **KompassGo Kanban** board in Jira, the exit criteria for each column, and the WIP (work in progress) limits. A card may move to the next column only when **all** exit criteria of its current column are met.

Jira board: https://kompassgo.atlassian.net/ (space **KompassGo Kanban**, key `KK`)

## Board flow

```
TO DO  →  IN PROGRESS  →  CODE REVIEW  →  TESTING  →  DONE
           (WIP ≤ 3)       (WIP ≤ 2)      (WIP ≤ 2)
```

## WIP limits

| Column | WIP limit | Why |
|---|---|---|
| To Do | – | Backlog of ready work, no limit |
| In Progress | **3** | Each team member works on at most one card at a time |
| Code Review | **2** | Reviews must not pile up; review before starting new work |
| Testing | **2** | Tested work moves to Done quickly |
| Done | – | No limit |

If a column is at its limit, the team **does not pull** a new card into it. Instead, help finish the cards already there (for example, review an open pull request).

## Exit criteria

### To Do → In Progress
- The card has a clear title and description.
- Acceptance criteria are written in the description.
- The card type is set (Story, Task or Bug) and it belongs to an epic.
- A team member is assigned (the member pulls the card, it is not pushed).
- The In Progress WIP limit (3) is not exceeded.

### In Progress → Code Review
- A feature branch exists, named after the Jira key: `feature/KK-<number>-short-description`.
- The code is written and commit messages start with the Jira key.
- `.\mvnw.cmd verify` (or `./mvnw verify`) passes locally.
- New logic has JUnit tests (validation, DAO, service).
- A pull request to `main` is open with the Jira key in its title and a reviewer is assigned.
- The Code Review WIP limit (2) is not exceeded.

### Code Review → Testing
- At least **one** team member (not the author) approved the pull request.
- All review comments are resolved.
- The CI build (GitHub Actions) on the pull request is green.
- The Testing WIP limit (2) is not exceeded.

### Testing → Done
- The app is run with `mvnw javafx:run` and every acceptance criterion is checked by hand.
- Edge cases are checked (empty fields, invalid input, wrong password, etc.).
- For a **Bug**: the steps to reproduce no longer cause the error.
- No new errors appear in the console.
- Any bug found during testing is logged as a new **Bug** card, or the card goes back to In Progress.

### Done
- The pull request is merged into `main` and the feature branch is deleted.
- The app builds and runs from a fresh clone by following the README.
- The README and documentation are updated if setup or behaviour changed.
- The card meets the team's [Definition of Done](DoD.md).

## Card types

| Type | Used for | Example |
|---|---|---|
| **Story** | A user-facing feature, written as "As a …, I want … so that …" | As a user, I want to log in and log out so that my schedule stays private |
| **Task** | Technical work with no direct user feature | Add GitHub Actions CI workflow |
| **Bug** | Something that does not work as expected; includes steps to reproduce, actual and expected result | Fresh clone: build fails with "mvn not recognized" |

## Rules
- Cards are **pulled**, never pushed: take the top card from To Do when you have capacity.
- Moving a card backwards is allowed (e.g. Testing → In Progress when a check fails); add a comment with the reason.
- Blocked cards are flagged in Jira with a comment explaining the blocker.
- Branch and pull request rules follow the README (protected `main`, one approval).
