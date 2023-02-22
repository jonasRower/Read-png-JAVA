
package vektorycar;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.HashMap;

public class OkrajKolemDokola {
    
    private static ArrayList<HashMap<String, Integer>> sousedVychod = new ArrayList<HashMap<String, Integer>>();
    private static ArrayList<HashMap<String, Integer>> sousedJih = new ArrayList<HashMap<String, Integer>>();
    private static ArrayList<HashMap<String, Integer>> sousedZapad = new ArrayList<HashMap<String, Integer>>();
    private static ArrayList<HashMap<String, Integer>> sousedSever = new ArrayList<HashMap<String, Integer>>();
    
    //uchovava data o pixelech v rozich
    private ArrayList<HashMap<String, Integer>> rohovePixely = new ArrayList<HashMap<String, Integer>>();
    
    
    //uchovava data na jakou stranu jsou orientovani sousedi pro jednotlive plochy
    private ArrayList<HashMap<String, Integer>> sousedVychodJednaPlocha = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedJihJednaPlocha = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedZapadJednaPlocha = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedSeverJednaPlocha = new ArrayList<HashMap<String, Integer>>();
    
    //uchovava data, ktera nebyla zatim pouzita
    private ArrayList<HashMap<String, Integer>> sousedVychodNezapsane = new ArrayList<HashMap<String, Integer>>();;
    private ArrayList<HashMap<String, Integer>> sousedJihNezapsane = new ArrayList<HashMap<String, Integer>>();;
    private ArrayList<HashMap<String, Integer>> sousedZapadNezapsane = new ArrayList<HashMap<String, Integer>>();;
    private ArrayList<HashMap<String, Integer>> sousedSeverNezapsane = new ArrayList<HashMap<String, Integer>>();;
    
    
    public OkrajKolemDokola(ArrayList<HashMap<String, Integer>> sousedVychod, ArrayList<HashMap<String, Integer>> sousedJih, ArrayList<HashMap<String, Integer>> sousedZapad, ArrayList<HashMap<String, Integer>> sousedSever){
        
        //slouzi k testovani, abych vedel jaka byla originalni data
        this.sousedVychod = sousedVychod;
        this.sousedJih = sousedJih;
        this.sousedZapad = sousedZapad;
        this.sousedSever = sousedSever;
        
        //defaultni data v polich pro vymazavabi dat
        //vymazava se proto, aby se vedelo ktere data jsou jiz zapsana a ktera zbyvaji
        //a taky kvuli tomu aby smycek bylo mene (aby nebezeli v jiz nalezenych datech)

        this.sousedVychodNezapsane = sousedVychod;
        this.sousedJihNezapsane = sousedJih;
        this.sousedZapadNezapsane = sousedZapad;
        this.sousedSeverNezapsane = sousedSever;
        
        
        HlavniProgram(); 
        
    }
    
    //---------------------------------------------------------------------------------
    
    //data z jedne plochy
    public ArrayList<HashMap<String, Integer>> getDataNaJednePloseVychod(){
        return(sousedVychodJednaPlocha);
    }
    
    public ArrayList<HashMap<String, Integer>> getDataNaJednePloseJih(){
        return(sousedJihJednaPlocha);
    }
    
    public ArrayList<HashMap<String, Integer>> getDataNaJednePloseZapad(){
        return(sousedZapadJednaPlocha);
    }
    
    public ArrayList<HashMap<String, Integer>> getDataNaJednePloseSever(){
        return(sousedSeverJednaPlocha);
    }
    
    //rohove pixely
    public ArrayList<HashMap<String, Integer>> getRohovePixelyNaJednePlose(){
        return(rohovePixely);
    }
    
    //---------------------------------------------------------------------------------
    
    //data, ktera nebyla nalezena v ramci jedne plochy, cekaji na volani plochy dalsi
    public ArrayList<HashMap<String, Integer>> getDataNezapsaneVychod(){
        return(sousedVychodNezapsane);
    }
    
    public ArrayList<HashMap<String, Integer>> getDataNezapsaneJih(){
        return(sousedJihNezapsane);
    }
    
    public ArrayList<HashMap<String, Integer>> getDataNezapsaneZapad(){
        return(sousedZapadNezapsane);
    }
    
    public ArrayList<HashMap<String, Integer>> getDataNezapsaneSever(){
        return(sousedSeverNezapsane);
    }
    
    //---------------------------------------------------------------------------------
    
    
    private void HlavniProgram(){
        
        ArrayList<HashMap<String, Integer>> sousediciPixely = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> navazujiciRada = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> smer = new HashMap<String, Integer>();
        String smerHledani = "y";
        int index = 0;
        int souradniceHledani= -1;
        boolean posledniHledani = false;
        
        //vychozi data
        smerHledani = "y"; 
        navazujiciRada = sousedZapad;
        sousediciPixely = vratPouzeSousediciPixelyNaHranici(navazujiciRada);
        
        //test
        smer = vratSouradniciLinie(sousediciPixely);
        
        
        for (int i = 0; i < 1000; i++) {
            
            //zapise rohove pixely, nebo vrati status pro ukonceni hledani
            posledniHledani = zapisRohovePixelyKDaneStrane(sousediciPixely, smerHledani);    //sousedi = zapad
            
            //prohodi smer hledani
            //smerHledani = zmenSmerHledani(smerHledani);
            smerHledani = podleOrientaceSousedaVratSmerHledani(sousediciPixely);
            
            //stanovuje index, pokud je posledni smycka, pak index = 0
            if(posledniHledani == false){
                index = i + 1;
            }
            else {
                index = 0;
                opravRohovyPixelVBodeNula();
            }
            
            //stanovi souradnici, na ktere bude vyhledavat dalsi polozky
            souradniceHledani = vratSouradniciHledani(smerHledani, posledniHledani);

            //vrati radu podle strany (V, J, S, Z)
            navazujiciRada = vratNavazujiciRadu(rohovePixely, index, posledniHledani);

            //vrati pouze ty polozky z navazujici rady, jen pro ktere plati souradnice hledani
            navazujiciRada = vratRaduJenUrcitePoradnice(navazujiciRada, souradniceHledani, smerHledani);

            //vrati prvky, jen ty ktere se sebou sousedi
            sousediciPixely = vratPouzeSousediciPixelyNaHranici(navazujiciRada);
           
            //vytridi data, ktera nebyla zapsana a zapise vytridena
            //plni privatni data
            
            zapisPrvkyKtereNebylyABylyZapsany(sousediciPixely, index, posledniHledani);
            
            //test
            smer = vratSouradniciLinie(sousediciPixely);
            
            System.out.print("");
            
            if(posledniHledani == true){
                break;
            }
            
            System.out.print("");
        }

        //test
        smer = vratSouradniciLinie(sousediciPixely);
        
        System.out.print("");
        
    }
    
    //je-li v bode 0 rohovy pixel varu x_konec, y_konec, pak ma problemy
    //je potreba tedy prohodit s beznym x a y
    private void opravRohovyPixelVBodeNula(){
        
        boolean obsahujeXKonec;
        HashMap<String, Integer> rohovyPixel = new HashMap<String, Integer>();
        HashMap<String, Integer> rohovyPixelNew = new HashMap<String, Integer>();
        
        rohovyPixel = rohovePixely.get(0);
        obsahujeXKonec = rohovyPixel.containsKey("x_Konec");
        if(obsahujeXKonec == true){
            int x_Konec;
            int y_Konec;
            int x;
            int y;
            int orientaceRoh;
            
            x_Konec = rohovyPixel.get("x_Konec");
            y_Konec = rohovyPixel.get("y_Konec");
            x = rohovyPixel.get("x");
            y = rohovyPixel.get("y");
            orientaceRoh = rohovyPixel.get("orientaceRoh");
            
            //prohodi data - vytvori novy HashMap
            rohovyPixelNew.put("x_Konec", x);
            rohovyPixelNew.put("y_Konec", y);
            rohovyPixelNew.put("x", x_Konec);
            rohovyPixelNew.put("y", y_Konec);
            rohovyPixelNew.put("orientaceRoh", orientaceRoh);
            
            //nahradi hashmap v ArrayListu
            rohovePixely.set(0, rohovyPixelNew);
            
        }
        
        System.out.print("");
    }
            
            
    
    
    private String podleOrientaceSousedaVratSmerHledani(ArrayList<HashMap<String, Integer>> sousediciPixely){
        
        HashMap<String, Integer> pixel;
        int orientaceSouseda;
        String smerHledani = "??";
        
        pixel = sousediciPixely.get(0);
        orientaceSouseda = pixel.get("orientace souseda");
        
        if(orientaceSouseda == 1){
            smerHledani = "x";
        }
        if(orientaceSouseda == 2){
            smerHledani = "y";
        }
        if(orientaceSouseda == 3){
            smerHledani = "x";
        }
        if(orientaceSouseda == 4){
            smerHledani = "y";
        }
        
        return(smerHledani);
        
    }
    
    
    private void zapisPrvkyKtereNebylyABylyZapsany(ArrayList<HashMap<String, Integer>> sousediciPixely, int index, boolean posledniHledani){
        
        int orientaceOkraje;
        boolean vratDruheCislo = true;
        
        ArrayList<HashMap<String, Integer>> nezapsanePixely = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> zapsanePixely = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> pixel;
        
        if(posledniHledani == true){
            vratDruheCislo = false;
        }
        
        //zjisti na jaky okraj ma zapisovat data
        orientaceOkraje = ziskejOznaceniPrvniRadyZRohovychPixelu(index, vratDruheCislo);
        
        
        if(orientaceOkraje == 1){
            //nezapsana data
            nezapsanePixely = sousedVychodNezapsane;
            nezapsanePixely = vymazPozadovanaData(nezapsanePixely, sousediciPixely);
            sousedVychodNezapsane = nezapsanePixely;
            
            //zapsana data
            zapsanePixely = sousedVychodJednaPlocha;
            zapsanePixely = zapisPozadovanaData(zapsanePixely, sousediciPixely);
            sousedVychodJednaPlocha = zapsanePixely;
            
            System.out.print("");
        }
        
        if(orientaceOkraje == 2){
            //nezapsana data
            nezapsanePixely = sousedJihNezapsane;
            nezapsanePixely = vymazPozadovanaData(nezapsanePixely, sousediciPixely);
            sousedJihNezapsane = nezapsanePixely;
            
            //zapsana data
            zapsanePixely = sousedJihJednaPlocha;
            zapsanePixely = zapisPozadovanaData(zapsanePixely, sousediciPixely);
            sousedJihJednaPlocha = zapsanePixely;
        }
        
        if(orientaceOkraje == 3){
            //nezapsana data
            nezapsanePixely = sousedZapadNezapsane;
            nezapsanePixely = vymazPozadovanaData(nezapsanePixely, sousediciPixely);
            sousedZapadNezapsane = nezapsanePixely;
            
            //zapsana data
            zapsanePixely = sousedZapadJednaPlocha;
            zapsanePixely = zapisPozadovanaData(zapsanePixely, sousediciPixely);
            sousedZapadJednaPlocha = zapsanePixely;
            
            System.out.print("");
        }
        
        if(orientaceOkraje == 4){
            
            
            
            //nezapsana data
            nezapsanePixely = sousedSeverNezapsane;
            nezapsanePixely = vymazPozadovanaData(nezapsanePixely, sousediciPixely);
            sousedSeverNezapsane = nezapsanePixely;
            
            //zapsana data
            zapsanePixely = sousedSeverJednaPlocha;
            zapsanePixely = zapisPozadovanaData(zapsanePixely, sousediciPixely);
            sousedSeverJednaPlocha = zapsanePixely;
        }
        
        System.out.print("");
        
    }
    
    
    
    
    private void zapisDataDoHlavnichDat(ArrayList<HashMap<String, Integer>> sousediciPixely, int index){
        
        int orientaceOkraje;
        ArrayList<HashMap<String, Integer>> sousedJednaPlochaBuffer = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> rohovyPixel = new HashMap<String, Integer>();
        
        //zjisti na jaky okraj ma zapisovat data
        int orientaceRoh;

        rohovyPixel = rohovePixely.get(index);
        orientaceRoh = rohovyPixel.get("orientaceRoh");
        
        orientaceOkraje = vratIndexOrientaceNavazujiciRady(orientaceRoh, false);
        

        //orientaceOkraje = ziskejOznaceniPrvniRadyZRohovychPixelu(index);
        
        
        if(orientaceOkraje == 1){
            sousedJednaPlochaBuffer = sousedVychodJednaPlocha;
            sousedVychodJednaPlocha = zapisJednotlivaData(sousedJednaPlochaBuffer,  sousediciPixely);
            System.out.print("");
        }
        if(orientaceOkraje == 2){
            sousedJednaPlochaBuffer = sousedJihJednaPlocha;
            sousedJihJednaPlocha = zapisJednotlivaData(sousedJednaPlochaBuffer,  sousediciPixely);
            System.out.print("");
        }
        if(orientaceOkraje == 3){
            sousedJednaPlochaBuffer = sousedZapadJednaPlocha;
            sousedZapadJednaPlocha = zapisJednotlivaData(sousedJednaPlochaBuffer,  sousediciPixely);
            System.out.print("");
        }
        if(orientaceOkraje == 4){
            sousedJednaPlochaBuffer = sousedSeverJednaPlocha;
            sousedSeverJednaPlocha = zapisJednotlivaData(sousedJednaPlochaBuffer,  sousediciPixely);
            System.out.print("");
        }
        
        System.out.print("");
    }
    
    private ArrayList<HashMap<String, Integer>> zapisJednotlivaData(ArrayList<HashMap<String, Integer>> sousedJednaPlochaBuffer, ArrayList<HashMap<String, Integer>> sousediciPixely){
        
        HashMap<String, Integer> pixel;
        
        for (int i = 0; i < sousediciPixely.size(); i++) {
            pixel = new HashMap<String, Integer>();
            pixel = sousediciPixely.get(i);
            
            sousedJednaPlochaBuffer.add(pixel);
        }
        
        return(sousedJednaPlochaBuffer);
    }
    
    
    private ArrayList<HashMap<String, Integer>> vymazPozadovanaData(ArrayList<HashMap<String, Integer>> zCehoPromazavat, ArrayList<HashMap<String, Integer>> coPromazavat){
        
        HashMap<String, Integer> pixelCoPromazavat;
        HashMap<String, Integer> pixelZCehoPromazavat;
        ArrayList<HashMap<String, Integer>> zCehoPromazavat2 = new ArrayList<HashMap<String, Integer>>();
        
        for (int i = 0; i < zCehoPromazavat.size(); i++) {
            pixelZCehoPromazavat = new HashMap<String, Integer>();
            pixelZCehoPromazavat = zCehoPromazavat.get(i);
            
            zCehoPromazavat2.add(pixelZCehoPromazavat);
        }
        
        for (int i = 0; i < coPromazavat.size(); i++) {
            pixelCoPromazavat = new HashMap<String, Integer>();
            pixelCoPromazavat = coPromazavat.get(i);
            
            zCehoPromazavat2.remove(pixelCoPromazavat);
        }
        
        return(zCehoPromazavat2);
    }
    
    
    private ArrayList<HashMap<String, Integer>> zapisPozadovanaData(ArrayList<HashMap<String, Integer>> zapisPredchozi, ArrayList<HashMap<String, Integer>> zapisNovy){
        
        HashMap<String, Integer> pixelNovy;
        
        for (int i = 0; i < zapisNovy.size(); i++) {
            pixelNovy = new HashMap<String, Integer>();
            pixelNovy = zapisNovy.get(i);
            zapisPredchozi.add(pixelNovy);
        }
        
        return(zapisPredchozi);
        
    }
    
    
    //ziska napr 3, kdyz orientaceRoh = 34
    private int ziskejOznaceniPrvniRadyZRohovychPixelu(int index, boolean vratDruheCislo){
        
        HashMap<String, Integer> rohovyPixel = new HashMap<String, Integer>();
        int orientaceRoh;
        int druheCislo;
        int prvniCislo;
        
        rohovyPixel = rohovePixely.get(index);
        orientaceRoh = rohovyPixel.get("orientaceRoh");
        
        druheCislo = vratIndexOrientaceNavazujiciRady(orientaceRoh, vratDruheCislo);
        
        return (druheCislo);
    }
    
    
    private int vratSouradniciHledani(String smerHledani, boolean posledniHledani){
        
        HashMap<String, Integer> sour = new HashMap<String, Integer>();
        int posledniRohovyPixel;
        int posledniSour = -1;
        
        if(posledniHledani == false){
            posledniRohovyPixel = rohovePixely.size() -1;
        }
        else {
            posledniRohovyPixel = 0;
        }
        
        sour = rohovePixely.get(posledniRohovyPixel);
        if(smerHledani == "x"){
            posledniSour = sour.get("y");
        }
        if(smerHledani == "y"){
            posledniSour = sour.get("x");
        }
              
        return (posledniSour);
        
    }
    
    //slouzi pro testovaci ucely a vraci x nebo y souradnici, ktera je pro celou radu konstantni
    private HashMap<String, Integer> vratSouradniciLinie(ArrayList<HashMap<String, Integer>> sousediciPixely){
        
        HashMap<String, Integer> souradnice;
        HashMap<String, Integer> smer = new HashMap<String, Integer>();
        int x = -1;
        int y = -1;
        int xy = -1;
        String smerStr = "??";
        
        int xPredchozi = -1;
        int yPredchozi = -1;
        
        boolean konstX = true;
        boolean konstY = true;
        
        for (int i = 0; i < sousediciPixely.size(); i++) {
            souradnice = new HashMap<String, Integer>();
            souradnice = sousediciPixely.get(i);
            
            x = souradnice.get("x_Hlavni");
            y = souradnice.get("y_Hlavni");
            
            if(i == 0){
                xPredchozi = x;
                yPredchozi = y;
            }
            else {
                if(konstX == true){
                    if( x == xPredchozi){
                        konstX = true;
                    }
                    else {
                        konstX = false;
                    }
                }
                
                if(konstY == true){
                    if( y == yPredchozi){
                        konstY = true;
                    }
                    else {
                        konstY = false;
                    }
                }
            
            }
        }
        
        if(konstX == true){
            xy = x;
            smerStr = "x";
        }
        
        if(konstY == true){
            xy = y;
            smerStr = "y";
        }
        
        smer.put(smerStr, xy);
        
        return(smer);
        
    }
    
    private ArrayList<HashMap<String, Integer>> vratNavazujiciRadu(ArrayList<HashMap<String, Integer>> rohovePixely, int index, boolean posledniHledani){
         
        HashMap<String, Integer> rohovyPixel = new HashMap<String, Integer>();
        int orientaceRoh;
        int orientaceNavazujiciRada;
        boolean druheCislo = true;
        
        ArrayList<HashMap<String, Integer>> sousedRada = new ArrayList<HashMap<String, Integer>>();
        
        rohovyPixel = rohovePixely.get(index);
        orientaceRoh = rohovyPixel.get("orientaceRoh");
        
        //prenastavi hledani na prvni cislo z oznaceni rohu
        if(posledniHledani == true){
            druheCislo = false;
        }
        
        //vetsinou vyhledava druheCislo = true, obcas druheCislo = false
        orientaceNavazujiciRada = vratIndexOrientaceNavazujiciRady(orientaceRoh, druheCislo);
        
        
        if(orientaceNavazujiciRada == 1){
            sousedRada = sousedVychod;
        }
        if(orientaceNavazujiciRada == 2){
            sousedRada = sousedJih;
        }
        if(orientaceNavazujiciRada == 3){
            sousedRada = sousedZapad;
        }
        if(orientaceNavazujiciRada == 4){
            sousedRada = sousedSever;
        }
        
        return(sousedRada);
        
    }
    
    //z oznaceni 32 vrati 2
    private int vratIndexOrientaceNavazujiciRady2(int orientaceRoh){
        
        int orientaceNavazujiciRada;
        orientaceNavazujiciRada = orientaceRoh;
        
        if(orientaceRoh < 10){
            orientaceNavazujiciRada = orientaceRoh;
        }
        else {
            for (int i = 1; i < 5; i++) {

                orientaceNavazujiciRada = orientaceNavazujiciRada - 10;
                if(orientaceNavazujiciRada < 10){
                    break;
                }
            }
        }
        
        return(orientaceNavazujiciRada);
        
    }
    
    
    private int vratIndexOrientaceNavazujiciRady(int orientaceRoh, boolean druheCislo){
        
        String orientaceRohStr;
        char[] poleZnaku;
        char prvniZnak;
        char druhyZnak;
        char znakPozadovany;
        int orientaceRada;
        String znakStr;
        
        orientaceRohStr = "" + orientaceRoh;
        char[] ch = new char[orientaceRohStr.length()]; 
        
        // Copy character by character into array 
        for (int i = 0; i < orientaceRohStr.length(); i++) { 
            ch[i] = orientaceRohStr.charAt(i); 
        } 
        
        prvniZnak = ch[0];
        druhyZnak = ch[1];
        
        
        if(druheCislo == true){
            znakPozadovany = druhyZnak;
        }
        else {
            znakPozadovany = prvniZnak;
        }
        
        znakStr = String.valueOf(znakPozadovany);
        orientaceRada = parseInt(znakStr);
        
        System.out.print("");
        
        return(orientaceRada);
        
    }
    
    
    //private int vratIndexOrientaceNavazujiciRady(int orientaceRoh){
        
        
    //}
    
    private boolean zapisRohovePixelyKDaneStrane(ArrayList<HashMap<String, Integer>> sousediciPixely, String smerHledani){
       
        ArrayList<HashMap<String, Integer>> rohovePixelyOrig = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> rohovePixelyNew = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> rgb;
        
        boolean danyPixelExistuje = false;
        int orientaceRoh = -1;
        int x;
        int y;
        
        boolean ukoncitHledani = false;
        int pocetKoliznichPixelu = 0;   //pokud detekuje pocet pixelu, ktere nemuze jiz zapsat
                                    //kteri se rovnaji poctu vsech nalezenych pixelu
                                    //tj. vsechny pixely jsou si jiz duplicitni, 
                                    //pak nastavi flag na ukonceni smycky (ukoncitHledani = true)
        
        
        rohovePixelyOrig = vratKrajniPixelyNaHranici(sousediciPixely, smerHledani);
        
        for (int i = 0; i < rohovePixelyOrig.size(); i++) {
            rgb = new HashMap<String, Integer>();
            rgb = rohovePixelyOrig.get(i);
            
            x = rgb.get("x_Hlavni");
            y = rgb.get("y_Hlavni");
            
            danyPixelExistuje = detekujZdaRohovyPixelExistuje(x, y);
            
            if(danyPixelExistuje == false){
                
                orientaceRoh = vratIndexOrientaceRohovehoSouseda(x, y, smerHledani);
                
                
                //zapisuje pokud je roh 2-ciselny, 
                //pokud je jednociselny, zapisuje se v metode orientaceRoh.
                if(orientaceRoh > 9){
                    zapisRohovyPixel(orientaceRoh, x, y);
                    System.out.print("");
                }
                
            }
            else {  //pokud neni mozno zapsat zadny novy pixel, pak ukonci vyhledavani
                pocetKoliznichPixelu = pocetKoliznichPixelu + 1;
            }
        }
        
        if(pocetKoliznichPixelu == rohovePixelyOrig.size()){
            ukoncitHledani = true;
        }
        
        return (ukoncitHledani);

 
    }
    
    
    private boolean detekujZdaRohovyPixelExistuje(int x, int y){
        
        int xPixel;
        int yPixel;
        boolean danyPixelExistuje = false;
        
        HashMap<String, Integer> pixel;
        for (int i = 0; i < rohovePixely.size(); i++) {    
            pixel = new HashMap<String, Integer>();
            pixel = rohovePixely.get(i);
            
            xPixel = pixel.get("x");
            yPixel = pixel.get("y");
            
            if(xPixel == x){
                if(yPixel == y){
                    danyPixelExistuje = true;
                    break;
                }
            }
            
        }
        
        return(danyPixelExistuje);
    }
    
    
    //vraci orientaci podle toho, v jakem smeru je pixel nalezen
    private int vratIndexOrientaceRohovehoSouseda(int x, int y, String smerHledani){

        int orientaceSouseda = -1;
        String orientaceSousedaStr = "";
        String orientaceSousedaRoh = "";
        int orientaceRoh = -1;
        
        int x2;
        int y2;
        int orientaceRoh2 = -1;
        
        boolean jeSousedem = false;
        ArrayList<HashMap<String, Integer>> soused = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> nejblizsiPixelPresRoh = null;
        
        for (int i = 1; i < 5; i++) {
            orientaceSousedaStr = "";
            
            if(i == 1){
                soused = sousedVychod;
            }
            if(i == 2){
                soused = sousedJih;
            }
            if(i == 3){
                soused = sousedZapad;
            }
            if(i == 4){
                soused = sousedSever;
            }
            
            jeSousedem = zkontrolujZdaObsahujeDanyPixel(soused, x, y);
            
            //je mozne, ze se jedna o roh do vnitr, pak projde pole jinym algoritmem
            //a prenastavi status
            
            
                
            if(jeSousedem == true){
                orientaceSouseda = i;
                orientaceSousedaStr = "" + orientaceSouseda;
                orientaceSousedaRoh = orientaceSousedaRoh + orientaceSousedaStr;
            }       
        }
        
        orientaceRoh = Integer.parseInt(orientaceSousedaRoh);  
        
        
        if(orientaceRoh > 9){
            //prizpusobi zapis cisel podle smeru hledani
            orientaceRoh = vratPoradiOznaceniOrientaceRohu(orientaceRoh, smerHledani);
        }
        else{    //muze se stat, ze souseda nenalezl, pak kod bezi tudy
           
            //zapise oba pixely, do jednoho radku
            nejblizsiPixelPresRoh = new HashMap<String, Integer>();
            nejblizsiPixelPresRoh = vratNejblizsiPixelPresRoh(x, y, orientaceRoh);
            orientaceRoh2 = nejblizsiPixelPresRoh.get("orientace");
            x2 = nejblizsiPixelPresRoh.get("x_Soused");
            y2 = nejblizsiPixelPresRoh.get("y_Soused");
            
            zapisRohovyPixelNesouhlasny(orientaceRoh, orientaceRoh2, x, y, x2, y2);
            
            orientaceRoh = -1;
     
        }
        
        return(orientaceRoh);
        
    }
    
    
    private int vratPoradiOznaceniOrientaceRohu(int stavajiciOznaceni, String smerHledani){
        
        int prvniCisloOcekavane1 = -1;
        int prvniCisloOcekavane2 = -1;
        int prvniCislo;
        int druheCislo;
        boolean prohoditCisla = true;
        int oznaceniNew;
        
        System.out.print("");
        druheCislo = vratIndexOrientaceNavazujiciRady(stavajiciOznaceni, true);
        prvniCislo = (stavajiciOznaceni - druheCislo) / 10;
        
        
        if(smerHledani == "y"){
            prvniCisloOcekavane1 = 1;
            prvniCisloOcekavane2 = 3;
        }
        if(smerHledani == "x"){
            prvniCisloOcekavane1 = 2;
            prvniCisloOcekavane2 = 4;
        }
        
        if(prvniCislo == prvniCisloOcekavane1){
            prohoditCisla = false;
        }
        if(prvniCislo == prvniCisloOcekavane2){
            prohoditCisla = false;
        }
        
        if(prohoditCisla == true){
            oznaceniNew = druheCislo * 10 + prvniCislo;
        }
        else {
            oznaceniNew = stavajiciOznaceni;
        }
        
        
        
        
        return(oznaceniNew);
           
    }
    
    
    private void zapisRohovyPixel(int orientaceRoh, int x, int y){
        
        HashMap<String, Integer> rgbNew = new HashMap<String, Integer>();
        
        rgbNew.put("x", x);
        rgbNew.put("y", y);
        rgbNew.put("orientaceRoh", orientaceRoh);
                
        rohovePixely.add(rgbNew);
    }
    
    
    private void zapisRohovyPixelNesouhlasny(int orientace1, int orientace2, int x1, int y1, int x2, int y2){
        
        int orientacePresRoh;
        HashMap<String, Integer> rgbNew = new HashMap<String, Integer>();
        
        orientacePresRoh = orientace1 * 10 + orientace2;
        
        rgbNew.put("x", x2);
        rgbNew.put("y", y2);
        rgbNew.put("orientaceRoh", orientacePresRoh);
        
        rgbNew.put("x_Konec", x1);
        rgbNew.put("y_Konec", y1);
        
        rohovePixely.add(rgbNew);
        
    }
    
    
    //muze se stat ze nenaleza souseda pres roh, proto jde kod tudy
    //zapise nejblizsiho souseda pres roh
    private HashMap<String, Integer> vratNejblizsiPixelPresRoh(int xOrig, int yOrig, int nevybiratSmer){
        
        int nevybiratSmer2;
        int indexSousedaNejblize;
        
        ArrayList<HashMap<String, Integer>> soused = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> nejblizsiPixelyPresRoh = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> nejblizsiPixelPresRohVybrany = new HashMap<String, Integer>();
        HashMap<String, Integer> nejblizsiPixelPresRoh = null;
        
        nevybiratSmer2 = nevybiratSmer - 2;
        if(nevybiratSmer2 < 1){
            nevybiratSmer2 = nevybiratSmer + 2;
        }
        
        for (int i = 1; i < 5; i++) {
            
            if(i != nevybiratSmer){
                if(i != nevybiratSmer2){
                    if(i == 1){
                        soused = sousedVychod;
                    }
                    if(i == 2){
                        soused = sousedJih;
                    }
                    if(i == 3){
                        soused = sousedZapad;
                    }
                    if(i == 4){
                        soused = sousedSever;
                    }
                
                    nejblizsiPixelPresRoh = new HashMap<String, Integer>();
                    nejblizsiPixelPresRoh = vratNejblizsiPixelPresRoh(soused, xOrig, yOrig);
                    nejblizsiPixelPresRoh.put("orientace", i);
                    
                    nejblizsiPixelyPresRoh.add(nejblizsiPixelPresRoh);
                    
                }
            }
        } 
        
        indexSousedaNejblize = vyberIndexSousedaKteryJeNejblize(nejblizsiPixelyPresRoh);
        nejblizsiPixelPresRohVybrany = nejblizsiPixelyPresRoh.get(indexSousedaNejblize);
        
        return(nejblizsiPixelPresRohVybrany);
        
    }
    
    //zapisRohovyPixel(int orientaceRoh, int x, int y)
    
    private void zapisPixelPresRohDoPole(HashMap<String, Integer> nejblizsiPixelPresRoh){
        
        int orientace;
        int x;
        int y;
        
    }
            
    
    private int vyberIndexSousedaKteryJeNejblize(ArrayList<HashMap<String, Integer>> nejblizsiPixelyPresRoh){
        
        int vzdalenostX;
        int vzdalenostY;
        double vzdalenost;
        double vzdalenostMin = -1;
        int indexMin = -1;
        HashMap<String, Integer> vzdalenosti;
        
        for (int i = 0; i < nejblizsiPixelyPresRoh.size(); i++) {
            vzdalenosti = new HashMap<String, Integer>();
            vzdalenosti = nejblizsiPixelyPresRoh.get(i);
            
            vzdalenostX = vzdalenosti.get("vzdalenostX");
            vzdalenostY = vzdalenosti.get("vzdalenostY");
            
            vzdalenost = sqrt(vzdalenostX * vzdalenostX + vzdalenostY * vzdalenostY);
            
            if(i == 0){
                vzdalenostMin = vzdalenost; 
                indexMin = i;
            }
            else {
                if(vzdalenost < vzdalenostMin){
                    vzdalenostMin = vzdalenost;
                    indexMin = i;
                }
                
            }
        }
        
        return(indexMin);
        
    } 
    
    
    private HashMap<String, Integer> vratNejblizsiPixelPresRoh(ArrayList<HashMap<String, Integer>> okraj, int xOriq, int yOrig){
        
        int xNew;
        int yNew;
        int xNewMin = -1;
        int yNewMin = -1;
        
        int vzdalenostX = -1;
        int vzdalenostY = -1;
        int vzdalenostXmin = -1;
        int vzdalenostYmin = -1;
        
        double vzdalenost;
        double vzdalenostMin = -1;
        
        HashMap<String, Integer> souradnice;
        HashMap<String, Integer> nejblizsiSoused = new HashMap<String, Integer>();
        
        for (int i = 0; i < okraj.size(); i++) {
            souradnice = new HashMap<String, Integer>();
            souradnice = okraj.get(i);
            xNew = souradnice.get("x_Hlavni");
            yNew = souradnice.get("y_Hlavni");
            
            vzdalenostX = abs(xOriq - xNew);
            vzdalenostY = abs(yOrig - yNew);
            vzdalenost = sqrt(vzdalenostX * vzdalenostX + vzdalenostY * vzdalenostY);
            
            if(i == 0){
                vzdalenostMin = vzdalenost;
                
                vzdalenostXmin = vzdalenostX;
                vzdalenostYmin = vzdalenostY;
                xNewMin = xNew;
                yNewMin = yNew;
            }
            else {
                if(vzdalenost < vzdalenostMin){
                    vzdalenostMin = vzdalenost;
                    
                    vzdalenostXmin = vzdalenostX;
                    vzdalenostYmin = vzdalenostY;
                    xNewMin = xNew;
                    yNewMin = yNew;
                }
            }
            
            System.out.print("");
        }
        
        nejblizsiSoused.put("x_Soused", xNewMin);
        nejblizsiSoused.put("y_Soused", yNewMin);
        nejblizsiSoused.put("vzdalenostX", vzdalenostXmin);
        nejblizsiSoused.put("vzdalenostY", vzdalenostYmin);
        
        return(nejblizsiSoused);
        
    }
    
    
    private ArrayList<HashMap<String, Integer>> vratPouzeSousediciPixelyNaHranici(ArrayList<HashMap<String, Integer>> hranicePixelu){        
        
        HashMap<String, Integer> pixelNaHranici;
        ArrayList<HashMap<String, Integer>> sousediciPixely = new ArrayList<HashMap<String, Integer>>();
       
        int x;
        int y;
        
        boolean jeToSousedniPixel;
        boolean hledatSousedniPixel;
        
        for (int i = 0; i < hranicePixelu.size(); i++) {
            
            pixelNaHranici = new HashMap<String, Integer>();
            pixelNaHranici = hranicePixelu.get(i);
            
            x = pixelNaHranici.get("x_Hlavni");
            y = pixelNaHranici.get("y_Hlavni");
        
            if(i == 0){
                sousediciPixely.add(pixelNaHranici);
            }
            else {
                jeToSousedniPixel = jePixelPixelemPozadovanym(x, y, sousediciPixely, true);
                if(jeToSousedniPixel == true){
                    sousediciPixely.add(pixelNaHranici);
                }
            }
        }
        
        return(sousediciPixely);
  
    }
    
    
    private ArrayList<HashMap<String, Integer>>  vratRaduJenUrcitePoradnice(ArrayList<HashMap<String, Integer>> rada, int poradnice, String smerHledani){
        
        HashMap<String, Integer> pixelNaHranici;
        ArrayList<HashMap<String, Integer>> radaNew = new ArrayList<HashMap<String, Integer>>();
        
        String klicHledani = "??";
        int poradniceHled = -1;
        
        if(smerHledani == "x"){
            klicHledani = "y_Hlavni";
        }
        if(smerHledani == "y"){
            klicHledani = "x_Hlavni";
        }
        
        for (int i = 0; i < rada.size(); i++) {
            pixelNaHranici = new HashMap<String, Integer>();
            pixelNaHranici = rada.get(i);
            
            poradniceHled = pixelNaHranici.get(klicHledani);
            if(poradniceHled == poradnice){
                radaNew.add(pixelNaHranici);
            }
        }
        
        return(radaNew);
    }
    
    
    private boolean jePixelPixelemPozadovanym(int xOrig, int yOrig, ArrayList<HashMap<String, Integer>> hranicePixelu, boolean hledatSousedniPixel){
        
        HashMap<String, Integer> pixelNaHranici;
        
        int xNew;
        int yNew;
        
        boolean jeToPozadovanyPixel = false;
        
        for (int i = 0; i < hranicePixelu.size(); i++) {
            
            pixelNaHranici = new HashMap<String, Integer>();
            pixelNaHranici = hranicePixelu.get(i);
            
            xNew = pixelNaHranici.get("x_Hlavni");
            yNew = pixelNaHranici.get("y_Hlavni");
            
            //metoda je volana ze dvou stran:
            //1) bud je pozadovana detekce sousedniho pixelu, nebo
            //2) je pozadovana detekce pixelu se stejnymi souradnicemi
            // --> k tomu slouzi priznak hledatSousedniPixel
            
            if(hledatSousedniPixel == true){
                jeToPozadovanyPixel = detekujZdaSeJednaOSousedniPixel(xOrig, yOrig, xNew, yNew);

                if(jeToPozadovanyPixel == true){
                    break;
                }
            }
            else {
                if(xOrig == xNew){
                    if(yOrig == yNew){
                        jeToPozadovanyPixel = true;
                    }
                }
            }
        }
            
        return(jeToPozadovanyPixel);   
        
    }
    
    
    private boolean detekujZdaSeJednaOSousedniPixel(int xA, int yA, int xB, int yB){
        
        int rozdilX;
        int rozdilY;
        boolean jeToSoused = false;
        
        rozdilX = abs(xA - xB);
        rozdilY = abs(yA - yB);
        
        if(rozdilX == 1){
            if(rozdilY == 0){
                jeToSoused = true;
            }
        }
        
        if(rozdilY == 1){
            if(rozdilX == 0){
                jeToSoused = true;
            }
        }
        
        return(jeToSoused);
        
    }
    
    
    private boolean zkontrolujZdaObsahujeDanyPixel(ArrayList<HashMap<String, Integer>> okraj, int xPozadovany, int yPozadovany){
        
        int iPlus;
        int iMinus;
        boolean jeToPixelPozadovany;
        boolean obsahujePozadovanyPixel = false;
        HashMap<String, Integer> pixelNaOkraji;
        
        int xOkraj = -1;
        int yOkraj = -1;
        
        iPlus = -1;
        iMinus = okraj.size();
        
        for (int i = 0; i < okraj.size(); i++) {
            
            //porovnava pixel od zacatku
            iPlus = iPlus + 1;
            pixelNaOkraji = okraj.get(iPlus);
           
            xOkraj = pixelNaOkraji.get("x_Hlavni");
            yOkraj = pixelNaOkraji.get("y_Hlavni");
            
            jeToPixelPozadovany = detekujZdaSeJednaStejnyPixel(xOkraj, yOkraj, xPozadovany, yPozadovany);
            
            if(jeToPixelPozadovany == true){
                obsahujePozadovanyPixel = true;
                break;
            }
            
            
            //porovnava pixel od konce
            iMinus = iMinus - 1;
            pixelNaOkraji = okraj.get(iMinus);
            
            xOkraj = pixelNaOkraji.get("x_Hlavni");
            yOkraj = pixelNaOkraji.get("y_Hlavni");
            
            jeToPixelPozadovany = detekujZdaSeJednaStejnyPixel(xOkraj, yOkraj, xPozadovany, yPozadovany);
            
            if(jeToPixelPozadovany == true){
                obsahujePozadovanyPixel = true;
                break;
            }
            
        }
        
        return(obsahujePozadovanyPixel);
    }
    
    
    
    
    
    private boolean detekujZdaSeJednaStejnyPixel(int xA, int yA, int xB, int yB){
        
        boolean jednaSeOStejnyPixel = false;
        
        if(xA == xB){
            if(yA == yB){
                jednaSeOStejnyPixel = true;
            }
        }
        
        return(jednaSeOStejnyPixel);
        
    }
    
    
    private ArrayList<HashMap<String, Integer>> vratKrajniPixelyNaHranici(ArrayList<HashMap<String, Integer>> okraj, String smerHledani){
        
        int[] pocetSouseduPole = null;
        int pocetSousedu;
        
        HashMap<String, Integer> pixel;
        ArrayList<HashMap<String, Integer>> krajniPixely = new ArrayList<HashMap<String, Integer>>();
                
        pocetSouseduPole = vratPoleSPoctemSousednichIndexu(okraj, smerHledani);
        
        
        for (int i = 0; i < pocetSouseduPole.length; i++) {
            pocetSousedu = pocetSouseduPole[i];
            if(pocetSousedu == 1){
                pixel = new HashMap<String, Integer>();
                pixel = okraj.get(i);
                krajniPixely.add(pixel);
            }
        }
        
        return(krajniPixely);
        
    }
    
    
    private int[] vratPoleSPoctemSousednichIndexu(ArrayList<HashMap<String, Integer>> okraj, String smerHledani){
        
        int delkaPole;
        delkaPole = okraj.size();
        
        int[] pocetSouseduPole = new int[delkaPole];
        int pocetSousedu;
        
        for (int i = 0; i < okraj.size(); i++) {
            pocetSousedu = vratPocetSousednichPixeluKindexu(okraj, i, smerHledani);
            pocetSouseduPole[i] = pocetSousedu;
        }
        
        return (pocetSouseduPole);
    }
    
    private int vratPocetSousednichPixeluKindexu(ArrayList<HashMap<String, Integer>> okraj, int index, String smerHledani){
        
        int xVychozi = - 1;
        int yVychozi = - 1;
        
        int xSoused = -1;
        int ySoused = -1;
        
        boolean existujeSousedniPixel;
        int pocetSousednichPixelu = 0;
        HashMap<String, Integer> pixel = new HashMap<String, Integer>();
        
        pixel = okraj.get(index);
        xVychozi = pixel.get("x_Hlavni");
        yVychozi = pixel.get("y_Hlavni");
        
        //hleda 1. souseda
        if(smerHledani == "x"){
            xSoused = xVychozi + 1;
            ySoused = yVychozi;
        }
        
        if(smerHledani == "y"){
            xSoused = xVychozi;
            ySoused = yVychozi + 1;
        }
        
        //proveri, zda je pixel skutecne sousednim
        existujeSousedniPixel = zkontrolujZdaObsahujeDanyPixel(okraj, xSoused, ySoused);
        if(existujeSousedniPixel == true){
            pocetSousednichPixelu = pocetSousednichPixelu + 1;
        }
        
        //hleda 2. souseda
        if(smerHledani == "x"){
            xSoused = xVychozi - 1;
            ySoused = yVychozi;
        }
        
        if(smerHledani == "y"){
            xSoused = xVychozi;
            ySoused = yVychozi - 1;
        }
        
        //proveri, zda je pixel skutecne sousednim
        existujeSousedniPixel = zkontrolujZdaObsahujeDanyPixel(okraj, xSoused, ySoused);
        if(existujeSousedniPixel == true){
            pocetSousednichPixelu = pocetSousednichPixelu + 1;
        }
        
        return(pocetSousednichPixelu);
        
    }
    
    
    private String zmenSmerHledani(String smerHledani){
        
        String smerHledaniNew = null;
        
        if(smerHledani == "x"){
            smerHledaniNew = "y";
        }
        
        if(smerHledani == "y"){
            smerHledaniNew = "x";
        }
        
        return (smerHledaniNew);
        
    }
    
    
}
