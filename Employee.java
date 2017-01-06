
public class Employee
{
	private int id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;	
	private String photo;
	
	
	public Employee()
	{
	}
	
	public Employee(String first_name, String middle_name, String last_name, String email, String photo)
	{
		this.id = -1;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.email = email;
		this.photo = photo;
	}

	public Employee(int id, String first_name, String middle_name, String last_name, String email, String photo)
	{
		this.id = id;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.email = email;
		this.photo = photo;
	}

	public int getId() { return id;	}
	public String getFirst_name() { return first_name; }
	public String getMiddle_name() { return middle_name; }
	public String getLast_name() { return last_name; }
	public String getEmail() { return email; }
	public String getPhoto() { return photo; }
	public void setId(int id) { this.id = id; }
	public void setFirst_name(String first_name) 
	{
		this.first_name = first_name;
	}
	public void setMiddle_name(String middle_name)
	{
		this.middle_name = middle_name;
	}
	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public void setPhoto(String photo)
	{
		this.photo = photo;
	}

	
}
