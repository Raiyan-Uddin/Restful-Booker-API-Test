param(
    [ValidateSet("init", "status", "pull", "push")]
    [string]$Action = "status",

    [string]$RepositoryUrl = "https://github.com/Raiyan-Uddin/Restful-Booker-API-Test.git",

    [string]$Branch = "main",

    [string]$CommitMessage = "chore: sync project via github sync agent"
)

$ErrorActionPreference = "Stop"

function Write-AgentMessage {
    param([string]$Message)
    Write-Host "[GitHub-Sync-Agent] $Message"
}

function Ensure-GitAvailable {
    if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
        throw "Git is not installed or not available in PATH."
    }
}

function Ensure-RepositoryInitialized {
    if (-not (Test-Path ".git")) {
        Write-AgentMessage "No .git folder found. Initializing repository..."
        git init | Out-Null
    }
}

function Ensure-RemoteOrigin {
    param([string]$Url)

    $remotes = @(git remote)
    $hasOrigin = $remotes -contains "origin"

    if (-not $hasOrigin) {
        Write-AgentMessage "Adding origin remote: $Url"
        git remote add origin $Url
        return
    }

    $existingOrigin = (git remote get-url origin).Trim()

    if ($existingOrigin -ne $Url) {
        Write-AgentMessage "Origin remote exists with different URL. Updating origin to: $Url"
        git remote set-url origin $Url
    }
}

function Ensure-MainBranch {
    param([string]$BranchName)

    $currentBranch = (git symbolic-ref --short HEAD 2>$null).Trim()
    if ([string]::IsNullOrWhiteSpace($currentBranch)) {
        $currentBranch = "(none)"
    }

    if ($currentBranch -ne $BranchName) {
        Write-AgentMessage "Switching branch from '$currentBranch' to '$BranchName'"
        git checkout -B $BranchName | Out-Null
    }
}

function Ensure-CommitIdentity {
    $name = (git config user.name).Trim()
    $email = (git config user.email).Trim()

    if ([string]::IsNullOrWhiteSpace($name) -or [string]::IsNullOrWhiteSpace($email)) {
        throw 'Git identity is not configured. Run: git config user.name "Your Name"; git config user.email "you@example.com"'
    }
}

function Show-Status {
    Write-AgentMessage "Repository status:"
    git --no-pager status
    Write-AgentMessage "Configured remotes:"
    git --no-pager remote -v
}

function Pull-Latest {
    param([string]$BranchName)

    Write-AgentMessage "Pulling latest changes from origin/$BranchName"
    git pull origin $BranchName
}

function Push-Changes {
    param(
        [string]$BranchName,
        [string]$Message
    )

    $hasStagedOrUnstagedChanges = (git status --porcelain)
    if (-not [string]::IsNullOrWhiteSpace($hasStagedOrUnstagedChanges)) {
        Ensure-CommitIdentity

        Write-AgentMessage "Changes detected. Staging all files..."
        git add .

        Write-AgentMessage "Committing changes..."
        git commit -m $Message
    } else {
        Write-AgentMessage "No local changes detected. Skipping commit."
    }

    Write-AgentMessage "Pushing to origin/$BranchName"
    git push -u origin $BranchName
}

Ensure-GitAvailable

switch ($Action) {
    "init" {
        Ensure-RepositoryInitialized
        Ensure-RemoteOrigin -Url $RepositoryUrl
        Ensure-MainBranch -BranchName $Branch
        Show-Status
    }
    "status" {
        Ensure-RepositoryInitialized
        Ensure-RemoteOrigin -Url $RepositoryUrl
        Show-Status
    }
    "pull" {
        Ensure-RepositoryInitialized
        Ensure-RemoteOrigin -Url $RepositoryUrl
        Ensure-MainBranch -BranchName $Branch
        Pull-Latest -BranchName $Branch
        Show-Status
    }
    "push" {
        Ensure-RepositoryInitialized
        Ensure-RemoteOrigin -Url $RepositoryUrl
        Ensure-MainBranch -BranchName $Branch
        Push-Changes -BranchName $Branch -Message $CommitMessage
        Show-Status
    }
    default {
        throw "Unsupported action: $Action"
    }
}




