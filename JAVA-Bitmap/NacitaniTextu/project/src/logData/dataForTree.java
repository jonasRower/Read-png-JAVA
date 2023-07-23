
package logData;

import java.util.ArrayList;


public class dataForTree {
    
    int indexPosledniUrovne;
    int[][] pathStartEndPole;
    
    public dataForTree(ArrayList<String> poleRadku){
        
        int poleOdsazeni[];
        String poleStromuRadku[];
        
        int[][] poleVsechIndexuRadku;
        int[][] startEndPole;
        
        poleOdsazeni = ziskejPoleOdsazeni(poleRadku);
        indexPosledniUrovne = vratMaxItem(poleOdsazeni); 
        
        poleVsechIndexuRadku = vratPoleVsechIndexuRadku(poleOdsazeni);
        startEndPole = vratStartEndPolePosledniUrovne(poleVsechIndexuRadku);
        pathStartEndPole = vratCestuStartEnd(startEndPole, poleVsechIndexuRadku);
        
        
        
        
    }
    
    
    public int[][] getPathStartEndPole(){
        return(pathStartEndPole);
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
    
    
    private int[][] vratPoleVsechIndexuRadku(int poleOdsazeni[]){
        
        int[][] poleVsechIndexuRadku = new int[indexPosledniUrovne+1][]; 
        
        for (int i = 0; i < indexPosledniUrovne+1; i++) {
            int[] poleIndexuRadku = vratPoleVsechIndexuRadkuProDaneOdsazeni(poleOdsazeni, i);
            poleVsechIndexuRadku[i] = poleIndexuRadku;
            
        }
        
        return(poleVsechIndexuRadku);
        
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
    
    
    private int[][] vratCestuStartEnd(int[][] startEndPole, int[][] poleVsechIndexuRadku){
        
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
        
        return(pathStartEndPole);
        
        
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
    
    
    
    
}
