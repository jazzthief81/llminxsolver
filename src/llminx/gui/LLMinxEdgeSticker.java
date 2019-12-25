package llminx.gui;

import llminx.LLMinx;

import java.awt.*;

/**
 *
 */
public class LLMinxEdgeSticker extends LLMinxSticker {

  private static final int[][] COLORS = new int[][]{
    {0, 1},
    {0, 2},
    {0, 3},
    {0, 4},
    {0, 5},
  };

  public LLMinxEdgeSticker(LLMinxCubie cubie, byte orientation) {
    super(cubie, orientation);
  }

  public void paint(Graphics aGraphics, boolean aSelected, LLMinx aMinx) {
    if (aSelected) {
      applyHighlightStyle(aGraphics);
      aGraphics.fillPolygon(getCubie());
      applyLineStyle(aGraphics, aSelected);
      aGraphics.drawPolygon(getCubie());
    }
    else {
      byte edge_cubie = getCubie().getCubie();
      int orientation = Math.abs(getOrientation() - aMinx.getEdgeOrientation(edge_cubie));
      if (((orientation != 0 || aMinx.getIgnoreEdgeOrientations()[edge_cubie]) && aMinx.getIgnoreEdgePositions()[edge_cubie])) {
        applyFillStyle(aGraphics, -1);
      }
      else {
        if (aMinx.getIgnoreEdgeOrientations()[edge_cubie]) {
          applyFillStyle(aGraphics, COLORS[aMinx.getEdgePositions()[edge_cubie]][0],COLORS[aMinx.getEdgePositions()[edge_cubie]][1]);
        }
        else {
          applyFillStyle(aGraphics, COLORS[aMinx.getEdgePositions()[edge_cubie]][orientation]);
        }
      }
      aGraphics.fillPolygon(this);
      applyLineStyle(aGraphics, aSelected);
      aGraphics.drawPolygon(this);
    }
  }

  public boolean interactsWith(LLMinxSticker aSticker, LLMinx aMinx) {
    return aSticker instanceof LLMinxEdgeSticker && aSticker != this;
  }

  public void paintInteraction(Graphics aGraphics, LLMinxSticker aSticker, LLMinx aMinx) {
    if (aSticker.getCubie() == getCubie()) {
      Point start = new Point(aSticker.getCenterX(), aSticker.getCenterY());
      Point end = new Point(getCenterX(), getCenterY());
      applyLineStyleIneraction(aGraphics);
      aGraphics.drawLine(start.x, start.y, end.x, end.y);
      applyLineStyle(aGraphics, true);
      paintArrow(aGraphics, start, end, 10);
      paintArrow(aGraphics, end, start, 10);
    }
    else {
      applyHighlightStyle(aGraphics);
      aGraphics.fillPolygon(getCubie());
      applyLineStyleIneraction(aGraphics);
      aGraphics.drawPolygon(getCubie());
      Point start = new Point((int) aSticker.getCubie().getBounds().getCenterX(),
        (int) aSticker.getCubie().getBounds().getCenterY());
      Point end = new Point((int) getCubie().getBounds().getCenterX(),
        (int) getCubie().getBounds().getCenterY());
      aGraphics.drawLine(start.x, start.y, end.x, end.y);
      applyLineStyle(aGraphics, true);
      paintArrow(aGraphics, start, end, 10);
      paintArrow(aGraphics, end, start, 10);
    }
  }

  public void performInteraction(LLMinxSticker aSticker, LLMinx aMinx) {
    byte target_edge_cubie = getCubie().getCubie();
    if (aSticker.getCubie() == getCubie()) {
      int orientation = (getOrientation() - aSticker.getOrientation() + aMinx.getEdgeOrientation(target_edge_cubie) + 2) % 2;
      aMinx.setEdgeOrientation(target_edge_cubie, orientation);
    }
    else {
      byte source_edge_cubie = aSticker.getCubie().getCubie();
      byte[] edges = aMinx.getEdgePositions();
      byte edge = edges[target_edge_cubie];
      edges[target_edge_cubie] = edges[source_edge_cubie];
      edges[source_edge_cubie] = edge;
      int orientation = aMinx.getEdgeOrientation(target_edge_cubie);
      aMinx.setEdgeOrientation(target_edge_cubie, aMinx.getEdgeOrientation(source_edge_cubie));
      aMinx.setEdgeOrientation(source_edge_cubie, orientation);
    }
  }
}
