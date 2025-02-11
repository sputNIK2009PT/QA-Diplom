@echo off
echo Choose subd:
echo 1 - psql
echo 2 - mysql
choice /c 12 /m "Choose subd:"
set choice=%errorlevel%
timeout /t 1
echo docker-compose up...
start cmd /k "docker-compose up"
timeout /t 25

if %choice%==1 (
    start cmd /k "java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar ./artifacts/aqa-shop.jar"
    timeout /t 15
    gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app
    gradlew allureServe
)

if %choice%==2 (
    start cmd /k "java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar ./artifacts/aqa-shop.jar"
    timeout /t 15
    gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app
    gradlew allureServe
)

exit