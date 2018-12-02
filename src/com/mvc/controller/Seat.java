/**
 * PATCH NOTES:
 * September 20 2018: Skeleton code created
 * October 3 2018: Created working code - untested
 * October 13 2018: Tested code, patched bugged code
 * October 19 2018: Added getters/setters code
 * November 30 2018: Added JavaDoc comments
 */
import java.sql.*;
import java.util.ArrayList;

public class Seat {

	private int SID; //seat ID
	private int showingID; //showing foreign key 
	private int isOpen; //is the seat open 0 means it's taken 
	//specific for maddies comp
	//private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	/**
	 * Default constructor - primarily used for retrieval of data using getters/setters
	 */
	public Seat() {
	
		this.SID = 0;
		this.showingID = 0;
		this.isOpen = 1;			
	}
	
	/**
	 * Overloaded constructor - Used to create new seats using parameters
	 * @param showing id for seat
	 * @param open status of seat
	 */
	public Seat(int showing, int open) {
		
		this.showingID = showing;
		this.isOpen = open;
	}
	
	/**
	 * Overloaded constructor - Used to create new seats using parameters
	 * @param id for specific seat
	 * @param showing id for seat
	 * @param open status of seat
	 */
	public Seat(int id, int showing, int open) {
		
		this.SID = id;
		this.showingID = showing;
		this.isOpen = open;
	}
	
	/**
	 * returns list of seats with certain showing id
	 * @param showing id of seat in database
	 * @return Array List of seats that have the specified id
	 */
	public ArrayList<Seat> getAllSeatsForShowing(int id){
		
		ArrayList<Seat> seats = new ArrayList<Seat>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);

			String query = "SELECT seatID, isOpen FROM Seats WHERE atShowing = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  id);
			rs = pstmt.executeQuery();
	
			while(rs.next()) {
				Seat seat = new Seat(rs.getInt("seatID"), id, rs.getInt("isOpen"));	
				seats.add(seat);			
			}//while

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
		
		
		return seats;
		
	}
	
	/**
	 * returns list of open seats associated with certain showing id
	 * @param showing id of seat in database
	 * @return Array List of seats that have the specified id AND are open
	 */
	public ArrayList<Seat> getOpenSeatsByShowing(int id){
		
		ArrayList<Seat> open = new ArrayList<Seat>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);

			String query = "SELECT seatID FROM Seats WHERE atShowing = ? AND isOpen = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  1);
			pstmt.setInt(2, id);
			rs = pstmt.executeQuery();
	
			while(rs.next()) {
				Seat seat = new Seat(rs.getInt("seatID"), id, 1);	
				open.add(seat);			
			}//while

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
		
		
		
		return open;
		
	}	
	
	/**
	 * Retrieves all relevant data related to seat into its default variables from database
	 */
	public void retrieveSeatFromDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT seatID, atShowing, isOpen FROM Seats WHERE seatID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  SID);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
			
				this.showingID = rs.getInt("atShowing");
				this.isOpen = rs.getInt("isOpen");				
			}
			
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
				
	}//retrieveSeatFromDB
	
	/**
	 * adds seat to seats database
	 */
	public void addSeatToDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
		
			String query = "INSERT into Seats(seatID, atShowing, isOpen) values(?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1,  this.SID);
			pstmt.setInt(2,  this.showingID);
			pstmt.setInt(3,  this.isOpen);	
			
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

	}//addSeatToDB

	public int getSID() {
		
		return this.SID;
	}
	
	public int getShowingID() {
		
		return this.showingID;
	}
	
	public int getIsOpen() {
		
		return this.isOpen;
	}
	
	public void setSID(int id) {
		
		this.SID = id;
	}
	
	public void setShowingID(int showing) {
		
		this.showingID = showing;
	}
	
	public void setIsOpen(int i) {
		
		this.isOpen = i;
	}
	
	/**
	 * Deletes seat from seats database
	 * EX: seat.deleteSeatFromDB();
	 */
	public void deleteSeatFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
	
			query = "DELETE FROM Seats WHERE seatID = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, SID);
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

	}//deleteSeatFromDB
	
	/**
	 * resets seat variables
	 */
	public void reset() {
		this.SID = 0;
		this.showingID = 0;
		this.isOpen = 0;	
	}
	
	/**
	 * Completely clears Seats database of all data (UNRECOVERABLE once used)
	 */
	public void deleteSeatsTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Seats";
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
	}//deleteSeatsTable
	
	@Override
	public String toString() {
		return "Current Seat data:\n"
				+ "SID: \t" + SID + "\n"
				+ "Showing ID: \t" + this.showingID + "\n"
				+ "IsOpen: \t" + this.isOpen + "\n";
				
	}
	
		
}
