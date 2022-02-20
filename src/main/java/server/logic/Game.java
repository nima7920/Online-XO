package server.logic;

import java.util.Arrays;

public class Game {

    private PlayerHandler currentPlayerHandler;
    private PlayerHandler[][] board = new PlayerHandler[7][7];

    public Game(PlayerHandler playerHandler1, PlayerHandler playerHandler2) {
        currentPlayerHandler = playerHandler1;
        playerHandler1.setMark('X');
        playerHandler2.setMark('O');
        playerHandler1.setOpponentPlayerHandler(playerHandler2);
        playerHandler2.setOpponentPlayerHandler(playerHandler1);
        playerHandler1.setCurrentGame(this);
        playerHandler2.setCurrentGame(this);
    }

    synchronized int selectCell(PlayerHandler playerHandler, int i, int j) {
        if (board[i][j] != null) {
            return 0;
        } else if (playerHandler != currentPlayerHandler) {
            return -1;
        }
        board[i][j] = currentPlayerHandler;
        currentPlayerHandler = currentPlayerHandler.getOpponentPlayerHandler();
        return 1;
    }

    synchronized int isWinner(PlayerHandler playerHandler) {
        // checking horizontal and vertical
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == playerHandler &&
                        board[i][j + 1] == playerHandler &&
                        board[i][j + 2] == playerHandler &&
                        board[i][j + 3] == playerHandler)
                    return 1;
                if (board[j][i] == playerHandler &&
                        board[j+1][i] == playerHandler &&
                        board[j+2][i] == playerHandler &&
                        board[j+3][i] == playerHandler)
                    return 1;

                if(i<4 && board[i][j] == playerHandler &&
                        board[i+1][j + 1] == playerHandler &&
                        board[i+2][j + 2] == playerHandler &&
                        board[i+3][j + 3] == playerHandler)
                    return 1;
                if(i>2 && board[i][j] == playerHandler &&
                        board[i-1][j + 1] == playerHandler &&
                        board[i-2][j + 2] == playerHandler &&
                        board[i-3][j + 3] == playerHandler)
                    return 1;
            }
        }
        return 0;
    }

    synchronized boolean isBoardFull() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public PlayerHandler getCurrentPlayerHandler() {
        return currentPlayerHandler;
    }
}
