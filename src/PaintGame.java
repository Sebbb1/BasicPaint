import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PaintGame extends StateBasedGame {
	
	public static int HEIGHT = 1280;
	public static int WIDTH = 720;
	
	public PaintGame(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new Menu());
		this.addState(new MainWindow());
	}
	
	public static void main(String[] args) {

		if ((double)HEIGHT/WIDTH == (double)16/9) {
			try {
				AppGameContainer app = new AppGameContainer(new PaintGame("Paint"));
				app.setDisplayMode(HEIGHT, WIDTH, false);
				app.setShowFPS(false);
				app.setTargetFrameRate(60);
				app.setIcons(new String[] { "res/icon32.png", "res/icon16.png" });
				app.start();
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ERROR: Wrong aspect ratio.");
		}
    }
}
