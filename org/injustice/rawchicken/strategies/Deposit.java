package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Var;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;
import java.awt.Point;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */

public class Deposit extends Node {

    @Override
    public boolean activate() {
        return Inventory.isFull() && Var.BANK_TILE.isOnScreen();
    }

    @Override
    public void execute() {
        final SceneObject DEPOSIT_BOX = SceneEntities.getNearest(Var.DEPOSIT_BOX_ID);  // initialises the deposit box
        Var.status = "Depositing";                                                     // changes the status
        if (DEPOSIT_BOX.isOnScreen()) {                                                // if the deposit box is on screen
            if (DEPOSIT_BOX.interact("Deposit")) {                                         // if interact "Deposit"
                do sleep(500, 750); while (Players.getLocal().isMoving());                     // sleep for a random period between 500 and 750 whilst player is moving
                if (Var.DEPOSIT_BOX_MAIN.isOnScreen()) {                                       // if main screen widget is on screen
                    if (Var.DEPOSIT_BOX_DEPOSIT_INVENTORY.isOnScreen()) {                          // if the deposit inventory widget is on screen
                        if (Var.DEPOSIT_BOX_DEPOSIT_INVENTORY.interact("Deposit")) {                   // if interact "Deposit"
                            sleep(500, 750);                                                               // sleep for a random period between 500 and 750
                            if (Var.DEPOSIT_BOX_CLOSE.isOnScreen()) {                                          // if close widget is on screen
                                Point p = Var.DEPOSIT_BOX_CLOSE.getCentralPoint();                                 // initialise point p to the close widget's central point
                                Mouse.click(p.x + Random.nextInt(-5, 5), p.y +                                     // click on point p +-5 x, +-5 y,
                                        Random.nextInt(-5, 5), true);                                                       // left click
                            }                                                                                  // end if
                        }                                                                                  // end if
                    }                                                                                  // end if
                }                                                                                  // end if
            }                                                                                  // end if
        }                                                                                 // end if
    }
}
