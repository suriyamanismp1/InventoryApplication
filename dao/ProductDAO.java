package dao;
import java.sql.*;

import com.mysql.cj.protocol.Resultset;
import java.util.*	;
import model.Product;
import connectionManager.*;
public class ProductDAO 
{	
	public int avalp=0;
	public int avalq=0;
	public int mins=0;
	public String pnn="";
	public void addProduct(Product product) throws ClassNotFoundException,SQLException
	{
		int id=product.getPRODUCTID();
		String name=product.getPRODUCTNAME();
		int minsell=product.getMINSELL();
		int price=product.getPRICE();
		int quantity=product.getQUANTITY();
		ConnectionManager conn = new ConnectionManager();
		Connection con=conn.establishConnection();
		String query ="INSERT INTO PRODUCT(PRODUCTID,PRODUCTNAME,MINSELL,PRICE, QUANTITY)VALUES(?,?,?,?,?)";
		PreparedStatement ps =con.prepareStatement(query);
		ps.setInt(1,id);
		ps.setString(2,name);
		ps.setInt(3,minsell);
		ps.setInt(4, price);
		ps.setInt(5, quantity);
		ps.executeUpdate();
		conn.closeConnection();
	}
	public void display()throws ClassNotFoundException,SQLException
	{
		ConnectionManager conn = new ConnectionManager();
		Connection con=conn.establishConnection();
		Statement st= con.createStatement();
		ResultSet rs=st.executeQuery("SELECT * FROM PRODUCT");
		System.out.println("   +--------------+-----------+---------+-------+----------+\n"
				         + "   | PRODUCT NAME | PRODUCTID | MINSELL | PRICE | QUANTITY |\n"
				         + "   +--------------+-----------+---------+-------+----------+");
		while(rs.next())
		{ 
			System.out.println("   | --------------------------------------------------\\");
			System.out.println("   | "+rs.getString("PRODUCTNAME")
					+ " | " + rs.getInt("PRODUCTID")
					+ " | " + rs.getInt("MINSELL")
					+ " | " + rs.getInt("PRICE")
					+ " | " + rs.getInt("QUANTITY")+" | ");
			System.out.println("   | --------------------------------------------------\\");
		}
		conn.closeConnection();
		
	}
	public Boolean display1col(Product product)throws ClassNotFoundException, SQLException
	{	
		int idd=product.getPRODUCTID();
		ConnectionManager conn = new ConnectionManager();
		Connection con=conn.establishConnection();
		
		PreparedStatement st=con.prepareStatement(" SELECT PRODUCTNAME,PRICE,QUANTITY,MINSELL FROM PRODUCT WHERE PRODUCTID=?;");
		st.setInt(1,idd);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
		avalq=rs.getInt("QUANTITY");
		mins=rs.getInt("MINSELL");
		pnn+=rs.getString("PRODUCTNAME");
		System.out.println("   +--------------+-----------+---------+"
		                 + "\n   | PRODUCT NAME | QUANTITY  | MINSELL |"
		                 + "\n   +--------------+-----------+---------+");
		System.out.println("   | --------------------\\");
		System.out.println("   | "+pnn
				         + " | " + avalq 
				         + " | "+mins);
		avalp=rs.getInt("PRICE");
		System.out.println("   | --------------------\\");
		pnn="";
		conn.closeConnection();
		return true;
		}
		else
		{
			System.out.println("   !-- No product avaliable --!");
			conn.closeConnection();
			return false;
		}
		
	}
	public  void buy(Product product) throws ClassNotFoundException, SQLException
	{	
		int avali=product.getPRODUCTID();
		int quan=product.getQUANTITY();
		int pr=0;
		if(quan>mins)
		{ 
			System.out.println("   !-- Exist MinSellQuantity --!");
		}
		else
		{	
			if(quan>avalq) 
			{
				System.out.println("   !-- We have low quantity with "+avalq+" --!");
			}
			else
			{
			avalq=avalq-quan;
			pr=avalp*quan;
			System.out.println("    Total cost Rs."+pr);
			ConnectionManager conn = new ConnectionManager();
			Connection con=conn.establishConnection();
			PreparedStatement st=con.prepareStatement(" SET SQL_SAFE_UPDATES=0;");
			st.executeUpdate();
			PreparedStatement t=con.prepareStatement("UPDATE PRODUCT SET QUANTITY=? WHERE PRODUCTID=?;");
			t.setInt(1,avalq);
			t.setInt(2,avali);
			t.executeUpdate();
			String query1=" INSERT INTO  "+product.getUSERNAME() +" (PRODUCTID,PRODUCTNAME,TRANSACTION,QUANTITY,PRICE,TOTALCOST)VALUES(?,?,?,?,?,?);";
			PreparedStatement y=con.prepareStatement(query1);
			y.setInt(1,avali);
			y.setString(2, pnn);
			y.setString(3,"BUY");
			y.setInt(4,quan);
			y.setInt(5, avalp);
			y.setInt(6, pr);
			y.executeUpdate();
			conn.closeConnection();
			}
		}
	}
	public void sell(Product product)throws ClassNotFoundException, SQLException
	{
		int id=product.getPRODUCTID();
		int quan=product.getQUANTITY();
		int price=0;
		int sellquan=0;
		String pn="";
		ConnectionManager conn = new ConnectionManager();
		Connection con=conn.establishConnection();
		PreparedStatement st=con.prepareStatement("SELECT PRODUCTNAME,PRICE,QUANTITY FROM PRODUCT WHERE PRODUCTID=?");
		st.setInt(1,id);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			 price=rs.getInt("PRICE");
			 sellquan=rs.getInt("QUANTITY");
			 pn+=rs.getString("PRODUCTNAME");
		}
		sellquan=sellquan+quan;
		System.out.println("   $ Total Cost for Sell Rs."+price*quan+" $");
		PreparedStatement rt=con.prepareStatement(" SET SQL_SAFE_UPDATES=0;");
		rt.executeUpdate();
		PreparedStatement t=con.prepareStatement("UPDATE PRODUCT SET QUANTITY=? WHERE PRODUCTID=?;");
		t.setInt(1,sellquan);
		t.setInt(2,id);
		t.executeUpdate();
		String query2=" INSERT INTO  "+product.getUSERNAME()+"  (PRODUCTID,PRODUCTNAME,TRANSACTION,QUANTITY,PRICE,TOTALCOST)VALUES(?,?,?,?,?,?);";
		PreparedStatement y=con.prepareStatement(query2);
		y.setInt(1,id);
		y.setString(2, pn);
		y.setString(3,"SELL");
		y.setInt(4,quan);
		y.setInt(5, price);
		y.setInt(6, price*quan);
		y.executeUpdate();
		conn.closeConnection();
		
	}
	public void history(Product product)throws ClassNotFoundException,SQLException
	{	
		LinkedList<String> d=new LinkedList<String>();
		ConnectionManager conn = new ConnectionManager();
		Connection con=conn.establishConnection();
		Statement st= con.createStatement();
		
		String query3="SELECT * FROM "+product.getUSERNAME()+";";
		ResultSet rs=st.executeQuery(query3);
System.out.println("   +------------+-------------+-------------+----------+-------+-----------+"
		         + "\n   | PRODUCT ID | PRODUCTNAME | TRANSACTION | QUANTITY | PRICE | TOTALCOAT |"
		         + "\n   +------------+-------------+-------------+----------+-------+-----------+");
		while(rs.next())
		{ 
			 d.add("   |----------------------------------------\\"
			   + "\n   | "+rs.getInt("PRODUCTID")
				 + " | " + rs.getString("PRODUCTNAME")
				 + " | " + rs.getString("TRANSACTION")
				 + " | " + rs.getInt("QUANTITY")
				 + " | " + rs.getInt("PRICE")
			     + " | " + rs.getInt("TOTALCOST")
			     + " | "
			   + "\n   |----------------------------------------\\");
		}
		Collections.reverse(d);
		for(Object i:d)
		{
			System.out.println(i);
		}
		conn.closeConnection();
		
	}	
}
 