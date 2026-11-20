package datastructures;

import model.WaitListEntry;

/**
 * A queue created using linked nodes.
 * The first customer to join is the first customer processed (FIFO).
 */
public class WaitingListQueue {
    private class Node {
        WaitListEntry data;
        Node next;

        Node(WaitListEntry data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int count;

    /** Adds a waiting request at the end of the queue. */
    public void enqueue(WaitListEntry entry) {
        Node newNode = new Node(entry);

        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }

        count++;
    }

    /** Removes and returns the oldest request. */
    public WaitListEntry dequeue() {
        if (front == null) {
            return null;
        }

        WaitListEntry entry = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        count--;
        return entry;
    }

    /** Looks at the oldest request without removing it. */
    public WaitListEntry peek() {
        if (front == null) {
            return null;
        }
        return front.data;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }
}
