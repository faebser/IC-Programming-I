import processing.core.PApplet;
import processing.core.PVector;

public class MatrixElements extends PApplet {
    /*

    Manfred Mohr
    Programm 32 - "Matrix Elemente", 1970

    In jedem der sechzehn Quadrate von 5 x 5 cm sind vierzig Linien
    angeordnet. Die obere Linie wird gebildet, indem eine bestimmte
    Zahl (zwischen drei und zwölf) von zufällig ausgewählten
    Punkten miteinander verbunden wird. Die aufeinanderfolgenden
    Linien werden so berechnet, das die vierzigste Linie horizontal
    endet.

    aus "Der Algorithmus des Manfred Mohr, Texte 1963 - 1979"

    ---

    Manfred Mohr

    Routine 32 - "Matrix Elements", 1970

    In each of the sixteen square of 5 by 5 cm are forty lines
    arranged. The topmost line consists of a random amount
    (between three and twelve) of points. Each of the following
    lines changes its shape until the last line ends up
    in horizontal state.

    from "Der Algorithmus des Manfred Mohr, Texte 1963 - 1979
    */

    // This is the main entry point of the routine.
    // We pass the class MatrixElements to PApplet
    public static void main(String[] args) {
        PApplet.main("MatrixElements");
    }

    // This is where we are going to stuff
    // our line blocks.
    Element [] elements = new Element[16];

    public void settings () {
        // we want to super smooth lines
        // so we set smooth to 8x anti-aliasing
        smooth(8);
        // We want to run the sketch in fullscreen
        // with the P3D render
        fullScreen(P3D);

        // You can uncomment the following line
        // to debug the sketch in a window
        //size(800, 800, P3D);
    }

    public void setup () {
        // it is not interactive and nothing moves
        // so we can lower the framerate
        frameRate(4f);
        // as in the original work we want
        // to have a black background
        background(0);
        // The joints of the our lines
        // should be round
        strokeJoin(ROUND);

        // You can uncomment the following line
        // to change the fill of the debug rect
        // to red
        // fill(255, 0, 0);

        // We set the stroke color to white
        // with just a tad of transparency (100 is solid)
        stroke(255, 98);
        // We set the weight of the lines
        // to the smallest possible value
        // that still generates nice output
        // without too much visual noise
        strokeWeight(1);

        // All our elements need to need to be squares
        // so we try to find the biggest square that is going
        // to fit onto the screen. The biggest square can not be
        // bigger than the smaller side of the screen, that is what
        // Math.min is for. Afterwards we divide it by 5 so that we can
        // fit 4 elements into it including half an element on the top and
        // at the bottom for margin.
        float element_size = Math.min(height, width) / 5;
        // old school for loop over the empty elements array
        for (int i = 0; i < elements.length; i++) {
            // 
            elements[i] = new Element(this, element_size, getPosition(i, element_size));
        }
    }

    public void draw () {
        background(0);
        for(Element e: elements) {
            e.draw();
        }
    }

    private PVector getPosition(int index, float element_size) {
        final int count = 4;
        final float half_element_size = element_size * 0.5f;
        if (height < width) {
            return new PVector(index % count * element_size + (width * 0.5f - 4 * element_size * 0.5f),
                    index / count * element_size + half_element_size );
        }
        return new PVector(index % count * element_size + half_element_size,
                index / count * element_size + (height * 0.5f - 4 * element_size * 0.5f));
    }
}
