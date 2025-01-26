package de.tu_darmstadt.patient_monitoring.alert_system;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import de.tu_darmstadt.patient_monitoring.medical_equipment.CallButton;
import de.tu_darmstadt.patient_monitoring.medical_equipment.HeartRateMonitor;
import de.tu_darmstadt.patient_monitoring.medical_equipment.MedicalDevice;
import de.tu_darmstadt.patient_monitoring.medical_equipment.OxygenMonitor;

public class Dashboard implements Indicator {

    private class RoomDataEntry {
        String room;

        boolean calling;

        // null, if no value for this room available
        Integer oxygenLevel;

        // null, if no value for this room available
        Integer heartRate;
    }

    private List<RoomDataEntry> roomData = new ArrayList<>();
    private AbstractTableModel tableDataModel;

    public Dashboard() {
        JFrame frame = new JFrame("Hospital Dashboard");

        tableDataModel = new AbstractTableModel() {
            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public int getRowCount() {
                return roomData.size();
            }

            @Override
            public Object getValueAt(int row, int column) {
                RoomDataEntry entry = roomData.get(row);
                switch (column) {
                    case 0:
                        return entry.room;
                    case 1:
                        if (entry.heartRate == null)
                            return "";
                        return String.format("%d BPM", entry.heartRate);
                    case 2:
                        if (entry.oxygenLevel == null)
                            return "";
                        return String.format("%d", entry.oxygenLevel) + "%";
                    case 3:
                        return entry.calling ? "CALLING" : "";
                    default:
                        throw new RuntimeException("Invalid column requested!");
                }
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "Room";
                    case 1:
                        return "Heart Rate";
                    case 2:
                        return "Oxygen Level";
                    case 3:
                        return "Calling?";
                    default:
                        throw new RuntimeException("Invalid column requested!");
                }
            }
        };
        JTable table = new JTable(tableDataModel);
        table.setAutoCreateRowSorter(true);
        table.getRowSorter().toggleSortOrder(0);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private RoomDataEntry findRoomData(String room) {
        for (RoomDataEntry e : roomData) {
            if (e.room.equals(room)) {
                return e;
            }
        }
        return null;
    }

    private RoomDataEntry createRoomDataEntry(String room) {
        RoomDataEntry entry = new RoomDataEntry();
        entry.room = room;
        entry.heartRate = null;
        entry.oxygenLevel = null;
        entry.calling = false;

        roomData.add(entry);
        return entry;
    }

    public void update(MedicalDevice device) {
        String room = device.getRoom();
        RoomDataEntry dataEntry = findRoomData(room);
        if (dataEntry == null) {
            dataEntry = createRoomDataEntry(room);
        }

        if (device instanceof HeartRateMonitor) {
            dataEntry.heartRate = ((HeartRateMonitor) device).getHeartRate();
        }

        if (device instanceof OxygenMonitor) {
            dataEntry.oxygenLevel = ((OxygenMonitor) device).getOxygenSaturation();
        }

        if (device instanceof CallButton) {
            dataEntry.calling = ((CallButton) device).isPressed();
        }

        tableDataModel.fireTableDataChanged();
    }

}