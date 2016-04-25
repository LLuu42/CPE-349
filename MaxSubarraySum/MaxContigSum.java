public class MaxContigSum {
  public static int findSumBruteForce(int [] arr) {
    int maxSoFar = arr[0];
    for (int i = 0; i < arr.length; i++) {
      for (int j = i; j < arr.length; j++) {
        int thisSum = 0;
        for (int k = j; k <= j; k++) {
          thisSum += arr[k];
        }
        if (thisSum > maxSoFar) {
          maxSoFar = thisSum;
        }
      }
    }
    return maxSoFar;
  }

  public static int findSumQuadratic(int [] arr) {
    int maxSum = arr[0];
    for (int i = 0; i < arr.length; i++) {
      int thisSum = 0;
      for (int j = i; j < arr.length; j++) {
        thisSum += arr[j];
        if (maxSum < thisSum) {
          maxSum = thisSum;
        }
      }
    }
    return maxSum;
  }

  public static int findSumNLogN(int [] arr, int left, int right) {
    if (left == right) {
      return arr[left];
    }
    int middle = (left + right) / 2;
    int leftSum = findSumNLogN(arr, left, middle);
    int middleSum = crossingSum(arr, left, middle, right);
    int rightSum = findSumNLogN(arr, middle + 1, right);
    return maxOfThree(leftSum, middleSum, rightSum);
  }

  private static int crossingSum(int arr[], int left, int middle, int right) {
    int sum = 0;
    int leftSum = arr[left];
    int rightSum = arr[middle + 1];
    for (int i = left; i <= middle; i++) {
      sum += arr[i];
      if (sum > leftSum) {
        leftSum = sum;
      }
    }
    sum = 0;
    for (int i = middle + 1; i <= right; i++) {
      sum += arr[i];
      if (sum > rightSum) {
        rightSum = sum;
      }
    }
    return leftSum + rightSum;
  }

  private static int maxOfThree(int i, int j, int k) {
    return Math.max(Math.max(i, j), k);
  }

  public static int findSumLinear(int [] arr) {
    int maxSum = arr[0];
    int thisSum = arr[0];
    for (int i = 1; i < arr.length; i++) {
      thisSum += arr[i];
      if (thisSum < arr[i]) {
        thisSum = arr[i];
      }
      if (maxSum < thisSum) {
        maxSum = thisSum;
      }
    }
    return maxSum;
  }
}
