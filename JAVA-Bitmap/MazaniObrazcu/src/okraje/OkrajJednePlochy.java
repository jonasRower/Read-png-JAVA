
package okraje;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import static jdk.nashorn.internal.objects.NativeArray.map;


public class OkrajJednePlochy {
    
    //jedna se o sousedy na hranici (tzn. sousediJinaBarva) avsak pouze jedne barvy hlavni
    //proto, že je volano ze smycky roztridSousediPodleBarvy ve tride RuzneBarvySousedu
    private ArrayList<HashMap<String, Integer>> sousediJinaBarva = new ArrayList<HashMap<String, Integer>>();
    
    //uchovava data na jakou stranu jsou orientovani sousedi
    private ArrayList<HashMap<String, Integer>> sousedVychod = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedJih = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedZapad = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedSever = new ArrayList<HashMap<String, Integer>>();
    
    //uchovava vzdalenosti mezi okraji - svisle nebo vodorovne
    private ArrayList<HashMap<String, Integer>> svisleOkraje = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> vodorovneOkraje = new ArrayList<HashMap<String, Integer>>();
    
    //uchovava nejake parametry dane plochy
    private HashMap<String, Double> dataOPlose = new HashMap<String, Double>();
    
    
    public OkrajJednePlochy(ArrayList<HashMap<String, Integer>> sousediJinaBarva){
        
        this.sousediJinaBarva = sousediJinaBarva;
        
        roztridPodleOrientace();
        vratProtilehleOkrajeSvisleAVodorovne();
        dataOPlose = vypoctyPlochy();
        
    }
    
    
    public OkrajJednePlochy(ArrayList<HashMap<String, Integer>> sousedVychod, ArrayList<HashMap<String, Integer>> sousedJih, ArrayList<HashMap<String, Integer>> sousedZapad, ArrayList<HashMap<String, Integer>> sousedSever){
        
        this.sousedVychod = sousedVychod;
        this.sousedJih = sousedJih;
        this.sousedZapad = sousedZapad;
        this.sousedSever = sousedSever;
        
        vratProtilehleOkrajeSvisleAVodorovne();
        //dataOPlose = vypoctyPlochy();
        
    }
    
    
    public HashMap<String, Double> vratDataOPlose(){
        
        return(dataOPlose);
        
    }
    
    
    public ArrayList<HashMap<String, Integer>> getSvisleOkraje(){
        
        return(svisleOkraje);
        
    }
    
    
    public ArrayList<HashMap<String, Integer>> getVodorovneOkraje(){
        
        return(vodorovneOkraje);
        
    }
    
    
    
    private void roztridPodleOrientace(){
        
        int orientaceSouseda;
        HashMap<String, Integer> Soused;
       
        for (int i = 0; i < sousediJinaBarva.size(); i++) {
            
            Soused = new HashMap<String, Integer>();
            Soused = sousediJinaBarva.get(i);
            
            orientaceSouseda = Soused.get("orientace souseda");
            
            //je-li soused orientovan na vychod
            if(orientaceSouseda == 1){
                sousedVychod.add(Soused);
            }
            
            //je-li soused orientovan na jih
            if(orientaceSouseda == 2){
                sousedJih.add(Soused);
            }
            
            //je-li soused orientovan na zapad
            if(orientaceSouseda == 3){
                sousedZapad.add(Soused);
            }
            
            //je-li soused orientovan na sever
            if(orientaceSouseda == 4){
                sousedSever.add(Soused);
            }
            
        }
        
        System.out.print("");
    }
    
    
    private void vratProtilehleOkrajeSvisleAVodorovne(){
        

        String[] klice = new String[4];
        String smerHledani;
        
        // vraci data pro svisle okraje
        klice[0] = "x_zapad";
        klice[1] = "y_zapad";
        klice[2] = "x_vychod";
        klice[3] = "y_vychod";
        smerHledani = "x";
        
        svisleOkraje = vratProtilehlyOkraj(sousedVychod, sousedZapad, klice, smerHledani);
        
        
        // vraci data pro vodorovne okraje
        klice[0] = "x_sever";
        klice[1] = "y_sever";
        klice[2] = "x_jih";
        klice[3] = "y_jih";
        smerHledani = "y";
        
        vodorovneOkraje = vratProtilehlyOkraj(sousedJih, sousedSever, klice, smerHledani);
        
        
        System.out.print("");
        
    }
    
    //pokud se jedna o plochu s otvorem, je potreba rozdelit okraje na okraje podle souradnice
    //tak aby se jednalo o okraje proti sobe, ale okraje nejblizsi
    
    
    private ArrayList<HashMap<String, Integer>> vratProtilehlyOkraj(ArrayList<HashMap<String, Integer>> okraj1, ArrayList<HashMap<String, Integer>> okraj2, String[] klice, String smerHledani){
        
        ArrayList<HashMap<String, Integer>> protilehlyOkraj = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> protilehlaSouradnice = new HashMap<String, Integer>();
        
        HashMap<String, Integer> souradnice;
        
        int xPozadovane;
        int yPozadovane;
        
        if(smerHledani == "y"){
            System.out.print("");
        }
        
        for (int i = 0; i < okraj1.size(); i++) {
            
            souradnice = new HashMap<String, Integer>();      
            souradnice = okraj1.get(i);
            
            xPozadovane = souradnice.get("x_Hlavni");
            yPozadovane = souradnice.get("y_Hlavni");
            
            protilehlaSouradnice = najdiProtilehlePixely(okraj2, xPozadovane, yPozadovane, klice, smerHledani);
            //souradnicePosouzeni.add(protilehlaSouradnice);
            
            protilehlyOkraj.add(protilehlaSouradnice);
        }
        
        return(protilehlyOkraj);
        
    }
    
    
    private HashMap<String, Integer> najdiProtilehlePixely(ArrayList<HashMap<String, Integer>> okraj, int xPozadovane, int yPozadovane, String[] nazvyKlicu, String smerHledani){
        
        ArrayList<HashMap<String, Integer>> souradnicePosouzeni = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> protilehlaSouradnice = null;
        HashMap<String, Integer> souradnice;
        
        int yHledane;
        int xHledane;
        
        String vzdalenostKlic = null;
        int vzdalenost = -1;
        boolean vypisujVysledky = false;
        
        
        if(smerHledani == "x"){
            vzdalenostKlic = "vzdalenost_x";
        }
        if(smerHledani == "y"){
            vzdalenostKlic = "vzdalenost_y";
        }
        
        
        for (int i = 0; i < okraj.size(); i++) {
            
            souradnice = new HashMap<String, Integer>();
            souradnice = okraj.get(i);
            vypisujVysledky = false;
            
            yHledane = souradnice.get("y_Hlavni");
            xHledane = souradnice.get("x_Hlavni");
            
            if(smerHledani == "x"){
                if(yHledane == yPozadovane){
                    vypisujVysledky = true;
                    vzdalenost = abs(xHledane - xPozadovane);
                }
            }
            
            if(smerHledani == "y"){
                if(xHledane == xPozadovane){
                    vypisujVysledky = true;
                    vzdalenost = abs(yHledane - yPozadovane);
                }
            }
            
            if(vypisujVysledky == true){
                protilehlaSouradnice = new HashMap<String, Integer>();
                
                protilehlaSouradnice.put(nazvyKlicu[0], xPozadovane);
                protilehlaSouradnice.put(nazvyKlicu[1], yPozadovane);
                
                protilehlaSouradnice.put(nazvyKlicu[2], xHledane);
                protilehlaSouradnice.put(nazvyKlicu[3], yHledane);
                
                protilehlaSouradnice.put(vzdalenostKlic, vzdalenost);
                
                souradnicePosouzeni.add(protilehlaSouradnice);
                
            }
        }
        
        protilehlaSouradnice = vratNejblizsiSouradnici(souradnicePosouzeni, nazvyKlicu, smerHledani);
        
        return(protilehlaSouradnice);
        
    }
    
    
    private HashMap<String, Integer> vratNejblizsiSouradnici(ArrayList<HashMap<String, Integer>> souradnicePosouzeni, String[] nazvyKlicu, String smerHledani){
        
        HashMap<String, Integer> souradnice = null;
        HashMap<String, Integer> souradniceMin = new HashMap<String, Integer>();
        
        int hodnota_xA = -1;
        int hodnota_yA = -1;
        
        int hodnota_xB = -1;
        int hodnota_yB = -1;
        
        
        int hodnota_xA_min = 99999;
        int hodnota_yA_min = 99999;
        
        int hodnota_xB_min = 99999;
        int hodnota_yB_min = 99999;
        
        String vzdalenostKlic = null;
        int vzdalenost = -1;
        int vzdalenostMin = -1;
    
        if(smerHledani == "x"){
            vzdalenostKlic = "vzdalenost_x";
        }
        if(smerHledani == "y"){
            vzdalenostKlic = "vzdalenost_y";
        }
        
        
        for (int i = 0; i < souradnicePosouzeni.size(); i++) {
            
            souradnice = new HashMap<String, Integer>();
            souradnice = souradnicePosouzeni.get(i);
            
            hodnota_xA = souradnice.get(nazvyKlicu[0]);
            hodnota_yA = souradnice.get(nazvyKlicu[1]);
            hodnota_xB = souradnice.get(nazvyKlicu[2]);
            hodnota_yB = souradnice.get(nazvyKlicu[3]);
            vzdalenost = souradnice.get(vzdalenostKlic);
            
            if(i == 0){
                hodnota_xA_min = hodnota_xA;
                hodnota_yA_min = hodnota_yA;
                hodnota_xB_min = hodnota_xB;
                hodnota_yB_min = hodnota_yB;
                vzdalenostMin = vzdalenost;
            }
            else {
                if(hodnota_xA < hodnota_xA_min){hodnota_xA_min = hodnota_xA;}
                if(hodnota_yA < hodnota_yA_min){hodnota_yA_min = hodnota_yA;}
                if(hodnota_xB < hodnota_xB_min){hodnota_xB_min = hodnota_xB;}
                if(hodnota_yB < hodnota_yB_min){hodnota_yB_min = hodnota_yB;}
                if(vzdalenost < vzdalenostMin){vzdalenostMin = vzdalenost;}
            }
            
            System.out.print("");
            
        }
        
        souradniceMin.put(nazvyKlicu[0], hodnota_xA);
        souradniceMin.put(nazvyKlicu[1], hodnota_yA);
        souradniceMin.put(nazvyKlicu[2], hodnota_xB);        
        souradniceMin.put(nazvyKlicu[3], hodnota_yB);
        souradniceMin.put(vzdalenostKlic, vzdalenost);
        
        return(souradniceMin);
        
    }
    
    
    private HashMap<String, Double> vypoctyPlochy(){
        
        HashMap<String, Double> dataOPlose = new HashMap<String, Double>();
        
        double tloustkaX;
        double tloustkaY;
        double plocha;
        
        double delkaUhlopricky;
        double tangentaUhlopricky;
        
        tloustkaX = vratTloustkuMeziOkrajiCary(svisleOkraje, "x");
        tloustkaY = vratTloustkuMeziOkrajiCary(vodorovneOkraje, "y");
        plocha = tloustkaX * tloustkaY;
        tangentaUhlopricky = tloustkaY/tloustkaX;
        delkaUhlopricky = sqrt(tloustkaX * tloustkaX + tloustkaY * tloustkaY); 
        
        dataOPlose.put("tloustkaX" , tloustkaX);
        dataOPlose.put("tloustkaY" , tloustkaY);
        dataOPlose.put("plocha" , plocha);
        dataOPlose.put("tangentaUhlopricky" , tangentaUhlopricky);
        dataOPlose.put("delkaUhlopricky" , delkaUhlopricky);
        
        
        return(dataOPlose);  
        
    }
    
    
    
    private double vratTloustkuMeziOkrajiCary(ArrayList<HashMap<String, Integer>> vzdalenostiMeziOkraji, String smerHledani){
        
        HashMap<String, Integer> souradnice = null;
        String vzdalenostKlic;
        
        int vzdalenostDilci;
        int vzdalenostSuma;
        int pocet;
        double vzdalenostPrumer;      //  = tloustka
        
        vzdalenostKlic = "vzdalenost_" + smerHledani;
        vzdalenostSuma = 0;
        pocet = vzdalenostiMeziOkraji.size();
        
        for (int i = 0; i < vzdalenostiMeziOkraji.size(); i++) {
            souradnice = vzdalenostiMeziOkraji.get(i);
            
            vzdalenostDilci = souradnice.get(vzdalenostKlic);
            vzdalenostSuma = vzdalenostSuma + vzdalenostDilci; 
        }
        
        vzdalenostPrumer = vzdalenostSuma / pocet;
        
        return(vzdalenostPrumer);
        
    }
    
}
