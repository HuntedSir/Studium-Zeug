package h09.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tudalgo.algoutils.student.Student.crash;

import h09.TUDa;
import h09.room.LectureHall;
import h09.room.SeminarRoom;
import h09.stack.StackOfObjects;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Predicate;

public class Tests {

    @Test
    public void testFilter() {
        StackOfObjects stack = TUDa.stackOfLectureHalls();

    }

    @Test
    public void testMap() {
        StackOfObjects<SeminarRoom> stack = TUDa.stackOfSeminarRooms();
        Function<SeminarRoom, Integer> function = new Function<SeminarRoom, Integer>() {
            @Override
            public Integer apply(SeminarRoom seminarRoom) {
                Integer number = seminarRoom.numberOfSeats();
                return number;
            }
        };
        StackOfObjects<Integer> result = StackOfObjectsOperations.map(stack, function);
        StackOfObjects<SeminarRoom> stackTest = TUDa.stackOfSeminarRooms();
        assertEquals(result.numberOfObjects(), stackTest.numberOfObjects());

        for (int i = 0; i < result.numberOfObjects(); i++) {
            Integer number = result.pop();
            SeminarRoom room = stackTest.pop();
            assertEquals(number, room.numberOfSeats());
        }
    }
}
