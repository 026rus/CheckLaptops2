
public class DBConst
{
	private DBConst(){}
	
	public static final String DATABASE_NAME 			= "hpdb";
	public static final String DATABASE_NAME_KIOSK		= "kiosk";
	
	public static final String TABLE_LAPTOP 			= "inventory";
	public static final String TABLE_EMPLOYEE 			= "employee"; 
	public static final String TABLE_RECOREDS 			= "laptoprecords"; // not Working 
	public static final String TABLE_UPDATES			= "laptopupdates"; // not Working 
	
	public static final String LAPTOP_ID 				= TABLE_LAPTOP + ".inventory_id";
	public static final String LAPTOP_TAG 				= TABLE_LAPTOP + ".tag";
	public static final String LAPTOP_EMPLOYEE 			= TABLE_LAPTOP + ".employee";
	public static final String LAPTOP_NOTES 			= TABLE_LAPTOP + ".notes";

	public static final String EMPLOYEE_ID 				= TABLE_EMPLOYEE + ".id";
	public static final String EMPLOYEE_F_NAME 			= TABLE_EMPLOYEE + ".first_name";
	public static final String EMPLOYEE_L_NAME 			= TABLE_EMPLOYEE + ".last_name";
	public static final String EMPLOYEE_M_NAME 			= TABLE_EMPLOYEE + ".middle_name";
	public static final String EMPLOYEE_EMAIL 			= TABLE_EMPLOYEE + ".email";
	public static final String EMPLOYEE_PHOTO 			= TABLE_EMPLOYEE + ".photo";

	public static final String RECORDS_ID 				= "records.id";
	public static final String RECORDS_TAG 				= "records.tag";
	public static final String RECORDS_EMPLOYEE 		= "records.employee";
	public static final String RECORDS_DATEIN 			= "records.datein";
	public static final String RECORDS_DATEOUT 			= "records.dateout"; 

	public static final String DB_USER_NAME 			= "kioskuser";
	public static final String DB_USER_PASS 			= "kioskuser";

	public static final String LAPTOPRECORDS_ID 		= TABLE_RECOREDS + ".id";
	public static final String LAPTOPRECORDS_TAG		= TABLE_RECOREDS + ".tag";
	public static final String LAPTOPRECORDS_EMPLOYEE	= TABLE_RECOREDS + ".employee";
	public static final String LAPTOPRECORDS_DATEIN		= TABLE_RECOREDS + ".datein";
	public static final String LAPTOPRECORDS_DATEOUT	= TABLE_RECOREDS + ".dateout";

	public static final String PEOPLE_FOLDER			= "people";
	public static final String PORT						= "3306";
	
	public static final int F_NAME 	= 0;
	public static final int L_NAME 	= 1;
	public static final int TAG		= 2;
	public static final int COMENT 	= 3;
	public static final int EMP_ID 	= 4;
	public static final int PHOTO 	= 5;
	
}
