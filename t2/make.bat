@echo Tyrant2 build script
@echo by tdemuyt, may 2002
@echo Compiling class files...
@call xtra/bat/make-tyrant2
@echo Creation of jar file... 
@call xtra/bat/make-tyrant2-jar
@echo Clean up...
@del *.class /s >log.del
@del log.del
@beep
