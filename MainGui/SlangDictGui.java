package MainGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import SlangDict.SlangDictHashMap;

public class SlangDictGui implements Serializable {
  private static final long serialVersionUID = 1L;
  private SlangDictGui mysSelf = this;
  private static final String PANEL_OPTION_DICT_MANIPULATE = "Manipulate dictionary";
  private static final String PANEL_OPTION_SEARCH = "Search a slang";
  private static final String PANEL_OPTION_RANDOM = "Feeling lucky";
  private static final String PANEL_OPTION_GUESS_SLANG = "Guess slang game";
  private static final String PANEL_OPTION_GUESS_DEF = "Guess definition game";
  private static final String SAVE_DICTIONARY = "Dictionary.LAM";
  private static final Random randomizer = new Random();

  private JFrame frame;
  private JTextArea defTextArea;

  private JList<String> slangKeyList;
  private DefaultListModel<String> listModel;

  private AddSlangPanel addSlangPanel;
  private SearchSlangPanel searchSlangPanel;
  private RandomSlangPanel randomSlangPanel;
  private GameSlangPanel guessSlangGame;
  private GameSlangPanel guessDefGame;
  private JTextArea logTextArea;
  private JTabbedPane inputTabPanel;

  private String selectedKey = "";
  private SlangDictHashMap slangDict;

  private ArrayList<Integer> searchIndexes = new ArrayList<Integer>();

  private boolean stagedForDeletion = false;
  private boolean stagedForReset = false;

  public static void main(String[] args) {
    boolean succesRestore = false;
    try {
      FileInputStream fi = new FileInputStream(new File(SlangDictGui.SAVE_DICTIONARY));
      ObjectInputStream oi = new ObjectInputStream(fi);
      SlangDictGui gui = (SlangDictGui) oi.readObject();
      gui.createGui();
      succesRestore = true;
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    if (succesRestore == false) {
      System.out.println("Restore failed");
      SlangDictGui gui = new SlangDictGui();
      gui.createGui();
    }

  }

  SlangDictGui() {
    slangDict = new SlangDictHashMap();
    slangDict.readFromFile("slang.txt");
    System.out.println(slangDict.getSlangDict().size());
  }

  public void createGui() {
    JFrame.setDefaultLookAndFeelDecorated(true);
    this.frame = new JFrame("Slang Dictionary 20127047");
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.addWindowListener(new WindowCloseHandler());

    String[] slangKeys = this.slangDict.getSlangDict().keySet().toArray(new String[0]);
    listModel = new DefaultListModel<String>();
    for (String Key : slangKeys) {
      listModel.addElement(Key);
    }
    this.slangKeyList = new JList<String>(this.listModel);
    this.slangKeyList.addListSelectionListener(new SlangKeyListSelectionHandle());
    JScrollPane dictKeyScrollPane = new JScrollPane(this.slangKeyList);
    this.frame.add(dictKeyScrollPane, BorderLayout.WEST);

    this.logTextArea = new JTextArea("Select something\n");
    this.logTextArea.setEditable(false);
    this.defTextArea = new JTextArea("Select something\n");
    this.defTextArea.setEditable(false);
    JScrollPane textDefScroll = new JScrollPane(this.defTextArea);
    JScrollPane textLogScroll = new JScrollPane(this.logTextArea);

    JPanel textAreaPanel = new JPanel(new GridLayout(1, 2));
    textAreaPanel.add(textDefScroll);
    textAreaPanel.add(textLogScroll);
    this.frame.add(textAreaPanel, BorderLayout.CENTER);

    // ====Input new Slang Panel
    this.addSlangPanel = new AddSlangPanel();
    this.addSlangPanel.addActionListenerToButton(new AddSlangBtnHandle());
    this.addSlangPanel.addActionListenerToReset(new ResetButtonHandler());

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

    // Guess slang game ============
    this.guessSlangGame = new GameSlangPanel();
    guessSlangGame.SetOptionButtonActionListener(new GuessSlangGameOptionsHandler());
    guessSlangGame.SetResetButtonActionListener(new GuessGameResetsHandler());
    JPanel guessSlangGameCardPanel = new JPanel(new java.awt.CardLayout());
    guessSlangGameCardPanel.add(guessSlangGame.getGanePanel(), SlangDictGui.PANEL_OPTION_GUESS_SLANG);
    // =====================

    // Guess slang game ============
    this.guessDefGame = new GameSlangPanel();
    guessDefGame.SetOptionButtonActionListener(new GuessDefGameOptionsHandler());
    guessDefGame.SetResetButtonActionListener(new GuessGameResetsHandler());
    JPanel guessDefGameCardPanel = new JPanel(new java.awt.CardLayout());
    guessDefGameCardPanel.add(guessDefGame.getGanePanel(), SlangDictGui.PANEL_OPTION_GUESS_DEF);
    // =====================

    // Tab Pane============
    this.inputTabPanel = new JTabbedPane();
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_DICT_MANIPULATE, addCardPanel);
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_SEARCH, searchCardPanel);
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_RANDOM, randomCardPanel);
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_GUESS_SLANG, guessSlangGameCardPanel);
    inputTabPanel.addTab(SlangDictGui.PANEL_OPTION_GUESS_DEF, guessDefGameCardPanel);

    this.frame.getContentPane().add(inputTabPanel, BorderLayout.PAGE_START);
    // ====================

    this.frame.setSize(800, 600);
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

  public void sysOutRunTime(long nanoTime, String msg) {
    double msTime = ((double) nanoTime) / 100000;
    double time = (msTime) / 1000;
    String logString = String.format("%s run time: %f(ms) | %f(s) \n", msg, msTime, time);
    System.out.println(logString);
    this.logTextArea.append(logString);
  }

  /**
   * 
   * @return size 5 list model index , 1st is the answer index , 4 remain is the
   *         options index
   */
  private int[] GenerateGuessGame() {
    int listModelSize = this.listModel.size();

    int[] indexes = {
        SlangDictGui.randomizer.nextInt(listModelSize), SlangDictGui.randomizer.nextInt(listModelSize),
        SlangDictGui.randomizer.nextInt(listModelSize), SlangDictGui.randomizer.nextInt(listModelSize)
    };

    int[] ans = {
        indexes[SlangDictGui.randomizer.nextInt(indexes.length)],
        indexes[0], indexes[1], indexes[2], indexes[3]
    };

    return ans;
  }

  class WindowCloseHandler implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void windowClosing(WindowEvent e) {
      try {
        FileOutputStream f = new FileOutputStream(new File(SlangDictGui.SAVE_DICTIONARY));
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(mysSelf);
      } catch (Exception err) {

      }

    }

    @Override
    public void windowClosed(WindowEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void windowActivated(WindowEvent e) {
      // TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
      // TODO Auto-generated method stub

    }
  }

  /**
   * Slang dict gui. Handler for slect slang
   */
  class SlangKeyListSelectionHandle implements ListSelectionListener {
    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (stagedForReset) {
        return;
      }
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
    }
  }

  /**
   * Handler for add slang button
   */
  class AddSlangBtnHandle implements java.awt.event.ActionListener {
    public boolean deleteSlangByKey(String newKey) {
      if (slangKeyList.getSelectedValue() != null) {
        while (slangKeyList.getSelectedValue().toLowerCase().equals(newKey.toLowerCase())) {
          int curId = (slangKeyList.getSelectedIndex() + 1) % listModel.size();
          slangKeyList.setSelectedIndex(curId);
        }
      }

      if (listModel.removeElement(newKey)) {
        slangDict.removeSlang(newKey);
        return true;
      }
      return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      long start = System.nanoTime();

      String newKey = addSlangPanel.getNewKey_TxtFld().getText();
      String newDef = addSlangPanel.getNewDef_TxtFld().getText();
      String currentOption = addSlangPanel.getCurrentOption();

      if (stagedForDeletion == true) {
        if ((currentOption == AddSlangPanel.DELETE_IF_EXIST) && !newKey.isBlank()) {
          String ans = deleteSlangByKey(newKey) ? "Delete successful" : "Key doesn't exist";
          addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
          addSlangPanel.getInputStatusLabel().setText(ans);
        } else {
          addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
          addSlangPanel.getInputStatusLabel().setText("Delete canceled");
        }
        stagedForDeletion = false;
        addSlangPanel.getNewKey_TxtFld().setEditable(true);
        return;
      }

      stagedForDeletion = false;

      if (currentOption == AddSlangPanel.DELETE_IF_EXIST && (!newKey.isBlank())) {
        if (listModel.contains(newKey)) {
          stagedForDeletion = true;
          addSlangPanel.getNewKey_TxtFld().setEditable(false);
          addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
          addSlangPanel.getInputStatusLabel().setText("Staging for deletion");
          logTextArea.append("Excute again to confirm deletion, Change option and press execute to cancel \n");
        } else {
          addSlangPanel.getInputStatusLabel().setForeground(Color.RED);
          addSlangPanel.getInputStatusLabel().setText("Key doesn't exist");
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

      long end = System.nanoTime();
      sysOutRunTime(end - start, "Manipulate dictionary");
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

      logTextArea.append("====Searches find==== \n");
      for (int index : searchIndexes) {
        String key = listModel.get(index);
        logTextArea.append(String.format("%s: %s \n",
            key, slangDict.getSlangByKey(key)));
      }
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

  class GuessSlangGameOptionsHandler implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      guessSlangGame.CalculateAnswer(((JButton) e.getSource()).getText());

      int[] generated = GenerateGuessGame();
      String[] options = {
          listModel.get(generated[1]), listModel.get(generated[2]),
          listModel.get(generated[3]), listModel.get(generated[4])
      };
      String answer = listModel.get(generated[0]);
      String question = String.format("slang for %s",
          slangDict.getSlangByKey(listModel.get(generated[0])));

      guessSlangGame.SetOptions(options);
      guessSlangGame.SetCorrectAnswer(answer);
      guessSlangGame.SetQuestion(question);
    }
  }

  class GuessDefGameOptionsHandler implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      guessDefGame.CalculateAnswer(((JButton) e.getSource()).getText());

      int[] generated = GenerateGuessGame();
      String[] options = {
          slangDict.getSlangByKey(listModel.get(generated[1])),
          slangDict.getSlangByKey(listModel.get(generated[2])),
          slangDict.getSlangByKey(listModel.get(generated[3])),
          slangDict.getSlangByKey(listModel.get(generated[4]))
      };
      String answer = slangDict.getSlangByKey(listModel.get(generated[0]));
      String question = String.format("definition of %s",
          listModel.get(generated[0]));

      guessDefGame.SetOptions(options);
      guessDefGame.SetCorrectAnswer(answer);
      guessDefGame.SetQuestion(question);
    }
  }

  class GuessGameResetsHandler implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String currentTab = inputTabPanel.getTitleAt(inputTabPanel.getSelectedIndex());

      if (currentTab == SlangDictGui.PANEL_OPTION_GUESS_SLANG) {
        guessSlangGame.RestartGame();
      }
      if (currentTab == SlangDictGui.PANEL_OPTION_GUESS_DEF) {
        guessDefGame.RestartGame();
      }
    }
  }

  class ResetButtonHandler implements java.awt.event.ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      long start = System.nanoTime();

      stagedForReset = true;
      slangKeyList.clearSelection();

      Set<String> keySetDefault = slangDict.getAllKeys();

      slangDict.restoreDefault();
      listModel.removeAllElements();

      for (String string : keySetDefault) {
        listModel.addElement(string);
      }

      stagedForReset = false;
      long end = System.nanoTime();
      sysOutRunTime(end - start, "Reset Dict ");
    }
  }
}
