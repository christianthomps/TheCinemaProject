/**
 * PATCH NOTES:
 * September 20 2018: Skeleton code created
 * October 3 2018: Created working code - untested
 * October 13 2018: Tested code, patched bugged code
 * October 19 2018: Added getters/setters code
 * November 30 2018: Added JavaDoc comments
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Theatre {

	private String NAME;
	private int TID;
	   
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
	public Theatre() {
		
		this.TID = 0;
		this.NAME = null;
	}
	/**
	 * Overloaded constructor - Used to create new theatres using parameters
	 * @param name of hall
	 */
	public Theatre(String name) {
		
		this.NAME = name;
	}
	/**
	 * Adds theatre to Halls table
	 */
	public void addThetreToDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			
			String query = "INSERT into Halls(hallName) values(?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1,  NAME);
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
	}//addTheatreToDB
	/**
	 * Retrieves theatre data based on the hall id
	 * @param id of the hall
	 */
	public void retrieveTheatreDataUsingID(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT hallName FROM Halls WHERE hallID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  id);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.NAME = rs.getString("hallName");
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
	
}//retrieveTheatreFromDB
	
	/**
	 * Retrieves all info from Halls table based on hall name
	 * @param t name of the hall
	 */
		public void retrieveTheatreData(String t) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(TEST);
				String query = "SELECT hallID, hallName FROM Halls WHERE hallName = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1,  t);
				rs = pstmt.executeQuery();

				while(rs.next()) {
					this.TID = rs.getInt("hallID");
					this.NAME = rs.getString("hallName");
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
		
	}//retrieveTheatreFromDB
	
	/**
	 * Deletes theatre from Halls table
	 */
	public void deleteTheatreFromDB() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "DELETE FROM Hall WHERE hallName = ?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, this.NAME);
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
		
	}//deleteTheatreFromDB
	
	/**
	 * Deletes entire Halls table -- UNRECOVERABLE
	 */
	public void deleteTheatresTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Halls";
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
	}//deleteTheatresTable
	
	/**
	 * resets theatre variables
	 */
	public void reset() {
		
		this.TID = 0;
		this.NAME = null;
	}//reset
	
	public int getTID() {
		
		return this.TID;
	}
	
	public String getName() {
		
		return this.NAME;
	}
	
	public void setTID(int id) {
		
		this.TID = id;
	}
	
	public void setName(String name) {
		
		this.NAME = name;
	}
	
	
	
}
