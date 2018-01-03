import processing.core.PApplet;
import processing.core.PVector;

public class Element {

    PApplet parent;
    float max_width;
    final static int points = (int)(Math.random() * 8 + 3);
    float[] point_distances;
    float margin;
    float[] differences = new float[points];
    float[][] lines = new float[40][points];
    PVector position;


    public Element (PApplet _parent, float _width, PVector _position) {
        parent = _parent;
        max_width = _width * 0.618f;
        position = _position;

        point_distances = generatePointDistances(points);
        // set base line
        // and compute difference for each new line
        for (int i = 0; i < lines[0].length; i++) {
            float temp = (float)(Math.random() * 250 - 20);
            differences[i] = temp / (lines.length - 1);
            lines[0][i] = temp;
        }
        // fill out the remaining lists
        for (int i = 1; i < lines.length; i++) {
            for(int point = 0; point < lines[i].length; point++) {
                lines[i][point] = lines[0][point] - (differences[point] * i);
            }
        }

        // calculate padding
        // stroke width is 2
        margin = max_width  / 40;
    }

    public void draw () {
        parent.pushMatrix();
        {
            parent.translate(position.x, position.y);
            parent.rect(0,0, max_width * 1.618122977f, max_width * 1.618122977f);
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
