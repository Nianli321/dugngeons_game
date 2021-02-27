package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Pane playButton;

    @FXML
    private Text playText;

    @FXML
    private Pane quitButton;

    @FXML
    private ScrollPane anchorLevelSelect;

    @FXML
    private Text quitText;

    @FXML
    private Text mainText;

    @FXML
    private GridPane grid;

    private boolean loadLevel;

    private String dungeon_string;

    Stage stage;

    public MainMenuController(Stage stage) {
        loadLevel = false;
        this.stage = stage;
    }

    /**
     * when clicking on play, change the menu to select dungeon
     * 
     * @param event
     */
    @FXML
    void playHandler(MouseEvent event) {
        if (!loadLevel) {
            playButton.setVisible(false);
            anchorLevelSelect.setVisible(true);
            quitText.setText("Back..");
            mainText.setText("Pick a level");
            loadLevel = true;
        } 
    }

    /**
     * quit button quits program
     * @param event
     */
    @FXML
    void quitHandler(MouseEvent event) {

        if (!loadLevel) {
            // close the application
            stage.hide();
        } else {
            playButton.setVisible(true);
            anchorLevelSelect.setVisible(false);
            quitText.setText("Quit");
            mainText.setText("DUNGEON");
            loadLevel = false;
        }
    }


    /**
     * select the level stored in a button
     * @param dungeon button that is labeled a dungeon
     * @throws IOException
     */
    @FXML
    public void levelSelect(Button dungeon) throws IOException {
        //select
        DungeonWindow newDungeon = new DungeonWindow(stage);
        newDungeon.loadMap(dungeon.getText());
        newDungeon.run();
    }

    /**
     * initialize the scene by loading all dungeon levels into the level list
     */
    @FXML
    void initialize() {
        File[] dungeons = new File("dungeons").listFiles();
        String file_name;
        int count = 0;
        for (File dungeon_name : dungeons) {
            //DONE: create a new item in the list and name it the dungeon name minus .json
            file_name = dungeon_name.getName();
            if (file_name.endsWith(".json")) {
                dungeon_string = file_name;
                //button attempt in grid pane
                Button button = new Button();
                button.setText(file_name);
                button.setOnAction((event) -> {
                    try {
                        this.levelSelect(button);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                this.grid.add(button, 0, count);
                button.setAlignment(Pos.CENTER);
                count++;
            }
        }
        

        
    }
}
