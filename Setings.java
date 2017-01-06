import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Setings
{
	private Setings(){}

	private static String ServerIP = "15.47.243.46";
	private static String Serverpass = "hpinvent";
	private static String Serveruser = "hpadmin";
	private static String Serverimgfolder = "/kiosk/people/";
	private static String Serverimgfullfolder = "/var/www/kiosk/people/";
	private static String DefoultePhoto = "000012.png";
	private static String BackgroundImg = "background.png";
	private static String LaptopIconFile = "laptop_256.png";
	private static String MainIcon = "icon-secure.png";
	private static String FontName = "HP Simplified";
	private static String DoNotForgetMessage = "Do not forget return Temp Badge!";
	private static String EnterNewEquipmentMessage = "The equipment you entered is not exist in the database,\nDo you want to add in to the database now.\n";
	private static String ChooseEmployeeMessage = "Please choose employee to assign laptop to.";
	private static String LaptopExistMessage = "The laptop already exists just assign it tp employee.";
	private static String LaptopInMessage = "The laptop was checked In and not Out do you want to continue?";
	private static String LaptopOutMessage = "The laptop was checked Out and not In do you want to continue?";
	private static String DeleteErrorTabMessage = "Make sure you are on the laptop tab and you selecting right laptop.";
	private static String UpdateEmployeeErrorMessage = "Make sure to select employee.";
	private static String LaptopDeleteMessage = "Do you want to delete this laptop?";
	private static String AboutMessage = "01110011 01101111 01110010 01110010 01111001 00100000 01101110 01101111\n"
		    								+ "00100000 01101001 00100000 01100100 01101001 01100100 01101110 00100111\n"
										    + "01110100 00100000 01101000 01100001 01110110 01100101 00100000 01110100\n"
										    + "01101001 01101101 01100101 00100000 01110100 01101111 00100000 01110111\n"
										    + "01110010 01101001 01110100 01100101 00100000 01101000 01100101 01101100\n"
										    + "01110000 00101110 00100000 01001001 01110100 00100000 01101101 01100001\n"
										    + "01111001 00100000 01100010 01100101 00100000 01101000 01100101 01110010\n"
										    + "01100101 00100000 01101001 01101110 00100000 01101110 01100101 01111000\n"
										    + "01110100 00100000 01110110 01100101 01110010 01110011 01101001 01101111\n"
										    + "01101110 00001010\n\n"
										    + "01000111 01101111 01101111 01100100 00100000 01101100 01110101 01100011\n"
										    + "01101011 00100001";

	private static String EnterTheTagPromt = "Please enter the TAG: ";
	private static String EquipmentName = "Laptop";
	
	private static final String [] Opt = 	{
										"ServerIP"
									};
	private static final String theFileName = "checklaptops.rc";
	private static final String logfile = "checklaptops.log";
	
	public static final int LAPTOP_ID 			= 0;
	public static final int LAPTOP_TAG 			= 1;
	public static final int LAPTOP_EMPLOYEE_ID 	= 2;
	public static final int LAPTOP_NOTES 		= 3;

	public static final int EMPLOYEE_ID 			= 0;
	public static final int EMPLOYEE_FIRST_NAME 	= 1;
	public static final int EMPLOYEE_MIDDLE_NAME 	= 2;
	public static final int EMPLOYEE_LAST_NAME 		= 3;
	public static final int EMPLOYEE_EMAIL 			= 4;
	public static final int EMPLOYEE_PHOTO 			= 5;
	
	private static final int PORT = 9999;
	//private static ServerSocket socket;
	
	public static String getServerIP()						{ return ServerIP; }
	public static String getServerpass()					{ return Serverpass; }
	public static String getServeruser()					{ return Serveruser; }
	public static String getServerimgfolder()				{ return Serverimgfolder; }
	public static String getServerimgfullfolder() 			{ return Serverimgfullfolder; }
	public static String getDefoultePhoto() 				{ return DefoultePhoto; }
	public static String getLaptopIconFile() 				{ return LaptopIconFile; }
	public static String getBackgroundImg()					{ return BackgroundImg; }
	public static String getFontName()						{ return FontName; }
	public static String getMainIcon()						{ return MainIcon; }
	public static String getDoNotForgetMessage()			{ return DoNotForgetMessage; }
	public static String getAboutMessage()					{ return AboutMessage; }
	public static String getEnterTheTagPromt()				{ return EnterTheTagPromt; }
	public static String getEquipmentName()					{ return EquipmentName; }
	public static String getEnterNewEquipmentMessage()		{ return EnterNewEquipmentMessage; }
	public static String getChooseEmployeeMessage()			{ return ChooseEmployeeMessage; }
	public static String getLaptopExistMessage() 			{ return LaptopExistMessage; }
	public static String getLaptopInMessage() 				{ return LaptopInMessage; } 
	public static String getLaptopOutMessage()				{ return LaptopOutMessage; }
	public static String getLaptopDeleteMessage()			{ return LaptopDeleteMessage; }
	public static String getDeleteErrorTabMessage()			{ return DeleteErrorTabMessage; }
	public static String getUpdateEmployeeErrorMessage() 	{ return UpdateEmployeeErrorMessage; }

	public static void setServerIP(String serverIP) 		{ ServerIP = serverIP;	}

	private static boolean isFileExist(String strfile)
	{
		File file = new File(strfile);
		if (file.exists()) return true;
		else return false;
	}
	@SuppressWarnings("resource")
	public static boolean isRunning()
	{
		try
		{
			new ServerSocket(PORT, 0, InetAddress.getByAddress(new byte[]{127,0,0,1}));
		}
		catch(BindException e)
		{
			print("Already running.", 0);
			return true;
		}
		catch(Exception e)
		{
			print("Unexpected error: " + e.getMessage(), 0);
			return true;
		}
		return false;
	}
	
	private static void makefile(String strfile, String text)  
	{
        try
        {
        	File file = new File(strfile);
        	FileWriter fout = new FileWriter(file);
        	fout.write(text);
        	fout.close();
        } catch ( IOException e )
        {
           e.printStackTrace();
        }	
	}
	
	public static void print(String str, int x)
	{
		boolean infile 	 = true;
		boolean inscreen = true;
		
		String formattedDate = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		if (formattedDate.equals("")) formattedDate = sdf.format(date);
		
		String text = formattedDate + ":\t" + str + System.getProperty("line.separator");
		if (infile)
		{
		    try
		    {
		    	File file = new File(logfile);
		    	FileWriter fout = new FileWriter(file, true);
		    	fout.write(text);
		    	fout.close();
		    } catch ( IOException e )
		    {
		       e.printStackTrace();
		    }	
		}
		if (inscreen)
		{
			// System.out.println(text);
			System.out.println(str);
		}
	}
	public static void init()
	{
		String fileText = Opt[0]+"="+ getServerIP();
		if (!isFileExist(theFileName)) 
		{
			makefile(theFileName, fileText);
			return;
		}

		try
		{
			FileReader crfout = new FileReader(new File(theFileName));
			LineNumberReader crlNum = new LineNumberReader (crfout);
			String text = null;
			while ((text = crlNum.readLine()) != null)
			{
				if (!text.startsWith("#"))
				{

					if (text.toLowerCase().contains(Opt[0].toLowerCase()))
					{
						String [] Nst;
						if (text.contains(" = "))
								Nst = text.split("= ");
						else 	Nst = text.split("=");
						setServerIP(Nst[1]);
					}
				}	
			}
			crlNum.close();
			crfout.close();
			
		} catch (Exception exp)
		{
			System.out.println("IndexOutOfBoundsException: " + exp.getMessage());
		}
	}

	public static void save()
	{
		
		String fileText = Opt[0]+"="+ getServerIP();
		makefile(theFileName, fileText);
	}
}







