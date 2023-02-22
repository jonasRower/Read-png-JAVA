
package souradniceAbecedy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class SouradnicePismene {
    
    //souradnice a RGB vsech pixelu obrazku
    private ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
    
    //souradnice pismen ktere jsou pouze dane RGB (cerne - 0:0:0)
    private ArrayList<HashMap<String, Integer>> souradnicePismena = new ArrayList<HashMap<String, Integer>>();
    
    //souradnice pismen vztazene k pocatku
    private ArrayList<HashMap<String, Integer>> souradnicePismena_0_0 = new ArrayList<HashMap<String, Integer>>();
    
    private String CestaKpng;
    private String nazevData;
    
    
    SouradnicePismene(String CestaKpng, String nazevPng) throws IOException{
        
        String[] arrNazevData = nazevPng.split("\\.");
        
        this.CestaKpng = CestaKpng + nazevPng;
        this.nazevData = arrNazevData[0];
        
        souborSPismenem();
        ExtraktujSouradnicePouzePismena();
        posunSouradnicePismenaDo_0_0();
        
    }
    
    
    public ArrayList<HashMap<String, Integer>> getSouradnicePismena_0_0(){
        return(souradnicePismena_0_0);
    }
    
    //nepouziva se
    public ArrayList<HashMap<String, Integer>> getsouradnicePismena(){
        return(souradnicePismena);
    }
     
    //naplni souradniceVsechBarev
    private void souborSPismenem() throws IOException{
        
        //File file= new File("C:\\Users\\jonas\\OneDrive\\Dokumenty\\JAVA_KCT\\TextReader\\TextReader\\obrazek.png");
        File file= new File(CestaKpng);
        BufferedImage img = ImageIO.read(file);
        
        //ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
        souradniceVsechBarev = vratSouradniceVsechBarev(img);
        
    }
    
    
    private ArrayList<HashMap<String, Integer>> vratSouradniceVsechBarev(BufferedImage img){
        
        ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> RGBPixeliu;
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                RGBPixeliu = vratRGBPixelu(x, y, img);
                souradniceVsechBarev.add(RGBPixeliu);
            }
        }
        
        return (souradniceVsechBarev);
    }
    
    
    private HashMap<String, Integer> vratRGBPixelu(int sourPixelX, int sourPixelY, BufferedImage img){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>(); 
        
        int red;
        int green;
        int blue;
        
        int pixel = img.getRGB(sourPixelX,sourPixelY);
        Color color = new Color(pixel, true);
        
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        
        RGB = zapisRGBDoMapy(sourPixelX, sourPixelY, red, green, blue);
        
        return(RGB);
        
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
    
    
    //naplni pole souradnicePismena
    void ExtraktujSouradnicePouzePismena(){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        HashMap<String, Integer> souradnicePismene = new HashMap<String, Integer>();
        
        
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
            
            
            if(red == 0){
                if(green == 0){
                    if(blue == 0){
                        souradnicePismene = zapisRGBDoMapy(x, y, red, green, blue);
                        souradnicePismena.add(souradnicePismene);
                    }
                }
            }
        }
    }
    
    
    
    
    //existujici souradnicePismena oreze, tak ze umisti prvni radek a sloupec pod souradnici 0, 0
    void posunSouradnicePismenaDo_0_0(){
        
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
        
        //0-ty radek souradnicePismena_0_0 oznacuje o jaka data se jedna
        oznaceniDat.put(nazevData, null);
        souradnicePismena_0_0.add(oznaceniDat);
        
        for (int i = 0; i < souradnicePismena.size(); i++) {
            RGB = souradnicePismena.get(i);
            
            xOrig = RGB.get("x");
            yOrig = RGB.get("y");
            
            xNew = xOrig - xMin;
            yNew = yOrig - yMin;
            
            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");
            
            
            souradnicePismene_0_0 = zapisRGBDoMapy(xNew, yNew, red, green, blue);
            souradnicePismena_0_0.add(souradnicePismene_0_0);
            
        }
        
        System.out.print("");
        
    }
    
    
    int vratMinimalniSouradnico(String XneboY){
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        
        int sour;
        
        int sourMin = -1;
   
        for (int i = 0; i < souradnicePismena.size(); i++) {
            RGB = souradnicePismena.get(i);
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
            
            
}
