call cd AskMateREST
call mvn package -Dmaven.test.skip=true
call cd ..
FOR %%F IN (./AskMateREST/target/*.jar) DO (
 set filename=%%F
 goto run
)
:run
docker build -t askmate . --build-arg jarfile=%filename%
