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
        SceneObject closedGate = SceneEntities.getNearest(Var.CLOSED_GATE_ID);
        if (!Walking.isRunEnabled() && Walking.getEnergy() > 20) {
            Var.status = "Setting run";
            Walking.setRun(true);
        }
        Var.status = "Walking to pen";
        Walking.findPath(Var.CHICKEN_TILE).traverse();
        out:
        if (closedGate != null) {
            if (Calculations.distanceTo(closedGate.getLocation()) <= 5 ) {
                if (closedGate.isOnScreen()) {
                    if (Methods.openGate()) {
                        do sleep(500, 1000); while (Players.getLocal().isMoving());
                        break out;
                    }
                } else Camera.turnTo(closedGate);
            } else {
                Var.status = "Walking to gate";
                Walking.findPath(Var.GATE_TILE).traverse();
                do sleep(500, 750); while (Players.getLocal().isMoving());
            }
        } else {
            Var.status = "Walking into pen";
            Walking.findPath(Var.CHICKEN_TILE).traverse();
            do sleep(500, 750); while (Players.getLocal().isMoving());
        }
    }
}
