package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.TerrainBlock;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Player;

/**
 * @author luka
 * @date 26/11/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class LevelFactory implements Loggable
{
    private static LevelFactory INSTANCE;

    private LevelFactory() { }

    public static LevelFactory getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new LevelFactory();
        }

        return INSTANCE;
    }

    public <T extends Player> Player createPlayer(GameState gameState,
                                                  Class<T> playerType,
                                                  Vector2 position)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        PolygonShape shape = shapeFactory.createPlayerShape(new Vector2(0.75f, 1f));
        Body body = bodyFactory.createWithShapeWithParams(shape,
                                                          BodyDef.BodyType.DynamicBody,
                                                          position,
                                                          65f,
                                                          0f,
                                                          0f);
        body.setFixedRotation(true);

        T player = gameObjectFactory.createWithBody(body, playerType, Body.class);
        player.setGameState(gameState);
        gameState.getCurrentLevel().addItem(player);

        return player;
    }

    public Level createLevelFromTmx(GameState gameState, String fileName)
    {
        //        ExternalFileHandleResolver fileHandleResolver = new ExternalFileHandleResolver();
        TiledMap map = new TmxMapLoader().load(fileName);
        Level level = new Level(map);

        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        for (MapLayer layer : map.getLayers())
        {
            for (MapObject object : layer.getObjects())
            {
                if (object instanceof RectangleMapObject)
                {
                    Vector2 position = new Vector2();
                    PolygonShape rectangleShape
                            = shapeFactory.createRectangleShape(((RectangleMapObject) object),
                                                                position);

                    Body body = bodyFactory.createWithShape(rectangleShape,
                                                            BodyDef.BodyType.StaticBody,
                                                            position);
                    TerrainBlock gameObject = gameObjectFactory.createWithBody(body,
                                                                               TerrainBlock.class,
                                                                               Body.class);
                    gameObject.setPosition(PhysicsEngine.toUnits(position));
                    body.getFixtureList().get(0).setUserData(gameObject);
                    level.addItem(gameObject);
                } else if (object instanceof PolygonMapObject)
                {
                    Vector2 position = new Vector2();
                    PolygonShape polygonShape
                            = shapeFactory.createPolygonShape(((PolygonMapObject) object),
                                                              position);

                    Body body = bodyFactory.createWithShape(polygonShape,
                                                            BodyDef.BodyType.StaticBody,
                                                            position);
                    TerrainBlock gameObject = gameObjectFactory.createWithBody(body,
                                                                               TerrainBlock.class,
                                                                               Body.class);
                    gameObject.setPosition(PhysicsEngine.toUnits(position));
                    body.getFixtureList().get(0).setUserData(gameObject);
                    level.addItem(gameObject);
                } else if (object instanceof CircleMapObject)
                {
                    Vector2 position = new Vector2();
                    CircleShape circleShape
                            = shapeFactory.createCircleShape(((CircleMapObject) object), position);

                    Body body = bodyFactory.createWithShape(circleShape,
                                                            BodyDef.BodyType.StaticBody,
                                                            position);
                    TerrainBlock gameObject = gameObjectFactory.createWithBody(body,
                                                                               TerrainBlock.class,
                                                                               Body.class);
                    gameObject.setPosition(PhysicsEngine.toUnits(position));
                    body.getFixtureList().get(0).setUserData(gameObject);
                    level.addItem(gameObject);
                }
            }
        }
        return level;
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
