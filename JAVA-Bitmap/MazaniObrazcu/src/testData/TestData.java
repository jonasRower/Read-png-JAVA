
package testData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;


public class TestData {
    
    ArrayList<HashMap<String, Integer>> souradniceRGB = new ArrayList<HashMap<String, Integer>>();
    private String adresaTxt;
    private BufferedImage image;
    
    public TestData(ArrayList<HashMap<String, Integer>> souradniceRGB) throws IOException{
        this.souradniceRGB = souradniceRGB;
        //this.adresaTxt = adresaTxt;
        
        //TiskDoTxt();
    }
    
    //vytiskne data prislusneho radku a sloupce
    public TestData(ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcich, int radek, int sloupec){
        
        ArrayList<ArrayList<HashMap<String, Integer>>> MapaVsechPismenNaJednomRadku = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<HashMap<String, Integer>> MapaPismeneZVybranehoSloupce = new ArrayList<HashMap<String, Integer>>();
        
        MapaVsechPismenNaJednomRadku = MapaVsechPismenNaVsechRadcich.get(radek);
        MapaPismeneZVybranehoSloupce = MapaVsechPismenNaJednomRadku.get(sloupec);
        
        this.souradniceRGB = MapaPismeneZVybranehoSloupce;
        
    }
    
    
    //vytiskne data prislusneho poradi
    public TestData(ArrayList<ArrayList<HashMap<String, Integer>>> MapaVsechPismenPole, int poradi){
        
        ArrayList<HashMap<String, Integer>> MapaPismeneZVybranehoPoradi = new ArrayList<HashMap<String, Integer>>();
        MapaPismeneZVybranehoPoradi = MapaVsechPismenPole.get(poradi);
        
        this.souradniceRGB = MapaPismeneZVybranehoPoradi;
        
    }

   
    void TiskDoTxt() throws IOException{
        
        //FileWriter writer = new FileWriter("C:\\Users\\jonas\\OneDrive\\Dokumenty\\JAVA_KCT\\TextReader\\TextReader\\pixel_values.txt");
        FileWriter writer = new FileWriter(adresaTxt);
    
        int v;
        int x;
        int y;
        
        int red;
        int green;
        int blue;
        
        String RGBstr;
        HashMap<String, Integer> RGB = new HashMap<String, Integer>(); 
        
        for (int i = 1; i < souradniceRGB.size(); i++) {
            RGB = souradniceRGB.get(i);
            
            x = RGB.get("x");
            y = RGB.get("y");
            
            red = RGB.get("Red");
            green = RGB.get("Green");
            blue = RGB.get("Blue");
            
            RGBstr = "x: " + x + "   y: " + y + "   red: " + red + "   green: " + green + "   blue: " + blue + "\n"; 
            writer.append(RGBstr);
            
        }  
        
        writer.close();
    }
    
    
    public void TiskDoPng(){
        
        File file = new File("C:\\Users\\jonas\\OneDrive\\Dokumenty\\JAVA_KCT\\TextReader\\VektoryCar_1_pismena\\Test\\test_data.png");
        
        HashMap<String, Integer> RGB = new HashMap<String, Integer>();
        
        int x;
        int y;
        
        int red;
        int green;
        int blue;
          
            
        try
        {
            //nacita prazdny testovaci png aby ziskal vychozi nastaveni obrazku
            image = ImageIO.read(file);
            
            for (int i = 1; i < souradniceRGB.size(); i++) {
                
                try{
                    RGB = souradniceRGB.get(i);

                    x = RGB.get("x");
                    y = RGB.get("y");

                    red = RGB.get("Red");
                    green = RGB.get("Green");
                    blue = RGB.get("Blue");

                    vykresliPixelPng(x, y, red, green, blue);
                }
                
                catch(Exception e){
                    System.out.println("");
                    System.out.println(e);
                }
        
            }  
            
            ImageIO.write(image, "png", new File("C:\\Users\\jonas\\OneDrive\\Dokumenty\\JAVA_KCT\\TextReader\\VektoryCar_1_pismena\\Test\\output5.png"));
          
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
         
        System.out.println("done");
        
    }
    
    
    void vykresliPixelPng(int x, int y, int red, int green, int blue){
       
        int rgb = 0;
        rgb = red*65536 + green*256 + blue;
        
        image.setRGB(x,y,rgb); 
        
    }
    
}
