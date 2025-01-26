package de.tu_darmstadt.patient_monitoring.alert_system;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;

public class AlarmBell implements Indicator {

    private boolean muted;
    private Clip clip;

    public AlarmBell() {
        muted = System.getenv("MUTED") != null;

        if (!muted) {
            try {
                File file = new File("./src/main/assets/beep.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } catch (Exception e) {
                System.err.println("Failed to load audio clip");
                // e.printStackTrace();
            }
        }
    }

    void playSound() {
        if (!muted) {
            clip.start();
            try {
                // clip.start plays the audio asynchronously in the background.
                // Hence, nothing blocks the main thread and the program exists immediately.
                // To avoid this we sleep for a short duration to trigger the audio.
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        } else {
            System.out.println("<BEEP>");
        }
    }

    @Override
    public void update(MedicalDevice device) {
        if(device.isCritical()) {
            playSound();
        }
    }

}
