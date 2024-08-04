package logData2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class variableJson {

    public variableJson(String json, String nazevSouboru){

        String adrOutput;
        String adresaSouboru;

        adrOutput = ziskejAdresuSlozky();
        adresaSouboru = adrOutput + "\\" + nazevSouboru;

        vytvorSlozku(adrOutput);
        tiskniJSon(json, adresaSouboru);

    }


    private String ziskejAdresuSlozky(){

        String adr;
        String adrNew;
        String slozka;

        adr = System.getProperty("user.dir");
        String regex = "[/\\\\]";
        String[] adrSpl = adr.split(regex);

        adrNew = "";

        for (int i = 0; i < adrSpl.length-1; i++) {
            slozka = adrSpl[i];
            adrNew = adrNew + slozka + "\\";
        }

        adrNew = adrNew + "InputOutput\\outputJson";


        return(adrNew);

    }


    private void tiskniJSon(String json, String adresaSouboru){

        //zapise data
        try {
            FileWriter myWriter = new FileWriter(adresaSouboru);
            myWriter.write(json);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }


    private void vytvorSlozku(String adrOutput){

        //vytvori slozku, pokud neni vytvorena
        try {
            new File(adrOutput).mkdirs();
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }

    }

}
