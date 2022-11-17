package MainGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SlangDict.SlangDictHashMap;

public class SlangDictGui {

  private static final String[] OPTIONS = {"Add if duplicate","Don't replace if duplicate","Replace if duplicate"};
  private static final String INPUT_OPTIONS_NEW = "Add new slang";
  private static final String INPUT_OPTION_SEARCH = "Search a slang";

  private JFrame frame;
  private JTextArea defTextArea;

  private JList<String> slangKeyList;
  private DefaultListModel<String> listModel;

  private AddSlangPanel addSlangPanel;

  private JTextField searchKey_TxtFld;
  private JTextField searchDef_TxtFld;
  private int searchIndex = 0;
  private int searchLength = 0;

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

    //====Input new Slang Panel  
    this.addSlangPanel = new AddSlangPanel();
    this.addSlangPanel.addActionListenerToButton(new AddSlangBtnHandle());

    JPanel addCardPanel = new JPanel(new java.awt.CardLayout());
    addCardPanel.add(this.addSlangPanel.getAddSlangPanel(),SlangDictGui.INPUT_OPTIONS_NEW);

    //===================
    //Input search slang panel
    this.searchDef_TxtFld = new JTextField();
    this.searchKey_TxtFld = new JTextField();

    

    JPanel searchCardPanel = new JPanel(new java.awt.CardLayout());
    searchCardPanel.add(newSearchPanel,SlangDictGui.INPUT_OPTION_SEARCH);

    //====================
    //Tab Pane============
    JTabbedPane inputTabPanel = new JTabbedPane();
    inputTabPanel.addTab(SlangDictGui.INPUT_OPTIONS_NEW, addCardPanel);
    inputTabPanel.addTab(SlangDictGui.INPUT_OPTION_SEARCH, searchCardPanel);
    this.frame.getContentPane().add(inputTabPanel, BorderLayout.PAGE_START);
    //====================

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
  class AddSlangBtnHandle implements java.awt.event.ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
      String newKey = addSlangPanel.getNewKey_TxtFld().getText();
      String newDef = addSlangPanel.getNewDef_TxtFld().getText();

      if(newDef.isBlank() || newDef.isEmpty() || newKey.isEmpty() || newKey.isBlank()){
        addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
        addSlangPanel.getInputStatusLabel().setText("Status: invalid input");
        return;
      }
      boolean replaceFlag = (addSlangPanel.getAddOptions().getSelectedIndex() != 0);
      int addAns = slangDict.addSlang(newKey, newDef, replaceFlag);
      if(addAns != 0){
        addSlangPanel.getInputStatusLabel().setForeground(Color.GREEN);
        addSlangPanel.getInputStatusLabel().setText("Status: input success");
        if(addAns == 1){
          listModel.addElement(newKey);
        }
      }else{
        addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
        addSlangPanel.getInputStatusLabel().setText("Status: input failed");
      }
    }    
  }
  class SearchSlangBtnHandle implements java.awt.event.ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      String[] keyModelStrings = (String[]) listModel.toArray();
      ArrayList<Integer> searchIndexes = this.SearchOnlyKey(keyModelStrings);
    }

    public ArrayList<Integer> SearchOnlyKey(String keyArr[]) {
      ArrayList<Integer> ansArrayList = new ArrayList<Integer>();
      for (int i = 0; i < keyArr.length; i++) {
        if(keyArr[i].contains(searchKey_TxtFld.getText())){
          ansArrayList.add(i);
        }
      }
      return ansArrayList;
    }

  }


}
