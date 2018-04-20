package de.uulm.mi.gdg.utils;

import controlP5.ControlP5;
import de.uulm.mi.gdg.GdGMain;
import processing.core.PApplet;

public class GUI {
    private GdGMain m_canvas;
    private Player audioPlayer;

    ControlP5 cp5;
    int c = 0;

    public GUI(GdGMain canvas, Player audioPlayer) {
        m_canvas = canvas;
        this.audioPlayer = audioPlayer;

        cp5 = new ControlP5(canvas);

        cp5.addButton("Play/Pause")
                .setPosition(canvas.width / 2 - 100, canvas.height / 2 + 100)
                .setSize(200, 19)
                .plugTo(this, "playPause");

        cp5.addButton("Start recording")
                .setPosition(canvas.width / 2 - 100, canvas.height / 2 + 50)
                .setSize(200, 19)
                .plugTo(this, "export");

        cp5.addButton("Black/White")
                .setPosition(canvas.width / 2 - 100, canvas.height / 2 + 130)
                .setSize(95, 19)
                .plugTo(this, "blackWhite");

        cp5.addButton("Random Color")
                .setPosition(canvas.width / 2 + 5, canvas.height / 2 + 130)
                .setSize(95, 19)
                .plugTo(this, "randomColor");
    }

    public void playPause(int value) {
        audioPlayer.togglePlaying();
        cp5.hide();
    }

    public void export(int value) {
        playPause(0);
//        m_canvas.ae.startExporting();
    }

    public Player getAudioPlayer() {
        return audioPlayer;
    }
}
