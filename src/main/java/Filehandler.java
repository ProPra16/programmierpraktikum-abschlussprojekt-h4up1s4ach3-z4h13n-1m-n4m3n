/**
 * Created by julius on 27.06.16.
 */
import java.io.*;

class Filehandler{
    private String path = "";

    public Filehandler(String inPath){
        path = inPath;
    }
    public String load(String filename){
        String line = "";
        try{
            FileInputStream stream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            while ((line = reader.readLine()) != null)   {
                line = line + "\n";
            }
            reader.close();
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
            // Writes the content to the file
        } catch (Exception e) {
            System.out.println("File Writing Error!");
        }
    }
}
