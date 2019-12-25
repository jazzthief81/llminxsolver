package llminx.gui;

import llminx.LLMinx;

import java.awt.*;

/**
 *
 */
public class LLMinxCornerSticker extends LLMinxSticker {

  private static final int[][] COLORS = new int[][]{
    {0, 3, 4},
    {0, 4, 5},
    {0, 5, 1},
    {0, 1, 2},
    {0, 2, 3},
  };

  public LLMinxCornerSticker(LLMinxCubie cubie, byte orientation) {
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
      byte corner_cubie = getCubie().getCubie();
      int orientation = (getOrientation() - aMinx.getCornerOrientation(corner_cubie) + 3) % 3;
      if (((orientation != 0 || aMinx.getIgnoreCornerOrientations()[corner_cubie]) && aMinx.getIgnoreCornerPositions()[corner_cubie])) {
        applyFillStyle(aGraphics, -1);
      }
      else {
        if (aMinx.getIgnoreCornerOrientations()[corner_cubie]) {
          applyFillStyle(aGraphics,
            COLORS[aMinx.getCornerPositions()[corner_cubie]][0],
            COLORS[aMinx.getCornerPositions()[corner_cubie]][1],
            COLORS[aMinx.getCornerPositions()[corner_cubie]][2]);
        }
        else {
          applyFillStyle(aGraphics, COLORS[aMinx.getCornerPositions()[corner_cubie]][orientation]);
        }
      }
      aGraphics.fillPolygon(this);
      applyLineStyle(aGraphics, aSelected);
      aGraphics.drawPolygon(this);
    }
  }

  public boolean interactsWith(LLMinxSticker sticker, LLMinx aMinx) {
    return sticker instanceof LLMinxCornerSticker && sticker != this;
  }

  public void paintInteraction(Graphics aGraphics, LLMinxSticker aSticker, LLMinx aMinx) {
    if (aSticker.getCubie() == getCubie()) {
      Point start = new Point(aSticker.getCenterX(), aSticker.getCenterY());
      Point end = new Point(getCenterX(), getCenterY());
      applyLineStyleIneraction(aGraphics);
      aGraphics.drawLine(start.x, start.y, end.x, end.y);
      applyLineStyle(aGraphics, true);
      paintArrow(aGraphics, start, end, 10);
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
    byte target_corner_cubie = getCubie().getCubie();
    if (aSticker.getCubie() == getCubie()) {
      int orientation = (getOrientation() - aSticker.getOrientation() + aMinx.getCornerOrientation(target_corner_cubie) + 3) % 3;
      aMinx.setCornerOrientation(target_corner_cubie, orientation);
    }
    else {
      byte source_corner_cubie = aSticker.getCubie().getCubie();
      byte[] corners = aMinx.getCornerPositions();
      byte corner = corners[source_corner_cubie];
      corners[source_corner_cubie] = corners[target_corner_cubie];
      corners[target_corner_cubie] = corner;
      int orientation = aMinx.getCornerOrientation(target_corner_cubie);
      aMinx.setCornerOrientation(target_corner_cubie, aMinx.getCornerOrientation(source_corner_cubie));
      aMinx.setCornerOrientation(source_corner_cubie, orientation);
    }
  }
}
