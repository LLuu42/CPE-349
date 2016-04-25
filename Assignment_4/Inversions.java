public class Inversions {
  public static int numInversions(int [] ranking) {
    //create a temp array of the same size
    int [] temp = new int [ranking.length];
    //call mergesort to get number of inversions
    return mergeSort(ranking, temp, 0, ranking.length - 1);
  }
  private static int mergeSort(int [] arr, int [] temp, int left, int right) {
    //if there is more than 1 element in the subarray we want to sort
    if (left < right) {
      //find the splitting point
      int center = (left + right) / 2;
      //sort the left side and count left inversions
      int invLeft = mergeSort(arr, temp, left, center);
      //sort the right side and count right inversions
      int invRight = mergeSort(arr, temp, center + 1, right);
      //count inversions that cross between the middle
      return invLeft + invRight + merge(arr, temp, left, center + 1, right);
    }
    //otherwise there are 0 inversions
    return 0;
  }
  private static int merge(int [] arr, int [] temp, int left, int right,
                           int end) {
    //find the ending point of the left subarray
    int leftEnd = right - 1;
    //find the starting point of the temp array
    int pos = left;
    //get the size of the sorted array
    int size = end - left + 1;
    //keep track of inversion count
    int invCount = 0;
    //comparing left and right side
    while (left <= leftEnd && right <= end) {
      if (arr[left] < arr[right]) {
        temp[pos++] = arr[left++];
      } else {
        temp[pos++] = arr[right++];
        //since the right side is supposed to be bigger than the left side
        //if we get into this conditional, then however many elements are 
        //on the left side, are bigger than the next element on the right side
        //we start from the middle point and subtract however many we already
        //put into the temp array
        invCount += leftEnd + 1 - left;
      }
    }
    //copy the rest of the elements
    while (left <= leftEnd) {
      temp[pos++] = arr[left++];
    }
    while (right <= end) {
      temp[pos++] = arr[right++];
    }
    //copy the temp array over, starting from the end
    for (int j = 0; j < size; j++, end--) {
      arr[end] = temp[end];
    }
    //return the number of inversions
    return invCount;
  }
}
