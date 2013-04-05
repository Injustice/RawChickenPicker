package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class Pick extends Node {

    @Override
    public boolean activate() {
        return !Inventory.isFull() && Var.CHICKEN_AREA.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
        Var.status = "Pickin' chicken";                                    // change status
        GroundItem chicken = GroundItems.getNearest(Var.RAW_CHICKEN_ID);   // initialise grounditem chicken
        if (chicken != null) {                                             // if chicken exists
            if (Var.CHICKEN_AREA.contains(chicken.getLocation())) {            // if chicken is in CHICKEN_AREA
                if (chicken.isOnScreen()) {                                        // if chicken is on screen
                    chicken.interact("Take", "chicken");                               // interact with chicken
                    Var.chickensPicked++;                                              // postincrement chickensPicker
                    do sleep(750, 900); while (Players.getLocal().isMoving());         // sleep for a random period between 750 and 1000 whilst player is moving
                } else Camera.turnTo(chicken);                                     // else turn to chicken
            } else if (!Var.CHICKEN_AREA.contains(chicken.getLocation()) &&
                    Calculations.distanceTo(Var.CHICKEN_TILE) > 5) {                                                           // else
                Walking.walk(Var.CHICKEN_TILE.randomize(1, 1));                   // walk to centre of pen
                Var.status = "Walking to middle";                                 // change status
                do sleep(500, 750); while (Players.getLocal().isMoving());
            }                                                                  // end if
        } else {                                                            // else
            chicken = GroundItems.getNearest(Var.RAW_CHICKEN_ID);               // reinitialise grounditem chicken (there may be a newer one)
            outer:                                                              // initialise break label outer
            while (chicken == null) {
                Var.status = "No chicken";
                sleep(500);
                if (GroundItems.getNearest(Var.RAW_CHICKEN_ID) != null) {
                    break outer;
                }
            }
        }
    }
}
