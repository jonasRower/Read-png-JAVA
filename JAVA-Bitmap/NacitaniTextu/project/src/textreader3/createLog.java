
package textreader3;

import java.io.File;
import java.io.FileWriter;   
import java.io.IOException;  
import java.util.ArrayList;


public class createLog {
    
    createLog(ArrayList<ArrayList<String>>logArrStr, String adresaProjektu, String nazevLogu){
        
        String adresaProjektuFull;
        adresaProjektuFull = ziskejAdresuKamGenerovat(adresaProjektu, nazevLogu);
        
        String text;
        text = vytvorPoleRadkuTxt(logArrStr);
        
        //vytiskne vystup do csv
        vytiskniVystup(text, adresaProjektuFull);
          
    }
    
    
    private String ziskejAdresuKamGenerovat(String adresaProjektu, String nazevLogu){
        
        String adresaProjektuFull;
        adresaProjektuFull = adresaProjektu + nazevLogu;
    
        return(adresaProjektuFull);
        
    }
    
    
    private String vytvorPoleRadkuTxt(ArrayList<ArrayList<String>>logArrStr){
        
        ArrayList<String> radekArr;
        String hodnota;
        
        String text;
        text = "";
        
        for (int r = 0; r < logArrStr.size(); r++) {
            radekArr = logArrStr.get(r);
            
            for (int s = 0; s < radekArr.size(); s++) {
                hodnota = radekArr.get(s);
                text = text + hodnota + ",";
            }
            
            text = text + "\n";
        }

        return(text);
       
    }
    
    
    private void vytiskniVystup(String text, String adresaProjektuFull){
        
        try {
                FileWriter myWriter = new FileWriter(adresaProjektuFull);
                myWriter.write(text);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
                
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
          }
        
    }
    
}
