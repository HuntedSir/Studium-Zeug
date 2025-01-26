package de.tu_darmstadt.smart_health.room_allocation;

import de.tu_darmstadt.smart_health.hospital.Patient;
import de.tu_darmstadt.smart_health.hospital.Room;

import java.util.List;

public class QuarantineMethod implements AllocationMethod{
    @Override
    public Room allocate(Patient p, List<Room> rooms) {
        if(rooms != null && !rooms.isEmpty()) {
            for (var r : rooms) {
                if (r.getNumberOfFreeBeds() - r.getPatients().size() == 0) {
                    r.addPatient(p);
                    return r;
                }
            }
        }
        return null;
    }
}
