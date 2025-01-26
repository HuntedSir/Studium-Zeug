package de.tu_darmstadt.smart_health.hospital;

public class Patient {    

    private String name;

    /// The room, in which the patient is currently stationed.
    /// null, if no room is assigned.
    private Room room;

    public Patient(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public Room getRoom(){
        return this.room;
    }

    public String toString(){
        return name;
    }

    public String getInfoText(){
        String roomInfo = "no room";
        if(this.room != null){
            roomInfo = room.toString();
        }
        return String.format("%s [%s]", this.toString(), roomInfo);
    }

}