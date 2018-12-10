package items;

import interfaces.Item;

public class Rating implements Item {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2609826289405400096L;
	private String _id;
	private String _itemId;
	private String _rateValue;
	
	//private HashMap<String, ArrayList<Item>> _ratedItems;

	@Override
	public String getName() {
		
		return this._id;
	}

	@Override
	public String getItemId() {
		
		return this._itemId;
	}
		
	public String getRateValue()
	{
		return this._rateValue;
	}
	
	public Rating(String id, String itemId, String rateValue)
	{
		this._id = id;
		this._itemId = itemId; 
		this._rateValue = rateValue;
	}


}
