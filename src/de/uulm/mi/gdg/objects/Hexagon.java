package de.uulm.mi.gdg.objects;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniCore;
import de.uulm.mi.gdg.GdGMain;
import de.uulm.mi.gdg.utils.AniImporter;
import de.uulm.mi.gdg.utils.CustomAnimation;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;

import static processing.core.PConstants.*;

public class Hexagon {
    private PApplet c = GdGMain.canvas;
    private float xPos;
    private float yPos;
    private float rotation;
    private PShape shape;

    private ArrayList<CustomAnimation> anis = new ArrayList<>();
    private ArrayList<Ani> activeAnis = new ArrayList<>();

    public Hexagon(float xPos, float yPos, int color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = PI / 2;
        float radius = 200;

        shape = c.createShape();
        shape.beginShape();

        shape.noStroke();
        shape.fill(color);

        for (float a = 0; a < TWO_PI; a += PI / 3) {
            shape.vertex(PApplet.cos(a) * radius / 2, PApplet.sin(a) * radius / 2);
        }
        shape.endShape(CLOSE);

        importAnimation();
    }

    private void importAnimation() {
        anis = AniImporter.importAnimation(c, "./data/timing/parent.json", "xPos");
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/parent.json", "yPos"));
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/parent.json", "rotation"));
        Collections.sort(anis);
        activeAnis = new ArrayList<>();
    }

    /**
     * Takes a value of the position of the playhead from the equalizer. It is intended to start animations just when
     * the cue-position is greater than the start position of the animation.
     *
     * @param time the cue position of the song
     */
    public void update(float time) {
        if (anis.size() == 0) {
            return;
        }
        if (time / 1000 < anis.get(0).getStart()) {
            return;
        }

        // Get a new animation
        CustomAnimation ani = anis.remove(0);
        activeAnis.add(Ani.to(this, ani.getDuration(), ani.getParams(), ani.getValue(), ani.getMode()));
        update(time);

        // Delete old and finished animations
        activeAnis.removeIf(AniCore::isEnded);
    }

    public void pause() {
        activeAnis.forEach(AniCore::pause);
    }

    public void resume() {
        activeAnis.removeIf(AniCore::isEnded);
        activeAnis.forEach(AniCore::resume);
    }

    /**
     * Displays the shape on the stored static canvas object.
     */
    public void display() {
        c.pushMatrix();
        c.translate(c.width * xPos, c.height * yPos);
        c.rotate(rotation);
        c.shape(shape);
        c.popMatrix();
    }

    public PVector getPosition() {
        return new PVector(c.width * xPos, c.height * yPos);
    }
}
