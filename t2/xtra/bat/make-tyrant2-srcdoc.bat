cd..
cd..

@ECHO Calling java doc...
@REM Javadoc should be installed with JDK, make sure the bin folder is in the path
@javadoc -use -private -package -linksource -d .\xtra\srcdoc .\rl\*.java
@ECHO Calling Source High Lighting
@REM Source High Lighting : http://www.gnu.org/software/src-highlite/source-highlight.html
@REM You might have to rename the executable to shl
@REM this works with version 1.4, later version might need up an updated t.judo
@for %%f in (.\rl\*.java) do shl -i%%f -o.\xtra\srcdoc\src-html\%%f -sjava -fhtml -t4 -n -d
@ECHO Deleting old HTML files...
@del .\xtra\srcdoc\src-html\rl\*.html
@ECHO Placing new HTML files...
@rename .\xtra\srcdoc\src-html\rl\*.java *.html
@ECHO Upgrading current HTML files
@java judo .\xtra\judo\t.judo

cd xtra
cd bat