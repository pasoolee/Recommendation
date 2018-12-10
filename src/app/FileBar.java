package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import fileio.CSVReader;
import interfaces.Item;
import items.Movie;
import items.Rating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import recommend.PersonalizedMovieRecommender;
import recommend.popularMovieRecommender;



public class FileBar extends VBox {
	//Book[] books;
	ArrayList<Movie> _movies;
	Item[] items;
	ArrayList<Rating> _ratings;
	TextField textItems;
	TextField textRatings;
	TextField textUserId;
	ComboBox<String> itemList;
	ComboBox<String> itemOptions;
	ComboBox<String> generofMovie;
	GridPane fourthLine;
	String originPathOfItems;
	String originPathOfRatings;
	Movie[] _results;
	ListView<String> displayResults;
	Button btLoad;
	final ToggleGroup recChoice = new ToggleGroup();
	final ToggleGroup ratingChoice = new ToggleGroup();
	//PersonalizedRecommender<Book> prbooks;
	//PersonalizedMovieRecommender prmovie;
	
	
	public FileBar(Stage stage) 
	{
		//first line
//		GridPane firstLine = new GridPane();    
//			
//		firstLine.setPadding(new Insets(10, 10, 0, 10)); 
//		
//		firstLine.setVgap(5); 
//		firstLine.setHgap(5); 
//		
//		
//
//		RadioButton fs1 = new RadioButton("GoodReadsFileReader");
//		fs1.setToggleGroup(loadChoice);
//		fs1.setSelected(true);
//
//		RadioButton fs2 = new RadioButton("MovieLensFileReader");
//		fs2.setToggleGroup(loadChoice);
//		 
//		RadioButton fs3 = new RadioButton("Other");
//		fs3.setToggleGroup(loadChoice);
//		
//		firstLine.add(fs1, 1, 0); 	
//		firstLine.add(fs2, 2, 0); 	
//		firstLine.add(fs3, 3, 0); 	
		
		//second line
		GridPane secondLine = new GridPane();    
		
		
		
		secondLine.setPadding(new Insets(10, 10, 0, 10)); 
		
		secondLine.setVgap(5); 
		secondLine.setHgap(5);    
	
		
		
		Label labelOfItem = new Label("Items:");
		labelOfItem.setPrefWidth(50);
		secondLine.add(labelOfItem, 0, 0); 	
		
		textItems = new TextField("Click to select file");
		textItems.setPrefWidth(200);
		textItems.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				textItems.clear();
				
				FileChooser fileChooser = new FileChooser();
				
				fileChooser.setInitialDirectory(new File("."));
				
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null) 
				{
					textItems.setText(file.getAbsolutePath());
				}
			}
		});
		secondLine.add(textItems, 2, 0); 
		
        itemOptions = new ComboBox<String>();
        itemOptions.getItems().add("TYPE");
        itemOptions.getItems().add("Books");
        itemOptions.getItems().add("Movies");
        //itemOptions.setPrefWidth(60);
        itemOptions.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				//System.out.println(itemOptions.getValue());
				if (itemOptions.getValue().equals("Books")) {
					//textItems.setText("datafiles\\books\\books.csv");
					//textRatings.setText("datafiles\\books\\ratings.csv");
					
					itemList.getItems().clear();
					
					fourthLine.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 5);
					fourthLine.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == 4);
					
				}
				else if (itemOptions.getValue().equals("Movies")) {
					//textItems.setText("datafiles/movie/movies.csv");
					//textRatings.setText("datafiles/movie/ratings.csv");
					generofMovie = new ComboBox<String>();
					Label labelOfGenre = new Label("genre:");
					labelOfGenre.setPrefWidth(50);
					
					itemList.getItems().clear();
					
					fourthLine.add(labelOfGenre, 4, 0); 
					fourthLine.add(generofMovie, 5, 0); 
				}
				
			}
        	
        });
        itemOptions.getSelectionModel().select(0);
        secondLine.add(itemOptions, 1, 0);
        
		
		Label labelOfRatings = new Label("Ratings:");
		//labelOfRatings.setPrefWidth(50);
		secondLine.add(labelOfRatings, 3, 0); 
		
		textRatings = new TextField("Click to select file");
		textRatings.setPrefWidth(200);
		textRatings.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				textRatings.clear();
				
				FileChooser fileChooser = new FileChooser();
				
				fileChooser.setInitialDirectory(new File("."));
				
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null) 
				{
					textRatings.setText(file.getAbsolutePath());
				}
			}
		});
		secondLine.add(textRatings, 4, 0); 
		
		btLoad = new Button("Load");
		btLoad.setPrefWidth(60);
		secondLine.add(btLoad, 5, 0); 		
		btLoad.setOnAction(this::btLoadClick);	
				
		Button btSave = new Button("Save");
		btSave.setPrefWidth(60);
		secondLine.add(btSave, 6, 0); 
		btSave.setOnAction(this::btSaveClick);	
		
		//third line
		GridPane thirdLine = new GridPane();   
		thirdLine.setPadding(new Insets(10, 10, 0, 10)); 		
		thirdLine.setVgap(5); 
		thirdLine.setHgap(5);    		
		
		Label labelOfUserId = new Label("User Id:");
		labelOfUserId.setPrefWidth(50);
		thirdLine.add(labelOfUserId, 0, 0); 	

		textUserId = new TextField();
		textUserId.setPrefWidth(88);
		thirdLine.add(textUserId, 1, 0); 

		Label labelOfItemsList = new Label("Item list:");
		//labelOfItemsList.setPrefWidth(50);
		thirdLine.add(labelOfItemsList, 2, 0); 	
		
		
        itemList = new ComboBox<String>();
        itemList.setPrefWidth(240);
        thirdLine.add(itemList, 3, 0);
        
        
		RadioButton rt1 = new RadioButton("0");
		rt1.setToggleGroup(ratingChoice);

		RadioButton rt2 = new RadioButton("1");
		rt2.setToggleGroup(ratingChoice);
		 
		RadioButton rt3 = new RadioButton("2");
		rt3.setToggleGroup(ratingChoice);
		
		RadioButton rt4 = new RadioButton("3");
		rt4.setToggleGroup(ratingChoice);
		rt4.setSelected(true);
		
				
		RadioButton rt5 = new RadioButton("4");
		rt5.setToggleGroup(ratingChoice);
		
		RadioButton rt6 = new RadioButton("5");
		rt6.setToggleGroup(ratingChoice);		
		
		thirdLine.add(rt1, 4, 0); 	
		thirdLine.add(rt2, 5, 0); 	
		thirdLine.add(rt3, 6, 0); 	
		thirdLine.add(rt4, 7, 0); 	
		thirdLine.add(rt5, 8, 0); 
		thirdLine.add(rt6, 9, 0); 
        
		Button btAddRating = new Button("Add Rating");
		//btAddRating.setPrefWidth(100);
		thirdLine.add(btAddRating, 10, 0); 
		btAddRating.setOnAction(this::btAddRatingClick);	
        
		//fourth line		
		fourthLine = new GridPane();   
		fourthLine.setPadding(new Insets(10, 10, 0, 10)); 		
		fourthLine.setVgap(5); 
		fourthLine.setHgap(5);  
		
		Label labelOfR = new Label("User Id:");
		labelOfR.setPrefWidth(50);
		fourthLine.add(labelOfR, 0, 0); 
		
		RadioButton popr = new RadioButton("Popular");
		popr.setToggleGroup(recChoice);		
		popr.setSelected(true);

		RadioButton perr = new RadioButton("Personal");
		perr.setToggleGroup(recChoice);	
		
		fourthLine.add(popr, 1, 0); 	
		fourthLine.add(perr, 2, 0); 	
		
		Button btReccommend = new Button("Recommned");
		//btReccommend.setPrefWidth(50);
		fourthLine.add(btReccommend, 3, 0); 		
		btReccommend.setOnAction(this::btReccommendClick);
		
		//fifth line
		displayResults = new ListView<String>();
		displayResults.setEditable(false);
		displayResults.setPrefHeight(480);
		
		VBox fifth = new VBox();   
		
		
		fifth.getChildren().add(displayResults);
		
		//this.getChildren().add(firstLine);
		this.getChildren().add(secondLine);
		this.getChildren().add(thirdLine);
		this.getChildren().add(fourthLine);
		this.getChildren().add(fifth);
		


	}
	
	/**
	 * This event handler handle the event of load button to load the item file and the rating file.
	 * @param e
	 */
	public void btLoadClick(ActionEvent e) {
		//System.out.println(textItems.getText() + " " + textItems.getText().length());
		//System.out.println(textRatings.getText() + " " + textRatings.getText().length());
	
		if (itemOptions.getValue().equals("Books")) {
			try 
			{
				ArrayList<String[]> tempArrayListOfData = new ArrayList<String[]>();
				HashMap<String, Integer> header;
				CSVReader movieReader = new CSVReader(textItems.getText());
			
				tempArrayListOfData = movieReader.getData();
			
				header = movieReader.getHeader();
			
			
			
				if (!header.containsKey("book_id") || !header.containsKey("original_title") || !header.containsKey("authors")) 
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("File reading error");
					alert.setHeaderText("File does not contains the correct data for instantiating the Book class.");			    
					alert.showAndWait();	
					return;					
				}			
				
				_movies = new ArrayList<Movie>();
			
				for (String[] data : tempArrayListOfData)
				{
					String temp = data[header.get("authors")];				
					_movies.add(new Movie(data[header.get("book_id")] ,data[header.get("original_title")], temp.replace("%2C", "\\|")));
				}
			
			
			
				CSVReader ratingReader = new CSVReader(textRatings.getText());
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
			
				_ratings = new ArrayList<Rating>();
			
				for (String[] data : tempArrayListOfData)
				{
					_ratings.add(new Rating(data[header.get("user_id")] ,data[header.get("book_id")], data[header.get("rating")]));
				}			
			
				//System.out.println(_ratings.size());
			
				originPathOfItems = textItems.getText();
				originPathOfRatings = textRatings.getText();
			
				itemList.getItems().clear();
			
				TreeSet<String> genres = new  TreeSet<String>();
				for (int i = 0; i< _movies.size(); i++)
				{ 
					itemList.getItems().add(_movies.get(i).getItemId() + "." + _movies.get(i).getName());    		    		
					
					for (int j = 0; j < _movies.get(i).getGenre().length; j++)
					{
						if (!_movies.get(i).getGenre()[j].contains("no genres"))
						{
							genres.add(_movies.get(i).getGenre()[j]);		    			
						}
					}		    		
				}	    	

				itemList.getSelectionModel().select(0);
	    	
			}
			catch (IOException e1) 
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("File reading error");
				alert.setHeaderText("Please type the correct file path.");			    
				alert.showAndWait();	
				return;
			}
		}
		else if (itemOptions.getValue().equals("Movies")) {
			try 
			{
				ArrayList<String[]> tempArrayListOfData = new ArrayList<String[]>();
				HashMap<String, Integer> header;
				CSVReader movieReader = new CSVReader(textItems.getText());
				
				tempArrayListOfData = movieReader.getData();
				
				header = movieReader.getHeader();
				
				if (!header.containsKey("movieId") || !header.containsKey("title") || !header.containsKey("genres")) 
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("File reading error");
				    alert.setHeaderText("File does not contains the correct data for instantiating the Movie class.");			    
				    alert.showAndWait();	
				    return;					
				}
				
				_movies = new ArrayList<Movie>();
				
				for (String[] data : tempArrayListOfData)
				{
					_movies.add(new Movie(data[header.get("movieId")] ,data[header.get("title")], data[header.get("genres")]));
				}

				CSVReader ratingReader = new CSVReader(textRatings.getText());
				tempArrayListOfData = ratingReader.getData();	
				
				header = ratingReader.getHeader();
				//System.out.println(header);
				
				if (!header.containsKey("userId") || !header.containsKey("movieId") || !header.containsKey("rating")) 
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("File reading error");
				    alert.setHeaderText("File does not contains the correct data for instantiating the Rating class.");			    
				    alert.showAndWait();	
				    return;					
				}
				
				_ratings = new ArrayList<Rating>();
				
				for (String[] data : tempArrayListOfData)
				{
					_ratings.add(new Rating(data[header.get("userId")] ,data[header.get("movieId")], data[header.get("rating")]));
				}			
				
				
				originPathOfItems = textItems.getText();
				originPathOfRatings = textRatings.getText();
				
				itemList.getItems().clear();
				
				TreeSet<String> genres = new  TreeSet<String>();
		    	for (int i = 0; i< _movies.size(); i++)
		    	{ 
		    		itemList.getItems().add(_movies.get(i).getItemId() + "." + _movies.get(i).getName());    		    		
		    		
		    		for (int j = 0; j < _movies.get(i).getGenre().length; j++)
		    		{
		    			if (!_movies.get(i).getGenre()[j].contains("no genres"))
		    			{
		    				genres.add(_movies.get(i).getGenre()[j]);		    			
		    			}
		    		}		    		
		    	}

		    	itemList.getSelectionModel().select(0);
		    	

		    	generofMovie.getItems().add("All");
		    	generofMovie.getItems().addAll(genres);
		    	generofMovie.getSelectionModel().select(0);
		    	
		    } 
			catch (IOException e1) 
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
			    alert.setTitle("File reading error");
			    alert.setHeaderText("Please type the correct file path.");			    
			    alert.showAndWait();	
			    return;
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Item's type is unknow");
		    alert.setHeaderText("Please select the type of items for recommendation.");		   
		    alert.showAndWait();	
		    return;
		}
		

	}
	
	/**
	 * This event handler handle the event of load button to save item and rating into a new files.
	 * @param e
	 */
	public void btSaveClick(ActionEvent e) {
//		//System.out.println(e.getSource());
//		Alert bf = new Alert(AlertType.WARNING, 
//		            "Are you sure to save to the CSV file to the location\n which shows on the text field?",
//		             ButtonType.OK, 
//		             ButtonType.CANCEL);
//		bf.setTitle("Data will be overwrited");
//		Optional<ButtonType> bfresult = bf.showAndWait();
//
//		if (bfresult.get() != ButtonType.OK) {
//		    return;
//		}
//		if (itemList.getItems().size() != 0)
//		{
//			if (itemOptions.getValue().equals("Movies")) 
//			{
//				try {
//					MovieLensFileReader.saveToFile(movies, textItems.getText(), "movieId,title,genres");
//				} catch (FileNotFoundException e1) {
//					Alert alert = new Alert(Alert.AlertType.ERROR);
//				    alert.setTitle("File reading error");
//				    alert.setHeaderText("File not found.");			    
//				    alert.showAndWait();	
//				    return;
//				} catch (IOException e1) {
//					Alert alert = new Alert(Alert.AlertType.ERROR);
//				    alert.setTitle("File reading error");
//				    alert.setHeaderText("IO Exception.");			    
//				    alert.showAndWait();	
//				    return;
//				}
//			}
//			else if (itemOptions.getValue().equals("Books")) 
//			{
//				try {
//					MovieLensFileReader.saveToFile(books, textItems.getText(), "book_id,goodreads_book_id,best_book_id,work_id,books_count,isbn,isbn13,authors,original_publication_year,original_title,title,language_code,average_rating,ratings_count,work_ratings_count,work_text_reviews_count,ratings_1,ratings_2,ratings_3,ratings_4,ratings_5,image_url,small_image_url");
//					MovieLensFileReader.saveToFile(ratings, textRatings.getText(), "userId,movieId,rating,timestamp");
//				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//
//			
//			
//		}
//		else
//		{
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//		    alert.setTitle("Data error");
//		    alert.setHeaderText("Please load data first.");
//		    //alert.setContentText("I have a great message for you!");
//		    alert.showAndWait();		
//		    return;			
//		}
	}	
	
	/**
	 * This event handler handle the event of load button to add new rating record in rating file.
	 * @param e
	 */
	public void btAddRatingClick(ActionEvent e) {
//		Alert bf = new Alert(AlertType.WARNING, 
//	            "Are you sure to append a new rating record in the rating file?",
//	             ButtonType.OK, 
//	             ButtonType.CANCEL);
//		
//		bf.setTitle("Add new rating record");
//		Optional<ButtonType> bfresult = bf.showAndWait();
//
//		if (bfresult.get() != ButtonType.OK) {
//			return;
//		}
//		if (textUserId.getText().length() == 0)
//		{
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//		    alert.setTitle("User ID has error");
//		    alert.setHeaderText("Please input a user ID.");
//		    //alert.setContentText("I have a great message for you!");
//		    alert.showAndWait();		
//		    return;
//		    
//		} else {
//			try 
//	        { 
//	            // checking valid integer using parseInt() method 
//	            Integer.parseInt(textUserId.getText()); 
//	            
//	        }  
//	        catch (NumberFormatException errorNumeric)  
//	        { 
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//			    alert.setTitle("User ID has error");
//			    alert.setHeaderText("User ID is not a number.");
//			    //alert.setContentText("I have a great message for you!");
//			    alert.showAndWait();		
//			    return;
//	        } 
//			RadioButton chk = (RadioButton)ratingChoice.getSelectedToggle(); 
//			String itemId = itemList.getValue();
//			itemId = itemId.substring(0, itemId.indexOf('.'));
//			Rating toAdd = new Rating(textUserId.getText(),itemId ,chk.getText());
//			String addnewRating = toAdd.toRawString();
//			
//			try {
//			    Files.write(Paths.get(originPathOfRatings), ('\n'+addnewRating).getBytes(), StandardOpenOption.APPEND);
//			}catch (IOException e2) {
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//			    alert.setTitle("add rating error");
//			    alert.setHeaderText("Please check the file path is correct");
//			    
//			    alert.showAndWait();
//			    return;
//			}
//			if (itemOptions.getValue().equals("Books") && ((RadioButton)recChoice.getSelectedToggle()).getText().equals("Personal"))
//			{
//				//System.out.println("<"+addnewRating+ "> - " + toAdd.getItemId());
//				
//				prbooks.addNewRating(toAdd);
//			} 
//			else if (itemOptions.getValue().equals("Movies") && ((RadioButton)recChoice.getSelectedToggle()).getText().equals("Personal"))
//			{
//				//addnewRating += ", ";
//				//System.out.println(addnewRating);
//				
//				prmovie.addNewRating(toAdd);
//			}
//			else
//			{
//				textItems.setText(originPathOfItems);
//				textRatings.setText(originPathOfRatings);
//				btLoad.fire();
//				
//			}
//			
//			 
//			  
//			//System.out.println(textUserId.getText()+","+ itemId +","+chk.getText());			
//		}
	}	
	
	
	/**
	 * This event handler handle the event of load button to give the recommendation back to the user.
	 * @param e
	 */
	public void btReccommendClick(ActionEvent e) {
		
		if (itemList.getItems().size() != 0)
		{
			RadioButton chk = (RadioButton)recChoice.getSelectedToggle(); 

			if (itemOptions.getValue().equals("Movies")) 
			{
					
				if (textUserId.getText().length() == 0)
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("User ID Error");
				    alert.setHeaderText("Please input a user ID.");
				    //alert.setContentText("I have a great message for you!");
				    alert.showAndWait();
					return;
					    
				} else {
					int userId = 0;
					try 
			        { 
			            // checking valid integer using parseInt() method 
						userId = Integer.parseInt(textUserId.getText()); 
			            
			        }  
			        catch (NumberFormatException errorNumeric)  
			        { 
						Alert alert = new Alert(Alert.AlertType.ERROR);
					    alert.setTitle("User ID Error");
					    alert.setHeaderText("User ID is not a number.");
					    //alert.setContentText("I have a great message for you!");
					    alert.showAndWait();	
					    return;
			        }
					

					
					if (chk.getText().equals("Popular"))
					{
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
						
						popularMovieRecommender popMovieRec = new popularMovieRecommender(movieArray, ratingArray);
						
						if(!popMovieRec.hasUserId(textUserId.getText()))
						{
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
						    alert.setTitle("User ID Error");
						    alert.setHeaderText("User ID does not exist in rating file.");
						    alert.setContentText("The result did not consider the effect of user ID.");
						    alert.showAndWait();
							
						}
						
						if (generofMovie.getValue().equals("All"))
						{
							_results = popMovieRec.recommend(userId, 50);
						}
						else
						{
							_results = popMovieRec.recommend(userId, 50, generofMovie.getValue());
						}

					}
					else if (chk.getText().equals("Personal"))
					{
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
						
						PersonalizedMovieRecommender perMovieRec = new PersonalizedMovieRecommender(movieArray, ratingArray);
						
						if(!perMovieRec.hasUserId(textUserId.getText()))
						{
							Alert alert = new Alert(Alert.AlertType.ERROR);
						    alert.setTitle("User ID Error");
						    alert.setHeaderText("User ID does not exist in rating file.");
						    alert.setContentText("Failure to find a simular user.");
						    alert.showAndWait();
							return;
						}
						
						if (generofMovie.getValue().equals("All"))
						{
							_results = perMovieRec.recommend(userId, 50);
						}
						else
						{
							_results = perMovieRec.recommend(userId, 50, generofMovie.getValue());
						}
						
						
					}			
						
						

						
						
					ObservableList<String> forDisplay = FXCollections.observableArrayList();
						
					for (int i = 0; i < _results.length; i++)
					{
						String ratingValue = _results[i].getAvgRating() + "";
						
						if (ratingValue.length() > 3)
						{
							ratingValue = ratingValue.substring(0, 4);
						}
						
						forDisplay.add(_results[i].getItemId() + " " + _results[i].getName() + " [AVG Rating: " + ratingValue + "] " + Arrays.deepToString(_results[i].getGenre()) + " [" + _results[i].getYear() + "]");
					}
						
						
					displayResults.setItems(forDisplay);
						
				}
					
			}
			else if (itemOptions.getValue().equals("Books"))
			{
				if (textUserId.getText().length() == 0)
				{
					Alert alert = new Alert(Alert.AlertType.ERROR);
				    alert.setTitle("User ID Error");
				    alert.setHeaderText("Please input a user ID.");
				    //alert.setContentText("I have a great message for you!");
				    alert.showAndWait();	
				    return;
				    
				} else {
					int userId = 0;
					
					try 
					{ 
			            // checking valid integer using parseInt() method 
						userId = Integer.parseInt(textUserId.getText()); 
				            
			        }  
			        catch (NumberFormatException errorNumeric)  
			        { 
						Alert alert = new Alert(Alert.AlertType.ERROR);
					    alert.setTitle("User ID Error");
					    alert.setHeaderText("User ID is not a number.");
					    //alert.setContentText("I have a great message for you!");
					    alert.showAndWait();	
					    return;
			        }
					if(!isOkUserId(textUserId.getText()))
					{
						Alert alert = new Alert(Alert.AlertType.ERROR);
					    alert.setTitle("User ID Error");
					    alert.setHeaderText("User ID does not exist in rating file.");
					    //alert.setContentText("I have a great message for you!");
					    alert.showAndWait();
						return;
					}						
					

					
					if (chk.getText().equals("Popular"))
					{
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
						
						popularMovieRecommender popMovieRec = new popularMovieRecommender(movieArray, ratingArray);
						
						//popMovieRec.Test();
						
						if(!popMovieRec.hasUserId(textUserId.getText()))
						{
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
						    alert.setTitle("User ID Error");
						    alert.setHeaderText("User ID does not exist in rating file.");
						    alert.setContentText("The result did not consider the effect of user ID.");
						    alert.showAndWait();
							
						}

						_results = popMovieRec.recommend(userId, 50);
						
					}
					else if (chk.getText().equals("Personal"))
					{						
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
						
						PersonalizedMovieRecommender perMovieRec = new PersonalizedMovieRecommender(movieArray, ratingArray);
						
						if(!perMovieRec.hasUserId(textUserId.getText()))
						{
							Alert alert = new Alert(Alert.AlertType.ERROR);
						    alert.setTitle("User ID Error");
						    alert.setHeaderText("User ID does not exist in rating file.");
						    alert.setContentText("Failure to find a simular user.");
						    alert.showAndWait();
							return;
						}
						
						_results = perMovieRec.recommend(userId, 50);
						
					}					
						
					ObservableList<String> forDisplay = FXCollections.observableArrayList();
						
					for (int i = 0; i < _results.length; i++)
					{
						String ratingValue = _results[i].getAvgRating() + "";
						
						if (ratingValue.length() > 3)
						{
							ratingValue = ratingValue.substring(0, 4);
						}
						forDisplay.add(_results[i].getItemId() + "." + _results[i].getName() + " [Avg Rating: " +  ratingValue + "]");
					}						
						
					displayResults.setItems(forDisplay);						
				}					
			}		
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Item list is empty");
		    alert.setHeaderText("Please add rated items first.");			    
		    alert.showAndWait();
		    return;			
		}
	}	
	
	/**
	 * To check the user ID is within the rating file.
	 * @param userid
	 * @return If there is a ID matching the userid, the return is true. If there is no ID matching the userid, the rturn is false;
	 */
	private boolean isOkUserId(String userid) {
		boolean result = true;
//		
//		for (int i = 0; i < ratings.length; i++)
//		{
//			if(ratings[i].getUserId().equals(userid))
//			{
//				result = true;
//				break;
//			}
//		}
//		
		return result;
	}
	
}
