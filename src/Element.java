import processing.core.PApplet;
import processing.core.PVector;

public class Element {

    PApplet parent; // this is the Processing Applet that we need to draw stuff
    float max_width; // the max width and height of our box excluding margin
    float rest; // the difference between the total width/height and the inner box
    final static int points = (int)(Math.random() * 8 + 3); // the amount of points our square, between 3 and 12
    float[] point_distances; // the array to store the distance between the points
    float margin; // the distance between the lines
    float[] differences = new float[points]; // the array to store amount a point needs to move per step to reach zero
    float[][] lines = new float[40][points]; // a two dimensional array for storing 40 times an array of our points
    PVector position; // the calculated position for our box


    // this is the constructor of our element. here we generate all the points
    // to draw the lines later
    public Element (PApplet _parent, float _width, PVector _position) {
        // we save the outer PApplet to draw our stuff later
        parent = _parent;
        // the _width is the total width/height of the box
        // we take bigger part of the golden cut for the lines
        max_width = _width * 0.618f;
        // this is the rest fo the bigger box
        // we need it later to center our content inside of the box
        rest = _width - max_width;
        // we save the position to use later
        position = _position;

        // we generate the different distances between our points
        point_distances = generatePointDistances(points);
        // now we start with calculating the points for the first line
        for (int i = 0; i < lines[0].length; i++) {
            // We generate a random value and scale it with three quarters
            // of the element height and move it up by 20 pixels so we also
            // have points that stick out on top
            float temp = (float)(Math.random() * (max_width * 0.75) - 20);
            // we calculate the step that each point has to take to
            // get to zero at the end
            differences[i] = temp / (lines.length - 1);
            // we save the point at the correct position
            lines[0][i] = temp;
        }
        // Now we need to add the remaining points to the lists
        for (int i = 1; i < lines.length; i++) {
            // here we have a beautiful double for loop sandwich
            // the outer layer iterates through all the lists
            // the inner loop iterates over all the points in a list
            for(int point = 0; point < lines[i].length; point++) {
                // we calculate our current point be subtracting
                // from the starting point the product our calculated step
                // times the index of the line
                lines[i][point] = lines[0][point] - (differences[point] * i);
            }
        }
        // just calculating the distance between the lines
        margin = max_width  / 40;
    }

    // this is the draw method in which we actually get
    // around to draw all the lines
    public void draw () {
        // we push a new transformation matrix
        // so that we can translate
        parent.pushMatrix();
        {
            // we translate the to the position of our element
            parent.translate(position.x, position.y);
            // You can uncomment the following line to see a debug rect
            // drawn behind our lines
            //parent.rect(0,0, max_width * 1.618122977f, max_width * 1.618122977f);
            // We translate again to center our element in the surrounding box
            // to do that we split the space that is not take up by our element
            // in half.
            parent.translate(rest * 0.5f , rest * 0.5f);
            // Again the beautiful double for loop sandwich
            for (int i = 0; i < lines.length ; i++) {
                // again we push a new transformation matrix
                parent.pushMatrix();
                {
                    // we translate to the correct height
                    // inside of our box to start the line
                    parent.translate(0, i * margin);
                    // this is the inner part of the sandwich
                    // that iterates over all the points in a line
                    // but we will not iterate until the last line
                    // but only to the line before it. The line
                    // needs two points to be draw but the last point
                    // concludes the line and does not continue it.
                    for (int j = 0; j < lines[i].length -1 ; j++) {
                        // we draw a line from the current point to the next point
                        // getting the position of the x axis from the getPointDistance
                        // method.
                        parent.line(getPointDistance(point_distances, j), lines[i][j], getPointDistance(point_distances,j + 1), lines[i][j+1]);
                    }
                }
                // we pop the matrix
                parent.popMatrix();
            }
        }
        // again popping the matrix
        parent.popMatrix();
        // and that's all we need for drawing all the lines of one element
    }

    private float getPointDistance(float[] _pointDistances, int index) {
        float sum = 0;
        for (int i = 0; i <= index; i++) {
            sum = sum + _pointDistances[i];
        }
        return sum;
    }

    // this method returns an array of float numbers that
    // describe the position of our points on the x axis.
    private float[] generatePointDistances(int amount) {
        // first we build an array with the correct amount
        // that we get from the call of the method
        float[] point_distances = new float[amount];
        // we put the max_width from the outer scope into
        // a local variable so we can work on it
        float max_graph_width = max_width;
        // the first point is always at 0 on the x axis
        point_distances[0] = 0;
        // we run a for loop over the array of values
        // starting with the first and leaving out the last one
        for (int i = 1; i < point_distances.length - 1; i++) {
            // To get a nicer and not total random distribution
            // of our points on the horizontal axis we take
            // the smaller value of rest of the length and half of the length
            float max = Math.min(max_graph_width, max_width * 0.5f);
            // We generate a random number in the range from 0-1 and
            // scale it with our max variable
            float temp = (float)(Math.random() * max);
            // Now we subtract our current value from
            // the full length to reduce the window for the
            // random value
            max_graph_width = max_graph_width - temp;
            // and save the value in the array
            point_distances[i] = temp;
        }
        // the last point is always at the last value
        point_distances[point_distances.length - 1] = max_graph_width;
        // we return the array with all the point values
        return point_distances;
    }
}
