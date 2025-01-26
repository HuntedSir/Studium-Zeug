package de.tu_darmstadt.smart_health.room_allocation;

import java.lang.reflect.Member;
import java.util.List;

import de.tu_darmstadt.smart_health.hospital.Room;
import de.tu_darmstadt.smart_health.hospital.Patient;


public class RoomAllocation {
    
    private List<Room> rooms;
    public AllocationMethod strategy = new BalancedAllocation();

    public RoomAllocation(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void allocatePatient(Patient patient){
        Room room = strategy.allocate(patient, rooms);

        if(room == null){
            System.out.printf("No free bed available for %s\n", patient, room);
            return;
        }

        assignPatientToRoom(patient, room);
        System.out.printf("Assigned %s to %s.\n", patient, room);
    }

    public void setStrategy(AllocationMethod method){
        if(method != null) {
            this.strategy = method;
        }
    }
    private static void assignPatientToRoom(Patient patient, Room room){
        room.addPatient(patient);
        patient.setRoom(room);
    }

    public static void main(String[] args){

        List<Patient> patients = List.of(
            new Patient("Archie May"),
            new Patient("Luke Mitchell"),
            new Patient("Joseph Lloyd"),
            new Patient("George Baxter"),
            new Patient("Riley Thompson"),
            new Patient("Alden Nolan"),
            new Patient("Silas Daugherty"),
            new Patient("Nickolas Kent"),
            new Patient("Jayson Sullivan"),
            new Patient("Kellen Taylor")
        );        

        List<Room> rooms = List.of(
            new Room(1, 1, 2),
            new Room(1, 2, 2),
            new Room(1, 3, 2),
            new Room(2, 1, 2),
            new Room(2, 2, 2),
            new Room(2, 3, 2)
        );

        RoomAllocation roomAllocation = new RoomAllocation(rooms);

        for(Patient p : patients){
            roomAllocation.allocatePatient(p);
        }
    }

}



