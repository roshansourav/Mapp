package comprepair.roshan.mapp;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Intro 
{
	public void intro()
	{
		JFrame Fwelcome=new JFrame("MAPP");
		Fwelcome.setSize(340,160);
		Fwelcome.setUndecorated(true);
		Fwelcome.setLayout(null);
		Fwelcome.setLocationRelativeTo(null);
		Fwelcome.getContentPane().setBackground(Color.black);
		
		ImageIcon iconMapp  = new ImageIcon("src/assets/PNGMapp.png");
		Image imageMapp = iconMapp.getImage();
		iconMapp.setImage(imageMapp);
		Fwelcome.setIconImage(imageMapp);
		
		ImageIcon iconWelcome  = new ImageIcon("src/assets/GIF1.gif");
		Image imageWelcome = iconWelcome.getImage();
		imageWelcome = imageWelcome.getScaledInstance(340,160, Image.SCALE_DEFAULT);
		iconWelcome.setImage(imageWelcome);
		
		JLabel lblWelcome=new JLabel(iconWelcome);
		lblWelcome.setBounds(0,0,340,160);
		Fwelcome.getContentPane().add(lblWelcome);
		Fwelcome.setVisible(true);
		
		new Thread()
		{
			public void run()
			{
				MainWindow objIntro = new MainWindow();
				try
				{
					sleep(1500);
					Fwelcome.dispose();
				}
				catch (Exception e) 
				{}
				objIntro.display();
			}
			
			
		}.start();
	}
}