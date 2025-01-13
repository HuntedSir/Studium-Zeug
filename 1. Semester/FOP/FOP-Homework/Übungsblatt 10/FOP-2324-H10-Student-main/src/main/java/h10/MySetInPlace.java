package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An in-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetInPlace<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetInPlace(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        //H1.2
        ListItem<T> lastValidItem = null;
        ListItem<T> current = null;
        if(this.head != null){
            current = this.head;
        }

        boolean headChanged = false;
        while(current != null){
            if(pred.test(current.key)){
                if(!headChanged){
                    this.head = current;
                    lastValidItem = this.head;
                    headChanged=true;
                }
                else{
                    lastValidItem.next = current;
                    lastValidItem = lastValidItem.next;
                }
            }
            current = current.next;
        }
        if(headChanged) {
            lastValidItem.next = null;
        }

        return this;
    }

    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {
        //H2.2

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

        ListItem<T> oldLastItem = null;
        ListItem<T> pointerM = null;
        ListItem<T> pointerN = null;

        if(this.head != null) {
            pointerM = this.head;
        }
        if(other.head != null){
            pointerN = other.head;
        }

        while(pointerM != null){
            if(oldLastItem == null){
                oldLastItem = this.head;
            }
            else {
                pointerM = pointerM.next;
                oldLastItem.next = null;
                oldLastItem = pointerM;
            }
        }

        oldLastItem = null;

        while(pointerN != null){
            if(oldLastItem == null){
                oldLastItem = this.head;
            }
            else {
                pointerN = pointerN.next;
                oldLastItem.next = null;
                oldLastItem = pointerN;
            }
        }

        return setCopy;
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {
        //H3.2

        ListItem<T> thisPointer = null;
        ListItem<T> otherPointer = null;
        ListItem<T> resultPointer = null;

        boolean matchFound = false;


        //check whether first element should still be in the list
        if(this.head != null){
            thisPointer = this.head;

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
            thisPointer = thisPointer.next;
            if(matchFound){
                this.head = null;
            }
            else{
                resultPointer = this.head;
            }
        }


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

                if(this.head==null){
                    this.head = thisPointer;
                    resultPointer=this.head;
                }
                else{
                    resultPointer.next = thisPointer;
                    resultPointer = resultPointer.next;
                }
            }

            matchFound=false;

            thisPointer=thisPointer.next;
        }

        return this;
    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        //H4.2

        ListItem<T> thisPointer = null;
        ListItem<T> otherPointer = null;
        ListItem<T> resultPointer = null;
        ListItem<ListItem<T>> headsPointer = heads;


        int numberOfHeads = 0;
        while(headsPointer != null){
            numberOfHeads++;
            headsPointer = headsPointer.next;
        }

        boolean[] matchesFound = new boolean[numberOfHeads];
        boolean allHaveElement = true;
        if(numberOfHeads==0){
            allHaveElement=false;
        }

        int headsIndex;

        //check whether first element should still be in the list
        if(this.head != null){
            thisPointer = this.head;

            headsPointer=heads;
            headsIndex = 0;
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

            thisPointer=thisPointer.next;
            if(allHaveElement){
                    resultPointer=this.head;
            }
            else{
                this.head = null;
            }

            allHaveElement=true;
            if(numberOfHeads==0){
                allHaveElement=false;
            }

            for (int i = 0; i < matchesFound.length; i++) {
                matchesFound[i]=false;
            }

        }


        while(thisPointer != null){

            headsIndex = 0;
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

                if(this.head==null){
                    this.head=thisPointer;
                    resultPointer=this.head;
                }
                else{
                    resultPointer.next = thisPointer;
                    resultPointer=resultPointer.next;
                }
            }

            allHaveElement=true;

            for (int i = 0; i < matchesFound.length; i++) {
                matchesFound[i]=false;
            }

            thisPointer=thisPointer.next;
        }

        return this;
    }
}
