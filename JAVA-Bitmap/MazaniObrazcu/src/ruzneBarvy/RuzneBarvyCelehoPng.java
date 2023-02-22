
package ruzneBarvy;

import java.util.ArrayList;
import java.util.HashMap;


public class RuzneBarvyCelehoPng {
    
    private ArrayList<HashMap<String, Integer>> souradniceVsechBarev;
    public ArrayList<HashMap<String, Integer>> poleVsechBarev = new ArrayList<HashMap<String, Integer>>();
    
    
    public RuzneBarvyCelehoPng(ArrayList<HashMap<String, Integer>> souradniceVsechBarev){
    
        this.souradniceVsechBarev = souradniceVsechBarev;
        vratPocetBarev();
    
    }
    
    
    public ArrayList<HashMap<String, Integer>> getPoleVsechBarev(){
        
        return (poleVsechBarev);
        
    }
    
    
    //vrati pocet barev jinych nez 0
    //zapise do pole, obsahujici index nalezene barvy a RGB
  
    private void vratPocetBarev(){
        
        //HashMap<String, Integer> barvy = new HashMap<String, Integer>
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        
        int red;
        int green;
        int blue;
        boolean barvaJeJizObsazena;
        boolean prvniBarva = true;
        int poradiBarvy;
        
        poradiBarvy = 0;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            
            RGB = souradniceVsechBarev.get(i);
            
            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");
            
            //jedna-li se o jinou barvu nez bilou
            if(red + green + blue != 255 + 255 + 255){
                
                if(prvniBarva == true){
                    barvaJeJizObsazena = false;
                    prvniBarva = false;
                }
                else {
                    barvaJeJizObsazena = detekujZdaJeBarvaObsazena(red, green, blue);
                }
            
                if(barvaJeJizObsazena == false){
                    poradiBarvy = poradiBarvy + 1;
                    pridejBarvuDoPole(red, green, blue, poradiBarvy);
                }
            }
            
        }
        System.out.print("");
    }
    
    
    private void pridejBarvuDoPole(int red, int green, int blue, int poradi){
        
        HashMap<String, Integer> barvaPole = new HashMap<String, Integer>();
        
        barvaPole.put("poradi", poradi);
        barvaPole.put("Red", red);
        barvaPole.put("Green", green);
        barvaPole.put("Blue", blue);
        
        poleVsechBarev.add(barvaPole);
        
    }
    
    
    private boolean detekujZdaJeBarvaObsazena(int r, int g, int b){
        
        HashMap<String, Integer> RGB;
        boolean barvaJeJizObsazena = false;
        int red;
        int green;
        int blue;
        
        for (int i = 0; i < poleVsechBarev.size(); i++) {
            RGB = new HashMap<String, Integer>();
            RGB = poleVsechBarev.get(i);
            
            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");
            
            if(red == r){
                if(green == g){
                    if(blue == b){
                        barvaJeJizObsazena = true;
                    }
                }
            }
        }
        
        return(barvaJeJizObsazena);
        
    }
}