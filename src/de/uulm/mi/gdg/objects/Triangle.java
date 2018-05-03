package de.uulm.mi.gdg.objects;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniCore;
import de.uulm.mi.gdg.GdGMain;
import de.uulm.mi.gdg.utils.AniImporter;
import de.uulm.mi.gdg.utils.CustomAnimation;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;

public class Triangle {
    private PApplet c = GdGMain.canvas;
    private float xPos;
    private float yPos;
    private float rotation;
    private PShape shape;

    private ArrayList<Particle> particleList = new ArrayList<>();
    private int particles = 0;

    private ArrayList<CustomAnimation> anis = new ArrayList<>();
    private ArrayList<Ani> activeAnis = new ArrayList<>();

    public Triangle(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = 0;

        shape = c.createShape(PConstants.TRIANGLE, -35, 25, 35, 25, 0, -35);
        shape.setFill(c.color(123, 255, 255));
        shape.setStroke(false);
        importAnimation();
    }

    private void importAnimation() {
        anis = AniImporter.importAnimation(c, "./data/timing/child.json", "xPos");
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/child.json", "yPos"));
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/child.json", "rotation"));
        anis.addAll(AniImporter.importAnimation(c, "./data/timing/child.json", "particles"));
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
        spawn();

        for (Particle p : particleList) {
            p.update();
        }

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
        for (Particle p : particleList) {
            p.display();
        }
        c.pushMatrix();
        c.translate(c.width * xPos, c.height * yPos);
        c.rotate(rotation);
        c.shape(shape);
        c.popMatrix();
    }

    /**
     * If particles are to be created this adds them to the list of livin particles.
     */
    private void spawn() {
        PVector position = new PVector(c.width * xPos, c.height * yPos);
        float weight = 2.0f;
        int color = c.color(48, 48, 48);

        for (; particles > 0; particles--) {
            particleList.add(new Particle(position, weight, color));
        }
    }
}
