package de.uulm.mi.gdg.objects;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniCore;
import de.uulm.mi.gdg.GdGMain;
import de.uulm.mi.gdg.utils.AniImporter;
import de.uulm.mi.gdg.utils.CustomAnimation;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.Collections;

public class Triangle {
    private PApplet c;
    private float xPos;
    private float yPos;
    private float rotation;
    private PShape shape;

    private ArrayList<CustomAnimation> anis = new ArrayList<>();
    private ArrayList<Ani> activeAnis = new ArrayList<>();

    public Triangle(float xPos, float yPos) {
        c = GdGMain.canvas;
        this.xPos = xPos;
        this.yPos = yPos;

        shape = c.createShape(PConstants.TRIANGLE, -35, 25, 35, 25, 0, -35);
        shape.setFill(c.color(123, 255, 255));
        shape.setStroke(false);
        importAnimation();
    }

    private void importAnimation() {
        anis = AniImporter.importAnimation(c, "./data/timing/child.json", "xPos");
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/child.json", "yPos"));
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/child.json", "rotation"));
        Collections.sort(anis);
        for (CustomAnimation c : anis) {
            System.out.println(c);
        }
    }

    /**
     * Takes a value of the position of the playhead from the equalizer. It is intended to start animations just when
     * the cue-position is greater than the start position of the animation.
     * <p>A possible error could be that if the animation is not gone through linearly all animations that have a
     * smaller start value than the cue they get all started simultaneously.</p>
     *
     * @param time the cue position of the song
     */
    public void update(float time) {
        if (anis.size() == 0) {
            return;
        }
        CustomAnimation a = anis.get(0);
        if (time / 1000 < a.getStart()) {
            return;
        }

        // Get a new animation
        CustomAnimation ani = anis.remove(0);
        activeAnis.add(Ani.to(this, ani.getDuration(), ani.getParams(), ani.getValue(), ani.getMode()));
        update(time);

        // Delete old and finished animations
        activeAnis.removeIf(AniCore::isEnded);
    }

    /**
     * Displays the shape on the stored static canvas object.
     */
    public void display() {
//        System.out.println("Child is at pos: " + xPos + " :: " + yPos);
        c.pushMatrix();
        c.translate(c.width * xPos, c.height * yPos);
        c.rotate(rotation);
        c.shape(shape);
        c.popMatrix();
    }
}
