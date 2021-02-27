package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private BorderPane rootPane;
    
    @FXML
    private GridPane squares;

    @FXML
    private Button exitButton;

    @FXML
    private StackPane gameOverScreen;

    @FXML
    private Text gameOverText;

    @FXML
    private Text treasureCounter;

    @FXML
    private Text keyCounter;

    @FXML
    private ProgressBar swordHealth;

    @FXML
    private ProgressBar potionTime;

    @FXML
    private TextArea goalsTextArea;

    // experiment by Nian
    private Stage s;
    private Scene menuScene;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    private String dungeon_name;

    private ImageView keyView;
    private ImageView swordView;
    private ImageView potionView;
    private ImageView treasureView;
    private ImageView humanHoldsSword;
    private ImageView humanSwing1;
    private ImageView humanSwing2;
    private ImageView humanView;
    private ImageView dragonView;
    private ImageView spikesView;

    private Image dragonImage;

    private Timeline updateTimeline;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, Stage s, String dungeon_name) {
        this.dungeon = dungeon;
        trackComplete(this.dungeon);
        this.player = dungeon.getPlayer();
        trackDeath(this.player);
        this.initialEntities = new ArrayList<>(initialEntities);
        // experiment by Nian
        this.s = s;
        this.dungeon_name = dungeon_name;
        // this.menuScene = menuScene;
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Door) {
                Door d = (Door) e;
                trackDoorOpen(d);
            }
            if (e instanceof Spikes) {
                Spikes spike = (Spikes) e;
                trackSpikesActivated(spike);
            }
        }
        trackNumKey(this.player);
        trackNumTreasure(this.player);
        trackSwordHP(this.player);
        countPotionTime(this.player);
        trackHoldSword(this.player);
        trackStopUpate(this.dungeon);

        treasureView = new ImageView(new Image((new File("images/gold_pile.png")).toURI().toString()));
        swordView = new ImageView(new Image((new File("images/greatsword_1_new.png")).toURI().toString()));
        potionView = new ImageView(new Image((new File("images/brilliant_blue_new.png")).toURI().toString()));
        keyView = new ImageView(new Image((new File("images/key.png")).toURI().toString()));
        humanHoldsSword = new ImageView(new Image((new File("images/human_sword.png")).toURI().toString()));
        humanView = new ImageView(new Image((new File("images/human_new.png")).toURI().toString()));
        humanSwing1 = new ImageView(new Image((new File("images/human_sword_attack1.png")).toURI().toString()));
        humanSwing2 = new ImageView(new Image((new File("images/human_sword_attack2.png")).toURI().toString()));
        dragonImage = new Image((new File("images/dragon.png")).toURI().toString());


        humanView = DungeonControllerLoader.getPlayerImageView();
        for (ImageView imageView: initialEntities) {
            if (imageView.getImage().getUrl().endsWith("dragon.png")) {
                dragonView = imageView;
                animateDragon();
            }
            if (imageView.getImage().getUrl().endsWith("spikes_down.png")) {
                spikesView = imageView;
            }
        }
        

        this.updateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                player.moveEnemies();
                if (player.UnderMe(player) instanceof Enemy) {
                    player.die();
                }
                dungeon.updateGoal();
            }

        }));

        this.updateTimeline.setCycleCount(Timeline.INDEFINITE);
        this.updateTimeline.play();

    }

    /**
     * track if the sword is now being held by the player
     * and load the image of sword in players hand
     * @param player player holding sword
     */
    private void trackHoldSword(Player player) {
        player.getHoldSword().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loadImage("images/human_sword.png", humanView);
            } else {
                loadImage("images/human_new.png", humanView);
            }
        });

    }

    /**
     * animate sword swing
     */
    public void animateSword() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(5);
        timeline.setAutoReverse(false);
        
        //load animation attack 1
        EventHandler <ActionEvent> attack1 = new EventHandler<ActionEvent>() {
            
			@Override
			public void handle(ActionEvent arg0) {
                //load image attack1 into ImageView
				loadImage("images/human_sword_attack1.png", humanView);
			}
            
        };

        //load animation attack2
        EventHandler <ActionEvent> attack2 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
                //load image attack2 into ImageView
				loadImage("images/human_sword_attack2.png", humanView);
			}
            
        };

        EventHandler <ActionEvent> endAttack = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
                //load image attack2 into ImageView
                if (player.getHoldSword().get()) loadImage("images/human_sword.png", humanView);
                else loadImage("images/human_new.png", humanView);
			}
            
        };

        final KeyFrame kf1 = new KeyFrame(Duration.millis(100), attack1);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(200), attack2);

        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.setOnFinished(endAttack);
        if (player.getHoldSword().get()) timeline.play();

    }

    /**
     * animate dragon flapping wings
     */
    public void animateDragon() {
        System.out.print("animating dragon");
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        //load flap 1
        EventHandler <ActionEvent> flap1 = new EventHandler<ActionEvent>() {
            
			@Override
			public void handle(ActionEvent arg0) {
                loadImage("images/dragon.png", dragonView);
			}
            
        };
        //load flap 2
        EventHandler <ActionEvent> flap2 = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
                loadImage("images/dragon2.png", dragonView);
			}
            
        };

        
        final KeyFrame kf1 = new KeyFrame(Duration.millis(100), flap1);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(200), flap2);

        timeline.getKeyFrames().addAll(kf1, kf2);
        timeline.play();

    }

    /**
     * load image into view
     * @param image path of image
     * @param view ImageView that needs to be updated
     */
    public void loadImage(String image, ImageView view) {
        Image newImage = new Image((new File(image)).toURI().toString());
        view.setImage(newImage);
    }
            
    private void trackStopUpate(Dungeon dungeon) {
        dungeon.getIsCompleteBP().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.updateTimeline.stop();
            }
        });
        dungeon.getPlayer().getIsDead().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.updateTimeline.stop();
            }
        });
    }

    /**
     * animate spikes up when stood on
     * @param spikes entity spikes
     */
    public void trackSpikesActivated(Spikes spikes) {
        spikes.getSpikeState().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                loadImage("images/spikes_up.png", spikes.getImageView());
            }
        }
        );
    }
    
    /**
     * add listener to track if door changes to open state
     * @param door door being tracked
     * @param node being tracked
     */
    private void trackDoorOpen(Door door) {
        door.getIsOpenBP().addListener((observable, oldValue, newValue) -> {

            if (newValue) {

                loadImage("images/open_door.png", door.getImageView());
            }
        }
        );
    }

    /**
     * track if dungeon is complete
     * and end game
     * @param dungeon dungeon that is being tracked
     */
    private void trackComplete(Dungeon dungeon) {
        dungeon.getIsCompleteBP().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                updateGameComplete();
                rootPane.setOnKeyPressed(null);
                potionTime.setVisible(false);
            }
        });
    }

    /**
     * track if the player has died
     * and end the game
     * @param player player which is being tracked
     */
    private void trackDeath(Player player) {
        player.getIsDead().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                updateGameOver();
                rootPane.setOnKeyPressed(null);
                potionTime.setVisible(false);
            }
        });
    }

    /**
     * track the number of keys on a player
     * and display it
     * @param player player which is being tracked
     */
    @FXML
    private void trackNumKey(Player player) {
        player.getNumKeyP().addListener((observable, oldValue, newValue) -> {
                this.keyCounter.setText(newValue.toString());

        });
    } 

    /**
     * track the number of treasure on a player
     * and display it
     * @param player player which is being tracked
     */
    @FXML
    private void trackNumTreasure(Player player) {
        player.getNumTreasureP().addListener((observable, oldValue, newValue) -> {
        
                this.treasureCounter.setText(newValue.toString());
        });
    }

    /**
     * track the amount of sword hits carried by player
     * and display it
     * @param player player which is being tracked
     */
    @FXML
    private void trackSwordHP(Player player) {
        player.getSwordHP().addListener((observable, oldValue, newValue) -> {
            this.swordHealth.setProgress((newValue.doubleValue()/5));
        });
    }

    /**
     * track the amount of time the potion will last
     * and display it
     * @param player player which is being tracked
     */
    @FXML
    private void countPotionTime(Player player) {
        player.getIsInviciableBP().addListener((observable, oldValue, newValue) -> {
        
            if (newValue) {
                IntegerProperty timeSeconds = new SimpleIntegerProperty(20*100);
                this.potionTime.progressProperty().bind(timeSeconds.divide(20*100.0));
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(25+1), new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
            }
        });
    }

    /**
     * initialize
     * load the ground first the size of the dungeon
     */
    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                ImageView square = new ImageView(ground);
                square.setViewOrder(ZLayer.FLOOR.getZIndex());
                squares.add(square, x, y);

            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        
        goalsTextArea.setText(setGoalString(dungeon.getGoal()));

    }

    public String setGoalString(Goal goal) {
        String text = "";
        if (goal instanceof ComplexGoal) {
            ComplexGoal cg = (ComplexGoal) goal;
            text = cg.getGoal() + ":";
            for (Goal subgoal: cg.getSubGoals()) {
                text = text + "\n" + setGoalString(subgoal);
            }
        } else {
            text = " - " + goal.getGoal();
        }
        return text;
    }

    /**
     * handle press of keys for game interaction
     * @param event key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {

        switch (event.getCode()) {
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case SPACE:
                player.attack();
                animateSword();
            default:
                break;
        }
        
    }

    /**
     * pop up game over screen
     */
    @FXML
    public void updateGameOver() {
        gameOverText.setText("Game Over");
        gameOverScreen.setVisible(true);
    }

    /**
     * pop up game complete screen
     */
    @FXML
    public void updateGameComplete() {
        if (dungeon.getIsComplete()) {
            gameOverScreen.setVisible(true);
        }
    }

    /**
     * exit the game when button pressed
     */
    @FXML
    public void exitHandler() throws IOException {
        DungeonWindow dw = new DungeonWindow(s);
        dw.loadMenu();
    }

    /**
     * restart the dungeon when button pressed
     */
    @FXML
    void restartHandle(ActionEvent event) throws IOException {
        DungeonWindow dw = new DungeonWindow(s);
        dw.loadMap(dungeon_name);
        dw.run();
    }
}

