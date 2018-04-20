package de.uulm.mi.gdg.utils;

import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import de.looksgood.ani.AniConstants;
import de.looksgood.ani.easing.Easing;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Abstract class to import a list of animations, provided in a json file to an arrayList of custom animations
 *
 * @author Tobias Lahmann
 * Date: 18.04.2018
 * @see de.uulm.mi.gdg.utils.CustomAnimation
 */
public abstract class AniImporter {
    /**
     * Method to import a list of animatios from a JSON file and convert it to an ArrayList of Custom Animations
     *
     * @param filePath the file path for the animation
     * @param param    the parameter that these animations shall be applied to
     * @return a list of custom animations to process
     * @see de.uulm.mi.gdg.utils.CustomAnimation
     */
    public static ArrayList<CustomAnimation> importAnimation(PApplet importer, String filePath, String param) {
        JSONObject file = importer.loadJSONObject(filePath);
        JSONArray backgroundAnimations = file.getJSONArray(param);

        ArrayList<CustomAnimation> anis = new ArrayList<>();
        for (int i = 0; i < backgroundAnimations.size(); i++) {
            JSONObject o = backgroundAnimations.getJSONObject(i);
            float start = o.getFloat("start");
            float duration = o.getFloat("duration");
            int value = o.getInt("value");
            String easingString = o.getString("easing");
            Easing easing = determineEasing(easingString);

            anis.add(new CustomAnimation(start, duration, param, value, easing));
        }

        return anis;
    }

    /**
     * Method to chose an easing method based on the string provided
     * This method will take an easing method as string and 'convert' it to the corresponding
     * ANI easing method
     *
     * @param ease the easing as string
     * @return Ani easing method
     * @see de.looksgood.ani.easing.Easing
     */
    private static Easing determineEasing(String ease) {
        Easing e;
        switch (ease) {
            case "quad_in":
                e = AniConstants.QUAD_IN;
                break;
            case "quad_out":
                e = AniConstants.QUAD_OUT;
                break;
            case "quad_in_out":
                e = AniConstants.QUAD_IN_OUT;
                break;
            case "cubic_in":
                e = AniConstants.CUBIC_IN;
                break;
            case "cubic_out":
                e = AniConstants.CUBIC_OUT;
                break;
            case "cubic_in_out":
                e = AniConstants.CUBIC_IN_OUT;
                break;
            case "quart_in":
                e = AniConstants.QUART_IN;
                break;
            case "quart_out":
                e = AniConstants.QUART_OUT;
                break;
            case "quart_in_out":
                e = AniConstants.QUART_IN_OUT;
                break;
            case "quint_in":
                e = AniConstants.QUINT_IN;
                break;
            case "quint_out":
                e = AniConstants.QUINT_OUT;
                break;
            case "quint_in_out":
                e = AniConstants.QUINT_IN_OUT;
                break;
            case "sine_in":
                e = AniConstants.SINE_IN;
                break;
            case "sine_out":
                e = AniConstants.SINE_OUT;
                break;
            case "sine_in_out":
                e = AniConstants.SINE_IN_OUT;
                break;
            case "circ_in":
                e = AniConstants.CIRC_IN;
                break;
            case "circ_out":
                e = AniConstants.CIRC_OUT;
                break;
            case "circ_in_out":
                e = AniConstants.CIRC_IN_OUT;
                break;
            case "expo_in":
                e = AniConstants.EXPO_IN;
                break;
            case "expo_out":
                e = AniConstants.EXPO_OUT;
                break;
            case "expo_in_out":
                e = AniConstants.EXPO_IN_OUT;
                break;
            case "back_in":
                e = AniConstants.BACK_IN;
                break;
            case "back_out":
                e = AniConstants.BACK_OUT;
                break;
            case "back_in_out":
                e = AniConstants.BACK_IN_OUT;
                break;
            case "bounce_in":
                e = AniConstants.BOUNCE_IN;
                break;
            case "bounce_out":
                e = AniConstants.BOUNCE_OUT;
                break;
            case "bounce_in_out":
                e = AniConstants.BOUNCE_IN_OUT;
                break;
            case "elastic_in":
                e = AniConstants.ELASTIC_IN;
                break;
            case "elastic_out":
                e = AniConstants.ELASTIC_OUT;
                break;
            case "elastic_in_out":
                e = AniConstants.ELASTIC_IN_OUT;
                break;
            default:
                e = AniConstants.LINEAR;
                break;
        }
        return e;
    }

    /**
     * Minim based audio FFT to data text file conversion.
     * Non real-time, so you don't wait 5 minutes for a 5 minute song :)
     * You can look at the produced txt file in the data folder
     * after running this program to see how it looks like.
     *
     * @param importer
     * @param fileName
     * @param track
     * @param seperator
     */
    public static void audioToTextFile(PApplet importer, String fileName, AudioSample track, String seperator) {
        PrintWriter output = importer.createWriter(importer.dataPath(fileName + ".txt"));

        int fftSize = 1024;
        float sampleRate = track.sampleRate();

        float[] fftSamplesL = new float[fftSize];
        float[] fftSamplesR = new float[fftSize];

        float[] samplesL = track.getChannel(AudioSample.LEFT);
        float[] samplesR = track.getChannel(AudioSample.RIGHT);

        FFT fftL = new FFT(fftSize, sampleRate);
        FFT fftR = new FFT(fftSize, sampleRate);

        fftL.logAverages(22, 3);
        fftR.logAverages(22, 3);

        int totalChunks = (samplesL.length / fftSize) + 1;
        int fftSlices = fftL.avgSize();

        for (int ci = 0; ci < totalChunks; ++ci) {
            int chunkStartIndex = ci * fftSize;
            int chunkSize = PApplet.min(samplesL.length - chunkStartIndex, fftSize);

            System.arraycopy(samplesL, chunkStartIndex, fftSamplesL, 0, chunkSize);
            System.arraycopy(samplesR, chunkStartIndex, fftSamplesR, 0, chunkSize);
            if (chunkSize < fftSize) {
                java.util.Arrays.fill(fftSamplesL, chunkSize, fftSamplesL.length - 1, 0.0f);
                java.util.Arrays.fill(fftSamplesR, chunkSize, fftSamplesR.length - 1, 0.0f);
            }

            fftL.forward(fftSamplesL);
            fftR.forward(fftSamplesL);

            // The format of the saved txt file.
            // The file contains many rows. Each row looks like this:
            // T|L|R|L|R|L|R|... etc
            // where T is the time in seconds
            // Then we alternate left and right channel FFT values
            // The first L and R values in each row are low frequencies (bass)
            // and they go towards high frequency as we advance towards
            // the end of the line.
            StringBuilder msg = new StringBuilder(PApplet.nf(chunkStartIndex / sampleRate, 0, 3).replace(',', '.'));
            for (int i = 0; i < fftSlices; ++i) {
                msg.append(seperator + PApplet.nf(fftL.getAvg(i), 0, 4).replace(',', '.'));
                msg.append(seperator + PApplet.nf(fftR.getAvg(i), 0, 4).replace(',', '.'));
            }
            output.println(msg.toString());
        }
        track.close();
        output.flush();
        output.close();
        System.out.println("Sound analysis done");
    }
}
