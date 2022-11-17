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

  private JTextField newKey_TxtFld;
  private JTextField newDef_TxtFld;
  private JLabel inputStatusLabel;
  //private JButton addSlangBtn;
  private JComboBox<String> addOptions;

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
    this.inputStatusLabel = new JLabel("Status: none");
    JButton addSlangBtn = new JButton("Add slang");
    addSlangBtn.addActionListener(new AddSlangBtnHandle());
    this.newKey_TxtFld = new JTextField();
    this.newDef_TxtFld = new JTextField();
    addOptions = new JComboBox<String>(OPTIONS);

    JPanel addSlangPanel1 = new JPanel();
    addSlangPanel1.setLayout(new java.awt.BorderLayout());
    addSlangPanel1.add(new JLabel("New key"),BorderLayout.WEST);
    addSlangPanel1.add(newKey_TxtFld,BorderLayout.CENTER);

    JPanel addSlangPanel2 = new JPanel();
    addSlangPanel2.setLayout(new java.awt.BorderLayout());
    addSlangPanel2.add(new JLabel("New definition"),BorderLayout.WEST);
    addSlangPanel2.add(newDef_TxtFld,BorderLayout.CENTER);

    JPanel addSlangPanel3 = new JPanel();
    addSlangPanel3.add(new JLabel("Add Option"));
    addSlangPanel3.add(this.addOptions);

    JPanel addSlangPanel4 = new JPanel();
    addSlangPanel4.add(this.inputStatusLabel);
    addSlangPanel4.add(addSlangBtn);
    
    JPanel newInputPanel = new JPanel();
    newInputPanel.setLayout(new java.awt.GridLayout(2,2));
    newInputPanel.add(addSlangPanel1);
    newInputPanel.add(addSlangPanel2);
    newInputPanel.add(addSlangPanel3);
    newInputPanel.add(addSlangPanel4);

    JPanel addCardPanel = new JPanel(new java.awt.CardLayout());
    addCardPanel.add(newInputPanel,SlangDictGui.INPUT_OPTIONS_NEW);

    //===================
    //Input search slang panel
    this.searchDef_TxtFld = new JTextField();
    this.searchKey_TxtFld = new JTextField();

    JPanel newSearchPanel1 = new JPanel();
    newSearchPanel1.setLayout(new BorderLayout());
    newSearchPanel1.add(new JLabel("Search key:"),BorderLayout.WEST);
    newSearchPanel1.add(this.searchKey_TxtFld,BorderLayout.CENTER);

    JPanel newSearchPanel2 = new JPanel();
    newSearchPanel2.setLayout(new BorderLayout());
    newSearchPanel2.add(new JLabel("Search definition:"),BorderLayout.WEST);
    newSearchPanel2.add(this.searchDef_TxtFld,BorderLayout.CENTER);
    
    JPanel newSearchPanel3 = new JPanel(new BorderLayout());
    JButton searchBtn = new JButton("Search");
    newSearchPanel3.add(searchBtn,BorderLayout.CENTER);
    newSearchPanel3.add(new JLabel("Search status: none"),BorderLayout.WEST);

    JPanel newSearchPanel = new JPanel(new java.awt.GridLayout(2,2));
    newSearchPanel.add(newSearchPanel1);
    newSearchPanel.add(newSearchPanel2);
    newSearchPanel.add(new JLabel(String.format("%d out of %d", this.searchIndex,this.searchLength)));
    newSearchPanel.add(newSearchPanel3);

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
      String newKey = newKey_TxtFld.getText();
      String newDef = newDef_TxtFld.getText();
      if(newDef.isBlank() || newDef.isEmpty() || newKey.isEmpty() || newKey.isBlank()){
        inputStatusLabel.setForeground(Color.RED);
        inputStatusLabel.setText("Status: invalid input");
        return;
      }
      boolean replaceFlag = (addOptions.getSelectedIndex() != 0);
      int addAns = slangDict.addSlang(newKey, newDef, replaceFlag);
      if(addAns != 0){
        inputStatusLabel.setForeground(Color.GREEN);
        inputStatusLabel.setText("Status: input success");
        if(addAns == 1){
          listModel.addElement(newKey);
        }
      }else{
        inputStatusLabel.setForeground(Color.RED);
        inputStatusLabel.setText("Status: input failed");
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
