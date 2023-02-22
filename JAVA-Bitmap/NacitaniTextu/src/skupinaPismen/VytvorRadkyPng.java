
package skupinaPismen;

//tato trida bude uchovavat vytvorene png jakozto radky z puvodniho png

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import nactiPng.NactiPng;


public class VytvorRadkyPng {
   
    //souradnice a RGB vsech pixelu obrazku
    private ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
    
    //y-ova souradnice udavajici horni a dolni okraj radku
    private ArrayList<HashMap<String, Integer>> YprvniAposledniPixelRadkuPole = new ArrayList<HashMap<String, Integer>>();
    
    //vyska obrazku
    int yMax;
    
    //obsahuje data vsech radku s textem
    ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> souradniceVsechBarevVsechRadkuText = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
       
    

    
    VytvorRadkyPng(ArrayList<HashMap<String, Integer>> souradniceVsechBarev) throws IOException{
        
        this.souradniceVsechBarev = souradniceVsechBarev; 
                  
        vratYSourPrvnihoAPoslednihoPixeluRadku();
        vytvorSouradniceVsechBarevPoRadcich();
        
        
        System.out.print("");
        
    }
    
    
    
    public ArrayList<HashMap<String, Integer>> getYprvniAposledniPixelRadkuPole(){
        
        return YprvniAposledniPixelRadkuPole;
        
    }
    
    public ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> getSouradniceVsechBarevVsechRadkuText(){
        
        return (souradniceVsechBarevVsechRadkuText);
        
    } 
    
    public ArrayList<HashMap<String, Integer>> getSouradniceVsechBarev(){
        
        return (souradniceVsechBarev);
        
    }
    
    
    
    //vrati pole s jednotlivimi radky
    private void vytvorSouradniceVsechBarevPoRadcich(){
        
        boolean yJeNaRadku;
        boolean yJeNaRadkuPredchozi = false;
        
        //obsahuje data celeho jednoho pixeloveho radku
        ArrayList<HashMap<String, Integer>> RGBJednohoPixRadku = new ArrayList<HashMap<String, Integer>>();
        
        //obsahuje data celeho jednoho radku s textem (nekolik RGBJednohoRadku - ktere jsou pod sebou)
        ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText = null;
        
        
        for (int y = 0; y < yMax; y++) {
            
            yJeNaRadku = detekujZdaRadekJeMeziPrvnimAPoslednimPixelemNaRadku(y);
            
            if(yJeNaRadku == true){
                if(yJeNaRadkuPredchozi == false){
                    souradniceVsechBarevJednohoRadkuText = new ArrayList<ArrayList<HashMap<String, Integer>>>();
                }
                
                RGBJednohoPixRadku = vratRGBUrciteYSouradnice(y);
                souradniceVsechBarevJednohoRadkuText.add(RGBJednohoPixRadku);
            }
            
            if(yJeNaRadku == false){
                if(yJeNaRadkuPredchozi == true){
                    souradniceVsechBarevVsechRadkuText.add(souradniceVsechBarevJednohoRadkuText);
                }
            }
            
            yJeNaRadkuPredchozi = yJeNaRadku;
            
        }
        
        
        
    }
    
    
    private boolean detekujZdaRadekJeMeziPrvnimAPoslednimPixelemNaRadku(int y){
        
        HashMap<String, Integer> YprvniAposledniPixelRadku = new HashMap<String, Integer>();
        int YzacatkuRadku;
        int YkonecRadku;
        
        boolean yJeNaRadku = false;
        
        for (int i = 0; i < YprvniAposledniPixelRadkuPole.size(); i++) {
            YprvniAposledniPixelRadku = YprvniAposledniPixelRadkuPole.get(i);
            YzacatkuRadku = YprvniAposledniPixelRadku.get("YzacatkuRadku");
            YkonecRadku = YprvniAposledniPixelRadku.get("YkonecRadku");
            
            if(y > YzacatkuRadku){
                if(y < YkonecRadku){
                    yJeNaRadku = true;
                    break;
                }
            }
        }
        
        return(yJeNaRadku);
        
    }
    
   
    
    private void vratYSourPrvnihoAPoslednihoPixeluRadku(){
        
        HashMap<String, Integer> YprvniAposledniPixelRadku = null;
        
        boolean jednoBarevnyRadek;
        boolean jednoBarevnyRadekPredchozi = true;
        
        //y-ova souradnice zacatku a konce radku
        int YzacatkuRadku;
        int YkonecRadku;
        
        boolean[] jednoBarevneRadkyPole;
        
        yMax = vratMaximalniYSouradnici();
        jednoBarevneRadkyPole = vratPoleVsechJednobarevnychRadku(yMax);
        
        
        for (int y = 0; y < jednoBarevneRadkyPole.length; y++) {
            jednoBarevnyRadek = jednoBarevneRadkyPole[y];
            
            //ziska y-ovou souradnici zacatku radku (jedna se o pixelovy radek)
            if(jednoBarevnyRadekPredchozi == true){
                if(jednoBarevnyRadek == false){
                    YzacatkuRadku = y;
                    YprvniAposledniPixelRadku = new HashMap<String, Integer>();
                    YprvniAposledniPixelRadku.put("YzacatkuRadku", y);
                }
            }
            
            //ziska y-ovou souradnici konce radku (jedna se o pixelovy radek)
            if(jednoBarevnyRadekPredchozi == false){
                if(jednoBarevnyRadek == true){
                    YkonecRadku = y;
                    YprvniAposledniPixelRadku.put("YkonecRadku", y);
                    
                    //prida zacatek a konec pix. do pole
                    YprvniAposledniPixelRadkuPole.add(YprvniAposledniPixelRadku);
                }
            }
            
            
            jednoBarevnyRadekPredchozi = jednoBarevnyRadek;
            
        }
        
        System.out.print("");
        
    }
    
   
    //kdyz je radek jednobarevny, pak neobsahuje text - jedna se o pixelovy radek
    private boolean[] vratPoleVsechJednobarevnychRadku(int yMax){
        
       ArrayList<HashMap<String, Integer>> RGBJednohoRadku = new ArrayList<HashMap<String, Integer>>();
       boolean jednoBarevnyRadek;
       boolean[] jednoBarevneRadkyPole = new boolean[yMax];
       
       for (int y = 0; y < yMax; y++) {
           RGBJednohoRadku = vratRGBUrciteYSouradnice(y);
           jednoBarevnyRadek = detekujZdaSeJednaOJednoBarevnyRadek(RGBJednohoRadku); 
           jednoBarevneRadkyPole[y] = jednoBarevnyRadek;
       }
       
       return(jednoBarevneRadkyPole);
        
    }
    
    
    //vraci pole udavajici y-souradnici zacatku a konce radku
    //tzn. vsechny y-ove pixely mezi temito pixely jsou radky ostatni nejsou
    private ArrayList<HashMap<String, Integer>> vratRGBUrciteYSouradnice(int pozadovanaYSour){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        ArrayList<HashMap<String, Integer>> RGBJednohoRadku = new ArrayList<HashMap<String, Integer>>();
        
        int y;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            RGB = souradniceVsechBarev.get(i);
            
            y = RGB.get("y");
            
            //nalezne-li pozadovanou souradnici y, pak prida do RGBJednohoRadku
            if (y == pozadovanaYSour){
                
                RGBJednohoRadku.add(RGB);
                
            }
        }
        
        return(RGBJednohoRadku);
    }
    

    //pokud je prazdny radek, pak je jednobarevny
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
    
    
    private int vratMaximalniYSouradnici(){
        
        int y;
        int yMax;
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        
        yMax = 0;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            
            RGB = souradniceVsechBarev.get(i);
            y = RGB.get("y");
            
            if(y > yMax){
                yMax = y;
            }
            
        }
        
        return(yMax);
        
    }
    
    
}
