[CmdletBinding()]
param()
$ErrorActionPreference = 'Stop'

# Projektwurzel relativ zu diesem Skript
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Split-Path -Parent $scriptDir
Set-Location $root

# Logs-Ordner sicherstellen
$logs = Join-Path $root 'scripts\logs'
if (-not (Test-Path $logs)) { New-Item -ItemType Directory -Path $logs | Out-Null }
$logFile = Join-Path $logs 'pitest_last.txt'

Write-Host "Starte PIT im Projekt: $root" -ForegroundColor Cyan
# Gradle ausführen und in Datei + Konsole schreiben
& .\gradlew clean pitest --no-daemon --console=plain *>&1 | Tee-Object -FilePath $logFile

if ($LASTEXITCODE -ne 0) {
  Write-Error "Gradle/PIT fehlgeschlagen. Logs: $logFile"
  exit 1
}

# Report öffnen
$report = Join-Path $root 'build\reports\pitest\index.html'
if (Test-Path $report) {
  Write-Host "Öffne PIT-Report: $report" -ForegroundColor Green
  Start-Process $report
} else {
  Write-Warning "PIT-Report nicht gefunden: $report"
  Write-Warning "Siehe Logs: $logFile"
}
