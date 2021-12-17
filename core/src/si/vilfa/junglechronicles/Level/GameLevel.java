package si.vilfa.junglechronicles.Level;

import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Level.Scene.BackgroundSceneTile;
import si.vilfa.junglechronicles.Level.Scene.Scene;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Level.Scene.SimpleScene;
import si.vilfa.junglechronicles.Player.Player;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Level
 **/
public abstract class GameLevel extends GameComponent implements Scene
{
    protected final SimpleScene scene;

    public GameLevel(SimpleScene scene)
    {
        super(0, true);
        this.scene = scene;
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
    public void removeItem(Object item)
    {
        scene.removeItem(item);
    }

    @Override
    public Array<GameBlock> getObjects()
    {
        return scene.getObjects();
    }

    @Override
    public Array<SceneTile> getTiles()
    {
        return scene.getTiles();
    }

    @Override
    public Array<Player> getPlayers()
    {
        return scene.getPlayers();
    }

    @Override
    public Array<BackgroundSceneTile> getBackgrounds()
    {
        return scene.getBackgrounds();
    }

    @Override
    public void clear()
    {
        scene.clear();
    }
}
