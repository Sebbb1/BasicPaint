import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

public class TextButton extends Button {
	
	private String container;

	public TextButton(String container, int width, int height, float x, float y, int id, Color color, boolean rounded) {
		this.container = container;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.color = color;
		this.id = id;
		this.rounded = rounded;

		if(rounded) {
			rect = new RoundedRectangle(x, y, width, height, 5);
		} else {
			rect = new Rectangle(x, y, width, height);
		}
	}
	
	private int getMiddleStringWidth(String string, Graphics g) {
		return width/2 - g.getFont().getWidth(string)/2;
	}
	
	private int getMiddleStringHeight(String string, Graphics g) {
		return height/2 - g.getFont().getHeight(string)/2;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(color);
		g.fill(rect);
		g.setColor(Color.white);
		if (width != 0 && height != 0) {
			g.drawString(this.container, getMiddleStringWidth(container, g) + x, getMiddleStringHeight(container, g) + y);
		}
	}
}
