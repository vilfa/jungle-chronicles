package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.utils.JsonValue;
import si.vilfa.junglechronicles.Scene.Objects.GameObjectFactory;

/**
 * @author luka
 * @date 26/11/2021
 * @package si.vilfa.junglechronicles.Scene.Levels
 **/
public class LevelFactory
{
    private static LevelFactory INSTANCE;
    private final GameObjectFactory gameObjectFactory;

    private LevelFactory(GameObjectFactory gameObjectFactory)
    {
        this.gameObjectFactory = gameObjectFactory;
    }

    public static LevelFactory getInstance(GameObjectFactory gameObjectFactory)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new LevelFactory(gameObjectFactory);
        }

        return INSTANCE;
    }

    public Level createEmptyLevel()
    {
        return new Level();
    }

    public Level createLevelWithSpec(JsonValue jsonValue)
    {
        return new Level();
    }
}
