all::  DialogPlanet.class  DialogTree.class  Helios.class  ManagerPlanet.class  PCanvas.class  Planet.class  TreePlanet.class

clean::
	rm *.class  *.jar *.tmp

jar::
	cd ../..; rm  phipo/Helios/Helios.jar;jar cfm phipo/Helios/Helios.jar phipo/Helios/Helios.mf phipo/Helios/*.class; jar uf phipo/Helios/Helios.jar phipo/Helios/Helios.mf

%.class: %.java
	javac  -classpath "../.." -deprecation -g $*.java
 
