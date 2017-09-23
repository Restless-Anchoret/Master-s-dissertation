set MAVEN_OPTS="-Djava.library.path=target/natives"
cd GraphicsEngine-Starter
call mvn exec:java -Dexec.mainClass=com.ran.engine.starter.main.Main
cd ..
