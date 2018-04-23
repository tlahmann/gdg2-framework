package de.uulm.mi.gdg.utils;

import controlP5.CColor;
import de.uulm.mi.gdg.GdGMain;

public class GdGConstants {
    public enum AnimationStates {
        READY, RUNNING, PAUSED
    }

    public enum ExportStates {
        EXPORTING, NOT_EXPORTING
    }

    public enum DevelopmentStates {
        DEBUG, DEPLOY
    }

    public interface Color {
        CColor RED = new CColor(GdGMain.canvas.color(211, 47, 47),
                GdGMain.canvas.color(244, 67, 54),
                GdGMain.canvas.color(198, 40, 40),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor PINK = new CColor(GdGMain.canvas.color(194, 24, 91),
                GdGMain.canvas.color(233, 30, 99),
                GdGMain.canvas.color(173, 20, 87),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor PURPLE = new CColor(GdGMain.canvas.color(123, 31, 162),
                GdGMain.canvas.color(156, 39, 176),
                GdGMain.canvas.color(106, 27, 154),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor DEEP_PURPLE = new CColor(GdGMain.canvas.color(81, 45, 168),
                GdGMain.canvas.color(103, 58, 183),
                GdGMain.canvas.color(69, 39, 160),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor INDIGO = new CColor(GdGMain.canvas.color(48, 63, 159),
                GdGMain.canvas.color(63, 81, 181),
                GdGMain.canvas.color(40, 53, 147),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor BLUE = new CColor(GdGMain.canvas.color(25, 118, 210),
                GdGMain.canvas.color(33, 150, 243),
                GdGMain.canvas.color(21, 101, 192),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor LIGHT_BLUE = new CColor(GdGMain.canvas.color(2, 136, 209),
                GdGMain.canvas.color(3, 169, 244),
                GdGMain.canvas.color(2, 119, 189),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor CYAN = new CColor(GdGMain.canvas.color(0, 151, 167),
                GdGMain.canvas.color(0, 188, 212),
                GdGMain.canvas.color(0, 131, 143),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor TEAL = new CColor(GdGMain.canvas.color(0, 121, 107),
                GdGMain.canvas.color(0, 150, 136),
                GdGMain.canvas.color(0, 105, 92),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor GREEN = new CColor(GdGMain.canvas.color(56, 142, 60),
                GdGMain.canvas.color(76, 175, 80),
                GdGMain.canvas.color(46, 125, 50),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor LIGHT_GREEN = new CColor(GdGMain.canvas.color(104, 159, 56),
                GdGMain.canvas.color(139, 195, 74),
                GdGMain.canvas.color(85, 139, 47),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor LIME = new CColor(GdGMain.canvas.color(175, 180, 43),
                GdGMain.canvas.color(205, 220, 57),
                GdGMain.canvas.color(158, 157, 36),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor YELLOW = new CColor(GdGMain.canvas.color(251, 192, 45),
                GdGMain.canvas.color(255, 235, 59),
                GdGMain.canvas.color(249, 168, 37),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor AMBER = new CColor(GdGMain.canvas.color(255, 160, 0),
                GdGMain.canvas.color(255, 193, 7),
                GdGMain.canvas.color(255, 143, 0),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor ORANGE = new CColor(GdGMain.canvas.color(245, 124, 0),
                GdGMain.canvas.color(255, 152, 0),
                GdGMain.canvas.color(239, 108, 0),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor DEEP_ORANGE = new CColor(GdGMain.canvas.color(230, 74, 25),
                GdGMain.canvas.color(255, 87, 34),
                GdGMain.canvas.color(216, 67, 21),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor BROWN = new CColor(GdGMain.canvas.color(93, 64, 55),
                GdGMain.canvas.color(121, 85, 72),
                GdGMain.canvas.color(78, 52, 46),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
        CColor GREY = new CColor(GdGMain.canvas.color(97, 97, 97),
                GdGMain.canvas.color(158, 158, 158),
                GdGMain.canvas.color(66, 66, 66),
                GdGMain.canvas.color(0, 0, 0),
                GdGMain.canvas.color(255, 255, 255));
        CColor BLUE_GREY = new CColor(GdGMain.canvas.color(69, 90, 100),
                GdGMain.canvas.color(96, 125, 139),
                GdGMain.canvas.color(55, 71, 79),
                GdGMain.canvas.color(255, 255, 255),
                GdGMain.canvas.color(255, 255, 255));
    }
}
