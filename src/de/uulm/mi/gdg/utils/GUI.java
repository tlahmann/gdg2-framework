package de.uulm.mi.gdg.utils;

import controlP5.ControlP5;
import processing.core.PApplet;

public class GUI {
    private ControlP5 cp5;

    public GUI(PApplet canvas) {
        InterfaceBuilder ib = new InterfaceBuilder();

        cp5 = new ControlP5(canvas);
        ib.createButton(cp5,
                "Play",
                "red",
                canvas.width / 2 - 100,
                canvas.height / 2 + 90,
                "playPause");
        ib.createButton(cp5,
                "BLACK & WHITE",
                "blue",
                canvas.width / 2 - 100,
                canvas.height / 2 + 130,
                "blackWhite");
        ib.createButton(cp5,
                "RANDOM COLOR",
                "green",
                canvas.width / 2 - 100,
                canvas.height / 2 + 170,
                "randomColor");
        ib.createButton(cp5,
                "TEST BUTTON 1",
                "teal",
                canvas.width / 2 - 100,
                canvas.height / 2 + 210,
                "randomColor");
        ib.createButton(cp5,
                "A longer name for a button",
                "amber",
                canvas.width / 2 - 100,
                canvas.height / 2 + 250,
                240,
                "randomColor");
        ib.createSlider(cp5,
                "SomeSlider",
                "red",
                canvas.width / 2 - 400,
                canvas.height / 2 - 250,
                "240");
    }

    public void hide() {
        cp5.hide();
    }
}
