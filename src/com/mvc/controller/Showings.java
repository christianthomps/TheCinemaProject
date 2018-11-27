import java.sql.*;

public class Showings {
	   
	//specific for maddies comp
	private static final String TEST = "jdbc:mysql://127.0.0.1:3306/e-booking?user=root&password=June201998";
		
	//private static final String TEST = "jdbc:mysql://127.0.01:3306/e-booking?user=root&password=password";
		
	
	static Statement stmt = null;
	static ResultSet rs = null;
	static Connection con = null;
	static PreparedStatement pstmt = null;
	
	private int SID; //show ID
	private int movieID; //movie ID of showing
	private int hallID; //hall ID of showing
	private int showtimeID; //id of actual showtime increment 
	
	private String showtime;
	private String movie;
	private String hall;

	public Showings() {
		
		this.SID = 0;
		this.movieID = 0;
		this.hallID = 0;
		this.showtimeID = 0;
		this.movie = null;
		this.showtime = null;
		this.hall = null;
		
	}//Showings
	
	public Showings(String s, String h, String m) {
		
		this.showtime = s;
		this.hall = h;
		this.movie = m;
	
	}//showings
	
	
	
	
	public void retrieveShowingFromDBForHall(String h) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT hallID FROM Halls WHERE hallName = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  h);
			rs = pstmt.executeQuery();
			
			this.hall = h;
			while(rs.next()) {
			
				this.hallID = rs.getInt("hallID");
			}
			
			query = "SELECT showID, ofMovie, inHall, atTime FROM Showings WHERE inHall = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.hallID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.SID = rs.getInt("showID");
				this.movieID = rs.getInt("ofMovie");
				this.showtimeID = rs.getInt("atTime");
			}//get from Movie table
			
			//now get the actual movie name using the movie ID foreign id 
			query = "SELECT title FROM Movies WHERE movieID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  movieID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.movie = rs.getString("title");
			}//while
			
			//now get the actual showtime string using showtime foreign ID 
			query = "SELECT timeStamp FROM Showtimes WHERE timeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  showtimeID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.showtime = rs.getString("timeStamp");
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
		
	}//retrieveShowingFromDBForHall
	
	public void retrieveShowingFromDBForTime(String tStamp) {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT timeID FROM Showtimes WHERE timeStamp = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  tStamp);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.showtimeID = rs.getInt("timeID");
			}
			
			this.showtime = tStamp;
			
			query = "SELECT showID, ofMovie, inHall, atTime FROM Showings WHERE atTime = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.showtimeID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.SID = rs.getInt("showID");
				this.movieID = rs.getInt("ofMovie");
				this.hallID = rs.getInt("inHall");
			
			}//get from Movie table
			
			//now get the actual movie name using the movie ID foreign id 
			query = "SELECT title FROM Movies WHERE movieID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  movieID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.movie = rs.getString("title");
			}//while
			
			//now get the actual hall name using the foreign hall ID
			query = "SELECT hallName FROM Halls WHERE hallID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  hallID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.hall = rs.getString("hallName");
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
		
	}//retrieveShowingFromDBForTime
	
	public void retrieveShowingFromDBForMovie(String t){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT movieID FROM Movies WHERE title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  t);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.movieID = rs.getInt("movieID");
			}
			
			this.movie = t;
			query = "SELECT showID, ofMovie, inHall, atTime FROM Showings WHERE ofMovie = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.movieID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.SID = rs.getInt("showID");
				this.hallID = rs.getInt("inHall");
				this.showtimeID = rs.getInt("atTime");
			}//get from Movie table
			
			//now get the actual hall name using the hall foreign key 
			query = "SELECT hallName FROM Halls WHERE hallID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  hallID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.hall = rs.getString("hallName");
			}//while
			
			//now get the actual showtime string using showtime foreign ID 
			query = "SELECT timeStamp FROM Showtimes WHERE timeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  showtimeID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.showtime = rs.getString("timeStamp");
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
		
	}//retrieveShowingFromDBForMovie
	
	public void retrieveShowingFromDB() {
		

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SELECT showID, ofMovie, inHall, atTime FROM Showings WHERE showID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  this.SID);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				this.movieID = rs.getInt("ofMovie");
				this.hallID = rs.getInt("inHall");
				this.showtimeID = rs.getInt("atTime");
			}//get from Movie table
			
			//now get the actual hall name using the hall foreign key 
			query = "SELECT hallName FROM Halls WHERE hallID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  hallID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.hall = rs.getString("hallName");
			}//while
			
			//now get the actual showtime string using showtime foreign ID 
			query = "SELECT timeStamp FROM Showtimes WHERE timeID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  showtimeID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			
				this.showtime = rs.getString("timeStamp");
			}//while
			
			//now get the actual movie using the movie foriegn key 
			query = "SELECT title FROM Movies WHERE movieID = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,  movieID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			
				this.movie = rs.getString("title");
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
		
		
		
		
	}
	
	
	public void addShowingToDB() {
	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);//change later to different URL if needed
			
			//first get the correct foreign key from movie table using the movie string
			String query = "SELECT movieID FROM Movies WHERE title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  movie);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.movieID = rs.getInt("movieID");
			}
			
			//then get correct foreign key from hall table using hall name
			query = "SELECT hallID FROM halls WHERE hallName = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  hall);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.hallID = rs.getInt("hallID");
			}			
			//then get correct foreign key from showtime table using showtime string 
			query = "SELECT timeID FROM Showtimes WHERE timeStamp = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  showtime);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			
				this.showtimeID = rs.getInt("timeID");
			}
			
			query = "INSERT into Showings(ofMovie, inHall, atTime) values(?, ?, ?)";
			pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1,  movieID);
			pstmt.setInt(2,  hallID);
			pstmt.setInt(3,  showtimeID);	
			
			
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();

			if (rs.next()) {
			    this.SID = rs.getInt(1);
			}
			
			//now get the showing id that it was inserted into 
			/*
			
			query="SELECT showID FROM Showings WHERE ofMovie LIKE '%'"+movieID+"'%' AND inHall LIKE '%'"+hallID+"'%' AND atTime LIKE '%'"+showtimeID+"'%'";
			rs=stmt.executeQuery(query);		
			while(rs.next()) {
			
				this.SID = rs.getInt("showID");
			}
			*/
			
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
		
	}//addShowingToDB
	
	public void deleteShowingFromDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			
			String query = "SET FOREIGN_KEY_CHECKS=0";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
	
			query = "DELETE FROM Showings WHERE showID = ?";
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

	}//deleteShowingFromDB
	
	public void reset() {
		this.SID = 0;
		this.movie = null;
		this.hall= null;	
		this.showtime = null;
		this.hallID = 0;
		this.showtimeID = 0;
		this.movieID = 0;
	
	}//reset
	
	public int getSID() {
		
		return this.SID;
	}
	
	
	public String getMovie() {
		
		return this.movie;
	}
	
	public String getHall() {
		
		return this.hall;
	}
	
	public String getShowtime() {
		
		return this.showtime;
	}
	
	public void setMovie(String m) {
		
		this.movie = m;
	}
	
	public void setHall(String h) {
		
		this.hall = h;
	}
	
	public void setShowtime(String s) {
		
		this.showtime = s;
	}
	
	public void deleteShowingsTable() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(TEST);
			String query = "SET FOREIGN_KEY_CHECKS=0";//Truncate resets AUTO_INCREMENT to 1 unlike DELETE which does not reset AUTO_INCREMENT and will resume at last digit.
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			
			query = "TRUNCATE TABLE Showings";
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
	}//deleteShowingsTable
	
	
	
	@Override
	public String toString() {
		return "Current Showing data:\n"
				+ "SID: \t" + SID + "\n"
				+ "MOVIE: \t" + movie + "\n"
				+ "HALL: \t" + hall + "\n"
				+ "SHOWTIME: \t" + showtime + "\n";
				
	}
	
	
}
