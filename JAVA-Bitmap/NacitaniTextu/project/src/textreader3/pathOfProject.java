
package textreader3;

import java.io.File;

public class pathOfProject {
    
    String adresaProjektu;
    
    
    pathOfProject(){
        
        File currentDirFile = new File(".");
        String helper;
        
        int lastSlash;
                
        helper = currentDirFile.getAbsolutePath();
      
        String str = "C:\\Users\\jonas\\OneDrive\\Dokumenty\\2023\\JAVA-Syntaxe\\PNG_tet1";
        lastSlash = str.lastIndexOf("\\");
        adresaProjektu = str.substring(0, lastSlash);
        
    }
    
    public String getAdresaProjektu(){
        return(adresaProjektu);
    }
    
}
