package tictactoe;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends BaseWindow {
    private final int size;
    private final Model model;
    private final JLabel label;
    private final MainWindow mainWindow;
    private final JButton[][] buttons;
    private final int windowSize;

    public Window(int size, int windowSize, MainWindow mainWindow) {
        this.size = size;
        this.windowSize = windowSize;
        buttons = new JButton[size][size];
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        model = new Model(size);

        JPanel top = new JPanel();

        label = new JLabel();
        updateLabelText();

        JButton newGameButton = new JButton();
        newGameButton.setText("New Game");
        newGameButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }

        });

        top.add(label);
        top.add(newGameButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
                //changeTable(mainPanel, model.minusPieces());


            }
        }

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                updateButton(mainPanel, i, j);
                //changeTable(mainPanel, model.minusPieces());


            }
        }

        setSize(windowSize,windowSize);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, int i, int j) {

        final JButton button = new JButton();
        buttons[i][j] = button;
        panel.add(button);

    }


    private void updateButton(JPanel panel, int i, int j){

        buttons[i][j].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player newValue = model.step(i, j);
                buttons[i][j].setText(newValue.name());

                updateLabelText();

               Player[][] refresh = model.minusPieces(i,j);
                for (int x = 0; x < size; ++x) {
                    for (int y = 0; y < size; ++y) {
                        buttons[x][y].setText(refresh[x][y].toString());


                    }
                }

                boolean draw = model.isDraw();
                if(draw){
                    showDrawMessage();
                }


                Player winner = model.findWinner();
                if (winner != Player.NOBODY) {
                    showGameOverMessage(winner);
                }

            }
        });

        panel.add(buttons[i][j]);
    }

    private void showGameOverMessage(Player winner) {
        JOptionPane.showMessageDialog(this,
                "Game Over. The winner is: " + winner.name());
        newGame();
    }

    private void showDrawMessage() {
        JOptionPane.showMessageDialog(this,
                "Game Over. Draw!");
        newGame();
    }

    private void newGame() {
        Window newWindow = new Window(size, windowSize, mainWindow);
        newWindow.setVisible(true);

        this.dispose();
        mainWindow.getWindowList().remove(this);
    }

    private void updateLabelText() {
        label.setText("Actual Player: "
                + model.getActualPlayer().name());
    }

    @Override
    protected void doUponExit() {
        super.doUponExit();
        mainWindow.getWindowList().remove(this);
    }
}
