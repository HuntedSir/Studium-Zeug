package de.tu_darmstadt.patient_monitoring.medical_equipment;

import java.util.Random;

import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;

public class OxygenMonitor extends MedicalDevice {

    // Blood oxygen saturation in percent.
    private int oxygenSaturation = 100;

    public OxygenMonitor(String room) {
        super(room);
    }

    public void measure() {
        this.oxygenSaturation = 100 - new Random().nextInt(12);
        notifyObservers();
    }

    public int getOxygenSaturation() {
        return this.oxygenSaturation;
    }

    @Override
    public boolean isCritical() {
        if (oxygenSaturation < 90){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Oxygen monitor in " + getRoom() + " shows " + oxygenSaturation + "%.";
    }

}
