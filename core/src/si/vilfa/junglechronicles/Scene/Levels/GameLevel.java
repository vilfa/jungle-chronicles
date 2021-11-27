package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Scene.Scene;
import si.vilfa.junglechronicles.Scene.SimpleScene;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Scene.Levels
 **/
public abstract class GameLevel extends GameComponent implements Scene
{
    protected SimpleScene scene;
    protected Vector2 playerSpawn;
    protected Array<Vector2> collectiblesSpawn;

    public GameLevel(SimpleScene scene, Vector2 playerSpawn)
    {
        super(0, true);
        this.scene = scene;
        this.playerSpawn = playerSpawn;
        this.collectiblesSpawn = new Array<>();
    }

    @Override
    public abstract void update();

    @Override
    public abstract void dispose();

    @Override
    public void addItem(Object item)
    {
        scene.addItem(item);
    }

    @Override
    public void addItems(Array<Object> items)
    {
        scene.addItems(items);
    }

    @Override
    public void removeItem(Object item)
    {
        scene.removeItem(item);
    }

    @Override
    public Array<Object> getItems()
    {
        return scene.getItems();
    }

    @Override
    public void clear()
    {
        scene.clear();
    }
}
