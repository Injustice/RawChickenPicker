package org.injustice.rawchicken.util;

import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.net.GeItem;

import java.awt.*;

import static org.injustice.rawchicken.util.Variables.*;

/**
 * Created with IntelliJ IDEA.
 * User: Azmat
 * Date: 19/03/13
 * Time: 20:35
 * To change this template use File | Settings | File Templates.
 */
public class Paint {
    public static void onRepaint(Graphics g1) {
        int chickensHour = (int) (chickensPicked * 3600000d / runTime.getElapsed());
        int chickenPrice = GeItem.lookup(RAW_CHICKEN_ID).getPrice();   // raw chicken
        int cashMade = chickenPrice * chickensPicked;
        int cashHour = (int) (cashMade * 3600000d / runTime.getElapsed());

        Graphics2D g = (Graphics2D) g1;
        BasicStroke stroke1 = new BasicStroke(1);
        g.setStroke(stroke1);
        Font font1 = new Font("Tahoma", 0, 15);
        Font font2 = new Font("Tahoma", 0, 9);
        Font font3 = new Font("Tahoma", 0, 11);
        Point loc = new Point(10, 220);
        Color grey = new Color(0, 0, 0, 100);
        Color blue = new Color(10, 50, 255, 100);
        Color green = new Color(10, 255, 50, 100);

        g.setColor(grey);
        g.fillRoundRect(5, 178, 190, 160, 7, 7);
        g.setColor(Color.GREEN);
        g.setStroke(stroke1);
        g.drawRoundRect(5, 178, 190, 160, 7, 7);
        g.drawRect(5, 178, 190, 21);
        g.setFont(font1);

        g.drawString("Injustice's Chicken Picker", 8, 196);
        g.setFont(font2);
        g.drawString("v0.2", 173, 196);
        g.setFont(font3);
        g.drawString("Chicken Picked: " + chickensPicked, loc.x, loc.y);     // loc.x, loc.y
        g.drawString("Chicken PH: " + chickensHour, loc.x + 90, loc.y);          // +90, loc.y
        g.drawString("Cash Made: " + cashMade, loc.x, loc.y + 15);       // loc.x, +15
        g.drawString("Cash PH: " + cashHour, loc.x + 89, loc.y + 15);         // +89, +15
 //       g.drawString("Levels: " , loc.x + 90, loc.y + 30);     // + 90, +30
 //       g.drawString("Start level: " , loc.x, loc.y + 30);   // loc.x, +30
 //       g.drawString("Logs cut: ", loc.x, loc.y + 45);         // loc.x, +45
 //       g.drawString("Logs PH: " , loc.x + 90, loc.y + 45);        // +90, +45
        g.drawString("Runtime: " + runTime.toElapsedString(), loc.x, loc.y + 70); // loc.x, +70
        g.drawString("Status: " + status, loc.x, loc.y + 85);            // loc.x, + 85

        drawMouse(g);
    }

    public static void drawMouse(Graphics g1) {
        ((Graphics2D) g1).setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        Point p = Mouse.getLocation();
        Color[] gradient = new Color[]{new Color(255, 0, 0),
                new Color(255, 0, 255), new Color(0, 0, 255),
                new Color(0, 255, 255), new Color(0, 255, 0),
                new Color(255, 255, 0), new Color(255, 0, 0)};
        Color outerCircle = gradient[0];
        Color innerCircle = Color.white;
        g1.setColor(gradient[0]);
        int circleRadius = 7;
        int circleDiameter = circleRadius * 2;
        g1.drawLine(p.x + circleRadius, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x - 2000, p.y, p.x - circleRadius, p.y);
// Vertical
        g1.drawLine(p.x, p.y + circleRadius, p.x, p.y + 2000);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y - circleRadius);
        for (int r = gradient.length - 1; r > 0; r--) {
            int steps = 200 / ((gradient.length - 1) * 2);
            for (int i = steps; i > 0; i--) {
                float ratio = (float) i / (float) steps;
                int red = (int) (gradient[r].getRed() * ratio + gradient[r - 1]
                        .getRed() * (1 - ratio));
                int green = (int) (gradient[r].getGreen() * ratio + gradient[r - 1]
                        .getGreen() * (1 - ratio));
                int blue = (int) (gradient[r].getBlue() * ratio + gradient[r - 1]
                        .getBlue() * (1 - ratio));
                Color stepColor = new Color(red, green, blue);
                g1.setColor(stepColor);
// Horizontal
                g1.drawLine(p.x + circleRadius, p.y, p.x
                        + ((i * 5) + (100 * r)), p.y);
                g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
                        - circleRadius, p.y);
// Vertical
                g1.drawLine(p.x, p.y + circleRadius, p.x, p.y
                        + ((i * 5) + (100 * r)));
                g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
                        - circleRadius);
            }
        }
        g1.setColor(outerCircle);
        final long mpt = System.currentTimeMillis() - Mouse.getPressTime();
        if (Mouse.getPressTime() == -1 || mpt >= 200) {
            g1.drawOval(p.x - circleRadius / 3, p.y - circleRadius / 3,
                    circleDiameter / 3, circleDiameter / 3);
        }
        if (mpt < 200) {
            g1.drawLine(p.x - circleRadius, p.y + circleRadius, p.x
                    + circleRadius, p.y - circleRadius);
            g1.drawLine(p.x - circleRadius, p.y - circleRadius, p.x
                    + circleRadius, p.y + circleRadius);
        }
        g1.setColor(outerCircle);
        g1.drawOval(p.x - circleRadius, p.y - circleRadius, circleDiameter,
                circleDiameter);
    }
}
