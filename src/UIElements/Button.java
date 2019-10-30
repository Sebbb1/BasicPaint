package UIElements;

import Interfaces.Clickable;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

public abstract class Button implements Clickable {
	protected int width;
	protected int height;
	protected float x;
	protected float y;
	protected Rectangle rect;
	protected Color color;
	protected int id;
	protected boolean rounded;
	protected boolean active;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		rect.setX(x);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		rect.setY(y);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;

		if (rounded) {
			rect = new RoundedRectangle(x, y, width, height, 5);
		} else {
			rect = new Rectangle(x, y, width, height);
		}

	}

	public Rectangle getRect() {
		return rect;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getActive() {
		return active;
	}

	@Override
	public boolean clickListener(Input input) {
		if(!this.rect.contains(input.getMouseX(), input.getMouseY())) {
			return false;
		}
		return input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
	}

	public void hover(Input input) {
		if(!active) {
			if (rect.contains(input.getMouseX(), input.getMouseY())) {
				color = new Color(75, 75, 75);
			} else {
				color = Color.black;
			}
		}
	}
}
