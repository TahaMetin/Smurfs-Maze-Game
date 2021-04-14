package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GozlukluSirin extends Oyuncu {

    public GozlukluSirin(String ad, int x, int y, int oyuncuID, int skor, String oyuncuAdi, String oyuncuTur) {
        super(ad, x, y, oyuncuID, skor, oyuncuAdi, oyuncuTur);
    }
    public GozlukluSirin() throws FileNotFoundException {
        Image gozlukluSirin = new Image(new FileInputStream("gözlüklü şirin.gif"));
        x =6;
        y =5;
        skor=20;
        Lokasyon lokasyon = XYToScreen(x, y);
        karakterIV = new ImageView(gozlukluSirin);
        karakterIV.setX(lokasyon.x);
        karakterIV.setY(lokasyon.y);
        karakterIV.setFitWidth(65);
        karakterIV.setFitHeight(65);
    }
    ImageView getIV(){
        return karakterIV;
    }
}
