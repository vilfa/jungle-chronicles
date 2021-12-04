package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.google.gson.GsonBuilder;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 03/12/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class ShapeFactory implements Loggable
{
    public static ShapeFactory INSTANCE;
    private final float worldPpm;
    private final float worldPpmMul;

    private ShapeFactory(GameState gameState)
    {
        this.worldPpm = gameState.getPhysics().getWorldPpm();
        this.worldPpmMul = gameState.getPhysics().getWorldPpmMul();
    }

    public static ShapeFactory getInstance(GameState gameState)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ShapeFactory(gameState);
        }
        return INSTANCE;
    }

    public PolygonShape createRectangleShape(RectangleMapObject rectangleMapObject,
                                             Vector2 position)
    {
        Rectangle rectangle = rectangleMapObject.getRectangle();

        rectangle.x *= worldPpmMul;
        rectangle.y *= worldPpmMul;
        rectangle.width *= worldPpmMul;
        rectangle.height *= worldPpmMul;

        Vector2 rectanglePosition = rectangle.getCenter(new Vector2());

//        Vector2 rectanglePosition = new Vector2(rectangle.x + rectangle.width * 0.5f,
//                                                rectangle.y + rectangle.height * 0.5f);
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(rectangle.width * 0.5f,
                                rectangle.height * 0.5f,
                                rectanglePosition,
                                0f);

        position.set(rectanglePosition);

        log(new GsonBuilder().setPrettyPrinting().create().toJson(rectangle));

        return rectangleShape;
    }

    public PolygonShape createPolygonShape(PolygonMapObject polygonMapObject, Vector2 position)
    {
        Polygon polygon = polygonMapObject.getPolygon();

        PolygonShape polygonShape = new PolygonShape();

        Vector2 maxPos = new Vector2(Float.MIN_VALUE, Float.MIN_VALUE);
        Vector2 minPos = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
        float[] vertices = polygon.getVertices();
        for (int i = 0; i < vertices.length; i++)
        {
            vertices[i] *= worldPpmMul;
            if (i % 2 == 0)
            {
                maxPos.x = Math.max(maxPos.x, vertices[i]);
                minPos.x = Math.min(minPos.x, vertices[i]);
            } else
            {
                maxPos.y = Math.max(maxPos.y, vertices[i]);
                minPos.y = Math.min(minPos.y, vertices[i]);
            }
        }
        polygonShape.set(vertices);

//        Vector2 polygonPosition = new Vector2(minPos.x + Math.abs(maxPos.x - minPos.x) * 0.5f,
//                                              minPos.y + Math.abs(maxPos.y - minPos.y) * 0.5f);
        Vector2 polygonPosition = new Vector2(maxPos.sub(polygonShape.getRadius(), polygonShape.getRadius()));

        position.set(polygonPosition);

        log(new GsonBuilder().setPrettyPrinting().create().toJson(polygon));

        return polygonShape;
    }

    public CircleShape createCircleShape(CircleMapObject circleMapObject, Vector2 position)
    {
        Circle circle = circleMapObject.getCircle();
        circle.x *= worldPpmMul;
        circle.y *= worldPpmMul;
        circle.radius *= worldPpmMul;

        Vector2 circlePosition = new Vector2(circle.x, circle.y);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius);

        position.set(circlePosition);

        log(new GsonBuilder().setPrettyPrinting().create().toJson(circle));

        return circleShape;
    }

    @Override
    public String getId()
    {
        return getClass().getSimpleName() + "#" + hashCode();
    }

    @Override
    public void log(String message)
    {
        Gdx.app.debug(getId(), message);
    }
}
