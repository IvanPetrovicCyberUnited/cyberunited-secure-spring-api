[CmdletBinding()]
param()
$ErrorActionPreference = 'Stop'

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Split-Path -Parent $scriptDir
Set-Location $root

$logs = Join-Path $root 'scripts\logs'
if (-not (Test-Path $logs)) { New-Item -ItemType Directory -Path $logs | Out-Null }
$logFile = Join-Path $logs 'build_test_last.txt'

Write-Host "Baue & starte Unit-Tests..." -ForegroundColor Cyan
& .\gradlew clean test --no-daemon --console=plain *>&1 | Tee-Object -FilePath $logFile

if ($LASTEXITCODE -ne 0) {
  Write-Error "Build/Tests fehlgeschlagen. Logs: $logFile"
  exit 1
}

Write-Host "Build & Tests OK. Logs: $logFile" -ForegroundColor Green
