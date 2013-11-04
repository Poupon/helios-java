
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
final public class DialogTree extends JFrame implements ActionListener {
	
public 	ManagerPlanet c_mplanet;
public 	PCanvas    c_canvas;
public  TreePlanet c_tree_canvas;


	JPanel    c_center_panel;
	JPanel    c_sud_panel;
	JButton   c_button_close;
	JScrollPane  c_scrollpane;

	//-------------------------------------
	public DialogTree( ManagerPlanet p_mplanet, PCanvas p_canvas) {
		super("Planetes");
		
		c_mplanet = p_mplanet;
		c_canvas  = p_canvas;

		getContentPane().setLayout( new BorderLayout() );

		//==========
		setSize(300,400);

		JLabel l_tmp_label;
		JPanel l_panel_center = new JPanel();
		l_panel_center.setLayout( new GridLayout( 0, 1 ));
		l_panel_center.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

		//l_panel_center.add( (c_tree_canvas = new TreePlanet( c_mplanet, c_canvas )));
		c_scrollpane = new JScrollPane( (c_tree_canvas = new TreePlanet( c_mplanet, c_canvas )),																		
																		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
																		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		c_tree_canvas.setScrollPane(c_scrollpane);

		c_scrollpane.setPreferredSize( new Dimension(200, 200) );
		c_scrollpane.revalidate();
		l_panel_center.add( c_scrollpane );

		getContentPane().add( l_panel_center, BorderLayout.CENTER );
		//==========
	 
		c_button_close = new JButton( "Hide" );
		c_button_close.setActionCommand( "hide");
		c_button_close.addActionListener( this );
		getContentPane().add( c_button_close, BorderLayout.SOUTH );
		pack();
	}
	//-------------------------------------
	public void actionPerformed( ActionEvent p_e ){
		
		if( p_e.getActionCommand().equals("hide"))
			setVisible(false);
	}	
}
//***********************************
