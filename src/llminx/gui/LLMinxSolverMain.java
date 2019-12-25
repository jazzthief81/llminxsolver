package llminx.gui;

import llminx.gui.LLMinxSolverMainWindow;
import llminx.solver.LLMinxSolver;

import javax.swing.*;

/**
 *
 */
public class LLMinxSolverMain {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
          // We can always try.
        }
        LLMinxSolverMainWindow main_window = new LLMinxSolverMainWindow(new LLMinxSolver());          
        main_window.setVisible(true);
        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
    }
    );
  }

}
