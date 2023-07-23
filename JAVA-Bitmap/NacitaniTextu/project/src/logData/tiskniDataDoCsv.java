/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class tiskniDataDoCsv {
   
    ArrayList<String> poleRadku = new ArrayList<>();
    int indexPosledniUrovne;
    
    public tiskniDataDoCsv(ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> data){
        
        int poleOdsazeni[];
        String poleVetvicek[];
        String poleStromuRadku[];
        boolean poleZanoreni[];
        
        int[][] poleVsechIndexuRadku;
        int[][] startEndPole;
        
        int[][] poleOdsazeniRodice;
        int[] koncoveIndexyPosledniUrovne;
                
        tiskniListArrayListArrayListHashMap(data);
        poleOdsazeni = ziskejPoleOdsazeni(poleRadku);
        indexPosledniUrovne = vratMaxItem(poleOdsazeni); 
        
        //udelat jinak
        //vytvorit pole, ktere 
        poleVsechIndexuRadku = vratPoleVsechIndexuRadku(poleOdsazeni);
        
        //zatim nepouzivam
        //poleOdsazeniRodice = vratIndexRodicuKeVsemOdsazenim(poleOdsazeni, poleVsechIndexuRadku);
        startEndPole = vratStartEndPolePosledniUrovne(poleVsechIndexuRadku);
        
        vratCestuStartEnd(startEndPole, poleVsechIndexuRadku);
        
       
        
        
        
        
        /*
        //-------------------------------------------------------
        //zatim neni v kodu
        poleVetvicek = vratPoleVetvicek(poleOdsazeni);
        
        //strom je treba jeste odsadit
        //vytori pole boolean tech radku, ktere jsou zanorene
        poleZanoreni = vratZanoreniBool(poleVetvicek);
        
        //vytvori odsazene vetvicky o jednu uroven
        poleVetvicek = vytvorZanoreneVetvicky(poleVetvicek, poleZanoreni);
        
        //sestavi strom
        poleStromuRadku = spojVetvickySTextem(poleVetvicek, poleRadku);
        
        //vytiskne do logu
        tiskniDoLoguArr(poleStromuRadku);
        
        System.out.println("");
        */
    }
    
    
    private void vratCestuStartEnd(int[][] startEndPole, int[][] poleVsechIndexuRadku){
        
        int[] dataZakladniUrovne = poleVsechIndexuRadku[0];
        int[][] pathStartEndPole = new int[startEndPole.length][3];
        int itemStart;
        int itemPrev;
        int itemEnd;
        
        for (int i = 0; i < startEndPole.length; i++){
            itemStart = startEndPole[i][0];
            
            if(itemStart > -1){
                itemPrev = vratDataNejblizsihoNizsihoIndexu(dataZakladniUrovne, itemStart);
                itemEnd = startEndPole[i][1];
            }
            else{
                itemStart = -1;
                itemPrev = -1;
                itemEnd = -1;
            }
            
            pathStartEndPole[i][0] = itemPrev;
            pathStartEndPole[i][1] = itemStart;
            pathStartEndPole[i][2] = itemEnd;
            
        }
        
        System.out.println("");
        
        
    }
    
    
    private int vratDataNejblizsihoNizsihoIndexu(int[] dataZakladniUrovne, int itemExp){
        
        int item;
        int itemPrev;
        itemPrev = -1;
        
        for (int i = 0; i < dataZakladniUrovne.length; i++){
            
            item = dataZakladniUrovne[i];
            if(item > itemExp){
                break;
            }
            itemPrev = item;
        }
        
        return(itemPrev);
        
    }
    
    
    private int[][] vratStartEndPolePosledniUrovne(int[][] poleVsechIndexuRadku){
    
        int[] dataPredposledniUrovne = poleVsechIndexuRadku[indexPosledniUrovne-1];
        int delkaPredposledniUrovne = dataPredposledniUrovne.length;
        int[] koncoveIndexyPosledniUrovne;
        
        koncoveIndexyPosledniUrovne = vratPoleKoncovychIndexuPosledniUrovne(delkaPredposledniUrovne, poleVsechIndexuRadku);
        
        int[][] startEndPole = new int[koncoveIndexyPosledniUrovne.length][2];
        int indexStart;
        int indexEnd;
        
        for (int i = 0; i < koncoveIndexyPosledniUrovne.length; i++){
            indexEnd = koncoveIndexyPosledniUrovne[i];
            indexStart = vratIndexStart(dataPredposledniUrovne, indexEnd);
            
            startEndPole[i][0] = indexStart;
            startEndPole[i][1] = indexEnd;
        }
        
        return(startEndPole);
        
    }
    
    
    private int vratIndexStart(int[] data, int indexEnd){
        
        int item;
        int indexStart;
        
        indexStart = -1;
        
        if(indexEnd > 0){
            
            for (int i = 0; i < data.length; i++){
            
                item = data[i];
                if(item == indexEnd+1){
                    indexStart = data[i-1];
                    break;
                }
            
            }
        }
        
        
        return(indexStart);
        
    }
    
    
    private int[] vratPoleKoncovychIndexuPosledniUrovne(int pocetVetvicekPosledniUrovne, int[][] poleVsechIndexuRadku){
          
        //int iStart;
        int indexEnd;
        int indexAct;
        int indexPrev;
        int iZapis;
        int i;
        
        int[] koncoveIndexyPosledniUrovne = new int[pocetVetvicekPosledniUrovne];
        
        iZapis = 0;
        for (i = 2; i < poleVsechIndexuRadku[2].length; i++){
            indexPrev = poleVsechIndexuRadku[2][i-1];
            indexAct = poleVsechIndexuRadku[2][i];
            
            if(indexAct > indexPrev+1){
                indexEnd = indexPrev;
                koncoveIndexyPosledniUrovne[iZapis] = indexEnd;
               
                iZapis++;
            }
            
            System.out.println("");
        }
        
        //prida jeste koncovy index
        koncoveIndexyPosledniUrovne[iZapis] = poleVsechIndexuRadku[2][i-1];
        
        
        return(koncoveIndexyPosledniUrovne);
        
    }
    
    
   
    
    
    
    private int vratPocetVyskytuIndexu(int[][] data2D, int valueExp){
        
        int value;
        int count;
        
        count = 0;
        for (int i = 0; i < data2D.length; i++){
            
            value = data2D[i][0];
            if(value == valueExp){
                count++;
            }
            
        }
        
        return(count);
        
    }
    
    
    private int[][] vratIndexRodicuKeVsemOdsazenim(int poleOdsazeni[], int[][]poleVsechIndexuRadku){
        
        int[][] poleOdsazeniRodice = new int[poleOdsazeni.length][2];
        int[] poleDleOdsazeni;
        int indexRadku;
        int odsazeni;
        int odsazeniRodic;
        int indexRadkuRodic;
        
        for (int i = 0; i < poleOdsazeni.length; i++){
            odsazeni = poleOdsazeni[i];
            odsazeniRodic = odsazeni - 1;
            indexRadku = i;
            
            if(odsazeniRodic > -1){
                poleDleOdsazeni = vratPoleDanehoOdsazeni(poleVsechIndexuRadku, odsazeniRodic);
                indexRadkuRodic = vratIndexRadkuRodice(poleDleOdsazeni, indexRadku);
                
                poleOdsazeniRodice[i][0] = odsazeni;
                poleOdsazeniRodice[i][1] = indexRadkuRodic; 
            }
            
        }
        
        return(poleOdsazeniRodice);
             
    }
    
    
    private int vratIndexRadkuRodice(int[] poleDleOdsazeni, int indexRadkuExp){
        
        int indexRadku;
        int indexRadkuPrev;
        indexRadkuPrev = 0;
        
        for (int i = 0; i < poleDleOdsazeni.length; i++) {
            
            indexRadku = poleDleOdsazeni[i];
            if(indexRadku > indexRadkuExp){
                break;
            }
            
            indexRadkuPrev = indexRadku;
            
        }
        
        return(indexRadkuPrev);
        
    }
    
    
    private int[] vratPoleDanehoOdsazeni(int[][]poleVsechIndexuRadku, int odsazeniExp){
        
        int[] poleDleOdsazeni = poleVsechIndexuRadku[odsazeniExp];
        
        return(poleDleOdsazeni);
        
    }
    
    
    private int[][] vratPoleVsechIndexuRadku(int poleOdsazeni[]){
        
        int[][] poleVsechIndexuRadku = new int[indexPosledniUrovne+1][]; 
        
        for (int i = 0; i < indexPosledniUrovne+1; i++) {
            int[] poleIndexuRadku = vratPoleVsechIndexuRadkuProDaneOdsazeni(poleOdsazeni, i);
            poleVsechIndexuRadku[i] = poleIndexuRadku;
            
        }
        
        return(poleVsechIndexuRadku);
        
    }
    
    
    //vrati pole vsech indexu radku, ktere jsou razeny dle odsazeni
    private int[] vratPoleVsechIndexuRadkuProDaneOdsazeni(int poleOdsazeni[], int odsazeniExp){
        
        int odsazeni;
        int pocetPolozek;
        
        int iZapis;
        
        pocetPolozek = vratPocetPolozekPole(poleOdsazeni, odsazeniExp);
        int[] poleIndexuRadku = new int[pocetPolozek+1];
        
        //do nulteho indexu se vzdy zapise cislo odsazeniExp
        poleIndexuRadku[0] = odsazeniExp;
        
        iZapis = 1;
        for (int i = 1; i < poleOdsazeni.length; i++) {
            
            odsazeni = poleOdsazeni[i];
            if(odsazeni == odsazeniExp){
                poleIndexuRadku[iZapis] = i;
                iZapis++;
            }
        }
        
        return(poleIndexuRadku);
        
    }
    
    
    //vrati maximalni polozku pole
    private int vratMaxItem(int[] array){
        
        int item;
        int itemMax;
        
        itemMax = 0;
        
        for (int i = 1; i < array.length; i++) {
            
            item = array[i];
            if(item > itemMax){
                itemMax = item;
            }
            
        }
        
        return(itemMax);
    }
    
    
    //vrati delku pole
    private int vratPocetPolozekPole(int pole[], int polozkaExp){
        
        int polozka;
        int pocetPolozek;
        
        pocetPolozek = 0;
        
        for (int i = 1; i < pole.length; i++) {
            polozka = pole[i];
            if(polozkaExp == polozka){
                pocetPolozek = pocetPolozek + 1;
            }
        }
        
        return(pocetPolozek);
        
    }
    
    
    private void vytvorPoleVsechPotomku(int poleOdsazeni[]){
        
        int rodic;
        int odsazeni;
        int prvniRadekPotomka;
        int posledniRadekPotomka;
        
        
        
        for (int i = 1; i < poleOdsazeni.length; i++) {
            
            
        }
        
    }
    
    
    private String[] vytvorZanoreneVetvicky(String[] poleVetvicek, boolean[] poleZanoreni){
        
        String radek;
        String radekNew;
        Boolean zanoreniBool0;
        Boolean zanoreniBool_1;
        
        String[] poleVetvicekNew = new String[poleVetvicek.length];
        
        for (int i = 1; i < poleVetvicek.length; i++) {
            
            radek = poleVetvicek[i];
            zanoreniBool0 = poleZanoreni[i];
            zanoreniBool_1 = poleZanoreni[i-1];
            
            if(zanoreniBool0 == true){
                if(zanoreniBool_1 == false){
                    radekNew = "├─" + radek;
                }
                else{
                    radekNew = "│ " + radek;
                }
            }
            else{
                radekNew = radek;
            }
            
            poleVetvicekNew[i] = radekNew;
            
        }
        
        return(poleVetvicekNew);
        
    }
    
    
    private boolean[] vratZanoreniBool(String[] poleVetvicek){
        
        String[] poleStromuRadkuNew = new String[poleVetvicek.length];
        
        String radek_1;
        String radek0;
        boolean zanorenaVetev;
        boolean zanor;
        boolean[] poleZanoreni = new boolean[poleVetvicek.length];
        
        zanor = false;
        poleZanoreni[0] = false;
        
        for (int i = 1; i < poleVetvicek.length; i++) {
            
            radek_1 = poleVetvicek[i-1];
            radek0 = poleVetvicek[i];
            
            //zacatek zanoreni
            if(zanor == false){
                zanorenaVetev = detekujZanorenouVetev(radek_1, radek0); 
                zanor = zanorenaVetev;
            }
            
            //konec zanoreni
            if(zanor == true){
                if(radek0 == ""){
                   zanor = false;
                }
            }
            
            poleZanoreni[i] = zanor;
            
        }
        
        return(poleZanoreni);
        
    }
    
    
    
    private boolean detekujZanorenouVetev(String radek_1, String radek0){
        
        boolean zanorenaVetev;
        zanorenaVetev = false;
        
        radek_1 = radek_1.trim();
        radek0 = radek0.trim();
                
        if(radek_1.equalsIgnoreCase("┐") == true){
            if(radek0.equalsIgnoreCase("┐") == true){
                zanorenaVetev = true;
            }
        }
        
        return(zanorenaVetev);
        
    }
    
    
    private String[] spojVetvickySTextem(String poleVetvicek[], ArrayList<String> poleRadku){
        
        String radek;
        String vetvicka;
        String vetvickaRadek;
        
        String[] poleStromuRadku = new String[poleRadku.size()];
        
        for (int i = 0; i < poleRadku.size(); i++) {
            
            radek = poleRadku.get(i);
            vetvicka = poleVetvicek[i];
            
            //slouci radek dohromady        
            vetvickaRadek = vetvicka + radek;
            
            /*
            //opravi vetvicku
            if(i == 0){
                vetvickaRadek = "┐";
            }
            else{
                vetvickaRadek = opravVetvicku(radek, vetvickaRadek, vetvicka);
            }
            */
            
            poleStromuRadku[i] = vetvickaRadek;
            
        }
        
        return(poleStromuRadku);
        
    }
    
    
    private String opravVetvicku(String radekOrig, String vetvickaRadek, String vetvicka){
        
        //opravi vetvicku, pokud je radek "{}" nebo "->"
        if(radekOrig == "->"){
            vetvickaRadek = "│ ";
        }
        
        if(radekOrig == "{}"){
            vetvickaRadek = "│ ";
        }
        
        if(vetvicka == "┐ "){
            vetvickaRadek = "┐ ";
        }
        
        if(vetvickaRadek.equalsIgnoreCase("└─<-") == true){
            vetvickaRadek = "";
        }
        
        if(vetvicka == null){
            vetvickaRadek = "";
        }
        
        return(vetvickaRadek);
        
    }
    
    
    private String[] vratPoleVetvicek(int poleOdsazeni[]){
        
        int odsazeni0;
        int odsazeni1;
        String vetvicka;
        
        String poleVetvicek[] = new String[poleOdsazeni.length];
        vetvicka = "";
        
        for (int i = 0; i < poleOdsazeni.length-1; i++) {
            odsazeni0 = poleOdsazeni[i];
            odsazeni1 = poleOdsazeni[i+1];
            
            if(odsazeni1 > odsazeni0){
                vetvicka = "┐ ";
            }
            
            if(odsazeni1 == odsazeni0){
                vetvicka = "├─";
            }
            
            if(odsazeni1 < odsazeni0){
                vetvicka = "└─";
            }
            
            poleVetvicek[i] = vetvicka;
            
        }
        
        return(poleVetvicek);
    }
    
    
    private int[] ziskejPoleOdsazeni(ArrayList<String> poleRadku){
        
        int odsazeni;
        String radek;
        
        int poleOdsazeni[] = new int[poleRadku.size()];
        
        odsazeni = 0;
        poleOdsazeni[0] = 0;
        
        for (int i = 1; i < poleRadku.size(); i++) {
            radek = poleRadku.get(i);
            if(radek == "->"){
                odsazeni = odsazeni + 1;
            }
            
            if(radek == "<-"){
                odsazeni = odsazeni - 1;
            }
            
            poleOdsazeni[i] = odsazeni;
            
        }
        
        return(poleOdsazeni);
        
    }
    
    
    private void tiskniListArrayListArrayListHashMap(ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> data){
        
        ArrayList<ArrayList<HashMap<String, Integer>>> ArrayListArrayListHashMap;
        
        //je potreba odskocit z nuly
        poleRadku.add("->");
        
        for (int i = 0; i < data.size(); i++) {
            ArrayListArrayListHashMap = data.get(i);
            poleRadku.add("->");
            tiskniArrayListHashMap(ArrayListArrayListHashMap);
            poleRadku.add("<-");
        }
        
    }
    
    
    private void tiskniArrayListHashMap(ArrayList<ArrayList<HashMap<String, Integer>>> ArrayListArrayListHashMap){
        
        ArrayList<HashMap<String, Integer>> ArrayListHashMap;
        
        for (int i = 0; i < ArrayListArrayListHashMap.size(); i++) {
            ArrayListHashMap = ArrayListArrayListHashMap.get(i);
            poleRadku.add("->");
            tiskniHashMap(ArrayListHashMap);
            poleRadku.add("<-");
        }
        
    }
    
    
    private void tiskniHashMap(ArrayList<HashMap<String, Integer>> ArrayListHashMap){
        
        HashMap<String, Integer> HashMap;
        String radek;
        
        for (int i = 0; i < ArrayListHashMap.size(); i++) {
            HashMap = ArrayListHashMap.get(i);
            radek = HashMap.toString();
            
            poleRadku.add(radek);
        }
        
    }
    
    //prevede array na String
    private String prevedArrNaText(String[] poleStromuRadku){
        
        String text;
        String radek;
        text = "";
                
        for (int i = 0; i < poleStromuRadku.length; i++) {
            
            radek = poleStromuRadku[i];
            text = text + radek + "\n";
            
        }
        
        return(text);
        
    }
    
    
    private void tiskniDoLoguArr(String[] poleStromuRadku){
        
        String text;
        text = prevedArrNaText(poleStromuRadku);
        
        try {
                FileWriter myWriter = new FileWriter("C:\\Users\\jonas\\OneDrive\\Dokumenty\\2023\\JAVA-Syntaxe\\Read-png-JAVA-01\\JAVA-Bitmap\\NacitaniTextu\\InputOutput\\treeVariables\\MapaVsechPismenNaVsechRadcich.txt");
                myWriter.write(text);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
                
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
          }
        
    }
    
    
}
