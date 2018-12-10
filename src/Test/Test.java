package Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import fileio.CSVReader;
import items.Book;
import items.Movie;
import items.Rating;
import javafx.scene.control.Alert;
import recommend.PersonalizedMovieRecommender;
import recommend.popularMovieRecommender;


public class Test {

	public static void main(String[] args) {

		ArrayList<String[]> tempArrayListOfData = new ArrayList<String[]>();
		HashMap<String, Integer> header;
		try {
//			CSVReader movieReader = new CSVReader("datafiles/movie/movies.csv");
//			tempArrayListOfData = movieReader.getData();
//			
//			header = movieReader.getHeader();
//			
//			ArrayList<Movie> movies = new ArrayList<Movie>();
//			
//			for (String[] data : tempArrayListOfData)
//			{
//				movies.add(new Movie(data[header.get("movieId")] ,data[header.get("title")], data[header.get("genres")]));
//			}
			
			//System.out.println(movies.get(100).getItemId() + " " + movies.get(100).getName());
			
			
//			CSVReader ratingReader = new CSVReader("datafiles/movie/ratings.csv");
//			tempArrayListOfData = ratingReader.getData();	
//			
//			header = ratingReader.getHeader();
//			//System.out.println(header);
//			
//			ArrayList<Rating> ratings = new ArrayList<Rating>();
//			
//			for (String[] data : tempArrayListOfData)
//			{
//				ratings.add(new Rating(data[header.get("userId")] ,data[header.get("movieId")], data[header.get("rating")]));
//			}			
			
			//System.out.println(ratings.get(100).getName() + " " + ratings.get(100).getItemId() + " " + ratings.get(100).getRateValue());
			
//			Movie[] movieArray = new Movie[movies.size()];
//			for (int i = 0; i < movies.size(); i++)
//			{
//				movieArray[i] = movies.get(i);
//			}
//			
//			Rating[] ratingArray = new Rating[ratings.size()];
//			for (int i = 0; i < ratings.size(); i++)
//			{
//				ratingArray[i] = ratings.get(i);
//			}
			
//			popularMovieRecommender popMovieRec = new popularMovieRecommender(movieArray, ratingArray);
//			
//			//Movie[] moviesRec = popMovieRec.recommend(652, 10, "Drama");
//			Movie[] moviesRec = popMovieRec.recommend(481, 10);
//			
//			for (int i = 0; i < moviesRec.length; i++)
//			{
//				System.out.println(moviesRec[i].getItemId() + " " + moviesRec[i].getName() + " " + moviesRec[i].getAvgRating());
//				//System.out.println(Arrays.deepToString(moviesRec[i].getGenre()));
//			}
//			
//			PersonalizedMovieRecommender perMovieRec = new PersonalizedMovieRecommender(movieArray, ratingArray);
//			
//			perMovieRec.Test();
			
			CSVReader bookReader = new CSVReader("datafiles/book/books.csv");
			tempArrayListOfData = bookReader.getData();	
			
			header = bookReader.getHeader();

			ArrayList<Movie> _movies = new ArrayList<Movie>();
			
			for (String[] data : tempArrayListOfData)
			{
				String temp = data[header.get("authors")];	
				_movies.add(new Movie(data[header.get("book_id")] ,data[header.get("original_title")], temp.replace("%2C", "\\|")));
			}
			CSVReader ratingReader = new CSVReader("datafiles/book/ratings.csv");
			tempArrayListOfData = ratingReader.getData();	
		
			header = ratingReader.getHeader();
			//System.out.println(header);
		
			if (!header.containsKey("user_id") || !header.containsKey("book_id") || !header.containsKey("rating")) 
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("File reading error");
				alert.setHeaderText("File does not contains the correct data for instantiating the Rating class.");			    
				alert.showAndWait();	
				return;					
			}
		
			ArrayList<Rating> _ratings = new ArrayList<Rating>();
		
			for (String[] data : tempArrayListOfData)
			{
				_ratings.add(new Rating(data[header.get("user_id")] ,data[header.get("book_id")], data[header.get("rating")]));
			}			
		
			//System.out.println(_ratings.size());
			Movie[] movieArray = new Movie[_movies.size()];
			for (int i = 0; i < _movies.size(); i++)
			{
				movieArray[i] = _movies.get(i);
				
			}
			
			Rating[] ratingArray = new Rating[_ratings.size()];
			for (int i = 0; i < _ratings.size(); i++)
			{
				ratingArray[i] = _ratings.get(i);
				
			}
			
//			for (int i = 0; i < _ratings.size(); i++)
//			{	
//				System.out.println(ratingArray[i].getRateValue());
//			}
			
			
			popularMovieRecommender popMovieRec = new popularMovieRecommender(movieArray, ratingArray);
			
			//popMovieRec.Test();
			
			//popMovieRec.Test();
			
//			CSVReader ratingReader = new CSVReader("datafiles/movie/ratings.csv");
//			
//			tempArrayListOfData = ratingReader.getData();
//			
//			header = ratingReader.getHeader();			
//			
//			System.out.println(header);
//			
//			System.out.println(Arrays.toString(tempArrayListOfData.get(0)));
//			
//			tempArrayListOfData.get(0)[3] = "lyc";
//			
//			System.out.println(Arrays.toString(tempArrayListOfData.get(0)));
//			
//			tempArrayListOfData = ratingReader.getData();
//			
//			System.out.println(Arrays.toString(tempArrayListOfData.get(0)));
//			
//			System.out.println(Arrays.toString(tempArrayListOfData.get(1)));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error message: " + e.getMessage());
		}


	}

}
