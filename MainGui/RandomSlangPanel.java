package MainGui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomSlangPanel {
  int maxKeyListLength = -1;

  private JPanel randomPanel = new JPanel(new BorderLayout());
  private JButton randomBtn = new JButton("I'm felling lucky");
  private JLabel yourLuckWordLabel = new JLabel("Felling lucky ?",SwingConstants.CENTER);
  private Random random = new Random();

  RandomSlangPanel(int maxLength){
    randomPanel.add(this.randomBtn,BorderLayout.WEST);
    randomPanel.add(this.yourLuckWordLabel);
    this.maxKeyListLength = maxLength;
  }

  public void SetRandomButtonHandler(java.awt.event.ActionListener listener){
    this.randomBtn.addActionListener(listener);
  }

  public int GetRandomIndex() {
    return this.random.nextInt(this.maxKeyListLength);
  }

  public int getMaxKeyListLength() {
    return this.maxKeyListLength;
  }

  public void setMaxKeyListLength(int maxKeyListLength) {
    this.maxKeyListLength = maxKeyListLength;
  }

  public JPanel getRandomPanel() {
    return this.randomPanel;
  }

  public JButton getRandomBtn() {
    return this.randomBtn;
  }

  public JLabel getYourLuckWordLabel() {
    return this.yourLuckWordLabel;
  }

}
