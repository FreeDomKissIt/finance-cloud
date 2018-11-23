set batPath=%~dp0
%batPath:~0,2%
cd %batPath%
call mvn clean install -Dmaven.test.skip 
pause