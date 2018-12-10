package app;
/**
 * This class is the main application that will interact with the user by GUI,
 * and that will put all the code together.
 * @author Michael, Camilia, Hua and Yongchao
 *
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecommenderApplicationGUI extends Application {
	
	//List<T> recommendedItems;
	
	public final static int WINDOW_WIDTH = 820;
	public final static int WINDOW_HEIGHT = 600;
	public final static int NB_OF_RECOMMENDED_ITEMS_TO_DISPLAY = 10;

	@Override
	public void init() {
		
	}

	@Override
	public void start(Stage primaryStage) {
		FileBar fileBar = new FileBar(primaryStage);
		//RecommendedItemsDisplay<T> recommendedItemsBox = new RecommendedItemsDisplay<T>();
		
		VBox root = new VBox();
		root.getChildren().add(fileBar); 
		//root.getChildren().add(recommendedItemsBox);
		

		Scene scene= new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		primaryStage.setTitle("Recommendations");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//Get recommendedItems after creating a Recommender thanks to user input 
		//recommendedItems = new ArrayList<T>(); //TODO put value here
		//recommendedItemsBox.SetItems(recommendedItems); //Shows the 10 first recommended items (or less)
		
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}