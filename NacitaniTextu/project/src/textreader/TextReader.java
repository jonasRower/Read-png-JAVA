package textreader;

import com.google.gson.Gson;
import createOutput.pathOfProject;
import createOutput.createOutput;
import ZdrojovaData.zdrojDataAbeceda;
import ZdrojovaData.zdrojDataZkoum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import logData2.variableJson;
import porovnaniSouradnic.PorovnaniSouradnic;
import porovnaniSouradnic.PosouzeniSouradnic;
import skupinaPismen.SouradniceSkupinyPismen;
import testData.TestData;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors




public class TextReader {
  
    public static void main(String[] args) throws IOException {

       String adresaProjektu;
       String json;
       
       pathOfProject cestaKProjektu = new pathOfProject();
       adresaProjektu = cestaKProjektu.getAdresaProjektu();
       
       //data pismen abecedy
       ArrayList<ArrayList<HashMap<String, Integer>>> MapaPismenAbeceda = new ArrayList<ArrayList<HashMap<String, Integer>>>();
       
       //data pismen zkoumaneho obrazku
       ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> MapaVsechPismenNaVsechRadcich = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
       
       //data obdsahuji posouzeni vsech pismen vsech radku z obrazku vuci vsem pismenum abecedy
       ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>> posouzeniPismenePngVsechRadkuKeVsemPismenABC = new ArrayList<ArrayList<ArrayList<HashMap<String, Double>>>>();
       
       //obshuje pole pismen v celem obrazku - radcich + sloupcich
       ArrayList<ArrayList<String>> pismenaVPng = new ArrayList<ArrayList<String>>();


       //inicializuje tridu
       SouradniceSkupinyPismen SouradnicePismena = new SouradniceSkupinyPismen();
       
       //ziska vstupni data originalni pro porovnavani
       zdrojDataAbeceda dataAbecedy = new zdrojDataAbeceda(adresaProjektu);
       SouradnicePismena.nactiDataAbecedy(dataAbecedy.getAdresaSlozky(), dataAbecedy.getNazvySouboruPng()); 
       MapaPismenAbeceda = SouradnicePismena.getMapaPismenAbeceda();


       //********************************************
       json = new Gson().toJson(MapaPismenAbeceda);
       variableJson jsonMapaPismenAbeceda = new variableJson(json, "MapaPismenAbeceda.json");
       //********************************************

       //ziska data zkoumaneho obrazku
       zdrojDataZkoum zkoumanaData = new zdrojDataZkoum(adresaProjektu);
       SouradnicePismena.nactiDataZkoumanehoObrazku(zkoumanaData.getAdresaSlozky(), zkoumanaData.getNazevSouboruPng());
       MapaVsechPismenNaVsechRadcich = SouradnicePismena.getMapaVsechPismenNaVsechRadcich();

       //********************************************
       json = new Gson().toJson(MapaVsechPismenNaVsechRadcich);
       variableJson jsonMapaVsechPismen = new variableJson(json, "MapaVsechPismenNaVsechRadcich.json");
       //********************************************

       //porovnavaData
       PosouzeniSouradnic posouzeni = new PosouzeniSouradnic(MapaPismenAbeceda, MapaVsechPismenNaVsechRadcich);
       posouzeniPismenePngVsechRadkuKeVsemPismenABC = posouzeni.getPosouzeni();

       
       PorovnaniSouradnic porovnani = new PorovnaniSouradnic(posouzeniPismenePngVsechRadkuKeVsemPismenABC, adresaProjektu);
       pismenaVPng = porovnani.getPismenaVPng();

       
       
       createOutput vytvorVystup = new createOutput(pismenaVPng, adresaProjektu, "\\InputOutput\\outputs\\output.csv");

        
    }
    
}