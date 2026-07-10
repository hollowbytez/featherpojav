# Feather Pojav Mod Compilation Script

Write-Host "=============================================" -ForegroundColor Magenta
Write-Host "   Feather Client for PojavLauncher Builder   " -ForegroundColor Magenta
Write-Host "=============================================" -ForegroundColor Magenta

$jdkRoot = "A:\MCMDS\jdk"
if (-not (Test-Path $jdkRoot)) {
    Write-Error "JDK directory not found! Please wait for the JDK download/extraction to finish."
    Exit 1
}

# Dynamically locate the JDK home directory inside the extraction folder
$jdkHomeDir = Get-ChildItem -Path $jdkRoot -Directory | Select-Object -First 1
if (-not $jdkHomeDir) {
    Write-Error "No JDK subdirectory found inside $jdkRoot! Extraction might still be in progress."
    Exit 1
}

$env:JAVA_HOME = $jdkHomeDir.FullName
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

Write-Host "Using Java Home: $env:JAVA_HOME" -ForegroundColor Cyan
Write-Host "Java Version Info:" -ForegroundColor Cyan
& java -version

# Run Gradle Build
Write-Host "`nRunning Gradle build..." -ForegroundColor Yellow
$gradleCmd = ".\gradlew.bat"
if (-not (Test-Path $gradleCmd)) {
    Write-Error "gradlew.bat not found in the workspace root!"
    Exit 1
}

# Run the build command
& $gradleCmd build

if ($LASTEXITCODE -eq 0) {
    Write-Host "`n=============================================" -ForegroundColor Green
    Write-Host "   BUILD SUCCESSFUL!" -ForegroundColor Green
    Write-Host "=============================================" -ForegroundColor Green
    
    $jarPath = Get-ChildItem -Path "A:\MCMDS\build\libs" -Filter "*.jar" | Where-Object { -not $_.Name.Contains("-sources") -and -not $_.Name.Contains("-dev") } | Select-Object -First 1
    if ($jarPath) {
        Write-Host "Compiled Mod JAR is located at: $($jarPath.FullName)" -ForegroundColor Green
        Write-Host "You can copy this JAR to your PojavLauncher's '.minecraft/mods' folder!" -ForegroundColor Green
    } else {
        Write-Host "Build succeeded, but compiled jar was not found in build/libs!" -ForegroundColor Yellow
    }
} else {
    Write-Host "`n=============================================" -ForegroundColor Red
    Write-Host "   BUILD FAILED!" -ForegroundColor Red
    Write-Host "=============================================" -ForegroundColor Red
    Exit 1
}
