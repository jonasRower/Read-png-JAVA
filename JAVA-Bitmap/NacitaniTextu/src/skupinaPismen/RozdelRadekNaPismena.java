
// pokracovat zde RozdelRadkyNaPismena

package skupinaPismen;


import java.util.ArrayList;
import java.util.HashMap;


public class RozdelRadekNaPismena {
    
    //souradnice vsech barev (na danem radku)
    ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //obsahuje indexy prvnich a poslednich y-souradnic sloupcu
    ArrayList<HashMap<String, Integer>> XprvniAposledniPixelSloupcePole = new ArrayList<HashMap<String, Integer>>();
    
    //mapa RGB jednoho pismene mezi zvolenymi indexy (na jednom radku)
    ArrayList<HashMap<String, Integer>> mapaPismene;
    
    //mapa vsech pismen na danem radku
    ArrayList<ArrayList<HashMap<String, Integer>>> mapaVsechPismenNaRadku = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    
    
    RozdelRadekNaPismena(ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText){
        
        this.souradniceVsechBarevJednohoRadkuText = souradniceVsechBarevJednohoRadkuText;
        
        vratPrvniAPosledniPixelyPole(souradniceVsechBarevJednohoRadkuText);
        vytvorMapuPismen();

    }
    
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getMapaVsechPismenNaRadku(){
        
        return(mapaVsechPismenNaRadku);
        
    }
    
    
    private void vratPrvniAPosledniPixelyPole(ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText){
        
        int xMax;  
        
        //RGB jednoho pixeloveho sloupce
        ArrayList<HashMap<String, Integer>> hodnotySloupecPixel = new ArrayList<HashMap<String, Integer>>();
        
        xMax = vratMaximalniXSouradnici(souradniceVsechBarevJednohoRadkuText); 
        
        //detekuje zda pixelovy sloupec je jednobarevny
        boolean pixSloupecJeJednobarevny;
        boolean[] pixSloupecJeJednobarevnyPole = new boolean[xMax];
        
        //ArrayList pixelovych sloupcu
        ArrayList<Boolean> pixSloupceJednobarevne = new ArrayList<Boolean>(); 
        
        
        for (int sloupec = 0; sloupec < xMax; sloupec++) {
            hodnotySloupecPixel = vratSloupecPixelu(souradniceVsechBarevJednohoRadkuText, sloupec);
            pixSloupecJeJednobarevny = detekujZdaSeJednaOJednoBarevnyRadek(hodnotySloupecPixel);
            pixSloupecJeJednobarevnyPole[sloupec] = pixSloupecJeJednobarevny;    
        }
        
        
        XprvniAposledniPixelSloupcePole = vratXSourPrvnihoAPoslednihoPixeluRadku(pixSloupecJeJednobarevnyPole);
        System.out.print("");
        
    }
    
    private void vytvorMapuPismen(){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        int XZacatkuSloupce;
        int XKonecSloupce;
        
        
        for (int i = 0; i < XprvniAposledniPixelSloupcePole.size(); i++) {
            
            //inicializuje nove pole, aby nevkladal data do toho predchoziho
            mapaPismene = new ArrayList<HashMap<String, Integer>>();
            
            RGB = XprvniAposledniPixelSloupcePole.get(i);
        
            XZacatkuSloupce = RGB.get("XzacatkuSloupce");
            XKonecSloupce = RGB.get("XkonecSloupce");
        
            for (int x = XZacatkuSloupce; x <= XKonecSloupce; x++) {
                zapisRGBUrciteXSouradniceDoMapy(x); 
            }
            
            //prida pismeno do mapy vsech pismen (na jednom radku)
            mapaVsechPismenNaRadku.add(mapaPismene);
            
        }
        
        System.out.print("");
                    
    }
    
    private void zapisRGBUrciteXSouradniceDoMapy(int pozadovanaXSour){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        ArrayList<HashMap<String, Integer>> dataPixelovehoRadku = new ArrayList<HashMap<String, Integer>>();
        
        int x;
        
        for (int i1 = 0; i1 < souradniceVsechBarevJednohoRadkuText.size(); i1++) {
            dataPixelovehoRadku = souradniceVsechBarevJednohoRadkuText.get(i1);
        
            for (int i = 0; i < dataPixelovehoRadku.size(); i++) {
                RGB = dataPixelovehoRadku.get(i);

                x = RGB.get("x");

                //nalezne-li pozadovanou souradnici y, pak prida do RGBJednohoRadku
                if (x == pozadovanaXSour){
                    mapaPismene.add(RGB);
                }
            }
        }
        
    }
    
    //detekujZdaSeJednaOJednoBarevnyRadek
    
    private ArrayList<HashMap<String, Integer>> vratSloupecPixelu(ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText, int sloupec){
        
        ArrayList<HashMap<String, Integer>> hodnotyRadkuText;
        ArrayList<HashMap<String, Integer>> hodnotySloupecPixel = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> hodnotyPixelu;
        
        for (int i = 0; i < souradniceVsechBarevJednohoRadkuText.size(); i++) {
            hodnotyRadkuText = souradniceVsechBarevJednohoRadkuText.get(i);
            hodnotyPixelu = hodnotyRadkuText.get(sloupec); 
            hodnotySloupecPixel.add(hodnotyPixelu);
        }
        
        //System.out.print("");
        
        return(hodnotySloupecPixel);
        
    }
    
    
    private int vratMaximalniXSouradnici(ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText){
        
        int xMax;    
        
        //obsahuje vsechny sloupce jednoho pixeloveho radku
        ArrayList<HashMap<String, Integer>> vsechnySloupceJednohoRadkuPixel;
        vsechnySloupceJednohoRadkuPixel = souradniceVsechBarevJednohoRadkuText.get(0);
        
        xMax = vsechnySloupceJednohoRadkuPixel.size();
        
        return(xMax);
        
    }
    
    //-------------------------------------------------------------------------------------------------------
    //upravit kod: toto je okopirovane z VytvorRadkyPng
    public boolean detekujZdaSeJednaOJednoBarevnyRadek(ArrayList<HashMap<String, Integer>> RGBJednohoRadku){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        
        int red;
        int green;
        int blue;
        
        int red_predchozi = -1;
        int green_predchozi = -1;
        int blue_predchozi = -1;
        
        boolean jednobarevnyRadek = true;
        
        
        for (int i = 0; i < RGBJednohoRadku.size(); i++) {
            RGB = RGBJednohoRadku.get(i);
            
            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");
            
            
            if (i > 0){
                if (red != red_predchozi){
                    jednobarevnyRadek = false;
                    break;
                }
                
                if (green != green_predchozi){
                    jednobarevnyRadek = false;
                    break;
                }
                
                if (blue != blue_predchozi){
                    jednobarevnyRadek = false;
                    break;
                }
                
            }
            
            red_predchozi = red;
            green_predchozi = green;   
            blue_predchozi = blue;
            
        }
        
        return (jednobarevnyRadek);
        
    }
    //-------------------------------------------------------------------------------------------------------
    
  
    private ArrayList<HashMap<String, Integer>> vratXSourPrvnihoAPoslednihoPixeluRadku(boolean[] jednoBarevneRadkyPole){
        
        HashMap<String, Integer> XprvniAposledniPixelSloupce = null;
        ArrayList<HashMap<String, Integer>> XprvniAposledniPixelSloupcePole = new ArrayList<HashMap<String, Integer>>();
        
        boolean jednoBarevnyRadek;
        boolean jednoBarevnyRadekPredchozi = true;
        
        //y-ova souradnice zacatku a konce radku
        int XzacatkuSloupce;
        int XkonecSloupce;
      
        
        for (int x = 0; x < jednoBarevneRadkyPole.length; x++) {
            jednoBarevnyRadek = jednoBarevneRadkyPole[x];
            
            //ziska x-ovou souradnici zacatku sloupce (jedna se o pixelovy sloupec)
            if(jednoBarevnyRadekPredchozi == true){
                if(jednoBarevnyRadek == false){
                    XzacatkuSloupce = x;
                    XprvniAposledniPixelSloupce = new HashMap<String, Integer>();
                    XprvniAposledniPixelSloupce.put("XzacatkuSloupce", x);
                }
            }
            
            //ziska x-ovou souradnici konce sloupce (jedna se o pixelovy sloupec)
            if(jednoBarevnyRadekPredchozi == false){
                if(jednoBarevnyRadek == true){
                    XkonecSloupce = x;
                    XprvniAposledniPixelSloupce.put("XkonecSloupce", x);
                    
                    //prida zacatek a konec pix. do pole
                    XprvniAposledniPixelSloupcePole.add(XprvniAposledniPixelSloupce);
                }
            }
            
            
            jednoBarevnyRadekPredchozi = jednoBarevnyRadek;
            
        }
        
        return(XprvniAposledniPixelSloupcePole);
        
       
    }
    
}
