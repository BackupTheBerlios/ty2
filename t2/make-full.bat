@echo Tyrant III build script
@echo by Tom Demuyt, 27-09-03
@cd xtra
@cd bat
call make-tyrant
call make-tyrant-jar

@echo To run Tyrant type:
@echo java -jar Tyrant.jar

call make-tyrant-src-jar
call make-tyrant-src-zip
call make-tyrant-zip
call make-web-dist
call srcdoc
@cd..
@cd..