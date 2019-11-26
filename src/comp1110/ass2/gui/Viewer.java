package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static comp1110.ass2.RailroadInk.*;

/**
 * A very simple viewer for tile placements in the Railroad Ink game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various tile placements.
 */
public class Viewer extends Application {
    /* board layout */
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;

    private static final String URI_BASE = "assets/";

    private final Group home = new Group();
    private final Group root = new Group();
    private final Group tilesInBoard = new Group();
    private final Group controls = new Group();
    private final Group randomTilesGroup = new Group();
    private final Group specialTilesGroup = new Group();
    private final Group rootAI = new Group();
    private final Group tilesInBoardAI = new Group();
    TextField textField;

    private String diceRoll = "";
    private String totalPlacement = "";
    private String totalPlacementAI = "";

    private int round = 0;
    private HBox showRoundBox = new HBox();
    private boolean canUseSpecialTiles = false;
    private int usedSpecialTiles = 0;

    private TileView randomTile1 = new TileView();
    private TileView randomTile2 = new TileView();
    private TileView randomTile3 = new TileView();
    private TileView randomTile4 = new TileView();
    private TileView specialTile1 = new TileView("S0");
    private TileView specialTile2 = new TileView("S1");
    private TileView specialTile3 = new TileView("S2");
    private TileView specialTile4 = new TileView("S3");
    private TileView specialTile5 = new TileView("S4");
    private TileView specialTile6 = new TileView("S5");

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     *
     * @author Hengjia Zhang
     */
    private void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        tilesInBoard.getChildren().clear();
        tilesInBoard.getChildren().removeAll();
        int num = placement.length()/5;

        for(int i = 0; i < num; i++)
        {
            ImageView tileImage = new ImageView();
            Image img = new Image(Viewer.class.getResource(Viewer.URI_BASE +placement.substring(0+5*i,2+5*i) +".png").toString());
            tileImage.setImage(img);
            tileImage.setFitHeight(100*0.7);
            tileImage.setFitWidth(100*0.7);
            tileImage.setY(10*0.7 + (placement.charAt(2+5*i)-'A') * 100*0.7 +100);
            tileImage.setX(267 + Integer.parseInt(placement.substring(3+5*i,4+5*i))*100*0.7);

            if(placement.charAt(4+5*i) == '1')
            {
                tileImage.setRotate(90);
            }
            if(placement.charAt(4+5*i) == '2')
            {
                tileImage.setRotate(180);
            }
            if(placement.charAt(4+5*i) == '3')
            {
                tileImage.setRotate(-90);
            }
            if(placement.charAt(4+5*i) == '4')
            {
                tileImage.setScaleX(-1);
            }
            if(placement.charAt(4+5*i) == '5')
            {
                tileImage.setScaleX(-1);
                tileImage.setRotate(90);
            }
            if(placement.charAt(4+5*i) == '6')
            {
                tileImage.setScaleX(-1);
                tileImage.setRotate(180);
            }
            if(placement.charAt(4+5*i) == '7')
            {
                tileImage.setScaleX(-1);
                tileImage.setRotate(-90);
            }

            tilesInBoard.getChildren().add(tileImage);
        }

    }

    /**
     * Draw a placement in the AI window, removing any previously drawn one
     *
     * @param placement A valid placement string
     *
     * @author Hengjia Zhang
     */
    private void makePlacementAI(String placement){
        tilesInBoardAI.getChildren().clear();
        tilesInBoardAI.getChildren().removeAll();
        int num = placement.length()/5;

        for(int i = 0; i < num; i++)
        {
            ImageView tileImage = new ImageView();
            Image img = new Image(Viewer.class.getResource(Viewer.URI_BASE +placement.substring(0+5*i,2+5*i) +".png").toString());
            tileImage.setImage(img);
            tileImage.setFitHeight(100*0.7);
            tileImage.setFitWidth(100*0.7);
            tileImage.setY(139 + (placement.charAt(2+5*i)-'A') * 100*0.7 );
            tileImage.setX(139 + Integer.parseInt(placement.substring(3+5*i,4+5*i))*100*0.7);

            if(placement.charAt(4+5*i) == '1')
            {
                tileImage.setRotate(90);
            }
            if(placement.charAt(4+5*i) == '2')
            {
                tileImage.setRotate(180);
            }
            if(placement.charAt(4+5*i) == '3')
            {
                tileImage.setRotate(-90);
            }
            if(placement.charAt(4+5*i) == '4')
            {
                tileImage.setScaleX(-1);
            }
            if(placement.charAt(4+5*i) == '5')
            {
                tileImage.setScaleX(-1);
                tileImage.setRotate(90);
            }
            if(placement.charAt(4+5*i) == '6')
            {
                tileImage.setScaleX(-1);
                tileImage.setRotate(180);
            }
            if(placement.charAt(4+5*i) == '7')
            {
                tileImage.setScaleX(-1);
                tileImage.setRotate(-90);
            }

            tilesInBoardAI.getChildren().add(tileImage);
        }
    }

    /**
     * Create a basic text field for input and a refresh button.
     *
     * @author Hengjia Zhang
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(280);
        hb.setLayoutY(VIEWER_HEIGHT - 100);
        controls.getChildren().add(hb);
    }

    /**
     * Set background include board and exit
     *
     * @author Hengjia Zhang
     */
    private void setBackgroud(){
        //set background
        Line lineY1 = new Line(267,10*0.7+100,267,710*0.7+100);
        Line lineY2 = new Line(337,10*0.7+100,337,710*0.7+100);
        Line lineY3 = new Line(407,10*0.7+100,407,710*0.7+100);
        Line lineY4 = new Line(477,10*0.7+100,477,710*0.7+100);
        Line lineY5 = new Line(547,10*0.7+100,547,710*0.7+100);
        Line lineY6 = new Line(617,10*0.7+100,617,710*0.7+100);
        Line lineY7 = new Line(687,10*0.7+100,687,710*0.7+100);
        Line lineY8 = new Line(757,10*0.7+100,757,710*0.7+100);
        Line lineX1 = new Line(267,10*0.7+100,757,10*0.7+100);
        Line lineX2 = new Line(267,110*0.7+100,757,110*0.7+100);
        Line lineX3 = new Line(267,210*0.7+100,757,210*0.7+100);
        Line lineX4 = new Line(267,310*0.7+100,757,310*0.7+100);
        Line lineX5 = new Line(267,410*0.7+100,757,410*0.7+100);
        Line lineX6 = new Line(267,510*0.7+100,757,510*0.7+100);
        Line lineX7 = new Line(267,610*0.7+100,757,610*0.7+100);
        Line lineX8 = new Line(267,710*0.7+100,757,710*0.7+100);
        Line lineZ1 = new Line(407,210*0.7+100,617,210*0.7+100);
        Line lineZ2 = new Line(407,210*0.7+100,407,510*0.7+100);
        Line lineZ3 = new Line(407,510*0.7+100,617,510*0.7+100);
        Line lineZ4 = new Line(617,210*0.7+100,617,510*0.7+100);
        lineY1.setStrokeWidth(3);
        lineY8.setStrokeWidth(3);
        lineX1.setStrokeWidth(3);
        lineX8.setStrokeWidth(3);
        lineZ1.setStrokeWidth(3);
        lineZ1.setStroke(Color.RED);
        lineZ2.setStrokeWidth(3);
        lineZ2.setStroke(Color.RED);
        lineZ3.setStrokeWidth(3);
        lineZ3.setStroke(Color.RED);
        lineZ4.setStrokeWidth(3);
        lineZ4.setStroke(Color.RED);

        root.getChildren().add(lineY1);
        root.getChildren().add(lineY2);
        root.getChildren().add(lineY3);
        root.getChildren().add(lineY4);
        root.getChildren().add(lineY5);
        root.getChildren().add(lineY6);
        root.getChildren().add(lineY7);
        root.getChildren().add(lineY8);
        root.getChildren().add(lineX1);
        root.getChildren().add(lineX2);
        root.getChildren().add(lineX3);
        root.getChildren().add(lineX4);
        root.getChildren().add(lineX5);
        root.getChildren().add(lineX6);
        root.getChildren().add(lineX7);
        root.getChildren().add(lineX8);
        root.getChildren().add(lineZ1);
        root.getChildren().add(lineZ2);
        root.getChildren().add(lineZ3);
        root.getChildren().add(lineZ4);

        ImageView highExit1 = new ImageView();
        Image imgHE = new Image(Viewer.class.getResource(Viewer.URI_BASE + "HighExit.png").toString());
        highExit1.setImage(imgHE);
        highExit1.setFitHeight(100*0.7);
        highExit1.setFitWidth(100*0.7);
        highExit1.setX(337);
        highExit1.setY(72);
        root.getChildren().add(highExit1);

        ImageView highExit2 = new ImageView();
        highExit2.setImage(imgHE);
        highExit2.setFitHeight(100*0.7);
        highExit2.setFitWidth(100*0.7);
        highExit2.setX(662*0.7+154);
        highExit2.setY(-40*0.7+100);
        root.getChildren().add(highExit2);

        ImageView highExit3 = new ImageView();
        highExit3.setImage(imgHE);
        highExit3.setFitHeight(100*0.7);
        highExit3.setFitWidth(100*0.7);
        highExit3.setX(337);
        highExit3.setY(562);
        highExit3.setRotate(180);
        root.getChildren().add(highExit3);

        ImageView highExit4 = new ImageView();
        highExit4.setImage(imgHE);
        highExit4.setFitHeight(100*0.7);
        highExit4.setFitWidth(100*0.7);
        highExit4.setX(662*0.7+154);
        highExit4.setY(660*0.7+100);
        highExit4.setRotate(180);
        root.getChildren().add(highExit4);

        ImageView highExit5 = new ImageView();
        highExit5.setImage(imgHE);
        highExit5.setFitHeight(100*0.7);
        highExit5.setFitWidth(100*0.7);
        highExit5.setX(112*0.7+154);
        highExit5.setY(310*0.7+100);
        highExit5.setRotate(-90);
        root.getChildren().add(highExit5);

        ImageView highExit6 = new ImageView();
        highExit6.setImage(imgHE);
        highExit6.setFitHeight(100*0.7);
        highExit6.setFitWidth(100*0.7);
        highExit6.setX(812*0.7+154);
        highExit6.setY(310*0.7+100);
        highExit6.setRotate(90);
        root.getChildren().add(highExit6);

        ImageView railExit1 = new ImageView();
        Image imgRE = new Image(Viewer.class.getResource(Viewer.URI_BASE + "RailExit.png").toString());
        railExit1.setImage(imgRE);
        railExit1.setFitHeight(100*0.7);
        railExit1.setFitWidth(100*0.7);
        railExit1.setX(112*0.7+154);
        railExit1.setY(110*0.7+100);
        railExit1.setRotate(-90);
        root.getChildren().add(railExit1);

        ImageView railExit2 = new ImageView();
        railExit2.setImage(imgRE);
        railExit2.setFitHeight(100*0.7);
        railExit2.setFitWidth(100*0.7);
        railExit2.setX(112*0.7+154);
        railExit2.setY(510*0.7+100);
        railExit2.setRotate(-90);
        root.getChildren().add(railExit2);

        ImageView railExit3 = new ImageView();
        railExit3.setImage(imgRE);
        railExit3.setFitHeight(100*0.7);
        railExit3.setFitWidth(100*0.7);
        railExit3.setX(812*0.7+154);
        railExit3.setY(110*0.7+100);
        railExit3.setRotate(90);
        root.getChildren().add(railExit3);

        ImageView railExit4 = new ImageView();
        railExit4.setImage(imgRE);
        railExit4.setFitHeight(100*0.7);
        railExit4.setFitWidth(100*0.7);
        railExit4.setX(812*0.7+154);
        railExit4.setY(510*0.7+100);
        railExit4.setRotate(90);
        root.getChildren().add(railExit4);

        ImageView railExit5 = new ImageView();
        railExit5.setImage(imgRE);
        railExit5.setFitHeight(100*0.7);
        railExit5.setFitWidth(100*0.7);
        railExit5.setX(462*0.7+154);
        railExit5.setY(-40 + 110);
        root.getChildren().add(railExit5);

        ImageView railExit6 = new ImageView();
        railExit6.setImage(imgRE);
        railExit6.setFitHeight(100*0.7);
        railExit6.setFitWidth(100*0.7);
        railExit6.setX(462*0.7+154);
        railExit6.setY(660*0.7+100);
        railExit6.setRotate(180);
        root.getChildren().add(railExit6);
    }

    /**
     * Set background include board and exit in AI Stage
     *
     * @author Hengjia Zhang
     */
    private void setBackgroudAI(){
        //set background
        Line lineY1 = new Line(139,139,139,629);
        Line lineY2 = new Line(337-128,10*0.7+100+32,337-128,710*0.7+100+32);
        Line lineY3 = new Line(407-128,10*0.7+100+32,407-128,710*0.7+100+32);
        Line lineY4 = new Line(477-128,10*0.7+100+32,477-128,710*0.7+100+32);
        Line lineY5 = new Line(547-128,10*0.7+100+32,547-128,710*0.7+100+32);
        Line lineY6 = new Line(617-128,10*0.7+100+32,617-128,710*0.7+100+32);
        Line lineY7 = new Line(687-128,10*0.7+100+32,687-128,710*0.7+100+32);
        Line lineY8 = new Line(757-128,10*0.7+100+32,757-128,710*0.7+100+32);
        Line lineX1 = new Line(267-128,10*0.7+100+32,757-128,10*0.7+100+32);
        Line lineX2 = new Line(267-128,110*0.7+100+32,757-128,110*0.7+100+32);
        Line lineX3 = new Line(267-128,210*0.7+100+32,757-128,210*0.7+100+32);
        Line lineX4 = new Line(267-128,310*0.7+100+32,757-128,310*0.7+100+32);
        Line lineX5 = new Line(267-128,410*0.7+100+32,757-128,410*0.7+100+32);
        Line lineX6 = new Line(267-128,510*0.7+100+32,757-128,510*0.7+100+32);
        Line lineX7 = new Line(267-128,610*0.7+100+32,757-128,610*0.7+100+32);
        Line lineX8 = new Line(267-128,710*0.7+100+32,757-128,710*0.7+100+32);
        Line lineZ1 = new Line(407-128,210*0.7+100+32,617-128,210*0.7+100+32);
        Line lineZ2 = new Line(407-128,210*0.7+100+32,407-128,510*0.7+100+32);
        Line lineZ3 = new Line(407-128,510*0.7+100+32,617-128,510*0.7+100+32);
        Line lineZ4 = new Line(617-128,210*0.7+100+32,617-128,510*0.7+100+32);
        lineY1.setStrokeWidth(3);
        lineY8.setStrokeWidth(3);
        lineX1.setStrokeWidth(3);
        lineX8.setStrokeWidth(3);
        lineZ1.setStrokeWidth(3);
        lineZ1.setStroke(Color.RED);
        lineZ2.setStrokeWidth(3);
        lineZ2.setStroke(Color.RED);
        lineZ3.setStrokeWidth(3);
        lineZ3.setStroke(Color.RED);
        lineZ4.setStrokeWidth(3);
        lineZ4.setStroke(Color.RED);

        rootAI.getChildren().add(lineY1);
        rootAI.getChildren().add(lineY2);
        rootAI.getChildren().add(lineY3);
        rootAI.getChildren().add(lineY4);
        rootAI.getChildren().add(lineY5);
        rootAI.getChildren().add(lineY6);
        rootAI.getChildren().add(lineY7);
        rootAI.getChildren().add(lineY8);
        rootAI.getChildren().add(lineX1);
        rootAI.getChildren().add(lineX2);
        rootAI.getChildren().add(lineX3);
        rootAI.getChildren().add(lineX4);
        rootAI.getChildren().add(lineX5);
        rootAI.getChildren().add(lineX6);
        rootAI.getChildren().add(lineX7);
        rootAI.getChildren().add(lineX8);
        rootAI.getChildren().add(lineZ1);
        rootAI.getChildren().add(lineZ2);
        rootAI.getChildren().add(lineZ3);
        rootAI.getChildren().add(lineZ4);

        ImageView highExit1 = new ImageView();
        Image imgHE = new Image(Viewer.class.getResource(Viewer.URI_BASE + "HighExit.png").toString());
        highExit1.setImage(imgHE);
        highExit1.setFitHeight(100*0.7);
        highExit1.setFitWidth(100*0.7);
        highExit1.setX(337-128);
        highExit1.setY(72+32);
        rootAI.getChildren().add(highExit1);

        ImageView highExit2 = new ImageView();
        highExit2.setImage(imgHE);
        highExit2.setFitHeight(100*0.7);
        highExit2.setFitWidth(100*0.7);
        highExit2.setX(662*0.7+154-128);
        highExit2.setY(-40*0.7+100+32);
        rootAI.getChildren().add(highExit2);

        ImageView highExit3 = new ImageView();
        highExit3.setImage(imgHE);
        highExit3.setFitHeight(100*0.7);
        highExit3.setFitWidth(100*0.7);
        highExit3.setX(337-128);
        highExit3.setY(562+32);
        highExit3.setRotate(180);
        rootAI.getChildren().add(highExit3);

        ImageView highExit4 = new ImageView();
        highExit4.setImage(imgHE);
        highExit4.setFitHeight(100*0.7);
        highExit4.setFitWidth(100*0.7);
        highExit4.setX(662*0.7+154-128);
        highExit4.setY(660*0.7+100+32);
        highExit4.setRotate(180);
        rootAI.getChildren().add(highExit4);

        ImageView highExit5 = new ImageView();
        highExit5.setImage(imgHE);
        highExit5.setFitHeight(100*0.7);
        highExit5.setFitWidth(100*0.7);
        highExit5.setX(112*0.7+154-128);
        highExit5.setY(310*0.7+100+32);
        highExit5.setRotate(-90);
        rootAI.getChildren().add(highExit5);

        ImageView highExit6 = new ImageView();
        highExit6.setImage(imgHE);
        highExit6.setFitHeight(100*0.7);
        highExit6.setFitWidth(100*0.7);
        highExit6.setX(812*0.7+154-128);
        highExit6.setY(310*0.7+100+32);
        highExit6.setRotate(90);
        rootAI.getChildren().add(highExit6);

        ImageView railExit1 = new ImageView();
        Image imgRE = new Image(Viewer.class.getResource(Viewer.URI_BASE + "RailExit.png").toString());
        railExit1.setImage(imgRE);
        railExit1.setFitHeight(100*0.7);
        railExit1.setFitWidth(100*0.7);
        railExit1.setX(112*0.7+154-128);
        railExit1.setY(110*0.7+100+32);
        railExit1.setRotate(-90);
        rootAI.getChildren().add(railExit1);

        ImageView railExit2 = new ImageView();
        railExit2.setImage(imgRE);
        railExit2.setFitHeight(100*0.7);
        railExit2.setFitWidth(100*0.7);
        railExit2.setX(112*0.7+154-128);
        railExit2.setY(510*0.7+100+32);
        railExit2.setRotate(-90);
        rootAI.getChildren().add(railExit2);

        ImageView railExit3 = new ImageView();
        railExit3.setImage(imgRE);
        railExit3.setFitHeight(100*0.7);
        railExit3.setFitWidth(100*0.7);
        railExit3.setX(812*0.7+154-128);
        railExit3.setY(110*0.7+100+32);
        railExit3.setRotate(90);
        rootAI.getChildren().add(railExit3);

        ImageView railExit4 = new ImageView();
        railExit4.setImage(imgRE);
        railExit4.setFitHeight(100*0.7);
        railExit4.setFitWidth(100*0.7);
        railExit4.setX(812*0.7+154-128);
        railExit4.setY(510*0.7+100+32);
        railExit4.setRotate(90);
        rootAI.getChildren().add(railExit4);

        ImageView railExit5 = new ImageView();
        railExit5.setImage(imgRE);
        railExit5.setFitHeight(100*0.7);
        railExit5.setFitWidth(100*0.7);
        railExit5.setX(462*0.7+154-128);
        railExit5.setY(-40 + 110+32);
        rootAI.getChildren().add(railExit5);

        ImageView railExit6 = new ImageView();
        railExit6.setImage(imgRE);
        railExit6.setFitHeight(100*0.7);
        railExit6.setFitWidth(100*0.7);
        railExit6.setX(462*0.7+154-128);
        railExit6.setY(660*0.7+100+32);
        railExit6.setRotate(180);
        rootAI.getChildren().add(railExit6);
    }

    /**
     * Place four random tiles in the left of windows
     *
     * @param dice A random string representing the die roll e.g. A0A4A3B2
     *
     * @author Hengjia Zhang
     */
    private void placeFourTiles(String dice){

        int xCoordinate = 120;
            round++;
            showRound();
            canUseSpecialTiles = true;

            randomTile1.setType(dice.substring(0,2));
            randomTile2.setType(dice.substring(2,4));
            randomTile3.setType(dice.substring(4,6));
            randomTile4.setType(dice.substring(6,8));

            randomTile1.setRotate(0);
            randomTile2.setRotate(0);
            randomTile3.setRotate(0);
            randomTile4.setRotate(0);
            randomTile1.setScaleX(1);
            randomTile2.setScaleX(1);
            randomTile3.setScaleX(1);
            randomTile4.setScaleX(1);
            randomTile1.setScaleY(1);
            randomTile2.setScaleY(1);
            randomTile3.setScaleY(1);
            randomTile4.setScaleY(1);

            randomTile1.setFitHeight(100*0.7);
            randomTile1.setFitWidth(100*0.7);
            randomTile1.setX(xCoordinate);
            randomTile1.setY(200);
            randomTilesGroup.getChildren().add(randomTile1);

            randomTile2.setFitHeight(100*0.7);
            randomTile2.setFitWidth(100*0.7);
            randomTile2.setX(xCoordinate);
            randomTile2.setY(200+100);
            randomTilesGroup.getChildren().add(randomTile2);

            randomTile3.setFitHeight(100*0.7);
            randomTile3.setFitWidth(100*0.7);
            randomTile3.setX(xCoordinate);
            randomTile3.setY(200+100*2);
            randomTilesGroup.getChildren().add(randomTile3);

            randomTile4.setFitHeight(100*0.7);
            randomTile4.setFitWidth(100*0.7);
            randomTile4.setX(xCoordinate);
            randomTile4.setY(200+100*3);
            randomTilesGroup.getChildren().add(randomTile4);
    }

    /**
     * Put six special tiles in board
     *
     * @author Hengjia Zhang
     */
    private void generateSpecialTiles(){
        int xCoordinate = 875;
        specialTile1.setFitHeight(100*0.7);
        specialTile1.setFitWidth(100*0.7);
        specialTile1.setX(xCoordinate);
        specialTile1.setY(80);
        specialTilesGroup.getChildren().add(specialTile1);

        specialTile2.setFitHeight(100*0.7);
        specialTile2.setFitWidth(100*0.7);
        specialTile2.setX(xCoordinate);
        specialTile2.setY(180);
        specialTilesGroup.getChildren().add(specialTile2);

        specialTile3.setFitHeight(100*0.7);
        specialTile3.setFitWidth(100*0.7);
        specialTile3.setX(xCoordinate);
        specialTile3.setY(280);
        specialTilesGroup.getChildren().add(specialTile3);

        specialTile4.setFitHeight(100*0.7);
        specialTile4.setFitWidth(100*0.7);
        specialTile4.setX(xCoordinate);
        specialTile4.setY(380);
        specialTilesGroup.getChildren().add(specialTile4);

        specialTile5.setFitHeight(100*0.7);
        specialTile5.setFitWidth(100*0.7);
        specialTile5.setX(xCoordinate);
        specialTile5.setY(480);
        specialTilesGroup.getChildren().add(specialTile5);

        specialTile6.setFitHeight(100*0.7);
        specialTile6.setFitWidth(100*0.7);
        specialTile6.setX(xCoordinate);
        specialTile6.setY(580);
        specialTilesGroup.getChildren().add(specialTile6);
    }

    /**
     * Create a button aimed at put four random tiles in board
     *
     * @author Hengjia Zhang
     */
    private void generateRandomTiles(){
        Button button = new Button("Next Round");
        button.setOnAction(e -> {
            if(randomTilesGroup.getChildren().isEmpty()) {
                if (round < 7) {
                    diceRoll = generateDiceRoll();
                    placeFourTiles(diceRoll);
                } else {
                    Stage secondaryStage = new Stage();

                    HBox pane = new HBox();
                    pane.setPadding(new Insets(10, 10, 10, 10));
                    Text text1 = new Text(20, 20, "You can only play 7 rounds!\nclick \"Get Scores\" to see your scores!");
                    text1.setFont(Font.font("Courier", FontWeight.BOLD, 15));
                    text1.setFill(Color.BLACK);

                    pane.getChildren().add(text1);
                    Scene scoreScene = new Scene(pane, 600, 100);
                    pane.setAlignment(Pos.CENTER);
                    secondaryStage.setScene(scoreScene);
                    secondaryStage.setTitle("Warning");
                    secondaryStage.show();
                }
            }
            else{
                Stage secondaryStage=new Stage();

                HBox pane = new HBox();
                pane.setPadding(new Insets(10,10,10,10));
                Text text1 = new Text(20,20,"You have to use four random tiles before roll!");
                text1.setFont(Font.font("Courier", FontWeight.BOLD, 15));
                text1.setFill(Color.BLACK);

                pane.getChildren().add(text1);
                Scene scoreScene = new Scene(pane,500,100);
                pane.setAlignment(Pos.CENTER);
                secondaryStage.setScene(scoreScene);
                secondaryStage.setTitle("Warning");
                secondaryStage.show();
            }
            });
        HBox hb = new HBox();
        hb.getChildren().add(button);
        hb.setSpacing(10);
        hb.setLayoutX(70);
        hb.setLayoutY(100);
        controls.getChildren().add(hb);
    }

    /**
     * Create a button aimed at put four random tiles in board
     * put random tiles in AI board
     *
     * @author Hengjia Zhang
     */
    private void generateRandomTilesAI(){
        Button button = new Button("Next Round");
        button.setOnAction(e -> {
            if(randomTilesGroup.getChildren().isEmpty()) {
                if (round < 7) {
                    diceRoll = generateDiceRoll();
                    placeFourTiles(diceRoll);
                    totalPlacementAI += generateMove(totalPlacementAI,diceRoll);
                    makePlacementAI(totalPlacementAI);
                } else {
                    Stage secondaryStage = new Stage();

                    HBox pane = new HBox();
                    pane.setPadding(new Insets(10, 10, 10, 10));
                    Text text1 = new Text(20, 20, "You can only play 7 rounds!\nclick \"Get Scores\" to see your scores!");
                    text1.setFont(Font.font("Courier", FontWeight.BOLD, 15));
                    text1.setFill(Color.BLACK);

                    pane.getChildren().add(text1);
                    Scene scoreScene = new Scene(pane, 600, 100);
                    pane.setAlignment(Pos.CENTER);
                    secondaryStage.setScene(scoreScene);
                    secondaryStage.setTitle("Warning");
                    secondaryStage.show();
                }
            }
            else{
                Stage secondaryStage=new Stage();

                HBox pane = new HBox();
                pane.setPadding(new Insets(10,10,10,10));
                Text text1 = new Text(20,20,"You have to use four random tiles before roll!");
                text1.setFont(Font.font("Courier", FontWeight.BOLD, 15));
                text1.setFill(Color.BLACK);

                pane.getChildren().add(text1);
                Scene scoreScene = new Scene(pane,500,100);
                pane.setAlignment(Pos.CENTER);
                secondaryStage.setScene(scoreScene);
                secondaryStage.setTitle("Warning");
                secondaryStage.show();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().add(button);
        hb.setSpacing(10);
        hb.setLayoutX(70);
        hb.setLayoutY(100);
        controls.getChildren().add(hb);
    }

    /**
     * Rotate the tiles 90 degree
     *
     * @author Hengjia Zhang
     */
    private void rotateTile(TileView tile){
        tile.rotate();
    }

    /**
     * Mirroring the tile over the y-axis
     *
     * @author Hengjia Zhang
     */
    private void mirrorTile(TileView tile){
        tile.mirror();
    }

    /**
     * Create buttons aimed at rotate and mirror random tiles
     *
     * @author Hengjia Zhang
     */
    private void controlRandomTiles(){
        Button button11 = new Button("rotate");
        button11.setOnAction(f -> rotateTile(randomTile1));
        HBox hb11 = new HBox();
        hb11.getChildren().add(button11);
        hb11.setSpacing(10);
        hb11.setLayoutX(50);
        hb11.setLayoutY(200);
        controls.getChildren().add(hb11);

        Button button12 = new Button("mirror");
        button12.setOnAction(f -> mirrorTile(randomTile1));
        HBox hb12 = new HBox();
        hb12.getChildren().add(button12);
        hb12.setSpacing(10);
        hb12.setLayoutX(50);
        hb12.setLayoutY(200+30);
        controls.getChildren().add(hb12);

        Button button21 = new Button("rotate");
        button21.setOnAction(f -> rotateTile(randomTile2));
        HBox hb21 = new HBox();
        hb21.getChildren().add(button21);
        hb21.setSpacing(10);
        hb21.setLayoutX(50);
        hb21.setLayoutY(200+100);
        controls.getChildren().add(hb21);

        Button button22 = new Button("mirror");
        button22.setOnAction(f -> mirrorTile(randomTile2));
        HBox hb22 = new HBox();
        hb22.getChildren().add(button22);
        hb22.setSpacing(10);
        hb22.setLayoutX(50);
        hb22.setLayoutY(200+100+30);
        controls.getChildren().add(hb22);

        Button button31 = new Button("rotate");
        button31.setOnAction(f -> rotateTile(randomTile3));
        HBox hb31 = new HBox();
        hb31.getChildren().add(button31);
        hb31.setSpacing(10);
        hb31.setLayoutX(50);
        hb31.setLayoutY(200+200);
        controls.getChildren().add(hb31);

        Button button32 = new Button("mirror");
        button32.setOnAction(f -> mirrorTile(randomTile3));
        HBox hb32 = new HBox();
        hb32.getChildren().add(button32);
        hb32.setSpacing(10);
        hb32.setLayoutX(50);
        hb32.setLayoutY(200+200+30);
        controls.getChildren().add(hb32);

        Button button41 = new Button("rotate");
        button41.setOnAction(f -> rotateTile(randomTile4));
        HBox hb41 = new HBox();
        hb41.getChildren().add(button41);
        hb41.setSpacing(10);
        hb41.setLayoutX(50);
        hb41.setLayoutY(200+300);
        controls.getChildren().add(hb41);

        Button button42 = new Button("mirror");
        button42.setOnAction(f -> mirrorTile(randomTile4));
        HBox hb42 = new HBox();
        hb42.getChildren().add(button42);
        hb42.setSpacing(10);
        hb42.setLayoutX(50);
        hb42.setLayoutY(200+300+30);
        controls.getChildren().add(hb42);
    }

    /**
     * Create buttons aimed at rotate and mirror special tiles
     *
     * @author Hengjia Zhang
     */
    private void controlSpecialTiles(){

        int xCoordinate = 805;

        Button button11 = new Button("rotate");
        button11.setOnAction(f -> rotateTile(specialTile1));
        HBox hb11 = new HBox();
        hb11.getChildren().add(button11);
        hb11.setSpacing(10);
        hb11.setLayoutX(xCoordinate);
        hb11.setLayoutY(80);
        controls.getChildren().add(hb11);

        Button button12 = new Button("mirror");
        button12.setOnAction(f -> mirrorTile(specialTile1));
        HBox hb12 = new HBox();
        hb12.getChildren().add(button12);
        hb12.setSpacing(10);
        hb12.setLayoutX(xCoordinate);
        hb12.setLayoutY(80+30);
        controls.getChildren().add(hb12);

        Button button21 = new Button("rotate");
        button21.setOnAction(f -> rotateTile(specialTile2));
        HBox hb21 = new HBox();
        hb21.getChildren().add(button21);
        hb21.setSpacing(10);
        hb21.setLayoutX(xCoordinate);
        hb21.setLayoutY(180);
        controls.getChildren().add(hb21);

        Button button22 = new Button("mirror");
        button22.setOnAction(f -> mirrorTile(specialTile2));
        HBox hb22 = new HBox();
        hb22.getChildren().add(button22);
        hb22.setSpacing(10);
        hb22.setLayoutX(xCoordinate);
        hb22.setLayoutY(180+30);
        controls.getChildren().add(hb22);

        Button button31 = new Button("rotate");
        button31.setOnAction(f -> rotateTile(specialTile3));
        HBox hb31 = new HBox();
        hb31.getChildren().add(button31);
        hb31.setSpacing(10);
        hb31.setLayoutX(xCoordinate);
        hb31.setLayoutY(280);
        controls.getChildren().add(hb31);

        Button button32 = new Button("mirror");
        button32.setOnAction(f -> mirrorTile(specialTile3));
        HBox hb32 = new HBox();
        hb32.getChildren().add(button32);
        hb32.setSpacing(10);
        hb32.setLayoutX(xCoordinate);
        hb32.setLayoutY(280+30);
        controls.getChildren().add(hb32);

        Button button41 = new Button("rotate");
        button41.setOnAction(f -> rotateTile(specialTile4));
        HBox hb41 = new HBox();
        hb41.getChildren().add(button41);
        hb41.setSpacing(10);
        hb41.setLayoutX(xCoordinate);
        hb41.setLayoutY(380);
        controls.getChildren().add(hb41);

        Button button42 = new Button("mirror");
        button42.setOnAction(f -> mirrorTile(specialTile4));
        HBox hb42 = new HBox();
        hb42.getChildren().add(button42);
        hb42.setSpacing(10);
        hb42.setLayoutX(xCoordinate);
        hb42.setLayoutY(380+30);
        controls.getChildren().add(hb42);

        Button button51 = new Button("rotate");
        button51.setOnAction(f -> rotateTile(specialTile5));
        HBox hb51 = new HBox();
        hb51.getChildren().add(button51);
        hb51.setSpacing(10);
        hb51.setLayoutX(xCoordinate);
        hb51.setLayoutY(480);
        controls.getChildren().add(hb51);

        Button button52 = new Button("mirror");
        button52.setOnAction(f -> mirrorTile(specialTile5));
        HBox hb52 = new HBox();
        hb52.getChildren().add(button52);
        hb52.setSpacing(10);
        hb52.setLayoutX(xCoordinate);
        hb52.setLayoutY(480+30);
        controls.getChildren().add(hb52);

        Button button61 = new Button("rotate");
        button61.setOnAction(f -> rotateTile(specialTile6));
        HBox hb61 = new HBox();
        hb61.getChildren().add(button61);
        hb61.setSpacing(10);
        hb61.setLayoutX(xCoordinate);
        hb61.setLayoutY(580);
        controls.getChildren().add(hb61);

        Button button62 = new Button("mirror");
        button62.setOnAction(f -> mirrorTile(specialTile6));
        HBox hb62 = new HBox();
        hb62.getChildren().add(button62);
        hb62.setSpacing(10);
        hb62.setLayoutX(xCoordinate);
        hb62.setLayoutY(580+30);
        controls.getChildren().add(hb62);
    }


    /**
     * Add drag event to random tiles
     *
     * @author Hengjia Zhang
     */
    private void dragRandomTiles(){
        randomTile1.setPickOnBounds(true);
        randomTile2.setPickOnBounds(true);
        randomTile3.setPickOnBounds(true);
        randomTile4.setPickOnBounds(true);
        randomTile1.setOnMouseDragged(e -> {
            randomTile1.setX(e.getSceneX()-35); //why can't I use e.getX()-35 ?
            randomTile1.setY(e.getSceneY()-35);
        });
        randomTile1.setOnMouseReleased(e -> putRandomTile(randomTile1,e.getX(),e.getY(),120,200));
        randomTile2.setOnMouseDragged(e -> {
            randomTile2.setX(e.getSceneX()-35);
            randomTile2.setY(e.getSceneY()-35);
        });
        randomTile2.setOnMouseReleased(e -> putRandomTile(randomTile2,e.getX(),e.getY(),120,300));
        randomTile3.setOnMouseDragged(e -> {
            randomTile3.setX(e.getSceneX()-35);
            randomTile3.setY(e.getSceneY()-35);
        });
        randomTile3.setOnMouseReleased(e -> putRandomTile(randomTile3,e.getX(),e.getY(),120,400));
        randomTile4.setOnMouseDragged(e -> {
            randomTile4.setX(e.getSceneX()-35);
            randomTile4.setY(e.getSceneY()-35);
        });
        randomTile4.setOnMouseReleased(e -> putRandomTile(randomTile4,e.getX(),e.getY(),120,500));
    }

    /**
     * Add drag event to special tiles
     *
     * @author Hengjia Zhang
     */
    private void dragSpecialTiles(){
        ArrayList<TileView> specialTilesArray = new ArrayList<>();
        specialTilesArray.add(specialTile1);
        specialTilesArray.add(specialTile2);
        specialTilesArray.add(specialTile3);
        specialTilesArray.add(specialTile4);
        specialTilesArray.add(specialTile5);
        specialTilesArray.add(specialTile6);

        for(TileView t:specialTilesArray){
            double xOriginal = t.getX();
            double yOriginal = t.getY();
            t.setPickOnBounds(true);
            t.setOnMouseDragged(e -> {
                t.setX(e.getSceneX()-35);
                t.setY(e.getSceneY()-35);
            });
            t.setOnMouseReleased(e -> putSpecialTile(t,e.getX(),e.getY(),xOriginal,yOriginal));
        }
    }

    /**
     * If the random tile is in valid place, put it in board and remove if form tileGroup
     * else, put it to its original place
     *
     * @author Hengjia Zhang
     */
    private void putRandomTile(TileView tile, double x, double y,double xOri, double yOri){
        String temp1 = "";
        String temp2 = "";
        if(x>267+5 && x<337-5){
            temp1 = "0";
        }
        else if(x>337+5 && x<407-5){
            temp1 = "1";
        }
        else if(x>407+5 && x<477-5){
            temp1 = "2";
        }
        else if(x>477+5 && x<547-5){
            temp1 = "3";
        }
        else if(x>547+5 && x<617-5){
            temp1 = "4";
        }
        else if(x>617+5 && x<687-5){
            temp1 = "5";
        }
        else if(x>687+5 && x<757-5){
            temp1 = "6";
        }

        if(y>107+5 && y<177-5){
            temp2 = "A";
        }
        else if(y>177+5 && y<247-5){
            temp2 = "B";
        }
        else if(y>247+5 && y<317-5){
            temp2 = "C";
        }
        else if(y>317+5 && y<387-5){
            temp2 = "D";
        }
        else if(y>387+5 && y<457-5){
            temp2 = "E";
        }
        else if(y>457+5 && y<527-5){
            temp2 = "F";
        }
        else if(y>527+5 && y<597-5){
            temp2 = "G";
        }

        if(temp1.compareTo("0") >=0 && temp1.compareTo("6") <= 0 && temp2.compareTo("A") >=0 && temp2.compareTo("G") <=0){
            tile.setLocation(temp2+temp1);
            if(isValidPlacementSequence(totalPlacement+tile.getPlacement())){
            randomTilesGroup.getChildren().remove(tile);
            specialTilesGroup.getChildren().remove(tile);
            totalPlacement += tile.getPlacement();
            makePlacement(totalPlacement);
            }
            else {
                tile.setX(xOri);
                tile.setY(yOri);
            }
        }
        else {
            tile.setX(xOri);
            tile.setY(yOri);
        }
    }

    /**
     * If the random tile is in valid place, put it in board and remove if form tileGroup
     * else, put it to its original place
     * show warning in some situations
     *
     * @author Hengjia Zhang
     */
    private void putSpecialTile(TileView tile, double x, double y,double xOriginal, double yOriginal){

        String temp1 = "";
        String temp2 = "";
        if(x>267+5 && x<337-5){
            temp1 = "0";
        }
        else if(x>337+5 && x<407-5){
            temp1 = "1";
        }
        else if(x>407+5 && x<477-5){
            temp1 = "2";
        }
        else if(x>477+5 && x<547-5){
            temp1 = "3";
        }
        else if(x>547+5 && x<617-5){
            temp1 = "4";
        }
        else if(x>617+5 && x<687-5){
            temp1 = "5";
        }
        else if(x>687+5 && x<757-5){
            temp1 = "6";
        }

        if(y>107+5 && y<177-5){
            temp2 = "A";
        }
        else if(y>177+5 && y<247-5){
            temp2 = "B";
        }
        else if(y>247+5 && y<317-5){
            temp2 = "C";
        }
        else if(y>317+5 && y<387-5){
            temp2 = "D";
        }
        else if(y>387+5 && y<457-5){
            temp2 = "E";
        }
        else if(y>457+5 && y<527-5){
            temp2 = "F";
        }
        else if(y>527+5 && y<597-5){
            temp2 = "G";
        }

        if(temp1.compareTo("0") >=0 && temp1.compareTo("6") <= 0 && temp2.compareTo("A") >=0 && temp2.compareTo("G") <=0){
            tile.setLocation(temp2+temp1);
            if(isValidPlacementSequence(totalPlacement+tile.getPlacement())
                    && canUseSpecialTiles == true && usedSpecialTiles<3){
                canUseSpecialTiles = false;
                usedSpecialTiles++;
                randomTilesGroup.getChildren().remove(tile);
                specialTilesGroup.getChildren().remove(tile);
                totalPlacement += tile.getPlacement();
                makePlacement(totalPlacement);
            }
            else {
                tile.setX(xOriginal);
                tile.setY(yOriginal);
                if(canUseSpecialTiles == false){
                    Stage secondaryStage=new Stage();

                    HBox pane = new HBox();
                    pane.setPadding(new Insets(10,10,10,10));
                    Text text1 = new Text(20,20,"You can use no more than one special tiles each round!");
                    text1.setFont(Font.font("Courier", FontWeight.BOLD, 15));
                    text1.setFill(Color.BLACK);

                    pane.getChildren().add(text1);
                    Scene scoreScene = new Scene(pane,600,100);
                    pane.setAlignment(Pos.CENTER);
                    secondaryStage.setScene(scoreScene);
                    secondaryStage.setTitle("Warning");
                    secondaryStage.show();
                }
                else if(usedSpecialTiles >= 3){
                    Stage secondaryStage=new Stage();

                    HBox pane = new HBox();
                    pane.setPadding(new Insets(10,10,10,10));
                    Text text1 = new Text(20,20,"You can use maximum of three special tiles!");
                    text1.setFont(Font.font("Courier", FontWeight.BOLD, 15));
                    text1.setFill(Color.BLACK);

                    pane.getChildren().add(text1);
                    Scene scoreScene = new Scene(pane,600,100);
                    pane.setAlignment(Pos.CENTER);
                    secondaryStage.setScene(scoreScene);
                    secondaryStage.setTitle("Warning");
                    secondaryStage.show();
                }
            }
        }
        else {
            tile.setX(xOriginal);
            tile.setY(yOriginal);
        }
    }

    /**
     * Show the score of the given placement
     *
     * @param totalPlacement A string representing placement of all tiles
     *
     * @author Hengjia Zhang
     */
    private void showScore(String totalPlacement){
        Stage secondaryStage=new Stage();

        HBox pane = new HBox();
        pane.setPadding(new Insets(10,10,10,10));
        Text text1 = new Text(20,20,"Your Final Score is: ");
        text1.setFont(Font.font("Courier", 25));

        Text text2 = new Text(20,20,""+getBasicScore(totalPlacement));
        text2.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC,25));
        text2.setFill(Color.BLUE);

        pane.getChildren().add(text1);
        pane.getChildren().add(text2);
        Scene scoreScene = new Scene(pane,500,100);
        pane.setAlignment(Pos.CENTER);
        secondaryStage.setScene(scoreScene);
        secondaryStage.show();
    }

    /**
     * Show the score of the given placement
     *
     * @param totalPlacement A string representing placement of all tiles
     * @param totalPlacementAI A string representing placement of all tiles for AI
     *
     * @author Hengjia Zhang
     */
    private void showScoreAI(String totalPlacement,String totalPlacementAI){
        Stage secondaryStage=new Stage();

        HBox pane = new HBox();
        pane.setPadding(new Insets(10,10,10,10));
        Text text1 = new Text(20,20,"Your Final Score is: \nAI's Final Score is: ");
        text1.setFont(Font.font("Courier", 25));

        Text text2 = new Text(20,20,""+getBasicScore(totalPlacement)+"\n"+getBasicScore(totalPlacementAI));
        text2.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC,25));
        text2.setFill(Color.BLUE);

        pane.getChildren().add(text1);
        pane.getChildren().add(text2);
        Scene scoreScene = new Scene(pane,500,100);
        pane.setAlignment(Pos.CENTER);
        secondaryStage.setScene(scoreScene);
        secondaryStage.show();
    }

    /**
     * Create buttons aimed at show scores
     *
     * @author Hengjia Zhang
     */
    private void finished(){
        Button button = new Button("Get Scores ");
        button.setOnAction(e -> showScore(totalPlacement));

        HBox hb = new HBox();
        hb.getChildren().add(button);
        hb.setSpacing(10);
        hb.setLayoutX(70);
        hb.setLayoutY(140);
        controls.getChildren().add(hb);
    }

    /**
     * Create buttons aimed at show  both AI's and player's scores
     *
     * @author Hengjia Zhang
     */
    private void finishedAI(){
        Button button = new Button("Get Scores ");
        button.setOnAction(e -> showScoreAI(totalPlacement,totalPlacementAI));

        HBox hb = new HBox();
        hb.getChildren().add(button);
        hb.setSpacing(10);
        hb.setLayoutX(70);
        hb.setLayoutY(140);
        controls.getChildren().add(hb);
    }

    /**
     * Show which round it is
     *
     * @author Hengjia Zhang
     */
    private void showRound(){
        root.getChildren().remove(showRoundBox);
        showRoundBox.getChildren().clear();
        showRoundBox.setLayoutX(50);
        showRoundBox.setLayoutY(50);

        showRoundBox.setPadding(new Insets(10,10,10,10));
        Text text1 = new Text(20,20,"Round ");
        text1.setFont(Font.font("Courier", 20));

        Text text2 = new Text(20,20,""+round);
        text2.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC,20));
        text2.setFill(Color.BLUE);

        showRoundBox.getChildren().add(text1);
        showRoundBox.getChildren().add(text2);

        root.getChildren().add(showRoundBox);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        VBox choiceBox = new VBox();


        //add three buttons for choice
        Button testButton = new Button("Test");
        testButton.setOnAction(e ->{
            Scene testScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
            root.getChildren().add(controls);
            root.getChildren().add(tilesInBoard);
            setBackgroud();
            makeControls();
            primaryStage.setScene(testScene);
        });
        choiceBox.getChildren().add(testButton);

        Button singlePlayerButton = new Button("SinglePlayer");
        singlePlayerButton.setOnAction(e -> {
            Scene singlePlayerScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
            root.getChildren().add(controls);
            root.getChildren().add(tilesInBoard);
            root.getChildren().add(randomTilesGroup);
            root.getChildren().add(specialTilesGroup);
            setBackgroud();
            //set and control of random tiles
            showRound();
            generateRandomTiles();
            controlRandomTiles();
            dragRandomTiles();
            //set and control of random tiles
            generateSpecialTiles();
            controlSpecialTiles();
            dragSpecialTiles();
            //begin the first round
            placeFourTiles(generateDiceRoll());
            //add finish button
            finished();
            primaryStage.setScene(singlePlayerScene);
        });
        choiceBox.getChildren().add(singlePlayerButton);

        Button playWithAIButton = new Button("Play With AI");
        playWithAIButton.setOnAction(e->{
            Scene singlePlayerScene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
            root.getChildren().add(controls);
            root.getChildren().add(tilesInBoard);
            root.getChildren().add(randomTilesGroup);
            root.getChildren().add(specialTilesGroup);
            setBackgroud();
            //set and control of random tiles
            showRound();
            generateRandomTilesAI();
            controlRandomTiles();
            dragRandomTiles();
            //set and control of random tiles
            generateSpecialTiles();
            controlSpecialTiles();
            dragSpecialTiles();
            //begin the first round
            diceRoll = generateDiceRoll();
            placeFourTiles(diceRoll);
            totalPlacementAI = generateMove(totalPlacementAI,diceRoll);
            makePlacementAI(totalPlacementAI);
            //add finish button
            finishedAI();
            primaryStage.setScene(singlePlayerScene);

            //add AI stage
            Stage aiStage = new Stage();
            rootAI.getChildren().add(tilesInBoardAI);

            //add name of AI stage
            Text text = new Text(20,20,"AI palyer");
            text.setFont(Font.font("Courier",20));
            text.setFill(Color.DARKBLUE);
            text.setLayoutX(300);
            text.setLayoutY(50);
            rootAI.getChildren().add(text);

            setBackgroudAI();

            Scene aiScene = new Scene(rootAI, VIEWER_HEIGHT, VIEWER_HEIGHT);
            aiStage.setScene(aiScene);
            aiStage.show();
        });
        choiceBox.getChildren().add(playWithAIButton);

        choiceBox.setSpacing(20);
        choiceBox.setLayoutX(VIEWER_WIDTH/2-50);
        choiceBox.setLayoutY(VIEWER_HEIGHT/2-50);
        home.getChildren().add(choiceBox);

        //add game's name
        Text text = new Text(20,20,"Railroad Ink: Deep Blue Edition");
        text.setFont(Font.font("Courier",25));
        text.setFill(Color.DARKBLUE);
        text.setLayoutX(300);
        text.setLayoutY(200);
        home.getChildren().add(text);

        Scene choiceScene = new Scene(home,VIEWER_WIDTH,VIEWER_HEIGHT);
        primaryStage.setScene(choiceScene);
        primaryStage.show();
    }
}
