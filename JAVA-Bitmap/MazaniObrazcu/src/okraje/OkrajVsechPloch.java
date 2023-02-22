//tady je asi chyba
//SousedVychodVsechnyPlochySOtvory ma stejne souradnice pro obe plochy, zatimco 
//SousedVychodVsechnyPlochy je nema stejne


package okraje;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashMap;
import vektorycar.OkrajKolemDokola;
import vektorycar.Otvory;


public class OkrajVsechPloch {
    
    //jedna se o sousedy na hranici (tzn. sousediJinaBarva) avsak pouze jedne barvy hlavni
    //proto, že je volano ze smycky roztridSousediPodleBarvy ve tride RuzneBarvySousedu
    private ArrayList<HashMap<String, Integer>> sousediJinaBarva = new ArrayList<HashMap<String, Integer>>();
    
    //uchovava data na jakou stranu jsou orientovani sousedi
    private ArrayList<HashMap<String, Integer>> sousedVychod = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedJih = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedZapad = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedSever = new ArrayList<HashMap<String, Integer>>();
    
    //data na vsech plochach
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedVychodVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedJihVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedZapadVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedSeverVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //rohove pixely na vsech plochach
    private ArrayList<ArrayList<HashMap<String, Integer>>> rohovePixelyVsechnyPlochy = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    //uchovava data, ktera jeste neroztridil
    private ArrayList<HashMap<String, Integer>> sousedVychodNeroztrizene = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedJihNeroztrizene = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedZapadNeroztrizene = new ArrayList<HashMap<String, Integer>>();
    private ArrayList<HashMap<String, Integer>> sousedSeverNeroztrizene = new ArrayList<HashMap<String, Integer>>();
    
    //------------------------------------------------------------------------------------------------------------------
    //data, vcetne dat s otvory
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedVychodVsechnyPlochySOtvory = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedJihVsechnyPlochySOtvory = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedZapadVsechnyPlochySOtvory = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private ArrayList<ArrayList<HashMap<String, Integer>>> sousedSeverVsechnyPlochySOtvory = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    
    
    //konstruktor stary, nebudu ho asi pouzivat
    public OkrajVsechPloch(ArrayList<HashMap<String, Integer>> sousediJinaBarva){
        
        this.sousediJinaBarva = sousediJinaBarva;

        //-------------------------------------------------------------------------------------------------------
        //vykonava metody
        roztridPodleOrientace();
        zapisDataDoVsechPloch();  
        ziskejDataSOtvory();
        
        
        System.out.print("");
        
    }
    
    
    public OkrajVsechPloch(ArrayList<HashMap<String, Integer>> sousedVychod, ArrayList<HashMap<String, Integer>> sousedJih, ArrayList<HashMap<String, Integer>> sousedZapad, ArrayList<HashMap<String, Integer>> sousedSever){
        
        this.sousedVychod = sousedVychod;
        this.sousedJih = sousedJih;
        this.sousedZapad = sousedZapad;
        this.sousedSever = sousedSever;
        
        //ulozi data do dat neroztridenych, data originalni jsou pro testovani
        sousedVychodNeroztrizene = sousedVychod;
        sousedJihNeroztrizene = sousedJih;
        sousedZapadNeroztrizene = sousedZapad;
        sousedSeverNeroztrizene = sousedSever;
        
        zapisDataDoVsechPloch();  
        ziskejDataSOtvory();
        
        System.out.print("");
                
    }
    
    
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedyVychodVsechnyPlochy(){
        return(sousedVychodVsechnyPlochySOtvory);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedyJihVsechnyPlochy(){
        return(sousedJihVsechnyPlochySOtvory);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedyZapadVsechnyPlochy(){
        return(sousedZapadVsechnyPlochySOtvory);
    }
    
    public ArrayList<ArrayList<HashMap<String, Integer>>> getSousedySeverVsechnyPlochy(){
        return(sousedSeverVsechnyPlochySOtvory);
    }
    
    
    private void ziskejDataSOtvory(){
        
        //Detekuje otvory
        Otvory otvorVPlose = new Otvory(sousedVychodVsechnyPlochy, sousedJihVsechnyPlochy, sousedZapadVsechnyPlochy, sousedSeverVsechnyPlochy, rohovePixelyVsechnyPlochy);
        
        sousedVychodVsechnyPlochySOtvory = otvorVPlose.getSousedVychodVsechnyPlochySloucene();
        sousedJihVsechnyPlochySOtvory = otvorVPlose.getSousedJihVsechnyPlochySloucene();
        sousedZapadVsechnyPlochySOtvory = otvorVPlose.getSsousedZapadVsechnyPlochySloucene();
        sousedSeverVsechnyPlochySOtvory = otvorVPlose.getSousedSeverVsechnyPlochySloucene();
                
        System.out.print("");
             
    }
    
    
    private void zapisDataDoVsechPloch(){
        
        boolean pokracujVHledani;
        OkrajKolemDokola okrajDokola = null;
        
        //data na jedne plose
        ArrayList<HashMap<String, Integer>> sousedVychodJednaPlocha;
        ArrayList<HashMap<String, Integer>> sousedJihJednaPlocha;
        ArrayList<HashMap<String, Integer>> sousedZapadJednaPlocha;
        ArrayList<HashMap<String, Integer>> sousedSeverJednaPlocha;
    
        //rohovePixely na jedne plose
        ArrayList<HashMap<String, Integer>> rohovePixelyJednaPlocha;
       
        
        for (int i = 0; i < 1000; i++) {
            
            //inicializuje data
            sousedVychodJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            sousedJihJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            sousedZapadJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            sousedSeverJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            rohovePixelyJednaPlocha = new ArrayList<HashMap<String, Integer>>();
            

            okrajDokola = new OkrajKolemDokola(sousedVychodNeroztrizene, sousedJihNeroztrizene, sousedZapadNeroztrizene, sousedSeverNeroztrizene);

            //roztridi data na jednu plochu
            sousedVychodJednaPlocha = okrajDokola.getDataNaJednePloseVychod();
            sousedJihJednaPlocha = okrajDokola.getDataNaJednePloseJih();
            sousedZapadJednaPlocha = okrajDokola.getDataNaJednePloseZapad();
            sousedSeverJednaPlocha = okrajDokola.getDataNaJednePloseSever();
            
            //ziska rohovePixely
            rohovePixelyJednaPlocha = okrajDokola.getRohovePixelyNaJednePlose();
            
           
            //zapise data na vsechny plochy
            sousedVychodVsechnyPlochy.add(sousedVychodJednaPlocha);
            sousedJihVsechnyPlochy.add(sousedJihJednaPlocha);
            sousedZapadVsechnyPlochy.add(sousedZapadJednaPlocha);
            sousedSeverVsechnyPlochy.add(sousedSeverJednaPlocha);
            
            //zapise rohove pixely na vsechny plochy
            rohovePixelyVsechnyPlochy.add(rohovePixelyJednaPlocha);
            

            //zapise neroztridena data
            sousedVychodNeroztrizene = okrajDokola.getDataNezapsaneVychod();
            sousedJihNeroztrizene = okrajDokola.getDataNezapsaneJih();
            sousedZapadNeroztrizene = okrajDokola.getDataNezapsaneZapad();
            sousedSeverNeroztrizene = okrajDokola.getDataNezapsaneSever();

            //vraci true, pokud nezustane ani jeden pixel navic
            //pokud zustane nejaky bordel navic, nutno zvazit, zda metodu nezmenit
            pokracujVHledani = detekujUkonceniHledani();
            
            if(pokracujVHledani == false){
                break;
            }
        }
        
        System.out.print("");
        
    }
   
    
    private void roztridPodleOrientace(){
        
        int orientaceSouseda;
        HashMap<String, Integer> Soused;
       
        for (int i = 0; i < sousediJinaBarva.size(); i++) {
            
            Soused = new HashMap<String, Integer>();
            Soused = sousediJinaBarva.get(i);
            
            orientaceSouseda = Soused.get("orientace souseda");
            
            //je-li soused orientovan na vychod
            if(orientaceSouseda == 1){
                sousedVychod.add(Soused);
            }
            
            //je-li soused orientovan na jih
            if(orientaceSouseda == 2){
                sousedJih.add(Soused);
            }
            
            //je-li soused orientovan na zapad
            if(orientaceSouseda == 3){
                sousedZapad.add(Soused);
            }
            
            //je-li soused orientovan na sever
            if(orientaceSouseda == 4){
                sousedSever.add(Soused);
            }
            
        }
        
        //ulozi data do dat neroztridenych, data originalni jsou pro testovani
        sousedVychodNeroztrizene = sousedVychod;
        sousedJihNeroztrizene = sousedJih;
        sousedZapadNeroztrizene = sousedZapad;
        sousedSeverNeroztrizene = sousedSever;
                
                
        System.out.print("");
    }
    
    
    private boolean detekujUkonceniHledani(){
        
        int sizeVychod;
        int sizeJih;
        int sizeZapad;
        int sizeSever;
        int size;
        boolean pokracovatVHledani;
        
        sizeVychod = sousedVychodNeroztrizene.size();
        sizeJih = sousedJihNeroztrizene.size();
        sizeZapad = sousedZapadNeroztrizene.size();
        sizeSever = sousedSeverNeroztrizene.size();
        
        size = sizeVychod + sizeJih + sizeZapad + sizeSever;
        if(size == 0){
            pokracovatVHledani = false;
        }
        else {
            pokracovatVHledani = true;
        }
        
        return(pokracovatVHledani);
    }
    
}
