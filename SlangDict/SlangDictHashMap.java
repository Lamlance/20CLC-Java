package SlangDict;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SlangDictHashMap implements Serializable {
  private static final long serialVersionUID = 1L;

  Map<String, String> slangDict = new HashMap<String, String>();
  Map<String, String> backupDict = new HashMap<String, String>();

  public boolean readFromFile(String fileName) {
    // try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
    //   String lineData = null;
    //   while ((lineData = fileReader.readLine()) != null) {
    //     String[] values = lineData.split("`");
    //     if (values.length >= 2) {
    //       String key = values[0];
    //       String def = values[1];
    //       this.slangDict.put(key, def);
    //       this.backupDict.put(key, def);
    //     }
    //   }
    //   return true;
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }
    // return false;
    try {
      readWithinJar(fileName);
    } catch (Exception e) {
      e.printStackTrace() ;  
    }
    return true;
  }

  private boolean readWithinJar(String fileName) throws IOException {
    InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
    try (InputStreamReader isr = new InputStreamReader(ioStream); BufferedReader br = new BufferedReader(isr);) {
      String lineData = null;
      while ((lineData = br.readLine()) != null) {
        String[] values = lineData.split("`");
        if (values.length >= 2) {
          String key = values[0];
          String def = values[1];
          this.slangDict.put(key, def);
          this.backupDict.put(key, def);
        }
      }
    }
    return true;
  }

  /**
   * 
   * @param key
   * @return null if not found
   */
  public String getSlangByKey(String key) {
    return this.slangDict.get(key);
  }

  /**
   * 
   * @param key         slang key
   * @param def         slang definition
   * @param replaceFlag if true will replace the existing item
   * @return 0 if failed; 1 if input a new item; 2 if replace/edit an existing
   *         item
   */
  public int addSlang(String key, String def, boolean replaceFlag) {
    boolean containsKey = this.slangDict.containsKey(key);

    if (!(containsKey) || replaceFlag) {
      this.slangDict.put(key, def);
      return containsKey ? 2 : 1;
    }
    return 0;

  }

  public void removeSlang(String key) {
    this.slangDict.remove(key);
  }

  public Map<String, String> getSlangDict() {
    return this.slangDict;
  }

  public void setSlangDict(Map<String, String> slangDict) {
    this.slangDict = slangDict;
  }

  public void restoreDefault() {
    slangDict.clear();
    // Set<String> keys = backupDict.keySet();

    slangDict.putAll(backupDict);

    slangDict = new HashMap<String, String>(backupDict);
  }

  public Set<String> getAllKeys() {
    return this.slangDict.keySet();
  }
}
