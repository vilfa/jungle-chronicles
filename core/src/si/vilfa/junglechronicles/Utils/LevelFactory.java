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
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Scene.Objects.CollectibleBlock;
import si.vilfa.junglechronicles.Scene.Objects.TerrainBlock;
import si.vilfa.junglechronicles.Scene.Objects.TrapBlock;
import si.vilfa.junglechronicles.Scene.Objects.TrapBlockType;

/**
 * @author luka
 * @date 26/11/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class LevelFactory implements Loggable
{
    private static LevelFactory INSTANCE;
    private final GameObjectFactory gameObjectFactory;

    private LevelFactory(GameObjectFactory gameObjectFactory)
    {
        this.gameObjectFactory = gameObjectFactory;
    }

    public static LevelFactory getInstance(GameObjectFactory gameObjectFactory)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new LevelFactory(gameObjectFactory);
        }

        return INSTANCE;
    }

    public Level createLevelFromTmx(GameState gameState, String fileName)
    {
        TiledMap map = new TmxMapLoader().load(fileName);
        Level level = new Level(map);

        ShapeFactory shapeFactory = ShapeFactory.getInstance(gameState);
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance(bodyFactory);

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
                    body.getFixtureList().get(0).setUserData(gameObject);
                    level.addItem(gameObject);
                }
            }
        }
        return level;
    }

    public Level createDefaultLevel(GameState gameState)
    {
        Level level = new Level();
        for (int i = 0; i < 200; i++)
        {
            TerrainBlock groundBlock = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(
                                                                                                i,
                                                                                                .5f),
                                                                                        0,
                                                                                        Float.POSITIVE_INFINITY,
                                                                                        0f,
                                                                                        .1f,
                                                                                        new Vector2(
                                                                                                .5f,
                                                                                                .5f),
                                                                                        TerrainBlock.class,
                                                                                        Body.class);
            groundBlock.setGameState(gameState);
            level.addItem(groundBlock);
        }

        for (int i = 1; i < 3; i++)
        {
            TerrainBlock worldBlock
                    = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(i, 5),
                                                                       0,
                                                                       Float.POSITIVE_INFINITY,
                                                                       0f,
                                                                       .1f,
                                                                       new Vector2(.5f, .5f),
                                                                       TerrainBlock.class,
                                                                       Body.class);
            worldBlock.setGameState(gameState);
            level.addItem(worldBlock);
        }

        for (int i = 0; i < 5; i++)
        {
            CollectibleBlock collectibleBlock
                    = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(5 + i, 1.5f),
                                                                       0f,
                                                                       1f,
                                                                       0f,
                                                                       0f,
                                                                       new Vector2(.25f, .25f),
                                                                       CollectibleBlock.class,
                                                                       Body.class);
            collectibleBlock.setGameState(gameState);
            level.addItem(collectibleBlock);
        }

        int i = 0;
        for (TrapBlockType type : TrapBlockType.values())
        {
            TrapBlock trapBlock = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(
                                                                                           11 + 2 * i, 1.5f),
                                                                                   0f,
                                                                                   1f,
                                                                                   0f,
                                                                                   0f,
                                                                                   new Vector2(.5f,
                                                                                               .5f),
                                                                                   TrapBlock.class,
                                                                                   Body.class);
            trapBlock.setGameState(gameState);
            trapBlock.setBlockType(type);
            level.addItem(trapBlock);
            i++;
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
