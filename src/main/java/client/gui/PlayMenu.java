package client.gui;

import client.util.ClientModerator;
import client.util.ConfigLoader;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayMenu extends GameMenu {

    private Actions actions;
    private ClientModerator clientModerator;
    private Square[][] board = new Square[7][7];
    private Square currentSquare;

    public PlayMenu(ClientModerator clientModerator) {
        this.clientModerator = clientModerator;
        this.actions = new Actions();
        configLoader = new ConfigLoader("src//main//java//client//configs//playMenu.properties");
        initMenu();
    }

    private void initMenu() {
        setLayout(new GridLayout(7, 7, 2, 2));
        setSize(configLoader.getSize("menuSize"));
        initSquares();
    }

    private void initSquares() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = new Square();
                board[i][j].addMouseListener(actions);
                add(board[i][j]);
            }
        }
    }

    public void refresh() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j].setText(' ');
            }
        }
    }

    public void selectSquare(char mark) {
        if (currentSquare != null) {
            currentSquare.setText(mark);
        }
    }

    public void opponentSelected(int i, int j, char mark) {
        board[i][j].setText(mark);
    }


    private class Actions implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (mouseEvent.getSource() == board[i][j]) {
                        currentSquare = board[i][j];
                        clientModerator.selectSquare(i, j);
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
}
