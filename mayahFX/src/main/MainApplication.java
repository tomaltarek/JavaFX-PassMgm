package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainApplication extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("/controller/Login.fxml"));
			Scene scene= new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Log in");
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		launch(args);
		
		
		
	}
}
