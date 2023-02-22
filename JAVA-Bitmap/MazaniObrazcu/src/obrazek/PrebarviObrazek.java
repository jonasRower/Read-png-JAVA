
package obrazek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static jdk.nashorn.internal.objects.NativeMath.max;
import ruzneBarvy.RuzneBarvySousedu;
import testData.TestData;


public class PrebarviObrazek {

private ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>> svisleOkrajeVsechnyPlochyVsechnyBarvy = new ArrayList<ArrayList<ArrayList<HashMap<String, Integer>>>>();
private ArrayList<ArrayList<HashMap<String, Integer>>> sousediStejnaBarva = new ArrayList<ArrayList<HashMap<String, Integer>>>();
private ArrayList<HashMap<String, Integer>> poleVsechBarev = new ArrayList<HashMap<String, Integer>>();
private ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
private ArrayList<HashMap<String, Integer>> souradniceVsechBarevPrebarvene = new ArrayList<HashMap<String, Integer>>();
private ArrayList<ArrayList<Boolean>> statusVsechnyPlochyVsechnyBarvy = new ArrayList<ArrayList<Boolean>>();

    public PrebarviObrazek(RuzneBarvySousedu barvySousedu, ArrayList<HashMap<String, Integer>> poleVsechBarev, ArrayList<HashMap<String, Integer>> souradniceVsechBarev) throws IOException {
        
        this.sousediStejnaBarva = barvySousedu.getSousediStejnaBarvaPodleBarev();
        this.svisleOkrajeVsechnyPlochyVsechnyBarvy = barvySousedu.getSvisleOkrajeVsechnyBarvy();
        this.poleVsechBarev = poleVsechBarev;
        this.souradniceVsechBarev = souradniceVsechBarev;
        this.statusVsechnyPlochyVsechnyBarvy = barvySousedu.getStatusy();
        
        
        vratPrebarveneSouranice();
        
        TestData test = new TestData(souradniceVsechBarevPrebarvene);
        test.TiskDoPng();
        
        System.out.println("");
        
    }
    
    
    private void vratPrebarveneSouranice(){
        
        int rPozadovane = 0;
        int gPozadovane = 0;
        int bPozadovane = 0;
        
        int IndexHledanaBarva;
        int nejblizsiIndexProZapis;
        int iInterval = 0;
        int hledejOdIndexu = 0;
        
        int indexZacatek;
        int indexKonec;
        boolean pixelNalezen = false;
        
        int x;
        int y;
        
        int r;
        int g;
        int b;
        
        //rgb pro ktere se bude prochazet cyklem,
        //aby kod nebezel prilis dlouho - u tech ostatnich barev
        int rProchazet = -1;
        int gProchazet = -1;
        int bProchazet = -1;
        
        boolean jeToPozadovanaBarva;
        
        HashMap<String, Integer> nejblizsiInterval;
        HashMap<String, Integer> souradnice;
        HashMap<String, Integer> souradniceNew;
        souradniceVsechBarevPrebarvene = souradniceVsechBarev;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            
            if(i == 30){
                System.out.print("");
            }
                    
            nejblizsiInterval = new HashMap<String, Integer>();
            IndexHledanaBarva = vratIndexPodlePozadovanehoRGB(rPozadovane, gPozadovane, bPozadovane);
            nejblizsiInterval = vratNejblizsiInterval(IndexHledanaBarva, hledejOdIndexu);
            souradnice = souradniceVsechBarev.get(i);
            
            x = souradnice.get("x");
            y = souradnice.get("y");
            
            //pokud se jedna o pozadovanou barvu, pak prochazi cyklem, jinak ne - aby usetril smycky
            //a rovnou nastavi pixelNalezen = true;
            jeToPozadovanaBarva = jednaSeOPozadovanouBarvu(souradnice, rProchazet, gProchazet, bProchazet);
            

            indexZacatek = nejblizsiInterval.get("index_Zacatek");
            indexKonec = nejblizsiInterval.get("index_Konec");
            
            
            if(i >= indexZacatek){
                if(i <= indexKonec){
                    
                    pixelNalezen = detekujZdaPixelJeMeziSvislymiOkraji(x, y);
                    //pixelNalezen = detekujZdaPixelJeMeziSvislymiOkraji(x, y);
                    
                    jeToPozadovanaBarva = true;
                    //smyckou prochazi jen v pripade predem definovane barvy
                    if(jeToPozadovanaBarva == true){
                        pixelNalezen = detekujZdaPixelJeMeziSvislymiOkraji(x, y);
                        System.out.print("");
                    }
                    else {  //jinak nastavi rovnou na true a smyckou neprochazi (jen aby usetril cykly)
                        pixelNalezen = true;
                    }
                    
                    
                    //v obou pripadech prebarvi pixely, rozdil je jen v rychlosti
                    if(pixelNalezen == true){
                        souradniceNew = new HashMap<String, Integer>();
                        souradniceNew = vratSouradniceNew(souradnice, true);
                        souradniceVsechBarevPrebarvene.set(i, souradniceNew);
                    }
                }
            }
            
            if(i == indexKonec){
                hledejOdIndexu = indexKonec;
            } 
            
            if(i == 30){
                System.out.print("");
            }
            
        }
        
        System.out.print("");
    }
    
    
    private HashMap<String, Integer> vratSouradniceNew(HashMap<String, Integer> souradnice, boolean prebarvit){
        
        HashMap<String, Integer> souradniceNew = new HashMap<String, Integer>();
        int rNew;
        int gNew;
        int bNew;
        
        int x;
        int y;
        
        if(prebarvit == true){
            rNew = 255;
            gNew = 255;
            bNew = 255;
        }
        else {
            rNew = souradnice.get("Red");
            gNew = souradnice.get("Green");
            bNew = souradnice.get("Blue"); 
        }
        
        x = souradnice.get("x");
        y = souradnice.get("y"); 
        
        souradniceNew.put("Red", rNew);
        souradniceNew.put("Green", gNew);
        souradniceNew.put("Blue", bNew);
        souradniceNew.put("x", x);
        souradniceNew.put("y", y);
        
        
        return(souradniceNew);
        
    }
    
    
    private int vratIndexPodlePozadovanehoRGB(int rPozadovane, int gPozadovane, int bPozadovane){
        
        HashMap<String, Integer> barva;
        int IndexHledanaBarva = - 1;
        
        int r;
        int g;
        int b;
        
        for (int i = 0; i < poleVsechBarev.size(); i++) {
            barva = new HashMap<String, Integer>();
            barva = poleVsechBarev.get(i);
            r = barva.get("Red");
            g = barva.get("Green");
            b = barva.get("Blue");
            
            if(r == rPozadovane){
               if(g == gPozadovane){
                   if(b == bPozadovane){
                       IndexHledanaBarva = i;
                   }
               } 
            }
        }
        
        return(IndexHledanaBarva);
        
    }
    
    
    private HashMap<String, Integer> vratNejblizsiInterval(int IndexHledanaBarva, int indexPredchozi){
        
        ArrayList<HashMap<String, Integer>> sousediStejnaBarvaPodleBarvy;
        HashMap<String, Integer> sousediStejnaBarvaInterval = null;
        
        int indexZacatek = -1;
        int indexKonec = -1;
        
        int[] iIntervalBarvy = new int[sousediStejnaBarva.size()];
        int[] indexZacatekBarvy = new int[sousediStejnaBarva.size()];
        
        boolean zastavitSmycky = false;
        
        for (int iBarva = 0; iBarva < sousediStejnaBarva.size(); iBarva++) {
            sousediStejnaBarvaPodleBarvy = new ArrayList<HashMap<String, Integer>>();
            sousediStejnaBarvaPodleBarvy = sousediStejnaBarva.get(iBarva);
            
            for (int iInterval = 0; iInterval < sousediStejnaBarvaPodleBarvy.size(); iInterval++) {
                sousediStejnaBarvaInterval = new HashMap<String, Integer>();
                sousediStejnaBarvaInterval = sousediStejnaBarvaPodleBarvy.get(iInterval);
                
                indexZacatek = sousediStejnaBarvaInterval.get("index_Zacatek");
                indexKonec = sousediStejnaBarvaInterval.get("index_Konec");
                
                if(indexPredchozi < indexKonec){
                    zastavitSmycky = true;
                    iIntervalBarvy[iBarva] = iInterval;
                    indexZacatekBarvy[iBarva] = indexZacatek;
                    
                    //vynecha, pokud je = indexPredchozi
                    if(indexPredchozi > 0){
                        if(indexZacatekBarvy[iBarva] == indexPredchozi){
                            indexZacatekBarvy[iBarva] = -1;
                        }
                    }
                    break;
                }
            }
        }
        
        sousediStejnaBarvaInterval = najdiNejblizsiIntervalZViceBarev(iIntervalBarvy, indexZacatekBarvy);
        
       
        return(sousediStejnaBarvaInterval);
        
    }
    
    
    private HashMap<String, Integer> najdiNejblizsiIntervalZViceBarev(int[] iIntervalBarvy, int[] indexZacatekBarvy){
        
        int indexZacatek;
        int indexZacatekMin;
        int indexZacatekMax;
        int interval;
        int index = 0;
        
        ArrayList<HashMap<String, Integer>> sousediStejnaBarvaPodleBarvy = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> sousediStejnaBarvaInterval = new HashMap<String, Integer>();
        
        //zjisti maximalni hodnotu
        indexZacatekMax = indexZacatekBarvy[0];
        for (int i = 1; i < indexZacatekBarvy.length; i++) {
            
            if(indexZacatekMax < indexZacatekBarvy[i]){
                indexZacatekMax = indexZacatekBarvy[i];
            }
        }
            
        
        indexZacatekMin = indexZacatekMax;
        
        for (int i = 0; i < indexZacatekBarvy.length; i++) {
            indexZacatek = indexZacatekBarvy[i];
            if(indexZacatekMin > indexZacatek){
                indexZacatekMin = indexZacatek;
                index = i;
            }
        }
        
        interval = iIntervalBarvy[index];
        sousediStejnaBarvaPodleBarvy = sousediStejnaBarva.get(index);
        sousediStejnaBarvaInterval = sousediStejnaBarvaPodleBarvy.get(interval);
        
        return(sousediStejnaBarvaInterval);
        
    }
    
    
    private boolean detekujZdaPixelJeMeziSvislymiOkraji(int xPozadovany, int yPozadovany){
        
        int xOkrajVychod = -1;
        int xOkrajZapad = -1;
        int yOkraj;
        
        boolean pixelNalezen = false;
        
        //data pro testovani
        int iBarvaTrue = -1;
        int iPlochaTrue = -1;
        int iOkrajTrue = -1;
        boolean status = false;
        
        
        HashMap<String, Integer> dataOkraj;
        ArrayList<HashMap<String, Integer>> dataPlocha; 
        ArrayList<ArrayList<HashMap<String, Integer>>> dataBarva;
        ArrayList<Boolean> statusBarva;
        
        for (int iBarva = 0; iBarva < svisleOkrajeVsechnyPlochyVsechnyBarvy.size(); iBarva++) {
            dataBarva = new ArrayList<ArrayList<HashMap<String, Integer>>>();
            statusBarva = new ArrayList<Boolean>();
            dataBarva = svisleOkrajeVsechnyPlochyVsechnyBarvy.get(iBarva);
            statusBarva = statusVsechnyPlochyVsechnyBarvy.get(iBarva);
            
            for (int iPlocha = 0; iPlocha < dataBarva.size(); iPlocha++) {
                dataPlocha = new ArrayList<HashMap<String, Integer>>();
                dataPlocha = dataBarva.get(iPlocha);
                status = statusBarva.get(iPlocha);
                
                //status = false;
                
               
                
                if(status == true){
                    
                    if(xPozadovany == 8){
                        System.out.print("");
                    }
                   
                    for (int iOkraj = 0; iOkraj < dataPlocha.size(); iOkraj++) {
                        dataOkraj = new HashMap<String, Integer>();
                        dataOkraj = dataPlocha.get(iOkraj);
                        yOkraj = dataOkraj.get("y_vychod");

                        if(yOkraj == yPozadovany){
                            xOkrajVychod = dataOkraj.get("x_vychod");
                            xOkrajZapad = dataOkraj.get("x_zapad");
                            if(xPozadovany >= xOkrajVychod){
                                if(xOkrajZapad >= xPozadovany){
                                    pixelNalezen = true;
                                    iOkrajTrue = iOkraj;
                                    break;
                                }
                            }    
                        }
                    }
                }
                
                if(pixelNalezen == true){
                    iPlochaTrue = iPlocha;
                    break;
                }
            }
            
            if(pixelNalezen == true){
                iBarvaTrue = iBarva;
                break;
            }
        }
        
        return(pixelNalezen);

    }
    
    
    private boolean jednaSeOPozadovanouBarvu(HashMap<String, Integer> rgb, int rPozadovana, int gPozadovana, int bPozadovana){
        
        int r;
        int g;
        int b;
        
        boolean jeToPozadovanaBava = false;
        
        r = rgb.get("Red");
        g = rgb.get("Green");
        b = rgb.get("Blue");
        
        if(r == rPozadovana){
            if(g == gPozadovana){
                if(b == bPozadovana){
                    jeToPozadovanaBava = true;
                }
            }
        }
        
        return(jeToPozadovanaBava);
        
    }

   
}
