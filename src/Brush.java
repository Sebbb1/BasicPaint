import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class Brush implements Drawable {

    private ArrayList<Circle> circles;
    private Color color;
    int radius;

    public Brush(int radius, Color color) {
        this.radius = radius;
        this.circles = new ArrayList<Circle>();
        this.color = color;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        for (Circle circle : circles) {
            g.setColor(color);
            g.fill(circle);
            g.draw(circle);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        Input input = gc.getInput();
        circles.add(new Circle(input.getMouseX(), input.getMouseY(), radius));
    }

    @Override
    public boolean contains(int x, int y) {
        for (Circle circle : circles) {
            if (circle.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void move(GameContainer gc, float x, float y) {
        Input input = gc.getInput();

        float xInput = input.getMouseX();
        float yInput = input.getMouseY();
        for (Circle circle : circles) {
            circle.setLocation(circle.getX() + xInput - x, circle.getY() + yInput - y);
        }
    }
}
