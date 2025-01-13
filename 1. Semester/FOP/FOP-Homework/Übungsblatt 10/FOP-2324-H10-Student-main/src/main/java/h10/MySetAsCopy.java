package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An out-of-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetAsCopy<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetAsCopy(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        //H1.1
        MySetAsCopy<T> setCopy = new MySetAsCopy<>(null, this.cmp);

        ListItem<T> lastValidItem = null;
        ListItem<T> current = null;
        if(this.head != null){
            current = this.head;
        }

        while(current != null){
            if(pred.test(current.key)){
                if(setCopy.head == null){
                    setCopy.head = new ListItem<>(current.key);
                    lastValidItem = setCopy.head;
                }
                else{
                    lastValidItem.next = new ListItem<>(current.key);
                    lastValidItem = lastValidItem.next;
                }
            }
            current = current.next;
        }
        //lastValidItem.next = null;

        return setCopy;
    }

    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        //H2.1

        Comparator<ListItem<T>> listItemComparator = new Comparator<ListItem<T>>() {
            @Override
            public int compare(ListItem<T> o1, ListItem<T> o2) {
                int result = cmp.compare(o1.key, o2.key);

                if(result <= 0){
                    if(result == 0){
                        int newResult = cmp.compare(o1.next.key, o2.next.key);
                        if(newResult < 0){
                            return -1;
                        }
                        if(newResult>0){
                            return 1;
                        }
                        return 0;
                    }
                    return -1;
                }
                else {
                    return 1;
                }
            }
        };

        MySetAsCopy<ListItem<T>> setCopy = new MySetAsCopy<>(null, listItemComparator);

        ListItem<T> thisPointer = null;
        ListItem<T> otherPointer = null;

        ListItem<ListItem<T>> lastItem = null;

        if(this.head != null) {
            thisPointer = this.head;
        }

        while(thisPointer != null)
        {
            if(other.head != null) {
                otherPointer = other.head;
            }

            while(otherPointer != null){
                ListItem<ListItem<T>> listItem = new ListItem<>();
                listItem.key=new ListItem<>(thisPointer.key);
                listItem.key.next = new ListItem<>(otherPointer.key);

                if(setCopy.head == null){
                    setCopy.head = listItem;
                    lastItem = setCopy.head;
                }
                else{
                    lastItem.next = listItem;
                    lastItem = lastItem.next;
                }

                otherPointer = otherPointer.next;
            }

            thisPointer = thisPointer.next;
        }

        return setCopy;
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {
        //H3.1
        MySetAsCopy<T> result = new MySetAsCopy<>(null, this.cmp);

        ListItem<T> thisPointer = null;
        ListItem<T> otherPointer = null;
        ListItem<T> resultPointer = null;

        if(this.head != null){
            thisPointer = this.head;
        }


        boolean matchFound = false;

        while(thisPointer != null){
            if(other.head != null){
                otherPointer = other.head;
            }
            while(otherPointer!= null){
                if(this.cmp.compare(thisPointer.key, otherPointer.key)==0){
                    matchFound=true;
                    break;
                }
                otherPointer=otherPointer.next;
            }

            if(!matchFound){
                ListItem<T> itemCopy = new ListItem<>(thisPointer.key);

                if(result.head==null){
                    result.head=itemCopy;
                    resultPointer=result.head;
                }
                else{
                    resultPointer.next = itemCopy;
                    resultPointer=resultPointer.next;
                }
            }

            matchFound=false;

            thisPointer=thisPointer.next;
        }

        return result;
    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        //H4.1
        MySetAsCopy<T> result = new MySetAsCopy<>(null, this.cmp);

        ListItem<T> thisPointer = null;
        ListItem<T> otherPointer = null;
        ListItem<T> resultPointer = null;
        ListItem<ListItem<T>> headsPointer = heads;


        if(this.head != null){
            thisPointer = this.head;
        }

        int numberOfHeads = 0;
        while(headsPointer != null){
            numberOfHeads++;
            headsPointer = headsPointer.next;
        }
        headsPointer = heads;

        boolean[] matchesFound = new boolean[numberOfHeads];
        boolean allHaveElement = true;
        if(numberOfHeads==0){
            allHaveElement=false;
        }

        int headsIndex;

        while(thisPointer != null){
            headsIndex = 0;
            headsPointer=heads;
            while(headsPointer != null) {
                otherPointer = headsPointer.key;

                while (otherPointer != null) {
                    if (this.cmp.compare(thisPointer.key, otherPointer.key) == 0) {
                        matchesFound[headsIndex] = true;
                        break;
                    }
                    otherPointer = otherPointer.next;
                }

                headsIndex++;
                headsPointer=headsPointer.next;
            }

            for (int i = 0; i < matchesFound.length; i++) {
                if(matchesFound[i]==false){
                    allHaveElement = false;
                    break;
                }
            }

            if(allHaveElement){
                ListItem<T> itemCopy = new ListItem<>(thisPointer.key);

                if(result.head==null){
                    result.head=itemCopy;
                    resultPointer=result.head;
                }
                else{
                    resultPointer.next = itemCopy;
                    resultPointer=resultPointer.next;
                }
            }

            allHaveElement=true;
            if(numberOfHeads==0){
                allHaveElement=false;
            }

            for (int i = 0; i < matchesFound.length; i++) {
                matchesFound[i]=false;
            }

            thisPointer=thisPointer.next;
        }

        return result;
    }
}
