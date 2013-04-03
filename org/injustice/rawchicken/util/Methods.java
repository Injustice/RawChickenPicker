package org.injustice.rawchicken.util;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Game;
import org.powerbot.core.script.methods.Players;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.LocalPath;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */


public class Methods {
    /**
     * @return inventory is empty
     */
    public static boolean inventoryEmpty() {
        return Inventory.getCount() == 0;
    }

    /**
     * @param number    number we want to format
     * @param precision precision in dp
     * @return formatted number
     */
    public static String format(long number, int precision) {
        String sign = number < 0 ? "-" : "";
        number = Math.abs(number);
        if (number < 1000) {
            return sign + number;
        }
        int exponent = (int) (Math.log(number) / Math.log(1000));
        return String.format("%s%." + precision + "f%s", sign,
                number / Math.pow(1000, exponent),
                "kmbtpe".charAt(exponent - 1));
    }

    /**
     * @return false if the gate is open
     * @description checks if the gate is open
     */
    public static boolean gateOpen() {
        SceneObject closedGate = SceneEntities.getNearest(Var.CLOSED_GATE_ID);
        return closedGate == null;
    }

    public static void checkGate() {
        SceneObject closedGate = SceneEntities.getNearest(Var.CLOSED_GATE_ID);
        if (closedGate != null) {
            if (closedGate.getLocation().isOnMap()) {
                if (closedGate.isOnScreen()) {
                    if (closedGate.interact("Open")) {
                        Var.status = "Opening gate";
                        do Task.sleep(750, 1000); while (Players.getLocal().isMoving());
                    }
                } else {
                    Camera.turnTo(closedGate);
                    Camera.setPitch(Random.nextInt(0, 15));
                }
            }
        }
    }

    public static boolean openGate() {
        SceneObject closedDoor = SceneEntities.getNearest(Var.CLOSED_GATE_ID);
        if (closedDoor != null) {
            if (closedDoor.getLocation().isOnMap()) {
                if (closedDoor.isOnScreen()) {
                    if (closedDoor.interact("Open")) {
                        Var.status = "Opening gate";
                        do Task.sleep(750, 1000); while (Players.getLocal().isMoving());
                    }
                } else Camera.turnTo(closedDoor);
            }
        }
        return closedDoor == null;
    }


    /**
     * @param x x location
     * @param y y location
     * @param w width
     * @param h height
     * @author Coma
     * @description takes a screenshot, saves in C:/Users/USER/Appdata/Local/Temp/Raw Chicken Picker
     */
    public static void savePaint(final int x, final int y, final int w, final int h) {

        final File path = new File(Environment.getStorageDirectory().getPath(), FileName.getName() + ".png");
        final BufferedImage img = Environment.captureScreen().getSubimage(x, y, w, h);
        try {
            ImageIO.write(img, "png", path);
            Var.status = "Saving screeny";
        } catch (Exception e) {
            e.printStackTrace();
            Var.status = "Failed screeny";
        }
    }


    /**
     * @param g1        Graphics
     * @param itemArea  area of the item
     * @param itemId    id of item (array)
     * @param itemColor color of all items
     * @param nextColor color of next item to pick
     * @param locColor  color of player location
     * @author Injustice
     */
    public static void drawTiles(Graphics g1, Area itemArea, int[] itemId,
                                 Color itemColor, Color nextColor, Color locColor) {
        Graphics2D g = (Graphics2D) g1;
        GroundItem[] items = GroundItems.getLoaded(itemId[0]);
        GroundItem nextItem = GroundItems.getNearest(itemId);
        SceneObject depositbox = SceneEntities.getNearest(Var.DEPOSIT_BOX_ID);
        for (Tile t : itemArea.getTileArray()) {
            if (itemArea.contains(t) && t.isOnMap()) {
                for (GroundItem gi : items) {
                    if (itemArea.contains(gi)) {
                        Tile tile = gi.getLocation();
                        if (tile != nextItem.getLocation()) {
                            g.setColor(itemColor);
                            tile.draw(g);
                        }
                    } else {
                        Tile tile = gi.getLocation();
                        g.setColor(Color.RED);
                        tile.draw(g);
                    }
                }
                g.setColor(nextColor);
                nextItem.getLocation().draw(g);
            }
        }
        if (depositbox.getLocation().isOnMap()) {
            Tile tile = depositbox.getLocation();
            g.setColor(Color.RED);
            tile.draw(g);
        }
        Tile t = new Tile(Players.getLocal().getLocation().getX(),
                Players.getLocal().getLocation().getY(),
                Players.getLocal().getLocation().getPlane());
        g.setColor(locColor);
        t.draw(g);

    }

    private static int state() {
        return Game.getClientState();
    }

    private static boolean getClientState() {
        return state() != 7 /*lobby*/ && state() != 9 /*logging in*/
                && state() != 12 /*logging in*/;
    }

    public static boolean isReady() {
        return Game.isLoggedIn() && getClientState();
    }

    /**
     * Adds an offset to parameter tile
     * @param t destination tile
     * @param r randomisation/offset
     * @return  tile with a random offset between -r and +r
     */
    private static Tile randomise(Tile t, int r) {
        int x = t.getX();
        int y = t.getY();
        int z = t.getPlane();
        return new Tile(x + Random.nextInt(-r, r), y + Random.nextInt(-r, r), z);
    }

    public static void sleepMoving(boolean doWhile, int lengthOne, int lengthTwo) {
        if (doWhile) {
            do Task.sleep(Random.nextInt(lengthOne, lengthTwo));
            while (Players.getLocal().isMoving());
        } else while (Players.getLocal().isMoving()) Task.sleep(Random.nextInt(lengthOne, lengthTwo));
    }
}

