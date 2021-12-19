package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.Scene.BackgroundSceneTile;
import si.vilfa.junglechronicles.Level.Scene.PlayerSceneTile;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class Renderer extends DrawableGameComponent implements WindowAdapter
{
    public static GameTime gameTime;
    private final float visibleWorldWidth;
    private final float visibleWorldHeight;
    private final float worldWidth;
    private final float worldHeight;
    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final GameState gameState;
    private final int screenWidthMax;
    private final int screenHeightMax;
    private final int screenRefreshRate;
    private final HashMap<HumanPlayer.State, PlayerSceneTile> playerAnimations = new HashMap<>();
    private final HashMap<Enemy.EnemySprite, PlayerSceneTile> enemyAnimations = new HashMap<>();
    private int screenWidth;
    private int screenHeight;
    private float screenAspectRatio;
    private float deltaTime;
    private float fpsTimer;

    public Renderer(GameState gameState)
    {
        super(0, true, 0, true);

        Renderer.gameTime = new GameTime();
        fpsTimer = 0f;
        deltaTime = 0f;

        Graphics.DisplayMode displayMode
                = Gdx.graphics.getDisplayMode(Gdx.graphics.getPrimaryMonitor());
        screenWidth = displayMode.width;
        screenWidthMax = displayMode.width;
        screenHeight = displayMode.height;
        screenHeightMax = displayMode.height;
        screenRefreshRate = displayMode.refreshRate;
        screenAspectRatio = (float) screenWidthMax / (float) screenHeightMax;
        visibleWorldHeight = 13;
        visibleWorldWidth = visibleWorldHeight * screenAspectRatio;
        Gdx.graphics.setForegroundFPS(screenRefreshRate);

        this.gameState = gameState;
        worldWidth = gameState.getPhysics().getWorldWidth();
        worldHeight = gameState.getPhysics().getWorldHeight();

        viewport = new ExtendViewport(visibleWorldWidth,
                                      visibleWorldHeight,
                                      worldWidth,
                                      worldHeight);
        viewport.apply(true);

        spriteBatch = new SpriteBatch();

        initializePlayerAnimations();
        initializeEnemyAnimations();

        gameState.getCurrentLevel().getBackgrounds().first().setViewport(viewport);
    }

    public Matrix4 getCombined()
    {
        return viewport.getCamera().combined;
    }

    private void initializePlayerAnimations()
    {
        HashMap<HumanPlayer.State, TextureAtlas> texturesByState = new HashMap<>();
        texturesByState.put(HumanPlayer.State.JUMP_LEFT, new TextureAtlas("Player/Jump.atlas"));
        texturesByState.put(HumanPlayer.State.JUMP_RIGHT,
                            texturesByState.get(HumanPlayer.State.JUMP_LEFT));
        texturesByState.put(HumanPlayer.State.IDLE_LEFT, new TextureAtlas("Player/Idle.atlas"));
        texturesByState.put(HumanPlayer.State.IDLE_RIGHT,
                            texturesByState.get(HumanPlayer.State.IDLE_LEFT));
        texturesByState.put(HumanPlayer.State.RUN_LEFT, new TextureAtlas("Player/Run.atlas"));
        texturesByState.put(HumanPlayer.State.RUN_RIGHT,
                            texturesByState.get(HumanPlayer.State.RUN_LEFT));
        texturesByState.put(HumanPlayer.State.SLIDE_LEFT, new TextureAtlas("Player/Slide.atlas"));
        texturesByState.put(HumanPlayer.State.SLIDE_RIGHT,
                            texturesByState.get(HumanPlayer.State.SLIDE_LEFT));

        Vector2 playerScale = new Vector2(1f, 1f);
        if (gameState.getPlayer() != null)
        {
            Vector2 playerBox = gameState.getPlayer().getBox();
            TextureRegion playerRegion = texturesByState.get(HumanPlayer.State.IDLE_LEFT)
                                                        .getRegions()
                                                        .first();
            playerScale.set(playerBox.x / playerRegion.getRegionWidth(),
                            playerBox.y / playerRegion.getRegionHeight());
        }

        for (Map.Entry<HumanPlayer.State, TextureAtlas> entry : texturesByState.entrySet())
        {
            int i = 0;
            TreeMap<Integer, TextureAtlas.AtlasRegion> animationSpec = new TreeMap<>();
            for (TextureAtlas.AtlasRegion region : entry.getValue().getRegions())
            {
                animationSpec.put(i++, region);
            }

            playerAnimations.put(entry.getKey(),
                                 new PlayerSceneTile(animationSpec,
                                                     Animation.PlayMode.LOOP,
                                                     1 / 14f,
                                                     HumanPlayer.State.getLeft()
                                                                      .contains(entry.getKey(),
                                                                                false),
                                                     false,
                                                     playerScale));
        }
    }

    private void initializeEnemyAnimations()
    {
        HashMap<Enemy.EnemySprite, TextureAtlas> texturesByEnemies = new HashMap<>();
        texturesByEnemies.put(Enemy.EnemySprite.ENEMY_ONE, new TextureAtlas("Enemy/Enemy1.atlas"));
        texturesByEnemies.put(Enemy.EnemySprite.ENEMY_TWO, new TextureAtlas("Enemy/Enemy2.atlas"));
        texturesByEnemies.put(Enemy.EnemySprite.ENEMY_THREE,
                              new TextureAtlas("Enemy/Enemy3.atlas"));

        Vector2 enemyScale = new Vector2(1f, 1f);
        if (gameState.getCurrentLevel().getEnemies().size > 0)
        {
            Vector2 enemyBox = gameState.getCurrentLevel().getEnemies().first().getBox();
            TextureRegion enemyRegion = texturesByEnemies.get(Enemy.EnemySprite.ENEMY_ONE)
                                                         .getRegions()
                                                         .first();
            enemyScale.set(enemyBox.x / enemyRegion.getRegionWidth(),
                           enemyBox.y / enemyRegion.getRegionHeight());
        }

        for (Map.Entry<Enemy.EnemySprite, TextureAtlas> entry : texturesByEnemies.entrySet())
        {
            int i = 0;
            TreeMap<Integer, TextureAtlas.AtlasRegion> animationSpec = new TreeMap<>();
            for (TextureAtlas.AtlasRegion region : entry.getValue().getRegions())
            {
                animationSpec.put(i++, region);
            }

            enemyAnimations.put(entry.getKey(),
                                new PlayerSceneTile(animationSpec,
                                                    Animation.PlayMode.LOOP,
                                                    1 / 14f,
                                                    false,
                                                    false,
                                                    enemyScale));
        }
    }

    @Override
    public void resize(int width, int height)
    {
        screenWidth = width;
        screenHeight = height;
        viewport.update(screenWidth, screenHeight, true);
        followPlayer(gameState.getPlayer());
    }

    @Override
    public float getScreenAspectRatio()
    {
        return screenAspectRatio;
    }

    @Override
    public void setScreenAspectRatio(float screenAspectRatio)
    {
        this.screenAspectRatio = screenAspectRatio;
    }

    @Override
    public int getScreenRefreshRate()
    {
        return screenRefreshRate;
    }

    private void followPlayer(Player player)
    {
        Vector2 newCameraPos = new Vector2(player.getPosition());
        if (newCameraPos.x < viewport.getWorldWidth() / 2f)
        {
            newCameraPos.x = viewport.getWorldWidth() / 2f;
        }
        if (newCameraPos.y < viewport.getWorldHeight() / 2f)
        {
            newCameraPos.y = viewport.getWorldHeight() / 2f;
        }
        viewport.getCamera().position.set(newCameraPos, 0f);
        viewport.getCamera().update();
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;

        // TODO: 20/11/2021 Make draw calls use draw order.
        deltaTime = gameTime.getDeltaTime();
        ScreenUtils.clear(1, 1, 1, 1);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        for (BackgroundSceneTile tile : gameState.getCurrentLevel().getBackgrounds())
        {
            // TODO: 17/12/2021 Implement parallax background scrolling

            //            Vector2 parallax = player.getLinearVelocity();
            //            parallax.scl(-.15f * Renderer.gameTime.getDeltaTime());
            tile.draw(spriteBatch);
        }

        for (SceneTile tile : gameState.getCurrentLevel().getTiles())
        {
            tile.draw(spriteBatch);
        }

        enemyAnimations.forEach((k, v) -> v.update());
        for (Player player : gameState.getCurrentLevel().getPlayers())
        {
            if (player instanceof Enemy)
            {
                Enemy enemy = (Enemy) player;
                PlayerSceneTile enemyAnimation = enemyAnimations.get(enemy.getEnemySprite());
                enemyAnimation.setCenter(enemy.getPosition());
                enemyAnimation.draw(spriteBatch);
            }
        }

        HumanPlayer player = gameState.getPlayer();
        PlayerSceneTile playerAnimation = playerAnimations.get(player.getState());
        playerAnimation.update();
        playerAnimation.setCenter(player.getPosition());
        playerAnimation.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        spriteBatch.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;

        deltaTime = gameTime.getDeltaTime();
        fpsTimer += deltaTime;
        if (fpsTimer > 1f)
        {
            log("FPS:" + Gdx.graphics.getFramesPerSecond());
            fpsTimer = 0;
        }

        followPlayer(gameState.getPlayer());
    }
}
