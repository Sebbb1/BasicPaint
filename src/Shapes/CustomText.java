package Shapes;

import Interfaces.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class CustomText implements Drawable {

    private float x, y;
    private String container;
    private float width, height;
    private Color color;

    public CustomText(float x, float y, String container, Color color) {
        this.x = x;
        this.y = y;
        this.container = container;
        this.color = color;
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        g.setColor(color);
        g.drawString(container, x, y);
        width = g.getFont().getWidth(container);
        height = g.getFont().getHeight(container);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        return;
    }

    @Override
    public boolean contains(int x, int y) {
        if (x > this.x && x < this.x + width && y > this.y && y < this.y + height) {
            return true;
        }
        return false;
    }

    @Override
    public void move(GameContainer gc, float x, float y) {
        Input input = gc.getInput();
        this.x = this.x + input.getMouseX() - x;
        this.y = this.y + input.getMouseY() - y;
    }
}
