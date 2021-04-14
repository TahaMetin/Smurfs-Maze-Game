package sample;

import javafx.scene.image.ImageView;

import java.util.LinkedList;

public class Karakter {
    ImageView karakterIV;
    String Ad;
    int x, y;
    LinkedList<Integer> path;
    public Karakter(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Karakter(String Ad, int x, int y) {
        this.Ad = Ad;
        this.x = x;
        this.y = y;
    }

    public Karakter() {
    }

  void EnKısaYol(LinkedList<Integer> path){
      this.path = path;

  }
    ImageView getIV(){
        return karakterIV;
    }
    int getScreenX(){
        return x*65+20;
    }
    int getScreenY(){
        return y*65+20;
    }
    Lokasyon XYToScreen(int x,int y){
        Lokasyon lokasyon = new Lokasyon();
        lokasyon.x = 20+65*x;
        lokasyon.y = 20+65*y;
        return lokasyon;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
