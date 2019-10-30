package Shapes;

import java.util.ArrayList;

import Interfaces.Drawable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Layer {
	
	private ArrayList<Drawable> layerItems;
	
	public Layer() {
		layerItems = new ArrayList<Drawable>();
	}

	public ArrayList<Drawable> getShape() {
		return layerItems;
	}

	public void addToLayer(Drawable shape) {
		this.layerItems.add(shape);
	}

	public int getLastIndex() {
		return layerItems.size() - 1;
	}

	public int size() {
		return layerItems.size();
	}

	public void removeFromLayer(int index) {
		this.layerItems.remove(index);
	}

	public void render(GameContainer gc, Graphics g) {
		if(!layerItems.isEmpty()) {
			for(Drawable shape : layerItems) {
				shape.render(gc, g);
			}
		}
	}
}
