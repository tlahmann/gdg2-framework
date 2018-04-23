package de.uulm.mi.gdg.utils;

import controlP5.ControlP5;
import de.uulm.mi.gdg.GdGMain;
import processing.core.PApplet;

public class GUI {
    private ControlP5 cp5;

    public GUI() {
        InterfaceBuilder ib = new InterfaceBuilder();

        PApplet canvas = GdGMain.canvas;

        cp5 = new ControlP5(canvas);
        ib.createButton(cp5,
                "Play",
                "light green",
                canvas.width / 2 - 100,
                canvas.height / 2 + 90,
                "playPause");
        ib.createButton(cp5,
                "Zurücksetzen",
                "blue",
                canvas.width / 2 - 100,
                canvas.height / 2 + 130,
                "reset");
        ib.createButton(cp5,
                "RANDOM COLOR",
                "green",
                canvas.width / 2 - 100,
                canvas.height / 2 + 170,
                "randomColor");
        ib.createButton(cp5,
                "export animation",
                "red",
                canvas.width - 175,
                canvas.height - 50,
                "startExporting");
        ib.createButton(cp5,
                "A longer name for a button",
                "amber",
                canvas.width / 2 - 100,
                canvas.height / 2 + 250,
                240,
                "randomColor");
        ib.createSlider(cp5,
                "SomeSlider",
                "orange",
                canvas.width / 2 - 400,
                canvas.height / 2 - 250,
                "240");
        ib.createToggle(cp5,
                "Debug Mode",
                "deep orange",
                canvas.width - 175,
                canvas.height - 120,
                "toggleSomething");
    }

    public void hide() {
        cp5.hide();
    }

    public void show() {
        cp5.show();
    }

    public boolean isVisible() {
        return cp5.isVisible();
    }
}
