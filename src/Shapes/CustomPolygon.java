package Shapes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

public class CustomPolygon extends CustomShape {

    public CustomPolygon(float[] points, Color color, boolean fill) {
        this.x = points[0];
        this.y = points[1];
        this.color = color;
        this.fill = fill;
        shape = new Polygon(points);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        Input input = gc.getInput();
        shape = new Polygon(new float[] { x, y, input.getMouseX(), y, x + (input.getMouseX() - x)/2, input.getMouseY() } );
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }
}
