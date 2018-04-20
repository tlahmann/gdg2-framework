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
    private JSONObject settings;
    private String settingsPath;

    private volatile ArrayList<CustomAnimation> anis;
    private CustomAnimation ani;

    //    BufferedReader reader;
    public AniExporter ae;

    float background = 255;

    @Override
    public void settings() {
        this.settingsPath = "data" + File.separator + "settings.json";
        File var5 = new File(this.settingsPath);
        if (var5.isFile()) {
            this.settings = this.loadJSONObject(this.settingsPath);
        } else {
            this.settings = new JSONObject();
        }

        Object width = this.settings.get("width");
        Object height = this.settings.get("height");

        if (width instanceof Integer && height instanceof Integer) {
            setSize((int) width, (int) height);
        }
    }

    @Override
    public void setup() {
        // Produce the video as fast as possible
        frameRate(100);

        // Read a sound file and output a txt file
        // with the FFT analysis.
        // It uses Minim, because the standard
        // Sound library did not work in my system.

        Player p = new Player(this, "." + File.separator + "data" + File.separator + this.settings.get("song"));
        gui = new GUI(this, p);
        AniImporter.audioToTextFile(this, this.settings.get("song").toString(), p.getSample(), "|");

        ae = new AniExporter(this, this.settings.get("song").toString(), "GdG-export.mp4");

        // Now open the text file we just created for reading
//        reader = createReader(audioFilePath + ".txt");


        Ani.init(this);

        startAnimation();
    }

    @Override
    public void draw() {
//        if (ae.isExporting()) return;
        background(background);
        stroke(140, 69, 24);
        HUD_text(new String[]{"Gdg2", frameRate + "", frameCount + ""});
        noFill();
        rect(this.width * 0.12f, this.height * 0.33f, 300, 400);
        ellipse(900, 300, 100, 200);
        triangle(300, 450, 650, 20, 1000, 450);
        if (gui.getAudioPlayer().getSong().position() < 10000) {
            ae.pushFrame(gui.getAudioPlayer().getSong().position() / 1000f, this.g);
        } else if (!ae.isExporting()) {
            ae.startExporting();
//            this.stop();
        }
//        pushMatrix();
        /*String line;
//        System.out.println(this.g);
        if (gui.getAudioPlayer().isPlaying()) {
//            System.out.println("exporting");
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                line = null;
            }
            // End when we have exported enough frames
            // to match the sound duration.
            if (line == null) {
                // Done reading the file.
                // Close the video file.
                videoExport.endMovie();
                exit();
            } else {
//                System.out.println(line);
                String[] p = split(line, SEP);
                // The first column indicates
                // the sound time in seconds.
                float soundTime = Float.parseFloat(p[0]);
                System.out.println(gui.getAudioPlayer().getSong().position());
//                System.out.println(videoExport.getCurrentTime() + ":::" + soundTime);
                while (videoExport.getCurrentTime() < soundTime + frameDuration * 0.5f) { //gui.getAudioPlayer().getSong().position()) {
                    update(soundTime);
                    display();
                    videoExport.saveFrame();
                }
            }
        }*/
    }

    /**
     * HUD text upper left corner
     *
     * @param texts
     */
    void HUD_text(String[] texts) {
        textSize(16);
        fill(57, 255, 20, 204);
        for (int i = 0; i < texts.length; i++) {
            text(texts[i], 20, 20 + i * 25);
        }
    }

    /**
     * Initializes the animations into the anis-list to get a whole new start even if the song restarts.
     */
    public void startAnimation() {
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
        if (val < anis.get(0).getStart()) {
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

    private static JSONArray toJSONArray(String var0) {
        return new JSONArray(new StringList(var0.split(" ")));
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{GdGMain.class.getName()});
    }
}
