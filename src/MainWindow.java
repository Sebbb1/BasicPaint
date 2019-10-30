import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import javax.swing.JOptionPane;
import java.awt.Font;

public class MainWindow extends BasicGameState {

	//Layout parameters / UI
	private Shape layerMenu, toolsMenu;
	private Rectangle colorMenu;
	private int widthLayerMenu, widthToolsMenu;
	private int widthColorMenu, heightColorMenu;
	private int buttonMargin, topMargin, selectionButtonMargin;
	private int buttonWidthHeight;
	private Font font;
	private TrueTypeFont trueTypeFont;
	private Shape selectionShape; // Action area when eraser or brush selected
	private float drawableAreaMaxX; // max authorized x to draw

	// Eyedropper button
	private Color eyedropperColor;
	private boolean mousePressed;

	//Buttons
	private int activeButton;
	private TextButton fillShapeButton, colorButton, saveImageButton, addLayerButton;
	private Hashtable<String, Button> selectionButton; //Selection button
	private Hashtable<String, Button> colorSelectionButton; //Color button
	private ArrayList<TextButton> layerButton; // Button to access layer

	//Temp field to write text when textButton is selected
	private TextField textField;

	//Move shape
	float x, y;

	//Layer parameters
	private ArrayList<Layer> layer;
	private Drawable tempShape;
	private boolean fillShape; //Fill shape
	private int maxLayer;
	private int activeLayer;

	//Select shape when moveShapeButton is selected and mouse clicked
	private Integer indexSelectedShape;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {

		indexSelectedShape = null;

		//Different menu size
		widthLayerMenu = 320;
		widthToolsMenu = 50;
		buttonWidthHeight = (78*widthToolsMenu)/100;
		widthColorMenu = 4*buttonWidthHeight + 5*5;
		heightColorMenu = 2*buttonWidthHeight + 3*5;
		selectionButtonMargin = 20;
		topMargin = 35;
		buttonMargin = 5;
		activeButton = 1;
		activeLayer = 0;
		x = 0; y = 0;

		selectionShape = null;

		drawableAreaMaxX = gc.getWidth() - (widthLayerMenu + widthToolsMenu);
		mousePressed = false;

		//Create the first layer
		maxLayer = 1;
		layer = new ArrayList<Layer>();
		layerButton = new ArrayList<TextButton>();
		layerButton.add(new TextButton("Layer " + maxLayer, widthLayerMenu, buttonWidthHeight, gc.getWidth() - widthLayerMenu, buttonWidthHeight + buttonMargin, 1, new Color(75, 75, 75), false));
		layer.add(new Layer());

		//Create the menu
		layerMenu = new Rectangle(gc.getWidth() - widthLayerMenu, 0, widthLayerMenu, gc.getHeight());
		toolsMenu = new Rectangle(layerMenu.getX() - widthToolsMenu, 0, widthToolsMenu, gc.getHeight());
		colorMenu = new Rectangle(toolsMenu.getX() - widthColorMenu - 10, buttonWidthHeight, 0, 0);

		//Get layer limit
		drawableAreaMaxX = gc.getWidth() - widthLayerMenu - widthToolsMenu;

		//Button type, width, height, xPosition, yPosition, BackgroundColor
		colorButton = new TextButton("", buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, topMargin, 1, Color.black, true);

		colorSelectionButton = new Hashtable<>();
		selectionButton = new Hashtable<>();
		selectionButton.put("circleButton", new ImageButton(new Image("res/circle.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, colorButton.getRect().getMaxY() + selectionButtonMargin, Color.black, 2, true));
		selectionButton.put("rectangleButton", new ImageButton(new Image("res/square.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("circleButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 3, true));
		selectionButton.put("triangleButton", new ImageButton(new Image("res/triangle.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("rectangleButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 4, true));
		selectionButton.put("moveShapeButton", new ImageButton(new Image("res/move.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("triangleButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 5, true));
		selectionButton.put("textButton", new ImageButton(new Image("res/text.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("moveShapeButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 6, true));
		selectionButton.put("lineButton", new ImageButton(new Image("res/line.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("textButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 7, true));
		selectionButton.put("eraserButton", new ImageButton(new Image("res/eraser.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("lineButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 8, true));
		selectionButton.put("brushButton", new ImageButton(new Image("res/brush.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("eraserButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 9, true));
		selectionButton.put("eyedropperButton", new ImageButton(new Image("res/eyedropper.png"), buttonWidthHeight, buttonWidthHeight, gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("brushButton").getRect().getMaxY() + selectionButtonMargin, Color.black, 10, true));

		saveImageButton = new TextButton("Save as PNG", widthLayerMenu, buttonWidthHeight, gc.getWidth() - widthLayerMenu + 1, 0, 101, Color.red, false);
		addLayerButton = new TextButton("+ Add Layer", widthLayerMenu, buttonWidthHeight, gc.getWidth() - widthLayerMenu, gc.getHeight() - buttonWidthHeight, 102, new Color(70, 70, 70), false);
		fillShapeButton = new TextButton("Fill", 0, 0,gc.getWidth() - widthLayerMenu - widthToolsMenu + (widthToolsMenu-buttonWidthHeight)/2, selectionButton.get("eyedropperButton").getRect().getMaxY() + selectionButtonMargin, 11, null, true);

        font = new Font("Product Sans", Font.PLAIN, 17);
        trueTypeFont = new TrueTypeFont(font, true);

		//Color
		hideOrCreateColorMenu();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {

	    g.setFont(trueTypeFont);

		//General settings
		g.setAntiAlias(true);
		g.setBackground(Color.white);

		if (selectionShape != null) {
			g.setColor(Color.gray);
			g.draw(selectionShape);
		}

		//Draw all the shapes in the layer
		for (int i = 0; i < layer.size(); i++) {
			layer.get(i).render(gc, g);
		}

		//Draw the current shape when left mouse button pressed
		if(tempShape != null) {
			tempShape.render(gc, g);
		}

		//Draw the text field when textButton selected and clicked once to create the text field
		if (textField != null) {
			g.setColor(colorButton.getColor());
			textField.render(gc, g);
		}

		//Draw the ui
		g.setColor(new Color(112, 112, 112));
		g.fill(layerMenu);
		g.fill(colorMenu);
		g.setColor(new Color(56, 56, 56));
		g.fill(toolsMenu);

		//Selection buttons (eraser, brush, ...)
		for (Map.Entry<String, Button> e : selectionButton.entrySet()) {
			e.getValue().render(gc, g);
		}

		fillShapeButton.render(gc, g);
		colorButton.render(gc, g);
		saveImageButton.render(gc, g);

		//Layer menu
		addLayerButton.render(gc, g);
		for (TextButton aLayerButton : layerButton) {
			aLayerButton.render(gc, g);
		}

		//Color button
		for (Map.Entry<String, Button> e : colorSelectionButton.entrySet()) {
			e.getValue().render(gc, g);
		}

		//Get the color of pixel if mouse clicked and eyedropper button selected
		if(mousePressed && (activeButton == selectionButton.get("eyedropperButton").getId())) {
			Color color = g.getPixel((int) x, (int) y);
			if (color != Color.white) {
				eyedropperColor = color;
			}
			mousePressed = false;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		Input input = gc.getInput();

		int xInput = input.getMouseX();
		int yInput = input.getMouseY();

		//Click listener for all selection button (except color selection button)
		for (Map.Entry<String, Button> e : selectionButton.entrySet()) {
			e.getValue().hover(input);
			if (e.getValue().clickListener(input)) {
				setActive(e.getValue());
			}
		}

		//Click listener for the color selection button
		if (colorButton.clickListener(input)) {
			showColorMenu();
		}

		//Click listener for the add layer button
		if(addLayerButton.clickListener(input)) {
			// 13 layers max
			if (layerButton.size() < 13) {
				maxLayer++;
				layerButton.add(new TextButton("Layer " + maxLayer, widthLayerMenu, buttonWidthHeight, gc.getWidth() - widthLayerMenu, layerButton.get(layerButton.size() - 1).getRect().getMaxY() + 10, layerButton.size() + 1, new Color(150, 150, 150), false));
				layer.add(new Layer());
				setLayerActive(layerButton.get(layerButton.size() - 1));
			}
		}
		if (saveImageButton.clickListener(input)) {
			saveImage(gc);
		}

		//Layer menu -> select active layer
		for (int i = 0; i < layerButton.size(); i++) {
			if (layerButton.get(i).clickListener(input)) {
				setLayerActive(layerButton.get(i));
			}
		}

		//click listener for the color button
		for (Map.Entry<String, Button> e : colorSelectionButton.entrySet()) {
			if (e.getValue().clickListener(input)) {
				colorButton.setColor(e.getValue().getColor());
				hideOrCreateColorMenu();
			}
		}

		if (activeButton == selectionButton.get("brushButton").getId() || activeButton == selectionButton.get("eraserButton").getId() && xInput < drawableAreaMaxX) {
			selectionShape = new Circle(xInput, yInput,10);
		} else {
			selectionShape = null;
		}

		if (fillShapeButton.clickListener(input)) {
			if (!fillShapeButton.getActive()) {
				fillShapeButton.setColor(new Color(175, 175, 175));
			} else {
				fillShapeButton.setColor(Color.black);
			}
			fillShapeButton.setActive(!fillShapeButton.getActive());
			fillShape = !fillShape;
		}

		//Create shape according to which button is active
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			mousePressed = true;
			x = input.getMouseX();
			y = input.getMouseY();

			//Different action depending on the selected button
			if (x < drawableAreaMaxX) {

				if (activeButton == selectionButton.get("rectangleButton").getId()) {
					tempShape = new CustomRectangle(input.getMouseX(), input.getMouseY(), 0, 0, colorButton.getColor(), fillShape);
				} else if (activeButton == selectionButton.get("circleButton").getId()) {
					tempShape = new CustomEllipse(input.getMouseX(), input.getMouseY(), 0, 0, colorButton.getColor(), fillShape);
				} else if (activeButton == selectionButton.get("triangleButton").getId()) {
					tempShape = new CustomPolygon(new float[]{x, y, x, y, x, y}, colorButton.getColor(), fillShape);
				} else if (activeButton == selectionButton.get("lineButton").getId()) {
					tempShape = new CustomLine(x, y, x, y, colorButton.getColor());
				} else if (activeButton == selectionButton.get("eyedropperButton").getId()) {
					colorButton.setColor(eyedropperColor);
				} else if (activeButton == selectionButton.get("textButton").getId() && textField == null && x < drawableAreaMaxX) {

					//Create a temporary textField to type the text -> is removed when enter is pressed
					textField = new TextField(gc, trueTypeFont, input.getMouseX(), input.getMouseY(), 720, 50);
					textField.setBackgroundColor(Color.transparent);
					textField.setBorderColor(Color.transparent);

				} else if (activeButton == selectionButton.get("brushButton").getId()) {
					tempShape = new Brush(10, colorButton.getColor());
				} else if (activeButton == selectionButton.get("moveShapeButton").getId() && x < drawableAreaMaxX) {

					//Select the fist shape
					for (int i = 0; i < layer.get(activeLayer).size(); i++) {
						if (layer.get(activeLayer).getShape().get(i).contains(xInput, yInput)) {
							indexSelectedShape = i;
						}
					}

				} else if (activeButton == selectionButton.get("eraserButton").getId()) {
					tempShape = new Brush(10, Color.white);
				}
			}
		}

		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

			//Update the rectangle, circle and triangle while not int the layer
			if (tempShape != null) {
				tempShape.update(gc, sb, delta);
			}

			if (activeButton == selectionButton.get("moveShapeButton").getId()) {
				//Move the shape
				if (indexSelectedShape != null) {
					layer.get(activeLayer).getShape().get(indexSelectedShape).move(gc, x, y);
					x = xInput;
					y = yInput;
				}
			}
		}

		//Add the text to the layer
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			if (activeButton == selectionButton.get("textButton").getId() && textField != null && tempShape == null) {
				if (textField.getText() != "") {
					tempShape = new CustomText(textField.getX(), textField.getY(), textField.getText(), colorButton.getColor());
					layer.get(activeLayer).addToLayer(tempShape);
					tempShape = null;
				}
			}
			textField = null;
		}

		//Remove last added shape (CTRL+Z) -> doesn't work if keep z down
		if (input.isKeyDown(Input.KEY_LCONTROL)) {
			if (input.isKeyPressed(Input.KEY_Z)) {
				//if a text field exist -> first delete it
				if (textField != null) {
					textField = null;
				} else if (layer.get(activeLayer).size() != 0) {
					int index = layer.get(activeLayer).getLastIndex();
					layer.get(activeLayer).removeFromLayer(index);
				}
			}
		}

		if (input.isKeyPressed(Input.KEY_DELETE)) {
			if (layer.size() != 1) {
				//Remove layer + button associated with it
				layer.remove(activeLayer);
				layerButton.remove(activeLayer);

				for (int i = activeLayer; i < layer.size(); i++) {
					layerButton.get(i).setId(layerButton.get(i).getId() - 1);
					if (i - 1 >= 0) {
						layerButton.get(i).setY(layerButton.get(i - 1).getRect().getMaxY() + 10);
					} else {
						layerButton.get(i).setY(buttonWidthHeight + buttonMargin);
					}
				}

				if (activeLayer != 0) {
					//Set layer beneath it active
					setLayerActive(layerButton.get(activeLayer - 1));
				} else {
					//If delete first layer -> activeLayer stay the same
					setLayerActive(layerButton.get(activeLayer));
				}
			}
		}
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			//When key no longer down -> add shape to the layer
			if (activeButton != selectionButton.get("moveShapeButton").getId() && tempShape != null) {
				layer.get(activeLayer).addToLayer(tempShape);
				tempShape = null;
			} else {
				indexSelectedShape = null;
			}
		}
	}

	private void setActive(Button button) {
		activeButton = button.getId();
		button.setColor(new Color(175, 175, 175));
		button.setActive(true);

		fillShape = true;
		if (activeButton == selectionButton.get("rectangleButton").getId() || activeButton == selectionButton.get("triangleButton").getId() || activeButton == selectionButton.get("circleButton").getId()) {
			fillShapeButton.setActive(true);
			fillShapeButton.setColor(new Color(175, 175, 175));
			fillShapeButton.setSize(buttonWidthHeight, buttonWidthHeight);
		} else {
			fillShapeButton.setSize(0, 0);
		}

		for (Map.Entry<String, Button> e : selectionButton.entrySet()) {
			if(e.getValue().getId() != activeButton) {
				e.getValue().setColor(Color.black);
				e.getValue().setActive(false);
			}
		}
	}

	private void setLayerActive(TextButton button) {
		button.setColor(new Color(75, 75, 75));
		activeLayer = button.getId() - 1;
		for (int i = 0; i < layerButton.size(); i++) {
			if (layerButton.get(i).getId() - 1 != activeLayer) {
				layerButton.get(i).setColor(new Color(150, 150, 150));
			}
		}
	}

	private void saveImage(GameContainer gc) throws SlickException {
		Image screenShot = new Image(gc.getWidth() - (widthToolsMenu + widthLayerMenu) - 1, gc.getHeight() - 1);
		gc.getGraphics().copyArea(screenShot, 0, 1);
		String imageName = JOptionPane.showInputDialog("Veuillez taper le nom de l'image \n (le même nom écrasera l'ancienne image)");

		if (imageName != null) {
			ImageOut.write(screenShot, imageName + ".png");
		}

		screenShot.destroy();
	}

	private void showColorMenu() {
		colorMenu = new Rectangle(toolsMenu.getX() - widthColorMenu - 10, buttonWidthHeight, widthColorMenu, heightColorMenu);
		colorMenu.setSize(widthColorMenu, heightColorMenu);
		colorSelectionButton.get("redButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("orangeButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("yellowButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("greenButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("blueButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("purpleButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("grayButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
		colorSelectionButton.get("blackButton").getRect().setSize(buttonWidthHeight, buttonWidthHeight);
	}

	private void hideOrCreateColorMenu() {
		colorMenu = new Rectangle(toolsMenu.getX() - widthColorMenu - 10, buttonWidthHeight, 0, 0);
		colorSelectionButton.put("redButton", new TextButton("", 0, 0, colorMenu.getX() + buttonMargin, buttonWidthHeight + buttonMargin, 208, Color.red, false));
		colorSelectionButton.put("orangeButton", new TextButton("", 0, 0, colorSelectionButton.get("redButton").getX() + buttonWidthHeight + buttonMargin, buttonWidthHeight + buttonMargin, 201, Color.orange, false));
		colorSelectionButton.put("yellowButton", new TextButton("", 0, 0, colorSelectionButton.get("orangeButton").getX() + buttonWidthHeight + buttonMargin, buttonWidthHeight + buttonMargin, 202, Color.yellow, false));
		colorSelectionButton.put("greenButton", new TextButton("", 0, 0, colorMenu.getX() + buttonMargin, 2*buttonWidthHeight + 2*buttonMargin, 203, Color.green, false));
		colorSelectionButton.put("blueButton", new TextButton("", 0, 0, colorSelectionButton.get("greenButton").getX() + buttonWidthHeight + buttonMargin, 2*buttonWidthHeight + 2*buttonMargin, 204, Color.blue, false));
		colorSelectionButton.put("purpleButton", new TextButton("", 0, 0, colorSelectionButton.get("blueButton").getX() + buttonWidthHeight + buttonMargin, buttonWidthHeight + 2*buttonMargin + buttonWidthHeight, 205, new Color(128, 0, 128), false));
		colorSelectionButton.put("grayButton", new TextButton("", 0, 0, colorSelectionButton.get("purpleButton").getX() + buttonWidthHeight + buttonMargin, buttonWidthHeight + 2 * buttonMargin + buttonWidthHeight, 206, Color.gray, false));
		colorSelectionButton.put("blackButton", new TextButton("", 0, 0, colorSelectionButton.get("yellowButton").getX() + buttonWidthHeight + buttonMargin, buttonWidthHeight + buttonMargin, 207, Color.black, false));
	}

	@Override
	public int getID() {
		return 2;
	}
}
