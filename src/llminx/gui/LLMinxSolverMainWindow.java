package llminx.gui;

import llminx.LLMinx;
import llminx.solver.LLMinxSolver;
import llminx.solver.StatusEvent;
import llminx.solver.StatusEventType;
import llminx.solver.StatusListener;
import llminx.solver.searchmode.LLMinxMetric;
import llminx.solver.searchmode.LLMinxSearchMode;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public class LLMinxSolverMainWindow extends JFrame implements StatusListener {

  private LLMinxCustomizerPanel fCustomizerPanel;
  private JComboBox fModes;
  private JTextArea fTextArea;
  private JCheckBox fLimitDepth;
  private JSpinner fDepthSpinner;
  private JButton fResetButton;
  private JButton fSolveCancelButton;
  private JLabel fStatusLabel;
  private JEditorPane fInfoPane;
  private JProgressBar fProgressBar;
  private ArrayList<Component> fDisabledComponents;
  private LLMinxSolver fMinxSolver;
  private Timer fStatusTimer;
  private boolean fFollowMessages = true;
  private boolean fLineAdded = true;

  public LLMinxSolverMainWindow(LLMinxSolver aSolver) throws HeadlessException {
    super("Last layer megaminx solver");
    getContentPane().setLayout(new GridBagLayout());
    fDisabledComponents = new ArrayList<Component>();

    fMinxSolver = aSolver;
    fMinxSolver.addStatusListener(this);

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.BOTH;
    c.gridheight = 4;
    fCustomizerPanel = new LLMinxCustomizerPanel(new LLMinx());
    JPanel customizer_panel = new JPanel(new GridLayout(1, 1));
    customizer_panel.add(fCustomizerPanel);
    customizer_panel.setPreferredSize(new Dimension(300, 300));
    customizer_panel.setMinimumSize(new Dimension(300, 300));
    customizer_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Starting position"));
    getContentPane().add(customizer_panel, c);

    c.gridx = 1;
    c.weightx = 1;
    c.gridheight = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.anchor = GridBagConstraints.NORTH;
    getContentPane().add(createModePanel(), c);

    c.gridy++;
    getContentPane().add(createMetricPanel(), c);

    c.gridy++;
    getContentPane().add(createDepthPanel(), c);

    c.gridy++;
    c.fill = GridBagConstraints.BOTH;
    getContentPane().add(createIgnorePiecesPanel(), c);

    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy++;
    c.anchor = GridBagConstraints.SOUTHEAST;
    c.fill = GridBagConstraints.HORIZONTAL;
    getContentPane().add(createButtonPanel(), c);

    c.fill = GridBagConstraints.BOTH;
    c.gridy++;
    c.weighty = 1;
    c.weightx = 1;
    getContentPane().add(createMessagePanel(), c);

    c.gridy++;
    c.weighty = 0;
    c.insets.left = 2;
    c.insets.right = 16;
    c.insets.top = 1;
    c.insets.bottom = 1;
    getContentPane().add(createStatusPanel(), c);

    pack();

    fStatusTimer = new Timer(500, new UpdateStatusListener());
    fStatusTimer.setInitialDelay(2000);
  }

  private JPanel createModePanel() {
    LLMinxSearchMode[] modes = LLMinxSearchMode.values();
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.VERTICAL;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1;
    panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Allowed faces"));
    fModes = new JComboBox();
    for (int i = 0; i < modes.length; i++) {
      LLMinxSearchMode mode = modes[i];
      fModes.addItem(mode);
    }
    fModes.setSelectedItem(fMinxSolver.getSearchMode());
    fModes.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent aItemEvent) {
        if (aItemEvent.getStateChange() == ItemEvent.SELECTED) {
          fMinxSolver.setSearchMode((LLMinxSearchMode) aItemEvent.getItem());
        }
      }
    });
    panel.add(fModes, c);
    fModes.setMinimumSize(fModes.getPreferredSize());    
    return panel;
  }

  private JPanel createDepthPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.fill = GridBagConstraints.VERTICAL;
    c.anchor = GridBagConstraints.WEST;
    fLimitDepth = new JCheckBox("Limit");
    fLimitDepth.setSelected(fMinxSolver.isLimitDepth());
    fLimitDepth.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent aActionEvent) {
        fMinxSolver.setLimitDepth(fLimitDepth.isSelected());
        fDepthSpinner.setEnabled(fLimitDepth.isSelected());
      }
    });
    panel.add(fLimitDepth, c);
    c.gridx++;
    c.weightx = 1;
    panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search depth"));
    fDepthSpinner = new JSpinner(new SpinnerNumberModel(fMinxSolver.getMaxDepth(), 1, 50, 1));
    fDepthSpinner.setEnabled(fLimitDepth.isSelected());
    fDepthSpinner.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        JSpinner spinner = (JSpinner) changeEvent.getSource();
        Integer value = (Integer) spinner.getValue();
        fMinxSolver.setMaxDepth(value.intValue());
      }
    });
    panel.add(fDepthSpinner, c);

    return panel;
  }

  private JPanel createMetricPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.VERTICAL;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1;
    panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Metric"));
    JComboBox metric_box = new JComboBox(LLMinxMetric.values());
    metric_box.setSelectedItem(fMinxSolver.getMetric());
    metric_box.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent itemEvent) {
        fMinxSolver.setMetric((LLMinxMetric) itemEvent.getItem());
      }
    });
    panel.add(metric_box, c);
    metric_box.setMinimumSize(metric_box.getPreferredSize());
    fDisabledComponents.add(metric_box);
    return panel;
  }

  private JPanel createIgnorePiecesPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = GridBagConstraints.REMAINDER;
    c.weightx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ignore"));
    JCheckBox ignore_corner_positions = new JCheckBox(new ToggleIgnoreCornerPositionsAction());
    ignore_corner_positions.setSelected(fMinxSolver.isIgnoreCornerPositions());
    panel.add(ignore_corner_positions, c);
    fDisabledComponents.add(ignore_corner_positions);
    JCheckBox ignore_edge_positions = new JCheckBox(new ToggleIgnoreEdgePositionsAction());
    ignore_edge_positions.setSelected(fMinxSolver.isIgnoreEdgePositions());
    panel.add(ignore_edge_positions, c);
    fDisabledComponents.add(ignore_edge_positions);
    JCheckBox ignore_corner_orientations = new JCheckBox(new ToggleIgnoreCornerOrientationsAction());
    ignore_corner_orientations.setSelected(fMinxSolver.isIgnoreCornerOrientations());
    panel.add(ignore_corner_orientations, c);
    fDisabledComponents.add(ignore_corner_orientations);
    JCheckBox ignore_edge_orientations = new JCheckBox(new ToggleIgnoreEdgeOrientationsAction());
    ignore_edge_orientations.setSelected(fMinxSolver.isIgnoreEdgeOrientations());
    panel.add(ignore_edge_orientations, c);
    fDisabledComponents.add(ignore_edge_orientations);
    c.weighty = 1;
    panel.add(new JLabel(), c);
    return panel;
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets.left = 4;
    c.insets.right = 2;
    c.weightx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    fInfoPane = new JEditorPane("text/html", "<html><i>Version 1.0. Developed by <a href=\"mailto:lars@qblog.be\">Lars Vandenbergh</a></i></html>");
    fInfoPane.setEditable(false);
    fInfoPane.setHighlighter(null);
    fInfoPane.setBackground(panel.getBackground());
    fInfoPane.addMouseListener( new CharlieListener());
    fInfoPane.addHyperlinkListener(new HyperlinkListener() {

      public void hyperlinkUpdate(HyperlinkEvent aHyperlinkEvent) {
        if (HyperlinkEvent.EventType.ACTIVATED.equals(aHyperlinkEvent.getEventType())) {
          try {
            Desktop.getDesktop().browse(new URI(aHyperlinkEvent.getURL().toString()));
          }
          catch (IOException e) {
            e.printStackTrace();
          }
          catch (URISyntaxException e) {
            e.printStackTrace();
          }
        }
      }
    });
    panel.add(fInfoPane, c);
    fResetButton = new JButton(new ResetAction());
    c.weightx = 0;
    c.insets.left = 0;
    panel.add(fResetButton, c);
    fSolveCancelButton = new JButton(new SolveAction());
    panel.add(fSolveCancelButton, c);
    return panel;
  }

  private JPanel createMessagePanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Solving status"));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = GridBagConstraints.REMAINDER;
    c.weightx = 1;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    fTextArea = new JTextArea();
    fTextArea.setEditable(false);
    fTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    final JScrollPane text_area = new JScrollPane(fTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    fTextArea.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent aComponentEvent) {
        if (fFollowMessages && fLineAdded) {
          fLineAdded = false;
          text_area.getVerticalScrollBar().setValue(text_area.getVerticalScrollBar().getMaximum());
        }
      }
    });
    text_area.setPreferredSize(new Dimension(500, 200));
    text_area.setMinimumSize(new Dimension(500, 200));
    text_area.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    text_area.setBackground(getBackground());
    panel.add(text_area, c);
    JCheckBox follow_messages = new JCheckBox(new AbstractAction("Follow messages") {
      public void actionPerformed(ActionEvent aActionEvent) {
        fFollowMessages = ((JCheckBox) aActionEvent.getSource()).isSelected();
      }
    });
    follow_messages.setSelected(fFollowMessages);
    c.weighty = 0;
    panel.add(follow_messages, c);
    return panel;
  }

  private JPanel createStatusPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets.right = 2;
    c.weightx = 1;
    c.fill = GridBagConstraints.BOTH;
    fStatusLabel = new JLabel(" ");
    panel.add(fStatusLabel, c);
    c.weightx = 0;
    fProgressBar = new JProgressBar(0, 1000);
    fProgressBar.setVisible(false);
    fProgressBar.setStringPainted(true);
    fProgressBar.setMinimumSize(fProgressBar.getPreferredSize());
    panel.add(fProgressBar, c);
    fStatusLabel.setPreferredSize(fProgressBar.getPreferredSize());
    return panel;
  }

  public void statusEvent(StatusEvent aEvent) {
    if (aEvent.getType() == StatusEventType.START_DEPTH ||
      aEvent.getType() == StatusEventType.END_DEPTH ||
      aEvent.getType() == StatusEventType.START_BUILDING_TABLE) {
      fStatusLabel.setText(aEvent.getMessage());
      updateProgress();
      if (!fStatusTimer.isRunning())
        fStatusTimer.start();
    }
    else if (aEvent.getType() == StatusEventType.END_BUILDING_TABLE) {
      fProgressBar.setVisible(false);
      fStatusTimer.stop();
      fStatusLabel.setText("Initializing search...");
    }
    else if (aEvent.getType() == StatusEventType.MESSAGE) {
      fTextArea.append(aEvent.getMessage());
      fTextArea.append("\n");
      if (fFollowMessages) fLineAdded = true;
    }
  }

  private void enableComponents() {
    fCustomizerPanel.setEnabled(true);
    fModes.setEnabled(true);
    fSolveCancelButton.setAction(new SolveAction());
    fSolveCancelButton.setEnabled(true);
    fLimitDepth.setEnabled(true);
    fDepthSpinner.setEnabled(fLimitDepth.isSelected());
    fResetButton.setEnabled(true);
    Iterator<Component> disabled_components = fDisabledComponents.iterator();
    while (disabled_components.hasNext()) {
      disabled_components.next().setEnabled(true);
    }
  }

  private void disableComponents() {
    fCustomizerPanel.setEnabled(false);
    fModes.setEnabled(false);
    fSolveCancelButton.setAction(new CancelAction());
    fSolveCancelButton.setEnabled(true);
    fLimitDepth.setEnabled(false);
    fDepthSpinner.setEnabled(false);
    fResetButton.setEnabled(false);
    Iterator<Component> disabled_components = fDisabledComponents.iterator();
    while (disabled_components.hasNext()) {
      disabled_components.next().setEnabled(false);
    }
  }

  private class ResetAction extends AbstractAction {

    public ResetAction() {
      super("Reset");
    }

    public void actionPerformed(ActionEvent actionEvent) {
      LLMinx minx = new LLMinx();
      minx.setIgnoreCornerPositions(fCustomizerPanel.getMinx().getIgnoreCornerPositions());
      minx.setIgnoreEdgePositions(fCustomizerPanel.getMinx().getIgnoreEdgePositions());
      minx.setIgnoreCornerOrientations(fCustomizerPanel.getMinx().getIgnoreCornerOrientations());
      minx.setIgnoreEdgeOrientations(fCustomizerPanel.getMinx().getIgnoreEdgeOrientations());
      fCustomizerPanel.setMinx(minx);
    }

  }

  private class SolveAction extends AbstractAction {

    public SolveAction() {
      super("Solve");
    }

    public void actionPerformed(ActionEvent actionEvent) {
      disableComponents();
      fTextArea.setText("");
      fStatusLabel.setText("Initializing search...");
      fMinxSolver.setStart(fCustomizerPanel.getMinx());
      Runnable solve_task = new Runnable() {
        public void run() {
          fMinxSolver.solve();
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              fProgressBar.setVisible(false);
              fStatusLabel.setText("Done");
              fStatusTimer.stop();
              enableComponents();
            }
          });
        }
      };
      Thread solve_thread = new Thread(solve_task);
      solve_thread.start();
    }

  }

  private class CancelAction extends AbstractAction {

    public CancelAction() {
      super("Cancel");
    }

    public void actionPerformed(ActionEvent actionEvent) {
      fMinxSolver.interupt();
      fSolveCancelButton.setEnabled(false);
    }

  }

  private void updateProgress() {
    fProgressBar.setValue((int) (fMinxSolver.getProgress() * 1000));
    fProgressBar.setString(((int) (fMinxSolver.getProgress() * 1000) / 10d) + "%");
  }

  private class UpdateStatusListener implements ActionListener {
    public void actionPerformed(ActionEvent actionEvent) {
      updateProgress();
      fProgressBar.setVisible(true);
    }
  }

  private class ToggleIgnoreCornerPositionsAction extends AbstractAction {


    public ToggleIgnoreCornerPositionsAction() {
      super("Corner positions");
    }

    public void actionPerformed(ActionEvent aEvent) {
      boolean ignore = ((JCheckBox) aEvent.getSource()).isSelected();
      fMinxSolver.setIgnoreCornerPositions(ignore);
      LLMinx minx = fCustomizerPanel.getMinx();
      minx.setIgnoreCornerPositions(
        new boolean[]{
          ignore, ignore, ignore, ignore, ignore, false, false, false, false, false, false, false, false, false
        }
      );
      fCustomizerPanel.setMinx(minx);
    }

  }

  private class ToggleIgnoreEdgePositionsAction extends AbstractAction {


    public ToggleIgnoreEdgePositionsAction() {
      super("Edge positions");
    }

    public void actionPerformed(ActionEvent aEvent) {
      boolean ignore = ((JCheckBox) aEvent.getSource()).isSelected();
      fMinxSolver.setIgnoreEdgePositions(ignore);
      LLMinx minx = fCustomizerPanel.getMinx();
      minx.setIgnoreEdgePositions(
        new boolean[]{
          ignore, ignore, ignore, ignore, ignore, false, false, false, false, false, false, false, false, false, false, false, false, false
        }
      );
      fCustomizerPanel.setMinx(minx);
    }

  }

  private class ToggleIgnoreCornerOrientationsAction extends AbstractAction {


    public ToggleIgnoreCornerOrientationsAction() {
      super("Corner orientations");
    }

    public void actionPerformed(ActionEvent aEvent) {
      boolean ignore = ((JCheckBox) aEvent.getSource()).isSelected();
      fMinxSolver.setIgnoreCornerOrientations(ignore);
      LLMinx minx = fCustomizerPanel.getMinx();
      minx.setIgnoreCornerOrientations(
        new boolean[]{
          ignore, ignore, ignore, ignore, ignore, false, false, false, false, false, false, false, false, false
        }
      );
      fCustomizerPanel.setMinx(minx);
    }

  }

  private class ToggleIgnoreEdgeOrientationsAction extends AbstractAction {


    public ToggleIgnoreEdgeOrientationsAction() {
      super("Edge orientations");
    }

    public void actionPerformed(ActionEvent aEvent) {
      boolean ignore = ((JCheckBox) aEvent.getSource()).isSelected();
      fMinxSolver.setIgnoreEdgeOrientations(ignore);
      LLMinx minx = fCustomizerPanel.getMinx();
      minx.setIgnoreEdgeOrientations(
        new boolean[]{
          ignore, ignore, ignore, ignore, ignore, false, false, false, false, false, false, false, false, false, false, false, false, false
        }
      );
      fCustomizerPanel.setMinx(minx);
    }

  }

  private class CharlieListener extends MouseAdapter{

    private int fCount;

    public void mouseClicked( MouseEvent e ) {
      fCount++;
      if(fCount==42){
        fInfoPane.setText( "<html><font color=\"#888800\"><b>Charliebananananananananana :)</b></font></html>" );
      }
    }
  }
}
