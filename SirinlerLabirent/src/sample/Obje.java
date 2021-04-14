package sample;

public class Obje {
    int x,y;

    public Obje(int x, int y) {
        this.x = x;
        this.y = y;
    }
    Lokasyon XYToScreen(int x,int y){
        Lokasyon lokasyon = new Lokasyon();
        lokasyon.x = 20+65*x;
        lokasyon.y = 20+65*y;
        return lokasyon;
    }
}
