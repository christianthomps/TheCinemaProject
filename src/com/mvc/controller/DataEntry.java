import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//class to add some example data to the database
public class DataEntry {

	
	public static void main(String[] args) {
	
		//this part is to clear what u have in ur database tables rn 
		Users users = new Users();
		users.deleteUsersTable();
		
		Movie m = new Movie();
		m.deleteMoviesTable();
		
		Theatre th = new Theatre();
		th.deleteTheatresTable();
		
		Showings sh = new Showings();
		sh.deleteShowingsTable();
		
		Seat s = new Seat();
		s.deleteSeatsTable();
		//
		
		
		
		
		//adding random users to database
	Users Users = new Users("johndoe@gmail.com", "pass", "John", "Doe", 123456789, 0);
	Users.addRegUsersToDB();
	Users = new Users("janedoe@gmail.com", "pass", "Jane", "Doe", 123456788, 1);
	Users.addRegUsersToDB();
	Users = new Users("fsusucks@gmail.com", "pass", "Gibby", "Unknown", 12345677, 1);
	Users.addRegUsersToDB();
	
		//adding random movies to database
	//1. The Grinch
	Movie movies = new Movie("The Grinch", "Grinch cast (Change later)", "Family", "Chris Meledandri", 2, "Grinch trailer pic link (change later)","https://www.youtube.com/watch?v=vjnqABgxfO0", 6.4, "PG", 1);
	movies.addMovieToDB();
	//2. Bohemian Rhapsody
	movies = new Movie("Bohemian Rhapsody", "BH cast (Change later)", "Drama", "Bryan Singer", 3, "BH trailer pic link (change later)","https://www.youtube.com/watch?v=mP0VHJYFOAU", 8.4, "PG-13", 1);
	movies.addMovieToDB();
	//3. A Star is Born
	movies = new Movie("A Star is Born", "star cast (Change later)", "Romance", "Bradley Cooper", 3, "Star trailer pic link (change later)","https://www.youtube.com/watch?v=nSbzyEJ8X9E", 8.2, "R", 1);
	movies.addMovieToDB();
	//4. Instant Family
	movies = new Movie("Instant Family", "Instant Family cast (Change later)", "Comedy", "Mark Wahlberg", 3, "Instant trailer pic link (change later)","https://www.youtube.com/watch?v=IUfZq3DUd3Y", 7.6, "PG-13", 1);
	movies.addMovieToDB();
	//5. Robin Hood
	movies = new Movie("Robin Hood", "Robin Hood cast (Change later)", "Action", "Leonardo DiCaprio", 3, "Robin Hood trailer pic link (change later)","https://www.youtube.com/watch?v=tJfDBSWYqU8", 6.9, "PG-13", 1);
	movies.addMovieToDB();
	//6. The Nutcracker and the Four Realms
	movies = new Movie("The Nutcracker and the Four Realms", "Nutcracker cast (Change later)", "Drama", "Mark Gordon", 2, "Nutcracker trailer pic link (change later)","https://www.youtube.com/watch?v=a2vmAttpgrg", 5.6, "PG", 1);
	movies.addMovieToDB();
	//7. Gremlins
	movies = new Movie("Gremlins", "Gremlins cast (Change later)", "Horror", "Michael Finnell", 2, "Gremlins trailer pic link (change later)","https://www.youtube.com/watch?v=-14d51QTVjo", 7.2, "PG", 1);
	movies.addMovieToDB();
	//8. Boy Erased 
	movies = new Movie("Boy Erased", "Boy Erased cast (Change later)", "Drama", "Joel Edgerton", 3, "Boy Erased trailer pic link (change later)","https://www.youtube.com/watch?v=-B71eyB_Onw", 7.1, "R", 1);
	movies.addMovieToDB();
	//9. Spider-Man : Into the Spider-Verse
	movies = new Movie("Spider-Man: Into the Spider-Verse", "Spider-Man cast (Change later)", "Sci-Fi", "Phil Lord", 2, "SpiderMan trailer pic link (change later)","https://www.youtube.com/watch?v=tg52up16eq0", 0, "PG", 0);
	movies.addMovieToDB();
	//10. The Mule
	movies = new Movie("The Mule", "The Mule cast (Change later)", "Action", "Clint Eastwood", 3, "The Mule trailer pic link (change later)","https://www.youtube.com/watch?v=N_QksSzK7sI", 0, "R", 0);
	movies.addMovieToDB();
	
	//now add random Theatre halls to database
	//five halls -- A B C D and E
	Theatre theatres = new Theatre("A");
	theatres.addThetreToDB();
	theatres = new Theatre("B");
	theatres.addThetreToDB();
	theatres = new Theatre("C");
	theatres.addThetreToDB();
	theatres = new Theatre("D");
	theatres.addThetreToDB();
	theatres = new Theatre("E");
	theatres.addThetreToDB();
	
	//now add showings for each movie
	
	//showings for hall A
	Showings showings = new Showings("2pm","A","The Grinch");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	
	
	
	showings = new Showings("5pm","A","Robin Hood");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("8pm","A","Instant Family");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	
	//showings for hall B
	showings = new Showings("2pm","B","A Star is Born");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("5pm","B","The Grinch");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("11pm","B","Bohemian Rhapsody");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 	
	
	//showings for hall C 
	showings = new Showings("5pm","C","A Star is Born");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("8pm","C","The Nutcracker and the Four Realms");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("11pm","C","The Grinch");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	
	//showings for hall D
	showings = new Showings("2pm","D","Boy Erased");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("8pm","D","Gremlins");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	
	//showings for hall E
	showings = new Showings("2pm","E","Robin Hood");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	showings = new Showings("11pm","E","The Nutcracker and the Four Realms");
	showings.addShowingToDB();
	for(int i=0; i<20; i++){
		
		Seat seat = new Seat(showings.getSID(),1);
		seat.addSeatToDB();
	
	}//adding 20 seats to this showing and theatre 
	
	
	
	
	
	
	
	
	}
	
}
