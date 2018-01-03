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
            // Now we generate and stash our elements into the array
            // If you want to follow the flow of the routine best
            // change to the getPosition function and then the constructor
            // of the Element class
            elements[i] = new Element(
                    this, // we have to pass in our instance of the PApplet
                    element_size, // the calculated max size of the element including margin
                    getPosition(i, element_size) // the position on the canvas coming from the getPosition method
            );
        }
    }

    // the draw method that gets called at the beginning of
    // each frame
    public void draw () {
        // we reset the background to black
        // because of our mildly transparent
        // stroke color
        background(0);
        // new style for loop over our elements
        for(Element e: elements) {
            e.draw(); // this draws the selected element
        }
    }

    // this is a convinience method to calculate the correct
    // position of an element given the its position in the array.
    // We are going to return a PVector a class that contains a x and
    // a y property (see also https://processing.org/reference/PVector.html)
    // We need to pass in the position of the element, the index, and also
    // the element size that we calculated earlier in the setup().
    private PVector getPosition(int index, float element_size) {
        // the count of elements on one line of our big square
        final int count = 4;
        // this variable helps with the readabilty of the next
        // block of code
        final float half_element_size = element_size * 0.5f;
        // we check if the height of the screen is smaller
        // than the width, like most pc screens today
        if (height < width) {
            // if height is smaller we need to account
            // for the additional space on the horizontal axis
            // of the screen.
            // we return a new instance of a PVector.
            return new PVector(
                    index % count * element_size + (width * 0.5f - 4 * element_size * 0.5f),
                    index / count * element_size + half_element_size
            );
        }
        // if width is smaller than height
        // we return a different PVector
        // that accounts for the additional space on the
        // vertical axis
        return new PVector(
                index % count * element_size + half_element_size,
                index / count * element_size + (height * 0.5f - 4 * element_size * 0.5f)
        );
    }
}
