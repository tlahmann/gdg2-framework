package de.uulm.mi.gdg.utils;

import controlP5.ControlFont;
import controlP5.ControlP5;
import de.uulm.mi.gdg.GdGMain;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PShape;

import static processing.core.PConstants.*;
import static de.uulm.mi.gdg.utils.GdGConstants.Color.*;

public class GUI {
    private ControlP5 cp5;

    private int now = 0;
    private float fps = 0;
    private float frameRate = 0;

    public GUI() {
        PApplet c = GdGMain.canvas;

        // Do not change the font
        PFont pfont = c.loadFont("font/Roboto-Regular-14.vlw"); // use true/false for smooth/no-smooth
        ControlFont font = new ControlFont(pfont, 14);

        cp5 = new ControlP5(c);
        cp5.addButton("Play")
                .setPosition(20, 125)
                .setSize(getWidth("Play"), 36)
                .setFont(font)
                .setColor(LIGHT_GREEN)
                .plugTo(c, "playPause");
        cp5.addButton("Zurücksetzen")
                .setPosition(20, 176)
                .setSize(getWidth("Zurücksetzen"), 36)
                .setFont(font)
                .setColor(BLUE_GREY)
                .plugTo(c, "initialize");
        cp5.addButton("Exit")
                .setPosition(25, c.height - 73)
                .setSize(150, 48)
                .setFont(font)
                .setColor(AMBER)
                .plugTo(c, "exit");
        cp5.addButton("Start Export")
                .setPosition(c.width - 175, c.height - 73)
                .setSize(150, 48)
                .setFont(font)
                .setColor(RED)
                .plugTo(c, "startExporting");
    }

    public void hide() {
        cp5.hide();
    }

    public void show() {
        cp5.show();
    }

    public boolean isVisible() {
        return cp5.isVisible();
    }

    private int getWidth(String text) {
        int width = 0;
        for (char c : text.toCharArray()) {
            switch (Character.toLowerCase(c)) {
                case 'm':
                case 'w':
                    width += 12;
                    break;
                case ' ':
                    width += 4;
                    break;
                case 'l':
                case 'r':
                case 'b':
                case 'd':
                case 'o':
                    width += 9;
                    break;
                default:
                    width += 8;
                    break;
            }
        }
        return Math.max(width + 32, 64);
    }

    public void update(int time, float framerate) {
        now = time;
        frameRate = framerate;
    }

    public void display() {
        int sec = now / 1000, ms = now % 1000;
        float smoothing = 0.95f;
        fps = (fps * smoothing) + (frameRate * (1.0f - smoothing));
        String[] texts = new String[]{"Gdg2 Animation",
                String.format("Framerate: %.2f", fps) + " f/s",
                "Song Position: " + sec + ":" + ms};
        GdGMain.canvas.textSize(16);
        GdGMain.canvas.textAlign(LEFT, TOP);
        GdGMain.canvas.fill(0, 0, 0, 160);
        GdGMain.canvas.noStroke();
        GdGMain.canvas.rect(20, 20, 175, texts.length * 25);
        GdGMain.canvas.fill(57, 255, 20);
        for (int i = 0; i < texts.length; i++) {
            GdGMain.canvas.text(texts[i], 25, 25 + i * 25);
        }
    }
}
