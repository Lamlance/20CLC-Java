package MainGui;

import javax.swing.*;
import java.awt.*;

public class SearchSlangPanel {
  private int searchIndex = 0;
  private int searchMaxIndex = 0;

  private JTextField searchKey_TxtFld = new JTextField();
  private JTextField searchDef_TxtFld = new JTextField();
  private JButton searchSlangBtn = new JButton("Search");
  private JLabel searchIndexLabel = new JLabel(String.format("%d out of %d", 0,0));
  private JLabel searchStatusLabel = new JLabel("Search status: none");

  private JPanel searchPanel = new JPanel(new java.awt.GridLayout(2,2));

  SearchSlangPanel(){
    JPanel searchSlangPanel1 = new JPanel();
    searchSlangPanel1.setLayout(new BorderLayout());
    searchSlangPanel1.add(new JLabel("Search key:"),BorderLayout.WEST);
    searchSlangPanel1.add(this.searchKey_TxtFld,BorderLayout.CENTER);

    JPanel searchSlangPanel2 = new JPanel();
    searchSlangPanel2.setLayout(new BorderLayout());
    searchSlangPanel2.add(new JLabel("Search definition:"),BorderLayout.WEST);
    searchSlangPanel2.add(this.searchDef_TxtFld,BorderLayout.CENTER);
    
    JPanel searchSlangPanel3 = new JPanel(new FlowLayout());
    searchSlangPanel3.add(searchStatusLabel);
    searchSlangPanel3.add(searchSlangBtn);

    JPanel searchSlangPanel4 = new JPanel(new BorderLayout());
    searchSlangPanel4.add(searchIndexLabel,BorderLayout.CENTER);

    this.searchPanel.add(searchSlangPanel1);
    this.searchPanel.add(searchSlangPanel2);
    this.searchPanel.add(searchSlangPanel4);
    this.searchPanel.add(searchSlangPanel3);
  }
  public void addActionListenerToButton(java.awt.event.ActionListener listener) {
    this.searchSlangBtn.addActionListener(listener);

  }


  public int getSearchIndex() {
    return this.searchIndex;
  }

  public void setSearchIndex(int searchIndex) {
    this.searchIndex = searchIndex;
  }

  public int getSearchMaxIndex() {
    return this.searchMaxIndex;
  }

  public void setSearchMaxIndex(int searchMaxIndex) {
    this.searchMaxIndex = searchMaxIndex;
  }

  public JTextField getSearchKey_TxtFld() {
    return this.searchKey_TxtFld;
  }

  public void setSearchKey_TxtFld(JTextField searchKey_TxtFld) {
    this.searchKey_TxtFld = searchKey_TxtFld;
  }

  public JTextField getSearchDef_TxtFld() {
    return this.searchDef_TxtFld;
  }

  public void setSearchDef_TxtFld(JTextField searchDef_TxtFld) {
    this.searchDef_TxtFld = searchDef_TxtFld;
  }

  public JButton getSearchSlangBtn() {
    return this.searchSlangBtn;
  }

  public void setSearchSlangBtn(JButton searchSlangBtn) {
    this.searchSlangBtn = searchSlangBtn;
  }

  public JLabel getSearchIndexLabel() {
    return this.searchIndexLabel;
  }

  public void setSearchIndexLabel(JLabel searchIndexLabel) {
    this.searchIndexLabel = searchIndexLabel;
  }

  public JLabel getSearchStatusLabel() {
    return this.searchStatusLabel;
  }

  public void setSearchStatusLabel(JLabel searchStatusLabel) {
    this.searchStatusLabel = searchStatusLabel;
  }

  public JPanel getSearchPanel() {
    return this.searchPanel;
  }

  public void setSearchPanel(JPanel searchPanel) {
    this.searchPanel = searchPanel;
  }

}
