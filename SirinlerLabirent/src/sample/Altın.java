package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Altın extends Obje {
    private ImageView altinIV;
    public Altın(int x, int y) throws FileNotFoundException {
        super(x, y);
        Image altin = new Image(new FileInputStream("altin.png"));
        Lokasyon lokasyon = XYToScreen(x, y);
        altinIV = new ImageView(altin);
        altinIV.setX(lokasyon.x);
        altinIV.setY(lokasyon.y);
        altinIV.setFitWidth(65);
        altinIV.setFitHeight(65);
    }

    ImageView GetIV(){
        return altinIV;
    }
}
