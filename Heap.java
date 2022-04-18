import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Heap {

    public static void main(String[] args) throws FileNotFoundException {
        File heapFile = new File("inputFile.txt");
        Scanner in = new Scanner(heapFile);
        int instructions = in.nextInt();
        int lastEm = 0;
        MinHeap heap = new MinHeap(instructions);

        if(in.hasNextLine()){
            for(int i = 0; instructions >= i; i++) {
                String line = in.nextLine();
                String[] details = line.split(" ");
                String type = details[0];
                switch (type) {
                    case ("IN"):
                        heap.insert(Integer.parseInt(details[1]));
                        break;
                    case ("DK"):
                        heap.decreaseKey(Integer.parseInt(details[1]), Integer.parseInt(details[2]));
                        break;
                    case ("EM"):
                        lastEm = heap.extractMin();
                        break;
                }
            }
        }
        System.out.println(lastEm);
    }
}

class MinHeap {
    private int array[];
    private int arraySize;
    private int heapSize;

    public MinHeap() {

    }

    /**
     * init method
     * @param arraySize
     */
    public MinHeap(int arraySize) {
        array = new int[arraySize];
        this.arraySize = arraySize;
        heapSize = 0;
    }

    /**
     * finds parent of given index
     * @param i
     * @return
     */
    private int findParent(int i) {
        int parent = (i - 1)/3;
        return parent;
    }

    /**
     * finds child when sorting
     * @param i
     * @param k
     * @return
     */
    private int findChild(int i, int k) {
        return 3 * i + k;
    }

    /**
     * sorts array and heap
     * @param index
     */
    private void sortDown(int index)
    {
        int child;
        int temp = array[index];
        while (findChild(index, 1) < heapSize)
        {
            child = minChild(index);
            if (array[child] < temp)
                array[index] = array[child];
            else
                break;
            index = child;
        }
        array[index] = temp;
    }

    /**
     * finds the smallest child in heap
     * @param index
     * @return
     */
    private int minChild(int index)
    {
        int child = findChild(index, 1);
        int i = 2;
        int position = findChild(index, i);
        while ((i <= 3) && (position < heapSize))
        {
            if (array[position] < array[child])
                child = position;
            position = findChild(index, i++);
        }
        return child;
    }

    /**
     * sorts array and heap
     * @param index
     */
    private void sortUp(int index) {
        boolean done = false;
        int temp = array[index];
        while (index > 0 && temp < array[findParent(index)] && done == false) {
            if(temp < array[findParent(index)]){
                array[index] = array[findParent(index)];
                index = findParent(index);
                array[index] = temp;
                if(array[index] < array[findParent(index)]){
                    array[index] = array[findParent(index)];
                    index = findParent(index);
                    array[index] = temp;
                }
                else{
                    done = true;
                }
            }
        }
    }

    /**
     * inserts new numbers into the heap then sends them to be sorted up or down
     * @param element
     */
    public void insert(int element) {
        array[heapSize++] = element;
        sortUp(heapSize-1);
    }

    /**
     * reduces the value of an element in the array at a set index
     * @param index
     * @param newElement
     */
    public void decreaseKey(int index, int newElement) {
        if(newElement < array[index]) {
            array[index] = newElement;
            sortUp(index);
        }
    }

    /**
     * removes the smallest value of the heap
     * @return
     */
    public int extractMin() {
        int min = array[0];
        heapSize--;
        array[0] = array[heapSize];
        sortDown(0);
        return min;
    }
}