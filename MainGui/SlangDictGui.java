package MainGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SlangDict.SlangDictHashMap;

public class SlangDictGui {

  private static final String PANEL_OPTION_DICT_MANIPULATE = "Add/Edit/Delete slang";
  private static final String PANEL_OPTION_SEARCH = "Search a slang";
  private static final String PANEL_OPTION_RANDOM = "I'm feeling lucky";

  private JFrame frame;
  private JTextArea defTextArea;

  private JList<String> slangKeyList;
  private DefaultListModel<String> listModel;

  private AddSlangPanel addSlangPanel;
  private SearchSlangPanel searchSlangPanel;
  private RandomSlangPanel randomSlangPanel;

  private String selectedKey = "";
  private SlangDictHashMap slangDict;

  private ArrayList<Integer> searchIndexes = new ArrayList<Integer>();

  public static void main(String[] args) {
    SlangDictGui gui = new SlangDictGui();
    gui.createGui();

  }

  SlangDictGui() {
    slangDict = new SlangDictHashMap();
    slangDict.readFromFile("slang.txt");
    System.out.println(slangDict.getSlangDict().size());
  }

  public void createGui() {
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
    this.frame.add(dictKeyScrollPane, BorderLayout.WEST);

    this.defTextArea = new JTextArea("Select something\n");
    JScrollPane textScroll = new JScrollPane(this.defTextArea);
    this.frame.add(textScroll, BorderLayout.CENTER);

    // ====Input new Slang Panel
    this.addSlangPanel = new AddSlangPanel();
    this.addSlangPanel.addActionListenerToButton(new AddSlangBtnHandle());

    JPanel addCardPanel = new JPanel(new java.awt.CardLayout());
    addCardPanel.add(this.addSlangPanel.getAddSlangPanel(), SlangDictGui.PANEL_OPTION_DICT_MANIPULATE);
    // ===================

    // Input search slang panel
    this.searchSlangPanel = new SearchSlangPanel();
    this.searchSlangPanel.addActionListenerToButton(new SearchSlangBtnHandle());
    this.searchSlangPanel.addActionListenerToIncIndex(new ChangeSearchIndexBtnHandle(1));
    this.searchSlangPanel.addActionListenerToDecIndex(new ChangeSearchIndexBtnHandle(-1));

    JPanel searchCardPanel = new JPanel(new java.awt.CardLayout());
    searchCardPanel.add(searchSlangPanel.getSearchPanel(), SlangDictGui.PANEL_OPTION_SEARCH);
    // ====================

    // Random slang Panel==
    this.randomSlangPanel = new RandomSlangPanel(this.listModel.size());
    this.randomSlangPanel.SetRandomButtonHandler(new RandomSlangButtonHandler());

    JPanel randomCardPanel = new JPanel(new java.awt.CardLayout());
    randomCardPanel.add(randomSlangPanel.getRandomPanel(), SlangDictGui.PANEL_OPTION_RANDOM);
    // =====================

    // Tab Pane============
    JTabbedPane inputTabPanel = new JTabbedPane();
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_DICT_MANIPULATE, addCardPanel);
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_SEARCH, searchCardPanel);
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_RANDOM, randomCardPanel);

    this.frame.getContentPane().add(inputTabPanel, BorderLayout.PAGE_START);
    // ====================

    this.frame.setSize(600, 600);
    this.frame.setVisible(true);
  }

  public void ListJumpToIndex(int index) {
    this.slangKeyList.ensureIndexIsVisible(index);
    this.slangKeyList.setSelectedIndex(index);
  }

  public void ListJumpToCurrentSearchIndex() {
    int searchId = searchSlangPanel.getSearchIndex();
    int searchIdMax = searchSlangPanel.getSearchMaxIndex();
    if (this.searchIndexes.size() <= 0) {
      return;
    }
    if (searchId <= searchIdMax) {
      int jumpId = this.searchIndexes.get(searchId);
      this.ListJumpToIndex(jumpId);
    }
  }

  public void sysOutRunTime(long nanoTime,String msg){
    double msTime = ((double)nanoTime) / 100000;
    double time = (msTime) / 1000;
    System.out.println(String.format("%s run time: %d(ns) | %f(ms) | %f(s)", msg,nanoTime,msTime,time));
  }

  /**
   * Slang dict gui. Handler for slect slang
   */
  class SlangKeyListSelectionHandle implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
      long start = System.nanoTime();

      if (e.getValueIsAdjusting()) {
        return;
      }
      String selected = slangKeyList.getSelectedValue();
      if (selected.equalsIgnoreCase(selectedKey)) {
        return;
      }
      selectedKey = selected;
      String def = slangDict.getSlangByKey(selectedKey);
      defTextArea.append(String.format("%s \n", def));

      long end = System.nanoTime();
      sysOutRunTime(end-start,"Select Slang");
    }
  }

  /**
   * Handler for add slang button
   */
  class AddSlangBtnHandle implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      long start = System.currentTimeMillis();

      String newKey = addSlangPanel.getNewKey_TxtFld().getText();
      String newDef = addSlangPanel.getNewDef_TxtFld().getText();
      String currentOption = addSlangPanel.getCurrentOption();

      if (currentOption == AddSlangPanel.DELETE_IF_EXIST && !(newDef.isBlank() || newDef.isEmpty())) {
        if (listModel.removeElement(newKey)) {
          slangDict.removeSlang(newKey);
          addSlangPanel.getInputStatusLabel().setForeground(Color.GREEN);
          addSlangPanel.getInputStatusLabel().setText("Status: delete successful");
        } else {
          addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
          addSlangPanel.getInputStatusLabel().setText("Status: slang key didn't exist");
        }
        return;
      }

      if (newDef.isBlank() || newDef.isEmpty() || newKey.isEmpty() || newKey.isBlank()) {
        addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
        addSlangPanel.getInputStatusLabel().setText("Status: invalid input");
        return;
      }

      boolean replaceFlag = currentOption != AddSlangPanel.NOT_REPLACE_IF_DUPLICATE;

      if (currentOption == AddSlangPanel.ADD_IF_DUPLICATE_FLAG) {
        String current = slangDict.getSlangByKey(newKey);

        newDef = (current != null) ? (String.format("%s || %s", current, newDef)) : newDef;
      }

      if (currentOption == AddSlangPanel.EDIT_IF_EXIST && (slangDict.getSlangByKey(newKey) == null)) {
        addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
        addSlangPanel.getInputStatusLabel().setText("Status: slang key didn't exist");
        return;
      }

      int addAns = slangDict.addSlang(newKey, newDef, replaceFlag);
      if (addAns != 0) {
        addSlangPanel.getInputStatusLabel().setForeground(Color.GREEN);
        addSlangPanel.getInputStatusLabel().setText("Status: input success");
        if (addAns == 1) {
          listModel.addElement(newKey);
        }
      } else {
        addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
        addSlangPanel.getInputStatusLabel().setText("Status: input failed");
      }
    }

  }

  /**
   * Change search result button handler
   */
  class ChangeSearchIndexBtnHandle implements java.awt.event.ActionListener {
    int valueChange = 0;

    ChangeSearchIndexBtnHandle(int valueChangeInit) {
      super();
      this.valueChange = valueChangeInit;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        int currentId = searchSlangPanel.getSearchIndex();
        int maxId = searchSlangPanel.getSearchMaxIndex();

        searchSlangPanel.updateSearchIndexLabel(currentId + this.valueChange, maxId);
        ListJumpToCurrentSearchIndex();
      } catch (Exception exception) {
        return;
      }

    }

  }

  /**
   * Search slang button handler
   */
  class SearchSlangBtnHandle implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      long start = System.nanoTime();

      ArrayList<String> keyModelStrings = Collections.list(listModel.elements());

      String searchKey = searchSlangPanel.getSearchKey();
      String searchDef = searchSlangPanel.getSearchDef();

      searchIndexes = this.Search(keyModelStrings, searchKey, searchDef);

      searchSlangPanel.updateSearchIndexLabel(0, searchIndexes.size() - 1);

      if (searchIndexes.size() != 0) {
        ListJumpToCurrentSearchIndex();
      }

      long end = System.nanoTime();
      sysOutRunTime(end - start, "Search slang");
    }

    public ArrayList<Integer> Search(ArrayList<String> keyArr, String searchKey, String searchDef) {
      ArrayList<Integer> ansArrayList = new ArrayList<Integer>();
      boolean skipKeyFlag = searchKey.isBlank() || searchKey.isEmpty();
      boolean skipDefFlag = searchDef.isBlank() || searchDef.isEmpty();

      if (skipDefFlag && skipKeyFlag) {
        return ansArrayList;
      }

      for (int i = 0; i < keyArr.size(); i++) {
        String currKey = keyArr.get(i);
        boolean searchKeySuccess = !skipKeyFlag && currKey.toLowerCase().contains(searchKey.toLowerCase());
        boolean searchDefSuccess = !skipDefFlag
            && slangDict.getSlangByKey(currKey).toLowerCase().contains(searchDef.toLowerCase());

        if ((!skipDefFlag && !skipKeyFlag) ? (searchDefSuccess && searchKeySuccess)
            : (searchKeySuccess || searchDefSuccess)) {
          ansArrayList.add(i);
        }
      }
      return ansArrayList;
    }

  }

  class RandomSlangButtonHandler implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int randomIndex = randomSlangPanel.GetRandomIndex();
      String randomKey = listModel.get(randomIndex);
      ListJumpToIndex(randomIndex);
      String luckyMsg = String.format("Your lucky slang '%s': '%s' ", randomKey, slangDict.getSlangByKey(randomKey));
      randomSlangPanel.getYourLuckWordLabel().setText(luckyMsg);
    }
  }

}
