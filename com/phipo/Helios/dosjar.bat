
cd   /home/team02/phipo/DEV/java/src; 
del  /home/team02/phipo/DEV/java/src/phipo/Helios/Helios.jar
jar cfm /home/team02/phipo/DEV/java/src/phipo/Helios/Helios.jar phipo/Helios/Helios.mf phipo/Helios/*.class; cd /home/team02/phipo/DEV/java/src; jar uf /home/team02/phipo/DEV/java/src/phipo/Helios/Helios.jar phipo/PhiLib/Interface/*.class phipo/PhiLib/Lang/*.class phipo/PhiLib/Util/*.class
