package items;

import interfaces.Item;

public class Book implements Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8746681141088159327L;
	private String _bookId;
	private String _name;
	private String _isbn;
	private String _year;
	private String[] _authors;
	
	@Override
	public String getName() 
	{		
		return this._name;
	}

	@Override
	public String getItemId() 
	{		
		return this._bookId;
	}
	
	public String getYear() 
	{		
		return this._year;
	}
	
	public String getISBN() 
	{		
		return this._isbn;
	}	
	
	public String[] getAuthors()
	{
		String[] returnAuthorArray = new String[_authors.length];
		
		for (int i = 0; i < _authors.length; i++)
		{
			returnAuthorArray[i] = _authors[i];
		}
		
		return returnAuthorArray;
	}
	
	public Book(String bookId, String name, String isbn, String year, String authors)
	{
		this._bookId = bookId;
		this._name = name;
		this._isbn = isbn;		
		
		if ( year.length() > 4)
		{			
			year = year.substring(0, 4);
		}			
		
		this._year = year;
		
		authors = authors.replaceAll("%2C", ",");
		
		_authors = authors.split(",");
	}

}
