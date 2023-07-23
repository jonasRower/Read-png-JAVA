
package ZdrojovaData;

public class zdrojDataZkoum {
    
    String adresaSlozky;
    String NazevSouboruPng;
    
    public zdrojDataZkoum(String adresaProjektu){
        
        //adresaSlozky = "C:\\Users\\jonas\\OneDrive\\Dokumenty\\JAVA_KCT\\TextReader\\TextReader\\test\\";
        adresaSlozky = adresaProjektu + "\\InputOutput\\pictureInput\\";
        NazevSouboruPng = "testPng.png";
        
    }
    
    public String getAdresaSlozky(){
        
        return (adresaSlozky);
        
    }
    
    public String getNazevSouboruPng(){
        
        return (NazevSouboruPng);
        
    }
    
}
