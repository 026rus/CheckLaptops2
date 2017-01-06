import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DB
{
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	PreparedStatement ps = null;
	private String urlDB				= "jdbc:mysql://" + Setings.getServerIP() + ":"+DBConst.PORT+"/" + DBConst.DATABASE_NAME;
	private String urlkioskDB			= "jdbc:mysql://" + Setings.getServerIP() + ":"+DBConst.PORT+"/" + DBConst.DATABASE_NAME_KIOSK;
	private String urlDBhttp			= "http://"+ Setings.getServerIP() + "/" + DBConst.DATABASE_NAME;
	private String urlPeopelfolder		= "/"+ DBConst.PEOPLE_FOLDER +"/";
	private String dbUserName			= DBConst.DB_USER_NAME;
	private String dbUserPassword		= DBConst.DB_USER_PASS;
	private String correntuser			= "";
	
	public void connectkioskDB() throws SQLException
	{
		correntuser	= System.getProperty("user.name");
		con = DriverManager.getConnection( urlkioskDB, dbUserName, dbUserPassword  );
		st = con.createStatement();
	}

	public void connectDB() throws SQLException
	{
		correntuser	= System.getProperty("user.name");
		con = DriverManager.getConnection(	urlDB, dbUserName, dbUserPassword  );
		st = con.createStatement();
	}
	public void disconnectDB () throws SQLException
	{
            if (rs != null){	rs.close();		}
            if (st != null){	st.close();		}
            if (con != null){	con.close();	}
	}
/* -------------------------------------------------------------------------------------*/	
	public String[] getEquipment(String equipmentNumber) throws SQLException
	{
		connectDB();
		String coloms = DBConst.LAPTOP_ID + ", "
						+ DBConst.LAPTOP_TAG + ", "
						+ DBConst.EMPLOYEE_F_NAME + ", "
						+ DBConst.EMPLOYEE_L_NAME + ", "
						+ DBConst.LAPTOP_NOTES + ", "
						+ DBConst.EMPLOYEE_PHOTO + ", "
						+ DBConst.EMPLOYEE_ID ;
		
		List<String> 	Equipment  = new ArrayList<String>();
		ps = con.prepareStatement("SELECT "+coloms+" FROM "+ DBConst.TABLE_LAPTOP
								 +" JOIN " + DBConst.TABLE_EMPLOYEE + " ON "
								 +" " + DBConst.LAPTOP_EMPLOYEE+"="+ DBConst.EMPLOYEE_ID
								 +" WHERE "+DBConst.LAPTOP_TAG+"=?");
		ps.setString(1, equipmentNumber);
		
		System.out.println("MYSQL is: " + ps.toString());
		rs = ps.executeQuery();
		
		while (rs.next())
		{
//			Equipment.add(rs.getString(DBConst.LAPTOP_ID));
			Equipment.add(rs.getString(DBConst.EMPLOYEE_F_NAME));
			Equipment.add(rs.getString(DBConst.EMPLOYEE_L_NAME));
			Equipment.add(rs.getString(DBConst.LAPTOP_TAG));
			Equipment.add(rs.getString(DBConst.LAPTOP_NOTES));
			Equipment.add(rs.getString(DBConst.EMPLOYEE_ID));
			Equipment.add(rs.getString(DBConst.EMPLOYEE_PHOTO));
		}
		
		disconnectDB ();
		
		System.out.println("Gote " + Equipment.size() + " number of things!");
		
		String[] str = null;
		if (Equipment.size() <= 0) 
			str = new String [] {"Error"};
		else if (Equipment != null) 
		{
			System.out.println("Inside of bildin Array");
			str = new String[Equipment.size()];

			Equipment.toArray(str);
			System.out.println("in Size : " + Equipment.size());
			System.out.println("in Array : " + str[str.length-1]);

			if ( !str[str.length-1].contains(urlDBhttp) )
				str[str.length-1] = urlDBhttp + "/"+DBConst.PEOPLE_FOLDER+"/" + str[str.length-1];
		}
		return str;
	}
/* -------------------------------------------------------------------------------------*/	
	public Object[][] getLaptopTable() throws SQLException
	{
		connectDB();
		ps = con.prepareStatement("SELECT * FROM " + DBConst.TABLE_LAPTOP);
		rs = ps.executeQuery();
		
		int size = 0;
		int numcol = 4;
		try
		{
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		}
		catch(Exception ex)
		{
		}
		
		System.out.println("Size of thabel is: " + size);
		Object[][] temp = new Object[size][numcol];
		int i=0;
		while (rs.next())
		{
			for (int c=1; c<=numcol; c++)
				temp[i][c-1] = rs.getString(c);
			i++;		
		}
		System.out.println("Get thrue # = " + i);
		disconnectDB();
		return temp;
	}
	public String[] getLaptopColomNames()
	{
		return new String[] {"ID", "TAG", "Employee", "Notes"};
	}
/* -------------------------------------------------------------------------------------*/	
	public Object[][] getEmployeeTable() throws SQLException
	{
		connectDB();
		ps = con.prepareStatement("SELECT * FROM " + DBConst.TABLE_EMPLOYEE + " ORDER BY "+ DBConst.EMPLOYEE_F_NAME
									+ ", " + DBConst.EMPLOYEE_L_NAME);
		rs = ps.executeQuery();
		
		int size = 0;
		int numcol = 6;
		try
		{
		    rs.last();
		    size = rs.getRow();
		    rs.beforeFirst();
		}
		catch(Exception ex)
		{
		}
		
		System.out.println("Size of thabel is: " + size);
		Object[][] temp = new Object[size][numcol];
		int i=0;
		while (rs.next())
		{
			for (int c=1; c<=numcol; c++)
				temp[i][c-1] = rs.getString(c);
			i++;		
		}
		System.out.println("Get thrue # = " + i);
		disconnectDB();
		return temp;
	}
/* -------------------------------------------------------------------------------------*/	
	public String[] getEmployeeColomNames()
	{
		return new String[] {"ID", "First Name", "Middle Name", "Last Name", "Email", "Photo"};
	}
/* -------------------------------------------------------------------------------------*/
	public boolean updatetLaptop(Laptop nlaptop) throws SQLException
	{
		String 	Tag			= nlaptop.getTag(),
				Notes 		= nlaptop.getNotes();
		int		Employee 	= nlaptop.getEmployeeID();
		
		String query = "UPDATE " + 	DBConst.TABLE_LAPTOP 
						+ " SET "+	DBConst.LAPTOP_EMPLOYEE+"=?,"
						+ " "+		DBConst.LAPTOP_NOTES+"=? "
						+ " WHERE "+DBConst.LAPTOP_TAG+"=?";
		
		ps = con.prepareStatement(query);
		ps.setInt(1, Employee);
		ps.setString(2, Notes);
		ps.setString(3, Tag);
		
		System.out.println("MYSQL sint: " + ps.toString());
		ps.execute();
		
		ps = con.prepareStatement("INSERT INTO "+ DBConst.TABLE_UPDATES + " VALUES(null,  ?, NOW() )");
		ps.setString(1, correntuser);
		ps.execute();
		
		return true;
	}

/* --------------------------------------------------------------------------------------------------------*
	public Laptop getLaptopByID(int ID) throws SQLException
	{
		String 	Tag = null,
				Name = null,
				FirstName = null,
				LastName = null,
				Notes = null,
				Photo = null;
		
		ps = con.prepareStatement("SELECT * FROM " + DBConst.TABLE_LAPTOP + " WHERE id=?");
		ps.setInt(1, ID);
		
		rs = ps.executeQuery();
		
		
		while (rs.next())
		{
			Tag 		= rs.getString("tag");
			Name 		= rs.getString("name");
			FirstName 	= rs.getString("firstname");
			LastName 	= rs.getString("lastname");
			Notes 		= rs.getString("notes");
			Photo 		= rs.getString("photo");
		}
				
		return new Laptop(ID, Tag, Name, FirstName, LastName, Notes, Photo );
	}
/* --------------------------------------------------------------------------------------------------------*/	
	
	public boolean addRecord(String tag, int employee, boolean in) throws SQLException
	{
		boolean retval = false;
		connectDB();
		if (in)
			ps = con.prepareStatement("INSERT INTO " + DBConst.TABLE_RECOREDS + " VALUES(NULL, ?, ?, NOW(), NULL)");
		else
			ps = con.prepareStatement("INSERT INTO " + DBConst.TABLE_RECOREDS + " VALUES(NULL, ?, ?, NULL, NOW() )");

		ps.setString(1, tag);
		ps.setInt(2, employee);
		retval = ps.execute();
		disconnectDB ();
		
		return retval;
	}
	public boolean isCheckIn(String tag) throws SQLException
	{
		connectDB();
		boolean retval = false;
		ps = con.prepareStatement("SELECT " + DBConst.LAPTOPRECORDS_DATEIN 
								+ " FROM " + DBConst.TABLE_RECOREDS 
								+ " WHERE " + DBConst.LAPTOPRECORDS_TAG + " = ? "
								+ " ORDER BY id DESC LIMIT 1");
		ps.setString(1, tag);
		Setings.print(ps.toString(), 0);
		rs = ps.executeQuery();
		while (rs.next())
			if(rs.getString("datein") == null)
				retval = false;
			else
				retval = true;

		disconnectDB ();
		return retval;
	}
	
	
	
	public void addExitRecord(String tag) throws SQLException
	{
		connectDB();
		ps = con.prepareStatement("UPDATE " + DBConst.TABLE_RECOREDS + " SET dateout = NOW() "
				+ "WHERE dateout is NULL AND teg=? ORDER BY datein DESC LIMIT 1");
		ps.setString(1, tag);
		ps.execute();
		disconnectDB ();
	}
	
	public boolean haveTempBadge(String fname, String lname) throws SQLException
	{
		List<String> 	employee  = new ArrayList<String>();
		connectkioskDB();
		ps = con.prepareStatement("select * from records "
				+ "WHERE true "
//				+ "DATE_SUB(CURDATE(), INTERVAL 5 DAY) <= datein "
				+ "AND tempbadge IS NOT NULL AND dateout IS NULL "
				+ "AND firstname=?"
				+ "AND lastname=?;");
		
		ps.setString(1, fname);
		ps.setString(2, lname);
		rs = ps.executeQuery();
		
		while (rs.next())
		{
			employee.add(rs.getString("id"));
			employee.add(rs.getString("firstname"));
			employee.add(rs.getString("lastname"));
			employee.add(rs.getString("company"));
			employee.add(rs.getString("country"));
			employee.add(rs.getString("reason"));
			employee.add(rs.getString("sponsor"));
			employee.add(rs.getString("datein"));
			employee.add(rs.getString("tempbadge"));
		}
		
		disconnectDB ();
		
		if (employee != null)
		{
			for (int i=0; i<employee.size(); ++i)
				System.out.println("\t=>\t" + employee.get(i));
		}
		
		if (employee.size() > 0)
			return true;
		else 
			return false;		
	}
	/*  */ 
	public String getUrlDBhttp()
	{
		return urlDBhttp;
	}
	public void setUrlDBhttp(String urlin)
	{
		urlDBhttp = urlin;
	}
	public String getUrlPeopelfolder()
	{
		return urlPeopelfolder;
	}
	public void setUrlPeopelfolder(String urlPeopelfolder)
	{
		this.urlPeopelfolder = urlPeopelfolder;
	}
	
}
