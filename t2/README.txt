To build Tyrant in Windows:
===========================

1. Make sure the java compiler and tools (JDK\bin etc.) are in the
current path. You can do this with:

  path (your JDK \bin directory here);%PATH

2. cd to the directory containing make.bat

3. Enter the command "make"

4. This should build an executable file called Tyrant.jar

You can now run Tyrant with the command:

  java -jar Tyrant.jar


To build Tyrant in Linux\Unix:
==============================

Scripts are totally out of date.

But all you have to do is compile the main application class:

  javac mikera/tyrant/QuestApplication.class

And build the resulting class and image files into a .jar file:

  jar cvfm Tyrant.jar MANIFEST.MF mikera/tyrant/*.class images/*.gif

And run it with:

  java -jar Tyrant.jar