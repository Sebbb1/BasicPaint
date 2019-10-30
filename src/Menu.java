import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.Font;

public class Menu extends BasicGameState {

	private TextButton buttonNew, buttonExit;
	private Font fontButton;
	private Image background;
	private TrueTypeFont trueTypeFontButton;

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		buttonNew = new TextButton("New", gc.getWidth()/6, gc.getHeight()/14, gc.getWidth()/2 - gc.getWidth()/6/2, gc.getHeight()/2 - 15, 0, Color.black, true);
		buttonExit = new TextButton("Exit", gc.getWidth()/6, gc.getHeight()/14, gc.getWidth()/2 - gc.getWidth()/6/2, gc.getHeight()/2 + 60, 1, Color.black, true);
		background = new Image("res/background_welcome_screen.png");

		fontButton = new Font("Product Sans", Font.PLAIN, 17);
		trueTypeFontButton = new TrueTypeFont(fontButton, true);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		g.setFont(trueTypeFontButton);
		buttonNew.render(gc, g);
		buttonExit.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) throws SlickException {
		Input input = gc.getInput();
		
		buttonNew.hover(input);
		if (buttonNew.clickListener(input)) {
			sb.enterState(2);
		}

		buttonExit.hover(input);
		if (buttonExit.clickListener(input)) {
			gc.exit();
		}

	}

	@Override
	public int getID() {
		return 1;
	}

}
