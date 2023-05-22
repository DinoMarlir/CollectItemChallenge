mkdir collectitemchallenge
cd collectitemchallenge
curl https://api.papermc.io/v2/projects/paper/versions/1.19.4/builds/538/downloads/paper-1.19.4-538.jar >> paper.jar
mkdir plugins
cd plugins
curl https://maven.blueamethyst.me/releases/tech/marlonr/collectitemchallenge/1.1/collectitemchallenge-1.1.jar >> collectitemchallenge-1.0.jar
cd ..
echo java -Xmx3G -jar paper.jar nogui > start.bat
echo Now you can start the server with the start.bat!
pause