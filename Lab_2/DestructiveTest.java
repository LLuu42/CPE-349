public class DestructiveTest {
    public static int [] findHighestSafeRung(int height, int safe_rung) {
        //creates array of integers to denote the output of the method
        int [] output = new int [6];
        //first element of output is the height of the ladder
        output[0] = height;
        //second element of output is the safe rung to be found
        output[1] = safe_rung;
        //fourth and fifth outputs denote where the first and second devices
        //broke, start them at -1 to mean that they haven't broken yet
        output[3] = -1;
        output[4] = -1;
        //create an interval using the square root of the height
        //use the ceiling function to convert the interval to an integer
        int step = (int) Math.ceil(Math.pow(height, .5));
        //int rung refers to the ladder rung where we last dropped the device
        int rung;
        //int tries refers to how many times the algorithm runs
        int tries = 0;
        //start at the bottom of the ladder, and as long as we haven't passed
        //the safe rung
        for (rung = 0; rung <= safe_rung;) {
            //move over an interval
            rung += step;
            //keep track of how many times we run this loop
            tries++;
            //if we go over the height of the ladder
            if (rung > height) {
                //then we say it broke at the top of the ladder
                output[3] = height;
                //we then break immediately since we've passed the safe rung
                //and the height of the ladder
                break;
            //but otherwise, keep track of the latest rung
            } else {
                output[3] = rung;
            }
        }
        //move back one interval so that we test on a rung that is lower than
        //the safe rung
        rung -= step;
        //linearly find the safe rung
        while (rung <= safe_rung) {
            //keep track of how many runs we have
            tries++;
            //move up by one rung and test again
            rung++;
        }
        //when we exit the loop, we have found the safe rung!
        //rung is now the first rung where the device will break when dropped
        //so the previous rung is the safe rung
        output[2] = rung - 1;
        //but if it is one less rung than where we broke the first device
        //we technically did not break the second device
        if (rung != output[3])
            //but if we did, then we keep track of it
            output[4] = rung;
        //assign the amount of tries to the output's last element
        output[5] = tries;
        //return the array of integers
        return output;
    }
}
