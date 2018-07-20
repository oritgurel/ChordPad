package com.oritmalki.mymusicapp2.utils;

public class StringQueueArray
{
    int front, rear, size;
    int  capacity;
    String array[];

    public StringQueueArray(int capacity) {
        this.capacity = capacity;
        front = this.size = 0;
        rear = capacity - 1;
        array = new String[this.capacity];

    }

    // StringQueueArray is full when size becomes equal to
    // the capacity
    public boolean isFull(StringQueueArray queue)
    {  return (queue.size == queue.capacity);
    }

    public int getIndexOf(String x) {
        for (int i=0; i<array.length; i++) {
            if (array[i] == null) {
                return -1;
            }
            if (array[i].equals(x)) {
                return i;
            }
        }
        return -1;
    }

    // StringQueueArray is empty when size is 0
    public boolean isEmpty(StringQueueArray queue)
    {  return (queue.size == 0); }

    // Method to add an item to the queue.
    // It changes rear and size
    public void enqueue( String item)
    {
        if (isFull(this))
            return;
        this.rear = (this.rear + 1)%this.capacity;
        this.array[this.rear] = item;
        this.size = this.size + 1;
        System.out.println(item+ " enqueued to queue");
    }

    public String[] getArray() {
        return array;
    }

    public int getCapacity() {

        return capacity;
    }

    // Method to remove an item from queue.
    // It changes front and size
    public String dequeue()
    {
        if (isEmpty(this))
            return "";

        String item = this.array[this.front];
        this.front = (this.front + 1)%this.capacity;
        this.size = this.size - 1;
        return item;
    }

    // Method to get front of queue
    public String front()
    {
        if (isEmpty(this))
            return "";

        return this.array[this.front];
    }

    // Method to get rear of queue
    public String rear()
    {
        if (isEmpty(this))
            return "";

        return this.array[this.rear];
    }
}