package items;

import java.util.Comparator;

public class MovieCompare implements Comparator<Movie> {

	@Override
	public int compare(Movie movieLeft, Movie movieRight) {
		
		int comparingValue = 0;
		
		if (movieLeft.getAvgRating() - movieRight.getAvgRating() > 0)
		{
			comparingValue = -1;
		}
		else if (movieLeft.getAvgRating() - movieRight.getAvgRating() < 0)
		{
			comparingValue = 1;
		}
		
		return comparingValue;
	}

}
