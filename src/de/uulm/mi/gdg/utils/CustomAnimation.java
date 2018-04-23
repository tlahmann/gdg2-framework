package de.uulm.mi.gdg.utils;

import de.looksgood.ani.easing.Easing;

/**
 * A class to hold all needed information for the variation of parameters in our animation
 *
 * @author Tobias Lahmann
 * Date: 18.04.2018
 */
public class CustomAnimation implements Comparable<CustomAnimation> {
    private float start;
    private float duration;
    private float value;
    private String params;
    private Easing mode;

    /**
     * Constructor of the CustomAnimation
     *
     * @param start          start cue of the animation in seconds
     * @param duration       the duration of the animation in seconds
     * @param params         what parameter should be changed
     * @param animateToValue what the finish value of the animation should be
     * @param mode           the animation mode as Easing
     */
    CustomAnimation(float start, float duration, String params, float animateToValue, Easing mode) {
        this.start = start;
        this.duration = duration;
        this.params = params;
        this.value = animateToValue;
        this.mode = mode;
    }

    /*
     * Only getters. Because we do not allow changing these parameters after creation.
     */

    /**
     * Starting time of this animation
     *
     * @return the starting time
     */
    public float getStart() {
        return start;
    }

    /**
     * Duration of this animation.
     *
     * @return the duration of this animation
     */
    public float getDuration() {
        return duration;
    }

    /**
     * The final value that will be animated to
     *
     * @return the animate to value
     */
    public float getValue() {
        return value;
    }

    /**
     * The parameter of the object that should be changed
     *
     * @return the parameter that should be changed
     */
    public String getParams() {
        return params;
    }

    /**
     * Easing mode applied during animation
     *
     * @return the easing mode of this animation
     */
    public Easing getMode() {
        return mode;
    }

    /**
     * Compares the starting time of one animation to another. Used for sorting.
     *
     * @param other The other animation
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(CustomAnimation other) {
        return ((this.start - other.start) < 0) ? -1 : 1;
    }

    @Override
    public String toString() {
        return this.params + ": " + this.start + " for " + this.duration + "s --> " + this.value;
    }
}
