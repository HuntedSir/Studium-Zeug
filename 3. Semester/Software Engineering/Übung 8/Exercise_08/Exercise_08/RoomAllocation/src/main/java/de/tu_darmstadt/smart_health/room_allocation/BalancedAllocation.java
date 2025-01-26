package de.tu_darmstadt.smart_health.room_allocation;

import de.tu_darmstadt.smart_health.hospital.Patient;
import de.tu_darmstadt.smart_health.hospital.Room;

import java.util.List;

public class BalancedAllocation implements AllocationMethod{
    @Override
    public Room allocate(Patient p, List<Room> rooms) {
        if(rooms != null && !rooms.isEmpty()) {
            var result = rooms.get(1);
            for (var r : rooms){
                if(r.getNumberOfFreeBeds()-r.getPatients().size() > result.getNumberOfFreeBeds() - result.getPatients().size()){
                    result = r;
                }
            }
            return result;
        }
        return null;
    }
}
