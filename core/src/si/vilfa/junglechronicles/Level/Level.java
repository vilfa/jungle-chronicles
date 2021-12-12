package si.vilfa.junglechronicles.Level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Level.Scene.SimpleScene;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Level
 **/
public class Level extends GameLevel
{
    private final TiledMap map;

    public Level(TiledMap map)
    {
        super(new SimpleScene(), new Vector2());
        this.map = map;
    }

    public Level(TiledMap map, Vector2 playerSpawn)
    {
        super(new SimpleScene(), playerSpawn);
        this.map = map;
    }

    public TiledMap getMap()
    {
        return map;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;

        scene.update();
    }

    @Override
    public void dispose()
    {
        scene.dispose();
        map.dispose();
    }

    public enum MapLayer
    {
        OBJECT_LAYER("Objects"),
        TERRAIN_LAYER("Terrain"),
        BACKGROUND_LAYER("Background"),
        PLAYER_LAYER("Player"),
        AI_LAYER("Ai");

        final String layerName;

        MapLayer(String layerName)
        {
            this.layerName = layerName;
        }

        public String getLayerName()
        {
            return layerName;
        }
    }

    public enum ObjectProperty implements Property
    {
        COLLECTIBLE("isCollectible"),
        COLLECTIBLE_POINTS("collectiblePoints"),
        TRAP("isTrap"),
        TRAP_HEALTH_POINTS("trapHealthPoints");

        final String propertyName;

        ObjectProperty(String propertyName)
        {
            this.propertyName = propertyName;
        }

        @Override
        public String getPropertyName()
        {
            return propertyName;
        }
    }

    public enum HumanPlayerProperty implements Property
    {
        HEALTH_POINTS("healthPoints");

        final String propertyName;

        HumanPlayerProperty(String propertyName)
        {
            this.propertyName = propertyName;
        }

        @Override
        public String getPropertyName()
        {
            return propertyName;
        }
    }

    public enum AiPlayerType implements Property
    {
        FRIEND("isFriend"), ENEMY("isEnemy");

        final String propertyName;

        AiPlayerType(String propertyName)
        {
            this.propertyName = propertyName;
        }

        @Override
        public String getPropertyName()
        {
            return propertyName;
        }
    }

    public enum AiPlayerProperty implements Property
    {
        EVADE("isEvade"),
        PURSUE("isPursue"),
        WANDER("isWander"),
        FACE("isFace"),
        ARRIVE("isArrive"),
        ARRIVE_DECELERATION_RADIUS("arriveDecelerationRadius"),
        ARRIVE_TOLERANCE("arriveTolerance"),
        ARRIVE_TTT("arriveTimeToTarget"),
        ENEMY_LEFT_BOUND("enemyLeftBound"),
        ENEMY_RIGHT_BOUND("enemyRightBound"),
        ENEMY_TOP_BOUND("enemyTopBound"),
        ENEMY_BOTTOM_BOUND("enemyBottomBound");

        final String propertyName;

        AiPlayerProperty(String propertyName)
        {
            this.propertyName = propertyName;
        }

        @Override
        public String getPropertyName()
        {
            return propertyName;
        }
    }

    public interface Property
    {
        String getPropertyName();
    }
}
