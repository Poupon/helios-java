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

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.*;
import java.awt.*;


//***********************************
final public class Helios extends JFrame {
	
	JMenuBar  c_menubar;
	JPanel    c_sud_panel;
	JLabel    c_status;
	PCanvas   c_canvas;
	ManagerPlanet c_mplanet;
	DialogPlanet c_dialog_planet=null;
	DialogTree c_dialog_tree;

	//-------------------------------------
	public Helios() {
		super("Helios");
				
		//---- Quit this app when the big window closes. 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		setSize(300,300);

		c_menubar = createMenuBar();
		setJMenuBar(c_menubar);

		getContentPane().setLayout( new BorderLayout() );
		c_canvas = new PCanvas( (c_mplanet = new ManagerPlanet()) );
		getContentPane().add( c_canvas, BorderLayout.CENTER );

		c_sud_panel = new JPanel();		
		c_sud_panel.setLayout( new GridLayout( 1, 0 )); 
		c_status  = new JLabel( "footbar" );
		c_sud_panel.add( c_status );
		getContentPane().add( c_sud_panel, BorderLayout.SOUTH );

		c_dialog_planet = new DialogPlanet( c_mplanet, c_canvas );
		c_dialog_planet.setVisible(true);

		c_dialog_tree = new DialogTree( c_mplanet, c_canvas );
		c_dialog_tree.setVisible(true);
	}
	//-------------------------------------
	protected JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Actions");
		menu.setMnemonic(KeyEvent.VK_D);
		JMenuItem menuItem = new JMenuItem("Reglages");
		menuItem.setMnemonic(KeyEvent.VK_R);
		menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
							createDialogReglages();
            }
		});
		menu.add(menuItem);

		
		JMenuItem menuItem2 = new JMenuItem("Liste des planetes");
		menuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
							createDialogTree();
            }
		});
		menu.add(menuItem2);


		menuBar.add(menu);
		return menuBar;
	}
	//-------------------------------------
	protected void createDialogTree() {

		if(c_dialog_tree==null) 
			c_dialog_tree = new DialogTree( c_mplanet, c_canvas );
		c_dialog_tree.setVisible(true);
	}
	//-------------------------------------
	protected void createDialogReglages() {
		if(c_dialog_planet==null) 
			c_dialog_planet = new DialogPlanet( c_mplanet, c_canvas );
		
		c_dialog_planet.setVisible(true);
	}
	//-------------------------------------
	public static void main(String[] args) {
		Helios frame = new Helios();
		frame.setVisible(true);
	}
}
//***********************************
