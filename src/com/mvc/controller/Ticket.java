import java.sql.*;
import java.util.ArrayList;

public class Ticket {

	//specific for maddies comp
	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	//private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	private int TID; //ticket ID
	private int fromBooking; //bookingID foreign key
	private int ticketType; //ticketTypeID 0 = child 1 = regular  2 = senior 
	private int ofSeat; // seatID foreign key
	private double sellingPrice;//selling price of ticket
	
	public Ticket() {
		
		this.TID = 0;
		this.fromBooking = 0;
		this.ticketType = 0;
		this.ofSeat = 0;
		this.sellingPrice = 0;
	}//default constructor
	
	public Ticket(int booking, int type, int seat) {
	
		this.fromBooking = booking;
		this.ticketType = type;
		this.ofSeat = seat;
		calculateSellingPrice();
	}
	
	public void calculateSellingPrice() {
		
		if(this.ticketType == 0) {
			
			this.sellingPrice = 8;
		}
		else if(this.ticketType == 1) {
			
			this.sellingPrice = 12;
		}
		else if(this.ticketType == 2) {
			
			this.sellingPrice = 9;
		}
	}//calculateSellingPrice


	//constructor for inside use ticket class
	public Ticket(int tid, int fromB, int type, int seat, double price) {
		
		this.TID = tid;
		this.fromBooking = fromB;
		this.ticketType = type;
		this.ofSeat = seat;
		this.sellingPrice = price;
	
	}//constructor
	
	public ArrayList<Ticket> getTicketsForBooking(int bid){
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		this.fromBooking = bid;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);

			String query = "SELECT ticketID, ticketType, ofSeat, sellingPrice FROM Tickets WHERE fromBooking = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  bid);
			rs = pstmt.executeQuery();
	
			while(rs.next()) {
				Ticket item = new Ticket(rs.getInt("ticketID"), bid, rs.getInt("ticketType"), rs.getInt("ofSeat"),rs.getDouble("sellingPrice"));						
				tickets.add(item);			
			}
			
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
		
		
		return tickets;
		
	}//getTickets
	
	public void updateTicketInDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			calculateSellingPrice();
			
			String query = "UPDATE Tickets SET fromBooking = ?, ticketType = ?, ofSeat = ?, sellingPrice = ? WHERE ticketID = "+this.TID;
		
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.fromBooking);
			pstmt.setInt(2,  this.ticketType);
			pstmt.setInt(3,  this.ofSeat);
			pstmt.setDouble(4,  this.sellingPrice);			
	
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
	
	public void addTicketToDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			
			//first get selling price based on the ticket type  
			calculateSellingPrice();
			
			String query = "INSERT into Tickets(ticketID, fromBooking, ticketType, ofSeat, sellingPrice) values(?, ?, ?, ?, ?)";
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
			
			calculateSellingPrice();
			
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
