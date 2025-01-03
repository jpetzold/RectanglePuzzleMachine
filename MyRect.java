import java.awt.Rectangle;
import java.util.Random;

public class MyRect extends Rectangle implements Cloneable {
    public MyRect(int x, int y, int width, int height) {

            super(x, y, width, height);

    }

    public MyRect clone() {
        return new MyRect(this.x, this.y, this.width, this.height);
    }
}
