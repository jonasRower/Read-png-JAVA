
package vektorycar;

import obrazek.sousediciSouradnice;
import ruzneBarvy.RuzneBarvySousedu;
import ruzneBarvy.RuzneBarvyCelehoPng;
import obrazek.PrebarviObrazek;
import obrazek.NactiPng;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
 

public class VektoryCar {

    public static void main(String[] args) throws IOException {
        
        ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> poleVsechBarev = new ArrayList<HashMap<String, Integer>>();
        
        ArrayList<HashMap<String, Integer>> sousediJinaBarva = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> sousediStejnaBarva = new ArrayList<HashMap<String, Integer>>();
        
        ArrayList<ArrayList<HashMap<String, Integer>>> sousediJinaBarvaPodleBarev = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<ArrayList<HashMap<String, Integer>>> sousediStejnaBarvaPodleBarev = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        
        
        NactiPng obrazek = new NactiPng();
        souradniceVsechBarev = obrazek.getSouradniceVsechBarev();
        
        RuzneBarvyCelehoPng barvy = new RuzneBarvyCelehoPng(souradniceVsechBarev);
        poleVsechBarev = barvy.getPoleVsechBarev();
        
        sousediciSouradnice sousedi = new sousediciSouradnice(souradniceVsechBarev);
        sousediJinaBarva = sousedi.getSousediJinaBarva();
        sousediStejnaBarva = sousedi.getSousediStejnaBarva();
        
        //CaraNeboPlocha plochaCara = new CaraNeboPlocha(sousediJinaBarva, sousediStejnaBarva);
        
        
        RuzneBarvySousedu barvySousedu = new RuzneBarvySousedu(poleVsechBarev, sousediStejnaBarva, sousediJinaBarva, souradniceVsechBarev);
        sousediJinaBarvaPodleBarev = barvySousedu.getSousediJinaBarvaPodleBarev();
        sousediStejnaBarvaPodleBarev = barvySousedu.getSousediStejnaBarvaPodleBarev();
        
        PrebarviObrazek prebarvuj = new PrebarviObrazek(barvySousedu, poleVsechBarev, souradniceVsechBarev);
        
        System.out.print("");
       
    }
}
