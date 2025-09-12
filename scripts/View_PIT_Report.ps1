[CmdletBinding()]
param()
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = Split-Path -Parent $scriptDir
$report = Join-Path $root 'build\reports\pitest\index.html'

if (Test-Path $report) {
  Start-Process $report
} else {
  Write-Error "Report nicht gefunden: $report`nBitte zuerst PIT laufen lassen."
  exit 1
}
