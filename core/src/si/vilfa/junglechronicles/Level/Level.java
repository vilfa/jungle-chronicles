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
    }
}
