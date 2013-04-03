package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.tab.Inventory;
/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class WalkBox extends Node {

    @Override
    public boolean activate() {
        return Inventory.isFull() && !Var.BANK_TILE.isOnScreen();
    }

    @Override
    public void execute() {
        if (!Walking.isRunEnabled() && Walking.getEnergy() > 20) {   // if run isn't enabled and energy is more than 20
            Var.status = "Setting run";                                  // change status
            Walking.setRun(true);                                        // set run to on
        }                                                            // end if
        if (Var.closedGate != null &&                                // if gate is closed and
                Players.getLocal().getLocation().getX() <=                   // player's x location
                        Var.closedGate.getLocation().getX()) {                       // is less than or equal to gate's x locatino
            if (Var.closedGate.isOnScreen()) {                           // if closed gate is on screen
                Var.status = "Opening gate";                                 // change status
                Var.closedGate.interact("Open");                             // interact with closed gate
            } else Walking.walk(Var.closedGate.getLocation());           // else walk to closed gate
        } else {                                                     // else
            Var.status = "Walking to bank";                              // change status
            Walking.findPath(Var.BANK_TILE).traverse();                  // find a path to bank tile and walk it
        }                                                            // end if
    }
}

/* -- Thanks to GeemanKan for helping me with some of this -- */

