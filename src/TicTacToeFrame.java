import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame
{
    private final int ROW = 3;
    private final int COL = 3;

    private TicTacToeTile[][] tiles = new TicTacToeTile[ROW][COL];
    private TicTacToeBoard board;

    private String player = "X";
    private int moveCnt = 0;

    private final int MOVES_FOR_WIN = 5;
    private final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame()
    {
        board = new TicTacToeBoard();
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

            if(!board.isValidMove(row, col))
            {
                JOptionPane.showMessageDialog(null,
                        "Illegal move! Try again.");
                return;
            }

            board.makeMove(row, col, player);
            clicked.setText(player);
            moveCnt++;

            if(moveCnt >= MOVES_FOR_WIN && board.isWin(player))
            {
                JOptionPane.showMessageDialog(null,
                        "Player " + player + " wins!");
                playAgain();
                return;
            }

            if(moveCnt >= MOVES_FOR_TIE && board.isTie(moveCnt))
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
        board.clearBoard();

        for(int row=0; row<ROW; row++)
        {
            for(int col=0; col<COL; col++)
            {
                tiles[row][col].setText(" ");
            }
        }
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