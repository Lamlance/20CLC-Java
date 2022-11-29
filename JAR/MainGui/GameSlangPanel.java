package MainGui;

import javax.swing.*;
import java.awt.*;

public class GameSlangPanel {
  private JPanel gamePanel = new JPanel(new BorderLayout());

  private JLabel questionLbl = new JLabel();

  private JButton optionBtnA = new JButton("Click me to start");
  private JButton optionBtnB = new JButton("Click me to start");
  private JButton optionBtnC = new JButton("Click me to start");
  private JButton optionBtnD = new JButton("Click me to start");

  private JLabel scoreLbl = new JLabel("Click any option to start");
  private JButton restartBtn = new JButton("Restart Game");
  private int score = -1;
  private String correectDef = "Click me to start";

  GameSlangPanel() {
    gamePanel.add(this.questionLbl, BorderLayout.PAGE_START);

    JPanel btnsPanel = new JPanel(new GridLayout(2, 2));
    btnsPanel.add(this.optionBtnA);
    btnsPanel.add(this.optionBtnB);
    btnsPanel.add(this.optionBtnC);
    btnsPanel.add(this.optionBtnD);
    gamePanel.add(btnsPanel, BorderLayout.CENTER);

    JPanel actionPanel = new JPanel(new BorderLayout());
    actionPanel.add(this.restartBtn, BorderLayout.WEST);
    actionPanel.add(this.scoreLbl, BorderLayout.EAST);
    gamePanel.add(actionPanel, BorderLayout.PAGE_END);
  }

  public void SetCorrectAnswer(String correctAnswer) {
    this.correectDef = correctAnswer;
  }

  public void SetQuestion(String questionSlang) {
    this.questionLbl.setText(String.format("What is the %s ?", questionSlang));
  }

  public boolean SetOptions(String[] optionString) {
    if (optionString.length > 4) {
      return false;
    }

    this.optionBtnA.setText(optionString[0]);
    this.optionBtnB.setText(optionString[1]);
    this.optionBtnC.setText(optionString[2]);
    this.optionBtnD.setText(optionString[3]);

    return true;
  }

  public void CalculateAnswer(String answer) {
    this.score += (answer.equals(this.correectDef)) ? 1 : 0;
    this.scoreLbl.setText(String.format("Your score: %d", this.score));
  }

  public void RestartGame() {
    questionLbl.setText("");

    optionBtnA.setText("Click me to start");
    optionBtnB.setText("Click me to start");
    optionBtnC.setText("Click me to start");
    optionBtnD.setText("Click me to start");

    scoreLbl.setText("Click any option to start");
    score = -1;
    correectDef = "Click me to start";
  }

  public void SetOptionButtonActionListener(java.awt.event.ActionListener listener) {
    this.optionBtnA.addActionListener(listener);
    this.optionBtnB.addActionListener(listener);
    this.optionBtnC.addActionListener(listener);
    this.optionBtnD.addActionListener(listener);
  }

  public void SetResetButtonActionListener(java.awt.event.ActionListener listener) {
    this.restartBtn.addActionListener(listener);
  }

  public JPanel getGanePanel(){
    return this.gamePanel;
  }
}
