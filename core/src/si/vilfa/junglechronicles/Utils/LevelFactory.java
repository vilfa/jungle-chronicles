package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.*;
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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.GameStateEvent;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.AI.AiPlayer;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.AI.Friend;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
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

    public <T extends Player> Player createPlayerWithShape(GameState gameState,
                                                           Class<T> playerType,
                                                           Vector2 position,
                                                           Shape shape)
    {
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

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

    public <T extends Player> Player createPlayerWithShapeWithExtra(GameState gameState,
                                                                    Class<T> playerType,
                                                                    Vector2 position,
                                                                    Shape shape,
                                                                    float density,
                                                                    float friction,
                                                                    float restitution)
    {
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        Body body = bodyFactory.createWithShapeWithParams(shape,
                                                          BodyDef.BodyType.DynamicBody,
                                                          position,
                                                          density,
                                                          friction,
                                                          restitution);
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

        MapLayers layers = map.getLayers();

        if (layers.get(Level.MapLayer.TERRAIN_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.TERRAIN_LAYER.getLayerName());
            if (layer instanceof TiledMapTileLayer)
            {
                createTerrainLayer(gameState, (TiledMapTileLayer) layer);
            } else
            {
                log("Error: expected a tile layer as a background layer");
            }
        }
        if (layers.get(Level.MapLayer.OBJECT_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.OBJECT_LAYER.getLayerName());
            createObjectLayer(gameState, layer);
        }
        if (layers.get(Level.MapLayer.BACKGROUND_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.BACKGROUND_LAYER.getLayerName());
            if (layer instanceof TiledMapImageLayer)
            {
                createBackgroundLayer(gameState, (TiledMapImageLayer) layer);
            } else
            {
                log("Error: expected an image layer as a background layer");
            }
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

    private void processPlayerLayer(GameState gameState, com.badlogic.gdx.maps.MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();

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
            if (shape != null)
            {
                HumanPlayer player = (HumanPlayer) createPlayerWithShapeWithExtra(gameState,
                                                                                  HumanPlayer.class,
                                                                                  PhysicsEngine.toUnits(
                                                                                          position),
                                                                                  shape,
                                                                                  65f,
                                                                                  0.05f,
                                                                                  0.01f);
                HashMap<Level.Property, Object> properties = getObjectProperties(object,
                                                                                 Level.HumanPlayerProperty.values());
                gameState.setPlayer(player);
                for (Level.Property key : properties.keySet())
                {
                    if (key == Level.HumanPlayerProperty.HEALTH_POINTS)
                    {
                        gameState.setPlayerHealth((Integer) properties.get(key));
                    }
                }
            }
        }
    }

    private void processAiLayer(GameState gameState, com.badlogic.gdx.maps.MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
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
                AiPlayer aiPlayer = null;
                HashMap<Level.Property, Object> aiPlayerType = getObjectProperties(object,
                                                                                   Level.AiPlayerType.values());

                for (Level.Property key : aiPlayerType.keySet())
                {
                    if (key == Level.AiPlayerType.ENEMY && (Boolean) aiPlayerType.get(key))
                    {
                        aiPlayer = (Enemy) createPlayerWithShapeWithExtra(gameState,
                                                                          Enemy.class,
                                                                          PhysicsEngine.toUnits(
                                                                                  position),
                                                                          shape,
                                                                          15f,
                                                                          0.05f,
                                                                          0f);
                    } else if (key == Level.AiPlayerType.FRIEND && (Boolean) aiPlayerType.get(key))
                    {
                        aiPlayer = (Friend) createPlayerWithShapeWithExtra(gameState,
                                                                           Friend.class,
                                                                           PhysicsEngine.toUnits(
                                                                                   position),
                                                                           shape,
                                                                           15f,
                                                                           0.05f,
                                                                           0f);
                    }
                }

                if (aiPlayer == null) continue;

                HashMap<Level.Property, Object> aiPlayerProperties = getObjectProperties(object,
                                                                                         Level.AiPlayerProperty.values());
                for (Level.Property key : aiPlayerProperties.keySet())
                {
                    if (key == Level.AiPlayerProperty.ARRIVE
                        && (Boolean) aiPlayerProperties.get(key))
                    {
                        aiPlayer.setBehaviour(new Arrive<>(aiPlayer, gameState.getPlayer()));
                        aiPlayer.getBehaviour().setLimiter(aiPlayer);
                    } else if (key == Level.AiPlayerProperty.PURSUE
                               && (Boolean) aiPlayerProperties.get(key))
                    {
                        aiPlayer.setBehaviour(new Pursue<>(aiPlayer, gameState.getPlayer()));
                        aiPlayer.getBehaviour().setLimiter(aiPlayer);
                    } else if (key == Level.AiPlayerProperty.EVADE
                               && (Boolean) aiPlayerProperties.get(key))
                    {
                        aiPlayer.setBehaviour(new Evade<>(aiPlayer, gameState.getPlayer()));
                        aiPlayer.getBehaviour().setLimiter(aiPlayer);
                    } else if (key == Level.AiPlayerProperty.FACE
                               && (Boolean) aiPlayerProperties.get(key))
                    {
                        aiPlayer.setBehaviour(new Face<>(aiPlayer, gameState.getPlayer()));
                        aiPlayer.getBehaviour().setLimiter(aiPlayer);
                    } else if (key == Level.AiPlayerProperty.WANDER
                               && (Boolean) aiPlayerProperties.get(key))
                    {
                        aiPlayer.setBehaviour(new Wander<>(aiPlayer));
                        aiPlayer.getBehaviour().setLimiter(aiPlayer);
                    }

                    if (aiPlayer.getBehaviour() instanceof Arrive)
                    {
                        if (key == Level.AiPlayerProperty.ARRIVE_DECELERATION_RADIUS
                            && aiPlayerProperties.get(key) != null)
                        {
                            Arrive<Vector2> agentBehaviour = (Arrive<Vector2>) aiPlayer.getBehaviour();
                            agentBehaviour.setDecelerationRadius((Float) aiPlayerProperties.get(key));
                        }
                        if (key == Level.AiPlayerProperty.ARRIVE_TOLERANCE
                            && aiPlayerProperties.get(key) != null)
                        {
                            Arrive<Vector2> agentBehaviour = (Arrive<Vector2>) aiPlayer.getBehaviour();
                            agentBehaviour.setArrivalTolerance((Float) aiPlayerProperties.get(key));
                        }
                    }
                }
            }
        }
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

    private void createObjectLayer(GameState gameState, com.badlogic.gdx.maps.MapLayer layer)
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
                //                gameObject.setPosition(PhysicsEngine.toUnits(position));
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
