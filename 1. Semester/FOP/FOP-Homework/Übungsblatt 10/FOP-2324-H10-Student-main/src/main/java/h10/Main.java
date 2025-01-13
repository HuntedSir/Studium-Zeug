package h10;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        ListItem<Integer> testHead = new ListItem<>(1);
        ListItem<Integer> secondTest = new ListItem<>(0);
        ListItem<Integer> thirdTest = new ListItem<>(1);
        ListItem<Integer> fourthTest = new ListItem<>(2);

        ListItem<Integer> testHeadPointer = testHead;
        ListItem<Integer> secondTestPointer = secondTest;

        Comparator<Integer> listItemComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };


        testHeadPointer.next = new ListItem<>(2);
        testHeadPointer=testHeadPointer.next;
        testHeadPointer.next = new ListItem<>(3);

        secondTestPointer.next = new ListItem<>(2);
        secondTestPointer=secondTestPointer.next;
        secondTestPointer.next = new ListItem<>(5);
        secondTestPointer=secondTestPointer.next;

        testHeadPointer = thirdTest;
        testHeadPointer.next = new ListItem<>(2);
        testHeadPointer=testHeadPointer.next;
        testHeadPointer.next = new ListItem<>(3);
        testHeadPointer=testHeadPointer.next;

        testHeadPointer = fourthTest;
        testHeadPointer.next = new ListItem<>(4);
        testHeadPointer=testHeadPointer.next;
        testHeadPointer.next = new ListItem<>(5);
        testHeadPointer=testHeadPointer.next;

        ListItem<ListItem<Integer>> lists = new ListItem<>(testHead);
        lists.next = new ListItem<>(secondTest);
        lists.next.next = new ListItem<>(thirdTest);
        lists.next.next.next = new ListItem<>(fourthTest);

        MySetAsCopy<Integer> testListAsCopy = new MySetAsCopy<>(testHead, listItemComparator);

        MySetAsCopy<Integer> comparisonList = new MySetAsCopy<>(secondTest, listItemComparator);


        var result = testListAsCopy.intersectionListItems(new ListItem<>(secondTest));

        System.out.println("done");
    }
}
