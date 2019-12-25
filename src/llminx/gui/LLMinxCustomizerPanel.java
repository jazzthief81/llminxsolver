package llminx.gui;

import llminx.LLMinx;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 */
public class LLMinxCustomizerPanel extends JPanel implements ComponentListener {

  private LLMinx fMinx;
  private Collection<LLMinxSticker> fHotSpots;
  private LLMinxSticker fSelection;
  private LLMinxSticker fInteraction;

  public LLMinxCustomizerPanel(LLMinx minx) {
    fMinx = minx;
    fHotSpots = new Vector<LLMinxSticker>();
    addComponentListener(this);
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent mouseEvent) {
        if (!isEnabled()) return;
        performSelection(mouseEvent);
      }

      public void mouseDragged(MouseEvent mouseEvent) {
        if (!isEnabled()) return;
        LLMinxSticker sticker_under_mouse = getStickerUnderMouse(mouseEvent);
        if (fSelection != null &&
          sticker_under_mouse != null &&
          sticker_under_mouse.interactsWith(fSelection, fMinx)) {
          fInteraction = sticker_under_mouse;
        }
        else {
          fInteraction = null;
        }
        repaint();
      }
    });
    addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent mouseEvent) {
        if (!isEnabled()) return;
        if (fSelection != null && fInteraction != null) {
          fInteraction.performInteraction(fSelection, fMinx);
        }
        performSelection(mouseEvent);
        fInteraction = null;
        repaint();
      }
    });
  }

  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    if (fHotSpots.isEmpty()) updateShapes();
    Graphics2D g = (Graphics2D) graphics;
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Iterator<LLMinxSticker> hot_spots = fHotSpots.iterator();
    while (hot_spots.hasNext()) {
      hot_spots.next().paint(g, false, fMinx);
    }
    if (fSelection != null) {
      fSelection.paint(g, true, fMinx);
      if (fInteraction != null) {
        fInteraction.paintInteraction(g, fSelection, fMinx);
      }
    }
  }

  private void updateShapes() {
    fHotSpots.clear();
    double half_width = getWidth() / 2;
    double half_height = getHeight() / 2;
    double outer_radius = Math.min(half_height, half_width) - 10;
    double middle_radius = 3 * outer_radius / 4;
    double inner_radius = outer_radius / 3;
    Point2D.Double center = new Point2D.Double(half_width, half_height);
    Point2D.Double corner = new Point2D.Double();
    Point2D.Double[] inner_corners = new Point2D.Double[5];
    Point2D.Double[] middle_corners = new Point2D.Double[5];
    Point2D.Double[] outer_corners = new Point2D.Double[5];
    LLMinxCenterSticker center_piece = new LLMinxCenterSticker(null, (byte) 0);
    for (int i = 0; i < 5; i++) {
      double angle = -Math.PI / 2 + (double) i / 5 * Math.PI * 2;
      pointAt(center, inner_radius, angle, corner);
      inner_corners[i] = new Point2D.Double(corner.getX(), corner.getY());
      center_piece.addPoint((int) Math.round(corner.getX()), (int) Math.round(corner.getY()));
      pointAt(center, middle_radius, angle, corner);
      middle_corners[i] = new Point2D.Double(corner.getX(), corner.getY());
      pointAt(center, outer_radius, angle, corner);
      outer_corners[i] = new Point2D.Double(corner.getX(), corner.getY());
    }
    int previous_corner, next_corner;
    Line2D.Double inner_line = new Line2D.Double();
    Line2D.Double outer_line = new Line2D.Double();
    Point2D.Double intersection = new Point2D.Double();
    Point2D.Double[] middle_edges_left = new Point2D.Double[5];
    Point2D.Double[] middle_edges_right = new Point2D.Double[5];
    for (int i = 0; i < 5; i++) {
      previous_corner = (i + 4) % 5;
      next_corner = (i + 1) % 5;
      inner_line.setLine(inner_corners[previous_corner], inner_corners[i]);
      outer_line.setLine(middle_corners[i], middle_corners[next_corner]);
      getLineLineIntersection(inner_line, outer_line, intersection);
      middle_edges_right[i] = new Point2D.Double(intersection.getX(), intersection.getY());
      inner_line.setLine(inner_corners[i], inner_corners[next_corner]);
      outer_line.setLine(middle_corners[previous_corner], middle_corners[i]);
      getLineLineIntersection(inner_line, outer_line, intersection);
      middle_edges_left[previous_corner] = new Point2D.Double(intersection.getX(), intersection.getY());
    }
    for (int i = 0; i < 5; i++) {
      previous_corner = (i + 4) % 5;
      next_corner = (i + 1) % 5;
      double fraction = middle_edges_left[previous_corner].distance(inner_corners[i]) / middle_edges_left[previous_corner].distance(middle_edges_right[next_corner]);
      Point2D.Double left_outer_corner = lerp(outer_corners[i], outer_corners[next_corner], fraction, new Point2D.Double());
      Point2D.Double right_outer_corner = lerp(outer_corners[i], outer_corners[previous_corner], fraction, new Point2D.Double());
      Point2D.Double left_outer_edge = lerp(outer_corners[next_corner], outer_corners[i], fraction, new Point2D.Double());

      // edge cubie.
      LLMinxCubie edge_cubie = new LLMinxCubie((byte) ((i + 3) % 5));
      edge_cubie.addPoint((int) Math.round(inner_corners[i].getX()), (int) Math.round(inner_corners[i].getY()));
      edge_cubie.addPoint((int) Math.round(inner_corners[next_corner].getX()), (int) Math.round(inner_corners[next_corner].getY()));
      edge_cubie.addPoint((int) Math.round(middle_edges_left[i].getX()), (int) Math.round(middle_edges_left[i].getY()));
      edge_cubie.addPoint((int) Math.round(left_outer_edge.getX()), (int) Math.round(left_outer_edge.getY()));
      edge_cubie.addPoint((int) Math.round(left_outer_corner.getX()), (int) Math.round(left_outer_corner.getY()));
      edge_cubie.addPoint((int) Math.round(middle_edges_right[i].getX()), (int) Math.round(middle_edges_right[i].getY()));

      // top edge sticker.
      LLMinxEdgeSticker edge_sticker = new LLMinxEdgeSticker(edge_cubie, (byte) 0);
      edge_sticker.addPoint((int) Math.round(inner_corners[i].getX()), (int) Math.round(inner_corners[i].getY()));
      edge_sticker.addPoint((int) Math.round(inner_corners[next_corner].getX()), (int) Math.round(inner_corners[next_corner].getY()));
      edge_sticker.addPoint((int) Math.round(middle_edges_left[i].getX()), (int) Math.round(middle_edges_left[i].getY()));
      edge_sticker.addPoint((int) Math.round(middle_edges_right[i].getX()), (int) Math.round(middle_edges_right[i].getY()));
      fHotSpots.add(edge_sticker);

      // bottom edge sticker.
      edge_sticker = new LLMinxEdgeSticker(edge_cubie, (byte) 1);
      edge_sticker.addPoint((int) Math.round(left_outer_corner.getX()), (int) Math.round(left_outer_corner.getY()));
      edge_sticker.addPoint((int) Math.round(middle_edges_right[i].getX()), (int) Math.round(middle_edges_right[i].getY()));
      edge_sticker.addPoint((int) Math.round(middle_edges_left[i].getX()), (int) Math.round(middle_edges_left[i].getY()));
      edge_sticker.addPoint((int) Math.round(left_outer_edge.getX()), (int) Math.round(left_outer_edge.getY()));
      fHotSpots.add(edge_sticker);

      // corner cubie.
      LLMinxCubie corner_cubie = new LLMinxCubie((byte) i);
      corner_cubie.addPoint((int) Math.round(inner_corners[i].getX()), (int) Math.round(inner_corners[i].getY()));
      corner_cubie.addPoint((int) Math.round(middle_edges_right[i].getX()), (int) Math.round(middle_edges_right[i].getY()));
      corner_cubie.addPoint((int) Math.round(left_outer_corner.getX()), (int) Math.round(left_outer_corner.getY()));
      corner_cubie.addPoint((int) Math.round(outer_corners[i].getX()), (int) Math.round(outer_corners[i].getY()));
      corner_cubie.addPoint((int) Math.round(right_outer_corner.getX()), (int) Math.round(right_outer_corner.getY()));
      corner_cubie.addPoint((int) Math.round(middle_edges_left[previous_corner].getX()), (int) Math.round(middle_edges_left[previous_corner].getY()));

      // top corner sticker.
      LLMinxCornerSticker corner_sticker = new LLMinxCornerSticker(corner_cubie, LLMinx.NEUTRAL_ORIENTATION);
      corner_sticker.addPoint((int) Math.round(inner_corners[i].getX()), (int) Math.round(inner_corners[i].getY()));
      corner_sticker.addPoint((int) Math.round(middle_edges_left[previous_corner].getX()), (int) Math.round(middle_edges_left[previous_corner].getY()));
      corner_sticker.addPoint((int) Math.round(middle_corners[i].getX()), (int) Math.round(middle_corners[i].getY()));
      corner_sticker.addPoint((int) Math.round(middle_edges_right[i].getX()), (int) Math.round(middle_edges_right[i].getY()));
      fHotSpots.add(corner_sticker);

      // left corner sticker.
      corner_sticker = new LLMinxCornerSticker(corner_cubie, LLMinx.NEGATIVE_ORIENTATION);
      corner_sticker.addPoint((int) Math.round(middle_corners[i].getX()), (int) Math.round(middle_corners[i].getY()));
      corner_sticker.addPoint((int) Math.round(middle_edges_right[i].getX()), (int) Math.round(middle_edges_right[i].getY()));
      corner_sticker.addPoint((int) Math.round(left_outer_corner.getX()), (int) Math.round(left_outer_corner.getY()));
      corner_sticker.addPoint((int) Math.round(outer_corners[i].getX()), (int) Math.round(outer_corners[i].getY()));
      fHotSpots.add(corner_sticker);

      // right corner sticker.
      corner_sticker = new LLMinxCornerSticker(corner_cubie, LLMinx.POSITIVE_ORIENTATION);
      corner_sticker.addPoint((int) Math.round(middle_corners[i].getX()), (int) Math.round(middle_corners[i].getY()));
      corner_sticker.addPoint((int) Math.round(middle_edges_left[previous_corner].getX()), (int) Math.round(middle_edges_left[previous_corner].getY()));
      corner_sticker.addPoint((int) Math.round(right_outer_corner.getX()), (int) Math.round(right_outer_corner.getY()));
      corner_sticker.addPoint((int) Math.round(outer_corners[i].getX()), (int) Math.round(outer_corners[i].getY()));
      fHotSpots.add(corner_sticker);
    }
    fHotSpots.add(center_piece);
  }

  private void invalidateShapes() {
    fHotSpots.clear();
  }

  private LLMinxSticker getStickerUnderMouse(MouseEvent mouseEvent) {
    Iterator<LLMinxSticker> hot_spots = fHotSpots.iterator();
    LLMinxSticker sticker_under_mouse = null;
    while (hot_spots.hasNext() && sticker_under_mouse == null) {
      LLMinxSticker hot_spot = hot_spots.next();
      if (hot_spot.contains(mouseEvent.getPoint())) {
        sticker_under_mouse = hot_spot;
      }
    }
    return sticker_under_mouse;
  }

  private void performSelection(MouseEvent mouseEvent) {
    fSelection = getStickerUnderMouse(mouseEvent);
    repaint();
  }

  private Point2D.Double pointAt(Point2D.Double aPoint, double aDistance, double aAngle, Point2D.Double aResult) {
    double x = aPoint.getX() + aDistance * Math.cos(aAngle);
    double y = aPoint.getY() + aDistance * Math.sin(aAngle);
    aResult.setLocation(x, y);
    return aResult;
  }

  private Point2D.Double lerp(Point2D.Double aPointOne, Point2D.Double aPointTwo, double aFraction, Point2D.Double aResult) {
    double x = aPointOne.getX() * (1 - aFraction) + aPointTwo.getX() * aFraction;
    double y = aPointOne.getY() * (1 - aFraction) + aPointTwo.getY() * aFraction;
    aResult.setLocation(x, y);
    return aResult;
  }

  static boolean getLineLineIntersection(Line2D.Double l1,
                                         Line2D.Double l2,
                                         Point2D.Double intersection) {
    double x1 = l1.getX1(), y1 = l1.getY1(),
      x2 = l1.getX2(), y2 = l1.getY2(),
      x3 = l2.getX1(), y3 = l2.getY1(),
      x4 = l2.getX2(), y4 = l2.getY2();

    intersection.x = det(det(x1, y1, x2, y2), x1 - x2,
      det(x3, y3, x4, y4), x3 - x4) /
      det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);
    intersection.y = det(det(x1, y1, x2, y2), y1 - y2,
      det(x3, y3, x4, y4), y3 - y4) /
      det(x1 - x2, y1 - y2, x3 - x4, y3 - y4);

    return true;
  }

  static double det(double a, double b, double c, double d) {
    return a * d - b * c;
  }

  public LLMinx getMinx() {
    return fMinx;
  }

  public void setMinx(LLMinx minx) {
    fMinx = minx;
    updateShapes();
    repaint();
  }

  public void componentResized(ComponentEvent componentEvent) {
    invalidateShapes();
  }

  public void componentMoved(ComponentEvent componentEvent) {
  }

  public void componentShown(ComponentEvent componentEvent) {
  }

  public void componentHidden(ComponentEvent componentEvent) {
  }
}
