package de.uulm.mi.gdg.utils;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import de.uulm.mi.gdg.GdGMain;
import processing.core.PApplet;

/**
 * Created by Tobias Lahmann on 25.03.2017.
 */
public class Player {
    private AudioPlayer song;

    public Player(String _file) {
        Minim minim = new Minim(GdGMain.canvas);
        song = minim.loadFile(_file);
    }

    public void togglePlaying() {
        if (!song.isPlaying()) {
            song.play();
        } else {
            song.pause();
            song.cue(0);
        }
    }

    public AudioPlayer song() {
        return this.song;
    }
}
