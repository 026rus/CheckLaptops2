import java.awt.Toolkit;
import java.sql.SQLException;

class SendDataToDB implements Runnable
	{
		
		private DB db = new DB();
		private String tag;
		private int employee;
		private boolean in;
		
		SendDataToDB(String t, int e, boolean i)
		{
			tag = t;
			employee = e;
			in = i;
		}

		@Override
		public void run()
		{
			try
			{

				if (db.addRecord(tag, employee, in))
					Toolkit.getDefaultToolkit().beep();
			}
			catch (SQLException e)
			{
				System.out.println(e.toString());
			}
		}
	}