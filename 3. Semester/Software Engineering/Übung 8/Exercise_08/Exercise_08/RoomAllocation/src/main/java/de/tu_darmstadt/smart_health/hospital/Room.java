package de.tu_darmstadt.smart_health.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Room {

    private int floor;
    private int roomNumberOnFloor;

    private int numberBeds;
    private List<Patient> patients;

    public Room(int floor, int roomNumberOnFloor, int numberBeds) {
        this.floor = floor;
        this.roomNumberOnFloor = roomNumberOnFloor;
        this.numberBeds = numberBeds;
        this.patients = new ArrayList<>();

    }

    public List<Patient> getPatients() {
        return patients;
    }

    public boolean hasFreeBed() {
        return getNumberOfFreeBeds() > 0;
    }

    public boolean isFull(){
        return getNumberOfFreeBeds() == 0;
    }

    public boolean isEmpty() {
        return patients.isEmpty();
    }

    public int getNumberOfFreeBeds() {
        return numberBeds - patients.size();
    }
    
    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public String toString() {
        return String.format("Room %d.%02d", floor, roomNumberOnFloor);
    }

    public String getInfoText() {
        return String.format("%s [%s]", this.toString(),
                this.patients.stream().map((p) -> p.getName()).collect(Collectors.joining()));
    }

}