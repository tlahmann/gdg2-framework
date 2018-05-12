package de.uulm.mi.gdg;

import de.looksgood.ani.Ani;
import de.uulm.mi.gdg.objects.Hexagon;
import de.uulm.mi.gdg.objects.Triangle;
import de.uulm.mi.gdg.utils.GUI;
import de.uulm.mi.gdg.utils.GdGConstants.AnimationStates;
import de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates;
import de.uulm.mi.gdg.utils.Player;
import processing.core.PApplet;
import processing.data.JSONArray;

import static de.uulm.mi.gdg.utils.GdGConstants.AnimationStates.*;
import static de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates.DEBUG;
import static de.uulm.mi.gdg.utils.GdGConstants.DevelopmentStates.DEPLOY;

/**
 * Main class for the processing application. This class is the main PApplet and will act as controller.
 */
public class GdGMain extends PApplet {
    private final String SONG = "./data/6_i_fazil say, patricia kopatchinskaja — bartók - rumänische volkstänze sz. 56 - 2. allegro (briul).mp3";
    private final String COLORS = "./data/colorbrewer.json";
    public static AnimationStates state = READY;
    private static DevelopmentStates devState = DEPLOY; // Development state, can eitehr be DEBUG or DEPLOY
    public static PApplet canvas;

    private static GUI gui;
    private static Player player;

    private Triangle child;
    private Hexagon parent;

    private int childColor;
    private int parentColor;
    private int backgroundColor;

    @Override
    public void settings() {
        if (devState == DEBUG) {
            setSize(1240, 720);
        } else {
            setSize(1920, 1080);
            fullScreen(2);
        }
    }

    @Override
    public void setup() {
        canvas = this;
        frameRate(60);
        surface.setResizable(true);
        player = new Player(SONG);
        gui = new GUI();

        Ani.init(this);

        loadColors(0);
        initialize();
    }

    /**
     * Init method. Called when the animation is loaded or when the reset button is pressed.
     * Do not change the visibility, because the gui relies on it being 'public'
     */
    public void initialize() {
        player.song().rewind();
        player.song().pause();
        child = new Triangle(0.1f, -0.1f, childColor);
        parent = new Hexagon(1.2f, 0.2f, parentColor);
        state = READY;
    }

    @Override
    public void draw() {
        background(backgroundColor);

        child.update(player.song().position(), parent.getPosition());
        child.display();

        parent.update(player.song().position());
        parent.display();

        // =====
        // Utility procedure, do not change unless you need to. Put all your drawing logic above
        // =====

        /* At the end of the animation the gui should be displayed. For this we'll check if the song is just about half
           a second away from the end*/
        if (Math.abs(player.song().length() - player.song().position()) < 500 && state == RUNNING) {
            state = READY;
        }
        /* If the animation is running we need to check if the gui is visible. If the animation is not running we
           want to show the gui at all times */
        if (state == RUNNING) {
            if (gui.isVisible()) gui.hide();
        } else {
            if (!gui.isVisible()) gui.show();
        }
        // This is the last line of the draw method, because it should always be rendered last (on top)
        if (devState == DEBUG) {
            gui.update(player.song().position(), frameRate);
            gui.display();
        }
    }

    /**
     * Overwritten method called when a keyboard key is pressed. The key that got pressed is stored in the <pre>key</pre>
     * variable within the PApplet
     */
    @Override
    public void keyPressed() {
        switch (Character.toLowerCase(key)) {
            case ' ':
            case ENTER:
                // Switch between running and paused state when pressing space
                playPause();
                break;
            case 'h':
                loadColors(0);
                break;
            case 'j':
                loadColors(1);
                break;
            case 'k':
                loadColors(2);
                break;
            case 'l':
                loadColors(3);
                break;
            default:
                // Do nothing
                break;
        }
        super.keyPressed();
    }

    /**
     * Play pause method changes the running state, shows/hides the gui and either resumes or pauses all animations.
     * Do not change the visibility, because the gui relies on it being 'public'
     */
    public void playPause() {
        state = state == RUNNING ? PAUSED : RUNNING;
        if (state == RUNNING) {
            player.song().play();
            gui.hide();
            child.resume();
            parent.resume();
        } else {
            player.song().pause();
            gui.show();
            child.pause();
            parent.pause();
        }
    }

    /**
     * Load the desired color scheme from an external file.
     * Do not change the visibility, because the gui relies on it being 'public'
     */
    public void loadColors(int colorValue) {
        JSONArray file = this.loadJSONArray(COLORS);
        JSONArray color = file.getJSONObject(colorValue).getJSONArray("colors");
        backgroundColor = rgbStringToColor(color.getString(2));
        childColor = rgbStringToColor(color.getString(0));
        parentColor = rgbStringToColor(color.getString(4));
        System.out.println("Colors loaded: " + colorValue);
        initialize();
    }

    /**
     * Calculates an integer color value used by processing from a string in the format 'rgb(0,255,128)' or similar
     */
    private int rgbStringToColor(String rgbString) {
        String a = rgbString.split("[(]")[1].split("[)]")[0];
        String[] rgb = a.split(",");
        return color(unhex("FF" + Integer.toString(Integer.parseInt(rgb[0]), 16) +
                Integer.toString(Integer.parseInt(rgb[1]), 16) +
                Integer.toString(Integer.parseInt(rgb[2]), 16)));
    }

    /**
     * Main entry point for our animation
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[]{GdGMain.class.getName()});
    }
}
