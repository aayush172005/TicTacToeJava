import java.util.*;

public class AIPlayer {
    private Random rand = new Random();

    public int[] getEasyMove(GameBoard game) {
        ArrayList<int[]> available = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (game.board[i][j] == ' ')
                    available.add(new int[]{i, j});
        return available.get(rand.nextInt(available.size()));
    }

    public int[] getMediumMove(GameBoard game) {
        // Try to win
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.board[i][j] = 'O';
                    if (game.checkWinner() == 'O') {
                        game.board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    game.board[i][j] = ' ';
                }
            }
        // Try to block
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.board[i][j] = 'X';
                    if (game.checkWinner() == 'X') {
                        game.board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    game.board[i][j] = ' ';
                }
            }
        return getEasyMove(game);
    }

    public int[] getHardMove(GameBoard game) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.board[i][j] = 'O';
                    int score = minimax(game, false);
                    game.board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{i, j};
                    }
                }
            }
        }
        return move;
    }

    private int minimax(GameBoard game, boolean isMaximizing) {
        char winner = game.checkWinner();
        if (winner == 'O') return 1;
        if (winner == 'X') return -1;
        if (winner == 'D') return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char player = isMaximizing ? 'O' : 'X';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.board[i][j] == ' ') {
                    game.board[i][j] = player;
                    int score = minimax(game, !isMaximizing);
                    game.board[i][j] = ' ';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }
}