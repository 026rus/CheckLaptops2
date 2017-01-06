import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.awt.*;

import javax.imageio.*;
import javax.swing.*;

public class Test
{

	public static void main(String[] args)
	{
		Test t = new Test();
		t.testDB();

	}

	public void testDB()
	{
		DB mdb = new DB();

		try
		{
			// connecting to Data Base Server.
			mdb.connectDB();

			String[] laptop = mdb.getEquipment("5CG5282KVL");

			for (int i = 0; i < laptop.length; i++)
			{
				if (laptop[i] != null)
					System.out.println("laptop [" + i + "]: " + laptop[i].toString());
				else
					System.out.println("laptop [" + i + "]: NULL");
			}
			mdb.getLaptopTable();

			// disconnecting from Data Base Server.
			mdb.disconnectDB();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			Utilitis.print("SQL not working SORRY !!!");

			e.printStackTrace();
		}
	}

}