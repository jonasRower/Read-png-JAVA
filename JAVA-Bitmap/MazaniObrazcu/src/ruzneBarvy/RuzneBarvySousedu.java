
package ruzneBarvy;

import okraje.OkrajVsechPloch;
import okraje.OkrajJednePlochy;
import java.util.ArrayList;
import java.util.HashMap;


public class RuzneBarvySousedu {
    
    private ArrayList<HashMap<String, Integer>> sousediJinaBarva = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousediStejnaBarva = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> poleVsechBarev = new ArrayList<HashMap<String, Integer>>();

    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarev = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediStejnaBarvaPodleBarev = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> svisleOkrajeVsechnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> vodorovneOkrajeVsechnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    
    private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> hraniceNaVychodVsecnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> hraniceNaJihVsecnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> hraniceNaZapadVsecnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> hraniceNaSeverVsecnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    
    private ArrayList<ArrayList<HashMap<String, Double>>> dataVsechnyPlochyVsechnyBarvy = new ArrayList<ArrayList<HashMap<String, Double>>>();
    private ArrayList<ArrayList<Boolean>> statusVsechnyPlochyVsechnyBarvy = new ArrayList<ArrayList<Boolean>>();
    
    /*
    //asi umistit do samostatne tridy, kde by byly orientace vsech sousedu na jednotlive strany roztridene podle barev
    private ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvyVychod = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvyJih = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvyZapad = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvySever = new ArrayList<HashMap<String, Integer>>();
    
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaVsechBarevVychod = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaVsechBarevJih = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaVsechBarevZapad = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaVsechBarevSever = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    */
    
    //obsahuje data rozdelene podle orientace a barev pouze pro plochy (nerozlisuje k jake plose, jen to ze to nepatri k care)
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevVychodPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevJihPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevZapadPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarevSeverPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    
    
    
    public RuzneBarvySousedu(ArrayList<HashMap<String, Integer>> poleVsechBarev, ArrayList<HashMap<String, Integer>> sousediStejnaBarva, ArrayList<HashMap<String, Integer>> sousediJinaBarva, ArrayList<HashMap<String, Integer>> souradniceVsechBarev){
        
        this.poleVsechBarev = poleVsechBarev;
        this.sousediJinaBarva = sousediJinaBarva;
        this.sousediStejnaBarva = sousediStejnaBarva;
        
        
        //doplni parove pixely
        //this.sousediJinaBarva = doplnParovePixely(this.sousediJinaBarva);
        
        //zatim provizorne je to tady
        RuzneBarvyPodleOrientace roztridOkrajePodleBarev = new RuzneBarvyPodleOrientace(sousediJinaBarva, sousediStejnaBarva, poleVsechBarev, souradniceVsechBarev);
        
        sousediJinaBarvaPodleBarevVychodPlochy = roztridOkrajePodleBarev.getSousediJinaBarvaPodleBarevVychodPlochy();
        sousediJinaBarvaPodleBarevJihPlochy = roztridOkrajePodleBarev.getSousediJinaBarvaPodleBarevJihPlochy();
        sousediJinaBarvaPodleBarevZapadPlochy = roztridOkrajePodleBarev.getSousediJinaBarvaPodleBarevZapadPlochy();
        sousediJinaBarvaPodleBarevSeverPlochy = roztridOkrajePodleBarev.getSousediJinaBarvaPodleBarevSeverPlochy();
        
        roztridSousediPodleBarvy();
        
       
        
        //roztridSousediPodleBarvy();
        
        System.out.print("");
        
    }

   
    
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousediJinaBarvaPodleBarev(){
        return(sousediJinaBarvaPodleBarev);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousediStejnaBarvaPodleBarev(){
        return(sousediStejnaBarvaPodleBarev); 
    }
    
    public ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> getSvisleOkrajeVsechnyBarvy(){
        return(svisleOkrajeVsechnyPlochyVsechnyBarvy);
    }
    
    public ArrayList<ArrayList<Boolean>> getStatusy(){
        return(statusVsechnyPlochyVsechnyBarvy);
    }
    
    
    //pripadne doplni pixely, ktere chybeji a jsou rozhranimi opacne barvy z druhe strany
    //tj. atributy Hlavni a soused jsou prohozeny a je otocena orientace
    private ArrayList<HashMap<String, Integer>> doplnParovePixely(ArrayList<HashMap<String, Integer>> SousediJinaBarva){
        
        HashMap<String, Integer> RGB;
        HashMap<String, Integer> RGBparovy;
        boolean obsahujiSousediJinaBarvaJizDanyPixel;
        int pocetPrvku;
        
        pocetPrvku = SousediJinaBarva.size();
        
        
        for (int i = 0; i < pocetPrvku; i++) {
            RGB = new HashMap<String, Integer>();
            RGB = SousediJinaBarva.get(i);
            RGBparovy = vratParovyPixel(RGB);
            
            obsahujiSousediJinaBarvaJizDanyPixel = SousediJinaBarva.contains(RGBparovy);
            
            
            if(obsahujiSousediJinaBarvaJizDanyPixel == false){
                SousediJinaBarva.add(RGBparovy);
            }
            
        }
        
        return(SousediJinaBarva);
        
    }
    
    private HashMap<String, Integer> vratParovyPixel(HashMap<String, Integer> RGBOrig){
            
        HashMap<String, Integer> RGBNew = new HashMap<String, Integer>();
        
        //originalni data
        int y_Hlavni_Orig;
        int x_Hlavni_Orig;
        int r_Hlavni_Orig;
        int g_Hlavni_Orig;
        int b_Hlavni_Orig;
        
        int y_Soused_Orig;
        int x_Soused_Orig;
        int r_Soused_Orig;
        int g_Soused_Orig;
        int b_Soused_Orig;
        
        int orientace_souseda_Orig;
        
        
        //nova data
        int y_Hlavni_New;
        int x_Hlavni_New;
        int r_Hlavni_New;
        int g_Hlavni_New;
        int b_Hlavni_New;
        
        int y_Soused_New;
        int x_Soused_New;
        int r_Soused_New;
        int g_Soused_New;
        int b_Soused_New;
        
        int orientace_souseda_New;
        
        //ziskani dat
        y_Hlavni_Orig = RGBOrig.get("y_Hlavni");
        x_Hlavni_Orig = RGBOrig.get("x_Hlavni");
        r_Hlavni_Orig = RGBOrig.get("r_Hlavni");
        g_Hlavni_Orig = RGBOrig.get("g_Hlavni");
        b_Hlavni_Orig = RGBOrig.get("b_Hlavni");
        
        y_Soused_Orig = RGBOrig.get("y_Soused");
        x_Soused_Orig = RGBOrig.get("x_Soused");
        r_Soused_Orig = RGBOrig.get("r_Soused");
        g_Soused_Orig = RGBOrig.get("g_Soused");
        b_Soused_Orig = RGBOrig.get("b_Soused");
        
        orientace_souseda_Orig = RGBOrig.get("orientace souseda");
        
        
        //souradnice_Hlavni se vzdy prohodi se sousedem
        y_Hlavni_New = y_Soused_Orig;
        x_Hlavni_New = x_Soused_Orig;
        
        y_Soused_New = y_Hlavni_Orig;
        x_Soused_New = x_Hlavni_Orig;
                
        //rgb_Hlavni se vzdy prohodi se sousedem
        r_Hlavni_New = r_Soused_Orig;
        g_Hlavni_New = g_Soused_Orig;
        b_Hlavni_New = b_Soused_Orig;
        
        r_Soused_New = r_Hlavni_Orig;
        g_Soused_New = g_Hlavni_Orig;
        b_Soused_New = b_Hlavni_Orig;
        
        if(orientace_souseda_Orig == 1){
            orientace_souseda_New = 3;
        }
        if(orientace_souseda_Orig == 3){
            orientace_souseda_New = 1;
        }
        if(orientace_souseda_Orig == 4){
            orientace_souseda_New = 2;
        }
        if(orientace_souseda_Orig == 2){
            orientace_souseda_New = 4;
        }

        RGBNew.put("y_Hlavni", y_Hlavni_New);
        RGBNew.put("x_Hlavni", x_Hlavni_New);
        RGBNew.put("r_Hlavni", r_Hlavni_New);
        RGBNew.put("g_Hlavni", g_Hlavni_New);
        RGBNew.put("b_Hlavni", b_Hlavni_New);
        
        RGBNew.put("y_Soused", y_Soused_New);
        RGBNew.put("x_Soused", x_Soused_New);
        RGBNew.put("r_Soused", r_Soused_New);
        RGBNew.put("g_Soused", g_Soused_New);
        RGBNew.put("b_Soused", b_Soused_New);
        
        RGBNew.put("orientace souseda", b_Soused_New);
        
        return(RGBNew);
        
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
            ziskejDataJednotlivychPloch(sousediJinaBarvaDaneBarvy, i);
          
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
    
    /*
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
        
        //ulozi data do dat neroztridenych, data originalni jsou pro testovani
        sousedVychodNeroztrizene = sousedVychod;
        sousedJihNeroztrizene = sousedJih;
        sousedZapadNeroztrizene = sousedZapad;
        sousedSeverNeroztrizene = sousedSever;
                
                
        System.out.print("");
    }
    */
    
    
    private void ziskejDataJednotlivychPloch(ArrayList<HashMap<String, Integer>> sousediJinaBarvaDaneBarvy, int index){
        
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevVychod = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevJih = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevSever = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> sousediJinaBarvaPodleBarevZapad = new ArrayList<HashMap<String, Integer>>();
        
        sousediJinaBarvaPodleBarevVychod = sousediJinaBarvaPodleBarevVychodPlochy.get(index);
        sousediJinaBarvaPodleBarevJih = sousediJinaBarvaPodleBarevJihPlochy.get(index);
        sousediJinaBarvaPodleBarevZapad = sousediJinaBarvaPodleBarevZapadPlochy.get(index);  
        sousediJinaBarvaPodleBarevSever = sousediJinaBarvaPodleBarevSeverPlochy.get(index);
        
        

        OkrajVsechPloch okraje = null;
        
        //Puvodni konstruktor, nez se udelalo rozpoznavani mezi plochou a carou
        //okraje = new OkrajVsechPloch(sousediJinaBarvaDaneBarvy);

        //novy konstruktor ktery posila data ktera neobsahuji okraje car
        okraje = new OkrajVsechPloch(sousediJinaBarvaPodleBarevVychod, sousediJinaBarvaPodleBarevJih, sousediJinaBarvaPodleBarevZapad, sousediJinaBarvaPodleBarevSever);
        
        
        //obsahuje data na hranicich plochy, pricemz index oznacuje poradi plochy
        ArrayList<ArrayList<HashMap<String, Integer>>> hraniceNaVychodVsecnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<ArrayList<HashMap<String, Integer>>> hraniceNaJihVsecnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<ArrayList<HashMap<String, Integer>>> hraniceNaZapadVsecnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<ArrayList<HashMap<String, Integer>>> hraniceNaSeverVsecnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        
        //obsahuje data na hranici jedne plochy
        ArrayList<HashMap<String, Integer>> hraniceNaVychodJednaPlocha;
        ArrayList<HashMap<String, Integer>> hraniceNaJihJednaPlocha;
        ArrayList<HashMap<String, Integer>> hraniceNaZapadJednaPlocha;
        ArrayList<HashMap<String, Integer>> hraniceNaSeverJednaPlocha;
        
        //ziska data ze vsech ploch
        hraniceNaVychodVsecnyPlochy = okraje.getSousedyVychodVsechnyPlochy();
        hraniceNaJihVsecnyPlochy = okraje.getSousedyJihVsechnyPlochy();
        hraniceNaZapadVsecnyPlochy = okraje.getSousedyZapadVsechnyPlochy();
        hraniceNaSeverVsecnyPlochy = okraje.getSousedySeverVsechnyPlochy();
        
        //obsahuje data o plose
        HashMap<String, Double> dataOPlose;
        ArrayList<HashMap<String, Double>> dataVsechnyPlochy = new ArrayList<HashMap<String, Double>>();
        
        //zalozi tridu
        OkrajJednePlochy jednaPlocha;
        
        //data mezi okraji pro jednotlive plochy
        ArrayList<HashMap<String, Integer>> svisleOkrajeJednaPlocha;
        ArrayList<HashMap<String, Integer>> vodorovneOkrajeJednaPlocha;
        
        //data mezi okraji pro vsechny plochy (jedne barvy)
        ArrayList<ArrayList<HashMap<String, Integer>>> svisleOkrajeVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<ArrayList<HashMap<String, Integer>>> vodorovneOkrajeVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        
        //status na zaklade cehoz se budou prebarvovat pixely
        ArrayList<Boolean> statusVsechnyPlochy = new ArrayList<Boolean>();
        
        boolean status;
        
        
        //pro jednotlive plochy vraci udaje o plose
        for (int i = 0; i < hraniceNaVychodVsecnyPlochy.size(); i++) {
            
            hraniceNaVychodJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            hraniceNaJihJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            hraniceNaZapadJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            hraniceNaSeverJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            
            svisleOkrajeJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            vodorovneOkrajeJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            
            
            //ziska data z jedne plochy dane indexem
            hraniceNaVychodJednaPlocha = hraniceNaVychodVsecnyPlochy.get(i);
            hraniceNaJihJednaPlocha = hraniceNaJihVsecnyPlochy.get(i);
            hraniceNaZapadJednaPlocha = hraniceNaZapadVsecnyPlochy.get(i);
            hraniceNaSeverJednaPlocha = hraniceNaSeverVsecnyPlochy.get(i);
            
            //inicializuje tridu
            jednaPlocha = new OkrajJednePlochy(hraniceNaVychodJednaPlocha, hraniceNaJihJednaPlocha, hraniceNaZapadJednaPlocha, hraniceNaSeverJednaPlocha);
            
            //ziska data o plose
            dataOPlose = jednaPlocha.vratDataOPlose();
            
            //zapise data o plose
            dataVsechnyPlochy.add(dataOPlose);
            
            //ziska data mezi okraji plochy
            svisleOkrajeJednaPlocha = jednaPlocha.getSvisleOkraje();
            vodorovneOkrajeJednaPlocha = jednaPlocha.getVodorovneOkraje();
            
            //zapise data na vsechny plochy
            svisleOkrajeVsechnyPlochy.add(svisleOkrajeJednaPlocha);
            vodorovneOkrajeVsechnyPlochy.add(vodorovneOkrajeJednaPlocha);
            
            //nastavi status
            //status = nastavStatus(dataOPlose);
            
            //zapise status
            //statusVsechnyPlochy.add(status); 
            statusVsechnyPlochy.add(true);
           
            System.out.print("");
            
        }
        
        //zapisou se postupne vsechny barvy sem
        hraniceNaVychodVsecnyPlochyVsechnyBarvy.add(hraniceNaVychodVsecnyPlochy);
        hraniceNaJihVsecnyPlochyVsechnyBarvy.add(hraniceNaJihVsecnyPlochy); 
        hraniceNaZapadVsecnyPlochyVsechnyBarvy.add(hraniceNaZapadVsecnyPlochy); 
        hraniceNaSeverVsecnyPlochyVsechnyBarvy.add(hraniceNaSeverVsecnyPlochy);
        
        svisleOkrajeVsechnyPlochyVsechnyBarvy.add(svisleOkrajeVsechnyPlochy);
        vodorovneOkrajeVsechnyPlochyVsechnyBarvy.add(vodorovneOkrajeVsechnyPlochy);
        
        dataVsechnyPlochyVsechnyBarvy.add(dataVsechnyPlochy);
        statusVsechnyPlochyVsechnyBarvy.add(statusVsechnyPlochy);
        
        System.out.print("");
        
    }
    
    private boolean nastavStatus(HashMap<String, Double> dataOPlose){
        
        //limit
        double tlouskaYLimit;
        double tlouskaXLimit;
        double tangentaUhloprickyLimit;
        double delkaUhloprickyLimit;
        double plochaLimit;
        
        //data na plose
        double tlouskaY;
        double tlouskaX;
        double tangentaUhlopricky;
        double delkaUhlopricky;
        double plocha;
        
        boolean status = true;
        
        //nastavene limity
        tlouskaYLimit = 15;
        tlouskaXLimit = -1;
        tangentaUhloprickyLimit = -1;
        delkaUhloprickyLimit = -1;
        plochaLimit = -1;
        
        //hodnoty z plochy
        tlouskaY = dataOPlose.get("tloustkaY");
        tlouskaX = dataOPlose.get("tloustkaX");
        tangentaUhlopricky = dataOPlose.get("tangentaUhlopricky");
        delkaUhlopricky = dataOPlose.get("delkaUhlopricky");
        plocha = dataOPlose.get("plocha");
        
        //vyhodnoti status
        status = vyhodnotStatus(tlouskaY, tlouskaYLimit, status);
        status = vyhodnotStatus(tlouskaX, tlouskaXLimit, status);
        status = vyhodnotStatus(tangentaUhlopricky, tangentaUhloprickyLimit, status);
        status = vyhodnotStatus(delkaUhlopricky, delkaUhloprickyLimit, status);
        status = vyhodnotStatus(plocha, plochaLimit, status);
        
        
        return(status);
        
    }
    
    boolean vyhodnotStatus(double hodnota, double limit, boolean statusPredchozi){
        
        boolean status = true;
        
        if(statusPredchozi == false){
            status = false;
        }
        else {
            if(limit > -1){
                if(limit > hodnota){
                    status = false;
                }
            }
        }
        
        return(status);
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

}
