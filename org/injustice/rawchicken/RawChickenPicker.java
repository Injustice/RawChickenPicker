package org.injustice.rawchicken;

import org.injustice.rawchicken.strategies.Deposit;
import org.injustice.rawchicken.strategies.Pick;
import org.injustice.rawchicken.strategies.WalkBox;
import org.injustice.rawchicken.strategies.WalkPen;
import org.injustice.rawchicken.util.Methods;
import org.injustice.rawchicken.util.Paint;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.net.GeItem;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Injustice
 * Date: 29/03/13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */

import static org.injustice.rawchicken.util.Var.*;

@Manifest(authors = {"Bartsome/Injustice"}, name = "Raw Chicken Picker", description = "Picks raw chicken in lumbridge and banks", version = RawChickenPicker.VERSION)
public class RawChickenPicker extends ActiveScript implements PaintListener, MouseListener {
    public static final double VERSION = 2.1;

    private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
    private Tree jobContainer = null;

    public final void provide(final Node... jobs) {
        for (final Node job : jobs) {
            if (!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    public void onStart() {
        Mouse.setSpeed(Mouse.Speed.VERY_FAST);
        startTime = System.currentTimeMillis();
        provide(new WalkBox(), new Pick(), new Deposit(), new WalkPen());
        chickenPrice = GeItem.lookup(RAW_CHICKEN_ID[0]).getPrice();
    }

    @Override
    public int loop() {
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }
        return 25;
    }

    @Override
    public void onRepaint(Graphics g1) {
        Paint.onRepaint(g1);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle screeny = new Rectangle(112, 210, 81, 11);
        Rectangle close = new Rectangle(132, 285, 59, 11);
        Rectangle open = new Rectangle(132, 285, 59, 11);
        Rectangle mouse = new Rectangle(131, 270, 61, 11);
        Rectangle tiles = new Rectangle(140, 255, 52, 11);
        Point p = e.getPoint();
        if (close.contains(p) && !hidePaint) hidePaint = true;
        else if (open.contains(p) && hidePaint) hidePaint = false;
        else if (screeny.contains(p))Methods.savePaint(5, 178, 190, 120);
        else if (mouse.contains(p) && showMouse) showMouse = false;
        else if (mouse.contains(p) && !showMouse) showMouse = true;
        else if (tiles.contains(p) && showTiles) showTiles = false;
        else if (tiles.contains(p) && !showTiles) showTiles = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}