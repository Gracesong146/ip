@ECHO OFF
pushd "%~dp0"

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\cathy\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    popd
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
if exist data\cathy.txt del data\cathy.txt
java -classpath ..\bin cathy.Cathy < input.txt > ACTUAL.TXT 2>&1

REM compare the output to the expected output
REM ignore all white space differences
FC /W ACTUAL.TXT EXPECTED.TXT > NUL

IF ERRORLEVEL 1 (
    echo Differences detected! Test FAILED.
) ELSE (
    echo No differences encountered. Test PASSED.
)

popd
pause