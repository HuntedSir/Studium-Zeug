package de.tu_darmstadt.patient_monitoring.alert_system;

import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;


public class Pager implements Indicator{
    public String doctorName;

    public Pager(String name) {
        this.doctorName = name;
    }

    @Override
    public void update(MedicalDevice device){
        if(device.isCritical()) {
            System.out.println(doctorName + " is called to Room " + device.getRoom());
        }
    }
}
