package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Methods;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;

import static org.injustice.rawchicken.util.Variables.BANK_TILE;
import static org.injustice.rawchicken.util.Variables.status;

public class WalkBox extends Node {

	@Override
	public boolean activate() {
        status = "4";
        return Inventory.isFull() && !BANK_TILE.isOnScreen();
	}

	@Override
	public void execute() {
        Methods.checkGate();
        status = Methods.walkPath(BANK_TILE, "bank");
    }
}
