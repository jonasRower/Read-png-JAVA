
package logData;

//ziska kompletni data do logu, tak ze spoji strom s polem radku
//spoji data a provede finalni upravy

import java.util.ArrayList;
import java.util.HashMap;

public class logData {
    
    String[] spojenaData;
    ArrayList<Integer> indexyRadkuKOprave = new ArrayList<>();

    logData(String[] vetveLog, ArrayList<String> poleRadku) {
        


        //opravi "┐┐", za "┬┐"
        vetveLog = opravDvojiteOdbockyVetvicek(vetveLog);
        
        //opravi "├─                    ┬─┐ ┬─┐ ┬─┐" tim ze doplni vodorovne vetvicky
        vetveLog = opravChybejiciVodorovneVetvicky(vetveLog);
        

        spojenaData = spojVetveLogAPoleRadku(vetveLog, poleRadku);
        spojenaData = opravVetvickyUDatUrcenychKOprave(spojenaData, indexyRadkuKOprave);
        
    }
    
    
    public String[] getSpojenaData(){
        return(spojenaData);
    }
    
    
    private String[] opravChybejiciVodorovneVetvicky(String[] vetveLog){
        
        
        ArrayList<Integer> indexyRadku = new ArrayList<>();
        indexyRadku = detekujIndexyRadkuKDoplneniVodorovnychVetvicek(vetveLog); 
        
        String radek;
        String radekNew;
        int indexRadku;
        
        for (int i = 0; i < indexyRadku.size(); i++) {
            
            indexRadku = indexyRadku.get(i);
            radek = vetveLog[indexRadku];
            
            radekNew = radek.trim();    
            radekNew = radekNew.replace(" ┬", "─┬");
            radekNew = radekNew.replace("┐─", "┬─");
            radekNew = radekNew.replace(" ", "─");
            
            
            vetveLog[indexRadku] = radekNew;
            
        }
        
        return(vetveLog);
        
    }
    
    
    
    private ArrayList<Integer> detekujIndexyRadkuKDoplneniVodorovnychVetvicek(String[] vetveLog){
        
        String radek;
        boolean radekObsahujeOdbockuDolu;
        
        ArrayList<Integer> indexyRadkuVodorovneVetveChybejici = new ArrayList<>();
        
        for (int i = 0; i < vetveLog.length; i++) {
            radek = vetveLog[i];
            radekObsahujeOdbockuDolu = detekujPritomnostSubStringu(radek, "┬");
            
            if(radekObsahujeOdbockuDolu == true){
                indexyRadkuVodorovneVetveChybejici.add(i);
            }
            
        }
        
        return(indexyRadkuVodorovneVetveChybejici);
        
    }
            
            
            
            
    
    
    private String[] opravDvojiteOdbockyVetvicek(String[] vetveLog){
        
        String radek;
        String radekNew;
        
        for (int i = 0; i < vetveLog.length; i++) {
            
            radek = vetveLog[i];
            radekNew = radek.replace("├─    ┐ ┐", "├─────┐ ┐");
            radekNew = radekNew.replace("┐ ┐", "┬─┐");
            vetveLog[i] = radekNew;
        }
        
        return(vetveLog);
          
    }
    
    
    private String[] opravVetvickyUDatUrcenychKOprave(String[] spojenaData, ArrayList<Integer> indexyRadkuKOprave){
        
        int indexRadkuKOprave;
        String radek;
        String radekNew;
        
        for (int i = 0; i < indexyRadkuKOprave.size(); i++) {
            indexRadkuKOprave = indexyRadkuKOprave.get(i);
            radek = spojenaData[indexRadkuKOprave];
            radekNew = opravRadek(radek);
            
            spojenaData[indexRadkuKOprave] = radekNew;
        }
        
        return(spojenaData);
        
    }
    
    
    private String opravRadek(String radek){
        
        String radekNew;
        radekNew = radek.replace("├─", "│ ");
        
        return(radekNew);
        
    }
    
    
    private String[] spojVetveLogAPoleRadku(String[] vetveLog, ArrayList<String> poleRadku){
        
        String radekVetve;
        String radekPoleRadku;
        String radekNew;
        
        String[] spojenaData = new String[vetveLog.length];
        
        for (int i = 0; i < vetveLog.length; i++) {
            radekVetve = vetveLog[i];
            radekPoleRadku = poleRadku.get(i+1);
            
            //opravi data
            radekPoleRadku = opravRadekPoleRadku(radekPoleRadku, i);
            
            radekNew = radekVetve + radekPoleRadku;
            spojenaData[i] = radekNew;
        }
        
        return(spojenaData);
        
    }
    
    
    String opravRadekPoleRadku(String radekPoleRadku, int indexRadku){
        
        boolean pridejDataDoRadkuKOprave;
        pridejDataDoRadkuKOprave = false;
        
        if(radekPoleRadku == "<-"){
            radekPoleRadku = "";
            pridejDataDoRadkuKOprave = true;
        }
        
        if(radekPoleRadku == "->"){
            radekPoleRadku = "";
            pridejDataDoRadkuKOprave = true;
        }
        
        if(radekPoleRadku == "{}"){
            radekPoleRadku = "";
            pridejDataDoRadkuKOprave = true;
        }
        
        if(pridejDataDoRadkuKOprave == true){
            if(indexRadku > 0){
                indexyRadkuKOprave.add(indexRadku);
            }
        }
        
        return(radekPoleRadku);
        
    }
    
    
    private boolean detekujPritomnostSubStringu(String radek, String subString){
        
        int indOf;
        boolean radekObsahujeSubstring;
        indOf = radek.indexOf(subString);
        
        if(indOf > -1){
            radekObsahujeSubstring = true;
        }
        else{
            radekObsahujeSubstring = false;
        }
        
        return(radekObsahujeSubstring);
        
    }
        
}
