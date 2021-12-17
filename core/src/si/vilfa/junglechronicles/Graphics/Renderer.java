package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    private int screenWidth;
    private int screenHeight;
    private float screenAspectRatio;

    private final HashMap<HumanPlayer.State, TextureAtlas> playerStateTextures = new HashMap<>();
    private final HashMap<HumanPlayer.State, PlayerSceneTile> playerAnimations = new HashMap<>();

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

        playerStateTextures.put(HumanPlayer.State.IDLE, new TextureAtlas("Player/Idle.atlas"));
        playerStateTextures.put(HumanPlayer.State.JUMP, new TextureAtlas("Player/Jump.atlas"));
        playerStateTextures.put(HumanPlayer.State.RUN_LEFT, new TextureAtlas("Player/Run.atlas"));
        playerStateTextures.put(HumanPlayer.State.RUN_RIGHT,
                                playerStateTextures.get(HumanPlayer.State.RUN_LEFT));
        playerStateTextures.put(HumanPlayer.State.SLIDE_LEFT,
                                new TextureAtlas("Player/Slide.atlas"));
        playerStateTextures.put(HumanPlayer.State.SLIDE_RIGHT,
                                playerStateTextures.get(HumanPlayer.State.SLIDE_LEFT));

        for (Map.Entry<HumanPlayer.State, TextureAtlas> entry : playerStateTextures.entrySet())
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
                                                     1 / 13f,
                                                     entry.getKey() == HumanPlayer.State.RUN_LEFT
                                                     || entry.getKey()
                                                        == HumanPlayer.State.SLIDE_LEFT,
                                                     false));
        }
    }

    public Matrix4 getCombined()
    {
        return viewport.getCamera().combined;
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
        if (newCameraPos.y < visibleWorldHeight / 2f)
        {
            newCameraPos.y = visibleWorldHeight / 2f;
        }
        viewport.getCamera().position.set(newCameraPos, 0f);
        viewport.getCamera().update();
    }

    @Override
    public void draw()
    {
        if (!isDrawable) return;

        // TODO Make draw calls use draw order.
        deltaTime = gameTime.getDeltaTime();
        ScreenUtils.clear(1, 1, 1, 1);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        HumanPlayer player = gameState.getPlayer();

        for (BackgroundSceneTile tile : gameState.getCurrentLevel().getBackgrounds())
        {
            Vector2 bgParallax = player.getLinearVelocity();
            bgParallax.scl(-Renderer.gameTime.getDeltaTime());
            bgParallax.x *= .15f;
            bgParallax.y *= .15f;
            tile.translate(bgParallax);
            tile.draw(spriteBatch);
        }

        for (SceneTile tile : gameState.getCurrentLevel().getTiles())
        {
            tile.draw(spriteBatch);
        }

        log(player.getState().toString());

        playerAnimations.get(player.getState()).update();
        playerAnimations.get(player.getState()).setCenter(player.getPosition());
        playerAnimations.get(player.getState()).draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        spriteBatch.dispose();
        for (TextureAtlas atlas : playerStateTextures.values())
        {
            atlas.dispose();
        }
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
