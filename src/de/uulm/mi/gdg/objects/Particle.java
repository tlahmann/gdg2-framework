package de.uulm.mi.gdg.objects;

import de.uulm.mi.gdg.GdGMain;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import static de.uulm.mi.gdg.utils.GdGConstants.AnimationStates.RUNNING;

class Particle {
    private PApplet c = GdGMain.canvas;
    private PShape self;
    private PVector position;
    private PVector velocity;
    private float orientation;
    private boolean isAlive = true;

    Particle(PVector position, float weight, int color) {
        // if we give the position in the constructor it gets called by reference
        this.position = position.copy();

        orientation = c.random(0, PConstants.TWO_PI);
        float magnitude = c.random(10);
        velocity = new PVector(magnitude * PApplet.sin(orientation), magnitude * PApplet.cos(orientation));

        self = c.createShape(PConstants.GROUP);
        for (int i = 0; i < 5; i++) {
            PShape line = c.createShape(PConstants.RECT, -weight * 20, -weight / 2, weight * 20, weight / 2);
            line.rotate(PApplet.radians(i * 8));
            line.setFill(color);
            self.addChild(line);
        }
        self.setStroke(false);
    }

    /**
     * Update a particles position according to its velocity
     */
    void update(PVector parentPosition) {
        if (GdGMain.state != RUNNING) return;
        velocity.mult(velocity.mag() > 0.05 ? 0.95f : 0f);
        // Location changes by velocity
        position.add(velocity);
        if (position.dist(parentPosition) <= 100) {
            isAlive = false;
        }
    }

    /**
     * Displays the shape onto the stored static canvas object.
     */
    void display() {
        if (!isAlive) return;
        // Locating and drawing the shape
        c.pushMatrix();
        c.translate(position.x, position.y);
        c.rotate(orientation);
        c.shape(self);
        c.popMatrix();
    }
}
