/*
This file is part of Helios programm
Copyright (C) 2013 Philippe Yves Poupon
	
This program is distributed under the terms of the GNU General Public Licence GPL v3

Details of these licenses can be found at: www.gnu.org/licenses

This file is part of Helios.

Helios is distributed in the hope that it will be useful, but WITHOUT ANY
   WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
   A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

package phipo.Helios;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.Timer;

//********************************************************
public final class ManagerPlanet extends Thread implements ActionListener{
	Timer         c_timer;

	Graphics      c_graphics=null;
	Dimension     c_vsize;
	Planet        c_TargetPlanet=null;

	synchronized void setTargetPlanet( Planet p_planet ){
		c_TargetPlanet = p_planet;
	}

	void setGraphics(  Graphics p_graphics,
										 Dimension     p_vsize ){
		c_graphics = p_graphics;
		c_vsize  = p_vsize;
	}

	public  double    c_temps =0;
	public  Planet    c_centre=null;	
	public  double    c_zoom = 9.0;
	public  Point     c_vise =  new Point( 0, 0 );
	public 	boolean   c_draw_orbite=true;
	public	boolean   c_draw_trajectoire=false;
	public  boolean   c_must_clear=false;
	public  int       c_val_timer=20;

	public void     setDrawTrajectoire( boolean p_draw_trajectoire ) { c_draw_trajectoire  = p_draw_trajectoire; }
	public void     setSpeed( int p_vit ){	
		c_val_timer = 	p_vit;
		//		c_timer.setDelay( 100/c_val_timer);
	}
	//----------------------------
		ManagerPlanet() {
			start();
		init();

		//		c_timer = new Timer( 100/c_val_timer, this );
		//		c_timer.start();
	}
		//----------------------------
	public void run(){
		//		System.out.println(">>run");
    
    while (true) {
      try {Thread.sleep(100/c_val_timer);} catch (InterruptedException e){}
			draw();
			computePosition();
      //      System.out.println(">>timer");
    }
	}
	//----------------------------
	synchronized void  draw(){
		if( c_graphics == null )// || true)
			return ;
		
		if( c_draw_trajectoire == false || c_must_clear)
			{
				c_graphics.setColor( Color.black );				
				c_graphics.clearRect(  0, 0,  c_vsize.width, c_vsize.height);
				c_must_clear = false;
			}

		if( c_TargetPlanet != null )
			{
				c_vise.setLocation(  -(c_TargetPlanet.c_x*c_zoom),
														 -(c_TargetPlanet.c_y*c_zoom) );
			}
		
		Point l_vise = new Point( (int)(c_vise.getX()+c_vsize.getWidth()*0.5), (int)(c_vise.getY()+c_vsize.getHeight()*0.5));
		

		c_centre.drawChild( c_graphics, c_zoom, l_vise, c_vsize, c_draw_orbite);		 		
	}
	//----------------------------
	void computePosition( ){
		if( c_centre != null)
			c_centre.computePositionChild( c_temps);
		c_temps++;
	}
	//----------------------------
	void drawTree(  Graphics g, int p_hauteur_ligne, int p_decalage_y ){
		g.setColor( Color.black );				
		g.clearRect(  0, 0,  2000, 2000);

		c_centre.drawTree( g, 0, 10, p_hauteur_ligne, p_decalage_y);
	}
	//----------------------------
	ArrayList getPlanetList(){
		return c_centre.getPlanetList(null);
	}
	//----------------------------
	void init() {
		/*		
		c_centre  = new Planet( "Soleil", 18, Color.yellow, null, 0, 0,  1,  0 );
		Planet Soleil = c_centre;
		Planet Mercure = new Planet( "Mercure", 3, Color.gray,   Soleil, 60, 60, 100,  0 );
		Planet Venus   = new Planet( "Venus",   7, Color.green,  Soleil, 120,120, 200,  0 );
		
		Planet Terre   = new Planet( "Terre",   7, Color.blue,   Soleil, 250, 240, 365,  0 );
		Planet Lune    = new Planet( "Lune",    3, Color.gray,   Terre, 30,30, 28,  0 );
		Planet LEM     = new Planet( "LEM",     1, Color.red,     Lune, 12, 12, 20, 0 );


		Planet Mars = new Planet( "Mars", 4, Color.orange, Soleil, 350,350, 300,  660 );
		Planet Doemos = new Planet( "Doemos", 1, Color.orange,Mars, 15, 12, 10, 60 );
		Planet Phoebos = new Planet( "Phoebos", 1, Color.orange,Mars, 20, 18, 15,  660 );
		
		Planet Jupiter = new Planet( "Jupiter", 15, Color.blue, Soleil, 600, 600, 800, 10 );
		Planet Io = new Planet( "Io", 3,  Color.blue,Jupiter, 25,25, 20, 10 );
		Planet Ganymede = new Planet( "Ganymede", 2,  Color.blue,Jupiter, 30, 30, 40, 10 );
		Planet Europe = new Planet( "Europe", 3,  Color.blue,Jupiter, 45, 45, 45,  50 );
		
		
		Planet Saturne = new Planet( "Saturne", 12, Color.gray , Soleil, 700, 700, 900,  800 );
		Planet anneau1 = new Planet( "Saturne", 1, Color.gray ,Saturne, 30, 30, 30,  800 );
		Planet anneau2 = new Planet( "Saturne", 1, Color.gray ,Saturne, 30, 30, 32, 900 );
		Planet anneau3 = new Planet( "Saturne", 1, Color.gray ,Saturne, 30, 30, 35,  0 );
		
		Planet Titan = new Planet( "Titan", 3, Color.gray, Saturne, 40,40, 40,  3500 );
		
		
		Planet Neptune = new Planet( "Neptune", 10, Color.blue , Soleil, 800,800, 900,  1700 );
		Planet Nereide = new Planet( "Nereide", 2, Color.blue, Neptune, 30, 30, 30,  1700 );
		Planet Triton = new Planet( "Triton", 4, Color.blue,Neptune, 40, 40, 42,  700 );
		
		Planet Uranus = new Planet( "Uranus", 9, Color.blue , Soleil, 850, 850, 900,  17000 );
		
		Planet Pluton = new Planet( "Pluton", 3, Color.gray , Soleil, 900, 800, 2000, 7000 );
		Planet Charon = new Planet( "Pluton", 2, Color.gray ,  Pluton, 20, 20,  20,  17000 );
 */
		Planet.c_centre  = c_centre     = new Planet( "Soleil", 1390000/4,   Color.yellow, null,  0,  0,  1,  0 );
		Planet Soleil = c_centre;

		Planet Mercure = new Planet( "Mercure",   4878,   Color.gray,   Soleil,   
																 57910000,    1.20563,      88,  0 );
		Planet Venus   = new Planet( "Venus",    12103.6, Color.green,  Soleil,  
																 108200000,   1.00685,     224,    0 );		
		Planet Terre   = new Planet( "Terre",    12756.3, Color.blue,   Soleil,  
																 149600000,   1.0167,     365,  0 );
		Planet Lune    = new Planet( "Lune",      3476,   Color.gray,   Terre,     
																 384400,      1,        28,  0 );    //?
		
		Planet LEM     = new Planet( "LEM",     0.1, Color.red,   Lune,  
																 30000, 1.5, 2, 0 ); //?
		
		Planet Mars    = new Planet( "Mars",      6794,  Color.orange,   Soleil, 
																 227940000,   1.09335,     686,  0 );
		
		
		Planet Phoebos = new Planet( "Phoebos", 22.2, Color.orange, Mars, 
																 9378*3, 1.2, 1,  660 );
		Planet Doemos  = new Planet( "Doemos",   12.6, Color.orange, Mars, 
																 23459*6, 1.3, 2, 60 );
		
		Planet Jupiter = new Planet( "Jupiter", 142984/2, Color.blue, Soleil, 
																 778330000, 1.04843, 11*365+30*10, 10 );

		//		Planet Metis   = new Planet( "Metis", 40,  Color.blue,Jupiter, 
		//																 128000, 1, 20, 10 );
		//		Planet Adrastee= new Planet( "Adrastee", 20,  Color.blue,Jupiter, 
		//																 129000, 1, 50, 10 );
		//		Planet Amalthee= new Planet( "Amalthee", 196,  Color.blue,Jupiter, 
		//																 181000, 1, 50, 10 );
		//		Planet Thebe   = new Planet( "Thebe", 100,  Color.blue,Jupiter, 
		//																 222000, 1, 50, 10 );


		Planet Io      = new Planet( "Io",  3630,  Color.blue,Jupiter, 
																 422000, 1, 10, 10 );
		Planet Europe  = new Planet( "Europe", 3138,  Color.blue,Jupiter, 
																 671000, 1, 20, 20 );
		Planet Ganymede= new Planet( "Ganymede", 5262,  Color.blue,Jupiter, 
																 1070000, 1, 30, 40 );
		Planet Callisto= new Planet( "Callisto", 4800,  Color.blue,Jupiter, 
																 1883000, 1, 40, 20 );

		//		Planet Leda    = new Planet( "Leda", 16,  Color.blue,Jupiter, 
		//																 11094000, 1, 70, 50 );
		
		
		Planet Saturne = new Planet( "Saturne", 120536/2, Color.gray , Soleil, 
																 1429400000, 1.05568, 29.5*365,  800 );

		Planet Mimas = new Planet( "Mimas", 392, Color.gray ,Saturne, 
																 186000, 1, 30,  70 );
		Planet Encelade = new Planet( "Encelade", 520, Color.gray ,Saturne, 
																 238000, 1, 40,  60 );
		Planet Thethys  = new Planet( "Thethys", 1060, Color.gray ,Saturne, 
																 295000, 1, 50,  50 );
		Planet Dione    = new Planet( "Dione", 1020, Color.gray ,Saturne, 
																 377000, 1, 60,  0 );
		Planet Rhea     = new Planet( "Rhea", 1530, Color.gray ,Saturne, 
																 527000, 1, 70,  8 );
		Planet Titan    = new Planet( "Titan", 5150, Color.gray ,Saturne, 
																 1222000, 1, 80,  30 );
		Planet Hyperion = new Planet( "Hyperion", 286, Color.gray ,Saturne, 
																 1481000,1 , 90,  70 );
		Planet Japet    = new Planet( "Japet", 1460, Color.gray ,Saturne, 
																 3561000, 1, 800,  80 );
		Planet Phoebe   = new Planet( "Phoebe", 220, Color.gray ,Saturne, 
																 12952000, 1, 700,  200 );	

	
		Planet Uranus    = new Planet( "Uranus", 51118/2, Color.blue , Soleil, 
																2870990e3, 1.04633, 84*365,  17000 );
		Planet  Miranda  = new Planet( "Miranda", 472, Color.gray, Uranus,
																 130000, 1, 20,  700 );	
		Planet  Ariel    = new Planet( "Ariel", 1158, Color.gray, Uranus,
																 191000, 1, 30,  20 );	
		Planet  Umbriel  = new Planet( "Umbriel", 1170, Color.gray, Uranus,
																 266000, 1, 50,  2 );	
		Planet  Titania  = new Planet( "Titania", 1578, Color.gray, Uranus,
																 436000, 1, 70,  200 );	
		Planet  Oberon   = new Planet( "Oberon", 1522, Color.gray, Uranus,
																 583000, 1, 80,  400 );	




		Planet Neptune = new Planet( "Neptune", 49532/2, Color.blue , Soleil, 
																 4504e6, 1.00900,  164*365, 44 );

		Planet Protee = new Planet( "Protee", 418, Color.blue,Neptune,
																118000, 1, 42,  700 );
		Planet Triton = new Planet( "Triton", 2700, Color.blue,Neptune, 
																355000, 1, 42,  70 );
		Planet Nereide = new Planet( "Nereide", 340, Color.blue, Neptune, 
																 5509000, 1, 30,  1700 );
		
		
		Planet Pluton = new Planet( "Pluton", 2274, Color.gray , Soleil, 
																5913.52e6, 1.24864, 247*365, 7000 );
		Planet Charon = new Planet( "Charon", 1172, Color.gray ,  Pluton, 
																19640, 1,  20,  170 );
		
	}
	//----------------------------

	public void actionPerformed( ActionEvent p_ev ){		
		draw();
		computePosition();
	}	
        
}
