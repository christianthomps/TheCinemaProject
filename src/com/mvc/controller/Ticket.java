import java.sql.*;

public class Ticket {

	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	private int TID; //ticket ID
	private int fromBooking; //bookingID foreign key
	private int ticketType; //ticketTypeID foreign key
	private int ofSeat; // seatID foreign key
	private double sellingPrice;//selling price of ticket
	
	public Ticket() {
		
		this.TID = 0;
		this.fromBooking = 0;
		this.ofSeat = 0;
		this.sellingPrice = 0;
	}//default constructor
	
	public Ticket(int booking, int seat, double price) {
		
		this.fromBooking = booking;
		this.ofSeat = seat;
		this.sellingPrice = price;
		
	}//constructor
	
	public Ticket(int booking, int seat) {
		
		this.fromBooking = booking;
		this.ofSeat = seat;
	
	}//constructor
	
	public void addTicketToDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			
			//first get selling price based on the ticket type  
			String query = "SELECT ticketTypePrice FROM TicketTypes WHERE ticketTypeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  ticketType);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.sellingPrice = rs.getDouble("ticketTypePrice");
			}//while
			
			query = "INSERT into Tickets(ticketID, fromBooking, ticketType, ofSeat, sellingPrice) values(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1,  this.TID);
			pstmt.setInt(2,  this.fromBooking);
			pstmt.setInt(3,  this.ticketType);
			pstmt.setInt(4,  this.ofSeat);
			pstmt.setDouble(5,  this.sellingPrice);	

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
	}//addMovieToDB
	
	public void retrieveTicketFromDB(){
		
		//first get all foreign keys 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT ticketID, fromBooking, ticketType, ofSeat FROM Tickets WHERE ticketID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.TID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				this.fromBooking = rs.getInt("fromBooking");
				this.ticketType = rs.getInt("ticketType");
				this.ofSeat = rs.getInt("ofSeat");
			
			}//get from Ticket Types 
			
			//now get selling price based on the ticket type 
			query = "SELECT ticketTypePricet FROM TicketTypes WHERE ticketTypeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.ticketType);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				this.sellingPrice = rs.getDouble("ticketTypePrice");
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
		
	}//addTicketToDB
		
	public void deleteTicketFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
	
			query = "DELETE FROM Tickets WHERE ticketID = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, TID);
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

	}//deleteTicketFromDB
	
	public void reset() {
		this.TID = 0;
		this.fromBooking = 0;
		this.ticketType = 0;
		this.ofSeat = 0;
		this.sellingPrice = 0;	
	
	}//reset
	

	public int getTID() {
		
		return this.TID;
	}
	
	public int getFromBooking() {
		
		return this.fromBooking;
	}
	
	public int getTicketType() {
		
		return this.ticketType;
	}
	
	public int getOfSeat() {
		
		return this.ofSeat;
	}
	
	public double getSellingPrice() {
		
		return this.sellingPrice;
	}
	
	public void setTID(int id) {
		
		this.TID = id;	
	}
	
	public void setFromBooking(int booking) {
		
		this.fromBooking = booking;
	}
	
	public void setTicketType(int type) {
		
		this.ticketType = type;
	}
	
	public void setOfSeat(int seat) {
		
		this.ofSeat = seat;
	}
	
	public void deleteTicketsTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Tickets";
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
	}//deleteTicketsTable
	
	
	@Override
	public String toString() {
		return "Current Ticket data:\n"
				+ "TID: \t" + TID + "\n"
				+ "Booking ID: \t" + this.fromBooking + "\n"
				+ "Ticket Type: \t" + this.ticketType + "\n"
				+ "Of Seat: \t" + this.ofSeat + "\n";
				
	}
	
	
	
}
