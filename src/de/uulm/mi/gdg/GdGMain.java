package de.uulm.mi.gdg;

import de.looksgood.ani.Ani;
import de.uulm.mi.gdg.utils.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;

import de.uulm.mi.gdg.utils.GdGConstants.AnimationStates;

import static de.uulm.mi.gdg.utils.GdGConstants.AnimationStates.*;

public class GdGMain extends PApplet {
    private static AnimationStates state = MENU;
    public static PApplet canvas;
    private static GUI gui;
    private static Player player;

    private ArrayList<CustomAnimation> anis;
    private CustomAnimation ani;

    private AniExporter ae;

    private float background = 255;
    private float fill = 0;

    public void settings() {
        setSize(1240, 720);
    }

    @Override
    public void setup() {
        canvas = this;

        // Do not change the frame rate configuration
        frameRate(30);

        player = new Player(this, "./data/Syntask - The Best of LMMS Vol.1 - 01 Galaksy.mp3");
        gui = new GUI(this);
//        ae = new AniExporter(this, "Syntask - The Best of LMMS Vol.1 - 01 Galaksy.mp3", "GdG-export.mp4");

        Ani.init(this);

        startAnimation();
    }

    float fps = frameRate;
    float smoothing = 0.95f;

    @Override
    public void draw() {
        background(background);
        stroke(140, 69, 24);
        fill(fill);
        rect(this.width * 0.12f, this.height * 0.33f, 300, 400);
        ellipse(900, 300, 100, 200);
        triangle(300, 450, 650, 20, 1000, 450);
        update(player.getSong().position());
        display();

        if (!player.isPlaying()) {
            int now = player.getSong().position();
            int sec = now / 1000;
            int ms = now % 1000;
            fps = (fps * smoothing) + (frameRate * (1.0f - smoothing));
            String f = String.format("%.1f", fps);
            HUD_text(new String[]{"Gdg2",
                    f + " f/s",
                    sec + ":" + ms});
        }
//        ae.setSoundTime(gui.getAudioPlayer().getSong().position());
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
        anis.addAll(AniImporter.importAnimation(this, "./data/timing/timing.json", "fill"));
        Collections.sort(anis);
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
        switch (Character.toLowerCase(key)) {
            case ' ':
            case ENTER:
                // Switch between running and paused state when pressing space
                state = state == RUNNING ? PAUSED : RUNNING;
                break;
            case 'e':
                System.out.println("E pressed");
                /*if (ae.isRunning()) {
                    ae.endExporting();
                } else {
                    ae.startExporting();
                    playPause(0);
                }*/
                break;
            default:
                // Do nothing
                break;
        }
        super.keyPressed();
    }

    public void playPause(int value) {
        player.togglePlaying();
        gui.hide();
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{GdGMain.class.getName()});
    }
}
