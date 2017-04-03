cd GraphicsEngine-OpenGLRunner
set MAVEN_OPTS="-Djava.library.path=target/natives"
mvn exec:java -Dexec.mainClass=com.ran.engine.opengl.main.OpenGLMain