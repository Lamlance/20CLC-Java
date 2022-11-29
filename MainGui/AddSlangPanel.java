package MainGui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class AddSlangPanel implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final String[] ADD_OPTIONS = {
    "Add if duplicate","Don't replace if duplicate","Replace if duplicate",
    "Edit if exist", "Delete if exist"
  };

  public final static String ADD_IF_DUPLICATE_FLAG = AddSlangPanel.ADD_OPTIONS[0];
  public final static String NOT_REPLACE_IF_DUPLICATE = AddSlangPanel.ADD_OPTIONS[1];
  public final static String REPLACE_IF_DUPLICATE = AddSlangPanel.ADD_OPTIONS[2];
  public final static String EDIT_IF_EXIST = AddSlangPanel.ADD_OPTIONS[3];
  public final static String DELETE_IF_EXIST = AddSlangPanel.ADD_OPTIONS[4];

  private JTextField newKey_TxtFld = new JTextField();
  private JTextField newDef_TxtFld = new JTextField();
  private JLabel inputStatusLabel = new JLabel("Status: none");
  private JButton addSlangBtn = new JButton("EXECUTE");
  private JButton resetDictBtn = new JButton("RESET DICTIONARY");

  private JComboBox<String> addOptions = new JComboBox<String>(AddSlangPanel.ADD_OPTIONS);

  private JPanel addSlangPanel = new JPanel(new java.awt.GridLayout(2,2));

  AddSlangPanel(){

    JPanel addSlangPanel1 = new JPanel();
    addSlangPanel1.setLayout(new java.awt.BorderLayout());
    addSlangPanel1.add(new JLabel("Key"),BorderLayout.WEST);
    addSlangPanel1.add(newKey_TxtFld,BorderLayout.CENTER);

    JPanel addSlangPanel2 = new JPanel();
    addSlangPanel2.setLayout(new java.awt.BorderLayout());
    addSlangPanel2.add(new JLabel("Definition"),BorderLayout.WEST);
    addSlangPanel2.add(newDef_TxtFld,BorderLayout.CENTER);

    JPanel addSlangPanel3 = new JPanel();
    addSlangPanel3.add(new JLabel("Option"));
    addSlangPanel3.add(this.addOptions);
    addSlangPanel3.add(this.inputStatusLabel);

    JPanel addSlangPanel4 = new JPanel();
    addSlangPanel4.add(addSlangBtn);
    addSlangPanel4.add(this.resetDictBtn);

    
    this.addSlangPanel.add(addSlangPanel1);
    this.addSlangPanel.add(addSlangPanel2);
    this.addSlangPanel.add(addSlangPanel3);
    this.addSlangPanel.add(addSlangPanel4);
  }
  public void addActionListenerToButton(java.awt.event.ActionListener listener) {
    this.addSlangBtn.addActionListener(listener);
  }
  public void addActionListenerToReset(java.awt.event.ActionListener listener) {
    this.resetDictBtn.addActionListener(listener);
  }
  public JTextField getNewKey_TxtFld() {
    return this.newKey_TxtFld;
  }

  public void setNewKey_TxtFld(JTextField newKey_TxtFld) {
    this.newKey_TxtFld = newKey_TxtFld;
  }

  public JTextField getNewDef_TxtFld() {
    return this.newDef_TxtFld;
  }

  public void setNewDef_TxtFld(JTextField newDef_TxtFld) {
    this.newDef_TxtFld = newDef_TxtFld;
  }

  public JLabel getInputStatusLabel() {
    return this.inputStatusLabel;
  }

  public void setInputStatusLabel(JLabel inputStatusLabel) {
    this.inputStatusLabel = inputStatusLabel;
  }

  public JButton getAddSlangBtn() {
    return this.addSlangBtn;
  }

  public void setAddSlangBtn(JButton addSlangBtn) {
    this.addSlangBtn = addSlangBtn;
  }

  public JComboBox<String> getAddOptions() {
    return this.addOptions;
  }

  public String getCurrentOption(){
    return (String)this.addOptions.getSelectedItem();
  }

  public void setAddOptions(JComboBox<String> addOptions) {
    this.addOptions = addOptions;
  }

  public JPanel getAddSlangPanel() {
    return this.addSlangPanel;
  }

  public void setAddSlangPanel(JPanel addSlangPanel) {
    this.addSlangPanel = addSlangPanel;
  }
  
}
