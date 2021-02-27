package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        primaryStage.setTitle("MainMenu");

        MainMenuController controller = new MainMenuController(primaryStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
        
        stage = primaryStage;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
