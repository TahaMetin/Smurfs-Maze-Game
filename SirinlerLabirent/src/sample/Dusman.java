package sample;

public class Dusman extends Karakter {
    int dusmanID,kapiID;
    String dusmanAdi;
    String dusmanTur;
boolean hedefeUlasti=false;
    public Dusman(String ad, int x, int y, int dusmanID, String dusmanAdi, String dusmanTur) {
        super(ad, x, y);
        this.dusmanID = dusmanID;
        this.dusmanAdi = dusmanAdi;
        this.dusmanTur = dusmanTur;
    }

    public Dusman() {
    }

    public Dusman( int x, int y) {
        super(x, y);
    }

    public int getDusmanID() {
        return dusmanID;
    }

    public void setDusmanID(int dusmanID) {
        this.dusmanID = dusmanID;
    }

    public String getDusmanAdi() {
        return dusmanAdi;
    }

    public void setDusmanAdi(String dusmanAdi) {
        this.dusmanAdi = dusmanAdi;
    }

    public String getDusmanTur() {
        return dusmanTur;
    }

    public void setDusmanTur(String dusmanTur) {
        this.dusmanTur = dusmanTur;
    }
}
