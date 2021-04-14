package sample;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    File file = new File("harita.txt");
    Image aGirisi,bGirisi,cGirisi,dGirisi, sirine,gozlukluSirin,tembelSirin;
    List<ImageView > kapilarIV = new ArrayList<ImageView >();
    int karakterSecimi; // 2 -> gözlüklü, 1 -> tembel;
    int hamleSayar=0;
    int random;
    ArrayList<Rectangle> cizilenYollar = new ArrayList<Rectangle>();
    {
        try {
            aGirisi = new Image(new FileInputStream("aGirisi.jpg"));
            bGirisi = new Image(new FileInputStream("bGirisi.jpg"));
            cGirisi = new Image(new FileInputStream("cGirisi.jpg"));
            dGirisi = new Image(new FileInputStream("dGirisi.jpg"));
            sirine = new Image(new FileInputStream("şirine.jpg"));
            gozlukluSirin = new Image(new FileInputStream("gözlüklü şirin.gif"));
            tembelSirin = new Image(new FileInputStream("tembel şirin.jpg"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    ImageView aGirisiIV = new ImageView(aGirisi);
    ImageView bGirisiIV = new ImageView(bGirisi);
    ImageView cGirisiIV = new ImageView(cGirisi);
    ImageView dGirisiIV = new ImageView(dGirisi);
    ImageView sirineIV = new ImageView(sirine);
    ImageView gozlukluSirinIV = new ImageView(gozlukluSirin);
    ImageView tembelSirinIV = new ImageView(tembelSirin);

    String st;
    String[] bolucu;
    List<String> karakterler = new ArrayList<String>(), kapilar = new ArrayList<String>();
    ArrayList<Dusman> dusmanlar= new ArrayList<Dusman>(2);
    ArrayList<Obje> objeler = new ArrayList<Obje>();
    int kapiNoktasi;
    int[][] labirent = new int[11][13];
    int j = 0;
    Oyuncu oyundakiKarakter;
    Group root = new Group();
    Scene scene = new Scene(root, 1100, 750);
    Text text = new Text();
    boolean firstTime=true;

    ArrayList<Lokasyon> tumLokasyonlar = new ArrayList<Lokasyon>();
    ArrayList<ArrayList<Integer>> adj;
    int noOfVertices, source, dest;
    int yolKesici=1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        kapilarIV.add(aGirisiIV);
        kapilarIV.add(bGirisiIV);
        kapilarIV.add(cGirisiIV);
        kapilarIV.add(dGirisiIV);

        TilePane r = new TilePane();

        gozlukluSirinIV.setPreserveRatio(true);
        tembelSirinIV.setPreserveRatio(true);

        Button buttonTembelSirin= new Button();
        Button buttonGozlukluSirin= new Button();

        tembelSirinIV.setFitHeight(250);
        tembelSirinIV.setFitWidth(250);
        gozlukluSirinIV.setFitHeight(250);
        gozlukluSirinIV.setFitWidth(250);
        buttonTembelSirin.setGraphic(tembelSirinIV);
        buttonGozlukluSirin.setGraphic(gozlukluSirinIV);
        buttonGozlukluSirin.setPrefSize(250, 250);
        buttonTembelSirin.setPrefSize(250, 250);
        r.getChildren().addAll(buttonGozlukluSirin,buttonTembelSirin);
        r.setAlignment(Pos.CENTER);
        Scene scene0 = new Scene(r,600,300);
        primaryStage.setScene(scene0);
        primaryStage.show();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) { // dosyayı okur
            try {
                if ((st = br.readLine()) == null) break;
                if (Character.toLowerCase(st.charAt(0)) == 'k') {
                    bolucu = st.split(",");
                    karakterler.add(bolucu[0].split(":")[1]);
                    kapilar.add(bolucu[1].split(":")[1]);

                } else {
                    for (int i = 0; i < 13; i++) {
                        labirent[j][i] = Integer.parseInt(st.split("\t", 13)[i]);
                    }
                    j++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 20; i < 800; i+=65) {                  // çizgileri çizer
            Line line = new Line(20, i, 865, i);
            root.getChildren().add(line);
        }
        for (int i = 20; i < 930; i+=65) {
            Line line = new Line(i, 20, i, 735);
            root.getChildren().add(line);
        }

        int kapiSayaci=0;
        for (int i = 20,x=0; x < 11; i+=65,x++) {    // duvarları ve kapıları çizer
            for (int j = 20,y=0; y < 13; j+=65,y++) {
                if(labirent[x][y]==0){
                    Rectangle square = new Rectangle(j, i, 65, 65);
                    square.setFill(Color.GRAY);
                    root.getChildren().add(square);
                }else if(x==0||y==0||x==10){
                    kapilarIV.get(kapiSayaci).setX(j);
                    kapilarIV.get(kapiSayaci).setY(i);
                    kapilarIV.get(kapiSayaci).setFitHeight(65);
                    kapilarIV.get(kapiSayaci).setFitWidth(65);
                    kapilarIV.get(kapiSayaci).setPreserveRatio(true);
                    root.getChildren().add(kapilarIV.get(kapiSayaci));
                    kapiSayaci++;
                    noOfVertices++;
                }else{
                    noOfVertices++;
                }
            }
        }
        sirineIV.setX(870);// şirineyi çizer
        sirineIV.setY(420);
        sirineIV.setFitHeight(200);
        sirineIV.setFitWidth(200);
        root.getChildren().add(sirineIV);




        for (int i = 0; i < karakterler.size(); i++) {  // düsmanlari olusturur
            Lokasyon kapi = new Lokasyon();
            switch (kapilar.get(i)) {   // dusmanın hangi kapıdan dogacagını ayırt eder
                case "A": // 215,20
                    kapi.x =3;
                    kapi.y =0;
                    kapiNoktasi =0;
                    break;
                case "B":   //670,20
                    kapi.x =10;
                    kapi.y =0;
                    kapiNoktasi =1;
                    break;
                case "C":   //20,345
                    kapi.x =0;
                    kapi.y =5;
                    kapiNoktasi =33;
                    break;
                case "D":   //215,670
                    kapi.x =3;
                    kapi.y =10;
                    kapiNoktasi =77;
                    break;
            }
            if (karakterler.get(i).equals("Gargamel") ) {   // dusman turunu ayırt eder
                Gargamel gargamel = new Gargamel(kapi.x,kapi.y);
                gargamel.kapiID= kapiNoktasi;
                dusmanlar.add(gargamel);
                root.getChildren().add(gargamel.getIV());
            } else { // azman
                Azman azman = new Azman(kapi.x,kapi.y);
                azman.kapiID= kapiNoktasi;
                dusmanlar.add(azman);
                root.getChildren().add(azman.getIV());
            }
        }

        for (int i =0; i < 11;i++) {
            for (int j = 0; j < 13;j++) {
                if(labirent[i][j]==0) continue;
                Lokasyon lokasyon = new Lokasyon(i,j);
                tumLokasyonlar.add(lokasyon);
            }
        }
        adj =new ArrayList<ArrayList<Integer>>(noOfVertices);
        for (int i =0; i < 11;i++) {          // tabloyu, noktaları ve komşularını tutan bir graph a çevirir
            for (int j = 0; j < 13;j++) {
                if(labirent[i][j]==0) continue;
                ArrayList<Integer> list = new ArrayList<>();
                if(i!=0 && labirent[i-1][j]!=0){
                    for (int k = 0; k < tumLokasyonlar.size(); k++) {
                        if (tumLokasyonlar.get(k).y == j && tumLokasyonlar.get(k).x == i-1) {
                            list.add(k);
                        }
                    }
                }
                if(i!=10 && labirent[i+1][j]!=0){
                    for (int k = 0; k < tumLokasyonlar.size(); k++) {
                        if (tumLokasyonlar.get(k).y == j && tumLokasyonlar.get(k).x == i+1) {
                            list.add(k);
                        }
                    }
                }
                if(j!=0 && labirent[i][j-1]!=0){
                    for (int k = 0; k < tumLokasyonlar.size(); k++) {
                        if (tumLokasyonlar.get(k).y == j-1 && tumLokasyonlar.get(k).x == i) {
                            list.add(k);
                        }
                    }
                }
                if(j!=12 && labirent[i][j+1]!=0){
                    for (int k = 0; k < tumLokasyonlar.size(); k++) {
                        if (tumLokasyonlar.get(k).y == j+1 && tumLokasyonlar.get(k).x == i) {
                            list.add(k);
                        }
                    }
                }

                adj.add(list);
            }
        }


        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {      // tuş basımlarını algılar

            if(key.getCode()== KeyCode.UP) {
                if(labirent[oyundakiKarakter.y -1][oyundakiKarakter.x]!=0){
                    MoveUp(oyundakiKarakter.getIV());
                    oyundakiKarakter.y =oyundakiKarakter.y -1;
                    hamleSayar++;
                    DusmaniHaraketEttir();
                    EtkiletisimVarmi();
                }
            }
            if(key.getCode()== KeyCode.DOWN) {
                if(labirent[oyundakiKarakter.y +1][oyundakiKarakter.x]!=0) {
                    MoveDown(oyundakiKarakter.getIV());
                    oyundakiKarakter.y =oyundakiKarakter.y +1;
                    hamleSayar++;
                    DusmaniHaraketEttir();
                    EtkiletisimVarmi();
                }
            }
            if(key.getCode()== KeyCode.LEFT) {
                if(labirent[oyundakiKarakter.y][oyundakiKarakter.x -1]!=0) {
                    MoveLeft(oyundakiKarakter.getIV());
                    oyundakiKarakter.x =oyundakiKarakter.x -1;
                    hamleSayar++;
                    DusmaniHaraketEttir();
                    EtkiletisimVarmi();
                }
            }
            if(key.getCode()== KeyCode.RIGHT) {
                if(labirent[oyundakiKarakter.y][oyundakiKarakter.x +1]!=0) {
                    MoveRight(oyundakiKarakter.getIV());
                    oyundakiKarakter.x =oyundakiKarakter.x +1;
                    hamleSayar++;
                    DusmaniHaraketEttir();
                    EtkiletisimVarmi();
                }
            }

        });


        primaryStage.setTitle("Şirinler");// kareler 65x65 , labirent boyutu 845x715 (13x11)
        EventHandler<ActionEvent> eventGozluklu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                karakterSecimi=2;
                OyuncuyuOlustur();
                for (Dusman d : dusmanlar) {
                    if(d instanceof Gargamel){
                        yolKesici=2;
                        EnKısaYoluCiz(d);
                        yolKesici++;
                    }else
                        EnKısaYoluCiz(d);
                }
                primaryStage.setScene(scene);
                primaryStage.show();
                PuanYazisiniOlustur();
                AltinOlustur();
                MantarOlustur();
            }
        };
        EventHandler<ActionEvent> eventTembel = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                karakterSecimi=1;
                OyuncuyuOlustur();
                for (Dusman d : dusmanlar) {
                    if(d instanceof Gargamel){
                        yolKesici=2;
                        EnKısaYoluCiz(d);
                        yolKesici++;
                    }else
                        EnKısaYoluCiz(d);

                }
                primaryStage.setScene(scene);
                primaryStage.show();
                PuanYazisiniOlustur();
                AltinOlustur();
                MantarOlustur();
            }
        };


        buttonGozlukluSirin.setOnAction(eventGozluklu);
        buttonTembelSirin.setOnAction(eventTembel);
    }

void PuanYazisiniOlustur(){
    text.setText(Integer.toString(oyundakiKarakter.skor));
    text.setX(900);
    text.setY(150);
    text.setFont(new Font("Arial",100));
    root.getChildren().add(text);
}

    void AltinOlustur(){ // 7-9 saniye aralıgında altın olusturur
        random = new Random().nextInt(3)+7;
        System.out.println(random + " saniye sonra altin olusacak");
        PauseTransition pause = new PauseTransition(Duration.seconds(random));
            pause.setOnFinished(event ->{
                System.out.println("altin olustu");
                for (int i = 0; i < 5; i++) {
                    boolean farkli=true;
                    int random = new Random().nextInt(tumLokasyonlar.size());
                    if(random==0||random==1||random==33||random==60) random+=2; // lokasyonun kapılardan biri olmadıgına emin olur
                    else if(random==77) random--;

                    Lokasyon lokasyon= tumLokasyonlar.get(random);
                    for (Obje obje :objeler) {
                        if (obje.x == lokasyon.y && obje.y == lokasyon.x){
                            farkli=false;
                        }
                    }
                    if(lokasyon.y!= oyundakiKarakter.x&&lokasyon.x!= oyundakiKarakter.y&&farkli){
                        Altın altin = null;
                        try {altin = new Altın(lokasyon.y, lokasyon.x);} catch (FileNotFoundException e) {e.printStackTrace();}
                        root.getChildren().add(altin.GetIV());
                        objeler.add(altin);
                    }
                    else i--;
                }
                AltinYokEt();
        });
        pause.play();
    }
    void AltinYokEt(){ // 5 sn bekleyip altınları yok eder
        System.out.println("5 saniye sonra altin yok olacak");
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
            pause.setOnFinished(event ->{
                System.out.println("altin yok oldu");
                int i= objeler.size()-1;
                while  (i>=0) {
                    if(objeler.get(i) instanceof Altın){
                        ((Altın) objeler.get(i)).GetIV().setVisible(false);
                        objeler.remove(objeler.indexOf(objeler.get(i)));
                        i= objeler.size()-1;
                    }else
                        i--;
                }

                AltinOlustur();
        });
        pause.play();
    }
    void MantarOlustur(){   // mantarı 13-17 arasında saniye sonra olusturur ve yok etme fonksiyonunu cagırır
        random = new Random().nextInt(5)+13;
        System.out.println(random + " saniye sonra mantar olusacak");
        PauseTransition pause = new PauseTransition(Duration.seconds(random));
        pause.setOnFinished(event ->{
            System.out.println("mantar olustu");
            for (int i = 0; i < 1; i++) {

                boolean farkli=true;

                int random = new Random().nextInt(tumLokasyonlar.size());
                if(random==0||random==1||random==33||random==60) random+=2; // lokasyonun kapılardan biri olmadıgına emin olur
                else if(random==77) random--;

                Lokasyon lokasyon= tumLokasyonlar.get(random);
                for (Obje obje :objeler) {
                    if (obje.x == lokasyon.y && obje.y == lokasyon.x){//objelerin ust uste binmemesini saglar
                        farkli=false;
                    }
                }
                    if(lokasyon.x!= oyundakiKarakter.y&&lokasyon.y!= oyundakiKarakter.x&&farkli){ // mantarın karakterin ustunde olusmamasını saglar
                        Mantar mantar = null;
                        try {mantar = new Mantar(lokasyon.y, lokasyon.x);} catch (FileNotFoundException e) {e.printStackTrace();}
                        root.getChildren().add(mantar.GetIV());
                        objeler.add(mantar);
                    }else   i--;
            }

            MantariYokEt();
        });
        pause.play();
    }
    void MantariYokEt(){    // 7 sn bekleyip mantarı yok eder vve mantarı olusturma fonksiyonunu cagırır
        System.out.println("7 saniye sonra mantar yok olacak");
        PauseTransition pause = new PauseTransition(Duration.seconds(7));
        pause.setOnFinished(event ->{
            System.out.println("mantar yok oldu");
            ArrayList<Integer> yokedilecekAltinİndexleri=new ArrayList<Integer>();
            for (Obje obje:objeler) {
                if(obje instanceof Mantar){
                    ((Mantar) obje).GetIV().setVisible(false);
                    yokedilecekAltinİndexleri.add(objeler.indexOf(obje));
                }
            }
            for (int i :yokedilecekAltinİndexleri) {
                if(objeler.size()>i)
                    objeler.remove(i);
            }
            MantarOlustur();
        });
        pause.play();
    }


    void EtkiletisimVarmi(){ // karakterimiz düşman ya da nesne ile üst üste mi diye kontrol eder
        for (Dusman dusman :dusmanlar) {    // ust uste gelinen dusman var mı diye kontrol eder
            if (oyundakiKarakter.x == dusman.x && oyundakiKarakter.y == dusman.y) {
                dusman.hedefeUlasti=true;
                if(dusman instanceof Gargamel)oyundakiKarakter.setSkor(oyundakiKarakter.skor-=15);
                else    oyundakiKarakter.setSkor(oyundakiKarakter.skor-=5);
            }
        }
        for (int i=0;i<objeler.size();i++) {// ust uste gelinen obje var mı diye kontrol eder
            if (objeler.get(i).y == oyundakiKarakter.y && objeler.get(i).x == oyundakiKarakter.x) {
                if(objeler.get(i) instanceof Mantar){
                    oyundakiKarakter.setSkor(oyundakiKarakter.skor+=50);    //mantar  ile ust uste gelindiginde calısır
                    ((Mantar) objeler.get(i)).GetIV().setVisible(false);
                    objeler.remove(i);
                    break;
                }
                else{
                    ((Altın) objeler.get(i)).GetIV().setVisible(false);     //altın  ile ust uste gelindiginde calısır
                    oyundakiKarakter.setSkor(oyundakiKarakter.skor+=5);
                    objeler.remove(i);
                    break;
                }
            }
        }
        if(oyundakiKarakter.x==12&&oyundakiKarakter.y==7){// cıkıs kapısına geldiginde
            text.setText("Kazandın");
            text.setX(300);
            text.setY(400);
        }

    }
    void DusmaniHaraketEttir(){ // dusmanın en kısa yolunu hesaplatır ve ilerletir
        if(hamleSayar%karakterSecimi==0){
            YollariSil();
            for (Dusman d : dusmanlar) {
                EnKısaYoluCiz(d);
                EnKısaYoldaIlerle(d);
            }
        }
    }
    void OyuncuyuOlustur()  {   //secime gore oyuncuyu olusturur
        if(karakterSecimi==2){
            try {
                oyundakiKarakter = new GozlukluSirin();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            try {
                oyundakiKarakter = new TembelSirin();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        oyundakiKarakter.setSkorText(text);
        root.getChildren().add(oyundakiKarakter.getIV());
    }

    void EnKısaYoldaIlerle(Dusman dusman){  // hesaplanan en kısa yolda ilerler
        int tekrar=1;
        if(dusman instanceof Gargamel) tekrar=2;    // dusman turu gargamel ise 2 adım ilerlemesini saglar
        for (int i = 0; i < tekrar; i++) {
            dusman.path.remove(dusman.path.size()-1);
            if(dusman.path.isEmpty())return;
            dusman.y= tumLokasyonlar.get(dusman.path.get(dusman.path.size()-1)).x;
            dusman.x= tumLokasyonlar.get(dusman.path.get(dusman.path.size()-1)).y;
            dusman.getIV().setX(dusman.getScreenX());
            dusman.getIV().setY(dusman.getScreenY());
        }

    }

    void EnKısaYoluCiz(Dusman dusman){

        for (int k = 0; k < tumLokasyonlar.size(); k++) {       //source degiskenine dusmanın konumunun denk geldiği noktayı atar
            if (tumLokasyonlar.get(k).x == dusman.y && tumLokasyonlar.get(k).y == dusman.x) {
                source =k;
                break;
            }
        }
        if(dusman.x== tumLokasyonlar.get(dusman.kapiID).y && dusman.y== tumLokasyonlar.get(dusman.kapiID).x)
        dusman.hedefeUlasti=false;  //eger dusman baslangıc kapısına geri donduyse tekrar cıkması icin hedefe ulasmadı olarak ayarlar

        if(dusman.hedefeUlasti){    // dusman oyuncuya ulastıgında cıktıgı kapıya tekrar yonelmesi icin hedefi degistirir
            dest = dusman.kapiID;
        }else { // dusman oyuncuya ulasmadıysa hedefi oyuncu olarak degistirir
            for (int k = 0; k < tumLokasyonlar.size(); k++) {
                if (tumLokasyonlar.get(k).x == oyundakiKarakter.y && tumLokasyonlar.get(k).y == oyundakiKarakter.x) {
                    dest = k;
                    break;
                }
            }
        }

        int pred[] = new int[noOfVertices];     //her nokta icin gecilen noktaları depolar
        int dist[] = new int[noOfVertices];     //her nokta icin uzaklıgı depolar

        BFS(adj, source, dest, noOfVertices, pred, dist);

        LinkedList<Integer> path = new LinkedList<Integer>();         // yolu tutan linkedlist
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }
        dusman.EnKısaYol(path);

        for (int i = path.size()-yolKesici; i >= 0; i--) {                                      // ekrana yolu cizer
            Lokasyon lokasyon= XYToScreen(tumLokasyonlar.get(path.get(i)).x,tumLokasyonlar.get(path.get(i)).y);
            Rectangle square = new Rectangle(lokasyon.y,lokasyon.x, 65, 65);
            square.setFill(Color.GREEN);
            square.setOpacity(0.2);
            root.getChildren().add(square);
            cizilenYollar.add(square);
            oyundakiKarakter.getIV().toFront(); // oyuncunun onde gozukmesini daglar
            for (Dusman d : dusmanlar) {
                d.getIV().toFront();    // dusmanların ekranda onde gozukmesini saglar
            }

        }
    }
    void YollariSil(){  // ekrana cizilen eski en kısa yolu siler
        for (Rectangle r : cizilenYollar) {
            r.setVisible(false);
        }
    }
    void BFS(ArrayList<ArrayList<Integer>> adj, int src,
                               int dest, int noOfVertices, int pred[], int dist[])      //en kısa yolu hesaplar
    {
        LinkedList<Integer> queue = new LinkedList<Integer>();

        boolean visited[] = new boolean[noOfVertices];

        for (int i = 0; i < noOfVertices; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
        // bfs algoritmasi
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    if (adj.get(u).get(i) == dest) // hedefe vardıgında donguden cıkarır
                        return;
                }
            }
        }
        return;
    }
    Lokasyon XYToScreen(int x,int y){ // 11x13 tablo kordinatlarının ekranda denk dustugu yeri hesaplar
        Lokasyon lokasyon = new Lokasyon();
        lokasyon.x = 20+65*x;
        lokasyon.y = 20+65*y;
        return lokasyon;
    }
    void MoveUp(ImageView ıv){
        ıv.setY(ıv.getY()-65);
    }
    void MoveDown(ImageView ıv){
        ıv.setY(ıv.getY()+65);
    }
    void MoveRight(ImageView ıv){ ıv.setX(ıv.getX()+65); }
    void MoveLeft(ImageView ıv){
        ıv.setX(ıv.getX()-65);
    }
    public static void main(String[] args) {launch(args);}
}