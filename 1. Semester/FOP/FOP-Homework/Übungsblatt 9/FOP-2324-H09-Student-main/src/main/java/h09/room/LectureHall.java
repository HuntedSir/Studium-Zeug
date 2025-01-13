package h09.room;

import h09.WithSeats;

/**
 * An object of record class {@link LectureHall} represents a lecture hall.
 *
 * @param name          the name of the lecture hall
 * @param numberOfSeats the number of seats in the lecture hall
 */
public record LectureHall(
    String name,
    int numberOfSeats
) implements Room, WithSeats {
}
