//dodelat tady:
//detekujZdaExistujeDanaBarvaVIntervalu


package ruzneBarvy;

import java.util.ArrayList;
import java.util.HashMap;


public class RuzneBarvyPodleOrientace {
    
    private ArrayList<HashMap<String, Integer>> sousediJinaBarva = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousediStejnaBarva = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> poleVsechBarev = new ArrayList<HashMap<String, Integer>>();
    
    private ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarev = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //obsahuje data rozdelene podle orientace a barev (patrici k plose, nebo i care - bez rozliseni)
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevVychod = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevJih = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevZapad = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevSever = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediStejnaBarvaPodleBarev = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //obsahuje data rozdelene podle orientace a barev pouze pro plochy (nerozlisuje k jake plose, jen to ze to nepatri k care)
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevVychodPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevJihPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevZapadPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevSeverPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //obsahuje data rozdelene podle orientace a barev pouze pro cary
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevVychodCary = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevJihCary = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevZapadCary = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevSeverCary = new ArrayList<ArrayList<HashMap<String, Integer>>>();
   
    
    //data kde si uchovava posledni index intervalu, kde neco nasel
    //v pristim cyklu vychazi od tohoto indexu, tak aby byl ve vyhledavani rychlejsi
    private int posledniIndexIntervalu = 0;
    
    
    public RuzneBarvyPodleOrientace(ArrayList<HashMap<String, Integer>> sousediJinaBarva, ArrayList<HashMap<String, Integer>> sousediStejnaBarva, ArrayList<HashMap<String, Integer>> poleVsechBarev, ArrayList<HashMap<String, Integer>> souradniceVsechBarev){
        
        this.sousediJinaBarva = sousediJinaBarva;
        this.sousediStejnaBarva = sousediStejnaBarva;
        this.poleVsechBarev = poleVsechBarev;
        this.souradniceVsechBarev = souradniceVsechBarev;
        
        roztridSousediPodleBarvy();
        roztridPodleOrientace();
        detekujZdaExistujeSousedOkraje();
        
        System.out.print("");
        
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousediJinaBarvaPodleBarevVychodPlochy(){
        
        return(sousediJinaBarvaPodleBarevVychodPlochy);
        
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousediJinaBarvaPodleBarevJihPlochy(){
        
        return(sousediJinaBarvaPodleBarevJihPlochy);
        
    }

    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousediJinaBarvaPodleBarevZapadPlochy(){
        
        return(sousediJinaBarvaPodleBarevZapadPlochy);
        
    }

    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousediJinaBarvaPodleBarevSeverPlochy(){
        
        return(sousediJinaBarvaPodleBarevSeverPlochy);
        
    }    
    
    
   
    private void roztridPodleOrientace(){
        
        ArrayList<HashMap<String, Integer>> sousedVychod;
        ArrayList<HashMap<String, Integer>> sousedJih;
        ArrayList<HashMap<String, Integer>> sousedZapad;
        ArrayList<HashMap<String, Integer>> sousedSever;
        
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvy;

        int orientaceSouseda;
        
        
        for (int i = 0; i < poleVsechBarev.size(); i++) {

            sousedVychod = new ArrayList<HashMap<String, Integer>>();
            sousedJih = new ArrayList<HashMap<String, Integer>>();
            sousedZapad = new ArrayList<HashMap<String, Integer>>();
            sousedSever = new ArrayList<HashMap<String, Integer>>();
            
            sousediJinaBarvaDaneBarvy = new ArrayList<HashMap<String, Integer>>();

            
            sousediJinaBarvaDaneBarvy = sousediJinaBarvaPodleBarev.get(i);
            sousedVychod = vratOrientovanyOkrajPodlePozadovaneOrientace(1, sousediJinaBarvaDaneBarvy);
            sousedJih = vratOrientovanyOkrajPodlePozadovaneOrientace(2, sousediJinaBarvaDaneBarvy);
            sousedZapad = vratOrientovanyOkrajPodlePozadovaneOrientace(3, sousediJinaBarvaDaneBarvy);
            sousedSever = vratOrientovanyOkrajPodlePozadovaneOrientace(4, sousediJinaBarvaDaneBarvy);
            
            sousediJinaBarvaPodleBarevVychod.add(sousedVychod);
            sousediJinaBarvaPodleBarevJih.add(sousedJih);
            sousediJinaBarvaPodleBarevZapad.add(sousedZapad);
            sousediJinaBarvaPodleBarevSever.add(sousedSever);
               
        }
    }
    
    
    private ArrayList<HashMap<String, Integer>> vratOrientovanyOkrajPodlePozadovaneOrientace(int orientaceSousedaPozadovana, ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvy){
        
        HashMap<String, Integer> Soused;
        int orientaceSouseda;
        ArrayList<HashMap<String, Integer>> sousedNaPozadovanouStranu = new ArrayList<HashMap<String, Integer>>(); 
     
        
        for (int i = 0; i < sousediJinaBarvaDaneBarvy.size(); i++) {
            Soused = sousediJinaBarvaDaneBarvy.get(i);
            
            orientaceSouseda = Soused.get("orientace souseda");

            //je-li soused orientovan na vychod
            if(orientaceSouseda == orientaceSousedaPozadovana){
                sousedNaPozadovanouStranu.add(Soused);
            }
            
        }
        
        return(sousedNaPozadovanouStranu);
        
    }
    
    
    //roztridi sousedy podle hlavni barvy
    private void roztridSousediPodleBarvy(){
        
        HashMap<String, Integer> RGB;
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvy = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> sousediStejnaBarvaDaneBarvy = new ArrayList<HashMap<String, Integer>>();
        
        
        int r;
        int g;
        int b;
        
        for (int i = 0; i < poleVsechBarev.size(); i++) {
            
            RGB = new HashMap<String, Integer>();
            RGB = poleVsechBarev.get(i);
            
            r = RGB.get("Red");
            g = RGB.get("Green");
            b = RGB.get("Blue");
            
          
            //sousedi jine barvy
            sousediJinaBarvaDaneBarvy = vratSousediJineBavyPodleDaneBarvy(r, g, b);
            sousediJinaBarvaPodleBarev.add(sousediJinaBarvaDaneBarvy);
            
            //zapise vsechny data pro stavajici barvu
            //ziskejDataJednotlivychPloch(sousediJinaBarvaDaneBarvy);
          
            //sousedi stejne barvy
            sousediStejnaBarvaDaneBarvy = vratSousediStejneBavyPodleDaneBarvy(r, g, b);
            sousediStejnaBarvaPodleBarev.add(sousediStejnaBarvaDaneBarvy);
            
        } 
        
        //jeste dopise bilou barvu, napr: comboboxy, checkBoxy
        r = 255;
        g = 255;
        b = 255;
        
        //sousedi jine barvy
        sousediJinaBarvaDaneBarvy = vratSousediJineBavyPodleDaneBarvy(r, g, b);
        sousediJinaBarvaPodleBarev.add(sousediJinaBarvaDaneBarvy);
        
        //sousedi stejne barvy
        sousediStejnaBarvaDaneBarvy = vratSousediStejneBavyPodleDaneBarvy(r, g, b);
        sousediStejnaBarvaPodleBarev.add(sousediStejnaBarvaDaneBarvy);
        
        System.out.print("");
        
    }
    
    
    private ArrayList<HashMap<String, Integer>> vratSousediJineBavyPodleDaneBarvy(int r, int g, int b){
    
        HashMap<String, Integer> RGB;
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvy = new ArrayList<HashMap<String, Integer>>();
        
        int red;
        int green;
        int blue;
        
        for (int i = 0; i < sousediJinaBarva.size(); i++) {
            
            RGB = new HashMap<String, Integer>();
            RGB = sousediJinaBarva.get(i);
            
            red = RGB.get("r_Hlavni");
            green = RGB.get("g_Hlavni");
            blue = RGB.get("b_Hlavni");
            
            if(i == 200){
                System.out.print("");
            }
            
            if(red == r){
                if(green == g){
                    if(blue == b){
                        sousediJinaBarvaDaneBarvy.add(RGB);
                    }
                }
            }
        }
        
        return(sousediJinaBarvaDaneBarvy);
    }
    
    
    private ArrayList<HashMap<String, Integer>> vratSousediStejneBavyPodleDaneBarvy(int r, int g, int b){
        
        HashMap<String, Integer> RGB;
        ArrayList<HashMap<String, Integer>> sousediStejnaBarvaDaneBarvy = new ArrayList<HashMap<String, Integer>>();
        
        int red;
        int green;
        int blue;
        
        for (int i = 0; i < sousediStejnaBarva.size(); i++) {
            
            RGB = new HashMap<String, Integer>();
            RGB = sousediStejnaBarva.get(i);
            
            red = RGB.get("r_Hlavni");
            green = RGB.get("g_Hlavni");
            blue = RGB.get("b_Hlavni");
            
            if(red == r){
                if(green == g){
                    if(blue == b){
                        sousediStejnaBarvaDaneBarvy.add(RGB);
                    }
                }
            }
        }
        
        return(sousediStejnaBarvaDaneBarvy);
        
    }
    
    
    //musi detekovat kde se nachazi soused
    private void detekujZdaExistujeSousedOkraje(){
        
        ArrayList<HashMap<String, Integer>> sousedVychod;
        ArrayList<HashMap<String, Integer>> sousedJih;
        ArrayList<HashMap<String, Integer>> sousedZapad;
        ArrayList<HashMap<String, Integer>> sousedSever;
        ArrayList<HashMap<String, Integer>> sousedStred;
        
        ArrayList<HashMap<String, Integer>> sousedStranaStatus = new ArrayList<HashMap<String, Integer>>();
        
        for (int i = 0; i < poleVsechBarev.size(); i++) {

            sousedVychod = new ArrayList<HashMap<String, Integer>>();
            sousedJih = new ArrayList<HashMap<String, Integer>>();
            sousedZapad = new ArrayList<HashMap<String, Integer>>();
            sousedSever = new ArrayList<HashMap<String, Integer>>();
            sousedStred = new ArrayList<HashMap<String, Integer>>();
            
            sousedVychod = sousediJinaBarvaPodleBarevVychod.get(i);
            sousedJih = sousediJinaBarvaPodleBarevJih.get(i);
            sousedZapad = sousediJinaBarvaPodleBarevZapad.get(i);
            sousedSever = sousediJinaBarvaPodleBarevSever.get(i);
            sousedStred = sousediStejnaBarvaPodleBarev.get(i);
            
            //zapise data do sousediJinaBarvaPodleBarevVychodPlochy/sousediJinaBarvaPodleBarevVychodCary
            posledniIndexIntervalu = 0;
            sousedStranaStatus = rozlisujKterePixelyOkrajeJsouPatriciKPloseNeboCare(sousedVychod, sousedStred, 1, sousedZapad, sousedSever, sousedJih);
            ZapisDataDoSousediPlochyNeboSousediCary(sousedStranaStatus, 1);
                  
            //zapise data do sousediJinaBarvaPodleBarevVychodPlochy/sousediJinaBarvaPodleBarevVychodCary
            posledniIndexIntervalu = 0;
            sousedStranaStatus = rozlisujKterePixelyOkrajeJsouPatriciKPloseNeboCare(sousedJih, sousedStred, 2, sousedSever, sousedZapad, sousedVychod);
            ZapisDataDoSousediPlochyNeboSousediCary(sousedStranaStatus, 2);
            
            //zapise data do sousediJinaBarvaPodleBarevVychodPlochy/sousediJinaBarvaPodleBarevVychodCary
            posledniIndexIntervalu = 0;
            sousedStranaStatus = rozlisujKterePixelyOkrajeJsouPatriciKPloseNeboCare(sousedZapad, sousedStred, 3, sousedVychod, sousedSever, sousedJih);
            ZapisDataDoSousediPlochyNeboSousediCary(sousedStranaStatus, 3);
            
            //zapise data do sousediJinaBarvaPodleBarevVychodPlochy/sousediJinaBarvaPodleBarevVychodCary
            posledniIndexIntervalu = 0;
            sousedStranaStatus = rozlisujKterePixelyOkrajeJsouPatriciKPloseNeboCare(sousedSever, sousedStred, 4, sousedJih, sousedZapad, sousedVychod);
            ZapisDataDoSousediPlochyNeboSousediCary(sousedStranaStatus, 4);
            
        }
        
        System.out.print("");
    }
    
    
    private void ZapisDataDoSousediPlochyNeboSousediCary(ArrayList<HashMap<String, Integer>> sousedStranaStatus, int orientace){
        
        HashMap<String, Integer> pixel;
        int status;
        boolean jednaSeOOkrajPlochy;
        
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevPlochy = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevCary = new ArrayList<HashMap<String, Integer>>();
        
        for (int i = 0; i < sousedStranaStatus.size(); i++) {
            pixel = new HashMap<String, Integer>();
            pixel = sousedStranaStatus.get(i);
            status = pixel.get("jedna se o okraj plochy");
            if(status == 1){
                jednaSeOOkrajPlochy = true;
                sousediJinaBarvaPodleBarevPlochy.add(pixel);
            }
            else {
                jednaSeOOkrajPlochy = false;
                sousediJinaBarvaPodleBarevCary.add(pixel);
            } 
        }
        
        //Prida data do sousedu plochy podle orientace, k dane barve
        //resp. cela tato metoda (ZapisDataDoSousediPlochyNeboSousediCary) je volana v ramci jedne barvy a jedne orientace
        ZapisDataDoSousediPlochyPodleOrientace(sousediJinaBarvaPodleBarevPlochy, orientace);
        ZapisPixelDoSousediCaryPodleOrientace(sousediJinaBarvaPodleBarevCary, orientace);
                
        
        System.out.print("");
        
    }
    
    
    private void ZapisDataDoSousediPlochyPodleOrientace(ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevPlochy, int orientace){
        
        if(orientace == 1){
            sousediJinaBarvaPodleBarevVychodPlochy.add(sousediJinaBarvaPodleBarevPlochy);
        }
        if(orientace == 2){
            sousediJinaBarvaPodleBarevJihPlochy.add(sousediJinaBarvaPodleBarevPlochy);
        }
        if(orientace == 3){
            sousediJinaBarvaPodleBarevZapadPlochy.add(sousediJinaBarvaPodleBarevPlochy);
        }
        if(orientace == 4){
            sousediJinaBarvaPodleBarevSeverPlochy.add(sousediJinaBarvaPodleBarevPlochy);
        }
        
    }
    
    
    private void ZapisPixelDoSousediCaryPodleOrientace(ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevCary, int orientace){
        
        if(orientace == 1){
            sousediJinaBarvaPodleBarevVychodCary.add(sousediJinaBarvaPodleBarevCary);
        }
        if(orientace == 2){
            sousediJinaBarvaPodleBarevJihCary.add(sousediJinaBarvaPodleBarevCary);
        }
        if(orientace == 3){
            sousediJinaBarvaPodleBarevZapadCary.add(sousediJinaBarvaPodleBarevCary);
        }
        if(orientace == 4){
            sousediJinaBarvaPodleBarevSeverCary.add(sousediJinaBarvaPodleBarevCary);
        }
        
    }
    
    
    //k jednotlivym sousedum (Vychod, Jih, Zapad, Sever) doplni statusy "jedna se o okraj plochy" a "jedna se o caru"
    private ArrayList<HashMap<String, Integer>> rozlisujKterePixelyOkrajeJsouPatriciKPloseNeboCare(ArrayList<HashMap<String, Integer>> sousedStrana, ArrayList<HashMap<String, Integer>> sousedStred, int orientace, ArrayList<HashMap<String, Integer>> sousedOkraj1, ArrayList<HashMap<String, Integer>> sousedOkraj2, ArrayList<HashMap<String, Integer>> sousedOkraj3){
        
        HashMap<String, Integer> pixelStrana;
        HashMap<String, Integer> pixelStranaStatus;
        ArrayList<HashMap<String, Integer>> sousedStranaStatus = new ArrayList<HashMap<String, Integer>>();
        
        int xHlavni;
        int yHlavni;
        
        int xPozadovane = -1;
        int yPozadovane = -1;
        
        boolean existujePixelSouseda;
        
        for (int i = 0; i < sousedStrana.size(); i++) {
     
            pixelStrana = new HashMap<String, Integer>();
            pixelStrana = sousedStrana.get(i);
            
            xHlavni = pixelStrana.get("x_Hlavni");
            yHlavni = pixelStrana.get("y_Hlavni");
            
            //podle orientace hleda sousedni pozadovane souradnice, ktere dal proveruje
            //z toho zjisti zda se jedna o plochu nebo o caru
            if(orientace == 1){
                xPozadovane = xHlavni - 1; 
                yPozadovane = yHlavni;
            }
            if(orientace == 2){
                xPozadovane = xHlavni; 
                yPozadovane = yHlavni + 1;
            }
            if(orientace == 3){
                xPozadovane = xHlavni + 1; 
                yPozadovane = yHlavni;
            }
            if(orientace == 4){
                xPozadovane = xHlavni; 
                yPozadovane = yHlavni - 1;
            }
            
            
            
            //vyhledava sousedni pixel z pole sousediStejnaBarvaPodleBarev
            existujePixelSouseda = detekujZdaExistujePixelSousedaStredu(xPozadovane, yPozadovane, sousedStred);
            
            //vyhledava sousedni pixel z pole sousediJinaBarvaPodleBarev
            //pokud souseda nevyhleda, pak ho vyhledava zde (okraj protilehly)
            if(existujePixelSouseda == false){
                existujePixelSouseda = detekujZdaExistujePixelSousedaOkraje(xPozadovane, yPozadovane, sousedOkraj1);
            }
            //pokud souseda nevyhleda, pak ho vyhledava zde (okraj sousedni pres roh)
            if(existujePixelSouseda == false){
                existujePixelSouseda = detekujZdaExistujePixelSousedaOkraje(xPozadovane, yPozadovane, sousedOkraj2);
            }
            //pokud souseda nevyhleda, pak ho vyhledava zde (okraj sousedni pres roh)
            if(existujePixelSouseda == false){
                existujePixelSouseda = detekujZdaExistujePixelSousedaOkraje(xPozadovane, yPozadovane, sousedOkraj3);
            }
            
            
            
            //doplni status do pixelStrana (resp. vytvori pixelStranaStatus)
            pixelStranaStatus = new HashMap<String, Integer>();
            pixelStranaStatus = pixelStrana;
            
            if(existujePixelSouseda == true){
                pixelStranaStatus.put("jedna se o okraj plochy", 1);
                pixelStranaStatus.put("jedna se o caru", 0);
            }
            else {
                pixelStranaStatus.put("jedna se o okraj plochy", 0);
                pixelStranaStatus.put("jedna se o caru", 1);
            }
            
            sousedStranaStatus.add(pixelStranaStatus);
             
        }
        
        return(sousedStranaStatus);
        
    }
    
    
    private boolean detekujZdaExistujePixelSousedaOkraje(int x, int y, ArrayList<HashMap<String, Integer>> sousedOkraj){
        
        //aby bylo vyhledavani rychlejsi, probiha od kraju
        int iPlus;
        int iMinus;
        int pocetCyklu;
        
        //bezi od spoda a od shora soucasne, takze nemusi bezet vsechny cykly
        boolean jednaSeOPixelSouseda = false;
        pocetCyklu = sousedOkraj.size()/3 * 2;
        
        for (int i = 0; i < pocetCyklu; i++) {
            iPlus = i;
            iMinus = sousedOkraj.size() - i - 1;
            
            //vyhledava smerem od spoda
            jednaSeOPixelSouseda = detekujZdaExistujePixelSousedaOkrajeTeloCyklu(x, y, sousedOkraj, iPlus);
            
            //vyhledava smerem od shora
            if(jednaSeOPixelSouseda == false){ 
                jednaSeOPixelSouseda = detekujZdaExistujePixelSousedaOkrajeTeloCyklu(x, y, sousedOkraj, iMinus);
            }
            
            if(jednaSeOPixelSouseda == true){ 
                break;
            }
            
        }
        
        return(jednaSeOPixelSouseda);
        
    }
    
    
    //tato metoda obsahuje telo cyklu v metode detekujZdaExistujePixelSousedaOkraje
    //je to pouzito aby bylo vyhledavani rychlejsi, bezi od kraju vyhledavane oblasti
    private boolean detekujZdaExistujePixelSousedaOkrajeTeloCyklu(int x, int y, ArrayList<HashMap<String, Integer>> sousedOkraj, int index){
       
        HashMap<String, Integer> pixel;
        boolean jednaSeOPixelSouseda = false;
        int xHlavni;
        int yHlavni;
        
        pixel = new HashMap<String, Integer>();
        pixel = sousedOkraj.get(index);

        xHlavni = pixel.get("x_Hlavni");
        yHlavni = pixel.get("y_Hlavni");

        if(x == xHlavni){
            if(y == yHlavni){
                jednaSeOPixelSouseda = true;

            }
        }
        
        return(jednaSeOPixelSouseda);    
    }
    
  
    private boolean detekujZdaExistujePixelSousedaStredu(int x, int y, ArrayList<HashMap<String, Integer>> sousedStred){
        
        HashMap<String, Integer> pixel;
        
        int indexZacatek;
        int indexKonec;
        
        boolean jeDanyPixelVIntervalu = false;
        
        //metoda postupuje ve vyhledavani od posledniIndexIntervalu, jelikoz pravdepodobnost, ze nalezena hodnota bude v prvni smycce je nizka
        //ale pravdepodobnost, ze bude nalezena ve smycce nekde blizko okolo posledniIndexIntervalu je vysoka
        //proto se vychazi od posledniIndexIntervalu a postupuje se nahoru a dolu v kazde smycce, i proto je zavedena metoda:
        //detekujZdaExistujePixelSousedaTeloSmycky - to proto, aby telo bylo jen jednou
        
        int iPlus;
        int iMinus;
        

        for (int i = 0; i < sousedStred.size(); i++) {
           
            jeDanyPixelVIntervalu = false;
            iPlus = posledniIndexIntervalu + i;
            iMinus = posledniIndexIntervalu - 1;
            
            if(iPlus < sousedStred.size()){
                if(iPlus > 0){
                    jeDanyPixelVIntervalu = detekujZdaExistujePixelSousedaTeloSmycky(x, y, iPlus, sousedStred);

                    if(jeDanyPixelVIntervalu == true){
                        posledniIndexIntervalu = iPlus;
                        break;
                    }
                }    
            }
            
            if(iMinus > 0){
                if(iMinus < sousedStred.size()){
                    jeDanyPixelVIntervalu = detekujZdaExistujePixelSousedaTeloSmycky(x, y, iMinus, sousedStred);

                    if(jeDanyPixelVIntervalu == true){
                        posledniIndexIntervalu = iMinus;
                        break;
                    }  
                }    
            }
                
        }
       
        return(jeDanyPixelVIntervalu);

    }
    
    //tato metoda je vlozena, aby algoritmus bezel rychleji
    private boolean detekujZdaExistujePixelSousedaTeloSmycky(int x, int y, int index, ArrayList<HashMap<String, Integer>> sousedStred){
        
        HashMap<String, Integer> pixel;
        
        int indexZacatek;
        int indexKonec;
        
        boolean jeDanyPixelVIntervalu = false;
        
        //ziska dany pixel pro detekci zda Existuje Souradnice
        pixel = new HashMap<String, Integer>();    
        pixel = sousedStred.get(index);

        indexZacatek = pixel.get("index_Zacatek");
        indexKonec = pixel.get("index_Konec");

        //zavola metodu, ktera vraci, zda dany pixel je v intervalu nalezen
        jeDanyPixelVIntervalu = detekujZdaExistujeDanaSouradniceVIntervalu(x, y, indexZacatek, indexKonec);

        return(jeDanyPixelVIntervalu);
        
    }
    
    
    private boolean detekujZdaExistujeDanaSouradniceVIntervalu(int xPozadovany, int yPozadovany, int indexZacatek, int indexKonec){
        
        int x;
        int y;
        
        boolean pixelNalezen = false;
        HashMap<String, Integer> pixelVsechBarev;
        
        for (int i = indexZacatek; i < indexKonec + 1; i++) {
            pixelVsechBarev = new HashMap<String, Integer>();
            pixelVsechBarev = souradniceVsechBarev.get(i);
            
            x = pixelVsechBarev.get("x");
            y = pixelVsechBarev.get("y");
            
            if(x == xPozadovany){
                if(y == yPozadovany){
                    pixelNalezen = true;
                    break;
                }
            }
        }
        
        return(pixelNalezen);
    }
    
    
    private void xxx(ArrayList<HashMap<String, Integer>> soused, int xPozadovane, int yPozadovane){
        
        HashMap<String, Integer> souradnice;
        int xHlavni;
        int yHlavni;
        
        for (int i = 0; i < soused.size(); i++) {
            souradnice = new HashMap<String, Integer>();
            souradnice = soused.get(i);
            
            //xHlavni = souradnice.get("x_Hlavni")
        }
        
    }
    
}
