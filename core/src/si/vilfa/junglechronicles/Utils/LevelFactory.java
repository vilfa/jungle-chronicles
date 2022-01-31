package si.vilfa.junglechronicles.Utils;

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
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.BackgroundSceneTile;
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

    public Level createLevelFromTmx(Game game, String fileName)
    {
        TiledMap map = new TmxMapLoader().load(fileName);
        Level level = new Level(map);
        game.setCurrentLevel(level);

        MapLayers layers = map.getLayers();

        if (layers.get(Level.MapLayer.TERRAIN_LAYER.getLayerName()) != null)
        {
            for (MapLayer layer : layers)
            {
                if (layer.getName().contains(Level.MapLayer.COLLECTIBLE_LAYER.getLayerName()))
                {
                    if (layer.isVisible())
                    {
                        createTerrainLayer(game, layer, Level.MapLayer.COLLECTIBLE_LAYER);
                    }
                } else if (layer.getName().contains(Level.MapLayer.TERRAIN_LAYER.getLayerName()))
                {
                    if (layer.isVisible())
                    {
                        createTerrainLayer(game, layer, Level.MapLayer.TERRAIN_LAYER);
                    }
                }
            }
        }
        if (layers.get(Level.MapLayer.OBJECT_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.OBJECT_LAYER.getLayerName());
            if (layer.isVisible()) createObjectLayer(game, layer);
        }
        if (layers.get(Level.MapLayer.BACKGROUND_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.BACKGROUND_LAYER.getLayerName());
            if (layer.isVisible())
            {
                createBackgroundLayer(game,
                                      (TiledMapImageLayer) layer,
                                      Level.MapLayer.BACKGROUND_LAYER);
            }
        }
        if (layers.get(Level.MapLayer.PLAYER_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.PLAYER_LAYER.getLayerName());
            if (layer.isVisible()) processPlayerLayer(game, layer);
        }
        if (layers.get(Level.MapLayer.AI_LAYER.getLayerName()) != null)
        {
            MapLayer layer = layers.get(Level.MapLayer.AI_LAYER.getLayerName());
            if (layer.isVisible()) processAiLayer(game, layer);
        }

        return level;
    }

    private void processPlayerLayer(Game game, MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        PlayerFactory playerFactory = PlayerFactory.getInstance();

        if (layer.getObjects().getCount() > 1)
        {
            error("expected a single human player.");
        }

        log("process player layer:" + layer.getName());

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

            HumanPlayer player = (HumanPlayer) playerFactory.createPlayerWithShapeWithExtra(game,
                                                                                            HumanPlayer.class,
                                                                                            PhysicsEngine.toUnits(
                                                                                                    position),
                                                                                            shape,
                                                                                            65f,
                                                                                            0f,
                                                                                            0.01f);

            playerFactory.setupPlayer(player,
                                      object,
                                      game,
                                      getObjectProperties(object,
                                                          Level.HumanPlayerProperty.values()));
        }
    }

    private void processAiLayer(Game game, MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        PlayerFactory playerFactory = PlayerFactory.getInstance();

        log("process ai layer:" + layer.getName());

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
                    aiPlayer = (Enemy) playerFactory.createPlayerWithShapeWithExtra(game,
                                                                                    Enemy.class,
                                                                                    PhysicsEngine.toUnits(
                                                                                            position),
                                                                                    shape,
                                                                                    5f,
                                                                                    0.05f,
                                                                                    0f);
                } else if (Level.AiPlayerType.FRIEND.equals(entry.getKey()))
                {
                    aiPlayer = (Friend) playerFactory.createPlayerWithShapeWithExtra(game,
                                                                                     Friend.class,
                                                                                     PhysicsEngine.toUnits(
                                                                                             position),
                                                                                     shape,
                                                                                     5f,
                                                                                     0.05f,
                            //                                                                                     0f);
                                                                                     0.15f);
                }
            }

            if (aiPlayer == null) continue;

            HashMap<Level.Property, Object> props = getObjectProperties(object,
                                                                        Level.AiPlayerProperty.values());
            playerFactory.setupPlayer(aiPlayer, object, game, props);
        }
    }

    private void createTerrainLayer(Game game, MapLayer layer, Level.MapLayer sourceLayer)
    {
        if (!(layer instanceof TiledMapTileLayer))
        {
            error("expected a tile layer as a terrain layer");
            return;
        }

        log("process terrain layer:" + layer.getName());
        TiledMapTileLayer l = (TiledMapTileLayer) layer;
        for (int i = 0; i < l.getWidth(); i++)
        {
            for (int j = 0; j < l.getHeight(); j++)
            {
                TiledMapTileLayer.Cell cell = l.getCell(i, j);
                if (cell == null) continue;

                SceneTile sceneTile = new SceneTile(i, j, l, cell.getTile());
                sceneTile.setGame(game);
                sceneTile.setSourceLayer(sourceLayer);
                game.getCurrentLevel().addItem(sceneTile);
            }
        }
    }

    private void createObjectLayer(Game game, MapLayer layer)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        BodyFactory bodyFactory = BodyFactory.getInstance(game);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        log("process object layer:" + layer.getName());

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
                body.getFixtureList().first().setUserData(gameObject);
                gameObject.setProperties(getObjectProperties(object,
                                                             Level.ObjectProperty.values()));
                gameObject.setGame(game);
                game.getCurrentLevel().addItem(gameObject);
            }
        }
    }

    private void createBackgroundLayer(Game game,
                                       TiledMapImageLayer layer,
                                       Level.MapLayer sourceLayer)
    {
        if (layer.getTextureRegion() != null)
        {
            log("process background layer:" + layer.getName());
            BackgroundSceneTile backgroundTile = new BackgroundSceneTile(layer);
            backgroundTile.setSourceLayer(sourceLayer);
            game.getCurrentLevel().addItem(backgroundTile);
        } else
        {
            error("empty background layer");
        }
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
}
