echo Tyrant build script
echo by mikera, Jan 2001
set -x
sh make-tyrant.sh
sh make-tyrant-jar.sh
set +x
echo To run Tyrant type:
echo java -jar Tyrant.jar
set -x
sh make-tyrant-src-jar.sh
#sh make-tyrant-src-zip.sh
#sh make-tyrant-zip.sh
#sh make-web-dist.sh
set +x
