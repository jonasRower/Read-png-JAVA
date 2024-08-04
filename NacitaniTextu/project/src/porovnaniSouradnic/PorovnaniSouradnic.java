
package porovnaniSouradnic;

import ZdrojovaData.zdrojDataAbeceda;
import java.util.ArrayList;
import java.util.HashMap;


public class PorovnaniSouradnic {
    
    //data obdsahuji posouzeni vsech pismen vsech radku z obrazku vuci vsem pismenum abecedy
    ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> posouzeniPismenePngVsechRadkuKeVsemPismenumABC = new ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>>();
    
    //obshuje pole pismen v celem obrazku - radcich + sloupcich
    ArrayList<ArrayList<String>> pismenaVPng = new ArrayList<ArrayList<String>>();
    
    public PorovnaniSouradnic(ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> posouzeniPismenePngVsechRadkuKeVsemPismenABC, String adresaProjektu){
        
        //data obsahuji posouzeni vsech pismen jednoho radku z obrazku vuci vsem pismenum abecedy
        ArrayList<ArrayList<HashMap<String, Double>>> posouzeniPismenePngJednohoRadkuKeVsemPismenABC = new ArrayList<ArrayList<HashMap<String, Double>>>();
        
        //data obsahuji posouzeni jednoho pismene z obrazku vuci vsem pismenum abecedy, ten s nejmensi odchylkaSqrt vyhrava
        ArrayList<HashMap<String, Double>> posouzeniJednohoPismenePngKeVsemPismenumABC = new ArrayList<HashMap<String, Double>>();
        
        //obshuje pole pismen na radku
        ArrayList<String> pismenaNaRadku = null;
        

         
        //nazvy pismen
        String[] NazvySouboruPng;
        
        String pismeno;
        
        zdrojDataAbeceda pismenaABC = new zdrojDataAbeceda(adresaProjektu);
        NazvySouboruPng = pismenaABC.getNazvySouboruPng();
        
        this.posouzeniPismenePngVsechRadkuKeVsemPismenumABC = posouzeniPismenePngVsechRadkuKeVsemPismenABC;
        
        for (int r = 0; r < posouzeniPismenePngVsechRadkuKeVsemPismenumABC.size(); r++) {
            posouzeniPismenePngJednohoRadkuKeVsemPismenABC = posouzeniPismenePngVsechRadkuKeVsemPismenumABC.get(r);
            pismenaNaRadku = new ArrayList<String>();
            
            for (int s = 0; s < posouzeniPismenePngJednohoRadkuKeVsemPismenABC.size(); s++) {
                posouzeniJednohoPismenePngKeVsemPismenumABC = posouzeniPismenePngJednohoRadkuKeVsemPismenABC.get(s);
        
                pismeno = vratOdpovidajiciPismena(posouzeniJednohoPismenePngKeVsemPismenumABC, NazvySouboruPng);
                pismenaNaRadku.add(pismeno);
            } 
            
            pismenaVPng.add(pismenaNaRadku);
            System.out.print("");
        }   
        
        System.out.print("");
    }
    
    
    public ArrayList<ArrayList<String>> getPismenaVPng(){
        return(pismenaVPng);
    }
    
    private String vratOdpovidajiciPismena(ArrayList<HashMap<String, Double>> posouzeniVsechPismenPngKeVsemPismenumABC, String[] NazvySouboruPng){
        
        double nejmensiHodnota = -999;
        double posuzovanaHodnota;
        int indexOdpovidajicihoPismene = -1;
        String pismeno;
        
        HashMap<String, Double> posouzeniJednohoPismenePngKeVsemPismenumABC = new HashMap<String, Double>();
        HashMap<String, Double> posouzeniData = new HashMap<String, Double>();
      
        for (int i = 0; i < posouzeniVsechPismenPngKeVsemPismenumABC.size(); i++) {
            posouzeniJednohoPismenePngKeVsemPismenumABC = posouzeniVsechPismenPngKeVsemPismenumABC.get(i);
            posuzovanaHodnota = posouzeniJednohoPismenePngKeVsemPismenumABC.get("odchylkaSqrt");
      
            if(i == 0){
                nejmensiHodnota = posuzovanaHodnota;
                indexOdpovidajicihoPismene = i;
            }
            else {
                if(posuzovanaHodnota < nejmensiHodnota){
                    nejmensiHodnota = posuzovanaHodnota;
                    indexOdpovidajicihoPismene = i;
                }
            }
            System.out.print("");
        }
        
        
        if(indexOdpovidajicihoPismene > -1){
            pismeno = vratOdpovidajiciPismeno(NazvySouboruPng, indexOdpovidajicihoPismene);
        }
        else {
            pismeno = "Err";
        }
        
        return (pismeno);
        
    }
    
    
    private String vratOdpovidajiciPismeno(String[] NazvySouboruPng, int indexOdpovidajicihoPismene){
        
        String nazevSouboru;
        String pismeno;
        String[] splitArr;
        
        nazevSouboru = NazvySouboruPng[indexOdpovidajicihoPismene];
        splitArr = nazevSouboru.split("\\.");
        pismeno = splitArr[0];
        
        return(pismeno);
    }

    
}
