import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;


@SuppressWarnings("serial")
public class Wcam extends JFrame
{
	private BufferedImage theIMG=null;
    private Rectangle cropBounds=null;
    
    private BufferedImage croptImege=null;
    public boolean ready = false;
    
    public BufferedImage getCropetImage()
    {
    	return croptImege;
    }

	private class SnapMeAction extends AbstractAction
	{

		public SnapMeAction()
		{
			super("Snapshot");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				for (int i = 0; i < webcams.size(); i++)
				{
					Webcam webcam = webcams.get(i);
					theIMG = webcam.getImage();
					
					/*
					File file = new File(String.format("Temp_%d.jpg", i));
					ImageIO.write(img, "JPG", file);
					System.out.format("Image for %s saved in %s \n", webcam.getName(), file);
					*/
					System.out.format("Image for %s saved\n", webcam.getName());
					
				}
				for (WebcamPanel panel : panels)
				{
					panel.stop();
				}
				
				editimg();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
	}

	private class StartAction extends AbstractAction implements Runnable
	{

		public StartAction()
		{
			super("Start");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{

			btStart.setEnabled(false);
			btSnapMe.setEnabled(true);

			// remember to start panel asynchronously - otherwise GUI will be
			// blocked while OS is opening webcam HW (will have to wait for
			// webcam to be ready) and this causes GUI to hang, stop responding
			// and repainting

			executor.execute(this);
		}

		@Override
		public void run()
		{

			btStop.setEnabled(true);

			for (WebcamPanel panel : panels)
			{
				panel.start();
			}
		}
	}

	private class StopAction extends AbstractAction
	{

		public StopAction()
		{
			super("Stop");
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{

			btStart.setEnabled(true);
			btSnapMe.setEnabled(false);
			btStop.setEnabled(false);

			for (WebcamPanel panel : panels)
			{
				panel.stop();
			}
		}
	}

	private Executor executor = Executors.newSingleThreadExecutor();

	// private Dimension size = WebcamResolution.QQVGA.getSize();
	private Dimension size = WebcamResolution.HD720.getSize();
	
	//@formatter:off
			Dimension[] nonStandardResolutions = new Dimension[]{
				WebcamResolution.PAL.getSize(),
				WebcamResolution.HD720.getSize(),
				// new Dimension(1024, 1024),
				// new Dimension(1024, 1024),
			};

	private List<Webcam> webcams = Webcam.getWebcams();
	private List<WebcamPanel> panels = new ArrayList<WebcamPanel>();

	private JButton btSnapMe = new JButton(new SnapMeAction());
	private JButton btStart = new JButton(new StartAction());
	private JButton btStop = new JButton(new StopAction());

	public Wcam()
	{
		// super("Test Snap Different Size");
		
	}
	
		
	public void showWebCam() throws InterruptedException
	{
		ready = false;

		for (Webcam webcam : webcams)
		{
			webcam.setCustomViewSizes(nonStandardResolutions);
			//webcam.setViewSize(size);
			//webcam.setCustomViewSizes(nonStandardResolutions);
			webcam.setViewSize(WebcamResolution.HD720.getSize());
			
			WebcamPanel panel = new WebcamPanel(webcam, size, false);
			panel.setFPSDisplayed(true);
			panel.setFillArea(true);
			panels.add(panel);
		}

		// start application with disable snapshot button - we enable it when
		// webcam is started

		btSnapMe.setEnabled(true);
		btStop.setEnabled(false);

		setLayout(new FlowLayout());

		for (WebcamPanel panel : panels)
		{
			add(panel);
		}

		add(btSnapMe);
		// add(btStart);
		//add(btStop);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Thread t = new Thread(new StartAction());
		t.start();
		
		
	}

	private void clearwindo()
	{
		this.getContentPane().removeAll();
		this.getContentPane().repaint();
	}
	
	private void editimg()
	{
		
		clearwindo();
		CropPane cp = new CropPane();
		cp.setMyframe(this);
		
		
		//JLabel lb = new JLabel(new ImageIcon(theIMG) );
		//lb.setBounds(0, 0, theIMG.getWidth(), theIMG.getHeight());
		//cp.add(lb);
		this.getContentPane().remove(this.getContentPane());
		this.getContentPane().add(cp);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
		
	}
	
	
    public class CropPane extends JPanel
    {
    	JFrame myframe = null;
    	
    	public void setMyframe(JFrame fr)
    	{
    		myframe = fr;
    	}
    	private void closeme()
    	{
    		
    		
    		if (myframe != null) myframe.dispose();
    		else 
    			System.exit(0);
    	}

        public CropPane()
        {
            MouseHandler handler = new MouseHandler();

            addMouseListener((MouseListener) handler);
            addMouseMotionListener((MouseMotionListener) handler);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(theIMG.getWidth(), theIMG.getHeight());
        }

        protected Rectangle getCropBounds() {
            Rectangle actualBounds = null;
            if (cropBounds != null) {
                int x = cropBounds.x;
                int y = cropBounds.y;
                int width = cropBounds.width;
                int height = cropBounds.height;

                if (width < 0) {
                    x += width;
                    width -= (width * 2);
                }
                if (height < 0) {
                    y += height;
                    height -= (height * 2);
                }

                actualBounds = new Rectangle(x, y, width, height);
                // System.out.println(actualBounds);
            }
            return actualBounds;
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            if (theIMG != null) {
                int x = (getWidth() - theIMG.getWidth()) / 2;
                int y = (getHeight() - theIMG.getHeight()) / 2;
                g2d.drawImage(theIMG, x, y, this);
            }

            Rectangle drawCrop = getCropBounds();
            if (drawCrop != null) {
                Color color = UIManager.getColor("List.selectionBackground");
                g2d.setColor(color);
                Composite composite = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.fill(drawCrop);
                g2d.setComposite(composite);
                g2d.draw(drawCrop);
            }
        }

        public class MouseHandler extends MouseAdapter 
        {

            @Override
            public void mouseReleased(MouseEvent e) 
            {
            	BufferedImage bff = theIMG.getSubimage(cropBounds.x, cropBounds.y, cropBounds.width, cropBounds.height);
            	
            	JLabel picLabel = new JLabel(new ImageIcon(bff));
            	int YesNo = JOptionPane.showConfirmDialog(null, picLabel, "About", JOptionPane.YES_NO_OPTION);
            	
            	if (YesNo == JOptionPane.YES_OPTION)
            	{		
            		
            		croptImege = bff;
            		ready = true;
            		/*
	            	File outputfile = new File("image.jpg");
	            	try
	            	{
						ImageIO.write(bff, "jpg", outputfile);
					}
	            	catch (IOException e1)
	            	{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					*/
	            	cropBounds = null;
	                repaint();
	                
	                closeme();
            	}
            	else 
            	{
            		croptImege = null;
            		ready = true;
            	}
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cropBounds = new Rectangle();
                cropBounds.setLocation(e.getPoint());
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (cropBounds != null) {
                    Point p = e.getPoint();
                    int width = p.x - cropBounds.x;
                    int height = p.y - cropBounds.y;
                    cropBounds.setSize(width, height);
                    repaint();
                }
            }
        }
    }
	
	public static void main(String[] args)
	{
		Wcam cam = new Wcam();
		try {
			cam.showWebCam();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}