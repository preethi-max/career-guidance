@echo off
REM Maven wrapper script for Student Career Guidance project
set "MAVEN_CMD=C:\Users\medib\tools\apache-maven-3.9.9\bin\mvn.cmd"
if exist "%MAVEN_CMD%" (
    "%MAVEN_CMD%" %*
) else (
    mvn %*
)
