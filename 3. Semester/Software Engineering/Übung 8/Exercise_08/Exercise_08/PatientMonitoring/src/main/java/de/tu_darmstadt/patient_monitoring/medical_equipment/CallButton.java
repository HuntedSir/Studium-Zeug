package de.tu_darmstadt.patient_monitoring.medical_equipment;

import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;

public class CallButton extends MedicalDevice {

    private boolean pressed;

    public CallButton(String room) {
        super(room);
        this.pressed = false;
    }

    public void press() {
        this.pressed = true;
        notifyObservers();
    }

    public void reset() {
        this.pressed = false;
        notifyObservers();
    }

    public boolean isPressed() {
        return pressed;
    }

    @Override
    public boolean isCritical() {
        if(pressed == true){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Call button in " + getRoom();
    }

}
