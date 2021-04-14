package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Azman extends Dusman {
    private Image azman;
    public Azman( int x, int y) throws FileNotFoundException {
        super(x, y);
        azman = new Image(new FileInputStream("azman.png"));
        Lokasyon lokasyon = XYToScreen(x, y);
        karakterIV = new ImageView(azman);
        karakterIV.setX(lokasyon.x);
        karakterIV.setY(lokasyon.y);
        karakterIV.setFitWidth(65);
        karakterIV.setFitHeight(65);
    }

    ImageView getIV(){
        return karakterIV;
    }
}
