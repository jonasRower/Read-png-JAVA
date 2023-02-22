
// Vycistit PorovnaniSouradnic - je v tom binec
// dat breakpoint na r. 52 a spustit. Dodelat rozpoznani nejmensi prumerRozdilu

//prumerRozdiluX a Y udava prumernou hodnotu vsech rozdilu mezi jednotlivimi pixely daneho pismenka
//tam kde je prumer nejmensi, bde se jednat o vybrane pismeno

package porovnaniSouradnic;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.HashMap;


public class PosouzeniSouradnic {
    
    ArrayList<ArrayList<HashMap<String, Integer>>> MapaPismenAbeceda = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcich = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    
    //data obdsahuji posouzeni vsech pismen vsech radku z obrazku vuci vsem pismenum abecedy
    ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> posouzeniPismenePngVsechRadkuKeVsemPismenABC = new ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>>();
    
    
    public PosouzeniSouradnic(ArrayList<ArrayList<HashMap<String, Integer>>> MapaPismenAbeceda, ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcich){
        
        this.MapaPismenAbeceda = MapaPismenAbeceda;
        this.MapaVsechPismenNaVsechRadcich = MapaVsechPismenNaVsechRadcich;
        
        //zatim provizorne ziskavam data jakozto dilci mapy pismenek, ktere budou porovnavat
        ArrayList<ArrayList<HashMap<String, Integer>>> MapaVsechPismenNaJednomRadku = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<HashMap<String, Integer>> MapaPismeneZVybranehoSloupce = new ArrayList<HashMap<String, Integer>>();
        
        //data obdsahuji posouzeni jednoho pismene z obrazku vuci vsem pismenum abecedy, ten s nejmensi odchylkaSqrt vyhrava
        ArrayList<HashMap<String, Double>> posouzeniJednohoPismenePngKeVsemPismenumABC = new ArrayList<HashMap<String, Double>>();
        
        ArrayList<ArrayList<HashMap<String, Double>>> posouzeniVsechPismenPngKeVsemPismenumABC = new ArrayList<ArrayList<HashMap<String, Double>>>();
        
        //data obdsahuji posouzeni vsech pismen jednoho radku z obrazku vuci vsem pismenum abecedy
        ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> posouzeniPismenePngJednohoRadkuKeVsemPismenABC = null;
        
        for (int r = 0; r < MapaVsechPismenNaVsechRadcich.size(); r++) {
            MapaVsechPismenNaJednomRadku = MapaVsechPismenNaVsechRadcich.get(r);
            posouzeniVsechPismenPngKeVsemPismenumABC = new ArrayList<ArrayList<HashMap<String, Double>>>();
            
            for (int s = 0; s < MapaVsechPismenNaJednomRadku.size(); s++) {
                MapaPismeneZVybranehoSloupce = MapaVsechPismenNaJednomRadku.get(s);
        
                posouzeniJednohoPismenePngKeVsemPismenumABC = (provedPosouzeniProVsechnaPismenaAbecedy(MapaPismeneZVybranehoSloupce, MapaPismenAbeceda));
                posouzeniVsechPismenPngKeVsemPismenumABC.add(posouzeniJednohoPismenePngKeVsemPismenumABC);
                System.out.print("");
            } 
            
            posouzeniPismenePngVsechRadkuKeVsemPismenABC.add(posouzeniVsechPismenPngKeVsemPismenumABC);
        }    
        
        //posouzeniPismenePngVsechRadkuKeVsemPismenABC.add(posouzeniPismenePngJednohoRadkuKeVsemPismenABC);
        System.out.print("");
    }
    
  
    public ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> getPosouzeni(){
        
        return(posouzeniPismenePngVsechRadkuKeVsemPismenABC);
        
    }
    
    //porovna vybrane pismeno z png se vsema pismenama z abecedy
    private ArrayList<HashMap<String, Double>> provedPosouzeniProVsechnaPismenaAbecedy(ArrayList<HashMap<String, Integer>> MapaPismenePng, ArrayList<ArrayList<HashMap<String, Integer>>> MapaPismenAbeceda){
        
        //data obdsahuji posouzeni jednoho pismene z obrazku vuci vsem pismenum abecedy, ten s nejmensi odchylkaSqrt vyhrava
        ArrayList<HashMap<String, Double>> posouzeniJednohoPismenePngKeVsemPismenABC = new ArrayList<HashMap<String, Double>>();
        
        ArrayList<HashMap<String, Integer>> MapaPismeneZAbecedy = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Double> posouzeniJednohoPismeneABC = new HashMap<String, Double>();
        
        
        for (int i = 0; i < MapaPismenAbeceda.size(); i++) {
            MapaPismeneZAbecedy = MapaPismenAbeceda.get(i);
            
            posouzeniJednohoPismeneABC = vratPosouzeniTamANazpet(MapaPismenePng, MapaPismeneZAbecedy);
            posouzeniJednohoPismenePngKeVsemPismenABC.add(posouzeniJednohoPismeneABC);
        }
        
        return (posouzeniJednohoPismenePngKeVsemPismenABC);
   
    }
    
    
    //posouzeni tam a nazpet
    private HashMap<String, Double> vratPosouzeniTamANazpet(ArrayList<HashMap<String, Integer>> MapaPismenePng, ArrayList<HashMap<String, Integer>> MapaPismeneAbeceda){
    
        //porovna jeden pixel zkoumaneho pismena ke vsem pixelum pismena abecedy
        HashMap<String, Double> posouzeniTam = new HashMap<String, Double>();
        
        //porovna jeden pixel pismena abecedy ke vsem pixelum zkoumaneho pismena
        HashMap<String, Double> posouzeniZpet = new HashMap<String, Double>();
        
        //vraci to posouzeni, ktere je z obou smeru maximalni (porovnava pouze odchylkuSqrt)
        HashMap<String, Double> posouzeniMax = new HashMap<String, Double>();
        
        double odchylkaSqrtTam;
        double odchylkaSqrtZpet;
        
        posouzeniTam = vratPosouzeni(MapaPismenePng, MapaPismeneAbeceda);
        posouzeniZpet = vratPosouzeni(MapaPismeneAbeceda, MapaPismenePng); 
        
        odchylkaSqrtTam = posouzeniTam.get("odchylkaSqrt");
        odchylkaSqrtZpet = posouzeniZpet.get("odchylkaSqrt");
        
        
        if(odchylkaSqrtTam > odchylkaSqrtZpet){
            posouzeniMax = posouzeniTam;
        }
        else {
            posouzeniMax = posouzeniZpet;
        }
        
        
        return(posouzeniMax);
        
    }

   
    //zkusit volat metodu taky obracene
    private HashMap<String, Double> vratPosouzeni(ArrayList<HashMap<String, Integer>> MapaPismenePng, ArrayList<HashMap<String, Integer>> MapaPismeneAbeceda){
        
        //MapaPismenePng obsahuje pouze jedno pismeno z png
        //MapaPismeneAbeceda obsahuje pouze jedno pismeno z abecedy
        
        int xABC;
        int yABC;
        
        double prumerRozdiluX;
        double prumerRozdiluY;
        
        double odchylkaOdPrumeruX = 0;
        double odchylkaOdPrumeruY = 0;
        
        double odchylkaOdPrumeruXprumer;
        double odchylkaOdPrumeruYprumer;
        
        double odchylkaSqrt;
        
        
        HashMap<String, Integer> RGBabc = new HashMap<String, Integer>();
        HashMap<String, Integer> nejblizsiSouradnice = new HashMap<String, Integer>();
        HashMap<String, Double> posouzeni = new HashMap<String, Double>();
        
        //obsahuje nejblizsi souradnice z png vztazene ke kazde sozradnici v abc
        ArrayList<HashMap<String, Integer>> nejblizsiSouradniceVsechPixeluABC = new ArrayList<HashMap<String, Integer>>();
        
        
        //sestavi pole nejblizsiSouradniceVsechPixeluABC
        for (int i = 0; i < MapaPismeneAbeceda.size(); i++) {
            
            RGBabc = MapaPismeneAbeceda.get(i);
            
            try {
                xABC = RGBabc.get("x");
                yABC = RGBabc.get("y");
                
                nejblizsiSouradnice = vratNejblizsiSouradnici(MapaPismenePng, xABC, yABC);
                nejblizsiSouradniceVsechPixeluABC.add(nejblizsiSouradnice);
                
            }
            
            catch(Exception e){
                System.out.println(e);
            }
  
        } 
        
        
        //dopocita prumerny rozdil X a Y
        prumerRozdiluX = vratPrumernouHodnotuRozdiluSouradnic(nejblizsiSouradniceVsechPixeluABC, "x");
        prumerRozdiluY = vratPrumernouHodnotuRozdiluSouradnic(nejblizsiSouradniceVsechPixeluABC, "y");
        
        odchylkaOdPrumeruX = vratOdchylkuOdPrumeru(nejblizsiSouradniceVsechPixeluABC, "x", prumerRozdiluX);
        odchylkaOdPrumeruY = vratOdchylkuOdPrumeru(nejblizsiSouradniceVsechPixeluABC, "y", prumerRozdiluY);
        
        odchylkaOdPrumeruXprumer = odchylkaOdPrumeruX * prumerRozdiluX;
        odchylkaOdPrumeruYprumer = odchylkaOdPrumeruY * prumerRozdiluY;
        
        odchylkaSqrt = sqrt(odchylkaOdPrumeruXprumer + odchylkaOdPrumeruYprumer);
        
        
        posouzeni.put("prumerRozdiluX", prumerRozdiluX);
        posouzeni.put("prumerRozdiluY", prumerRozdiluY);
        posouzeni.put("odchylkaOdPrumeruX", odchylkaOdPrumeruX);
        posouzeni.put("odchylkaOdPrumeruY", odchylkaOdPrumeruY);
        posouzeni.put("odchylkaOdPrumeruXprumer", odchylkaOdPrumeruXprumer);
        posouzeni.put("odchylkaOdPrumeruYprumer", odchylkaOdPrumeruYprumer);
        posouzeni.put("odchylkaSqrt", odchylkaSqrt);
        
        
        return(posouzeni);
    }
    
    
    
    
    private HashMap<String, Integer> vratNejblizsiSouradnici(ArrayList<HashMap<String, Integer>> MapaPismenePng, int xABC, int yABC){
        
        int xPng = -999;
        int yPng = -999;
        
        int rozdilX;
        int rozdilY;
        
        int xPngMin = -999;
        int yPngMin = -999;
        
        double rozdil;
        double rozdilMin = 999;
        
        int indexSNejblizsiSouradnici = -1;
        boolean prvniCyklus = true;
        
        HashMap<String, Integer> RGBpng = new HashMap<String, Integer>();
        HashMap<String, Integer> nejblizsiSouradnice = new HashMap<String, Integer>();
        
        for (int i = 0; i < MapaPismenePng.size(); i++) {
            
            System.out.print("");
            
            RGBpng = MapaPismenePng.get(i);
            
            try{
               
                xPng = RGBpng.get("x");
                yPng = RGBpng.get("y");

                rozdilX = abs(xPng - xABC);
                rozdilY = abs(yPng - yABC);
                
                rozdil = sqrt(rozdilX*rozdilX + rozdilY*rozdilY);

                if(prvniCyklus == true){
                    rozdilMin = rozdil;
                    indexSNejblizsiSouradnici = i;
                    xPngMin = xPng;
                    yPngMin = yPng;
                    
                    prvniCyklus = false;
                }
                else {
                    if(rozdil < rozdilMin){
                        rozdilMin = rozdil;
                        indexSNejblizsiSouradnici = i;
                        xPngMin = xPng;
                        yPngMin = yPng;
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        
  
        nejblizsiSouradnice = vratMapuNejblizsiSouradnice(xPngMin, yPngMin, xABC, yABC);
        
        return (nejblizsiSouradnice);
        
    }
    
    
    private HashMap<String, Integer> vratMapuNejblizsiSouradnice(int xPng, int yPng, int xABC, int yABC){
        
        HashMap<String, Integer> nejblizsiSouradnice = new HashMap<String, Integer>();
        
        int rozdilX;
        int rozdilY;
        
        rozdilX = abs(xABC - xPng);
        rozdilY = abs(yABC - yPng);
        
        nejblizsiSouradnice.put("xPng", xPng);
        nejblizsiSouradnice.put("yPng", yPng);
        nejblizsiSouradnice.put("xABC", xABC);
        nejblizsiSouradnice.put("yABC", yABC);
        nejblizsiSouradnice.put("rozdilX", rozdilX);
        nejblizsiSouradnice.put("rozdilY", rozdilY);
        
        return (nejblizsiSouradnice);
        
    }
    
    
    private double vratPrumernouHodnotuRozdiluSouradnic(ArrayList<HashMap<String, Integer>> nejblizsiSouradniceVsechPixeluABC, String XneboY){
        
        HashMap<String, Integer> RGBrozdilySour = new HashMap<String, Integer>();
        int rozdilSour;
        int rozdilSourSuma = 0;
        
        double rozdilSourSumaDbl;
        double pocetHodnotDbl;
        double rozdilSourPrumerDbl;
        
        for (int i = 0; i < nejblizsiSouradniceVsechPixeluABC.size(); i++) {
            
            RGBrozdilySour = nejblizsiSouradniceVsechPixeluABC.get(i);
            
            if(XneboY == "x"){
                rozdilSour = RGBrozdilySour.get("rozdilX");
            }
            else {
                rozdilSour = RGBrozdilySour.get("rozdilY");
            }
            
            rozdilSourSuma = rozdilSourSuma + rozdilSour;
            
        }
        
 
        rozdilSourSumaDbl = rozdilSourSuma;
        pocetHodnotDbl = nejblizsiSouradniceVsechPixeluABC.size();
        rozdilSourPrumerDbl = rozdilSourSumaDbl/pocetHodnotDbl;
        
        
        return(rozdilSourPrumerDbl);
            
    }
    
    private double vratOdchylkuOdPrumeru(ArrayList<HashMap<String, Integer>> nejblizsiSouradniceVsechPixeluABC, String XneboY, double prumer){
        
        HashMap<String, Integer> RGBrozdilySour = new HashMap<String, Integer>();
        double maximalniRozdil = 0;
        double Rozdil = 0;
        double odchylkaOdPrumeru;
    
        
        for (int i = 0; i < nejblizsiSouradniceVsechPixeluABC.size(); i++) {
            
            RGBrozdilySour = nejblizsiSouradniceVsechPixeluABC.get(i);
            
            if(XneboY == "x"){
                Rozdil = RGBrozdilySour.get("rozdilX");
            }
            else {
                Rozdil = RGBrozdilySour.get("rozdilY");
            }
            
            if(Rozdil > maximalniRozdil){
                maximalniRozdil = Rozdil; 
            }
            
        }
        
        odchylkaOdPrumeru = abs(maximalniRozdil - prumer);
        
        return(odchylkaOdPrumeru);
        
    } 
    
}
