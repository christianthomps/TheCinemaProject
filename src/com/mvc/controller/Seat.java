import java.sql.*;
import java.util.ArrayList;

public class Seat {

	private int SID; //seat ID
	private int showingID; //showing foreign key 
	private int isOpen; //is the seat open 0 means it's taken 
	//specific for maddies comp
	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	//private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	public Seat() {
	
		this.SID = 0;
		this.showingID = 0;
		this.isOpen = 1;			
	}
		
	public Seat(int showing, int open) {
		
		this.showingID = showing;
		this.isOpen = open;
	}
	
	public Seat(int id, int showing, int open) {
		
		this.SID = id;
		this.showingID = showing;
		this.isOpen = open;
	}
	
	
	
	//retrieves the seat info based on a given showing id
	
	//not sure about this -- might want to put in a diff class bc we want to get all the seats for a showing not just one
	public void retrieveSeatFromDBForShowing(int showID) {
							
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT seatID, atShowing, isOpen FROM Seats WHERE atShowing = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  showID);
			rs = pstmt.executeQuery();

			this.showingID = showID;
			
			while(rs.next()) {
			
				this.SID = rs.getInt("seatID");
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
	
}//retrieveSeatFromDBForShowing

	public ArrayList<Seat> getOpenSeats(){
		
		ArrayList<Seat> open = new ArrayList<Seat>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);

			String query = "SELECT seatID, atShowing FROM Seats WHERE isOpen = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  1);
			rs = pstmt.executeQuery();
	
			while(rs.next()) {
				Seat seat = new Seat(rs.getInt("seatID"), rs.getInt("atShowing"), 1);	
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
	
	
	
	//retrieves the seat's info based on the seat id 
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
	
	public void reset() {
		this.SID = 0;
		this.showingID = 0;
		this.isOpen = 0;	
	}
	
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
