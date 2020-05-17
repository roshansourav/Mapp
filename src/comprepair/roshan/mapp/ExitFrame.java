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

public class ExitFrame
{
	public void exitframe()
	{
		JFrame exitFrame=new JFrame("Exit Mapp");
		exitFrame.setSize(300,150);
		exitFrame.setUndecorated(true);
		exitFrame.setOpacity(0.9f);
		exitFrame.setLocationRelativeTo(null);
		exitFrame.setLayout(null);
		exitFrame.getContentPane().setBackground(new Color(176,224,230));
		
		ImageIcon iconMapp  = new ImageIcon("src/assets/PNGMapp.png");
		Image imageMapp = iconMapp.getImage();
	    iconMapp.setImage(imageMapp);
	    exitFrame.setIconImage(imageMapp);
    	exitFrame.setVisible(true);
	    	
	    JPanel pnlexitHdr=new JPanel();
		pnlexitHdr.setBackground(Color.black);
		pnlexitHdr.setLayout(null);
		pnlexitHdr.setBounds(0, 0, 300,50);
	    
	    JButton btnCloseExit = new JButton();
		btnCloseExit.setBounds(255, 5, 40, 40);
		btnCloseExit.setBorder(javax.swing.BorderFactory.createLineBorder(pnlexitHdr.getBackground()));
		btnCloseExit.setToolTipText("Cancel");
		btnCloseExit.setBackground(Color.black);
		btnCloseExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				exitFrame.dispose();
		    }          

		});
		
		ImageIcon iconClose  = new ImageIcon("src/assets/PNGClose.png");
	    Image imageClose = iconClose.getImage();
	    imageClose = imageClose.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconClose.setImage(imageClose);
		btnCloseExit.setIcon(iconClose);
		
		JLabel lblConfirm=new JLabel("Confirm Exit",SwingConstants.CENTER);
		lblConfirm.setBounds(10,5,235,40);
		lblConfirm.setForeground(Color.WHITE);
		lblConfirm.setFont(new Font("Times New Roman",Font.BOLD,18));
		pnlexitHdr.add(lblConfirm);
		
		JButton btnOK=new JButton("OK");
		btnOK.setBounds(30,80,100,40);
		btnOK.setBackground(new Color(30,144,255));
		btnOK.setFont(new Font("Times New Roman",Font.BOLD,16));
		btnOK.setForeground(Color.BLACK);
		btnOK.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				System.exit(0);
		    }          
		});
		
		JButton btnCancel=new JButton("Cancel");
		btnCancel.setBounds(170,80,100,40);
		btnCancel.setBackground(new Color(30,144,255));
		btnCancel.setFont(new Font("Times New Roman",Font.BOLD,16));
		btnCancel.setForeground(Color.BLACK);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				exitFrame.dispose();
		    }          
		});
	    
		exitFrame.getContentPane().add(pnlexitHdr);
	    pnlexitHdr.add(btnCloseExit);
	    exitFrame.add(btnOK);
	    exitFrame.add(btnCancel);
	    exitFrame.repaint();
	    //btnOK.requestFocusInWindow();
	    
	    MoveMouseListener mmlExitFrameObj = new MoveMouseListener(pnlexitHdr);
	    pnlexitHdr.addMouseListener(mmlExitFrameObj);
	    pnlexitHdr.addMouseMotionListener(mmlExitFrameObj);
	}
}