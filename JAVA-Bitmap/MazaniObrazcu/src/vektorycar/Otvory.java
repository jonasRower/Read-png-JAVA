
package vektorycar;

import java.util.ArrayList;
import java.util.HashMap;


public class Otvory {

    //data na vsech plochach - original
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedVychodVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedJihVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedZapadVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedSeverVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //rohove pixely na vsech plochach
    private ArrayList<ArrayList<HashMap<String, Integer>>> rohovePixelyVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //popisOtvoru
    private ArrayList<HashMap<String, Integer>> popisOtvoru = new ArrayList<HashMap<String, Integer>>();
    
    //plochy ktere jsou otvorem
    private boolean[] plochyKtereJsouOtvorem;
    
    //plochy, ktere se budou zapisovat jako originalni(nemaji otvor)
    //ostatni plochy se budou slucovat s otvory
    boolean[] zapisovatPoleOrig;
    
    //poznamkyKTestovani
    private ArrayList<HashMap<String, Integer>> poznamkyKTestovani = new ArrayList<HashMap<String, Integer>>();
    
    //slouci data na tech plochach, kde jsou otvrory, tj plocha s otvorem prijde do dat rodicovske plochy
    //data na vsech plochach - sloucene
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedVychodVsechnyPlochySloucene = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedJihVsechnyPlochySloucene = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedZapadVsechnyPlochySloucene = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedSeverVsechnyPlochySloucene = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    
    public Otvory(ArrayList<ArrayList<HashMap<String, Integer>>> sousedVychodVsechnyPlochy, ArrayList<ArrayList<HashMap<String, Integer>>> sousedJihVsechnyPlochy, ArrayList<ArrayList<HashMap<String, Integer>>> sousedZapadVsechnyPlochy, ArrayList<ArrayList<HashMap<String, Integer>>> sousedSeverVsechnyPlochy, ArrayList<ArrayList<HashMap<String, Integer>>> rohovePixelyVsechnyPlochy){
        
        this.sousedVychodVsechnyPlochy = sousedVychodVsechnyPlochy;
        this.sousedJihVsechnyPlochy = sousedJihVsechnyPlochy;
        this.sousedZapadVsechnyPlochy = sousedZapadVsechnyPlochy;
        this.sousedSeverVsechnyPlochy = sousedSeverVsechnyPlochy;       
        this.rohovePixelyVsechnyPlochy = rohovePixelyVsechnyPlochy;   
        
        //pripravi data
        vratPoleKdeJsouOtvoryJakoPlochy();
        vratPopisOtvoruPole();
        vratIndexyPlochKtereZapisovatJakoOriginalni();

        //slouci plochy
        slucujPlochy();
        
        System.out.print("");
        
    }
    
    //vraci data
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedVychodVsechnyPlochySloucene(){
        return(sousedVychodVsechnyPlochySloucene);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedJihVsechnyPlochySloucene(){
        return(sousedJihVsechnyPlochySloucene);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSsousedZapadVsechnyPlochySloucene(){
        return(sousedZapadVsechnyPlochySloucene);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedSeverVsechnyPlochySloucene(){
        return(sousedSeverVsechnyPlochySloucene);
    }
    
    private void slucujPlochy(){
        
        ArrayList<ArrayList<Integer>> sledSlucovaniPlochy = new ArrayList<ArrayList<Integer>>();
        sledSlucovaniPlochy = vytvorSledSlucovaniPloch();
        
        sousedVychodVsechnyPlochySloucene = slucujOkrajVsechPloch(sledSlucovaniPlochy, sousedVychodVsechnyPlochy);
        sousedJihVsechnyPlochySloucene = slucujOkrajVsechPloch(sledSlucovaniPlochy, sousedJihVsechnyPlochy);
        sousedZapadVsechnyPlochySloucene = slucujOkrajVsechPloch(sledSlucovaniPlochy, sousedZapadVsechnyPlochy);
        sousedSeverVsechnyPlochySloucene = slucujOkrajVsechPloch(sledSlucovaniPlochy, sousedSeverVsechnyPlochy);
        
        
        System.out.print("");
    }
    
    
    private ArrayList<ArrayList<HashMap<String, Integer>>> slucujOkrajVsechPloch(ArrayList<ArrayList<Integer>> sledSlucovaniPlochy, ArrayList<ArrayList<HashMap<String, Integer>>> okrajVsechnyPlochy){
        
        
        ArrayList<Integer> sledSlucovaniDoJednePlochy = null;
        
        ArrayList<HashMap<String, Integer>> slucDataProJedenOkraj = new ArrayList<HashMap<String, Integer>>();
        ArrayList<ArrayList<HashMap<String, Integer>>> sloucenyOkrajVsechPloch = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<HashMap<String, Integer>> origDataProJedenOkraj; 
       
        for (int i = 0; i < sledSlucovaniPlochy.size(); i++) {
            sledSlucovaniDoJednePlochy = new ArrayList<Integer>();
            sledSlucovaniDoJednePlochy = sledSlucovaniPlochy.get(i);

            slucDataProJedenOkraj = slucJedenOkraj(sledSlucovaniDoJednePlochy, okrajVsechnyPlochy);
            sloucenyOkrajVsechPloch.add(slucDataProJedenOkraj);
            System.out.print("");
        }
        
        return(sloucenyOkrajVsechPloch);
           
    }
    
    
    private ArrayList<HashMap<String, Integer>> slucJedenOkraj(ArrayList<Integer> sledSlucovaniDoJednePlochy, ArrayList<ArrayList<HashMap<String, Integer>>> sousedVsechnyPlochy){
        
        ArrayList<HashMap<String, Integer>> origDataProJedenOkraj; 
        ArrayList<HashMap<String, Integer>> slucDataProJedenOkraj = new ArrayList<HashMap<String, Integer>>();
        int cisloPlochy;
        
        for (int i = 0; i < sledSlucovaniDoJednePlochy.size(); i++) {
            cisloPlochy = sledSlucovaniDoJednePlochy.get(i);
            
            //vychodni okraj
            origDataProJedenOkraj = new ArrayList<HashMap<String, Integer>>();
            //origDataProJedenOkraj = sousedVsechnyPlochy.get(i);
            origDataProJedenOkraj = sousedVsechnyPlochy.get(cisloPlochy);
            slucDataProJedenOkraj = slucDataProJedenOkraj(slucDataProJedenOkraj, origDataProJedenOkraj);
            
        }
        
        return(slucDataProJedenOkraj);
        
    }
    
    
    private ArrayList<HashMap<String, Integer>> slucDataProJedenOkraj(ArrayList<HashMap<String, Integer>> dataPuvodniJednaPlocha, ArrayList<HashMap<String, Integer>> dataPridavanaJednaPlocha){
        
        ArrayList<HashMap<String, Integer>> dataNewJednaPlocha = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> dataPixel;
        
        //prekopopiruje puvodni data
        for (int i = 0; i < dataPuvodniJednaPlocha.size(); i++) {
            dataPixel = new HashMap<String, Integer>();
            dataPixel = dataPuvodniJednaPlocha.get(i);
            
            dataNewJednaPlocha.add(dataPixel);
        }
        
        //prida nova data
        for (int i = 0; i < dataPridavanaJednaPlocha.size(); i++) {
            dataPixel = new HashMap<String, Integer>();
            dataPixel = dataPridavanaJednaPlocha.get(i);
            
            dataNewJednaPlocha.add(dataPixel);
        }
        
        return(dataNewJednaPlocha);
                
    }
    
    
    //na zaklade popisOtvoru vytvori pole v jakem poradi se budou slucovat plochy
    //prvni index je vzdy originalni pole
    //nasledujici indexy jsou indexy ploch
    //vsechny indexy jsou plochy, takze se vybiraji ze stejnych dat
    private ArrayList<ArrayList<Integer>> vytvorSledSlucovaniPloch(){
        
        ArrayList<Integer> sledSlucovani;
        ArrayList<ArrayList<Integer>> sledSlucovaniPlochy = new ArrayList<ArrayList<Integer>>();
        
        
        boolean plochaJeOtvorem;
        
        for (int i = 0; i < plochyKtereJsouOtvorem.length; i++) {
            plochaJeOtvorem = plochyKtereJsouOtvorem[i];
            if(plochaJeOtvorem == false){
                sledSlucovani = new ArrayList<Integer>();
                sledSlucovani = vyhledejVsechnyOtvoryPatriciPodJednuPlochu(i);
                sledSlucovaniPlochy.add(sledSlucovani);
            }
        }
           
        return(sledSlucovaniPlochy);
        
    }
    
    
    private ArrayList<Integer> vyhledejVsechnyOtvoryPatriciPodJednuPlochu(int cisloPlochyPozadovane){
        
        int plochaCislo;
        int otvorCislo;
        boolean hledat;
        HashMap<String, Integer> popisOtvor;
        ArrayList<Integer> sledVolani = new ArrayList<Integer>();
        
        sledVolani.add(cisloPlochyPozadovane);
        
        for (int i = 0; i < popisOtvoru.size(); i++) {
            popisOtvor = new HashMap<String, Integer>();
            popisOtvor = popisOtvoru.get(i);
            hledat = popisOtvor.containsKey("obsahuje otvor cislo");
            
            if(hledat == true){
                plochaCislo = popisOtvor.get("plocha cislo (rodic)");
                
                if(plochaCislo == cisloPlochyPozadovane){
                    otvorCislo = popisOtvor.get("obsahuje otvor cislo");
                    sledVolani.add(otvorCislo);
                }
            }
        }
        
        return (sledVolani);
        
    }
    
    private void vratIndexyPlochKtereZapisovatJakoOriginalni(){
        
        boolean obsahujePlochaOtvor;
        boolean plochaJeSamaOtvorem;
        
        zapisovatPoleOrig = new boolean[rohovePixelyVsechnyPlochy.size()];
        
        int indexPlochy;
        String status;
        
        for (int i = 0; i < rohovePixelyVsechnyPlochy.size(); i++) {
            indexPlochy = i;
            obsahujePlochaOtvor = detekujZdaProDanouPlochuExistujeOtvor(indexPlochy);
            plochaJeSamaOtvorem = plochyKtereJsouOtvorem[indexPlochy];
            
            zapisovatPoleOrig[i] = false;
            
            if(obsahujePlochaOtvor == false){
                if(plochaJeSamaOtvorem == false){
                    zapisovatPoleOrig[i] = true;
                }
            }
        }
        
        System.out.print("");
    }
    
    
    private boolean detekujZdaProDanouPlochuExistujeOtvor(int plochaRodicPozadovane){
        
        int plochaRodic;
        boolean obsahujePlochaOtvor = false;
        
        HashMap<String, Integer> popisOtvoruHash;
        
        for (int i = 0; i < popisOtvoru.size(); i++) {
            popisOtvoruHash = new HashMap<String, Integer>();
            popisOtvoruHash = popisOtvoru.get(i);
            
            plochaRodic = popisOtvoruHash.get("plocha cislo (rodic)");
            if(plochaRodic == plochaRodicPozadovane){
                obsahujePlochaOtvor = true;
                break;
            } 
        }
        
        return(obsahujePlochaOtvor);
    }
    
    
    private void vratPopisOtvoruPole(){
        
        HashMap<String, Integer> popisOtvoruHash = null;
        
        boolean[] plochaJeOtvoremPole;
        boolean plochaJeOtvorem;
        int indexPlochyKdeJeOtvor = -1;
        
        plochaJeOtvoremPole = vratPoleKdeJsouOtvoryJakoPlochy();
        plochyKtereJsouOtvorem = plochaJeOtvoremPole;
        
        //vyhleda plochu k danemu otvoru
        for (int i = 0; i < plochaJeOtvoremPole.length; i++) {
            popisOtvoruHash = new HashMap<String, Integer>();
            plochaJeOtvorem = plochaJeOtvoremPole[i];
            if(plochaJeOtvorem == true){
                indexPlochyKdeJeOtvor = vratIndexPlochyKdeJeDanyOtvor(i);
                popisOtvoruHash = vratHashMapKteraPlochaMaKteryOtvor(indexPlochyKdeJeOtvor, i);
                popisOtvoru.add(popisOtvoruHash);
            }
        }
    }
    
    
    private HashMap<String, Integer> vratHashMapKteraPlochaMaKteryOtvor(int indexPlochy, int indexOtvoru){
        
        HashMap<String, Integer> popisOtvoruHash = new HashMap<String, Integer>();
        
        popisOtvoruHash.put("plocha cislo (rodic)", indexPlochy);
        popisOtvoruHash.put("obsahuje otvor cislo", indexOtvoru);
        
        return (popisOtvoruHash);
        
    }
    
    
    private int vratIndexPlochyKdeJeDanyOtvor(int index){
        
        ArrayList<HashMap<String, Integer>> rohovePixelyOtvor = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> rohovePixelyJednaPlocha = new ArrayList<HashMap<String, Integer>>();
        rohovePixelyOtvor = rohovePixelyVsechnyPlochy.get(index);
        boolean otvorJeSoucastiDanePlochy;
        int indexPlochyKdeJeOtvor = -1;
        
        for (int i = 0; i < rohovePixelyVsechnyPlochy.size(); i++) {
            if(i != index){
                rohovePixelyJednaPlocha = rohovePixelyVsechnyPlochy.get(i);
                otvorJeSoucastiDanePlochy = detekujZeJeOtvorSoucastiDanePlochy(rohovePixelyOtvor, rohovePixelyJednaPlocha);
                if(otvorJeSoucastiDanePlochy == true){
                    indexPlochyKdeJeOtvor = i;
                    break;
                }
            }
        }
        
        return(indexPlochyKdeJeOtvor);
    }
    
    
    private boolean detekujZeJeOtvorSoucastiDanePlochy(ArrayList<HashMap<String, Integer>> rohovePixelyOtvor, ArrayList<HashMap<String, Integer>> rohovePixelyJednaPlocha){
        
        HashMap<String, Integer> souradniceOtvor = new HashMap<String, Integer>();
        HashMap<String, Integer> souradnicePlochaA;
        HashMap<String, Integer> souradnicePlochaB;
        int xOtvor;
        int yOtvor;
        int xPlochaA;
        int yPlochaA;
        int xPlochaB;
        int yPlochaB;
        
        boolean souradniceLeziMeziSebouX = false;
        boolean souradniceLeziMeziSebouY = false;
        boolean otvorJeSoucastiDanePlochy = false;
        
        
        souradniceOtvor = rohovePixelyOtvor.get(0);
        
        xOtvor = souradniceOtvor.get("x_Konec");
        yOtvor = souradniceOtvor.get("y_Konec");
        

        for (int i = 1; i < rohovePixelyJednaPlocha.size(); i++) {
            souradnicePlochaA = new HashMap<String, Integer>();
            souradnicePlochaA = rohovePixelyJednaPlocha.get(i-1);
            souradnicePlochaB = new HashMap<String, Integer>();
            souradnicePlochaB = rohovePixelyJednaPlocha.get(i);
            
            xPlochaA = souradnicePlochaA.get("x");
            yPlochaA = souradnicePlochaA.get("y");
            xPlochaB = souradnicePlochaB.get("x");
            yPlochaB = souradnicePlochaB.get("y");
            
            if(souradniceLeziMeziSebouX == false){
                souradniceLeziMeziSebouX = detekujZdaSouradniceLeziMeziSebou(xPlochaA, xOtvor, xPlochaB);
            }
            if(souradniceLeziMeziSebouY == false){
                souradniceLeziMeziSebouY = detekujZdaSouradniceLeziMeziSebou(yPlochaA, yOtvor, yPlochaB);
            }
        }
        
        if(souradniceLeziMeziSebouX == true){
            if(souradniceLeziMeziSebouY == true){
                otvorJeSoucastiDanePlochy = true;
            }
        }
        
        return(otvorJeSoucastiDanePlochy);
  
    }
    
    
    private boolean detekujZdaSouradniceLeziMeziSebou(int sourPlochaA, int sourOtvor, int sourPlochaB){
        
        int sourMin;
        int sourMax;
        boolean sourLeziVIntervalu = false;
        
        if(sourPlochaA < sourPlochaB){
            sourMin = sourPlochaA;
            sourMax = sourPlochaB;
        }
        else {
            sourMin = sourPlochaB;
            sourMax = sourPlochaA;
        }
        
        if(sourMin < sourOtvor){
            if(sourOtvor < sourMax){
                sourLeziVIntervalu = true;
            }
        }
                    
        return(sourLeziVIntervalu);
    }
    
    
    private boolean[] vratPoleKdeJsouOtvoryJakoPlochy(){
        
        boolean[] plochaJeOtvorem = new boolean[rohovePixelyVsechnyPlochy.size()];
        boolean jednaSeOOtvor;
        
        for (int i = 0; i < rohovePixelyVsechnyPlochy.size(); i++) {
            jednaSeOOtvor = detekujZdaJePlochaOtvorem(i);
            plochaJeOtvorem[i] = jednaSeOOtvor;
        }
        
        return(plochaJeOtvorem);
        
    }
    
    
    private boolean detekujZdaJePlochaOtvorem(int index){
        
        ArrayList<HashMap<String, Integer>> rohovePixelyJednaPlocha = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> rohovyPixel;
        rohovePixelyJednaPlocha = rohovePixelyVsechnyPlochy.get(index);
        boolean jednaSeOOtvor = true;
        
        int sizeHashMap;
        for (int i = 0; i < rohovePixelyJednaPlocha.size(); i++) {
            rohovyPixel = new HashMap<String, Integer>();
            rohovyPixel = rohovePixelyJednaPlocha.get(i);
            sizeHashMap = rohovyPixel.size();
            
            if(sizeHashMap != 5){
                jednaSeOOtvor = false;
                break;        
            }
        }
        
        return(jednaSeOOtvor);
        
    }
    
    
}
