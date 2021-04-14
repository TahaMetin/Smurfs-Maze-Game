package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TembelSirin extends Oyuncu {

    public TembelSirin(String ad, int x, int y, int oyuncuID, int skor, String oyuncuAdi, String oyuncuTur) {
        super(ad, x, y, oyuncuID, skor, oyuncuAdi, oyuncuTur);
    }

    public TembelSirin() throws FileNotFoundException {
        Image tembelSirin = new Image(new FileInputStream("tembel şirin.jpg"));
        x =6;
        y =5;
        skor=20;
        Lokasyon lokasyon = XYToScreen(x, y);
        karakterIV = new ImageView(tembelSirin);
        karakterIV.setX(lokasyon.x);
        karakterIV.setY(lokasyon.y);
        karakterIV.setFitWidth(65);
        karakterIV.setFitHeight(65);
    }
    ImageView getIV(){
        return karakterIV;
    }
}
