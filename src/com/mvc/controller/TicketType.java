import java.sql.*;

public class TicketType {

	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	private int TTID; //ticket type ID
	private String typeName; //ticket type name 
	private double typePrice; //ticket type price
	
	public TicketType(){
		
		this.TTID = 0;
		this.typeName = null;
		this.typePrice = 0;
	}//constructor
	
	public TicketType(String name, double price) {
		
		this.typeName = name;
		this.typePrice = price;	
	
	}
	
	public void retrieveTicketTypeFromDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT ticketTypeID, ticketTypeName, ticketTypePrice FROM TicketTypes WHERE ticketTypeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.TTID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.typeName = rs.getString("ticketTypeName");
				this.typePrice = rs.getDouble("ticketTypePrice");
			}//get from Ticket Types 
			
			rs.close();
			pstmt.close();
			con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException se2) {
			}
			try {
				if(con!=null)
					con.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}
				
	}
	
	public int getTTID() {
		
		return this.TTID;
	}
	
	public String getTypeName() {
		
		return this.typeName;
	}
	
	public double getTypePrice() {
		
		return this.typePrice;
	}
	
	public void setTTID(int id) {
		
		this.TTID = id;
	}
	
	public void setTypeName(String name) {
		
		this.typeName = name;
	}
	
	public void setTypePrice(double p) {
		
		this.typePrice = p;
	}
	
	public void deleteTicketTypeFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
	
			query = "DELETE FROM TicketTypes WHERE ticketTypeID = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, TTID);
			pstmt.executeUpdate();
			
			query = "SET FOREIGN_KEY_CHECKS=1";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException se2) {
			}
			try {
				if(con!=null)
					con.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}
		
		reset();

	}//deleteTicketTypeFromDB
	
	public void reset() {
		this.TTID = 0;
		this.typeName = null;
		this.typePrice= 0;	
	}
	
	public void deleteTicketTypessTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE TicketTypes";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "SET FOREIGN_KEY_CHECKS=1";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			}catch(SQLException se2) {
			}
			try {
				if(con!=null)
					con.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
		}
	}//deleteTicketTypesTable
	
	public void updateTicketType() {
		try {
			
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(TEST);//change later to different URL if needed
		String query = "UPDATE TicketTypes SET ticketTypeName = ?, ticketTypePrice = ? WHERE ticketTypeID = "+this.TTID;
		
		pstmt = con.prepareStatement(query);
		pstmt.setString(1,  this.typeName);
		pstmt.setDouble(2,  this.typePrice);
		pstmt.executeUpdate();

		pstmt.close();
		con.close();
	
	
	}catch(SQLException se) {
		se.printStackTrace();
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		try {
			if(pstmt!=null)
				pstmt.close();
		}catch(SQLException se2) {
		}
		try {
			if(con!=null)
				con.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
	}
				
	}
		
	@Override
	public String toString() {
		return "Current Ticket Type data:\n"
				+ "TTID: \t" + TTID + "\n"
				+ "Type Name: \t" + this.typeName+ "\n"
				+ "Type Price: \t" + this.typePrice + "\n";
				
	}
	
	
	
	
}
