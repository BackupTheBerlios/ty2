@echo Tyrant III build script
@echo by Tom Demuyt, 27-09-03
@cd xtra
@cd bat
call make-tyrant
@REM call make-tyrant-jar

@echo To run Tyrant type:
@echo java -jar Tyrant.jar

@cd..
@cd..

java rl/QuestApplication
