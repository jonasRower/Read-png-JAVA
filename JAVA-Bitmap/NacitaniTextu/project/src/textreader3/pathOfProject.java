
package textreader3;

import java.io.File;

public class pathOfProject {
    
    String adresaProjektu;
    
    
    pathOfProject(){
        
        File currentDirFile = new File(".");
        String helper;
        String helper2;
        
        int lastSlash;
                
        helper = currentDirFile.getAbsolutePath();
      
        lastSlash = helper.lastIndexOf("\\");
        helper2 = helper.substring(0, lastSlash);
        
        lastSlash = helper2.lastIndexOf("\\");
        adresaProjektu = helper2.substring(0, lastSlash);
        
    }
    
    public String getAdresaProjektu(){
        return(adresaProjektu);
    }
    
}
