package h09.room;

import h09.WithSeats;

/**
 * An object of record class {@link SeminarRoom} represents a seminar room.
 *
 * @param name          the name of the seminar room
 * @param numberOfSeats the number of seats in the seminar room
 */
public record SeminarRoom(
    String name,
    int numberOfSeats
) implements Room, WithSeats {
}
