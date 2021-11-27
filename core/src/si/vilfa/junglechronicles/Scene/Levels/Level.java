package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Scene.SimpleScene;

/**
 * @author luka
 * @date 11/11/2021
 * @package si.vilfa.junglechronicles.Scene.Levels
 **/
public class Level extends GameLevel
{
    public Level()
    {
        super(new SimpleScene(), new Vector2(0, 0));
    }

    @Override
    public void update()
    {
        if (!isUpdatable) { return; }

        scene.update();
    }

    @Override
    public void dispose()
    {
        scene.dispose();
    }
}
