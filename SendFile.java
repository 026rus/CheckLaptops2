import java.io.File;
import java.io.FileInputStream;

import com.jcraft.jsch.*;



public class SendFile implements Runnable
{
	
	private String theFileName = null;
	private File theFile = null;
	
	public final String serverphotoloc = "http://" + Setings.getServerIP() + Setings.getServerimgfolder();
	
	public void setTheFielName(String filename)
	{
		theFileName = filename;
	}
	public void setTheFile(File f)
	{
		theFile = f;
	}
	/**
	*
	*/
	public SendFile(String filename, File f)
	{
		theFileName = filename;
		theFile = f;
	}
	 
	public SendFile()
	{
	}
	
	public String makephtonaem (String tag, String firstname, String lastname)
	{
		String retval = tag+"_"+firstname+"_"+lastname+".jpg";
		retval.replaceAll("\\s+", "");
		return retval; 
	}
	
	public void send(String fimename)
	{
		System.out.println("The file to send is: " + fimename);
		String SFTPHOST = Setings.getServerIP();
		int    SFTPPORT = 22;
		String SFTPUSER = Setings.getServeruser();
		String SFTPPASS = Setings.getServerpass();
		String SFTPWORKINGDIR = Setings.getServerimgfullfolder();
		 
		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;
		 
		try
		{
			JSch jsch = new JSch();
		    session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
		    session.setPassword(SFTPPASS);
		    java.util.Properties config = new java.util.Properties();
		    config.put("StrictHostKeyChecking", "no");
		    session.setConfig(config);
		    session.connect();
		    channel = session.openChannel("sftp");
		    channel.connect();
		    channelSftp = (ChannelSftp)channel;
		    channelSftp.cd(SFTPWORKINGDIR);
		    File f = new File(fimename);
		    if (!f.exists())
		        throw new RuntimeException("Error. Local file not found");
		    channelSftp.put(new FileInputStream(f), f.getName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			
			channelSftp.exit();
			session.disconnect();
		}
	
	}

	public void send(File fs)
	{
		String SFTPHOST = Setings.getServerIP();
		int    SFTPPORT = 22;
		String SFTPUSER = Setings.getServeruser();
		String SFTPPASS = Setings.getServerpass();
		String SFTPWORKINGDIR = Setings.getServerimgfullfolder();
		 
		Session     session     = null;
		Channel     channel     = null;
		ChannelSftp channelSftp = null;
		 
		try
		{
			JSch jsch = new JSch();
		    session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
		    session.setPassword(SFTPPASS);
		    java.util.Properties config = new java.util.Properties();
		    config.put("StrictHostKeyChecking", "no");
		    session.setConfig(config);
		    session.connect();
		    channel = session.openChannel("sftp");
		    channel.connect();
		    channelSftp = (ChannelSftp)channel;
		    channelSftp.cd(SFTPWORKINGDIR);
		    if (!fs.exists())
		        throw new RuntimeException("Error. Local file not found");
		    channelSftp.put(new FileInputStream(fs), fs.getName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			
			channelSftp.exit();
			session.disconnect();
		}
	
	}
	
	private void goforit()
	{
		if ( theFileName != null )
			send(theFileName);
		else if (theFile != null)
			send(theFile);
				
	}

	@Override
	public void run()
	{
		goforit();
		
	}
}
