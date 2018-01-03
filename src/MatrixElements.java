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

    */

    public static void main(String[] args) {
        PApplet.main("MatrixElements");
    }

    Element [] elements = new Element[16];

    public void settings () {
        smooth(8);
        fullScreen(P3D);
        //size(800, 800, P3D);
    }

    public void setup () {
        background(0);
        strokeJoin(ROUND);
        fill(255, 0, 0);
        stroke(255);
        strokeWeight(2);
        float element_size = Math.min(height, width) / 6;
        for (int i = 0; i < elements.length; i++) {
            PVector temp = new PVector(i % 4 * element_size + element_size, i / 4 * element_size + element_size);
            println(i, width , temp.x);
            println(i, width, temp.y);
            elements[i] = new Element(this, element_size, temp);
        }
    }

    public void draw () {
        for(Element e: elements) {
            e.draw();
        }
    }
}
