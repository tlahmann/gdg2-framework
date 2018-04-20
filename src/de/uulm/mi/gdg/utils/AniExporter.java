package de.uulm.mi.gdg.utils;

import com.hamoid.VideoExport;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AniExporter extends PApplet {
    private VideoExport videoExport;
    float movieFPS = 30;
    float frameDuration = 1 / movieFPS;
    private boolean running = true;
    private boolean exporting = false;
    ArrayList<Frame> frames = new ArrayList<>();
    private volatile Frame frame = null;

    private PApplet canvas;
    private String audioFile;
    private String videoFile;

    private PGraphics pg;

    public AniExporter(PApplet canvas, String audioFile, String videoFile) {
        super();
        this.canvas = canvas;
        this.audioFile = audioFile;
        this.videoFile = videoFile;
        PApplet.runSketch(new String[]{"Graphical User Interface"}, this);
        pg = createGraphics(100, 100);
    }

    @Override
    public void settings() {
        setSize(canvas.width, canvas.height);

    }

    @Override
    public void setup() {
        // Set up the video exporting
        videoExport = new VideoExport(this);
        videoExport.setFrameRate(movieFPS);
        videoExport.setAudioFileName(audioFile);
        videoExport.setMovieFileName(videoFile);
    }

    public void startExporting() {
//        System.out.println("export started");
        System.out.println("(export started) Size of frames array: " + frames.size());
        videoExport.startMovie();
        exporting = true;
    }

    public void pushFrame(float time, PGraphics graphics) {
        frames.add(new Frame(time, graphics.copy()));
    }

    public void finish() {
        running = false;
    }

    public boolean isExporting() {
        return exporting;
    }

    float[] p = {8.707f, 0.3177f, 0.3177f, 0.7030f, 0.7030f, 1.0884f, 1.0884f, 1.0884f, 1.0884f, 5.1124f, 5.1124f, 9.1364f, 9.1364f, 10.6494f, 10.6494f, 12.1624f, 12.1624f, 9.3293f, 9.3293f, 8.3221f, 8.3221f, 12.5379f, 12.5379f, 16.2832f, 16.2832f, 20.3226f, 20.3226f, 20.0463f, 20.0463f, 3.9735f, 3.9735f, 19.3278f, 19.3278f, 15.9145f, 15.9145f, 8.9447f, 8.9447f, 7.1390f, 7.1390f, 3.4550f, 3.4550f, 3.0808f, 3.0808f, 1.6318f, 1.6318f, 0.8853f, 0.8853f, 0.4932f, 0.4932f, 0.3779f, 0.3779f, 0.2804f, 0.2804f, 0.2530f, 0.2530f, 0.1013f, 0.1013f, 0.0719f, 0.0719f, 0.0675f, 0.0675f};

    @Override
    public void draw() {
        if (!exporting) {
            return;
        }

        if (frames.size() > 0) {
            frame = frames.remove(0);
            System.out.println("Frame removed. Remaining: " + frames.size());
        } else {
            videoExport.endMovie();
            exit();
        }
//            System.out.println(frame.time);
//            System.out.println("(exporting) Size of frames array: " + frames.size());
//            System.out.println(videoExport.getCurrentTime());
//        videoExport.setGraphics(frame.image);
        while (videoExport.getCurrentTime() < frame.time + frameDuration * 0.5f) { //gui.getAudioPlayer().getSong().position()) {
            image(frame.image, 0, 0);
//                canvas.println(videoExport.getCurrentTime());
            videoExport.saveFrame();
        }
    }

    private class Frame {
        float time;
        PImage image;

        private Frame(float time, PImage img) {
            this.time = time;
            this.image = img;
        }
    }
}
