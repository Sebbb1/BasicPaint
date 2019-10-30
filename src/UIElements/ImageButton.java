package UIElements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class ImageButton extends Button {

	private Image image;

	public ImageButton(Image image,int width, int height, float x, float y, Color color, int id, boolean rounded) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.rect = new RoundedRectangle(x, y, width, height, 5);
		this.color = color;
		this.id = id;
		this.active = false;
		this.image = image;

		if(rounded) {
			rect = new RoundedRectangle(x, y, width, height, 5);
		} else {
			rect = new Rectangle(x, y, width, height);
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(color);
		g.fill(rect);
		image.draw(x + (width*20)/100, y + (height*20)/100, width - (width*40)/100, height - (height*40)/100);
	}
}
