package de.uulm.mi.gdg.utils;

import controlP5.CColor;
import controlP5.ControlFont;
import controlP5.ControlP5;
import de.uulm.mi.gdg.GdGMain;
import processing.core.PFont;

class InterfaceBuilder {
    private ControlFont font;

    InterfaceBuilder() {
        PFont pfont = GdGMain.canvas.loadFont("font/Roboto-Regular-14.vlw"); // use true/false for smooth/no-smooth
        font = new ControlFont(pfont, 14);
    }

    void createButton(ControlP5 cp5, String text, String color, int xPosition, int yPosition, String methodName) {
        cp5.addButton(text)
                .setPosition(xPosition, yPosition)
                .setSize(getWidth(text), 36)
                .setFont(font)
                .setColor(determineColor(color))
                .plugTo(GdGMain.canvas, methodName);
    }

    void createButton(ControlP5 cp5, String text, String color, int xPosition, int yPosition, int width, String methodName) {
        cp5.addButton(text)
                .setPosition(xPosition, yPosition)
                .setSize(width, 36)
                .setFont(font)
                .setColor(determineColor(color))
                .plugTo(GdGMain.canvas, methodName);
    }

    void createSlider(ControlP5 cp5, String text, String color, int xPosition, int yPosition, String methodName) {
        cp5.addSlider(text)
                .setPosition(xPosition, yPosition)
                .setSize(getWidth(text), 36)
                .setFont(font)
                .setColor(determineColor(color))
                .setRange(0, 100)
                .setValue(50)
                .plugTo(GdGMain.canvas, methodName);
    }

    void createToggle(ControlP5 cp5, String text, String color, int xPosition, int yPosition, String methodName) {
        cp5.addToggle(text)
                .setPosition(xPosition, yPosition)
                .setSize(getWidth(text), 36)
                .setFont(font)
                .setColor(determineColor(color))
                .setMode(ControlP5.SWITCH)
                .plugTo(GdGMain.canvas, methodName);
    }

    private int getWidth(String text) {
        int width = 0;
        for (char c : text.toCharArray()) {
            switch (Character.toLowerCase(c)) {
                case 'm':
                case 'w':
                    width += 12;
                    break;
                case ' ':
                    width += 4;
                    break;
                case 'l':
                case 'r':
                case 'b':
                case 'd':
                case 'o':
                    width += 9;
                    break;
                default:
                    width += 8;
                    break;
            }
        }
        return Math.max(width + 32, 64);
    }

    private CColor determineColor(String color) {
        switch (color) {
            case "red":
                return Color.RED;
            case "pink":
                return Color.PINK;
            case "purple":
                return Color.PURPLE;
            case "deep purple":
                return Color.DEEP_PURPLE;
            case "indigo":
                return Color.INDIGO;
            case "blue":
                return Color.BLUE;
            case "light blue":
                return Color.LIGHT_BLUE;
            case "cyan":
                return Color.CYAN;
            case "teal":
                return Color.TEAL;
            case "green":
                return Color.GREEN;
            case "light green":
                return Color.LIGHT_GREEN;
            case "lime":
                return Color.LIME;
            case "yellow":
                return Color.YELLOW;
            case "amber":
                return Color.AMBER;
            case "orange":
                return Color.ORANGE;
            case "deep orange":
                return Color.DEEP_ORANGE;
            default:
                return Color.INDIGO;
        }
    }

    private interface Color {
        CColor RED = new CColor(GdGMain.canvas.color(211, 47, 47),
                GdGMain.canvas.color(244, 67, 54),
                GdGMain.canvas.color(198,40,40),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor PINK = new CColor(GdGMain.canvas.color(194, 24, 91),
                GdGMain.canvas.color(233, 30, 99),
                GdGMain.canvas.color(173,20,87),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor PURPLE = new CColor(GdGMain.canvas.color(123, 31, 162),
                GdGMain.canvas.color(156, 39, 176),
                GdGMain.canvas.color(106,27,154),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor DEEP_PURPLE = new CColor(GdGMain.canvas.color(81, 45, 168),
                GdGMain.canvas.color(103, 58, 183),
                GdGMain.canvas.color(69,39,160),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor INDIGO = new CColor(GdGMain.canvas.color(48, 63, 159),
                GdGMain.canvas.color(63, 81, 181),
                GdGMain.canvas.color(40,53,147),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor BLUE = new CColor(GdGMain.canvas.color(25, 118, 210),
                GdGMain.canvas.color(33, 150, 243),
                GdGMain.canvas.color(21,101,192),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor LIGHT_BLUE = new CColor(GdGMain.canvas.color(2, 136, 209),
                GdGMain.canvas.color(3, 169, 244),
                GdGMain.canvas.color(2,119,189),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor CYAN = new CColor(GdGMain.canvas.color(0, 151, 167),
                GdGMain.canvas.color(0, 188, 212),
                GdGMain.canvas.color(0,131,143),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor TEAL = new CColor(GdGMain.canvas.color(0, 121, 107),
                GdGMain.canvas.color(0, 150, 136),
                GdGMain.canvas.color(0,105,92),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor GREEN = new CColor(GdGMain.canvas.color(56, 142, 60),
                GdGMain.canvas.color(76, 175, 80),
                GdGMain.canvas.color(46,125,50),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor LIGHT_GREEN = new CColor(GdGMain.canvas.color(104, 159, 56),
                GdGMain.canvas.color(139, 195, 74),
                GdGMain.canvas.color(85,139,47),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor LIME = new CColor(GdGMain.canvas.color(175, 180, 43),
                GdGMain.canvas.color(205, 220, 57),
                GdGMain.canvas.color(158,157,36),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor YELLOW = new CColor(GdGMain.canvas.color(251, 192, 45),
                GdGMain.canvas.color(255, 235, 59),
                GdGMain.canvas.color(249,168,37),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor AMBER = new CColor(GdGMain.canvas.color(255, 160, 0),
                GdGMain.canvas.color(255, 193, 7),
                GdGMain.canvas.color(255,143,0),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor ORANGE = new CColor(GdGMain.canvas.color(245, 124, 0),
                GdGMain.canvas.color(255, 152, 0),
                GdGMain.canvas.color(239,108,0),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor DEEP_ORANGE = new CColor(GdGMain.canvas.color(230, 74, 25),
                GdGMain.canvas.color(255, 87, 34),
                GdGMain.canvas.color(216,67,21),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
    }
}
