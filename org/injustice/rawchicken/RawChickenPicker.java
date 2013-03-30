package org.injustice.rawchicken;

import org.injustice.rawchicken.strategies.*;
import org.injustice.rawchicken.util.Paint;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Manifest(authors = {"Bartsome/Injustice"}, name = "Raw Chicken Picker", description = "Picks raw chicken in lumbridge and banks")
public class RawChickenPicker extends ActiveScript implements PaintListener {

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
        provide(new WalkBox(), new Pick(), new Deposit(), new WalkPen());
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
        return Random.nextInt(10, 50);
    }

    @Override
    public void onRepaint(Graphics g1) {
        Paint.onRepaint(g1);
    }
}