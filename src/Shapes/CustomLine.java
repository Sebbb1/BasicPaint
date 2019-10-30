package Shapes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.state.StateBasedGame;

public class CustomLine extends CustomShape {

    public CustomLine(float x1, float y1, float x2, float y2, Color color) {
        this.x = x1;
        this.y = y1;
        this.color = color;
        this.fill = true;
        this.shape = new Line(x1, y1, x2, y2);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        Input input = gc.getInput();
        ((Line) shape).set(x, y, input.getMouseX(), input.getMouseY());
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.setColor(color);
        g.draw(shape);
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }
}
