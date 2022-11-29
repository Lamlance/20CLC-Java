package MainGui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class SearchSlangPanel implements Serializable {
  private static final long serialVersionUID = 1L;

  private int searchIndex = 0;
  private int searchMaxIndex = 0;

  private JTextField searchKey_TxtFld = new JTextField();
  private JTextField searchDef_TxtFld = new JTextField();

  private JButton searchSlangBtn = new JButton("Search");
  private JLabel searchStatusLabel = new JLabel("Search status: none");

  private JLabel searchIndexLabel = new JLabel(String.format("%d out of %d", 0,0));
  private JButton incSearchIndexBtn = new JButton("+");
  private JButton decSearchIndexBtn = new JButton("-");

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
    searchIndexLabel.setHorizontalAlignment(SwingConstants.CENTER);
    searchSlangPanel4.add(searchIndexLabel,BorderLayout.CENTER);
    searchSlangPanel4.add(incSearchIndexBtn,BorderLayout.EAST);
    searchSlangPanel4.add(decSearchIndexBtn,BorderLayout.WEST);

    this.searchPanel.add(searchSlangPanel1);
    this.searchPanel.add(searchSlangPanel2);
    this.searchPanel.add(searchSlangPanel4);
    this.searchPanel.add(searchSlangPanel3);
  }
  public void addActionListenerToButton(java.awt.event.ActionListener listener) {
    this.searchSlangBtn.addActionListener(listener);
  }
  public void addActionListenerToIncIndex(java.awt.event.ActionListener listener) {
    this.incSearchIndexBtn.addActionListener(listener);
  }
  public void addActionListenerToDecIndex(java.awt.event.ActionListener listener) {
    this.decSearchIndexBtn.addActionListener(listener);
  }
  public void updateSearchIndexLabel(int index,int maxIndex){
    if(index > maxIndex || index < 0){
      return;
    }
    this.searchIndex = index;
    this.searchMaxIndex = maxIndex;
    this.searchIndexLabel.setText(String.format("%d out of %d", this.searchIndex+1,this.searchMaxIndex+1));
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

  public String getSearchKey(){
    return this.searchKey_TxtFld.getText();
  }
  
  public String getSearchDef(){
    return this.searchDef_TxtFld.getText();
  }

  public JPanel getSearchPanel() {
    return this.searchPanel;
  }

  public void setSearchPanel(JPanel searchPanel) {
    this.searchPanel = searchPanel;
  }

}
