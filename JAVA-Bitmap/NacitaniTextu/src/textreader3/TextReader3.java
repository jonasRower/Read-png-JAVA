
package textreader3;

import ZdrojovaData.zdrojDataAbeceda;
import ZdrojovaData.zdrojDataZkoum;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import porovnaniSouradnic.PorovnaniSouradnic;
import porovnaniSouradnic.PosouzeniSouradnic;
import skupinaPismen.SouradniceSkupinyPismen;
import testData.TestData;



public class TextReader3 {
  
    public static void main(String[] args) throws IOException {
        
       String adresaSlozkyABC;
       String[] nazvySouboruABC;
       
       String adresaSlozkyData;
       String nazevSouboruData;
       
       
       //data pismen abecedy
       ArrayList<ArrayList<HashMap<String, Integer>>> MapaPismenAbeceda = new ArrayList<ArrayList<HashMap<String, Integer>>>();
       
       //data pismen zkoumaneho obrazku
       ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcich = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
       
       //data obdsahuji posouzeni vsech pismen vsech radku z obrazku vuci vsem pismenum abecedy
       ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> posouzeniPismenePngVsechRadkuKeVsemPismenABC = new ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>>();
       
       
       
        
       //inicializuje tridu
       SouradniceSkupinyPismen SouradnicePismena = new SouradniceSkupinyPismen();
       
       //ziska vstupni data originalni pro porovnavani
       zdrojDataAbeceda dataAbecedy = new zdrojDataAbeceda();
       SouradnicePismena.nactiDataAbecedy(dataAbecedy.getAdresaSlozky(), dataAbecedy.getNazvySouboruPng()); 
       MapaPismenAbeceda = SouradnicePismena.getMapaPismenAbeceda();
       
       //ziska data zkoumaneho obrazku
       zdrojDataZkoum zkoumanaData = new zdrojDataZkoum();
       SouradnicePismena.nactiDataZkoumanehoObrazku(zkoumanaData.getAdresaSlozky(), zkoumanaData.getNazevSouboruPng());
       MapaVsechPismenNaVsechRadcich = SouradnicePismena.getMapaVsechPismenNaVsechRadcich();
       
       
       //porovnavaData
       PosouzeniSouradnic posouzeni = new PosouzeniSouradnic(MapaPismenAbeceda, MapaVsechPismenNaVsechRadcich);
       posouzeniPismenePngVsechRadkuKeVsemPismenABC = posouzeni.getPosouzeni();
       
       PorovnaniSouradnic porovnani = new PorovnaniSouradnic(posouzeniPismenePngVsechRadkuKeVsemPismenABC);
       
       //testuje data - zakom./odkomentovat jeden nebo druhy radek, podle toho, co chci tisknout
       //TestData test = new TestData(MapaPismenAbeceda, 0);
       //TestData test = new TestData(MapaVsechPismenNaVsechRadcich, 0, 0);
       
       //test.TiskDoPng();
        
    }
    
}
