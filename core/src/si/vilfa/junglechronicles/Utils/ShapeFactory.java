package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

/**
 * @author luka
 * @date 03/12/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class ShapeFactory implements Loggable
{
    private static ShapeFactory INSTANCE;

    private ShapeFactory() { }

    public static ShapeFactory getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ShapeFactory();
        }
        return INSTANCE;
    }

    public PolygonShape createPlayerShape(Vector2 box)
    {
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(box.x * 0.5f, box.y * 0.5f);
        return rectangleShape;
    }

    public PolygonShape createRectangleShape(RectangleMapObject rectangleMapObject,
                                             Vector2 position)
    {
        Rectangle rectangle = rectangleMapObject.getRectangle();
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(PhysicsEngine.toUnits(rectangle.getWidth() * 0.5f),
                                PhysicsEngine.toUnits(rectangle.getHeight() * 0.5f));

        position.set(new Vector2(rectangle.getX() + rectangle.getWidth() * 0.5f,
                                 rectangle.getY() + rectangle.getHeight() * 0.5f));
        return rectangleShape;
    }

    public PolygonShape createPolygonShape(PolygonMapObject polygonMapObject, Vector2 position)
    {
        Polygon polygon = polygonMapObject.getPolygon();
        float[] vertices = polygon.getVertices();
        for (int i = 0; i < vertices.length; i++)
        {
            vertices[i] = PhysicsEngine.toUnits(vertices[i]);
        }

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices);

        position.set(polygon.getX(), polygon.getY());
        return polygonShape;
    }

    public CircleShape createCircleShape(CircleMapObject circleMapObject, Vector2 position)
    {
        Circle circle = circleMapObject.getCircle();
        Vector2 circlePosition = new Vector2(circle.x, circle.y);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(PhysicsEngine.toUnits(circle.radius));
        position.set(circlePosition);
        return circleShape;
    }

    public CircleShape createEllipseShape(EllipseMapObject ellipseMapObject, Vector2 position)
    {
        Ellipse ellipse = ellipseMapObject.getEllipse();
        Vector2 ellipsePosition = new Vector2(ellipse.x, ellipse.y);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(PhysicsEngine.toUnits(ellipse.width / 2));
        position.set(ellipsePosition);
        return circleShape;
    }
}
