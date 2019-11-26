package comp1110.ass2.gui;

import comp1110.ass2.Tile;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TileView extends ImageView {
    private static final String URI_BASE = "assets/";

    private Image img;
    private String type;
    private String location;
    private int orientation;
    private String placement;

    TileView(){
        this.type = "A1";
        this.location = "";
        this.orientation = 0;
        this.placement = this.type + this.location + this.orientation;
        this.img = new Image(Viewer.class.getResource(TileView.URI_BASE + this.type +".png").toString());
        this.setImage(this.img);
        this.setFitHeight(100*0.7);
        this.setFitWidth(100*0.7);
    }

    TileView(String type){
        this.type = type;
        this.location = "";
        this.orientation = 0;
        this.placement = this.type + this.location + this.orientation;
        this.img = new Image(Viewer.class.getResource(TileView.URI_BASE + this.type +".png").toString());
        this.setImage(this.img);
    }

    public void setType(String type){
        this.type = type;
        this.location = "";
        this.orientation = 0;
        this.placement = this.type + this.location + this.orientation;
        this.img = new Image(Viewer.class.getResource(TileView.URI_BASE + this.type +".png").toString());
        this.setImage(this.img);
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public int getOrientation() {
        return orientation;
    }

    public String getPlacement() {
        return placement;
    }

    public Image getImg() {
        return img;
    }

    public void rotate(){

        if(this.getRotate() == 0){
            this.setRotate(90);
        }
        else if(this.getRotate() == 90){
            this.setRotate(180);
        }
        else if(this.getRotate() == 180){
            this.setRotate(270);
        }
        else if(this.getRotate() == 270){
            this.setRotate(0);
        }
        orientation ++;
        if(orientation == 4 || orientation == 7){
            orientation -=4;
        }
    }

    public void mirror(){
        if(this.getRotate() == 0 || this.getRotate() == 180){
            this.setScaleX(-this.getScaleX());
        }
        else if(this.getRotate() == 90 || this.getRotate() == 270){
            this.setScaleY(-this.getScaleY());
        }
        if(orientation == 0){
            orientation = 4;
        }
        else if(orientation == 1){
            orientation = 7;
        }
        else if(orientation == 2){
            orientation = 6;
        }
        else if(orientation == 3){
            orientation = 5;
        }
        else if(orientation == 4){
            orientation = 0;
        }
        else if(orientation == 5){
            orientation = 3;
        }
        else if(orientation == 6){
            orientation = 2;
        }
        else if(orientation == 7){
            orientation = 1;
        }
    }
    public void setOrientation(int orientation){
        this.orientation = orientation;
    }

    public void setLocation(String location){
        this.location = location;
        this.placement = this.type + this.location + this.orientation;
    }


}
