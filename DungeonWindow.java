package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonWindow {
    private Stage stage;
    private Scene scene;
    private DungeonController dungeonController;
    private DungeonControllerLoader dungeonControllerLoader;

    public DungeonWindow(Stage stage) throws IOException {
        this.stage = stage;
    }

    /**
     * run the current scene
     */
    public void run() {
        stage.setTitle("Dungeon");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * load the dungeon_name in as the new scene
     * @param dungeon_name
     * @throws IOException
     */
    public void loadMap(String dungeon_name) throws IOException {
        dungeonControllerLoader = new DungeonControllerLoader(dungeon_name);
        dungeonController = dungeonControllerLoader.loadController(stage, dungeon_name);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(dungeonController);
        Parent root = loader.load();
        this.scene = new Scene(root);
        root.requestFocus();
    }

    /**
     * load the main menu as the current scene
     * @throws IOException
     */
    public void loadMenu() throws IOException {
        
        MainMenuController controller = new MainMenuController(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));

        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


}