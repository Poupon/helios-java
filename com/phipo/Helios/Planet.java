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
import java.math.*;
import java.util.ArrayList;


//********************************************************
public final class Planet {

	String  c_name;
  double  c_size;
  double  c_paxe, c_gaxe;
  double  c_depart;
  double  c_duree_revol;
  Color   c_color;
  double  c_x,c_y;
	Planet  c_mother=null;

	ArrayList c_listchild;

	public static  Planet c_centre;
	//----------------------------

  double getX() { return c_x; }
  double getY() { return c_y; }
	//----------------------------
 public  Planet( String p_name, double p_size, Color p_color , Planet p_mother,
		     double p_gaxe, double p_excentricite, double p_duree_revol, double p_depart )
  {
    //   System.out.println(">>Satellite " + str );
		c_listchild = new ArrayList();

   c_name   = p_name ;   
	 //   c_size   = p_size/10000;

   c_size   = Math.sqrt(p_size/130000);
	 c_color  = p_color;
   c_mother = p_mother;
	 //	 c_gaxe   = p_gaxe/1000000;
	 //	 c_paxe   = p_paxe/1000000;

	 if( p_mother == c_centre )
		 {
			 c_gaxe   = Math.sqrt(p_gaxe/1000000);
			 c_paxe   = c_gaxe/p_excentricite;
		 }
	 else
		 {
			 c_gaxe   = Math.sqrt(p_gaxe/300000);
			 c_paxe   = c_gaxe/p_excentricite;
		 }

	 c_duree_revol = p_duree_revol;
	 c_depart = p_depart;
   int tmpsz;


   
	 c_x = 0;
	 c_y = 0;
   if( c_mother != null )
    {
      c_mother.c_listchild.add( this );
    }     
  }
  //----------------------------
	final	void computePosition( double p_temps ) {

			if( c_mother == null )
				return;
			
			double l_pos = p_temps/c_duree_revol;
			c_x = Math.cos( l_pos+c_depart ) * c_gaxe*0.5 + c_mother.getX();
			c_y = Math.sin( l_pos+c_depart ) * c_paxe*0.5 + c_mother.getY();
	}		
  //----------------------------
	void computePositionChild( double p_temps ) {
		
		computePosition( p_temps );
		for( int i=0; i<c_listchild.size(); i++) 
			{
				((Planet)(c_listchild.get(i))).computePositionChild( p_temps );			
			}
	}				
  //----------------------------
	void draw( Graphics G, double p_zoom, Point p_vise, Dimension p_vsize, boolean p_draw_orbite ) {
		
		double l_x = c_x * p_zoom + p_vise.getX();
		double l_y = c_y * p_zoom + p_vise.getY();

		double l_size = c_size*p_zoom;

		
		if( p_draw_orbite && c_mother != null ) 
			{
				
				G.setColor( Color.gray );
				G.drawOval(  (int)(p_vise.getX()+(c_mother.c_x-(c_gaxe*0.5))* p_zoom), 
										 (int)(p_vise.getY()+(c_mother.c_y-(c_paxe*0.5))* p_zoom), 
										 (int)(c_gaxe*p_zoom),  (int)(c_paxe*p_zoom ));
			}

		if( l_x > 0 && l_y > 0 && l_x < p_vsize.getWidth() && l_y < p_vsize.getHeight())
			{
				G.setColor( c_color );
				G.fillOval( (int)(l_x-l_size*0.5), (int)(l_y-l_size*0.5), (int)(l_size), (int)(l_size) );
			}

	}
  //----------------------------
	void drawChild(  Graphics G, double p_zoom, Point p_vise, Dimension p_vsize, boolean p_draw_orbite ) {
		for( int i=0; i<c_listchild.size(); i++)
			{
				((Planet)(c_listchild.get(i))).drawChild( G, p_zoom, p_vise, p_vsize, p_draw_orbite );
			}
		draw( G, p_zoom, p_vise, p_vsize, p_draw_orbite );
	}		
  //----------------------------
	int drawTree(  Graphics G, int p_depth, int p_y,  int p_hauteur_ligne, int p_decalage_y) {

		int l_x = p_depth * p_decalage_y+10;
		G.setColor( c_color );

		double l_size = Math.sqrt(c_size);
		l_size *= 20 ;

		G.fillOval( l_x, p_y, ((int)l_size), ((int)l_size) );
		//		G.setColor( Color.white );
		G.setColor( Color.black );
		G.drawString( c_name, l_x + ((int)l_size) +10 , p_y + p_decalage_y/2 );


		for( int i=0; i<c_listchild.size(); i++)
			{
				p_y = ((Planet)(c_listchild.get(i))).drawTree( G, p_depth+1, p_y+p_hauteur_ligne, p_hauteur_ligne,  p_decalage_y  );
			}

		return p_y;
	}		
  //----------------------------
	ArrayList getPlanetList( ArrayList p_list) {

		if( p_list == null )
			p_list = new ArrayList();

		p_list.add( this );
		for( int i=0; i<c_listchild.size(); i++)
			{
				((Planet)(c_listchild.get(i))).getPlanetList( p_list );
			}

		return p_list;
	}			
	
}
//********************************************************
