
public class Laptop
{
	int id;
	String tag;
	int employeeID;
	String notes;
	
	
	public Laptop()	{	}
	
	public Laptop(int id, String tag, int emp_id, String not)
	{
		this.id = id;
		this.tag = tag;
		this.employeeID = emp_id;
		this.notes = not;
	}

	public Laptop(String tag, int emp_id, String not)
	{
		this.tag = tag;
		this.employeeID = emp_id;
		this.notes = not;
	}
	
	public int getId(){ return id;}
	public void setId(int id){this.id = id;}
	public String getTag(){return tag;}
	public void setTag(String tag){this.tag = tag;}
	public String getNotes(){return notes;}
	public void setNotes(String notes){this.notes = notes;}
	public int getEmployeeID() { return employeeID; }
	public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }
}
