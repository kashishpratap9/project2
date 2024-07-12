package nqueens;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NQueensVisualizer extends JPanel {
    private int[] queens;
    private int n;
    private static final int CELL_SIZE = 80; // Increased cell size
    private static final int DELAY = 390; // Delay in milliseconds
    private List<int[]> solutions = new ArrayList<>();

    public NQueensVisualizer(int n) {
        this.n = n;
        queens = new int[n];
        for (int i = 0; i < n; i++) {
            queens[i] = -1;
        }
        setPreferredSize(new Dimension(n * CELL_SIZE, n * CELL_SIZE));
    }

    public void findAllSolutions() {
        solve(0);
    }

    private boolean solve(int row) {
        if (row == n) {
            int[] solution = queens.clone();
            solutions.add(solution);
            repaintAndSleep();
            showSolutionFoundPopup();
            return true;
        }
        boolean found = false;
        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                repaintAndSleep();
                if (solve(row + 1)) {
                    found = true;
                }
                queens[row] = -1; // Reset the row when backtracking
                repaintAndSleep();
            }
        }
        return found;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    private void repaintAndSleep() {
        repaint();
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            e.printStackTrace();
        }
    }

    private void showSolutionFoundPopup() {
        JOptionPane.showMessageDialog(this, "Solution found!");
    }

    private void showTotalSolutions() {
        JOptionPane.showMessageDialog(this, "Total solutions: " + solutions.size());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color lightColor = new Color(255, 248, 220); // Cream color
        Color darkColor = new Color(139, 69, 19); // Dark brown color
        Color queenColor = new Color(222, 184, 135); // Dark white (light gray) for queen
        Color highlightColor = new Color(255, 215, 0); // Darker yellow for highlighting

        // Set the thickness of the border
        int borderThickness = 4;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(lightColor);
                } else {
                    g.setColor(darkColor);
                }
                g.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (queens[row] == col) {
                    // Draw a border around the square
                    g.setColor(highlightColor);
                    ((Graphics2D) g).setStroke(new BasicStroke(borderThickness));
                    g.drawRect(col * CELL_SIZE + borderThickness / 2, row * CELL_SIZE + borderThickness / 2,
                            CELL_SIZE - borderThickness, CELL_SIZE - borderThickness);

                    g.setColor(queenColor);
                    g.setFont(new Font("SansSerif", Font.BOLD, CELL_SIZE / 2));
                    FontMetrics fm = g.getFontMetrics();
                    String queenSymbol = "♛";
                    int x = col * CELL_SIZE + (CELL_SIZE - fm.stringWidth(queenSymbol)) / 2;
                    int y = row * CELL_SIZE + (CELL_SIZE + fm.getAscent()) / 2;
                    g.drawString(queenSymbol, x, y);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String input = JOptionPane.showInputDialog("Enter the size of the board (n):");
            int n;
            try {
                n = Integer.parseInt(input);
                if (n <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer.");
                return;
            }

            NQueensVisualizer visualizer = new NQueensVisualizer(n);
            JFrame frame = new JFrame("N-Queens Solver");
            frame.add(visualizer);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            new Thread(() -> {
                visualizer.findAllSolutions();
             //   visualizer.printAllSolutions();
                visualizer.showTotalSolutions();
            }).start();
        });
    }
}
