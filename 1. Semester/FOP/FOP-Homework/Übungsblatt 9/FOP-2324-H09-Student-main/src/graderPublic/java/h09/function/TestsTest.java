package h09.function;

import h09.TUDa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.exceptions.base.MockitoException;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mockStatic;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertTrue;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.emptyContext;

@TestForSubmission
public class TestsTest {

    private final List<MockedStatic.Verification> intVerifications = List.of(
        () -> Assertions.assertEquals(anyInt(), anyInt()),
        () -> Assertions.assertEquals(anyInt(), any(Integer.class)),
        () -> Assertions.assertEquals(any(Integer.class), anyInt()),
        () -> Assertions.assertEquals(any(Integer.class), any(Integer.class)),
        () -> Assertions.assertEquals(anyInt(), anyInt(), anyString()),
        () -> Assertions.assertEquals(anyInt(), any(Integer.class), anyString()),
        () -> Assertions.assertEquals(any(Integer.class), anyInt(), anyString()),
        () -> Assertions.assertEquals(any(Integer.class), any(Integer.class), anyString()),
        () -> Assertions.assertEquals(anyInt(), anyInt(), any(Supplier.class)),
        () -> Assertions.assertEquals(anyInt(), any(Integer.class), any(Supplier.class)),
        () -> Assertions.assertEquals(any(Integer.class), anyInt(), any(Supplier.class)),
        () -> Assertions.assertEquals(any(Integer.class), any(Integer.class), any(Supplier.class))
    );

    private final List<MockedStatic.Verification> stringVerifications = List.of(
        () -> Assertions.assertEquals(anyString(), anyString()),
        () -> Assertions.assertEquals(anyString(), anyString(), anyString()),
        () -> Assertions.assertEquals(anyString(), anyString(), any(Supplier.class))
    );

    @SuppressWarnings("unchecked")
    @Test
    public void testTestFilter() {
        try (
            MockedStatic<Assertions> assertionsMock = mockStatic(Assertions.class, CALLS_REAL_METHODS);
            MockedStatic<StackOfObjectsOperations> stackOperationsMock = mockStatic(
                StackOfObjectsOperations.class,
                CALLS_REAL_METHODS
            )
        ) {
            new Tests().testFilter();

            stackOperationsMock.verify(() -> StackOfObjectsOperations.filter(any(), any()), atLeastOnce());

            assertInvocation(assertionsMock, intVerifications, 1);
            assertInvocation(assertionsMock, stringVerifications, 5);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTestMap() {
        try (
            MockedStatic<Assertions> assertionsMock = mockStatic(Assertions.class, CALLS_REAL_METHODS);
            MockedStatic<StackOfObjectsOperations> stackOperationsMock = mockStatic(
                StackOfObjectsOperations.class,
                CALLS_REAL_METHODS
            )
        ) {
            new Tests().testMap();

            stackOperationsMock.verify(() -> StackOfObjectsOperations.map(any(), any()), atLeastOnce());
            assertionsMock.verify(() -> Assertions.assertEquals(anyInt(), anyInt()), atLeastOnce());

            assertInvocation(assertionsMock, intVerifications, TUDa.stackOfSeminarRooms().numberOfObjects());
        }
    }

    private void assertInvocation(MockedStatic<?> mock, Collection<MockedStatic.Verification> verifications, int times) {
        boolean hasCorrectCall = false;
        for (MockedStatic.Verification verification : verifications) {
            try {
                mock.verify(verification, atLeast(times));
                hasCorrectCall = true;
                break;
            } catch (AssertionError | MockitoException ignored) {

            }
        }
        assertTrue(hasCorrectCall, emptyContext(), r -> "The returned values of Map are not correctly checked.");
    }
}
