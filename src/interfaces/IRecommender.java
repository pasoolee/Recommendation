package interfaces;

public interface IRecommender <T> {
	
	T[] recommend(int userId, int n);
	
}
