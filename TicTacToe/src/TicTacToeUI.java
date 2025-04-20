// TicTacToeUI.java
import javax.swing.*;
import java.awt.*;

public class TicTacToeUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private GameBoard game;
    private AIPlayer ai;
    private JLabel status, scoreLabel;
    private JComboBox<String> difficultyBox;

    public TicTacToeUI() {
        game = new GameBoard();
        ai = new AIPlayer();
        setTitle("Tic Tac Toe - AI Levels 🎮");
        setSize(450, 550);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 48);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(font);
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
                int row = i, col = j;
                btn.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (!game.gameOver && btn.getText().equals(""))
                            btn.setBackground(new Color(220, 240, 255));
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if (!game.gameOver && btn.getText().equals(""))
                            btn.setBackground(Color.WHITE);
                    }
                });
                btn.addActionListener(e -> handleClick(row, col));
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }

        JPanel topPanel = new JPanel(new BorderLayout());
        status = new JLabel("Your turn (❌)", JLabel.CENTER);
        status.setFont(new Font("Arial", Font.PLAIN, 20));
        String[] levels = {"Easy", "Medium", "Hard"};
        difficultyBox = new JComboBox<>(levels);
        topPanel.add(status, BorderLayout.CENTER);
        topPanel.add(difficultyBox, BorderLayout.EAST);

        scoreLabel = new JLabel("Wins: 0 | Losses: 0 | Draws: 0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton resetBtn = new JButton("🔄 Reset Game");
        resetBtn.addActionListener(e -> resetGame());

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.add(scoreLabel);
        bottomPanel.add(resetBtn);

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handleClick(int row, int col) {
        if (game.makeMove(row, col, 'X')) {
            updateBoard();
            char result = game.checkWinner();
            if (result != ' ') {
                endGame(result);
                return;
            }

            int[] move;
            String level = (String) difficultyBox.getSelectedItem();
            switch (level) {
                case "Medium": move = ai.getMediumMove(game); break;
                case "Hard": move = ai.getHardMove(game); break;
                default: move = ai.getEasyMove(game); break;
            }

            game.makeMove(move[0], move[1], 'O');
            updateBoard();
            result = game.checkWinner();
            if (result != ' ') endGame(result);
        }
    }

    private void updateBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                char symbol = game.board[i][j];
                buttons[i][j].setText(symbol == 'X' ? " X " : symbol == 'O' ? " O " : "");
            }
        status.setText("Your turn ( X )");
    }

    private void endGame(char winner) {
        game.gameOver = true;
        if (winner == 'X') {
            status.setText(" You Win!!!! ");
            game.playerWins++;
        } else if (winner == 'O') {
            status.setText("🤖 Computer Wins!");
            game.computerWins++;
        } else {
            status.setText("😐 It's a Draw!");
            game.draws++;
        }
        updateScore();
        highlightWinningLine(winner);
    }

    private void updateScore() {
        scoreLabel.setText("Wins: " + game.playerWins + " | Losses: " + game.computerWins + " | Draws: " + game.draws);
    }

    private void highlightWinningLine(char winner) {
        if (winner == ' ') return;
        Color winColor = new Color(200, 255, 200);

        for (int i = 0; i < 3; i++) {
            if (game.board[i][0] == winner && game.board[i][1] == winner && game.board[i][2] == winner) {
                for (int j = 0; j < 3; j++) buttons[i][j].setBackground(winColor);
                return;
            }
            if (game.board[0][i] == winner && game.board[1][i] == winner && game.board[2][i] == winner) {
                for (int j = 0; j < 3; j++) buttons[j][i].setBackground(winColor);
                return;
            }
        }
        if (game.board[0][0] == winner && game.board[1][1] == winner && game.board[2][2] == winner) {
            for (int i = 0; i < 3; i++) buttons[i][i].setBackground(winColor);
            return;
        }
        if (game.board[0][2] == winner && game.board[1][1] == winner && game.board[2][0] == winner) {
            buttons[0][2].setBackground(winColor);
            buttons[1][1].setBackground(winColor);
            buttons[2][0].setBackground(winColor);
        }
    }

    private void resetGame() {
        game.resetBoard();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        status.setText("Your turn ( X )");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeUI().setVisible(true));
    }
}
