package h09.function;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import static h09.H09_TestUtils.assertType;
import static h09.H09_TestUtils.match;
import static h09.H09_TestUtils.matchNested;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class RoomFunctionTest {

    @Test
    public void testNULL_PREDICATE() {
        Field nullPredicate =
            BasicTypeLink.of(RoomFunctions.class).getField(identical("IS_NULL_PREDICATE")).reflection();

        assertType(nullPredicate, matchNested(Predicate.class, match(Object.class)));
    }
}
