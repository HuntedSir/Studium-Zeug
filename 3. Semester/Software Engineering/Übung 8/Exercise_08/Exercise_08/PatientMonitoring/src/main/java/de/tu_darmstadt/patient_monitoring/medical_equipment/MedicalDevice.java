package de.tu_darmstadt.patient_monitoring.medical_equipment;

import java.util.concurrent.CopyOnWriteArrayList;

import de.tu_darmstadt.patient_monitoring.alert_system.Indicator;

public abstract class MedicalDevice {
    
    // The room where this device is deployed.
    private String room;

    private CopyOnWriteArrayList<Indicator> observers;

    public MedicalDevice(String room) {
        this.room = room;
        this.observers = new CopyOnWriteArrayList<>();
    }

    public String getRoom() {
        return this.room;
    }

    public void attach(Indicator indicator) {
        if (indicator == null){
            throw new IllegalArgumentException();
        }
        else {
            observers.addIfAbsent(indicator);
        }
    }

    public void detach(Indicator indicator) {
        if (indicator == null){
            throw new IllegalArgumentException();
        }
        else {
            observers.remove(indicator);
        }
    }

    public void notifyObservers() {
        for (var indicator : observers){
            indicator.update(this);
        }
    }

    public abstract boolean isCritical();

}
