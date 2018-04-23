package de.uulm.mi.gdg;

import de.looksgood.ani.Ani;
import de.looksgood.ani.AniCore;
import de.uulm.mi.gdg.utils.*;
import de.uulm.mi.gdg.utils.GdGConstants.AnimationStates;
import de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates;
import de.uulm.mi.gdg.utils.GdGConstants.ExportStates;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;

import static de.uulm.mi.gdg.utils.GdGConstants.AnimationStates.*;
import static de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates.*;
import static de.uulm.mi.gdg.utils.GdGConstants.ExportStates.*;

public class GdGMain extends PApplet {
    private static AnimationStates state = READY;
    private static ExportStates exportState = NOT_EXPORTING;
    public static DevelopmentStates devState = DEBUG;
    public static PApplet canvas;

    private static GUI gui;
    private static Player player;

    private ArrayList<CustomAnimation> anis = new ArrayList<>();
    private ArrayList<Ani> activeAnis = new ArrayList<>();
    private CustomAnimation ani;

    private AniExporter ae;

    private float fill = 0;
    private float rotation = 0;

    public void settings() {
        setSize(1240, 720);
    }

    @Override
    public void setup() {
        canvas = this;
        frameRate(60);
        player = new Player("./data/6_i_fazil say, patricia kopatchinskaja — bartók - rumänische volkstänze sz. 56 - 2. allegro (briul).mp3");
        gui = new GUI();

        Ani.init(this);

        initialize();
    }

    public void initialize() {
        player.song().cue(0);
        activeAnis.forEach(AniCore::end);
        activeAnis.clear();
        anis.clear();

        fill = 0;
        rotation = 0;
        importAnimation();
    }

    @Override
    public void draw() {
        background(255);
        stroke(140, 69, 24);
        fill(fill);
        pushMatrix();
        translate(width / 2, height / 2);
        rotate(rotation);
        rect(this.width * -0.12f, this.height * -0.33f, 300, 400);
        ellipse(200, 100, 100, 100);
        triangle(300, 450, 650, 20, 1000, 450);
        update(player.song().position());
        display();
        popMatrix();

        if (exportState == EXPORTING) {
            ae.setSoundTime(player.song().position());
        }
        if (devState == DEBUG) {
            gui.update(player.song().position(), frameRate);
            gui.display();
        }
    }

    /**
     * Initializes the animations into the anis-list to get a whole new start even if the song restarts.
     */
    private void importAnimation() {
        anis = AniImporter.importAnimation(this, "./data/timing/timing.json", "fill");
        anis.addAll(AniImporter.importAnimation(this, "./data/timing/main.json", "rotation"));
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
        CustomAnimation a = anis.get(0);
        if (val / 1000 < a.getStart()) {
            return;
        }
        if (val / 1000 > a.getStart() + a.getDuration() + 0.5) {
            anis.remove(0);
            return;
        }

        ani = anis.remove(0);
        activeAnis.removeIf(AniCore::isEnded);
    }

    /**
     * Starts the animation if one is present.
     */
    public void display() {
        if (ani == null) {
            return;
        }
        activeAnis.add(Ani.to(this, ani.getDuration(), ani.getParams(), ani.getValue(), ani.getMode()));
        ani = null;
    }

    public void keyPressed() {
        switch (Character.toLowerCase(key)) {
            case ' ':
            case ENTER:
                // Switch between running and paused state when pressing space
                playPause();
                break;
            default:
                // Do nothing
                break;
        }
        super.keyPressed();
    }

    public void playPause() {
        state = state == RUNNING ? PAUSED : RUNNING;
        if (state == RUNNING) {
//            player.song().cue(20000);
            player.song().play();
            gui.hide();
            // Remove ended animations because we cannot resume those that are already done
            activeAnis.removeIf(AniCore::isEnded);
            activeAnis.forEach(AniCore::resume);
        } else {
            player.song().pause();
            activeAnis.forEach(AniCore::pause);
            gui.show();
        }
    }

    public void startExporting() {
        ae = new AniExporter(this, "6_i_fazil say, patricia kopatchinskaja — bartók - rumänische volkstänze sz. 56 - 2. allegro (briul).mp3", "GdG-export.mp4");
        ae.startExporting();
        state = RUNNING;
        player.song().play();
        gui.hide();
        exportState = EXPORTING;
    }

    public void toggleSomething() {
        devState = devState == DEBUG ? DEPLOY : DEBUG;
        System.out.println(devState);
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{GdGMain.class.getName()});
    }
}
