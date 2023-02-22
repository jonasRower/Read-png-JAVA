
package obrazek;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;


public class sousediciSouradnice {
    
    ArrayList<HashMap<String, Integer>> souradniceVsechBarev = new ArrayList<HashMap<String, Integer>>();
    ArrayList<HashMap<String, Integer>> sousediJinaBarva = new ArrayList<HashMap<String, Integer>>();
    ArrayList<HashMap<String, Integer>> sousediStejnaBarva = new ArrayList<HashMap<String, Integer>>();
    
    private boolean vychodZapsan = false;
    private boolean jihZapsan = false;
    private boolean zapadZapsan = false;
    private boolean severZapsan = false;
    private boolean sousedStejneBarvyZapsan = false;
    
    private int sousedPredchoziRed = -1;
    private int sousedPredchoziGreen = -1;
    private int sousedPredchoziBlue = -1;
    private int PredchoziSousedIndex = 0;
    
    public sousediciSouradnice(ArrayList<HashMap<String, Integer>> souradniceVsechBarev){
        
        this.souradniceVsechBarev = souradniceVsechBarev;
        
        int redHlavni;
        int greenHlavni;
        int blueHlavni;
        int xHlavni;
        int yHlavni;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            
            HashMap<String, Integer> RGBhlavni = new HashMap<String, Integer>();
            
            RGBhlavni = souradniceVsechBarev.get(i);
            
            redHlavni = RGBhlavni.get("Red");
            greenHlavni = RGBhlavni.get("Green");
            blueHlavni = RGBhlavni.get("Blue");
            
            xHlavni = RGBhlavni.get("x");
            yHlavni = RGBhlavni.get("y");
            
            nalezniSousedyJineBarvy(i, redHlavni, greenHlavni, blueHlavni, xHlavni, yHlavni);
            zapisSousedaSeStejnouBarvou(redHlavni, greenHlavni, blueHlavni, xHlavni, yHlavni, i);
            
        }
    }
   
    public ArrayList<HashMap<String, Integer>> getSousediJinaBarva(){
        
        return(sousediJinaBarva);
        
    }
    
    public ArrayList<HashMap<String, Integer>> getSousediStejnaBarva(){
        
        return(sousediStejnaBarva);
        
    }
    
    //prohleda sever, jih, vychod, zapad dane polozky a zapise data bud do pole:
    //rozdilny soused, nebo
    //stejny soused
    //
    //rozdilny nebo stejny se rozlisuje podle barvy souseda
    private void nalezniSousedyJineBarvy(int index, int redHlavni, int greenHlavni, int blueHlavni, int xHlavni, int yHlavni){
        
        HashMap<String, Integer> RGBsoused = new HashMap<String, Integer>();
        
        //snazim se vyhledavat efektivnim zpusobem, tzn.
        //vim ze pozadovane xSoused a ySoused jsou nekde v okoli xHlavni a yHlavni
        //proto hledam cik-cak s pocatkem od bodu daneho indexu
        
        int i_nad;
        int i_pod;
        
        i_nad = index;
        i_pod = index;  
        
        vychodZapsan = false;
        jihZapsan = false;
        zapadZapsan = false;
        severZapsan = false;

           
        boolean pokracuj;
        
        for (int i = 0; i < souradniceVsechBarev.size(); i++) {
            
            //hleda od hlavni souradnice nahoru
            i_nad = i_nad + 1;
            
            if(i_nad < souradniceVsechBarev.size()){
                nalezniSousedyVyhledavani(i_nad, redHlavni, greenHlavni, blueHlavni, xHlavni, yHlavni);
            }
          
            //hleda od hlavni souradnice dolu
            i_pod = i_pod - 1;
            
            if(i_pod > -1){
                nalezniSousedyVyhledavani(i_pod, redHlavni, greenHlavni, blueHlavni, xHlavni, yHlavni);
            }
            
            pokracuj = pokracujVeVyhledavani();
            
            if(pokracuj == false){
                break;
            }
        } 
        
        System.out.print("");
    }
    
    private void nalezniSousedyVyhledavani(int index, int redHlavni, int greenHlavni, int blueHlavni, int xHlavni, int yHlavni){
        
        int xSoused;
        int ySoused;
        
        int rSoused;
        int gSoused;
        int bSoused;
        
        boolean jednaSeOSouseda;
        boolean stejnaBarvaSouseda;
        String orientaceSouseda;
        
        
        HashMap<String, Integer> RGBsoused = new HashMap<String, Integer>();
        RGBsoused = souradniceVsechBarev.get(index);

        xSoused = RGBsoused.get("x");
        ySoused = RGBsoused.get("y");
        
        ukonciVyhledavani(yHlavni, ySoused);

        jednaSeOSouseda = detekujZdaSeJednaOSouseda(xHlavni, xSoused, yHlavni, ySoused);

        if(jednaSeOSouseda == true){

            //detekuje, zda se jedna o stejnou barvu souseda
            rSoused = RGBsoused.get("Red");
            gSoused = RGBsoused.get("Green");
            bSoused = RGBsoused.get("Blue");

            stejnaBarvaSouseda = detekujZdaSeJednaOStejnouBarvu(redHlavni, greenHlavni, blueHlavni, rSoused, gSoused, bSoused);
            orientaceSouseda = detekujOrientaciSouseda(xHlavni, xSoused, yHlavni, ySoused);
            
            
            if(stejnaBarvaSouseda == false){
                zapisSousedaSJinouBarvou(redHlavni, greenHlavni, blueHlavni, xHlavni, yHlavni, rSoused, gSoused, bSoused, xSoused, ySoused, orientaceSouseda);
            }
            else{
                if(sousedStejneBarvyZapsan == false){
                    //zapisSousedaSeStejnouBarvou(redHlavni, greenHlavni, blueHlavni, xHlavni, yHlavni, index);
                    sousedStejneBarvyZapsan = true;
                }
            }
            
            System.out.print("");
            
        } 
        
        System.out.print("");
        
    }
    
    
    //detekuje zda pokracovat ve vyhledavani, nebo nikoliv
    private boolean pokracujVeVyhledavani(){
        
        boolean dalVyhledavej = true;
        
        if(vychodZapsan == true){
            if(jihZapsan == true){
                if(zapadZapsan == true){
                    if(severZapsan == true){
                        dalVyhledavej = false;
                    }
                }
            }
        }
    
        return(dalVyhledavej);
    }
    
    
    //aby nebezel prilis dlouho, ukonci vyhledavani, pokud je rozdil y-souradnice pixelu > 2
    //nastavi vsechny smery zapsanych hodnot na true a tim vynuti ukonceni
    private void ukonciVyhledavani(int yHlavni, int ySoused){
        
        int yRozdil;
        yRozdil = abs(yHlavni - ySoused);
        
        if(yRozdil > 2){
            vychodZapsan = true;
            jihZapsan = true;
            zapadZapsan = true;
            severZapsan = true;
        }

    }
            
           
    
    private void zapisSousedaSJinouBarvou(int rHlavni, int gHlavni, int bHlavni, int xHlavni, int yHlavni, int rSoused, int gSoused, int bSoused, int xSoused, int ySoused, String orientace){
        
        HashMap<String, Integer> RGBsousedJineBarvy = new HashMap<String, Integer>();
        
        //prevadi orientaci na int, aby ji mohl zapsat
        int orientaceInt = -1;
        
        if(orientace == "vychod"){orientaceInt = 1;}
        if(orientace == "jih"){orientaceInt = 2;}
        if(orientace == "zapad"){orientaceInt = 3;}
        if(orientace == "sever"){orientaceInt = 4;}
        
        
        RGBsousedJineBarvy.put("r_Hlavni", rHlavni);
        RGBsousedJineBarvy.put("g_Hlavni", gHlavni);
        RGBsousedJineBarvy.put("b_Hlavni", bHlavni);
        
        RGBsousedJineBarvy.put("x_Hlavni", xHlavni);
        RGBsousedJineBarvy.put("y_Hlavni", yHlavni);
        
        RGBsousedJineBarvy.put("r_Soused", rSoused);
        RGBsousedJineBarvy.put("g_Soused", gSoused);
        RGBsousedJineBarvy.put("b_Soused", bSoused);
        
        RGBsousedJineBarvy.put("x_Soused", xSoused);
        RGBsousedJineBarvy.put("y_Soused", ySoused);
        
        RGBsousedJineBarvy.put("orientace souseda", orientaceInt);
        
        sousediJinaBarva.add(RGBsousedJineBarvy);
        System.out.print("");
        
    }
    
    
    private void zapisSousedaSeStejnouBarvou2(int rHlavni, int gHlavni, int bHlavni, int xHlavni, int yHlavni){
        
        HashMap<String, Integer> RGBsousedStejneBarvy = new HashMap<String, Integer>();
        
        RGBsousedStejneBarvy.put("r_Hlavni", rHlavni);
        RGBsousedStejneBarvy.put("g_Hlavni", gHlavni);
        RGBsousedStejneBarvy.put("b_Hlavni", bHlavni);
        
        RGBsousedStejneBarvy.put("x_Hlavni", xHlavni);
        RGBsousedStejneBarvy.put("y_Hlavni", yHlavni);
        
        //sousediStejnaBarva.add(RGBsousedStejneBarvy);
       
    }
    
    
    private void zapisSousedaSeStejnouBarvou(int rHlavni, int gHlavni, int bHlavni, int xHlavni, int yHlavni, int index){
        
        HashMap<String, Integer> RGBsousedStejneBarvy;
        boolean zmena = false;
        int indexZacatek;
        int indexKonec;
        
        if(index == 0){
            sousedPredchoziRed = rHlavni;
            sousedPredchoziGreen = gHlavni;
            sousedPredchoziBlue = bHlavni;
        } 
        else {
            
            //prepise barvy
            if(rHlavni != sousedPredchoziRed){
                zmena = true;
            }
            if(gHlavni != sousedPredchoziGreen){
                zmena = true;
            }
            if(bHlavni != sousedPredchoziBlue){
                zmena = true;
            }
            
            if(zmena == true){
                indexZacatek = PredchoziSousedIndex;
                indexKonec = index;
                PredchoziSousedIndex = indexKonec;

                //prida data do pole
                RGBsousedStejneBarvy = new HashMap<String, Integer>();

                RGBsousedStejneBarvy.put("index_Zacatek", indexZacatek);
                RGBsousedStejneBarvy.put("index_Konec", indexKonec);

                RGBsousedStejneBarvy.put("r_Hlavni", sousedPredchoziRed);
                RGBsousedStejneBarvy.put("g_Hlavni", sousedPredchoziGreen);
                RGBsousedStejneBarvy.put("b_Hlavni", sousedPredchoziBlue);

                sousediStejnaBarva.add(RGBsousedStejneBarvy);
                
                //prepise barvy
                sousedPredchoziRed = rHlavni;
                sousedPredchoziGreen = gHlavni;
                sousedPredchoziBlue = bHlavni;
                
                System.out.println();
            }
            
        }
        
        System.out.println();

    }
    
    
    
    //rozhodne zda soused je na severum jihu, zapade, vychode
    private String detekujOrientaciSouseda(int xHlavni, int xSoused, int yHlavni, int ySoused){
        
        String orientaceSouseda = null;
        
        if(yHlavni == ySoused){
            if(xHlavni < xSoused){
                orientaceSouseda = "vychod";
                vychodZapsan = true;
            }
            else {
                orientaceSouseda = "zapad";
                zapadZapsan = true;
            }
        }
        
        if(xHlavni == xSoused){
             if(yHlavni < ySoused){
                orientaceSouseda = "sever";
                severZapsan = true;
            }
            else {
                orientaceSouseda = "jih";
                jihZapsan = true;
            }
        }
        
        return(orientaceSouseda);
      
    }
    
    private boolean detekujZdaSeJednaOSouseda(int xHlavni, int xSoused, int yHlavni, int ySoused){
        
        boolean jednaSeOSousedaX = false;
        boolean jednaSeOSousedaY = false;
        boolean jednaSeOSouseda = false;
        
        if(abs(yHlavni - ySoused) == 0){
            if(abs(xHlavni - xSoused) == 1){
                jednaSeOSousedaX = true;
                jednaSeOSouseda = true;
            }
        }
    
        if(abs(xHlavni - xSoused) == 0){
            if(abs(yHlavni - ySoused) == 1){
                jednaSeOSousedaY = true;
                jednaSeOSouseda = true;
            }
        }
        
        //jedna-li se o souseda v obou smerech, pak to je rohovy soused
        //rohoveho souseda vsak neuvazuji, takze soused to neni
        if(jednaSeOSousedaX == true){
            if(jednaSeOSousedaY == true){
                jednaSeOSouseda = false;
            }
        }
    
        return(jednaSeOSouseda);
        
    }
    
    private boolean detekujZdaSeJednaOStejnouBarvu(int rHlavni, int gHlavni, int bHlavni, int rSoused, int gSoused, int bSoused){
        
        boolean stejnaBarva = false;
        
        if(rHlavni == rSoused){
            if(rHlavni == rSoused){
                if(rHlavni == rSoused){
                    stejnaBarva = true;
                }
            }
        }
        
        return (stejnaBarva);
        
    }
    
}
