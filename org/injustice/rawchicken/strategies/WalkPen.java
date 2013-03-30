package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Methods;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.wrappers.Tile;

import static org.injustice.rawchicken.util.Variables.*;


public class WalkPen extends Node {

	@Override
	public boolean activate() {
        status = "2";
        return Methods.inventoryEmpty() && !CHICKEN_TILE.isOnScreen();
	}

	@Override
	public void execute() {
        Methods.walkPath(CHICKEN_TILE, "pen");
        Methods.checkGate();
        Methods.walkPath(new Tile(3229, 3298, 0), "chicken");
    }
}
