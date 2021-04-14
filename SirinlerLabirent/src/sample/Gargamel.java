package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Gargamel extends Dusman {
    private Image gargamel;
    public Gargamel( int x, int y) throws FileNotFoundException {
        super(x, y);
        gargamel = new Image(new FileInputStream("gargamel.png"));
        Lokasyon lokasyon = XYToScreen(x, y);
        karakterIV = new ImageView(gargamel);
        karakterIV.setX(lokasyon.x);
        karakterIV.setY(lokasyon.y);
        karakterIV.setFitWidth(65);
        karakterIV.setFitHeight(65);
    }

    ImageView getIV(){
        return karakterIV;
    }
}
