package de.uulm.mi.gdg.utils;

import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

/**
 * Created by Tobias Lahmann on 25.03.2017.
 */
public class Player {
    private AudioPlayer song;
    private AudioSample sample;
    private FFT fft;

    public Player(PApplet _parent, String _file) {
        Minim minim = new Minim(_parent);
        song = minim.loadFile(_file);
        sample = minim.loadSample(_file, 2048);
        fft = new FFT(song.bufferSize(), song.sampleRate());
    }

    public void togglePlaying() {
        if (!song.isPlaying()) {
            song.play();
        } else {
            song.pause();
            song.cue(0);
        }
    }

    public FFT getFFT() {
        return this.fft;
    }

    public AudioPlayer getSong() {
        return this.song;
    }

    public AudioSample getSample() {
        return this.sample;
    }

    public boolean isPlaying() {
        return this.song.isPlaying() || this.song.position() > 0;
    }
}
