package de.uulm.mi.gdg;

import de.looksgood.ani.Ani;
import de.uulm.mi.gdg.objects.Triangle;
import de.uulm.mi.gdg.utils.AniExporter;
import de.uulm.mi.gdg.utils.GUI;
import de.uulm.mi.gdg.utils.GdGConstants.AnimationStates;
import de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates;
import de.uulm.mi.gdg.utils.GdGConstants.ExportStates;
import de.uulm.mi.gdg.utils.Player;
import processing.core.PApplet;

import static de.uulm.mi.gdg.utils.GdGConstants.AnimationStates.*;
import static de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates.DEBUG;
import static de.uulm.mi.gdg.utils.GdGConstants.ExportStates.EXPORTING;
import static de.uulm.mi.gdg.utils.GdGConstants.ExportStates.NOT_EXPORTING;

public class GdGMain extends PApplet {
    public static AnimationStates state = READY;
    private static ExportStates exportState = NOT_EXPORTING;
    private static DevelopmentStates devState = DEBUG;
    public static PApplet canvas;

    private static GUI gui;
    private static Player player;

    private AniExporter ae;

    private Triangle child;

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
        player.song().rewind();
        child = new Triangle(0.1f, -0.1f);
    }

    @Override
    public void draw() {
        background(0);

        child.update(player.song().position());
        child.display();

        if (exportState == EXPORTING) {
            ae.setSoundTime(player.song().position());
        }
        if (devState == DEBUG) {
            gui.update(player.song().position(), frameRate);
            gui.display();
        }
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
            player.song().play();
            gui.hide();
        } else {
            player.song().pause();
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

    public static void main(String[] args) {
        PApplet.main(new String[]{GdGMain.class.getName()});
    }
}
