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

    public enum LevelMapLayer
    {
        OBJECT_LAYER("Objects"), TERRAIN_LAYER("Terrain"), BACKGROUND_LAYER("Background");

        final String layerName;

        LevelMapLayer(String layerName)
        {
            this.layerName = layerName;
        }

        public String getLayerName()
        {
            return layerName;
        }
    }

    public enum LevelObjectProperty
    {
        COLLECTIBLE("isCollectible"),
        COLLECTIBLE_POINTS("collectiblePoints"),
        TRAP("isTrap"),
        TRAP_HEALTH_POINTS("trapHealthPoints");

        final String propertyName;

        LevelObjectProperty(String propertyName)
        {
            this.propertyName = propertyName;
        }

        public String getPropertyName()
        {
            return propertyName;
        }
    }
}
