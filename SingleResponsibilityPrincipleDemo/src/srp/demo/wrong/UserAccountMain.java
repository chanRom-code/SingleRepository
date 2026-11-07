package srp.demo.wrong;

import java.io.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAccountMain {
	
	//UserAccountEntity.java -- start
	private static String userName;
	private static String emailAddress;
	
	public static String getUserName() {
		return userName;
	}


	public static void setUserName(String userName1) {
		userName = userName1;
	}


	public static String getEmailAddress() {
		return emailAddress;
	}


	public static void setEmailAddress(String emailAddress1) {
		emailAddress = emailAddress1;
	}
	//UserAccountEntity.java --end-done
	
	
	
	public static void main (String [] args) throws IOException {
		
		
		String name, email;
		
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		
		//UserAccountEntity entity = new UserAccountEntity()
		
		System.out.print("Please enter your name: ");
		name = br.readLine();
		//entity.setUserName(name);
		setUserName(name);
		
		System.out.print("Please enter your email address: ");
		email = br.readLine();
		//entity.setUserName(name);
		setEmailAddress(email);
		
		System.out.println("User Name: " + getUserName());
		System.out.println("Email: "     + getEmailAddress());
		
		
		if(!isValidEmailAddress( getEmailAddress() )){
			System.out.println("Invalid email address Format!");
		} else if (!isValidUserName( getUserName() )) {
			System.out.println("Invalid user name!");
		} else {
			//all inputs are valid, save to db
			insertRecords( getUserName() , getEmailAddress());
			
		}
		
	}
	
	
	//UserAccountRepository.java --start
	//Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "hr";
    private static final String PASS = "hr123";	
	
	public static void connectToOracleDB() {
		 System.out.println("Connecting to Oracle Database...");

	        try (
	        	Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery("SELECT * FROM hr.user_acct")) {

	            if (conn != null) {
	                System.out.println("Connected successfully!");
	                
	                // Process the results
	                while (rs.next()) {
	                    System.out.println(
	                    		rs.getString("user_name") + "\t\t\t" + 
	                    		rs.getString("email_address") );
	                }
	            }
	            conn.close();
	        } catch (SQLException e) {
	            System.err.println("Database connection failed!");
	            e.printStackTrace();
	        }
	}
	
	//public static void insertRecords(UserAccountEntity user)
	public static void insertRecords(String name, String email) {
		String query = "insert into hr.user_acct(user_name, email_address)"
				+ " values (?,?)";
		
		  try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); //open db connection
			  PreparedStatement pstmt = conn.prepareStatement(query)) {
			  
			  if (conn != null) {
				  System.out.println("Connected successfully!");
	              
				  pstmt.setString(1, name);
				  //pstmt.setString(1, user.getUserName());
	  			  pstmt.setString(2, email);
	  			  //pstmt.setString(2, user.getUserName());
	  			  int rowsAdded = pstmt.executeUpdate();
	  			  
	  			  if (rowsAdded > 0) System.out.println("User inserted successfully!");    
	  		  }
			  
			  conn.close(); //close the oracle DB connection
			  
			  
		  } catch (SQLException e) {
	            System.err.println("Database connection insert failed!");
	            e.printStackTrace();
	      }
		
		
	}
	//UserAccountRepository.java -- end
	
	
	//UserNameValidatorUtil.java --start
	public static boolean isValidUserName(String name) {
		if (name == null) {
	        return false;
	     }
		return !(name.length() == 0); 
	}
	//UserNameValidatorUtil.java --end

	
	
	//EmailValidatorUtil.java --start
	// Pre-compile the regex pattern for better performance
    private static final String EMAIL_REGEX = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
	
	public static boolean isValidEmailAddress(String email) {
		 if (email == null) {
	        return false;
	     }
	     
		 Matcher matcher = pattern.matcher(email);
	     return matcher.matches();
	    
	}
	//EmailValidatorUtil.java --end
	
	

	
}
