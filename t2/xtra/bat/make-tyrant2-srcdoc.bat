REM copy /y .\rl\*.java .\xtra\srcdoc\*.java
@ECHO Calling java doc...
@javadoc -linksource -d .\xtra\srcdoc .\rl\*.java
@ECHO Calling Source High Lighting
@for %%f in (.\rl\*.java) do shl -i%%f -o.\xtra\srcdoc\src-html\%%f -sjava -fhtml -t4 -n -d
@ECHO Deleting old HTML files...
@del .\xtra\srcdoc\src-html\rl\*.html
@ECHO Placing new HTML files...
@rename .\xtra\srcdoc\src-html\rl\*.java *.html
@ECHO Upgrading current HTML files
@java judo t.judo
