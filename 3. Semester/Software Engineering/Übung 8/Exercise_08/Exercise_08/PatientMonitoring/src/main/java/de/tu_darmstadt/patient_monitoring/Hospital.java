package de.tu_darmstadt.patient_monitoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import de.tu_darmstadt.patient_monitoring.alert_system.AlarmBell;
import de.tu_darmstadt.patient_monitoring.alert_system.Dashboard;
import de.tu_darmstadt.patient_monitoring.alert_system.Indicator;
import de.tu_darmstadt.patient_monitoring.alert_system.Pager;
import de.tu_darmstadt.patient_monitoring.medical_equipment.CallButton;
import de.tu_darmstadt.patient_monitoring.medical_equipment.HeartRateMonitor;
import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;
import de.tu_darmstadt.patient_monitoring.medical_equipment.OxygenMonitor;

public class Hospital {

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        hospital.initializeMedicalDevices();
        hospital.initializeIndicators();
        hospital.attachIndicatorsToDevices();
        hospital.simulate(5);
    }

    List<HeartRateMonitor> heartRateMonitors = new ArrayList<>();
    List<OxygenMonitor> oxygenMonitors = new ArrayList<>();
    List<CallButton> callButtons = new ArrayList<>();

    List<Indicator> indicators = new ArrayList<>();

    private Hospital() {
    }

    private void initializeMedicalDevices() {
        int floors = 2;
        int roomsPerFloor = 10;

        for (int floor = 1; floor <= floors; floor++) {
            for (int room = 1; room <= roomsPerFloor; room++) {
                generateDeviceForRoom(floor, room);
            }
        }
    }

    private void initializeIndicators() {
        indicators.add(new AlarmBell());
        indicators.add(new Dashboard());
        indicators.add(new Pager("Dr. Grey"));
        indicators.add(new Pager("Dr. House"));
    }

    private void attachIndicatorsToDevices() {
        for (var d : heartRateMonitors){
            for (var i : indicators){
                d.attach(i);
                d.notifyObservers();
            }
        }
        for (var d : oxygenMonitors){
            for (var i : indicators){
                d.attach(i);
                d.notifyObservers();
            }
        }
        for (var d : callButtons){
            for (var i : indicators){
                d.attach(i);
                d.notifyObservers();
            }
        }
    }

    private void generateDeviceForRoom(int floor, int room) {
        String roomName = generateRoomName(floor, room);
        RandomGenerator rng = RandomGeneratorFactory.of("Random").create(17 * floor + 23 * room);

        callButtons.add(new CallButton(roomName));

        if (rng.nextInt(3) == 0) {
            heartRateMonitors.add(new HeartRateMonitor(roomName));
        }

        if (rng.nextInt(5) == 0) {
            oxygenMonitors.add(new OxygenMonitor(roomName));
        }
    }

    private static String generateRoomName(int floor, int room) {
        return String.format("Room %d.%02d", floor, room);
    }

    private void simulate(int steps) {
        for (int i = 0; i < steps; i++) {
            for (HeartRateMonitor e : heartRateMonitors) {
                e.measure();
            }
            for (OxygenMonitor e : oxygenMonitors) {
                e.measure();
            }

            CallButton randomButton = callButtons.get(new Random().nextInt(callButtons.size()));
            randomButton.press();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

}
