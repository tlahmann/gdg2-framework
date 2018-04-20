package de.uulm.mi.gdg;

import de.looksgood.ani.Ani;
import de.uulm.mi.gdg.utils.*;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.data.StringList;

import java.io.File;
import java.util.ArrayList;

public class GdGMain extends PApplet {
    private static GUI gui;

    private ArrayList<CustomAnimation> anis;
    private CustomAnimation ani;

    //    BufferedReader reader;
    private AniExporter ae;
//    private Thread aet;

    private float background = 255;

    private boolean exporting = false;

    public void settings() {
        setSize(1240, 720);
    }

    @Override
    public void setup() {
        // Produce the video as fast as possible
        frameRate(60);

        // Read a sound file and output a txt file
        // with the FFT analysis.
        // It uses Minim, because the standard
        // Sound library did not work in my system.

        Player p = new Player(this, "./data/Syntask - The Best of LMMS Vol.1 - 01 Galaksy.mp3");
        gui = new GUI(this, p);
        ae = new AniExporter(this, "Syntask - The Best of LMMS Vol.1 - 01 Galaksy.mp3", "GdG-export.mp4");
//        aet = new Thread(ae);
//        aet.start();

        Ani.init(this);

        startAnimation();
    }

    float movieFPS = 5;
    float frameDuration = (1 / movieFPS) * 1000;
    float lastFrame = System.currentTimeMillis();

    @Override
    public void draw() {
//        if (ae.isExporting()) return;

        background(background);
        stroke(140, 69, 24);
        HUD_text(new String[]{"Gdg2", frameRate + "", frameCount + ""});
        fill(123, 23, 200);
        rect(this.width * 0.12f, this.height * 0.33f, 300, 400);
        ellipse(900, 300, 100, 200);
        triangle(300, 450, 650, 20, 1000, 450);
        update(gui.getAudioPlayer().getSong().position());
        display();
        ae.setSoundTime(gui.getAudioPlayer().getSong().position());
//        System.out.println(gui.getAudioPlayer().getSong().position());
        /*if (System.currentTimeMillis() > lastFrame + frameDuration) {
            ae.soundTime = gui.getAudioPlayer().getSong().position();
//            ae.startExporting();
            lastFrame = System.currentTimeMillis();
        }*/
    }

    /**
     * HUD text upper left corner
     *
     * @param texts array of texts to display
     */
    private void HUD_text(String[] texts) {
        textSize(16);
        fill(57, 255, 20, 204);
        for (int i = 0; i < texts.length; i++) {
            text(texts[i], 20, 20 + i * 25);
        }
    }

    /**
     * Initializes the animations into the anis-list to get a whole new start even if the song restarts.
     */
    private void startAnimation() {
        anis = AniImporter.importAnimation(this, "./data/timing/timing.json", "background");
    }

    /**
     * Takes a value of the position of the playhead from the equalizer. It is intended to start animations just when
     * the cue-position is greater than the start position of the animation.
     * <p>A possible error could be that if the animation is not gone through linearly all animations that have a
     * smaller start value than the cue they get all started simultaneously.</p>
     *
     * @param val the cue position of the song
     */
    public void update(float val) {
        if (anis.size() == 0) {
            return;
        }
        if (val / 1000 < anis.get(0).getStart()) {
            return;
        }

        ani = anis.remove(0);
    }

    /**
     * Starts the animation if one is present.
     */
    public void display() {
        if (ani == null) {
            return;
        }
        Ani.to(this, ani.getDuration(), ani.getParams(), ani.getValue(), ani.getMode());
        ani = null;
    }

    public void keyPressed() {
        if (key == 'e' || key == 'E') {
            if (ae.isRunning()) {
                ae.endExporting();
            } else {
                ae.startExporting();
                gui.playPause(0);
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{GdGMain.class.getName()});
    }
}
