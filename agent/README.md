# GitHub Sync Agent

This folder contains a PowerShell-based agent that helps you manage GitHub push and pull operations for this project.

## File

- `github-sync-agent.ps1`

## What it does

- Initializes git if `.git` does not exist
- Sets or updates `origin` remote
- Supports `status`, `pull`, and `push` operations
- Commits local changes automatically before push (when changes exist)

## Default repository

- `https://github.com/Raiyan-Uddin/Restful-Booker-API-Test.git`

## Usage (PowerShell)

```powershell
Set-Location "D:/1. Intellij Idea/Restful-Booker-API-Test"
git config user.name "Your Name"
git config user.email "you@example.com"
powershell -ExecutionPolicy Bypass -File ".\agent\github-sync-agent.ps1" -Action status
powershell -ExecutionPolicy Bypass -File ".\agent\github-sync-agent.ps1" -Action pull
powershell -ExecutionPolicy Bypass -File ".\agent\github-sync-agent.ps1" -Action push -CommitMessage "chore: sync project"
```

## Notes

- On first push/pull, Git may ask for GitHub authentication.
- If your default branch is not `main`, pass `-Branch <branch-name>`.


