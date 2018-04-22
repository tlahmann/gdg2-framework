package de.uulm.mi.gdg.utils;

import com.hamoid.VideoExport;
import processing.core.PApplet;

public class AniExporter implements Runnable {
    private VideoExport videoExport;
    // Do not change the export frames per second configuration
    private float movieFPS = 30;
    private float frameDuration = 1 / movieFPS;
    private final Thread t;
    private volatile boolean shouldStop = false;
    private volatile float soundTime = 0;

    public AniExporter(PApplet canvas, String audioFile, String videoFile) {
        // Set up the video exporting
        videoExport = new VideoExport(canvas);
        // Set the quality of video and audio.
        // Video quality (0-100)
        // Audio quality e.g 192 very good, 256 near lossless
        videoExport.setQuality(85, 192);
        videoExport.setFrameRate(movieFPS);
        videoExport.setAudioFileName(audioFile);
        videoExport.setMovieFileName(videoFile);
        videoExport.startMovie();

        t = new Thread(this, "export thread");
    }

    public void startExporting() {
        System.out.println("AniExporter: export started.");
        t.start();
    }

    public void endExporting() {
        System.out.println("AniExporter: export stopped.");
        shouldStop = true;
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
        System.out.println("AniExporter: export finished.");
        videoExport.endMovie();
    }
}

