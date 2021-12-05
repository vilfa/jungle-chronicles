package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.GameStateEvent;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Player;

import java.util.HashMap;

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
        body.getFixtureList().get(0).setUserData(player);

        player.setGameState(gameState);
        player.registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT, gameState);
        player.registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                     gameState.getPhysics());
        player.registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState);
        player.registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getPhysics());

        gameState.getCurrentLevel().addItem(player);

        return player;
    }

    public Level createLevelFromTmx(GameState gameState, String fileName)
    {
        TiledMap map = new TmxMapLoader().load(fileName);
        Level level = new Level(map);
        gameState.setCurrentLevel(level);

        for (MapLayer layer : map.getLayers())
        {
            if (layer.getName().equals(Level.LevelMapLayer.TERRAIN_LAYER.getLayerName()))
            {
                if (layer instanceof TiledMapTileLayer)
                {
                    createTerrainLayer(gameState, (TiledMapTileLayer) layer);
                } else
                {
                    log("Error: expected a tile layer as a terrain layer");
                }
            } else if (layer.getName().equals(Level.LevelMapLayer.OBJECT_LAYER.getLayerName()))
            {
                createObjectLayer(gameState, layer);
            } else if (layer.getName().equals(Level.LevelMapLayer.BACKGROUND_LAYER.getLayerName()))
            {
                if (layer instanceof TiledMapImageLayer)
                {
                    createBackgroundLayer(gameState, (TiledMapImageLayer) layer);
                } else
                {
                    log("Error: expected an image layer as a background layer");
                }
            }
        }
        return level;
    }

    private void createTerrainLayer(GameState gameState, TiledMapTileLayer layer)
    {
        for (int i = 0; i < layer.getHeight(); i++)
        {
            for (int j = 0; j < layer.getWidth(); j++)
            {
                TiledMapTileLayer.Cell cell = layer.getCell(i, j);
                if (cell == null) continue;

                SceneTile sceneTile = new SceneTile(i, j, layer, cell.getTile());
                sceneTile.setGameState(gameState);
                gameState.getCurrentLevel().addItem(sceneTile);
            }
        }
    }

    private void createObjectLayer(GameState gameState, MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        for (MapObject object : layer.getObjects())
        {
            Shape shape = null;
            Vector2 position = new Vector2();
            if (object instanceof RectangleMapObject)
            {
                shape = shapeFactory.createRectangleShape(((RectangleMapObject) object), position);
            } else if (object instanceof PolygonMapObject)
            {
                shape = shapeFactory.createPolygonShape(((PolygonMapObject) object), position);
            } else if (object instanceof CircleMapObject)
            {
                shape = shapeFactory.createCircleShape(((CircleMapObject) object), position);
            }

            if (shape != null)
            {
                Body body = bodyFactory.createWithShape(shape,
                                                        BodyDef.BodyType.StaticBody,
                                                        position);
                GameBlock gameObject = gameObjectFactory.createWithBody(body,
                                                                        GameBlock.class,
                                                                        Body.class);
                gameObject.setPosition(PhysicsEngine.toUnits(position));
                body.getFixtureList().get(0).setUserData(gameObject);

                gameObject.setProperties(getObjectProperties(object));
                gameObject.setGameState(gameState);

                gameState.getCurrentLevel().addItem(gameObject);
            }
        }
    }

    private void createBackgroundLayer(GameState gameState, TiledMapImageLayer layer)
    {

    }

    private <T extends MapObject> HashMap<Level.LevelObjectProperty, Object> getObjectProperties(T object)
    {
        HashMap<Level.LevelObjectProperty, Object> objectProperties = new HashMap<>();
        for (Level.LevelObjectProperty property : Level.LevelObjectProperty.values())
        {
            if (object.getProperties().get(property.getPropertyName()) != null)
            {
                objectProperties.put(property,
                                     object.getProperties().get(property.getPropertyName()));
            }
        }
        return objectProperties;
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
