$ErrorActionPreference = "Stop"

$Repo = "dimeskigj/jack-cli"
$InstallDir = "$HOME\.jack"
$BinDir = "$InstallDir\bin"
$ExePath = "$BinDir\jack.exe"

Write-Host "Installing Jack CLI..."

# 1. Get Latest Version
try {
    $LatestRelease = Invoke-RestMethod -Uri "https://api.github.com/repos/$Repo/releases/latest"
    $LatestTag = $LatestRelease.tag_name
} catch {
    Write-Error "Could not find latest release. Please check your internet connection."
    exit 1
}

if ([string]::IsNullOrEmpty($LatestTag)) {
    Write-Error "Could not find latest release tag."
    exit 1
}

Write-Host "Latest version: $LatestTag"

# 2. Prepare Directory
if (-not (Test-Path $BinDir)) {
    New-Item -ItemType Directory -Force -Path $BinDir | Out-Null
}

# 3. Download Native Binary
$AssetUrl = "https://github.com/$Repo/releases/download/$LatestTag/jack-windows-x64.exe"

Write-Host "Downloading $AssetUrl..."
try {
    Invoke-WebRequest -Uri $AssetUrl -OutFile $ExePath
} catch {
    Write-Error "Failed to download release asset. Ensure the release has a 'jack-windows-x64.exe' asset."
    exit 1
}

# 4. Add to PATH
$UserPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($UserPath -notlike "*$BinDir*") {
    if ([string]::IsNullOrEmpty($UserPath)) {
        $NewUserPath = $BinDir
    } else {
        $TrimmedUserPath = $UserPath.TrimEnd(';')
        $NewUserPath = "$TrimmedUserPath;$BinDir"
    }
    [Environment]::SetEnvironmentVariable("Path", $NewUserPath, "User")
    Write-Host "Added $BinDir to User PATH."
} else {
    Write-Host "Path already configured."
}

Write-Host "✅ Installation complete! Please restart your terminal."
Write-Host "Try running: jack --version"
