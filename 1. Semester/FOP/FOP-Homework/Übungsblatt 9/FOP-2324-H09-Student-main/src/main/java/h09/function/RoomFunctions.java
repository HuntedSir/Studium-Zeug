package h09.function;

import h09.WithSeats;
import h09.room.Room;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A collection of function usable for rooms.
 */
@SuppressWarnings("Convert2MethodRef")
public class RoomFunctions {

    /**
     * A predicate that returns {@code true} iff the given object is {@code null}.
     */
    public static Predicate<Object> IS_NULL_PREDICATE = object -> object == null;

    /**
     * Returns a predicate that returns {@code true} iff the name of the given room starts with the given location prefix.
     *
     * @param locationPrefix the location prefix
     * @return the predicate
     */
    public static <X> Predicate<X> isInArea(char locationPrefix) {
        return room -> {
            Room a = (Room) room;
            return a.name().charAt(0) == locationPrefix;
        };
    }

    /**
     * Returns a predicate that returns {@code true} iff the name of the given room starts with the given location prefix and
     * the number of seats of the room is equal to or higher than the given number of seats.
     *
     * @param locationPrefix the location prefix
     * @param numberOfSeats  the number of seats
     * @return the predicate
     */
    public static <X> Predicate<X> isInAreaAndHasMinimumNumberOfSeats(char locationPrefix, int numberOfSeats) {
        Predicate<X> isInArea = isInArea(locationPrefix);
        Predicate<X> hasMinimumNumberOfSeats = room -> {
            WithSeats a = (WithSeats) room;
            return a.numberOfSeats() >= numberOfSeats;
        };
        return isInArea.and(hasMinimumNumberOfSeats);
    }

    /**
     * Returns a function that casts the given object to an object of the given type or
     * {@code null} if the given object is not of the given type.
     *
     * @param type the type
     * @return the function
     */
    @SuppressWarnings("unchecked")
    public static <X> Function<Room, X> toRoomTypeOrNull(Class<X> type) {
        return room -> {
            X a = (X) room;
            return type.isInstance(room) ? a : null;
        };
    }

    private RoomFunctions() {
    }
}
