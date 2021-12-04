package si.vilfa.junglechronicles.Level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Level.Scene.Scene;
import si.vilfa.junglechronicles.Level.Scene.SimpleScene;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Level
 **/
public abstract class GameLevel extends GameComponent implements Scene
{
    protected final SimpleScene scene;
    protected final Vector2 playerSpawn;

    public GameLevel(SimpleScene scene, Vector2 playerSpawn)
    {
        super(0, true);
        this.scene = scene;
        this.playerSpawn = playerSpawn;
    }

    public Vector2 getPlayerSpawn()
    {
        return playerSpawn;
    }

    public void setPlayerSpawn(Vector2 playerSpawn)
    {
        this.playerSpawn.set(playerSpawn);
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
