package java.mystack;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class MyVector<E>
    extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    protected Object[] elementData;
    protected int elementCount;
    protected int capacityIncrement;

    public MyVector(int initialCapacity, int capacityIncrement) {
        super(); //AbstractList
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " +
                initialCapacity);
        }
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    public MyVector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    public MyVector() {
        this(10);
    }

    /**
     * E 하위의 Collection을 원소로 받아 그 크기만큼의 배열로 만든다.
     * Collection이 ArrayList 클래스일 경우 toArray된 그대로 elementData에  할당한다.
     * 그외인 경우 Arrays.copyOf를 통해 복사한 배열을 할당한다.
     * @param collection the collection whose elements are to be placed into this vector
     * @throws NullPointerException if the specified collection is null
     */
    public MyVector(Collection<? extends E> collection) {
        Object[] result = collection.toArray();
        elementCount = result.length;
        if (collection.getClass() == ArrayList.class) {
            elementData = result;
        } else {
            elementData = Arrays.copyOf(result, elementCount, Object[].class);
        }
    }

    /**
     * anArray의 마지막 주소의 다음부터 시작하는 배열로 복사한다.
     * @param anArray the array into which the component get copied
     */
    public synchronized void copyInto(Object[] anArray) {
        System.arraycopy(elementData, 0, anArray, 0, elementCount);
    }

    /**
     * Vector의 capacity가 size 보다 크다면 size만큼으로 줄인다.
     * modCount는 이 Vector가 몇 번이나 구조적으로 변경되었는지 기록하는 변수이다.
     */
    public synchronized void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
        if (elementCount < oldCapacity) {
            elementData = Arrays.copyOf(elementData, elementCount);
        }
    }

    /**
     * 현재 capacity가 mibCapacity 보다 작다면 minCapacity만큼 grow 한다.
     * @param minCapacity the desired minimum capacity
     */
    public synchronized void ensureCapacity(int minCapacity) {
        if (minCapacity > 0) {
            modCount++;
            if (minCapacity > elementData.length) {
                grow(minCapacity);
            }
        }
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData,
            newCapacity(minCapacity));
    }

    /**
     * 크기를 1만큼 늘린다.
     * @return
     */
    private Object[] grow() {
        return grow(elementCount + 1);
    }

    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                        capacityIncrement : oldCapacity); // capacityIncrement <= 0 이면 2배로 늘린다.
        if (newCapacity - minCapacity <= 0) {
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
            ? newCapacity
            : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) { // overflow
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    public synchronized void setSize(int newSize) {
        modCount++;
        if (newSize > elementData.length) {
            grow(newSize);
        }
        final Object[] elementToBeErased = elementData; // 왜 shallow copy 된 배열로 지우는가?
        for (int i = newSize; i < elementCount; i++) {
            elementToBeErased[i] = null;
        }
        elementCount = newSize;
    }


    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        super.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return super.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return super.parallelStream();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return super.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return super.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        List.super.sort(c);
    }
}
