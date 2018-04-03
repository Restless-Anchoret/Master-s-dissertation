export MAVEN_OPTS="-Djava.library.path=target/natives"
cd GraphicsEngine-Starter
mvn exec:java -Dexec.mainClass=com.ran.engine.starter.main.Main
cd ..
