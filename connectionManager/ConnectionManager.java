package connectionManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionManager {
	Connection con =null;
	public Connection establishConnection()throws ClassNotFoundException,SQLException
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con =DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/INVENTORY",
				"root",
				"root"
				);
		return con;
	}
	public void closeConnection() throws SQLException
	{
		con.close();
	}
	 
}
