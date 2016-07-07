package de.hzin.tddt.util; /**
 * Created by julius on 27.06.16.
 */
import java.io.*;

public class Filehandler{
    private String path = "";
    private String className = "";
    private String content = "";
    private boolean isTest = false;

    public Filehandler(String inFile){
        // Muss relativer Pfad werden!!!
        path = "Catalog/" + inFile + ".java";
        className = inFile;
    }

    public String load(){
        String line = "";
        try{
            FileInputStream stream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            while ((line = reader.readLine()) != null)   {
                if(!isTest && line.contains("//~Test")){
                    isTest = true;
                }
                content = content + line + "\n";
            }
            reader.close();
            //System.out.print(content);
        }
        catch (Exception e){
            System.out.println("Reading Error!");
        }

        return line;
    }

    public void save(String text){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(path,true));
            out.write(text);
            out.close();
        } catch (Exception e) {
            System.out.println("File Writing Error!");
        }
    }

    public String getContent(){
        return content;
    }

    public boolean getisTest(){
        return isTest;
    }

    public void pwd(){
        System.out.print(System.getProperty("user.dir"));
    }
}