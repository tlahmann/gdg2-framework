package de.uulm.mi.gdg.utils;

import com.hamoid.VideoExport;
import processing.core.PApplet;

public class AniExporter implements Runnable {
    private VideoExport videoExport;
    private float movieFPS = 30;
    private float frameDuration = 1 / movieFPS;
    private final Thread t;
    private volatile boolean shouldStop = false;
    private volatile float soundTime = 0;

    public AniExporter(PApplet canvas, String audioFile, String videoFile) {
        // Set up the video exporting
        videoExport = new VideoExport(canvas);
        videoExport.setFrameRate(movieFPS);
        videoExport.setAudioFileName(audioFile);
        videoExport.setMovieFileName(videoFile);
        videoExport.startMovie();

        t = new Thread(this, "export thread");
    }

    public void startExporting() {
        System.out.println("export started");
        t.start();
    }

    public void endExporting() {
        System.out.println("export stopped");
        shouldStop = true;
    }

    public boolean isRunning() {
        return t.isAlive();
    }

    public void setSoundTime(float soundTime) {
        this.soundTime = soundTime;
    }

    @Override
    public void run() {
        while (!shouldStop) {
            while (videoExport.getCurrentTime() * 1000 < soundTime + frameDuration * 0.5) {
                videoExport.saveFrame();
            }
        }
        System.out.println("Export ended");
        videoExport.endMovie();
    }
}

