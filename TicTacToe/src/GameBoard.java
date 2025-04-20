public class GameBoard {
    public char[][] board;
    public boolean gameOver;
    public int playerWins = 0;
    public int computerWins = 0;
    public int draws = 0;

    public GameBoard() {
        board = new char[3][3];
        resetBoard();
    }

    public void resetBoard() {
        gameOver = false;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    public boolean makeMove(int row, int col, char player) {
        if (board[row][col] == ' ' && !gameOver) {
            board[row][col] = player;
            return true;
        }
        return false;
    }

    public char checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (same(board[i][0], board[i][1], board[i][2])) return board[i][0];
            if (same(board[0][i], board[1][i], board[2][i])) return board[0][i];
        }
        if (same(board[0][0], board[1][1], board[2][2])) return board[0][0];
        if (same(board[0][2], board[1][1], board[2][0])) return board[0][2];
        return isFull() ? 'D' : ' ';
    }

    private boolean same(char a, char b, char c) {
        return a != ' ' && a == b && b == c;
    }

    private boolean isFull() {
        for (char[] row : board)
            for (char c : row)
                if (c == ' ')
                    return false;
        return true;
    }
}

