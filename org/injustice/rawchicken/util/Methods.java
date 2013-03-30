package org.injustice.rawchicken.util;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import static org.injustice.rawchicken.util.Variables.CLOSED_GATE_ID;
import static org.injustice.rawchicken.util.Variables.status;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class Methods {
    public static String walkPath(Tile tile, String place) {
        walkPath(tile);
        return "Walking to " + place;
    }

    public static void walkPath(Tile tile) {
        Walking.findPath(tile).traverse();
    }

    public static boolean inventoryEmpty() {
        return Inventory.getCount() == 0;
    }

    public static void checkGate() {
        SceneObject closedDoor = SceneEntities.getNearest(CLOSED_GATE_ID);
        if (closedDoor.validate()) {
            status = "Opening gate";
            closedDoor.interact("Open");
            do Task.sleep(1000);
            while (Players.getLocal().isMoving());
        }
        Task.sleep(1000);
    }
}
