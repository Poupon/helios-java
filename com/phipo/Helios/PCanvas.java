
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

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;

//***********************************
final public class PCanvas extends JPanel 
	implements MouseListener, ActionListener, Runnable{

  Thread   thread ;

	Graphics      c_graphics;
	ManagerPlanet c_mplanet;
	BufferedImage c_buffer;
	Timer         c_timer;
	public  int   c_val_timer=2;

	Dimension     c_size;
	int i=0;

	public void     setSpeed( int p_vit ){	
		c_val_timer = 	p_vit;
		//		c_timer.setDelay( 50*c_val_timer);
	}

	//--------------------------
	public PCanvas(ManagerPlanet p_mplanet ){

		super();

	 	setSize(400,400);

		c_mplanet = p_mplanet;
		c_size    = getSize();
 		addMouseListener( this );		

		//		c_timer = new Timer( 10*c_val_timer, this );
		//		c_timer.start();
		start();
	}

	public void paint( Graphics g ){
		super.paintComponent(g);

		Dimension l_size = getSize();

		if( c_buffer == null || l_size.equals( c_size) == false )
			{
				GraphicsEnvironment l_ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice []    l_gd = l_ge.getScreenDevices();
				GraphicsConfiguration[] l_gc = l_gd[0].getConfigurations();
				
				c_size = l_size;
				c_buffer = l_gc[0].createCompatibleImage( c_size.width, c_size.height );
				//				c_buffer = new BufferedImage( c_size.width, c_size.height,  BufferedImage.TYPE_BYTE_INDEXED );
				if( c_graphics != null) 
					{
						c_graphics.finalize();
					}
				c_graphics = c_buffer.createGraphics();
				c_graphics.setColor( Color.black );				
				c_graphics.clearRect(  0, 0,  c_size.width, c_size.height);
				c_mplanet.setGraphics( c_graphics,  c_size );
			}
	
		synchronized( c_mplanet ){
		g.drawImage( c_buffer, 0, 0, this );				
		}
	}
	//-------------------------- 
public	void update( Graphics g ){
	//	System.out.println( "Update" );
	}
	//-------------------------- 
	public void actionPerformed( ActionEvent p_ev ){		
		//		System.out.println(".");
		repaint( new Rectangle( 0, 0, 2000, 2000 ));
	}	
	//-------------------------- 
	public void mousePressed( MouseEvent p_e ) {
		//		System.out.println( "mouse pressed" );
	} 

	public void mouseReleased( MouseEvent p_e ) {
		//		System.out.println( "mouse released" );
	}

	public void mouseEntered( MouseEvent p_e ) {
		//		System.out.println( "mouse entered" );
	}

	public void mouseExited( MouseEvent p_e ) {
		//		System.out.println( "mouse exited" );
	}

	public void mouseClicked( MouseEvent p_e ) {


		int l_diffx = (int)( c_mplanet.c_vsize.getWidth()*0.5  - p_e.getX() );
		int l_diffy = (int)( c_mplanet.c_vsize.getHeight()*0.5 - p_e.getY() );

		c_mplanet.c_vise.setLocation( c_mplanet.c_vise.getX()+l_diffx, c_mplanet.c_vise.getY()+l_diffy );
		c_mplanet.setTargetPlanet( null );

	}
  //----------------------------
  public void start() {

		//      System.out.println(">>start");

    if(thread== null)
      {
       	thread = new Thread(this);
	
				//				System.out.println(">>call start");
        thread.start();
      }
}
  
  //----------------------------
  public void stop() {
		//    System.out.println(">>stop");

		thread = null;
  }
  
  //----------------------------
  public void run() {
		//    System.out.println(">>run");
    
    while (thread != null) {
      try {Thread.sleep( 50*c_val_timer);} catch (InterruptedException e){}
			//      paint
      repaint();
    }

    thread = null;
  }

}
//***********************************

