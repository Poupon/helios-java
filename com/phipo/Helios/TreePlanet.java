/*
This file is part of Helios programm
Copyright (C) 2001-2013 Philippe Yves Poupon
	
This program is distributed under the terms of the GNU General Public Licence GPL v3

Details of these licenses can be found at: www.gnu.org/licenses

This file is part of Helios.

Helios is distributed in the hope that it will be useful, but WITHOUT ANY
   WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
   A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

package phipo.Helios;


import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.Scrollable;

import java.util.ArrayList;



//***********************************
final public class TreePlanet extends JPanel implements MouseListener { //, Scrollable{
	ManagerPlanet  c_mplanet;
	PCanvas        c_canvas;
	ArrayList      c_planetlist=null;
	JScrollPane    c_scrollpane;

	void setScrollPane(JScrollPane p_scrollpane){ c_scrollpane = p_scrollpane; }

	final int c_hauteur_ligne = 30;
	final int p_decalage_y    = 40;

	//--------------------------
	public TreePlanet(ManagerPlanet p_mplanet, PCanvas p_canvas ){

		super();

	 	setSize(200,300);

		c_mplanet = p_mplanet; 
		addMouseListener( this );
		//		c_planetlist = c_mplanet.getPlanetList( );		
	}
	//-------------------------- 
public	void update( Graphics g ){
	}
	//--------------------------
	public void paint( Graphics g ){

		super.paintComponent(g);

		c_mplanet.drawTree( g, c_hauteur_ligne, p_decalage_y ); 
		c_planetlist = c_mplanet.getPlanetList( );		

		setPreferredSize( new Dimension( 400, c_planetlist.size()*c_hauteur_ligne) );
		if( c_scrollpane != null ) c_scrollpane.revalidate();
	}

	//-------------------------------
	public void mouseClicked( MouseEvent p_e ) {

		repaint( new Rectangle( 0, 0, 2000, 2000 ));	

		int l_y = p_e.getY() / c_hauteur_ligne;
		if( c_planetlist == null || l_y <0 || l_y>= c_planetlist.size())
			return;
	 		

		Planet l_planet = (Planet)c_planetlist.get( l_y );

		if( SwingUtilities.isLeftMouseButton( p_e ) == true )
			{
				c_mplanet.c_vise.setLocation(  -(l_planet.c_x*c_mplanet.c_zoom),
																			 -(l_planet.c_y*c_mplanet.c_zoom));
			 c_mplanet.setTargetPlanet( null );
			}
		if( SwingUtilities.isRightMouseButton( p_e ) == true )
			{
				c_mplanet.setTargetPlanet( l_planet );
			}
	}
	//-------------------------------
	public void mousePressed( MouseEvent p_e ) {
	} 

	public void mouseReleased( MouseEvent p_e ) {
	}

	public void mouseEntered( MouseEvent p_e ) {
	}

	public void mouseExited( MouseEvent p_e ) {
	}
	//--------------------------------------
	//--------------------------------------
	//--------------------------------------
	public Dimension getPreferredScrollableViewportSize(){
		return getPreferredSize();
		
	
}
//***********************************
