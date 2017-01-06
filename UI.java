import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

///////////////////////////////////////////////////////////////////////////////////////////////////
class MyJPanel extends JPanel
{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private Image img;
	
	MyJPanel (String strimg)
	{
		img = new ImageIcon(strimg).getImage();
	}
	
	MyJPanel ()
	{
		img = new ImageIcon(Setings.getDefoultePhoto()).getImage();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, this);
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////
public class UI extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame that = this;	
	private JPanel contentPane = new MyJPanel(Setings.getBackgroundImg());
	private JTextField	barcode = new JTextField();
	private JButton btnOk = new JButton("OK");
	private JButton btnCansel = new JButton("Reset");
	JButton btnAdd = new JButton("ADD");
	private JLabel lblScanOrEnter = new JLabel("Scan or enter the barcod of the laptop");
	private JLabel lblStatusBar = new JLabel("Ready!");
	private JProgressBar progressBar = new JProgressBar();
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	
	private String font_name = Setings.getFontName();
	private Font myfont = new Font(font_name, Font.PLAIN, 14);
	private Font in_out_font = new Font(font_name, Font.PLAIN, 18);
	private Font font_status = new Font(font_name, Font.PLAIN, 12);
	private Font font_output1 = new Font(font_name, Font.BOLD, 24);
	private Font font_output2 = new Font(font_name, Font.PLAIN, 24);
	private Font font_menu 	  = new Font(font_name, Font.PLAIN, 12);
	private final String title = "Equipment Logging";
	private okPress ok = new okPress();
	private resset ressetbtn = new resset();
	private KeyChek keychek = new KeyChek();
	private BarckodeFocuseListener barcodeListener = new BarckodeFocuseListener();
	private RadioListener rdbtnlistener = new RadioListener();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnOut = new JRadioButton("Out", false);
	private JRadioButton rdbtnIn = new JRadioButton("In", true);
	
	
	/* ---------------------------*/

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new UI();
				} catch (Exception e)
	       		{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public UI()
	{
		if(Setings.isRunning()) System.exit(0);
		Setings.init();
		makeMenuBar();
		ImageIcon img = new ImageIcon(Setings.getMainIcon());
		setIconImage(img.getImage());		
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 440);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		decore();
		setMainWindo();
		that.setVisible(true);
		that.setTitle(title);
		barcode.requestFocus();
	}
	
	private String[] getemployee(String str)
	{
		DB d = new DB();
		String[] a=null;
		try
		{
			a = d.getEquipment(str);
			if ( (a.length >= 2) && d.haveTempBadge(a[0], a[1]))
			{
				JOptionPane.showMessageDialog(this,
						Setings.getDoNotForgetMessage(),
						"Error!!!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (SQLException e1)
		{
			JOptionPane.showMessageDialog(this,
				e1.getMessage(),
				"Error!!!",
				JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		
		return a;
	}
	
	private void makeMenuBar()
	{
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		menu.getAccessibleContext().setAccessibleDescription("The menu for help and about items");
		menuBar.add(menu);
		menuItem = new JMenuItem("Setings");
		menuItem.setFont(font_menu);
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				that.setAlwaysOnTop(false);
				JDialog dialog = new SetingsUI();
				dialog.setModal(true);
			    dialog.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
				dialog.setVisible(true);
				that.setAlwaysOnTop(true);
			}
		});
		menu.add(menuItem);
		menuItem = new JMenuItem("About");
		menuItem.setFont(font_menu);
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(that,
					      Setings.getAboutMessage());
			}
		});
		menu.add(menuItem);
		if (that != null) that.setJMenuBar(menuBar);
	}
	
	private void checkLaptop(String inLaptop)
	{
		if (!inLaptop.isEmpty())
		{
			
			String[] employee = getemployee(inLaptop);
			if (employee.length < 3)
			{
				System.out.println("Not found !!!");
				int addYesNo = JOptionPane.showConfirmDialog(
					    this,
					    Setings.getEnterNewEquipmentMessage(),
					    "Add LapTop",
					    JOptionPane.YES_NO_OPTION);
				if (addYesNo == JOptionPane.YES_OPTION);
				else 
					System.out.println("Not wont to add LapTop!!!");
			}
			else 
			{
				for (int i=0; i< employee.length; i++)
					System.out.println("employee[" + i+ "] = "+ employee[i]);
				
				erase();
				setMainWindo();

				try
				{
					DB db = new DB();
					boolean isin = db.isCheckIn(employee[DBConst.TAG]);

						if (isin ^ rdbtnIn.isSelected())
						{
							int empID = Integer.parseInt(employee[DBConst.EMP_ID]);
							SendDataToDB sdtdb = new SendDataToDB(employee[DBConst.TAG],
																  empID,
																  rdbtnIn.isSelected() );
							sdtdb.run();
							empOutput(employee);
						}
						else
						{
							String str = null;
							if (rdbtnIn.isSelected())
								str = Setings.getLaptopInMessage();
							else 
								str = Setings.getLaptopOutMessage();
							
							int addYesNo = JOptionPane.showConfirmDialog(
					    			this,
					    			str,
					    			"Add LapTop",
					    			JOptionPane.YES_NO_OPTION);
							if (addYesNo == JOptionPane.YES_OPTION)
							{
								int empID = Integer.parseInt(employee[DBConst.EMP_ID]);
								SendDataToDB sdtdb = new SendDataToDB(employee[DBConst.TAG],
																	  empID,
																	  rdbtnIn.isSelected() );
								sdtdb.run();
								empOutput(employee);
							}
							else
							{
								erase();
								setMainWindo();
							}
						}
				}
				catch (Exception e)
				{
					
					JOptionPane.showMessageDialog(this,
						e.getMessage(),
						"Error!!!",
						JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

						
			}
		}
	}
	
	private void empOutput(String[] strin)
	{
		System.out.println(" Emploee 3 = " + strin[3]);
		if (strin.length > 7 )
		{
			JOptionPane.showMessageDialog(this,
				"We have a problem !!!",
				"Error!!!",
				JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<JLabel> output = new ArrayList<JLabel>();
		FontMetrics metrics = getFontMetrics(font_output1);
		int 	X = 10,
				Y = 180,
				H = metrics.getHeight()+5;
		Rectangle r = getBounds();
		int 	Height = r.height;
		String debugOut = "";
		for (int i=0; i<strin.length-1; i++)
		{
			if (i != DBConst.EMP_ID)
			{
				int Width = metrics.stringWidth(strin[i])+10;
				if (Width > 265)
				{
					Width = 265;
					Height += 30;
				}
				JLabel newLabel = new JLabel(strin[i]);
				// Set font for First and Last name
				if (i<2) newLabel.setFont(font_output1);
				else newLabel.setFont(font_output2);
				int xmid = (Height/4) - Width;
				int xnext = (Height/4);
				if (xmid < 0)
					{
						xmid = 5;
						xnext = xmid +  Width;
					}
				// Ferst name
				if (i==DBConst.F_NAME)
						newLabel.setBounds( xmid , Y, Width+50, H);
				// Last name
				else if (i==DBConst.L_NAME)
						newLabel.setBounds(xnext, Y, Width, H);
				// Rest staff
				else
						newLabel.setBounds(X, Y+=H, Width, H);
				output.add(newLabel);
			}
		}
		for (int i=0; i<strin.length-1; i++)
			if (i!=DBConst.EMP_ID)contentPane.add( output.get(i) );

		JLabel inoutlabel;
		if (rdbtnIn.isSelected()) inoutlabel = new JLabel("Checked In!");
		else  inoutlabel = new JLabel("Checked Out!");
		inoutlabel.setBounds(270, 50, 200, 200);
		inoutlabel.setFont(font_output1);
		contentPane.add(inoutlabel);
	
		URL url;
		try
		{
			url = new URL(strin[strin.length-1]);
			Image img = ImageIO.read(url);
			Image newimg = img.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
			ImageIcon image = new ImageIcon(newimg);
			JLabel imageLabel = new JLabel(image);
			imageLabel.setBounds(270, 170, 200, 200);
			imageLabel.setVisible(true);
			contentPane.add(imageLabel);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this,
				e.getMessage(),
				"Error!!!",
				JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		contentPane.repaint();
		lblStatusBar.setText(debugOut);
	}

	private void decore()
	{
		lblScanOrEnter.setFont(myfont);
		barcode.setFont(myfont);
		barcode.addFocusListener(barcodeListener);
		btnOk.setFont(myfont);
		btnCansel.setFont(myfont);
		lblStatusBar.setFont(font_status);
		btnOk.addActionListener(ok);
		btnCansel.addActionListener(ressetbtn);
		barcode.addKeyListener(keychek);
		rdbtnOut.setFont(in_out_font);
		rdbtnIn.setFont(in_out_font);
		rdbtnIn.addActionListener(rdbtnlistener);
		rdbtnOut.addActionListener(rdbtnlistener);
		rdbtnOut.setOpaque(false);
		rdbtnIn.setOpaque(false);
	}
	
	private void setMainWindo()
	{
		int	X=10,
			Y=30,
			H=23,
			rW=55;
		Rectangle r = getBounds();
		int 	Height = r.height,
			Width  = r.width;
		lblScanOrEnter.setBounds(X, Y, Width-(X*2), H);
		barcode.setBounds(X, Y+=(H*2), Width-(X*2), H);
		rdbtnIn.setBounds(10, Y+=(H), rW, H);
		rdbtnOut.setBounds(67, Y, rW, H);
		btnOk.setBounds(104, Y+=(H*2), 89, H);
		btnCansel.setBounds(Width - 105, 5 , 89, H);

		lblStatusBar.setBounds(X, (int)(Height- (H*2.5)), Width-(X*2), H);

		buttonGroup.add(rdbtnIn);
		buttonGroup.add(rdbtnOut);
		contentPane.add(lblScanOrEnter);
		contentPane.add(barcode);
		contentPane.add(btnCansel);
		contentPane.add(lblStatusBar);
		contentPane.add(rdbtnIn);
		contentPane.add(rdbtnOut);
		contentPane.repaint();
		barcode.requestFocus();
	}
	
	public void setTestWindo()
	{
		btnOk.addActionListener(new okPress());
		btnOk.setFont(myfont);
		btnOk.setBounds(104, 119, 89, 23);
		contentPane.add(btnOk);
		btnCansel.addActionListener(new resset());
		btnCansel.setFont(myfont);
		btnCansel.setBounds(297, 119, 89, 23);
		contentPane.add(btnCansel);
		progressBar.setFont(myfont);
		progressBar.setBounds(8, 338, 484, 14);
		contentPane.add(progressBar);
		lblStatusBar.setFont(myfont);
		lblStatusBar.setBounds(8, 387, 357, 14);
		lblStatusBar.setText("Test Windo!");
		contentPane.add(lblStatusBar);
		contentPane.repaint();
	}
	private void erase()
	{
		if ( barcode != null ) barcode.setText("");
		contentPane.removeAll();
		contentPane.repaint();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	/* Actions evens  */
	class BarckodeFocuseListener implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e)
		{
			barcode.setBackground(Color.WHITE);
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			barcode.setBackground(Color.RED);
		}
		
	}
	class okPress implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			
			checkLaptop(barcode.getText());
		}
	}
	class resset implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			erase();
			setMainWindo();
		}
	}
	
	class KeyChek implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent arg0)
		{
			String str="";
			if (arg0.getKeyCode() == 10)
			{
				
				if (arg0.getComponent() instanceof JTextField)
				{
					str = ((JTextField)arg0.getSource()).getText();
					checkLaptop(str);
					((JTextField)arg0.getSource()).setText("");
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{		}
	}
	class RadioListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			barcode.requestFocus();
		}
		
	}
}
