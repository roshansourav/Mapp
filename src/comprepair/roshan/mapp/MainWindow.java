package comprepair.roshan.mapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.management.OperatingSystemMXBean;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MainWindow 
{
	JFrame mainf;
	JLabel lblDate, lblClock, lblOS, lblArch, lblUser, lblProcessor, lblRam, lblSwap;
	JLabel lblCurrentSong, lblAni;
	JPanel pnlNetwork, pnlDisk, pnlSystem, pnlHeader;
	JButton btnMPP, btnMNext;
	OperatingSystemMXBean osMBean =	(OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	Image imageAni, imageLogo;
	public Player player;
	int i = 0, playstatus = 0, filepathresponse, trackNo = 0;
	long totalS,usableS,ramS1,ramS2,swapS1,swapS2,pauseLoc,songLength;
	JLabel labels[] = new JLabel[10];
	FileInputStream fis1;
	File[] selectedFile;
	BufferedInputStream bis1;
	JFileChooser fcPath=new JFileChooser();
	String strPath="",strPathNew;
	FileNameExtensionFilter filter;
	ImageIcon iconPlay, iconPause, iconAni, iconLogo;
	
	public MainWindow()
	{		
		mainf=new JFrame("MAPP");
		mainf.setSize(350,723);
		mainf.setLayout(null);
		mainf.getContentPane().setBackground(Color.BLACK);
		mainf.setUndecorated(true);
		mainf.setOpacity(0.9f);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	    int x = (int) rect.getMaxX() - mainf.getWidth() -2;
	    int y = 2;
	    mainf.setLocation(x, y);
	    
	    ImageIcon iconMapp  = new ImageIcon("src/assets/PNGMapp.png");
	    Image imageMapp = iconMapp.getImage();
	    iconMapp.setImage(imageMapp);
	    mainf.setIconImage(imageMapp);
		
		pnlHeader=new JPanel();
		pnlHeader.setBackground(Color.black);
		pnlHeader.setBounds(0, 0, 350,70);
		pnlHeader.setLayout(null);
		mainf.add(pnlHeader);
		
		lblClock=new JLabel("Clock",SwingConstants.CENTER);
		lblClock.setFont(new Font("Times New Roman",Font.BOLD,20));
		lblClock.setBounds(10, 20, 300, 20);
		lblClock.setForeground(new Color(0,255,255));
		pnlHeader.add(lblClock);
		
		lblDate=new JLabel("Date",SwingConstants.CENTER);
		lblDate.setFont(new Font("Times New Roman",Font.BOLD,20));
		lblDate.setBounds(10, 40, 300, 20);
		lblDate.setForeground(new Color(0,255,255));
		pnlHeader.add(lblDate);
		
		JButton btnClose = new JButton();
		btnClose.setBounds(305, 10, 40, 40);
		btnClose.setFont(new Font("Times New Roman",Font.BOLD,9));
		btnClose.setBorder(javax.swing.BorderFactory.createLineBorder(pnlHeader.getBackground()));
		btnClose.setToolTipText("Close");
		btnClose.setBackground(Color.black);
		btnClose.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				ExitFrame objMainWindow = new ExitFrame();
				objMainWindow.exitframe();
		    }          

		});
		ImageIcon iconClose  = new ImageIcon("src/assets/PNGClose.png");
	    Image imageClose = iconClose.getImage();
	    imageClose = imageClose.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconClose.setImage(imageClose);
		btnClose.setIcon(iconClose);
		pnlHeader.add(btnClose);
		
		//calling features
		clock();
		network();
		disk();
		system();
		music();

		init();
		
		MoveMouseListener mmlmainWindowHeader = new MoveMouseListener(pnlHeader);
	    pnlHeader.addMouseListener(mmlmainWindowHeader);
	    pnlHeader.addMouseMotionListener(mmlmainWindowHeader);
	}//constructor closed here
	
	public void music()
	{
		lblCurrentSong=new JLabel("",SwingConstants.CENTER);
		lblCurrentSong.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblCurrentSong.setForeground(new Color(127,255,0));
		lblCurrentSong.setBounds(20,470,310,20);
		lblCurrentSong.setBackground(Color.red);
		mainf.getContentPane().add(lblCurrentSong);
		
		
	    
	    ImageIcon iconStop  = new ImageIcon("src/assets/PNGStop.png");
	    Image imageStop = iconStop.getImage();
	    imageStop = imageStop.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconStop.setImage(imageStop);
	    
	
	    JButton btnStop = new JButton(iconStop);
		btnStop.setBounds(20, 505, 40, 40);
		btnStop.setFont(new Font("Times New Roman",Font.BOLD,9));
		btnStop.setBackground(Color.BLACK);
		btnStop.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnStop.setToolTipText("Stop");
		btnStop.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				stop();
		    }          
		});
		
		ImageIcon iconPrev  = new ImageIcon("src/assets/PNGPrevious.png");
	    Image imagePrev = iconPrev.getImage();
	    imagePrev = imagePrev.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconPrev.setImage(imagePrev);
	    
		JButton btnMPrev = new JButton(iconPrev);
		btnMPrev.setBounds(120, 505, 40, 40);
		btnMPrev.setFont(new Font("Times New Roman",Font.BOLD,9));
		btnMPrev.setBackground(Color.BLACK);
		btnMPrev.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnMPrev.setToolTipText("Previous");
		btnMPrev.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				try
				{
				if(trackNo==0)
					trackNo=selectedFile.length;
				
				player.close();
				trackNo--;
				strPath=selectedFile[trackNo].getAbsolutePath();
				strPath=strPath.replace("\\", "\\\\");
				}
				catch(Exception e2){}
				if(filepathresponse==0 && playstatus!=0)
				plays(strPath);
				
		    }          

		}); //button previous-song ended here
		
		
		
		iconPlay  = new ImageIcon("src/assets/PNGPlay.png");
	    Image imagePlay = iconPlay.getImage();
	    imagePlay = imagePlay.getScaledInstance(59,59, Image.SCALE_SMOOTH);
	    iconPlay.setImage(imagePlay);
	    
	    
	    iconPause  = new ImageIcon("src/assets/PNGPause.png");
	    Image imagePause = iconPause.getImage();
	    imagePause = imagePause.getScaledInstance(59,59, Image.SCALE_SMOOTH);
	    iconPause.setImage(imagePause);
		
		btnMPP = new JButton(iconPlay);
		btnMPP.setBounds(195, 493, 60, 60);
		btnMPP.setFont(new Font("Times New Roman",Font.BOLD,9));
		btnMPP.setBackground(Color.BLACK);
		btnMPP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnMPP.setToolTipText("Select MP3 files");
		btnMPP.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				if(playstatus==0)
				{
					filter = new FileNameExtensionFilter("MP3 File","mp3");
					fcPath.setFileFilter(filter);
					fcPath.setMultiSelectionEnabled(true);
					fcPath.setCurrentDirectory(new File(System.getProperty("user.home")));
					filepathresponse = fcPath.showOpenDialog(mainf);
					if (filepathresponse == JFileChooser.APPROVE_OPTION) 
					{
				    // user selects a file
						selectedFile = fcPath.getSelectedFiles();
						strPath=selectedFile[trackNo].getAbsolutePath();
						strPath=strPath.replace("\\", "\\\\");
						plays(strPath);
					}	
				}
				
				else if(playstatus==1)
				{
					btnMPP.setIcon(iconPlay);
					lblAni.setIcon(iconLogo);
					playstatus=2;
					pause();
					
				}
				
				else
				{
					resume();
				}
		    }          

		});//button play-pause action ended here
		

		ImageIcon iconNext  = new ImageIcon("src/assets/PNGNext.png");
	    Image imageNext = iconNext.getImage();
	    imageNext = imageNext.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconNext.setImage(imageNext);
		
		btnMNext = new JButton(iconNext);
		btnMNext.setBounds(290, 505, 40, 40);
		btnMNext.setFont(new Font("Times New Roman",Font.BOLD,9));
		btnMNext.setBackground(Color.BLACK);
		btnMNext.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnMNext.setToolTipText("Next");
		btnMNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
		    {
				try
				{
				if(trackNo==selectedFile.length-1)
					trackNo=-1;
				
				player.close();
				trackNo++;
				strPath=selectedFile[trackNo].getAbsolutePath();
				strPath=strPath.replace("\\", "\\\\");
				}
				catch(Exception e2){}
				if(filepathresponse==0 && playstatus!=0)
				plays(strPath);
		    }          

		});
		
		
		iconAni  = new ImageIcon("src/assets/animated3.gif");
	    imageAni = iconAni.getImage();
	    imageAni = imageAni.getScaledInstance(270,100, Image.SCALE_DEFAULT);
	    iconAni.setImage(imageAni);
	    
	    iconLogo  = new ImageIcon("src/assets/GIF1.gif");
	    imageLogo = iconLogo.getImage();
	    imageLogo = imageLogo.getScaledInstance(270,100, Image.SCALE_DEFAULT);
	    iconLogo.setImage(imageLogo);
		
		lblAni=new JLabel(iconLogo);
		lblAni.setBounds(40,563,270,100);
		mainf.getContentPane().add(lblAni);
		
		ImageIcon iconDict  = new ImageIcon("src/assets/PNGDict.png");
	    Image imageDict = iconDict.getImage();
	    imageDict = imageDict.getScaledInstance(39,39, Image.SCALE_SMOOTH);
	    iconDict.setImage(imageDict);
		
		JButton btnDict=new JButton();
		btnDict.setBounds(20,673,40,40);
		btnDict.setToolTipText("MAPP English - Hindi Dictionary");
		btnDict.setFont(new Font("Times New Roman",Font.BOLD,18));
		btnDict.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnDict.setIcon(iconDict);
		btnDict.setBackground(Color.BLACK);
		
		btnDict.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
		    {
				Dictionary objMainDictionary = new Dictionary();
				objMainDictionary.display();
		    }          
		});
		
		ImageIcon iconInfo  = new ImageIcon("src/assets/PNGInfo.png");
	    Image imageInfo = iconInfo.getImage();
	    imageInfo = imageInfo.getScaledInstance(38,38, Image.SCALE_SMOOTH);
	    iconInfo.setImage(imageInfo);
	    
		JButton btnInfo=new JButton();
		btnInfo.setBounds(290,673,40,40);
		btnInfo.setToolTipText("Info");
		btnInfo.setFont(new Font("Times New Roman",Font.BOLD,18));
		btnInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		btnInfo.setIcon(iconInfo);
		btnInfo.setBackground(Color.BLACK);
		btnInfo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
		    {
				Information objMainInfo = new Information();
				objMainInfo.infoframe();
		    }          

		});
		mainf.add(btnInfo);
		mainf.add(btnDict);
		mainf.add(btnStop);
		mainf.add(btnMPrev);
		mainf.add(btnMPP);
		mainf.add(btnMNext);
		mainf.repaint();
	}//music()_method closed here
	
	public void resume()
	{
		try 
		{
			fis1=new FileInputStream(strPathNew);
			bis1=new BufferedInputStream(fis1);
			player=new Player(bis1);
			songLength=fis1.available();
			playstatus=1;
			fis1.skip(songLength-pauseLoc);
			btnMPP.setIcon(iconPause);
			lblAni.setIcon(iconAni);
			lblCurrentSong.setText(selectedFile[trackNo].getName());
			btnMPP.setToolTipText("Pause");
		}
		catch (FileNotFoundException  | JavaLayerException ex) 
		{
			playstatus=0;
			btnMPP.setIcon(iconPlay);
			lblAni.setIcon(iconLogo);
			lblCurrentSong.setText("");
			JOptionPane.showMessageDialog(null,ex);
			btnMPP.setToolTipText("Select MP3 files");
			stop();
		} 
		catch (IOException e) 
		{}
		new Thread()
		{
			public void run()
			{
				try
				{
					player.play();
					if(player.isComplete())
					{
						btnMNext.doClick();
					}
				}
				catch (JavaLayerException e) 
				{
					JOptionPane.showMessageDialog(null,e);
					strPath="";
					playstatus=0;
					lblCurrentSong.setText("");
					btnMPP.setIcon(iconPlay);
					lblAni.setIcon(iconLogo);
				}
			}
		}.start();
	}//resume method ended here
	
	public void pause()
	{
		if(player != null)
		{
			try 
			{
				pauseLoc=fis1.available();
				player.close();
				playstatus=2;
				btnMPP.setToolTipText("Resume");
			}catch (IOException e) {}
		}
	}//pause method closed here
	
	public void plays(String path)
	{
		try 
		{
			
			fis1=new FileInputStream(path);
			bis1=new BufferedInputStream(fis1);
			player=new Player(bis1);
			songLength=fis1.available();
			playstatus=1;
			btnMPP.setIcon(iconPause);
			lblAni.setIcon(iconAni);
			lblCurrentSong.setText(selectedFile[trackNo].getName());
			strPathNew=path+"";
			btnMPP.setToolTipText("Pause");
		}
		catch (FileNotFoundException  | JavaLayerException ex) 
		{
			playstatus=0;
			btnMPP.setIcon(iconPlay);
			lblAni.setIcon(iconLogo);
			lblCurrentSong.setText("");
			btnMPP.setToolTipText("Select MP3 files");
		} 
		catch (IOException e) 
		{}
		new Thread()
		{
			public void run()
			{
				try
				{
					player.play();
					
					if(player.isComplete())
					{
						btnMNext.doClick();
					}
				}
				catch (JavaLayerException e) 
				{
					strPath="";
					playstatus=0;
					lblCurrentSong.setText("");
					btnMPP.setIcon(iconPlay);
					lblAni.setIcon(iconLogo);
				}
			}
		}.start();
		
		
	}//plays method closed here	
	
	public void stop()
	{
		if(player != null)
		{
			playstatus=0;
			strPath="";
			lblCurrentSong.setText("");
			btnMPP.setIcon(iconPlay);
			lblAni.setIcon(iconLogo);
			player.close();
			trackNo=0;
			btnMPP.setToolTipText("Select MP3 files");
		}
	}//stop method closed here
	
	public void init()
	{
		pnlSystem.validate();
		pnlSystem.repaint();
		
		pnlNetwork.validate();
		pnlNetwork.repaint();
	}//init()_method closed here
	
	public void system()
	{
		pnlSystem=new JPanel(); //System panel
	    pnlSystem.setBackground(Color.black);
	    pnlSystem.setBounds(20, 280, 310,180);
		TitledBorder bdrSystem = new TitledBorder("SYSTEM");
		bdrSystem.setTitleColor(new Color(0,255,255));
		bdrSystem.setTitleJustification(TitledBorder.CENTER);
		bdrSystem.setTitlePosition(TitledBorder.TOP);
		pnlSystem.setBorder(bdrSystem);
		pnlSystem.setLayout(new GridLayout(0, 1));
		mainf.getContentPane().add(pnlSystem);
		
		lblOS=new JLabel();
		lblOS.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblOS.setForeground(new Color(0,255,255));
		String nameOS = "os.name";
		String architectureOS = "os.arch";
		String usern="user.name";
		lblArch=new JLabel();
		lblArch.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblArch.setForeground(new Color(0,255,255));
		String temp = String.format("Architecture :"+"%30s",System.getProperty(architectureOS));
		lblArch.setText(temp);
		temp = String.format("OS :"+"%46s",System.getProperty(nameOS));
		lblOS.setText(temp);
		lblUser=new JLabel();
		lblUser.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblUser.setForeground(new Color(0,255,255));
		temp = String.format("User :"+"%42s",System.getProperty(usern));
		lblUser.setText(temp);
		
		lblProcessor=new JLabel();
		lblProcessor.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblProcessor.setForeground(new Color(0,255,255));
		temp = String.format("No. of processors:"+"%19s",osMBean.getAvailableProcessors());
		lblProcessor.setText(temp);
		
		lblRam=new JLabel("getting info");
		lblRam.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblRam.setForeground(new Color(0,255,255));
		
		lblSwap=new JLabel("getting info");
		lblSwap.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblSwap.setForeground(new Color(0,255,255));
		
		pnlSystem.add(lblUser);
		pnlSystem.add(lblOS);
		pnlSystem.add(lblArch);
		pnlSystem.add(lblProcessor);
		pnlSystem.add(lblRam);
		pnlSystem.add(lblSwap);
		
		printSystemUsage();
	}//system()_method closed here
	
	public void printSystemUsage()
	{
		Thread printSystemUsage =new Thread()
		{
			public void run()
			{
				try 
				{
					for(;;)
					{	
						ramS1=osMBean.getTotalPhysicalMemorySize()-osMBean.getFreePhysicalMemorySize();
						ramS2=(100*ramS1)/osMBean.getTotalPhysicalMemorySize();
						String temp = String.format("Ram Usage :"+"%29s",ramS2);
						lblRam.setText(temp+" %");
		
						swapS1=osMBean.getTotalSwapSpaceSize()-osMBean.getFreeSwapSpaceSize();
						swapS2=(100*swapS1)/osMBean.getTotalSwapSpaceSize();
						temp = String.format("Swap Usage :"+"%27s",swapS2);
						lblSwap.setText(temp+" %");
						sleep(1000);
					}
				}catch (InterruptedException e){}
			}
		};
	printSystemUsage.start();	
	}//printSystemUsage ended
	
	public void disk()
	{
		pnlDisk=new JPanel(); //Disk panel
		pnlDisk.setBackground(Color.black);
		pnlDisk.setBounds(20, 160, 310,100);
		TitledBorder bdrDisk = new TitledBorder("DISKS");
		bdrDisk.setTitleColor(new Color(0,255,255));
		bdrDisk.setTitleJustification(TitledBorder.CENTER);
		bdrDisk.setTitlePosition(TitledBorder.TOP);
		pnlDisk.setBorder(bdrDisk);
		pnlDisk.setLayout(new GridLayout(0, 1));
		mainf.getContentPane().add(pnlDisk);
		  
		/* Get a list of all file system roots on this system */
		File[] roots = File.listRoots();
		// For each file system root, print some info 
		for (File root : roots) 
		{  
			SwingUtilities.invokeLater(new Runnable() 
			{
	        @Override
		        public void run() 
		        {
		        	totalS=root.getTotalSpace()/1073741824;  //changing Bytes into GigBytes #1073741824
		            usableS=root.getUsableSpace()/1073741824;
		            labels[i]=new JLabel();
		            String temp = String.format(""+ root.getAbsolutePath()+" "+usableS+" GB free of "+"%25s",totalS);
		            
		            labels[i].setText(temp+" GB");
		            labels[i].setForeground(new Color(0,255,255));
		            labels[i].setFont(new Font("Times New Roman",Font.BOLD,14));
		            pnlDisk.add(labels[i]);
		            
		            pnlDisk.validate();
		            pnlDisk.repaint();
		        }
			}); 
		    i++;
		}
	}//disk()_method closed here
	
	public void network()
	{
		pnlNetwork=new JPanel();  //network panel
		pnlNetwork.setBackground(Color.black);
		pnlNetwork.setBounds(20, 80, 310,60);
		pnlNetwork.setLayout(new GridLayout(0, 1));
		TitledBorder bdrNetwork = new TitledBorder("NETWORK");
		bdrNetwork.setTitleColor(new Color(0,255,255));
	    bdrNetwork.setTitleJustification(TitledBorder.CENTER);
	    bdrNetwork.setTitlePosition(TitledBorder.TOP);
		pnlNetwork.setBorder(bdrNetwork);
		mainf.getContentPane().add(pnlNetwork);
		JLabel lblIP=new JLabel();
		lblIP.setFont(new Font("Times New Roman",Font.BOLD,14));
		lblIP.setForeground(new Color(0,255,255));
		InetAddress ip;
		  try
		  {
			ip = InetAddress.getLocalHost();
			String temp = String.format(" IP Address " + "%33s", ip.getHostAddress());
			lblIP.setText(temp);
		  } 
		  catch (Exception e){}
		  pnlNetwork.add(lblIP);
	}//network()_method closed here
	
	public void clock()
	{
		Thread clock =new Thread()
				{
					public void run()
					{
						try 
						{
							for(;;)
							{
							Calendar cal =new GregorianCalendar();
							
							int day=cal.get(Calendar.DAY_OF_MONTH);
							int month=cal.get(Calendar.MONTH);
							int year=cal.get(Calendar.YEAR);
							int second=cal.get(Calendar.SECOND);
							int minute=cal.get(Calendar.MINUTE);
							int hour=cal.get(Calendar.HOUR);
							lblClock.setText(""+hour+" : "+minute+" : "+second);
							lblDate.setText(""+day+" / "+(month+1)+" / "+year);
							
							sleep(1000);
							}
						} 
							catch (InterruptedException e) 
						{}
					}
				};
			clock.start();							
	}//clock method closed

	public void display()
	{
		mainf.setVisible(true);
	}//display()_method closed here
}