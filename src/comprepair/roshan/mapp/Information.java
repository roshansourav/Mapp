package comprepair.roshan.mapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Information 
{
	public void infoframe()
	{
		JFrame infoFrame=new JFrame("Info");
		infoFrame.setSize(700,500);
		infoFrame.setUndecorated(true);
		infoFrame.setOpacity(0.9f);
		infoFrame.setLocationRelativeTo(null);
		
		infoFrame.setLayout(null);
		infoFrame.getContentPane().setBackground(new Color(176,224,230));
		
		ImageIcon iconMapp  = new ImageIcon("src/assets/PNGMapp.png");
		Image imageMapp = iconMapp.getImage();
	    iconMapp.setImage(imageMapp);
		infoFrame.setIconImage(imageMapp);
		infoFrame.setVisible(true);
		
		JPanel pnlInfoHdr=new JPanel();
		pnlInfoHdr.setBackground(Color.black);
		pnlInfoHdr.setLayout(null);
		pnlInfoHdr.setBounds(0, 0, 700,60);
		infoFrame.getContentPane().add(pnlInfoHdr);
		
		JLabel lblInfo=new JLabel("About MAPP",SwingConstants.CENTER);
		lblInfo.setBounds(10,5,650,40);
		lblInfo.setForeground(Color.WHITE);
		lblInfo.setFont(new Font("Times New Roman",Font.BOLD,24));
		pnlInfoHdr.add(lblInfo);
		
		JButton btnCloseInfo = new JButton();
		btnCloseInfo.setBounds(650, 10, 40, 40);
		btnCloseInfo.setBorder(javax.swing.BorderFactory.createLineBorder(pnlInfoHdr.getBackground()));
		btnCloseInfo.setToolTipText("Close");
		btnCloseInfo.setBackground(Color.black);
		
		ImageIcon iconClose  = new ImageIcon("src/assets/PNGClose.png");
		Image imageClose = iconClose.getImage();
	    imageClose = imageClose.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconClose.setImage(imageClose);
		btnCloseInfo.setIcon(iconClose);
		btnCloseInfo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				infoFrame.dispose();
		    }          
		});
		pnlInfoHdr.add(btnCloseInfo);
		
		ImageIcon iconLogo  = new ImageIcon("src/assets/GIF1.gif");
		Image imageLogo = iconLogo.getImage();
		imageLogo = imageLogo.getScaledInstance(380,150, Image.SCALE_DEFAULT);
		iconLogo.setImage(imageLogo);
		
		JLabel lblLogo=new JLabel(iconLogo);
		lblLogo.setBounds(20,80,380,150);
		infoFrame.getContentPane().add(lblLogo);
		
		JLabel lblInfo1=new JLabel();
		lblInfo1.setBounds(200,200,450,300);
		lblInfo1.setText("<html>MAPP v1.0 is designed & developed by :-<br><br>-> Roshan Kumar<br><br>-> Upendra Gupta<br><br> <br><br>for monitoring the system's resources.<br><br>Created on 20th april 2016 at KIHEAT (GGSIPU)</html>");
		lblInfo1.setFont(new Font("Times New Roman",Font.BOLD,14));
		infoFrame.getContentPane().add(lblInfo1);
		
		infoFrame.repaint();
	
		MoveMouseListener mmlInfromationObj = new MoveMouseListener(pnlInfoHdr);
	    pnlInfoHdr.addMouseListener(mmlInfromationObj);
	    pnlInfoHdr.addMouseMotionListener(mmlInfromationObj);	
	}//info frame method closed
}