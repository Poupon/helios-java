javac  -classpath "../.." -deprecation -g DialogPlanet.java
javac  -classpath "../.." -deprecation -g DialogTree.java
javac  -classpath "../.." -deprecation -g Helios.java
javac  -classpath "../.." -deprecation -g ManagerPlanet.java
javac  -classpath "../.." -deprecation -g PCanvas.java
javac  -classpath "../.." -deprecation -g Planet.java
javac  -classpath "../.." -deprecation -g TreePlanet.java

cd ../..; del  phipo/Helios/Helios.jar;jar cfm phipo/Helios/Helios.jar phipo/Helios/Helios.mf phipo/Helios/*.class; jar uf phipo/Helios/Helios.jar phipo/Helios/Helios.mf