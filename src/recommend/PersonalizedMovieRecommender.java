package recommend;

import java.util.HashMap;
import java.util.TreeMap;

import interfaces.IRecommender;
import items.Movie;
import items.Rating;

public class PersonalizedMovieRecommender implements IRecommender<Movie> {

	private HashMap<String, Movie> _movieMap;
	private HashMap<String, HashMap<String, String>> _ratingMap;
	private TreeMap<Movie, String> _sortMovieMap;
	private popularMovieRecommender popMovieRec;


	@Override
	public Movie[] recommend(int userId, int n)
	{
		String SimUserId = mostSimUser(userId + "");		
		Movie[] recomendMovies = popMovieRec.recommend(Integer.parseInt(SimUserId), n);		
		return recomendMovies;
	}

	public Movie[] recommend(int userId, int n,String genre)
	{
		String SimUserId = mostSimUser(userId + "");		
		Movie[] recomendMovies = popMovieRec.recommend(Integer.parseInt(SimUserId), n, genre);		
		return recomendMovies;
	}
	public PersonalizedMovieRecommender(Movie[] movies, Rating[] ratings)
	{
		InitMovie(movies);
		InitRating(ratings);
		SortMovies();
		popMovieRec = new popularMovieRecommender(movies, ratings);
	}


	private void SortMovies() {
		_sortMovieMap = new TreeMap<Movie, String>();
		
		for (String key : _movieMap.keySet())
		{
			_sortMovieMap.put(_movieMap.get(key), key);
		}
		
	}


	private void InitRating(Rating[] ratings) {
		_ratingMap = new HashMap<String, HashMap<String, String>>();
		
		for(int i = 0; i< ratings.length; i++)
		{
			HashMap<String, String> movieRating;			
			
			if(!_movieMap.containsKey(ratings[i].getItemId()))
			{
				continue;
			}
			
			Movie putRateValueMovie = _movieMap.get(ratings[i].getItemId());				
			
			putRateValueMovie.AddRate(ratings[i].getName(), ratings[i].getRateValue());
			
			if (!_ratingMap.containsKey(ratings[i].getName()))
			{
				movieRating = new HashMap<String, String>();				
				movieRating.put(ratings[i].getItemId(), ratings[i].getRateValue());
				_ratingMap.put(ratings[i].getName(), movieRating);
			}
			else
			{
				movieRating = _ratingMap.get(ratings[i].getName());
				
				if (!movieRating.containsKey(ratings[i].getItemId()))
				{
					movieRating.put(ratings[i].getItemId(), ratings[i].getRateValue());
				}
			}
		}		

		
	}


	private void InitMovie(Movie[] movies) {
		_movieMap = new HashMap<String, Movie>();

		for(int i = 0; i < movies.length; i++)
		{
			if (!_movieMap.containsKey(movies[i].getItemId()))
			{
				_movieMap.put(movies[i].getItemId(), movies[i]);
			}
			else
			{
				continue;
			}
		}		
	}
	
	private String mostSimUser(String userid)
	{
		String mostSimUserId = null;
		
		double SimMax = 0;
		
		HashMap<String, String> userMovieRating  = _ratingMap.get(userid);		
		
		for(String comparingUserId : _ratingMap.keySet())
		{
			for(String userMovieId : userMovieRating.keySet())
			{
				double accumulating = 0;
				
				for(String compareUserMovieId : _ratingMap.get(comparingUserId).keySet())
				{
					if (userMovieId.equals(compareUserMovieId))
					{
						double userRating = Double.parseDouble(userMovieRating.get(userMovieId));
						double SimUserRating = Double.parseDouble(_ratingMap.get(comparingUserId).get(compareUserMovieId));
						accumulating += (userRating - 2.5)*(SimUserRating - 2.5);
					}
				}
				
				if(accumulating > SimMax)
				{
					SimMax = accumulating;
					mostSimUserId = comparingUserId;
				}
			}
		}
		
		return mostSimUserId;
	}
	
	public boolean hasUserId(String userid)
	{
		return popMovieRec.hasUserId(userid);
	}
	
	public void Test()
	{
		for(String comparingUserId : _ratingMap.keySet())
		{
			System.out.println(comparingUserId + " - " + mostSimUser(comparingUserId));
		}
		
	}
	
}
