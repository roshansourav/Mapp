package comprepair.roshan.mapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;



public class Dictionary 
{
	JFrame JFDict;
	JPanel pnlDictHdr;
	JTextField searchField;
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> list=new JList<String>(listModel);
	
	Connection conn=null;
	Connection connection = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	ResultSet rs1;
	ResultSet rs2;
	ResultSet rs3;
	String srch="";
	String meaning;
	String str1,str2,str3;
	int eids,hids;
	
	public Dictionary()
	{
		init();
		 
		JFDict=new JFrame("MAPP Dictionary");
		JFDict.setSize(600, 400);
		JFDict.getContentPane().setBackground(Color.black);
		JFDict.setLayout(null);
		JFDict.setUndecorated(true);
		JFDict.setOpacity(0.9F);
		JFDict.setLocationByPlatform(true);
		
		ImageIcon iconMapp  = new ImageIcon("src/assets/PNGMapp.png");
		Image imageMapp = iconMapp.getImage();
		iconMapp.setImage(imageMapp);
		JFDict.setIconImage(imageMapp);
		    
		JFDict.setVisible(true);
		
		pnlDictHdr=new JPanel();
		pnlDictHdr.setBackground(Color.black);
		pnlDictHdr.setLayout(null);
		pnlDictHdr.setBounds(0, 0, 600,59);
		JFDict.add(pnlDictHdr);
		
		JPanel pnlList=new JPanel();
		pnlList.setBounds(20,120,418,260);
		pnlList.setBackground(Color.black);
		JFDict.getContentPane().add(pnlList);
		list.setSize(400,200);
		list.setSelectedIndex(0);
		list.setBackground(new Color(176,224,230));
		list.setFont(new Font("Areal",Font.BOLD,24));
		
		JScrollPane scrollPane= new JScrollPane();
		scrollPane.setViewportView(list);
		scrollPane.setPreferredSize(new Dimension(420, 260));
		pnlList.add(scrollPane);
		
		ImageIcon iconClose  = new ImageIcon("src/assets/PNGClose.png");
	    Image imageClose = iconClose.getImage();
	    imageClose = imageClose.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconClose.setImage(imageClose);
	    
	    JButton btnCloseDict = new JButton();
		btnCloseDict.setBounds(550, 10, 40, 40);
		btnCloseDict.setIcon(iconClose);
		btnCloseDict.setBorder(javax.swing.BorderFactory.createLineBorder(pnlDictHdr.getBackground()));
		btnCloseDict.setToolTipText("Close");
		btnCloseDict.setBackground(Color.black);
		btnCloseDict.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
					try 
					{
						conn.close();
					}
					catch (SQLException e1) {}	
					JFDict.dispose();
		    }
		});
		pnlDictHdr.add(btnCloseDict);
		
		JLabel lblTitle=new JLabel("- Dictionary -",SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman",Font.BOLD,24));
		lblTitle.setBounds(50, 10, 500, 40);
		lblTitle.setForeground(Color.WHITE);
		pnlDictHdr.add(lblTitle);
		
		searchField=new JTextField();
		searchField.setBounds(20,60,280,40);
		searchField.setFont(new Font("Times New Roman",Font.BOLD,22));
		searchField.setBackground(Color.white);
		Border border = BorderFactory.createLineBorder(new Color(0,250,154),3);
		searchField.setBorder(border);
		JFDict.getContentPane().add(searchField);
		
		JButton btnSearchDict = new JButton("Search");
		btnSearchDict.setBounds(298,60,140,40);
		btnSearchDict.setToolTipText("Search");
		btnSearchDict.setBackground(new Color(30,144,255));
		btnSearchDict.setBorder(border);
		btnSearchDict.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				srch=searchField.getText();
				try
				{
					listModel.clear();
				}
				catch(Exception ee){}
				try 
				{
					stmt1 = conn.createStatement();
					str1="select * from EnglishWordInfo where eword='"+srch+"';";
					rs1 = stmt1.executeQuery(str1);
					if (!rs1.isBeforeFirst() ) 
					{
						listModel.addElement("No result found. Check spelling");
						searchField.requestFocusInWindow();
					}
					else
					{
						while(rs1.next())
						{
							eids=rs1.getInt("eid");
						}	
					
						stmt2 = conn.createStatement();
						str2="select * from HindiEnglishPair  where eid='"+eids+"';";
						stmt3 = conn.createStatement();
						rs2=stmt2.executeQuery(str2);
					
						while(rs2.next())
						{
							hids=rs2.getInt("hid");
						
							str3="select * from HindiWordInfo  where hid='"+hids+"';";
							rs3=stmt3.executeQuery(str3);
							while(rs3.next())		
							{
								meaning=rs3.getString("hword");
								listModel.addElement(""+srch+" : "+meaning);
							}	
						}
						searchField.requestFocusInWindow();
					}
				}
				catch (SQLException e1) 
				{
					listModel.addElement("No result found. Check spelling");
					searchField.requestFocusInWindow();
				}
		    }
		
		});
				
		JFDict.getContentPane().add(btnSearchDict);
		
		searchField.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		    	btnSearchDict.doClick();
		    }
		});
		
		searchField.requestFocusInWindow();
		
		MoveMouseListener mmlDictionaryObj = new MoveMouseListener(pnlDictHdr);
	    pnlDictHdr.addMouseListener(mmlDictionaryObj);
	    pnlDictHdr.addMouseMotionListener(mmlDictionaryObj);
	}//constructor closed here
	
	public void init()
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
			conn=DriverManager.getConnection("jdbc:sqlite:/root/eclipse-workspace/MAPP/src/assets/dict.sqlite");
	    }   
		catch (ClassNotFoundException | SQLException e)
		{
			JOptionPane.showMessageDialog(null,"Database not found \n"+e);
			return;
		}
	}//init() method closed here
	
	public void display()
	{
		JFDict.setVisible(true);
	}//display()_method closed here
}
