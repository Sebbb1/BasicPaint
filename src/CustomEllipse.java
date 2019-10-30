import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.state.StateBasedGame;

public class CustomEllipse extends CustomShape {

    private float radius1;
    private float radius2;

    public CustomEllipse(float centerPointX, float centerPointY, float radius1, float radius2, Color color, boolean fill) {
        this.x = centerPointX;
        this.y = centerPointY;
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.color = color;
        this.fill = fill;
        this.shape = new Ellipse(x, y, radius1, radius2);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sb, int delta) {
        Input input = gc.getInput();

        float xSize = (input.getMouseX() - x) / 2;
        float ySize = (input.getMouseY() - y) / 2;

        //Make a Circle or an Ellipse
        if (input.isKeyDown(Input.KEY_LSHIFT)) {
            ((Ellipse) shape).setRadii(xSize, xSize);
        } else {
            ((Ellipse) shape).setRadii(xSize, ySize);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }


}
