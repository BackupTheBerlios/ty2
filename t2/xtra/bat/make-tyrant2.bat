@cd..
@cd..

@echo Entering the rl directory...

@rem @cd rl
@rem @echo Deleting all class files...
@rem @del *.class /s > null
@rem @echo Returning to root...
@rem @cd ..
@echo Building main app...

@javac -deprecation rl\QuestApplication.java

@rem echo Listing all extra targets...
@rem java judo -q ./xtra/judo/build rl
@rem echo About to build all extra targets...
@rem call strategy
@rem del strategy.bat

@cd xtra
@cd bat