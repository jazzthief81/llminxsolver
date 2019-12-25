package llminx.gui;

import llminx.LLMinx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.awt.geom.AffineTransform;

/**
 *
 */
public abstract class LLMinxSticker extends Polygon {

  private LLMinxCubie fCubie;
  private byte fOrientation;
  private final static Color[] COLORS = new Color[]{
    new Color(225, 225, 0),
    new Color(200, 0, 0),
    new Color(225, 100, 0),
    new Color(0, 200, 0),
    new Color(255, 150, 150),
    new Color(0, 0, 150),
  };
  protected final static Color IGNORE_COLOR = new Color(128, 128, 128);
  protected final static Color fColor = Color.BLACK;
  protected final static Color fSelectionColor = new Color(128, 0, 0);
  protected final static Color fInteractionColor = new Color(128, 0, 0);
  protected final static Color fHighlightColor = new Color(255, 0, 0, 32);
  protected final static BasicStroke fStroke = new BasicStroke(1);
  protected final static BasicStroke fSelectionStroke = new BasicStroke(3);
  protected final static BasicStroke fInteractionStroke = new BasicStroke(3,
    BasicStroke.CAP_SQUARE,
    BasicStroke.JOIN_MITER,
    10,
    new float[]{6, 8},
    0
  );
  protected static final WritableRaster TWO_COLORS;
  protected static final WritableRaster THREE_COLORS;

  static {
    int width = 16;
    BufferedImage image = new BufferedImage(width*2, width*2, BufferedImage.TYPE_BYTE_INDEXED);
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int s = (i / width) % 2;
        s += (j / width) % 2;
        image.getRaster().setSample(j, i, 0, s % 2);
      }
    }
    TWO_COLORS = image.getRaster();
    image = new BufferedImage(width*3, width*3, BufferedImage.TYPE_BYTE_INDEXED);
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        int s = (i / width) % 3;
        s += (j / width) % 3;
        image.getRaster().setSample(j, i, 0, s % 3);
      }
    }
    THREE_COLORS = image.getRaster();
  }

  public LLMinxSticker(LLMinxCubie cubie, byte orientation) {
    fCubie = cubie;
    fOrientation = orientation;
  }


  public LLMinxCubie getCubie() {
    return fCubie;
  }

  public byte getOrientation() {
    return fOrientation;
  }

  public int getCenterX() {
    int sum = 0;
    for (int i = 0; i < xpoints.length; i++) {
      sum += xpoints[i];
    }
    return sum /= xpoints.length;
  }

  public int getCenterY() {
    int sum = 0;
    for (int i = 0; i < ypoints.length; i++) {
      sum += ypoints[i];
    }
    return sum /= ypoints.length;
  }

  public abstract void paint(Graphics aGraphics, boolean aSelected, LLMinx aMinx);

  public abstract boolean interactsWith(LLMinxSticker aCubie, LLMinx aMinx);

  public abstract void paintInteraction(Graphics aGraphics, LLMinxSticker aCubie, LLMinx aMinx);

  public abstract void performInteraction(LLMinxSticker aCubie, LLMinx aMinx);

  protected void applyLineStyle(Graphics aGraphics, boolean aSelected) {
    Graphics2D graphics = (Graphics2D) aGraphics;
    if (aSelected) {
      graphics.setColor(fSelectionColor);
      graphics.setStroke(fSelectionStroke);
    }
    else {
      graphics.setColor(fColor);
      graphics.setStroke(fStroke);
    }
  }

  public void paintArrow(Graphics aGraphics, Point aStart, Point aEnd, int aSize) {
    Graphics2D g = (Graphics2D) aGraphics;
    AffineTransform transform = g.getTransform();
    double rico = Math.atan2(aEnd.y - aStart.y, aEnd.x - aStart.x);
    g.rotate(rico, aEnd.getX(), aEnd.getY());
    g.drawLine(aEnd.x, aEnd.y, aEnd.x - aSize, aEnd.y - aSize / 2);
    g.drawLine(aEnd.x, aEnd.y, aEnd.x - aSize, aEnd.y + aSize / 2);
    g.setTransform(transform);
  }

  protected void applyLineStyleIneraction(Graphics aGraphics) {
    Graphics2D graphics = (Graphics2D) aGraphics;
    graphics.setColor(fInteractionColor);
    graphics.setStroke(fInteractionStroke);
  }

  protected void applyFillStyle(Graphics aGraphics, int aColor) {
    if (aColor == -1) {
      aGraphics.setColor(IGNORE_COLOR);
    }
    else {
      aGraphics.setColor(COLORS[aColor]);
    }
  }

  protected void applyFillStyle(Graphics aGraphics, int aColorA, int aColorB) {
    byte[] reds = new byte[]{(byte) COLORS[aColorA].getRed(), (byte) COLORS[aColorB].getRed()};
    byte[] greens = new byte[]{(byte) COLORS[aColorA].getGreen(), (byte) COLORS[aColorB].getGreen()};
    byte[] blues = new byte[]{(byte) COLORS[aColorA].getBlue(), (byte) COLORS[aColorB].getBlue()};
    IndexColorModel colorModel = new IndexColorModel(1, 2, reds, greens, blues);
    ((Graphics2D) aGraphics).setPaint(new TexturePaint(new BufferedImage(colorModel, TWO_COLORS, false, null), TWO_COLORS.getBounds()));
  }

  protected void applyFillStyle(Graphics aGraphics, int aColorA, int aColorB, int aColorC) {
    byte[] reds = new byte[]{(byte) COLORS[aColorA].getRed(), (byte) COLORS[aColorB].getRed(), (byte) COLORS[aColorC].getRed(), 0};
    byte[] greens = new byte[]{(byte) COLORS[aColorA].getGreen(), (byte) COLORS[aColorB].getGreen(), (byte) COLORS[aColorC].getGreen(), 0};
    byte[] blues = new byte[]{(byte) COLORS[aColorA].getBlue(), (byte) COLORS[aColorB].getBlue(), (byte) COLORS[aColorC].getBlue(), 0};
    IndexColorModel colorModel = new IndexColorModel(2, 4, reds, greens, blues);
    ((Graphics2D) aGraphics).setPaint(new TexturePaint(new BufferedImage(colorModel, THREE_COLORS, false, null), THREE_COLORS.getBounds()));
  }

  protected void applyHighlightStyle(Graphics aGraphics) {
    aGraphics.setColor(fHighlightColor);
  }

}
