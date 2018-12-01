import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.util.Random;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class WordSearch extends JPanel{
  public static final int WIDTH = 1024;
  public static final int HEIGHT = 768;
  private Board board;

public WordSearch(){}
/////////////////////////////////////////
////// BOARD CLASS
/////////////////////////////////////////
// class Board{
//       public Board() {
//
//       }
//
//       array = new Block[rows][columns];
//
//       // Create the Cells, all in their default states.
//       for (int row = 0; row < rows; row += 1) {
//           for (int col = 0; col < columns; col += 1) {
//         _array[row][col] = new Cell(this, row, col);
//           }
//       }
//
//     } // class Board

/////////////////////////////////////////
////// BLOCK CLASS
/////////////////////////////////////////

// class Block{
//   public Block(Board board, int row, int column) {}
//
// // Set the initial cell to be empty.
// content = '';
//
//
//
// } // class Block

public static ArrayList getCategory()

public static void main(String[] args){
  JFrame frame = new JFrame("Word Search");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  WordSearch mainInstance = new WordSearch();
  frame.setContentPane(mainInstance);
  frame.pack();
  frame.setVisible(true);
}

public void paintComponent(Graphics g) {
  super.paintComponent(g);
  g.setColor(Color.BLACK);
  g.fillRect(0, 0, WIDTH, HEIGHT);
  g.setColor(Color.WHITE);
  for(int i = 0; i < HEIGHT; i = i + 50){
    for(int j = 0; j < WIDTH; i = i + 50)
      g.fillRect(j, i, WIDTH, HEIGHT);
  }
}

} // WordSearch
