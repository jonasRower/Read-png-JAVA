
package obrazek;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class NactiPng {
    
    //souradnice a RGB vsech pixelu obrazku
    private ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
    
    private String CestaKpng;
    
    //nacte png a vrati souradnice vsech barev
    public NactiPng() throws IOException{

        this.CestaKpng = "C:\\Users\\jonas\\OneDrive\\Dokumenty\\JAVA_KCT\\TextReader\\VektoryCar_1_pismena\\Test\\testCara.png";
        
        NactiImg();
    }
    
    public ArrayList<HashMap<String, Integer>> getSouradniceVsechBarev(){
        
        return souradniceVsechBarev;
        
    }

    
    //naplni souradniceVsechBarev
    private void NactiImg() throws IOException{
        
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
    
}
