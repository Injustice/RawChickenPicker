package org.injustice.rawchicken.strategies;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;

import static org.injustice.rawchicken.util.Variables.*;

public class Deposit extends Node {

    @Override
    public boolean activate() {
        status = "3";
        return Inventory.isFull() && BANK_TILE.isOnScreen();
    }

    @Override
    public void execute() {
        final SceneObject DEPOSIT_BOX = SceneEntities.getNearest(DEPOSIT_BOX_ID);
        status = "Depositing";
        if (DEPOSIT_BOX.isOnScreen()) {
            if (DEPOSIT_BOX.interact("Deposit")) {
                sleep(1000, 1500);
                if (DEPOSIT_BOX_MAIN.isOnScreen()) {            // main screen
                    sleep(1000, 1500);
                    if (DEPOSIT_BOX_DEPOSIT_INVENTORY.isOnScreen()) {       // deposit inventory
                        if (DEPOSIT_BOX_DEPOSIT_INVENTORY.interact("Deposit")) {
                            sleep(1500);
                            if (DEPOSIT_BOX_CLOSE.isOnScreen()) {// close
                                Point p = DEPOSIT_BOX_CLOSE.getCentralPoint();
                                Mouse.click(p.x + Random.nextInt(-5, 5), p.y +
                                        Random.nextInt(-5, 5), true);
                            }
                        }
                    }
                }
            }
        }
    }

}
