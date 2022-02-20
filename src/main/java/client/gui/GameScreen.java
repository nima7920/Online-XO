package client.gui;

import client.util.ClientModerator;

import javax.swing.*;
import java.awt.*;

public class GameScreen {

    private ClientModerator clientModerator;

    private LoginMenu loginMenu;
    private MainMenu mainMenu;
    private PlayMenu playMenu;
    private char mark;
    private String opponentName;
    private JLabel playMessageLabel, playCharLabel;
    private ScoreBoardMenu scoreBoardMenu;
    private InfoMenu infoMenu;
    private JFrame frame;


    public GameScreen(ClientModerator clientModerator) {
        this.clientModerator = clientModerator;
//        loginMenu=new LoginMenu(clientModerator);
//        playMenu=new PlayMenu(clientModerator);
        initFrame();
    }

    private void initFrame() {
        frame = new JFrame("7*7 Tic Tac Toe");
//        playMessageLabel=new JLabel("Message");
        frame.setLayout(new BorderLayout());
//        frame.getContentPane().add(playMenu, BorderLayout.CENTER);
//        frame.getContentPane().add(playMessageLabel,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setPreferredSize(new Dimension(600,400));
//        frame.setSize(playMenu.getSize());
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
    }

    public void gotoLoginMenu() {
        frame.getContentPane().removeAll();

        if (loginMenu == null)
            loginMenu = new LoginMenu(clientModerator);
        frame.setSize(loginMenu.getSize());
        frame.getContentPane().add(loginMenu, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void gotoMainMenu() {
        frame.getContentPane().removeAll();
        if (mainMenu == null)
            mainMenu = new MainMenu(clientModerator);
        frame.setSize(mainMenu.getSize());
        frame.getContentPane().add(mainMenu, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void gotoScoreBoardMenu() {
        frame.getContentPane().removeAll();

        if (scoreBoardMenu == null)
            scoreBoardMenu = new ScoreBoardMenu(clientModerator);

        frame.setSize(scoreBoardMenu.getSize());
        frame.getContentPane().add(scoreBoardMenu, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void updateScoreBoard(String[][] contents) {
        scoreBoardMenu.update(contents);
    }

    public void gotoInfoMenu() {
        frame.getContentPane().removeAll();
        if (infoMenu == null)
            infoMenu = new InfoMenu(clientModerator);

        frame.setSize(infoMenu.getSize());
        frame.getContentPane().add(infoMenu, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void refreshInfoPanel(String username,int wins,int loses,int score){
        infoMenu.refresh(username,wins,loses,score);
    }
    public void gotoPlayMenu() {
        frame.getContentPane().removeAll();

        if (playMenu == null) {
            playMenu = new PlayMenu(clientModerator);
        }
        playMessageLabel = new JLabel("Waiting for opponent");
        playCharLabel = new JLabel("");
        frame.setSize(playMenu.getSize());
        frame.getContentPane().add(playMenu, BorderLayout.CENTER);
        playMenu.refresh();
        frame.getContentPane().add(playCharLabel, BorderLayout.NORTH);
        frame.getContentPane().add(playMessageLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // methods for the main game
    public void startGame(String opponentName, String mark, boolean turn) {
        System.out.println("mark is:" + mark);
        this.opponentName = opponentName;
        this.mark = mark.charAt(0);
        if (turn)
            playCharLabel.setText(mark + "     " + "your turn");
        else
            playCharLabel.setText(mark + "     " + "opponents turn");
        playMessageLabel.setText(opponentName);
    }

    public void updateLabels(boolean turn) {
        if (turn)
            playCharLabel.setText(mark + "     " + "your turn");
        else
            playCharLabel.setText(mark + "     " + "opponents turn");
    }

    public void selectSquare() {
        playMenu.selectSquare(mark);
    }

    public void opponentSelected(int i, int j) {
        char opponentMark = (mark == 'X' ? 'O' : 'X');
        playMenu.opponentSelected(i, j, opponentMark);
    }

    public void showMessage(String title, String content) {
        JOptionPane.showMessageDialog(null, content, title, JOptionPane.OK_OPTION);
    }
}
