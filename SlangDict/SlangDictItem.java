package SlangDict;

import java.util.Objects;

/**
 * SlangDictItem
 */
public class SlangDictItem {
  private String key = "";
  private String def = "";

  public SlangDictItem() {

  }
  
  public boolean readDataFromLine(String lineData) {
    String[] values = lineData.split("`");
    if(values.length != 2){
      return false;
    }
    try {
      this.key = values[0];
      this.def = values[1];
    } catch (Exception e) {
      System.err.println(e.toString());
      return false;
    }
    return true;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getDef() {
    return this.def;
  }

  public void setDef(String def) {
    this.def = def;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.key, this.def);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    try {
      SlangDictItem other = (SlangDictItem) obj;
      return (this.key.equals(other.key) && this.def.equals(other.def));
    } catch (Exception e) {
      return false;
    }
  }

}