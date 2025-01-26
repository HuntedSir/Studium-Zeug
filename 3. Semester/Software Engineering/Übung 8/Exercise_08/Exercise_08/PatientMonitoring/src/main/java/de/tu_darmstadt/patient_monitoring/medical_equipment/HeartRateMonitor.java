package de.tu_darmstadt.patient_monitoring.medical_equipment;

import java.util.Random;

import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;

public class HeartRateMonitor extends MedicalDevice {

    // Heart rate in beats per minute.
    private int heartRate = 60;

    public HeartRateMonitor(String room) {
        super(room);
    }

    public void measure() {
        this.heartRate = new Random().nextInt(140) + 30;
        notifyObservers();
    }

    public int getHeartRate() {
        return this.heartRate;
    }

    @Override
    public boolean isCritical() {
        if (heartRate < 40 || heartRate > 140) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Heart rate monitor in " + getRoom() + " shows " + heartRate + "BPM.";
    }

}
