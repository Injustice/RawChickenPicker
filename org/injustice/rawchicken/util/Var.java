package org.injustice.rawchicken.util;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */


public class Var {

    /*
     * DYNAMICS
     */

        /* -- Timers -- */

    public static Timer runTime = new Timer(0);
    public static long startTime;

        /* -- Strings -- */

    public static String status = "Starting..";

        /* -- Integers -- */

    public static int chickensPicked;
    public static int chickenPrice;
    public static int bonesPicked;

        /* -- Booleans -- */

    public static boolean hidePaint = false;
    public static boolean showTiles = true;
    public static boolean showMouse = true;
    public static boolean doAntiban = false;

	/*
     * CONSTANTS
	 */

        /* -- IDs -- */

    public static final int[] RAW_CHICKEN_ID = {2138};
    public static final int DEPOSIT_BOX_ID = 79036;
    public static final int[] CLOSED_GATE_ID = { 45206, 45208 };

        /* -- WidgetChilds -- */

    public static final WidgetChild DEPOSIT_BOX_MAIN = Widgets.get(11, 1);
    public static final WidgetChild DEPOSIT_BOX_DEPOSIT_INVENTORY = Widgets.get(11, 19);
    public static final WidgetChild DEPOSIT_BOX_CLOSE = Widgets.get(11, 8);

        /* -- Tiles -- */

    public static final Tile CHICKEN_TILE = new Tile(3230, 3298, 0);
    public static final Tile GATE_TILE = new Tile(3238, 3297, 0);
    public static final Tile BANK_TILE = new Tile(3214, 3257, 0);

        /* -- Areas -- */

    public static final Area CHICKEN_AREA = new Area(new Tile(3223, 3302, 0), new Tile(3236, 3302, 0), new Tile(3236, 3294, 0),
            new Tile(3223, 3294, 0));
    public static final Area WEST_OF_HUT = new Area(new Tile(3225, 3301, 1), new Tile(3227, 3301, 1),  // west of hut
            new Tile(3227, 3295, 1), new Tile(3225, 3201, 1));
    public static final Area NEAR_DEPOSITBOX = new Area(new Tile(3226, 3242, 0), new Tile(3232, 3242, 0), new Tile(3232, 3235, 0),
            new Tile(3226, 3235, 0), new Tile(3230, 3232, 0), new Tile(3235, 3232, 0),
            new Tile(3235, 3237, 0), new Tile(3235, 3242, 0), new Tile(3232, 3246, 0),
            new Tile(3227, 3247, 0), new Tile(3222, 3247, 0), new Tile(3220, 3242, 0),
            new Tile(3220, 3237, 0), new Tile(3223, 3233, 0), new Tile(3228, 3232, 0),
            new Tile(3230, 3232, 0), new Tile(3226, 3236, 0));


        /* -- TileArrays -- */  /* Saved if needed in future */

    public static final Tile[] PATH_TO_PEN = new Tile[]{new Tile(3214, 3257, 0), new Tile(3222, 3260, 0), new Tile(3228, 3262, 0),
            new Tile(3238, 3261, 0), new Tile(3245, 3265, 0), new Tile(3241, 3272, 0),
            new Tile(3239, 3280, 0), new Tile(3237, 3288, 0), new Tile(3236, 3295, 0)};
    public static final Tile[] PATH_TO_BOX = new Tile[]{new Tile(3214, 3256, 0), new Tile(3223, 3260, 0), new Tile(3232, 3261, 0),
            new Tile(3240, 3264, 0), new Tile(3240, 3272, 0), new Tile(3238, 3280, 0),
            new Tile(3238, 3288, 0), new Tile(3237, 3296, 0), new Tile(3231, 3297, 0)};

        /* -- Filters -- */    /* Saved if needed in future */

    public static Filter<GroundItem> chickenFilter = new Filter<GroundItem>() {
        @Override
        public boolean accept(GroundItem groundItem) {
            for (int x : RAW_CHICKEN_ID) {
                GroundItem g = GroundItems.getNearest(x);
                return CHICKEN_AREA.contains(g) && g.getId() == x;
            }
            return false;
        }
    };

    public static SceneObject closedGate = SceneEntities.getNearest(new Filter<SceneObject>() {
        public boolean accept(SceneObject so) {
            if (so.getId() == 45206) {
                int y = so.getLocation().getY();
                return y > 3294 && y < 3298;
            }
            return false;
        }
    });
}
