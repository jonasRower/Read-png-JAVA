//prevede ArrayList na poleRadku
package logData;

import createOutput.pathOfProject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class convertArrayList {
    
    ArrayList<String> poleRadku = new ArrayList<>();
    String nazevTxt;
    
    public convertArrayList(ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> data, String nazevTxt){
        
        this.nazevTxt = nazevTxt;
                
        tiskniListArrayListArrayListHashMap(data);
        dataForTree vytvorDataProStrom = new dataForTree(poleRadku);

        int[][] pathStartEndPole = vytvorDataProStrom.getPathStartEndPole();
        String[] vetveLog;
        String[] spojenaData;
        
        //tiskne log do txt
        kresliVetve vetveVLogu = new kresliVetve(pathStartEndPole);
        vetveLog = vetveVLogu.getVratVetve();
        
        logData ziskejKompletniLog = new logData(vetveLog, poleRadku);
        spojenaData = ziskejKompletniLog.getSpojenaData();
        
        //vytiskne data do logu
        tiskniDoLoguArr(spojenaData);
         
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
        String adresaProjektu;
        String adresaProjektuLog;
        text = prevedArrNaText(poleStromuRadku);
        
        pathOfProject cestaKProjektu = new pathOfProject();
        adresaProjektu = cestaKProjektu.getAdresaProjektu();
        adresaProjektuLog = adresaProjektu + "\\InputOutput\\treeVariables\\" + nazevTxt;
        
        
        try {
                FileWriter myWriter = new FileWriter(adresaProjektuLog);
                myWriter.write(text);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
                
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
          }
        
    }
    
}
