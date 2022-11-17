package MainGui;

import javax.swing.*;
import java.awt.*;

public class SearchSlangPanel {
  private int searchIndex = 0;
  private int searchMaxIndex = 0;

  private JTextField searchKey_TxtFld = new JTextField();
  private JTextField searchDef_TxtFld = new JTextField();
  private JButton searchSlangBtn = new JButton("Search");
  

  SearchSlangPanel(){
    JPanel searchSlangPanel1 = new JPanel();
    searchSlangPanel1.setLayout(new BorderLayout());
    searchSlangPanel1.add(new JLabel("Search key:"),BorderLayout.WEST);
    searchSlangPanel1.add(this.searchKey_TxtFld,BorderLayout.CENTER);

    JPanel searchSlangPanel2 = new JPanel();
    searchSlangPanel2.setLayout(new BorderLayout());
    searchSlangPanel2.add(new JLabel("Search definition:"),BorderLayout.WEST);
    searchSlangPanel2.add(this.searchDef_TxtFld,BorderLayout.CENTER);
    
    JPanel searchSlangPanel3 = new JPanel(new BorderLayout());
    searchSlangPanel3.add(searchSlangBtn,BorderLayout.CENTER);
    searchSlangPanel3.add(new JLabel("Search status: none"),BorderLayout.WEST);

    JPanel searchSlangPanel4 = new JPanel(new BorderLayout());
    searchSlangPanel4.add(new JLabel(String.format("%d out of %d", this.searchIndex,this.searchMaxIndex))

    JPanel newSearchPanel = new JPanel(new java.awt.GridLayout(2,2));
    newSearchPanel.add(newSearchPanel1);
    newSearchPanel.add(newSearchPanel2);
    newSearchPanel.add());
    newSearchPanel.add(newSearchPanel3);
  }
}
