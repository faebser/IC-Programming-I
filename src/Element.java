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

        //
        point_distances = generatePointDistances(points);
        // set base line
        // and compute difference for each new line
        for (int i = 0; i < lines[0].length; i++) {
            float temp = (float)(Math.random() * max_width * 0.75 - 20);
            differences[i] = temp / (lines.length - 1);
            lines[0][i] = temp;
        }
        // fill out the remaining lists
        for (int i = 1; i < lines.length; i++) {
            for(int point = 0; point < lines[i].length; point++) {
                lines[i][point] = lines[0][point] - (differences[point] * i);
            }
        }

        margin = max_width  / 40;
    }

    public void draw () {
        parent.pushMatrix();
        {
            parent.translate(position.x, position.y);
            //parent.rect(0,0, max_width * 1.618122977f, max_width * 1.618122977f);
            parent.translate(rest * 0.5f , rest * 0.5f);
            for (int i = 0; i < lines.length ; i++) {
                parent.pushMatrix();
                {
                    parent.translate(0, i * margin);
                    for (int j = 0; j < lines[i].length -1 ; j++) {
                        parent.line(getPointDistance(point_distances, j), lines[i][j], getPointDistance(point_distances,j + 1), lines[i][j+1]);
                    }
                }
                parent.popMatrix();
            }
        }
        parent.popMatrix();
    }

    private float getPointDistance(float[] _pointDistances, int index) {
        float sum = 0;
        for (int i = 0; i <= index; i++) {
            sum = sum + _pointDistances[i];
        }
        return sum;
    }

    private float[] generatePointDistances(int amount) {
        float[] point_distances = new float[amount];
        float max_graph_width = max_width;
        point_distances[0] = 0;
        for (int i = 1; i < point_distances.length - 1; i++) {
            float max = Math.min(max_graph_width, 150);
            float temp = (float)(Math.random() * max);
            max_graph_width = max_graph_width - temp;
            point_distances[i] = temp;
        }
        point_distances[point_distances.length - 1] = max_graph_width;
        return point_distances;
    }
}
