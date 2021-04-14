package sample;

import javafx.scene.text.Text;

public class Oyuncu extends Karakter {

    int oyuncuID;
    int skor;
    String oyuncuAdi;
    String oyuncuTur;
    Text skorText;


    public Oyuncu(String ad, int x, int y, int oyuncuID, int skor, String oyuncuAdi, String oyuncuTur) {
        super(ad, x, y);
        this.oyuncuID = oyuncuID;
        this.skor = skor;
        this.oyuncuAdi = oyuncuAdi;
        this.oyuncuTur = oyuncuTur;
    }

    public Oyuncu() {
    }

    public Oyuncu(int oyuncuID, int skor, String oyuncuAdi, String oyuncuTur) {
        this.oyuncuID = oyuncuID;
        this.skor = skor;
        this.oyuncuAdi = oyuncuAdi;
        this.oyuncuTur = oyuncuTur;
    }
    public void setSkorText(Text skorText) {
        this.skorText = skorText;
    }

    public int getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(int oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public int getSkor() {
        return skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
        if(skor<=0){
            skorText.setText("Kaybettin");
            skorText.setX(300);
            skorText.setY(400);
        }
        else{
            skorText.setX(900);
            skorText.setY(150);
            skorText.setText(Integer.toString(skor));

        }
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;
    }

    public String getOyuncuTur() {
        return oyuncuTur;
    }

    public void setOyuncuTur(String oyuncuTur) {
        this.oyuncuTur = oyuncuTur;
    }

    void PuaniGoster(){

    }
}
