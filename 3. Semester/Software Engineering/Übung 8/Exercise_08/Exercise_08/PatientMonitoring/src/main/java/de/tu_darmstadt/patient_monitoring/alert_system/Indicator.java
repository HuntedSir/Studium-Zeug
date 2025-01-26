package de.tu_darmstadt.patient_monitoring.alert_system;

import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;

public interface Indicator {
    
    public void update(MedicalDevice device);    

}
