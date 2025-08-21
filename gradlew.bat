@echo off
REM Forward to android_frontend gradle wrapper to satisfy CI calls from repo root.
setlocal ENABLEDELAYEDEXPANSION
set SCRIPT_DIR=%~dp0
set WRAPPER=%SCRIPT_DIR%android_frontend\gradlew.bat

if not exist "%WRAPPER%" (
  echo Gradle wrapper not found at %WRAPPER% 1>&2
  exit /b 127
)

call "%WRAPPER%" %*
