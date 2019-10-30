import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public class CustomRectangle extends CustomShape {

    private float width;
    private float height;

    public CustomRectangle(float x, float y, float width, float height, Color color, boolean fill) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.fill = fill;
        this.shape = new Rectangle(x, y, width, height);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {

        Input input = gc.getInput();
        float xSize = input.getMouseX() - shape.getX();
        float ySize = input.getMouseY() - shape.getY();

        //Make a square or a rectangle
        if (input.isKeyDown(Input.KEY_LSHIFT)) {
            ((Rectangle)shape).setSize(xSize, xSize);
        } else {
            ((Rectangle)shape).setSize(xSize, ySize);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        if (x > shape.getMinX() && x < shape.getMaxX() && y > shape.getMinY() && y < getMaxY()) {
            return true;
        }
        return false;
    }
}
