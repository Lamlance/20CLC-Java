package MainGui;

import javax.swing.*;
import java.awt.*;

public class AddSlangPanel {
  private static final String[] ADD_OPTIONS = {"Add if duplicate","Don't replace if duplicate","Replace if duplicate"};

  private JTextField newKey_TxtFld = new JTextField();
  private JTextField newDef_TxtFld = new JTextField();
  private JLabel inputStatusLabel = new JLabel("Status: none");
  private JButton addSlangBtn = new JButton("Add slang");
  private JComboBox<String> addOptions = new JComboBox<String>(AddSlangPanel.ADD_OPTIONS);

  private JPanel addSlangPanel = new JPanel(new java.awt.GridLayout(2,2));

  AddSlangPanel(){

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
    
    this.addSlangPanel.add(addSlangPanel1);
    this.addSlangPanel.add(addSlangPanel2);
    this.addSlangPanel.add(addSlangPanel3);
    this.addSlangPanel.add(addSlangPanel4);
  }
  public void addActionListenerToButton(java.awt.event.ActionListener listener) {
    this.addSlangBtn.addActionListener(listener);

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
