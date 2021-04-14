package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Mantar extends Obje {
    ImageView mantarIV;
    public Mantar(int x, int y) throws FileNotFoundException {
        super(x, y);
        Image  mantar = new Image(new FileInputStream("mantar.png"));
        Lokasyon lokasyon = XYToScreen(x, y);
        mantarIV = new ImageView(mantar);
        mantarIV.setX(lokasyon.x);
        mantarIV.setY(lokasyon.y);
        mantarIV.setFitWidth(65);
        mantarIV.setFitHeight(65);

    }

    ImageView GetIV(){
        return mantarIV;
    }
}
