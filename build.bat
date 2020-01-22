@echo off

echo "==== starting to build ===="

cd "%~dp0"

@rem call mvn clean package -pl begin-service,begin-web -am  -DskipTests

call mvn clean package -pl begin-service -am  -DskipTests

echo "==== building finished ===="

pause