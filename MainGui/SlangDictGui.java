package MainGui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SlangDict.SlangDictHashMap;

public class SlangDictGui {
  private JFrame frame;
  private JTextArea defTextArea;

  private JList<String> slangKeyList;
  private DefaultListModel<String> listModel;

  private JTextField addSlangKey_TextField;
  private JTextField addSlangDef_TextField;

  private String selectedKey = "";
  private SlangDictHashMap slangDict;

  public static void main(String[] args) {
    SlangDictGui gui = new SlangDictGui();
    gui.createGui();
    
  }
  SlangDictGui(){
    slangDict = new SlangDictHashMap();
    slangDict.readFromFile("slang.txt");
  }
  public void createGui(){
    JFrame.setDefaultLookAndFeelDecorated(true);
    this.frame = new JFrame("Slang Dictionary");
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    


    String[] slangKeys = this.slangDict.getSlangDict().keySet().toArray(new String[0]);
    listModel = new DefaultListModel<String>();
    for (String Key : slangKeys) {
      listModel.addElement(Key);
    }
    this.slangKeyList = new JList<String>(this.listModel);
    this.slangKeyList.addListSelectionListener(new SlangKeyListSelectionHandle());
    JScrollPane dictKeyScrollPane = new JScrollPane(this.slangKeyList);    
    this.frame.add(dictKeyScrollPane,BorderLayout.WEST);

    this.defTextArea = new JTextArea("Select something\n");
    JScrollPane textScroll = new JScrollPane(this.defTextArea);
    this.frame.add(textScroll,BorderLayout.CENTER);



    this.frame.setSize(600, 600);
    this.frame.setVisible(true);
  }

  class SlangKeyListSelectionHandle implements ListSelectionListener{

    @Override
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()){
        return;
      }
      String selected = slangKeyList.getSelectedValue();
      if(selected.equalsIgnoreCase(selectedKey)){
        return;
      }
      selectedKey = selected;
      String def = slangDict.getSlangByKey(selectedKey);
      defTextArea.append(String.format("%s \n", def));
    }
  }
}
