package de.hzin.tddt.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julius on 14.07.16.
 */
public class RevertableCode {
    List<String> content = new ArrayList<>();

    public RevertableCode(){
        content.add("");
    }

    public void addContent(String tmpContent){
        content.add(tmpContent);
    }

    public String getCurrentContent(){
        return content.get(content.size()-1);
    }

    public String getPastContent(){
        if(content.size() > 1) return content.get(content.size()-2);
        else                   return content.get(0);
    }
}