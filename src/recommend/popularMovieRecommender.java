package recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import interfaces.IRecommender;
import items.Movie;

import items.Rating;

public class popularMovieRecommender implements IRecommender<Movie> {

	private HashMap<String, Movie> _movieMap;
	private HashMap<String, HashMap<String, String>> _ratingMap;
	private TreeMap<Movie, String> _sortMovieMap;	
	
	@Override
	public Movie[] recommend(int userId, int n) {
		String userIdStr = userId + "";
		ArrayList<Movie> movieRecommend = new ArrayList<Movie>();
	
		for (Map.Entry<Movie, String> entry : _sortMovieMap.entrySet())
		{
			Movie movie = entry.getKey();
			if (!movie.isRatedByUser(userIdStr))
			{
				movieRecommend.add(movie);
				n--;
			}
			if (n == 0)
			{
				break;
			}
		}
		
		Movie[] movieRecommendArray = new Movie[movieRecommend.size()];
		
		for (int i = 0; i < movieRecommendArray.length; i++)
		{
			try {
				movieRecommendArray[i] = movieRecommend.get(i).deepCopy();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return movieRecommendArray;
	}
	
	public boolean hasUserId(String userid)
	{
		boolean hasUser = false;
		
		for(String index : _ratingMap.keySet())
		{
			if (index.equals(userid))
			{
				hasUser = true;
				break;
			}
			else
			{
				continue;
			}
		}	
		
		return hasUser;
	}

	public Movie[] recommend(int userId, int n, String genre) {
		String userIdStr = userId + "";
		ArrayList<Movie> movieRecommend = new ArrayList<Movie>();
	
		for (Map.Entry<Movie, String> entry : _sortMovieMap.entrySet())
		{
			Movie movie = entry.getKey();
			if (!movie.isRatedByUser(userIdStr) && movie.hasGenre(genre))
			{
				movieRecommend.add(movie);
				n--;
			}
			if (n == 0)
			{
				break;
			}
		}
		
		Movie[] movieRecommendArray = new Movie[movieRecommend.size()];
		
		for (int i = 0; i < movieRecommendArray.length; i++)
		{
			try {
				movieRecommendArray[i] = movieRecommend.get(i).deepCopy();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return movieRecommendArray;
	}
	
	
	public popularMovieRecommender(Movie[] movies, Rating[] ratings)
	{		
		InitMovie(movies);
		InitRating(ratings);		
		SortMovies();
	}

	private void SortMovies() {
		
		_sortMovieMap = new TreeMap<Movie, String>();
		
		for (String key : _movieMap.keySet())
		{
			_sortMovieMap.put(_movieMap.get(key), key);
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
	
	public void Test()
	{
		int i = 0;
		for (Movie key : _sortMovieMap.keySet())
		{
			System.out.println(key.getItemId() + " " + key.getAvgRating());
			if (++i == 50)
			{
				break;
			}
		}
	}

}
