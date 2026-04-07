@echo off
REM Helper script to simplify Podman commands on Windows
REM Usage: 
REM   run-podman-compose.cmd up --build
REM   run-podman-compose.cmd down
REM   run-podman-compose.cmd ps

REM Check if podman-compose is available
where podman-compose >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ============================================================
    echo ERROR: podman-compose not found on your system!
    echo.
    echo To install podman-compose, choose one of the options below:
    echo.
    echo Option 1 - Using Chocolatey (Windows):
    echo   choco install podman-compose
    echo.
    echo Option 2 - Using pip (requires Python):
    echo   pip install podman-compose
    echo.
    echo Option 3 - Manual installation:
    echo   Visit: https://github.com/containers/podman-compose
    echo ============================================================
    echo.
    exit /b 1
)

REM Execute podman-compose with the provided arguments
podman-compose %*
exit /b %ERRORLEVEL%
