package de.tu_darmstadt.smart_health.room_allocation;

import java.util.List;

import de.tu_darmstadt.smart_health.hospital.Patient;
import de.tu_darmstadt.smart_health.hospital.Room;

public interface AllocationMethod {
    public Room allocate(Patient p, List<Room> rooms);
}
