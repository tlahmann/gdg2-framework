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

    Particle(PVector position, float weight, int color) {
        // if we give the position in the constructor it gets called by reference
        this.position = position.copy();

        float orientation = c.random(0, PConstants.TWO_PI);
        float magnitude = c.random(20);
        velocity = new PVector(magnitude * PApplet.sin(orientation), magnitude * PApplet.cos(orientation));

        self = c.createShape(PConstants.ELLIPSE, 0, 0, weight, weight);
        self.setFill(color);
        self.setStroke(false);
    }

    /**
     * Update a particles position according to its velocity
     */
    void update() {
        if (GdGMain.state != RUNNING) return;
        velocity.mult(velocity.mag() > 0.05 ? 0.95f : 0f);
        // Location changes by velocity
        position.add(velocity);
    }

    /**
     * Displays the shape onto the stored static canvas object.
     */
    void display() {
        // Locating and drawing the shape
        c.pushMatrix();
        c.translate(position.x, position.y);
        c.shape(self);
        c.popMatrix();
    }
}
