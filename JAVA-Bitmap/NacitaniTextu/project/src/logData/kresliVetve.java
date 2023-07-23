/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logData;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jonas
 */
public class kresliVetve {
    
    String[] dataVetveTotNul;
    
    public kresliVetve(int[][] pathStartEndPole){
        
        String[][] dataVsechVetvi;
        String[] dataVetveTot;
        String[] svislaNultaVetev;
       
        
        dataVsechVetvi = vratPoleVsechVetvi(pathStartEndPole);
        dataVetveTot = sjednotDataVsechVetvi(dataVsechVetvi);
        svislaNultaVetev = vytvorDataNulteVetve(dataVetveTot);
        dataVetveTotNul = sjednotNultouVetevSDaty(svislaNultaVetev, dataVetveTot);  
        
    }
    
    
    public String[] getVratVetve(){
        return(dataVetveTotNul);
    }
    
    
    private String[] sjednotNultouVetevSDaty(String[] svislaNultaVetev, String[] dataVetveTot){
        
        String[] dataVetveTotNul;
        dataVetveTotNul = sjednotDataDvouVetvi(svislaNultaVetev, dataVetveTot);
        
        return(dataVetveTotNul);
    }
    
    
    private String[] vytvorDataNulteVetve(String[] dataVetveTot){
        
        int[] poleIndexuRadkuSOdbockamiDolu;
        String[] poleOdbocekNulteVetve;
        String[] dataNulteVetveSvisla = new String[dataVetveTot.length];
        String[] svislaNultaVetev;

            
        poleIndexuRadkuSOdbockamiDolu = vratPoleIndexuRadkuSOdbockami(dataVetveTot);
        poleOdbocekNulteVetve = poleOdbocekNulteVetve(poleIndexuRadkuSOdbockamiDolu);
        dataNulteVetveSvisla = vytvorPouzeSvislouVetev(poleIndexuRadkuSOdbockamiDolu, dataVetveTot);
        svislaNultaVetev = doNulteSvileVetveDokresliOdbocky(dataNulteVetveSvisla, poleIndexuRadkuSOdbockamiDolu, poleOdbocekNulteVetve);
   
        
        return(svislaNultaVetev);
        
    }
    
    
    
    private String[] doNulteSvileVetveDokresliOdbocky(String[] dataNulteVetveSvisla, int[] poleIndexuRadkuSOdbockamiDolu, String[] poleOdbocekNulteVetve){
        
        int indexOdbocky;
        String strOdbocky;
        
        for (int i = 0; i < poleIndexuRadkuSOdbockamiDolu.length-1; i++) {
            indexOdbocky = poleIndexuRadkuSOdbockamiDolu[i];
            strOdbocky = poleOdbocekNulteVetve[i];
            
            dataNulteVetveSvisla[indexOdbocky] = strOdbocky;
        }
        
        return(dataNulteVetveSvisla);
    }
    
    
    private String[] vytvorPouzeSvislouVetev(int[] poleIndexuRadkuSOdbockamiDolu, String[] dataVetveTot){
        
        int indStart;
        int indEnd;
        
        indStart = poleIndexuRadkuSOdbockamiDolu[0];
        indEnd = poleIndexuRadkuSOdbockamiDolu[poleIndexuRadkuSOdbockamiDolu.length-1];
        
        String[] dataNulteVetveSvisla = new String[dataVetveTot.length];
        String radek;
        
        for (int i = 0; i < dataVetveTot.length; i++) {
            
            radek = "  ";
            
            if(i < indEnd){
                if(i >= indStart){
                    radek = "│ ";
                }
            }    
            else{
                if(i == indEnd){
                    radek = "└─";
                }
                else{
                    radek = "  ";
                }

            }
                
            dataNulteVetveSvisla[i] = radek;
            
        }
        
        return(dataNulteVetveSvisla);
        
    }
    
           
    
    
    private String[] poleOdbocekNulteVetve(int[] poleIndexuRadkuSOdbockamiDolu){
        
        int indexRadkuSOdbockou;
        String[] poleOdbocekNulteVetve = new String [poleIndexuRadkuSOdbockamiDolu.length];
        String strOdbocky;
        
        for (int i = 0; i < poleIndexuRadkuSOdbockamiDolu.length; i++) {
            
            indexRadkuSOdbockou = poleIndexuRadkuSOdbockamiDolu[i];
            if(i < poleIndexuRadkuSOdbockamiDolu.length-1){
                strOdbocky = "├─";
            }
            else{
                strOdbocky = "└─";
            }
            
            poleOdbocekNulteVetve[i] = strOdbocky;
            
        }
        
        return(poleOdbocekNulteVetve);
        
    }
    
    //je potreba do pole dataVetveTot dopnit vodorovne vetvicky
    //doplneno bude tam, kde je "┐",
    //proto je potreba vratit pole se vsemi indexy techto radku
    private int[] vratPoleIndexuRadkuSOdbockami(String[] dataVetveTot){
        
        int pocetOdbocekDolu;
        String radek;
        boolean obsahujeSubstr;
        int iZapis;
        
        pocetOdbocekDolu = vratPocetOdbocekDolu(dataVetveTot);
        iZapis = 0;
        
        int[] poleIndexuRadku = new int[pocetOdbocekDolu];
        
        for (int i = 0; i < dataVetveTot.length; i++) {
            radek = dataVetveTot[i];
            obsahujeSubstr = detekujPritomnostSubStringu(radek, "┐");
            
            if(obsahujeSubstr == true){
                poleIndexuRadku[iZapis] = i;
                iZapis++;
            }
            
        }
        
        return(poleIndexuRadku);
        
    }
    
    //vrati pocet radku s "┐", tak aby mohl naplnit vytvorit delku pole
    private int vratPocetOdbocekDolu(String[] dataVetveTot){
        
        String radek;
        boolean obsahujeSubstr;
        int pocetOdbocekDolu;
        
        pocetOdbocekDolu = 0;
        
        for (int i = 0; i < dataVetveTot.length; i++) {
            radek = dataVetveTot[i];
            obsahujeSubstr = detekujPritomnostSubStringu(radek, "┐");
            
            if(obsahujeSubstr == true){
                pocetOdbocekDolu++;
            }
            
        }
        
        return(pocetOdbocekDolu);
        
    }
    
    
    private boolean detekujPritomnostSubStringu(String str, String subStr){
        
        int ind;
        ind = str.indexOf(subStr);
        
        boolean obsahujeSubstr;
        
        if(ind > -1){
            obsahujeSubstr = true;
        }
        else{
            obsahujeSubstr = false;
        }
        
        return(obsahujeSubstr);
        
    }
            
            
    
    
    private String[] sjednotDataVsechVetvi(String[][] dataVsechVetvi){
        
        String[] dataVetveTot = new String[dataVsechVetvi[dataVsechVetvi.length-1].length];
        
        for (int i = dataVsechVetvi.length-1; i > -1; i--) {
        
           String[] dataJedneVetve; 
           dataJedneVetve = dataVsechVetvi[i];
           dataVetveTot = sjednotDataDvouVetvi(dataVetveTot, dataJedneVetve);
        
        }
        
        return(dataVetveTot);
        
    }
    
    
    private String[] sjednotDataDvouVetvi(String[] vetevOrig, String[] vetevNew){
        
        String radekOrig;
        String radekNew;
        String radekTot;
        
        boolean prvniZapis;
        String[] vetevTot = new String[vetevOrig.length];
        
        if(vetevOrig[0] == null){
            prvniZapis = true;
        }
        else{
            prvniZapis = false;
        }
        
        
        for (int i = 0; i < vetevOrig.length; i++) {
            
            if(prvniZapis == true){
                    radekOrig = "";
            }
            else{
                radekOrig = vetevOrig[i];
            }
            
            if(i >= vetevNew.length){
                radekNew = "";
            }
            else{
                radekNew = vetevNew[i]; 
            }
            
            
            vetevTot[i] = radekOrig + radekNew;
            
        }
        
        return(vetevTot);
        
    }
    
    
    
    private String[][] vratPoleVsechVetvi(int[][] pathStartEndPole){
    
        int[] dataVetve;
        String[] radkyVetve;
        
        int posledniIndex;
        
        posledniIndex = vratPosledniIndexPlatneVetve(pathStartEndPole)+1;
        String[][] dataVsechVetvi = new String[posledniIndex][];
        
        
        for (int i = 0; i < posledniIndex; i++) {
            
            dataVetve = pathStartEndPole[i];
            radkyVetve = nakresliVetev(dataVetve);
            dataVsechVetvi[i] = radkyVetve;
               
        }
        
        return(dataVsechVetvi);
    
    }
    
    
    private int vratPosledniIndexPlatneVetve(int[][] pathStartEndPole){
        
        int dataStart;
        int posledniIndex;
        
        posledniIndex = -1;
        
        for (int i = 0; i < pathStartEndPole.length; i++) {
            
            dataStart = pathStartEndPole[i][0];
            
            if(dataStart == -1){
                break;
            }
            
            posledniIndex = i;
            
        }
        
        return(posledniIndex);
        
    }
            
    
    
    private String[] nakresliVetev(int[] dataVetve){
        
        int pocetRadku;
        int zacatekVetve;
        int prvniOdbocka;
        
        String znakVetve;
        
        zacatekVetve = dataVetve[0];
        prvniOdbocka = dataVetve[1];
        pocetRadku = dataVetve[2];
        
        String[] radkyVetve =  new String[pocetRadku];
        
        for (int i = 0; i < pocetRadku; i++) {
            znakVetve = "";
            
            if(i == zacatekVetve){
                znakVetve = "┐ ";
            }
            else{
                if(i > zacatekVetve){
                    if(i < prvniOdbocka){
                       znakVetve = "│ ";
                    }
                    else{
                       if(i >= prvniOdbocka){
                           znakVetve = "├─";
                       }
                       if(i == pocetRadku-1){
                           znakVetve = "└─";
                       }
                    }
                }
                else{
                    znakVetve = "  ";
                }
            }
             
            radkyVetve[i] = znakVetve;
            
        }

     
        return(radkyVetve);
        
    }
    
    /*
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
    
    */
    
}
