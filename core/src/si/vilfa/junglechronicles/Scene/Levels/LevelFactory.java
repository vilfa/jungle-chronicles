package si.vilfa.junglechronicles.Scene.Levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.JsonValue;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Scene.Objects.*;

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

    public Level createDefaultLevel(GameState gameState)
    {
        Level level = new Level();
        for (int i = 0; i < 200; i++)
        {
            TerrainBlock groundBlock = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(
                                                                                                i,
                                                                                                .5f),
                                                                                        0,
                                                                                        Float.POSITIVE_INFINITY,
                                                                                        0f,
                                                                                        .1f,
                                                                                        new Vector2(
                                                                                                .5f,
                                                                                                .5f),
                                                                                        TerrainBlock.class,
                                                                                        Body.class);
            groundBlock.setGameState(gameState);
            level.addItem(groundBlock);
        }

        for (int i = 1; i < 3; i++)
        {
            TerrainBlock worldBlock
                    = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(i, 5),
                                                                       0,
                                                                       Float.POSITIVE_INFINITY,
                                                                       0f,
                                                                       .1f,
                                                                       new Vector2(.5f, .5f),
                                                                       TerrainBlock.class,
                                                                       Body.class);
            worldBlock.setGameState(gameState);
            level.addItem(worldBlock);
        }

        for (int i = 0; i < 5; i++)
        {
            CollectibleBlock collectibleBlock
                    = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(5 + i, 1.5f),
                                                                       0f,
                                                                       1f,
                                                                       0f,
                                                                       0f,
                                                                       new Vector2(.25f, .25f),
                                                                       CollectibleBlock.class,
                                                                       Body.class);
            collectibleBlock.setGameState(gameState);
            level.addItem(collectibleBlock);
        }

        int i = 0;
        for (TrapBlockType type : TrapBlockType.values())
        {
            TrapBlock trapBlock = gameObjectFactory.createStaticWithPolygonFixture(new Vector2(
                                                                                           11 + 2 * i, 1.5f),
                                                                                   0f,
                                                                                   1f,
                                                                                   0f,
                                                                                   0f,
                                                                                   new Vector2(.5f,
                                                                                               .5f),
                                                                                   TrapBlock.class,
                                                                                   Body.class);
            trapBlock.setGameState(gameState);
            trapBlock.setBlockType(type);
            level.addItem(trapBlock);
            i++;
        }

        return level;
    }
}
