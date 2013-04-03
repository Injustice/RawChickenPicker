package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Methods;
import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class WalkPen extends Node {

    @Override
    public boolean activate() {
        return Methods.inventoryEmpty() && !Var.CHICKEN_AREA.contains(Players.getLocal().getLocation());
    }

    @Override
    public void execute() {
        SceneObject closedGate = SceneEntities.getNearest(Var.CLOSED_GATE_ID);    // initialise sceneobject closedGate
        if (!Walking.isRunEnabled() && Walking.getEnergy() > 20) {                // if run is off and energy is more than 20
            Var.status = "Setting run";                                               // change status
            Walking.setRun(true);                                                     // turn run on
        }                                                                         // end if
        if (!Var.CHICKEN_TILE.isOnScreen()) {                                     // if CHICKEN_TILE is not on screen
            Walking.findPath(Var.GATE_TILE).traverse();                               // walk to GATE_TILE
            Var.status = "Walking to pen";                                            // change status
        }                                                                         // end if
        out:                                                                      // initialise break label out
        if (closedGate.validate() &&                                              // if gate is closed and
                Calculations.distanceTo(closedGate.getLocation()) <= 5 ) {                // distance to closed gate is smaller or equal to 5
            if (closedGate.getLocation().isOnMap()) {                                 // if gate location is on map
                if (closedGate.isOnScreen()) {                                            // if gate is on screen
                    if (Methods.openGate()) {                                                 // if gate is open (else open gate, the method does this)
                        do sleep(500, 1000); while (Players.getLocal().isMoving());               // sleep for a random period between 500 and 1000 whilst the player is moving
                        break out;                                                                // break to the label out
                    }                                                                         // end if
                } else Camera.turnTo(closedGate);                                         // else turn to gate
            } else {                                                                  // else
                Var.status = "Walking into pen";                                          // change status
                Walking.walk(Var.CHICKEN_TILE);                                           // walk to chicken tile
                Methods.sleepMoving(true, 1000, 1250);                                    // sleep for a random period between 1000 and 1250 then whilst moving
            }                                                                         // end if
        }                                                                         // end if
    }
}


