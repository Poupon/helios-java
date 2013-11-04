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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JCheckBox;


//***********************************
final public class DialogPlanet extends JFrame 	implements ItemListener, ActionListener, ChangeListener{
	
public 	ManagerPlanet c_mplanet;
public 	PCanvas c_canvas;

	JCheckBox c_orbite;
	JCheckBox c_trajectoire;
public 	JSlider   c_slider_vitesse;
public 	JSlider   c_slider_zoom;  
public 	JSlider   c_slider_rafraichissement;

	JPanel    c_center_panel;
	JPanel    c_sud_panel;
	JLabel    c_status;
	JButton   c_button_close;
	JButton   c_button_recentrer;

	//-------------------------------------
	public DialogPlanet( ManagerPlanet p_mplanet, PCanvas p_canvas) {
		super("Reglages Helios");
		
		c_mplanet = p_mplanet;
		c_canvas  = p_canvas;


		getContentPane().setLayout( new BorderLayout() );

		//==========
		setSize(300,400);

		JLabel l_tmp_label;
		JPanel l_panel_center = new JPanel();
		l_panel_center.setLayout( new GridLayout( 0, 1 ));
		l_panel_center.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

		c_orbite      = new JCheckBox( "Orbite" );
		c_orbite.addItemListener( this );
		l_panel_center.add( c_orbite );
		
		c_trajectoire = new JCheckBox( "Trajectoire" );
		c_trajectoire.addItemListener( this );
		l_panel_center.add( c_trajectoire );

		l_tmp_label = new JLabel( "Vitesse");
		l_tmp_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		l_panel_center.add( l_tmp_label );

		c_slider_vitesse = makeSlider( 1, 100, c_mplanet.c_val_timer );		
		l_panel_center.add( c_slider_vitesse );

		l_tmp_label = new JLabel( "Zoom");
		l_tmp_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		l_panel_center.add( l_tmp_label );
		c_slider_zoom =  makeSlider(  3, 30, (int)(Math.sqrt(c_mplanet.c_zoom) ));		
		l_panel_center.add( c_slider_zoom );
		
		JButton c_button_recentrer  = new JButton( "Recentrer" );
		c_button_recentrer.setActionCommand( "recentrer");
		c_button_recentrer.addActionListener( this ); 
		l_panel_center.add( c_button_recentrer );


		l_tmp_label = new JLabel( "Rafraichissement");
		l_tmp_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		l_panel_center.add( l_tmp_label );

		c_slider_rafraichissement = makeSlider( 1, 20, 21-c_canvas.c_val_timer );		
		l_panel_center.add( c_slider_rafraichissement );

		getContentPane().add( l_panel_center, BorderLayout.CENTER );
		//==========
	 
		c_button_close = new JButton( "Hide" );
		c_button_close.setActionCommand( "hide");
		c_button_close.addActionListener( this );
		getContentPane().add( c_button_close, BorderLayout.SOUTH );

		pack();
		c_orbite.setSelected(c_mplanet.c_draw_orbite);
		c_trajectoire.setSelected(c_mplanet.c_draw_trajectoire);												
	}
	//-------------------------------------
	 JSlider makeSlider( int p_min, int p_max, int p_current ){
		 
		JSlider l_slider = new JSlider( JSlider.HORIZONTAL, p_min, p_max, p_current );		
		l_slider.addChangeListener( this ); 
		l_slider.setMajorTickSpacing(10);
		l_slider.setMinorTickSpacing(1);
		l_slider.setPaintTicks(false);
		l_slider.setPaintLabels(false);
		l_slider.setBorder( BorderFactory.createEmptyBorder(0, 0, 10, 0));
		return l_slider;
	}
	//---------------------
	public void itemStateChanged(ItemEvent p_e ){

		//		System.out.println( "itemStateChanged" );

		Object l_source = p_e.getItemSelectable();

		if( l_source == c_orbite )
			{
				if( p_e.getStateChange() == ItemEvent.DESELECTED )
					c_mplanet.c_draw_orbite = false;
				else
					{
						c_mplanet.c_draw_orbite      = true;
						c_mplanet.c_draw_trajectoire = false;
						c_trajectoire.setSelected(false);						
					}
				
				c_mplanet.c_must_clear=true;
			}

		if( l_source == c_trajectoire )
			{
				if( p_e.getStateChange() == ItemEvent.DESELECTED )
					c_mplanet.c_draw_trajectoire = false;
				else
					{
						c_mplanet.c_draw_trajectoire = true;
						c_mplanet.c_draw_orbite      = false;
						c_orbite.setSelected(false);	
					}

				c_mplanet.c_must_clear =true;
			}
	}
	//---------------------
	public void actionPerformed( ActionEvent p_e ){
		
		if( p_e.getActionCommand().equals("hide"))
			setVisible(false);
		else
			if( p_e.getActionCommand().equals("recentrer"))
				{
					c_mplanet.c_vise.setLocation(0,0);
					c_mplanet.c_must_clear =true;
					c_mplanet.setTargetPlanet( null );
				}
	}
		//---------------------
public void stateChanged( ChangeEvent p_e ){
		JSlider l_source = (JSlider)p_e.getSource();
	 	if( l_source == c_slider_vitesse )
			{
				if( !l_source.getValueIsAdjusting()){
					int l_val = (int) l_source.getValue();
					c_mplanet.setSpeed( l_val );
				}
			}
		else
			if( l_source == c_slider_zoom )
				{
					if( !l_source.getValueIsAdjusting()){
						int l_val  =  (int)l_source.getValue();
						c_mplanet.c_zoom = l_val*l_val;
						c_mplanet.c_must_clear =true;
					}
				}
		else
			if( l_source ==  c_slider_rafraichissement )
				{
					if( !l_source.getValueIsAdjusting()){
						int l_val  =  (int)l_source.getValue();
						c_canvas.setSpeed( 21-l_val );					
					}
				}
	}
}
//***********************************
