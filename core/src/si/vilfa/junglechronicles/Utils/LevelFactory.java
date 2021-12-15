package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.AI.AiPlayer;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.AI.Friend;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

import java.util.HashMap;
import java.util.Map;

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

    public Level createLevelFromTmx(GameState gameState, String fileName)
    {
        TiledMap map = new TmxMapLoader().load(fileName);
        Level level = new Level(map);
        gameState.setCurrentLevel(level);

        MapLayers layers = map.getLayers();

        if (layers.get(Level.MapLayer.TERRAIN_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.TERRAIN_LAYER.getLayerName());
            createTerrainLayer(gameState, layer);
        }
        if (layers.get(Level.MapLayer.OBJECT_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.OBJECT_LAYER.getLayerName());
            createObjectLayer(gameState, layer);
        }
        if (layers.get(Level.MapLayer.BACKGROUND_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.BACKGROUND_LAYER.getLayerName());
            createBackgroundLayer(gameState, (TiledMapImageLayer) layer);
        }
        if (layers.get(Level.MapLayer.PLAYER_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.PLAYER_LAYER.getLayerName());
            processPlayerLayer(gameState, layer);
        }
        if (layers.get(Level.MapLayer.AI_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.AI_LAYER.getLayerName());
            processAiLayer(gameState, layer);
        }

        return level;
    }

    private void processPlayerLayer(GameState gameState, MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        PlayerFactory playerFactory = PlayerFactory.getInstance();


        if (layer.getObjects().getCount() > 1)
        {
            log("Error: Expected a single human player.");
        }

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
            } else if (object instanceof EllipseMapObject)
            {
                shape = shapeFactory.createEllipseShape((EllipseMapObject) object, position);
            }

            if (shape == null) continue;

            HumanPlayer player = (HumanPlayer) playerFactory.createPlayerWithShapeWithExtra(
                    gameState,
                    HumanPlayer.class,
                    PhysicsEngine.toUnits(position),
                    shape,
                    65f,
                    0.05f,
                    0.01f);

            playerFactory.setupPlayer(player,
                                      gameState,
                                      getObjectProperties(object,
                                                          Level.HumanPlayerProperty.values()));
        }
    }

    private void processAiLayer(GameState gameState, MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        PlayerFactory playerFactory = PlayerFactory.getInstance();

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
            } else if (object instanceof EllipseMapObject)
            {
                shape = shapeFactory.createEllipseShape((EllipseMapObject) object, position);
            }

            if (shape == null) continue;

            AiPlayer aiPlayer = null;
            HashMap<Level.Property, Object> aiPlayerType = getObjectProperties(object,
                                                                               Level.AiPlayerType.values());

            for (Map.Entry<Level.Property, Object> entry : aiPlayerType.entrySet())
            {
                if (Level.AiPlayerType.ENEMY.equals(entry.getKey()))
                {
                    aiPlayer = (Enemy) playerFactory.createPlayerWithShapeWithExtra(gameState,
                                                                                    Enemy.class,
                                                                                    PhysicsEngine.toUnits(
                                                                                            position),
                                                                                    shape,
                                                                                    5f,
                                                                                    0.05f,
                                                                                    0f);
                } else if (Level.AiPlayerType.FRIEND.equals(entry.getKey()))
                {
                    aiPlayer = (Friend) playerFactory.createPlayerWithShapeWithExtra(gameState,
                                                                                     Friend.class,
                                                                                     PhysicsEngine.toUnits(
                                                                                             position),
                                                                                     shape,
                                                                                     5f,
                                                                                     0.05f,
                                                                                     0f);
                }
            }

            if (aiPlayer == null) continue;

            HashMap<Level.Property, Object> props = getObjectProperties(object,
                                                                        Level.AiPlayerProperty.values());
            playerFactory.setupPlayer(aiPlayer, gameState, props);
        }
    }

    private void createTerrainLayer(GameState gameState, MapLayer layer)
    {
        if (!(layer instanceof TiledMapTileLayer))
        {
            log("Error: expected a tile layer as a terrain layer");
            return;
        }

        TiledMapTileLayer l = (TiledMapTileLayer) layer;
        for (int i = 0; i < l.getHeight(); i++)
        {
            for (int j = 0; j < l.getWidth(); j++)
            {
                TiledMapTileLayer.Cell cell = l.getCell(i, j);
                if (cell == null) continue;

                SceneTile sceneTile = new SceneTile(i, j, l, cell.getTile());
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
            } else if (object instanceof EllipseMapObject)
            {
                shape = shapeFactory.createEllipseShape((EllipseMapObject) object, position);
            }

            if (shape != null)
            {
                Body body = bodyFactory.createWithShape(shape,
                                                        BodyDef.BodyType.StaticBody,
                                                        PhysicsEngine.toUnits(position));
                GameBlock gameObject = gameObjectFactory.createWithBody(body,
                                                                        GameBlock.class,
                                                                        Body.class);
                body.getFixtureList().get(0).setUserData(gameObject);
                gameObject.setProperties(getObjectProperties(object,
                                                             Level.ObjectProperty.values()));
                gameObject.setGameState(gameState);
                gameState.getCurrentLevel().addItem(gameObject);
            }
        }
    }

    private void createBackgroundLayer(GameState gameState, TiledMapImageLayer layer)
    {

    }

    private <T extends MapObject> HashMap<Level.Property, Object> getObjectProperties(T object,
                                                                                      Level.Property[] properties)
    {
        HashMap<Level.Property, Object> objectProperties = new HashMap<>();
        for (Level.Property property : properties)
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
