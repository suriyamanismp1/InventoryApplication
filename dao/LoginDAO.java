package dao;
import java.io.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import connectionManager.ConnectionManager;
import model.Login;
public class LoginDAO {

	public boolean validate(Login login) throws IOException, SQLException,ClassNotFoundException
	{
		String username=login.getUSERNAME();
		String password=login.getPASSWORD();
		
		ConnectionManager conn=new ConnectionManager();
		Connection con=conn.establishConnection();
		
		Statement st= con.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM LOGIN");
		
		while(rs.next())
		{
			if(username.equals(rs.getString("USERNAME"))&& password.equals(rs.getString("PASSWORD")))
			{
				conn.closeConnection();
				return true;
			}
		}
		conn.closeConnection();
		return false;
	}
	public boolean validatea(Login login) throws IOException, SQLException,ClassNotFoundException
	{
		String username=login.getUSERNAME();
		String password=login.getPASSWORD();
		
		ConnectionManager conn=new ConnectionManager();
		Connection con=conn.establishConnection();
		
		Statement st= con.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM ADMINLOGIN");
		
		while(rs.next())
		{
			if(username.equals(rs.getString("USERNAME"))&& password.equals(rs.getString("PASSWORD")))
			{
				conn.closeConnection();
				return true;
			}
		}
		conn.closeConnection();
		return false;
	}
}
