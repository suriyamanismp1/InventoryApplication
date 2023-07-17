package controller;
import java.io.* ;
import java.sql.SQLException;
import model.Login;
import model.Product;
import dao.LoginDAO;
import dao.ProductDAO;
public class Main
{

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
{	
		System.out.println("|||| Welcome to Inventory Application ||||");
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		int option;
  		Login login =new Login();
  		Product product=new Product();
  		LoginDAO logindao=new LoginDAO();
  		ProductDAO productdao=new ProductDAO();
		do
{
			System.out.println("++++---------+++---------+++--------++++++"
					       + "\n ||| 1.Admin ||| 2.Agent ||| 3.Exit |||   "
					       + "\n +++---------+++---------+++--------+++   ");
			option=Integer.parseInt(br.readLine());
			
			switch(option)
	{
			case 1:
		{
		    System.out.println("   +-------------+"
		    		       + "\n   | Admin Login |"
		    		       + "\n   +-------------+"
		    		       + "\n   | Enter username:");
			String username =br.readLine();
			System.out.println("   | Enter password:");
			String password = br.readLine();
			System.out.println("   +---------------+");
			login.setUSERNAME(username);
			login.setPASSWORD(password);
			boolean result =logindao.validatea(login);
			if(result==true)
			{
				System.out.println("   +**------------------**+"
						       + "\n   *** Login Successful ***"
						       + "\n   +**------------------**+");
				int ption;
				do {
					System.out.println("  ++---------------++-----------------------------++----------++"
							       + "\n  || 1.Add Product || 2.Display Inventroy Details || 3.Logout ||"
						           + "\n  ++---------------++-----------------------------++----------++");
					ption = Integer.parseInt(br.readLine());
					switch(ption)
					{
					case 1:System.out.println("   +-------------------------\n"
							                + "   | Enter the Product Name :");
					String productName=br.readLine();
					System.out.println("   | Enter the Product ID :");
					int productId=Integer.parseInt(br.readLine());
					System.out.println("   | Enter the MinSelling Quantity :");
					int minsell=Integer.parseInt(br.readLine());
					System.out.println("   | Enter the Price of the Product :");
					int price=Integer.parseInt(br.readLine());
					System.out.println("   | Enter the quantity :");
					int quantity=Integer.parseInt(br.readLine());
					System.out.println("   +--------------------------------+");
					product.setPRODUCTNAME(productName);
					product.setPRODUCTID(productId);
					product.setMINSELL(minsell);
					product.setQUANTITY(quantity);
					product.setPRICE(price);
					productdao.addProduct(product);
					break;
					case 2: productdao.display();break;
					case 3: System.out.println("   +-----Admin Logout-----+");break;
					}
				   }while(ption !=3);
			 }
			else
			{
				System.out.println  ("   +++------------+++"
						         + "\n   --- Login fail ---"
						         + "\n   +++------------+++");
			}
			break;
		}
			case 2:
		{   
			System.out.println("   +----------------------------------"
					       + "\n   | Agent Login |\n   | Enter username:");
			String agentname =br.readLine();
			System.out.println("   | Enter password:");
			String agentpassword = br.readLine();
			System.out.println("   +----------------------------------");
			login.setUSERNAME(agentname);
			login.setPASSWORD(agentpassword);
			boolean agentresult =logindao.validate(login);
			if(agentresult==true)
			{
				System.out.println("   +**------------------**+"
					       + "\n   *** Login Successful ***"
					       + "\n   +**------------------**+");
				int option1=0;
				do {
					System.out.println("  ++------------++----------------++----------++"
						           + "\n  || 1.Buy/Sell || 2.Show History || 3.Logout ||"
							       + "\n  ++------------++----------------++----------++");
					option1 = Integer.parseInt(br.readLine());
					switch(option1)
					{
					case 1:{
						 	System.out.println("   +---Buy-+---Sell-+");
							System.out.println("   | 1.Buy | 2.Sell |"
									       + "\n   +-------+--------+");
							int option2=Integer.parseInt(br.readLine());
							switch(option2)
							{
							case 1:{
								System.out.println("   +----Buying  Section---+"
										       + "\n   | Enter the Product Id :");
								int productid=Integer.parseInt(br.readLine());
								product.setPRODUCTID(productid);
								if(productdao.display1col(product)==false) {
									System.out.println("   | Invalid Product Id |");
									break;}
								System.out.println("   | Enter quantity :");
								int quan=Integer.parseInt(br.readLine());
								System.out.println("   +-----------------------+");
								product.setUSERNAME(agentname);
								product.setQUANTITY(quan);
								productdao.buy(product);
									
								break;
							}
							case 2:{
								System.out.println("   +----Selling  section---------+"
										       + "\n   | Enter the Product ID to Sell : ");
								int productid=Integer.parseInt(br.readLine());
								product.setPRODUCTID(productid);
								System.out.println("   | Enter quantity : ");
								int quan=Integer.parseInt(br.readLine());
								System.out.println("   +------------------------------+");
								product.setUSERNAME(agentname);
								product.setQUANTITY(quan);
								productdao.sell(product);
								break;
							}
							}
					        break;
					        }
					case 2:{
							System.out.println("   +---------------------History-------------------|");
							product.setUSERNAME(agentname);
							productdao.history(product);
							break;
							}
					case 3:{
						System.out.println("   +-----Agent Logout-----+");
							
							break;
							}
					}
					
			       }while(option1 !=3);
			}
			else
			{
				System.out.println  ("   +++------------+++"
				         + "\n   --- Login fail ---"
				         + "\n   +++------------+++");
			}
			break;
		}
			case 3:System.out.println("++++-----------------------------------++++"
					              + "\n|||| Logout from Inventory Application ||||"
				                  + "\n          ||||| Thank you |||||    ");break;
		
}
}while(option!=3);
}
}
