
package souradniceJednohoPismene;

import java.util.ArrayList;
import java.util.HashMap;
import nactiPng.NactiPng;


public class SouradniceJednohoPismene {
    
    ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
    ArrayList<HashMap<String, Integer>> souradnicePismeneRedukovana = new ArrayList<HashMap<String, Integer>>();
    
    
    public SouradniceJednohoPismene(ArrayList<HashMap<String, Integer>> souradniceVsechBarev){
        
        this.souradniceVsechBarev = souradniceVsechBarev;
          
    }
    
    public ArrayList<HashMap<String, Integer>> getSouradnicePismeneCernobile(int r, int g, int b){
        
        extraktujSouradniceUrciteBarvy(r, g, b);
        return(souradnicePismeneRedukovana);
        
    }
    
    public ArrayList<HashMap<String, Integer>> getSouradniceVztazeneKNule(){
        
        vztahniSouradniceKNule();
        return(souradnicePismeneRedukovana);
        
    }

    
    private void extraktujSouradniceUrciteBarvy(int r, int g, int b){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        HashMap<String, Integer> RGBnew = new HashMap<String, Integer>();
        ArrayList<HashMap<String, Integer>> souradnicePismene = new ArrayList<HashMap<String, Integer>>();
        
        int x;
        int y;
        
        int red;
        int green;
        int blue;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            RGB = souradniceVsechBarev.get(i);
            
            
            x = RGB.get("x");
            y = RGB.get("y");

            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");


            if(red == r){
                if(green == g){
                    if(blue == b){
                        RGBnew = zapisRGBDoMapy(x, y, red, green, blue);
                        souradnicePismene.add(RGBnew);
                    }
                }
            }
            
            
          
        }
        
        souradnicePismeneRedukovana = souradnicePismene;
        System.out.print("");
        
    } 
    
    //vztahne souradnice k nule
    private void vztahniSouradniceKNule(){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        HashMap<String, Integer> souradnicePismene_0_0 = new HashMap<String, Integer>();
        HashMap<String, Integer> oznaceniDat = new HashMap<String, Integer>();
        
        int xOrig;
        int yOrig;
        
        int xNew;
        int yNew;
        
        int xMin = vratMinimalniSouradnico("x");
        int yMin = vratMinimalniSouradnico("y");
        
        int red;
        int green;
        int blue;
        
        //0-ty radek souradnicePismeneRedukovana oznacuje o jaka data se jedna
        //oznaceniDat.put(nazevData, null);
        souradnicePismeneRedukovana.add(oznaceniDat);
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {  
            RGB = souradniceVsechBarev.get(i);
                     
            
            xOrig = RGB.get("x");
            yOrig = RGB.get("y");
            
            xNew = xOrig - xMin + 1;
            yNew = yOrig - yMin + 1;
            
            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");
            
            
            souradnicePismene_0_0 = zapisRGBDoMapy(xNew, yNew, red, green, blue);
            souradnicePismeneRedukovana.add(souradnicePismene_0_0);
               
        }
        
        
    }
    
    
    int vratMinimalniSouradnico(String XneboY){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        
        int sour;
        
        int sourMin = -1;
   
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            RGB = souradniceVsechBarev.get(i);
            sour = RGB.get(XneboY);
            
            if(i == 0){
                sourMin = sour;
            }
            else {
                if(sour < sourMin){
                    sourMin = sour;
                }
            }
        }
        
        return(sourMin);
    }
    
    
    private HashMap<String, Integer> zapisRGBDoMapy(int x, int y, int r, int g, int b){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>(); 
        
        RGB.put("x", x); 
        RGB.put("y", y); 
        
        RGB.put("Red", r); 
        RGB.put("Green", g); 
        RGB.put("Blue", b);
        
        return(RGB);
        
    }
    
}
