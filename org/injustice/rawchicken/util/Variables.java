package org.injustice.rawchicken.util;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


public class Variables {
    // dynamics
    public static Timer runTime = new Timer(0);
    public static String status;
    public static int chickensPicked;


	//constants
	public static final int RAW_CHICKEN_ID = 2138;
	public static final Tile CHICKEN_TILE = new Tile(3237, 3295, 0);
	public static final Tile HALF_TILE = new Tile(3228, 3270, 0);
	public static final Tile BANK_TILE = new Tile(3214, 3257, 0);
	public static final int DEPOSIT_BOX_ID = 79036;
	public static final int CLOSED_GATE_ID = 45208;
    public static final Area CHICKEN_AREA = new Area(new Tile(3225, 3300, 0), new Tile(3226, 3295, 0), new Tile(3231, 3295, 0),
            new Tile(3231, 3287, 0), new Tile(3234, 3288, 0), new Tile(3234, 3301, 0));
    public static final WidgetChild DEPOSIT_BOX_MAIN = Widgets.get(11, 1);
    public static final WidgetChild DEPOSIT_BOX_DEPOSIT_INVENTORY = Widgets.get(11, 19);
    public static final WidgetChild DEPOSIT_BOX_CLOSE = Widgets.get(11, 8);

}
