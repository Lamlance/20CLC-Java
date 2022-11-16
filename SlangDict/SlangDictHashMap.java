package SlangDict;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class SlangDictHashMap {
  Map<String,String> slangDict = new HashMap<String,String>();

  public boolean readFromFile(String fileName) {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
      String lineData = null;
      while((lineData = fileReader.readLine()) != null){
        String[] values = lineData.split("`");
        if(values.length >= 2){
          String key = values[0];
          String def = values[1];
          this.slangDict.put(key, def);
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public String getSlangByKey(String key){
    return this.slangDict.get(key);
  }
  public int addSlang(String key,String def,boolean replaceFlag) {
    boolean containsKey = this.slangDict.containsKey(key);

    if(!(containsKey) || replaceFlag){
      this.slangDict.put(key, def);
      return containsKey ? 2 : 1;
    }
    return 0;
    
  }
  public Map<String,String> getSlangDict() {
    return this.slangDict;
  }

  public void setSlangDict(Map<String,String> slangDict) {
    this.slangDict = slangDict;
  }
}
