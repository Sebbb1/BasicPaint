import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public interface Clickable {
    void render(GameContainer gc, Graphics g);
    boolean clickListener(Input input);
}
