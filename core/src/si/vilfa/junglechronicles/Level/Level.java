package si.vilfa.junglechronicles.Level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Level.Scene.SimpleScene;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Level
 **/
public class Level extends GameLevel implements EventListener
{
    private final TiledMap map;

    public Level(TiledMap map)
    {
        super(new SimpleScene());
        this.map = map;
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

    @Override
    public void handleEvent(Event event)
    {
        // TODO: 02/01/2022 Implement this
    }

    public enum MapLayer
    {
        OBJECT_LAYER("Objects"),
        TERRAIN_LAYER("Terrain"),
        COLLECTIBLE_LAYER("TerrainCollectible"),
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
        ENEMY_BOTTOM_BOUND("enemyBottomBound"),
        ENEMY_SPRITE_TYPE("enemySpriteType");

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
