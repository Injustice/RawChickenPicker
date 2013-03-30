package org.injustice.rawchicken.strategies;

import org.injustice.rawchicken.util.Methods;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;

import static org.injustice.rawchicken.util.Variables.*;

public class Pick extends Node {

	@Override
	public boolean activate() {
        status = "1";
        final GroundItem chicken = GroundItems.getNearest(RAW_CHICKEN_ID);
        return !Inventory.isFull() && chicken.isOnScreen();
	}

	@Override
	public void execute() {
        if (!CHICKEN_AREA.contains(Players.getLocal().getLocation())) {
            Methods.checkGate();
        }
        status = "Pickin' chicken";
        GroundItem chicken = GroundItems.getNearest(RAW_CHICKEN_ID);
		if (chicken != null) {
			if (chicken.isOnScreen()) {
				chicken.interact("Take", "chicken");
                chickensPicked++;
                do sleep(1000);
				while(Players.getLocal().isMoving());
			} else Camera.turnTo(chicken);
		} else sleep(500);

	}
}
