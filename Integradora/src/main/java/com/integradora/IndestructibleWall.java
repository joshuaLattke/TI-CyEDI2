package  com.integradora;
import com.integradora.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class IndestructibleWall extends Wall {
    public IndestructibleWall(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK); // Color de la pared indestructible
        gc.fillRect(x, y, width, height);
    }

    @Override
    public boolean isDestroyed() {
        return false; // Las paredes indestructibles no se pueden destruir
    }
}
