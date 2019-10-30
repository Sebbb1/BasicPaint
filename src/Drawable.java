import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface Drawable {
    void render(GameContainer gc, Graphics g);
    void update(GameContainer gc, StateBasedGame sb, int delta);
    boolean contains(int x, int y);
    void move(GameContainer gc, float x, float y);
}
