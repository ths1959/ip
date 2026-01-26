@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
REM REMOVED: -cp ..\src\main\java
javac -Xlint:none -d ..\bin ..\src\main\java\memomax\*.java ..\src\main\java\memomax\task\*.java ..\src\main\java\memomax\ui\*.java ..\src\main\java\memomax\parser\*.java ..\src\main\java\memomax\storage\*.java ..\src\main\java\memomax\exception\*.java ..\src\main\java\memomax\tasklist\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin memomax.MemoMax < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT