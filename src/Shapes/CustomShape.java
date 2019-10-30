package Shapes;

import Interfaces.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;

abstract public class CustomShape implements Drawable {
    protected Shape shape;
    protected boolean fill;
    protected Color color;
    protected float x;
    protected float y;

    public float getMinX() {
        return shape.getMinX();
    }

    public float getMinY() {
        return shape.getMinY();
    }

    public float getMaxX() {
        return shape.getMaxX();
    }

    public float getMaxY() {
        return shape.getMaxY();
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.setColor(color);
        if (fill) {
            g.fill(shape);
        } else {
            g.draw(shape);
        }
    }

    @Override
    public void move(GameContainer gc, float x, float y) {
        Input input = gc.getInput();
        float xInput = input.getMouseX();
        float yInput = input.getMouseY();
        shape.setLocation(shape.getX() + xInput - x, shape.getY() + yInput - y);
    }

}
