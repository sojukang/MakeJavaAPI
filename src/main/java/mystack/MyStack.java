package java.mystack;

import java.util.EmptyStackException;
import java.util.Vector;

public class MyStack<E> extends MyVector<E> {
    public MyStack() {
    }

    /**
     * Stack에 원소를 넣는다. LIFO
     * Vector의 addElement와 동일한 기능
     */
    public E push(E item) {
        addElement(item);

        return item;
    }

    public synchronized E pop() {
        E obj;
        int len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    /**
     * 가장 마지막 원소를 제거하지 않고 반환한다.
     * 스택 사이즈가 0일 경우 EmptyStackException을 던진다.
     */
    public synchronized E peek() {
        int len = size();

        if (len == 0) {
            throw new EmptyStackException();
        }
        return elementAt(len - 1);
    }

    public boolean empty() {
        return size() == 0;
    }

    /**
     * 마지막으로부터 위치를 찾는다.
     * @param o the desired object
     * @return 마지막에 위치할 경우 1을 반환하며, 1씩 증가한다. 원소가 스택에 존재하지 않을 경우 -1을 반환한다.
     */
    public synchronized int search(Object o) {
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }
}
