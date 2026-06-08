@echo off
chcp 65001 > nul

set APP_DIR=%~dp0
set LOCAL_JAVA=%APP_DIR%jdk\bin\java.exe

if exist "%LOCAL_JAVA%" (
    "%LOCAL_JAVA%" -cp "%APP_DIR%bin" HealthApp.Main
) else (
    java -cp "%APP_DIR%bin" HealthApp.Main
)

pause
