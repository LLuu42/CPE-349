import java.lang.*;
import java.util.*;
public class MyHeap {
  //declares the private variables for the heap
  //the actual array
  private int [] heap;
  //the amount of occupied spots in the heap, IE elements in the heap
  private int size;
  //default constructor; sets the size to be 50, meaning that the
  //size has to be 51 to account for the empty spot
  public MyHeap() {
    heap = new int [51];
    size = 0;
  }
  //constructor that takes in an integer denoting the capacity of the
  //heap; consequently, the size has to be n + 1
  public MyHeap(int n) {
    heap = new int [n + 1];
    size = 0;
  }
  //builds the heap out of an array of integers; assumes we build
  //out of an empty heap
  //we're also assuming that the array starts at index 0 and not index 1
  public boolean buildHeap(int [] elements) {
    //checks to see if there is enough space in the heap
    if (elements.length > getHeapCap()) {
      return false;
    }
    size = elements.length;
    //shifts the input array elements by 1 to start the heap at index 1
    for (int i = 0; i < elements.length; i++) {
      heap[i + 1] = elements[i];
    }
    //calls driftdown starting from the bottom and working its way up
    for (int i = size / 2; i >= 1; i--) {
      driftDown(i);
    }
    //the build was successful since there's enough space
    return true;
  }
  //returns the internal array
  public int [] heapContents() {
    return heap;
  }
  //inserts an element into the heap
  public boolean insert(int element) {
    //checks to see if there is space
    if (isFull()) {
      return false;
    }
    //increases the size, and inserts that element into the next empty spot
    heap[++size] = element;
    //drifts that element up
    driftup(size);
    //if we've reached this far, we successfully inserted the element
    return true;
  }
  //returns the element at index 1
  public int findMax() {
    return heap[1];
  }
  //deletes the element at index 1
  public int deleteMax() {
    //gets the element at index 1
    int element = heap[1];
    //gets the last element and moves it up the starting spot
    heap[1] = heap[size--];
    //drifts the element we just moved up down to its proper position
    driftDown(1);
    //returns the max element
    return element;
  }
  //checks to see if the heap is empty
  public boolean isEmpty() {
    //even if the array may contain elements after removing the max
    //we'll say it's empty when the size is 0
    return size == 0;
  }
  //checks to see if the heap is full
  public boolean isFull() {
    //if the heap is at full capacity, then it is full
    return size + 1 == heap.length;
  }
  //returns the capacity of the heap, it is always one less than the size of
  //the array since we leave index 0 to be empty
  public int getHeapCap() {
    return heap.length - 1;
  }
  //gets the amount of elements in the array
  public int getHeapSize() {
    return size;
  }
  //calls heapsort on the array of elements passed in
  public int [] heapSortIncreasing(int [] elements) {
    //declares a temp variable
    int newSize = elements.length;
    //builds a heap for the array of elements passed in
    MyHeap newHeap = new MyHeap(newSize);
    //builds the heap
    newHeap.buildHeap(elements);
    //we need a new array to return since we need to reverse the elements
    int [] output = new int [newSize];
    //we store the largest element at the back of the new array and
    //move backwards through the output array
    for (int i = 1; i <= newSize; i++) {
      output[newSize - i] = newHeap.deleteMax();
    }
    return output;
  }
  public void driftDown(int index) {
    //we need to keep track of our starting point and the left child
    int child;
    int top = heap[index];
    //to prevent going out of the bounds of the heap
    while (2 * index <= size) {
      //get the leftmost child
      child = 2 * index;
      //if both children exist, and the left child is smaller than the right
      if (child != size && heap[child] < heap[child + 1]) {
        //then we'll move the right child up
        child++;
      }
      //if the parent is smaller than the child
      if (top < heap[child]) {
        //move the child up the heap
        heap[index] = heap[child];
      //but if the parent is bigger than both children, we are done
      } else {
        break;
      }
      //but if we keep going, we progress down the child's path
      index = child;
    }
    //move the original element to the spot where it belongs
    heap[index] = top;
  }
  public void driftup(int index) {
    //since we are drifting up, we need to find the parent
    int parent = index / 2;
    //gets the element at that particular index
    int element = heap[index];
    //as long as the index is never greater than the top of the heap
    //and the parent is smaller than the element
    while (index > 1 && heap[parent] < element) {
        //move the parent down a spot
        heap[index] = heap[parent];
        //ascend the tree
        index = parent;
        //get the next parent
        parent = parent / 2;
    }
    //finally, put the original element where it belongs
    heap[index] = element;
  }
}
