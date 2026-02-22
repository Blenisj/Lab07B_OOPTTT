import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame
{
    private final int ROW = 3;
    private final int COL = 3;

    private TicTacToeTile[][] tiles = new TicTacToeTile[ROW][COL];
    private String[][] board = new String[ROW][COL];

    private String player = "X";
    private int moveCnt = 0;

    private final int MOVES_FOR_WIN = 5;
    private final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame()
    {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3,3));

        ButtonListener listener = new ButtonListener();

        for(int row = 0; row < ROW; row++)
        {
            for(int col = 0; col < COL; col++)
            {
                tiles[row][col] = new TicTacToeTile(row, col);
                tiles[row][col].addActionListener(listener);
                boardPanel.add(tiles[row][col]);
            }
        }

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));

        add(boardPanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        clearBoard();
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            TicTacToeTile clicked = (TicTacToeTile) e.getSource();
            int row = clicked.getRow();
            int col = clicked.getCol();

            if(!isValidMove(row, col))
            {
                JOptionPane.showMessageDialog(null,
                        "Illegal move! Try again.");
                return;
            }

            board[row][col] = player;
            clicked.setText(player);
            moveCnt++;

            if(moveCnt >= MOVES_FOR_WIN && isWin(player))
            {
                JOptionPane.showMessageDialog(null,
                        "Player " + player + " wins!");
                playAgain();
                return;
            }

            if(moveCnt >= MOVES_FOR_TIE && isTie())
            {
                JOptionPane.showMessageDialog(null,
                        "tie!");
                playAgain();
                return;
            }

            switchPlayer();
        }
    }

    private void switchPlayer()
    {
        if(player.equals("X"))
            player = "O";
        else
            player = "X";
    }

    private void clearBoard()
    {
        moveCnt = 0;
        player = "X";

        for(int row=0; row<ROW; row++)
        {
            for(int col=0; col<COL; col++)
            {
                board[row][col] = " ";
                tiles[row][col].setText(" ");
            }
        }
    }

    private boolean isValidMove(int row, int col)
    {
        return board[row][col].equals(" ");
    }

    private boolean isWin(String player)
    {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player)
    {
        for(int row=0; row<ROW; row++)
        {
            if(board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player))
                return true;
        }
        return false;
    }

    private boolean isColWin(String player)
    {
        for(int col=0; col<COL; col++)
        {
            if(board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player))
                return true;
        }
        return false;
    }

    private boolean isDiagonalWin(String player)
    {
        if(board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player))
            return true;

        if(board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player))
            return true;

        return false;
    }

    private boolean isTie()
    {
        if(moveCnt == 9)
            return true;

        return false;
    }

    private void playAgain()
    {
        int choice = JOptionPane.showConfirmDialog(null,
                "Play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);

        if(choice == JOptionPane.YES_OPTION)
        {
            clearBoard();
        }
        else
        {
            System.exit(0);
        }
    }
}