
package skupinaPismen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import nactiPng.NactiPng;
import souradniceAbecedy.SouradnicePismene;
import souradniceJednohoPismene.SouradniceJednohoPismene;
import testData.TestData;


public class SouradniceSkupinyPismen {
    
    //Mapa pismen abecedy - originalni data
    private ArrayList<ArrayList<HashMap<String, Integer>>> MapaPismenAbeceda = new ArrayList<ArrayList<HashMap<String, Integer>>>();
     
    //Mapa s daty s pismeny na vsech radcich
    ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcich = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
    
    
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getMapaPismenAbeceda(){
        
        return (MapaPismenAbeceda);
        
    }
    
    public ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> getMapaVsechPismenNaVsechRadcich(){
        
        return (MapaVsechPismenNaVsechRadcich);
        
    } 
    
    
    
    
    public void nactiDataAbecedy(String adresaSlozky, String[] NazvySouboruPng) throws IOException{
        
        String nazevZdrojPng;
        String celaAdresaZdrojPng;
        
        NactiPng novyObrazek;
        
        ArrayList<HashMap<String, Integer>> souradniceVsechBarevObrazku;
        ArrayList<HashMap<String, Integer>> souradniceObrazkuModifikovane = new ArrayList<HashMap<String, Integer>>();
        
        for (int i = 0; i < NazvySouboruPng.length; i++) {
            nazevZdrojPng = NazvySouboruPng[i];
            
            celaAdresaZdrojPng = adresaSlozky + nazevZdrojPng;
            novyObrazek = new NactiPng(adresaSlozky, nazevZdrojPng);
            
            //nacte data z jednoho obrazku
            souradniceVsechBarevObrazku = novyObrazek.getSouradniceVsechBarev();
            
            //inicializuje tridu pro ziskani souradnic, ktere jsou pouze cerne
            SouradniceJednohoPismene souradniceCerne = new SouradniceJednohoPismene(souradniceVsechBarevObrazku);
            souradniceObrazkuModifikovane = souradniceCerne.getSouradnicePismeneCernobile(0, 0, 0);
            
            //re-inicializuje tridu pro ziskani souradnic, ktere jsou vztazene k nule
            SouradniceJednohoPismene souradniceKNule = new SouradniceJednohoPismene(souradniceObrazkuModifikovane);
            souradniceObrazkuModifikovane = souradniceKNule.getSouradniceVztazeneKNule();
            
            //zapise souradnice do pole
            MapaPismenAbeceda.add(souradniceObrazkuModifikovane);
            
            System.out.print("");
        }
        
        System.out.print("");
        
    }
    
    
    public void nactiDataZkoumanehoObrazku(String adresaSlozky, String NazevSouboruPng) throws IOException{
        
        String celaAdresaZdrojPng;
        ArrayList<HashMap<String, Integer>> souradniceVsechBarevObrazku;
        ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> souradniceVsechBarevVsechRadkuText;
        ArrayList<ArrayList<HashMap<String, Integer>>> souradniceVsechBarevJednohoRadkuText;
        
        //celaAdresaZdrojPng = adresaSlozky + NazevSouboruPng;
        NactiPng novyObrazek = new NactiPng(adresaSlozky, NazevSouboruPng);
        
        //nacte data z jednoho obrazku
        souradniceVsechBarevObrazku = novyObrazek.getSouradniceVsechBarev();
        
        //mapa vsech pismen na jednom radku
        ArrayList<ArrayList<HashMap<String, Integer>>> MapaVsechPismenNaRadku = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        
        //vrati souradnice pixelu radku
        VytvorRadkyPng pngRadky = new VytvorRadkyPng(souradniceVsechBarevObrazku);
        
        //Trida s daty s pismeny na jednom radku
        RozdelRadekNaPismena PismenaNaRadku;
        
        
        //---------------- Data rozdelena na jednotlive radky --------------------------------
        //
        //souradnice barev rozdelena na radky
        souradniceVsechBarevVsechRadkuText = pngRadky.getSouradniceVsechBarevVsechRadkuText();
        
        
        //data na jednotlivych radcich
        for (int i = 0; i < souradniceVsechBarevVsechRadkuText.size(); i++) {
            
            souradniceVsechBarevJednohoRadkuText = souradniceVsechBarevVsechRadkuText.get(i);
            
            //Po jednotlivych radcich rozdeli data na jednotliva pismena
            //Inicializuje tridu v kazdem cyklu zvlast
            PismenaNaRadku = new RozdelRadekNaPismena(souradniceVsechBarevJednohoRadkuText);
            MapaVsechPismenNaRadku = PismenaNaRadku.getMapaVsechPismenNaRadku(); 
        
            //prida data z jednoho radku do vsech radku
            MapaVsechPismenNaVsechRadcich.add(MapaVsechPismenNaRadku);
            System.out.print("");
                    
        } 
        
        //redukuje data vsech pismen
        redukujDataMapyVsechPismen();
        
        System.out.print("");
        
    }
    
    //redukuje data, s tim, ze vrati pouze souradnice RGB s cernou barvou a vztazene k nule
    private void redukujDataMapyVsechPismen() throws IOException{
        
        ArrayList<ArrayList<HashMap<String, Integer>>> MapaVsechPismenNaRadku = new ArrayList<ArrayList<HashMap<String, Integer>>>();
        ArrayList<HashMap<String, Integer>> MapaPismene = new ArrayList<HashMap<String, Integer>>(); 
        
        ArrayList<HashMap<String, Integer>> MapaPismeneModifikovane = new ArrayList<HashMap<String, Integer>>(); 
        ArrayList<ArrayList<HashMap<String, Integer>>> MapaVsechPismenNaRadkuModifikovane = null;
        ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcichModifikovane = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
        
        for (int r = 0; r < MapaVsechPismenNaVsechRadcich.size(); r++) {
            MapaVsechPismenNaRadku = MapaVsechPismenNaVsechRadcich.get(r);
            MapaVsechPismenNaRadkuModifikovane = new ArrayList<ArrayList<HashMap<String, Integer>>>();
            
            for (int s = 0; s < MapaVsechPismenNaRadku.size(); s++) {
                MapaPismene = MapaVsechPismenNaRadku.get(s);
                
                //MapaPismeneModifikovane = MapaPismene;
                
                //inicializuje tridu pro ziskani souradnic, ktere jsou pouze cerne
                SouradniceJednohoPismene souradniceCerne = new SouradniceJednohoPismene(MapaPismene);
                MapaPismeneModifikovane = souradniceCerne.getSouradnicePismeneCernobile(0, 0, 0);
            
                //re-inicializuje tridu pro ziskani souradnic, ktere jsou vztazene k nule
                SouradniceJednohoPismene souradniceKNule = new SouradniceJednohoPismene(MapaPismeneModifikovane);
                MapaPismeneModifikovane = souradniceKNule.getSouradniceVztazeneKNule();
                
                
                
                //zpetne posklada data
                MapaVsechPismenNaRadkuModifikovane.add(MapaPismeneModifikovane);
                
                //Testuje data
                //TestData test = new TestData(MapaPismeneModifikovane);
                //test.TiskDoPng();
                
                
                System.out.println();
                
            }
            
            MapaVsechPismenNaVsechRadcichModifikovane.add(MapaVsechPismenNaRadkuModifikovane);
            System.out.println();
            
        }
        
        MapaVsechPismenNaVsechRadcich = MapaVsechPismenNaVsechRadcichModifikovane; 

    }
    
   
    
    
    
    
    
      
}
