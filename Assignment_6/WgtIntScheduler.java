import java.util.*;
import java.lang.*;
import java.io.File;

public class WgtIntScheduler {
  public static int [] getOptSet(int [] stime, int [] ftime,
                                        int [] weight) {
    //create interval objects, which are needed to keep order since we
    //have three arrays
    Interval [] intervals = new Interval [stime.length];
    //create the interval objects
    for (int i = 0; i < stime.length; i++) {
      intervals[i] = new Interval(i + 1, stime[i], ftime[i], weight[i]);
    }
    //sort the intervals in order of increasing finish time
    Arrays.sort(intervals, new IntervalComparator());
    //calculate the closest compatible job for job i
    int [] closestJobs = new int[stime.length];
    for (int i = 0; i < stime.length ; i++) {
      closestJobs[i] = 0;
      for (int j = i - 1; j >= 0; j--) {
        if (intervals[i].stime >= intervals[j].ftime) {
          closestJobs[i] = j + 1;
          j = 0;
        }
      }
    }
    //represent the total weight at that time
    int [] times = new int[stime.length + 1];
    //starting after the first interval, find the optimum subset of intervals
    for (int i = 1; i < stime.length; i++) {
      //calculate the compatible interval prior to this interval
      //this step is used to see if we use the interval or not
      times[i] = Math.max(intervals[i-1].weight
                 + times[closestJobs[i-1]], times[i-1]);
    }
    //get the intervals we used in our optimal solution
    //used stringbuilder because of its ability to reverse strings
    String [] steps = new StringBuilder(traceback(stime.length,
                      intervals, times, closestJobs)).
                      reverse().toString().split(" ");
    //represents the intervals in our optimal solution, size is -1 because of
    //the string that traceback returns
    int [] schedule = new int [steps.length - 1];
    //convert each step into an int
    for (int i = 0; i < schedule.length; i++) {
      schedule[i] = Integer.parseInt(steps[i + 1]);
    }
    //return an array containing the job numbers
    Arrays.sort(schedule);
    return schedule;
  }

  //traceback method
  //we are using a string because this method is recursive, and makes acquiring
  //data from the previous recursive call much easier
  private static String traceback(int jobStep, Interval [] intervals,
                                  int [] times, int [] closestJobs) {
    //to make sure we don't go below index 0
    if (jobStep != 0) {
      //calculate the closest job
      int closestJob = closestJobs[jobStep - 1];
      //if we did use this job, traceback
      if ((intervals[jobStep - 1].weight + times[closestJob])
                                      >= times[jobStep - 1]) {
        return intervals[jobStep - 1].job + " " +
               traceback(closestJob, intervals, times, closestJobs);
      } else {
        //otherwise go to the previous job and see if we did
        return "" + traceback(jobStep - 1, intervals, times, closestJobs);
      }
    }
    //base case
    return "";
  }

  //private class representing an interval
  //this makes sorting the intervals much simpler
  private static class Interval {
    int job, stime, ftime, weight;
    //constructor for the interval
    Interval(int job, int stime, int ftime, int weight) {
      this.job = job;
      this.stime = stime;
      this.ftime = ftime;
      this.weight = weight;
    }
  }

  //comparator for the interval
  //necessary to sort the intervals with the Arrays.sort method by finish time
  public static class IntervalComparator implements Comparator<Object> {
    //compares two intervals
    public int compare(Object o1, Object o2) {
      //casts the objects into intervals
      Interval obj1 = (Interval) o1;
      Interval obj2 = (Interval) o2;
      //orders the intervals by their finish time
      if (obj1.ftime > obj2.ftime) {
        return 1;
      } else if (obj1.ftime < obj2.ftime) {
        return -1;
      } else {
        return 0;
      }
    }
    //method is needed to fulfill the comparator interface
    public boolean compare(Object obj)
    {
 	    if (obj == null)
 	      return false;
 	    if (!obj.getClass().equals(this.getClass()))
 	      return false;
 	    return true;
    }
  }

}
