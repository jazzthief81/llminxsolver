package llminx.gui;

import llminx.LLMinx;

import java.awt.*;

/**
 *
 */
public class LLMinxCenterSticker extends LLMinxSticker {

  public LLMinxCenterSticker(LLMinxCubie cubie, byte orientation) {
    super(cubie, orientation);
  }

  public void paint(Graphics aGraphics, boolean aSelected, LLMinx aMinx) {
    applyFillStyle(aGraphics,0);
    aGraphics.fillPolygon(this);
    applyLineStyle(aGraphics, false);
    aGraphics.drawPolygon(this);
  }

  public boolean interactsWith(LLMinxSticker aCubie, LLMinx aMinx) {
    return false;
  }

  public void paintInteraction(Graphics aGraphics, LLMinxSticker aCubie, LLMinx aMinx) {
  }

  public void performInteraction(LLMinxSticker aCubie, LLMinx aMinx) {    
  }
}
