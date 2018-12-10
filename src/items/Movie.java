package items;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import interfaces.Item;


public class Movie implements Item, Comparable<Movie> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -325980760135739197L;
	
	private String _name;
	private String _id;	
	private String _year;
	private double _avgRating;
	
	private TreeSet<String> _genres;
	private HashMap<String, Double> _ratingData;

	@Override
	public String getName() {
		
		return _name;
	}

	@Override
	public String getItemId() {
		
		return _id;
	}
	
	public String getYear()
	{
		return this._year;
	}
	
	public String[] getGenre()
	{
		String[] genres = new String[this._genres.size()];		
		
		Iterator<String> itData = this._genres.iterator();
		
		int i = 0;
		
		while (itData.hasNext())
		{
			genres[i++] = itData.next();
		}
		
		return genres;
	}
	
//	public HashMap<String, Double> getRatingData()
//	{
//		return _ratingData;
//	}
	
	public double getAvgRating()
	{
		return this._avgRating;
	}
	
	public void AddRate(String userId, String rate)
	{
		this._ratingData.put(userId, Double.parseDouble(rate));
		
		//System.out.println("AddRate " + userId +" "+ rate);
		
		double avgRating = 0;
		
		if (_ratingData.size() > 0)
		{
			double sum = 0;
			
			for (Map.Entry<String, Double> entry : _ratingData.entrySet())
			{
				sum += entry.getValue();
			}
			
			avgRating = sum / _ratingData.size();
		}
		
		this._avgRating = avgRating;
	}
	
	public boolean isRatedByUser(String userId)
	{
		boolean compareResult = false;
		
		if (_ratingData.containsKey(userId))
		{
			compareResult = true;
		}
		
		return compareResult;
	}
	
	public boolean hasGenre(String genre)
	{
		boolean compareResult = false;
		
		if (_genres.contains(genre))
		{
			compareResult = true;
		}
		
		return compareResult;
	}
	
    public Movie deepCopy() throws Exception
    {
        //Serialization of object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(this);
 
        //De-serialization of object
        ByteArrayInputStream bis = new   ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        Movie copied = (Movie) in.readObject();
 
        //Verify that object is not corrupt
 
        //validateNameParts(fName);
        //validateNameParts(lName);
 
        return copied;
    }
	
	public Movie(String id, String name, String genres)
	{
		this._id = id;

		name = name.replaceAll("%2C", ",");
		
		this._name = CutName(name);
		
		InitGenre(genres); 
		
		_ratingData = new HashMap<String, Double>();
	}
	
	private String CutName(String name)
	{
		String movieName = "CSV COLUMN ERROR";
		
		if (name.length() != 0)
		{
			if (name.startsWith("\"") && name.endsWith("\""))
			{				
				name = name.substring(1, name.length()-1);
				name = name.replaceAll("\"+", "\"");				
			}
			
			if (name.contains("(") && name.contains(")"))
			{
				int cutPosition = name.lastIndexOf("(");
				
				this._year = name.substring(cutPosition).trim();
				
				this._year = this._year.substring(1, this._year.length()-1);
				
				movieName = name.substring(0, cutPosition).trim();
				
			}
			else
			{
				movieName = name.trim();
				this._year = "NO DATA";
			}
			
			if (this._year.length() == 9)
			{
				String[] years = this._year.split("-");
				this._year = years[0];				
			}			
		}
		
		return movieName;
	}
	
	private void InitGenre(String genres) 
	{
		this._genres = new TreeSet<String>();
		
		String[] genreArray = genres.split("\\|");
		
		for(int i = 0; i< genreArray.length; i++)
		{
			this._genres.add(genreArray[i]);
		}
	}

	@Override
	public int compareTo(Movie movieCompareTo) {
		int comparingValue = 0;
		
		if (this.getAvgRating() - movieCompareTo.getAvgRating() > 0)
		{
			comparingValue = -1;
		}
		else if (this.getAvgRating() - movieCompareTo.getAvgRating() < 0)
		{
			comparingValue = 1;
		}
		
		return comparingValue;
	}
	
}
