package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Config.Config;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Level.Scene.AnimatedSceneTile;
import si.vilfa.junglechronicles.Level.Scene.SceneTile;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Player.Player;

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
    private float playerLastVelocity;

    // TODO Remove this
    private final AnimatedSceneTile playerAnimation;

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

        playerLastVelocity = 0.1f;
        playerAnimation = new AnimatedSceneTile(new Texture("Atlases/Player.png"),
                                                Config.PLAYER_ANIMATION_SPEC,
                                                Animation.PlayMode.LOOP,
                                                1 / 10f);
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

        for (SceneTile tile : gameState.getCurrentLevel().getTiles())
        {
            tile.draw(spriteBatch);
        }

        MapLayer backgroundLayer = gameState.getCurrentLevel()
                                            .getMap()
                                            .getLayers()
                                            .get(Level.MapLayer.BACKGROUND_LAYER.getLayerName());
        if (backgroundLayer instanceof TiledMapImageLayer && backgroundLayer.isVisible())
        {
            // TODO Implement background rendering.
            TiledMapImageLayer layer = ((TiledMapImageLayer) backgroundLayer);
        }

        HumanPlayer player = gameState.getPlayer();
        Vector2 velocity = player.getVelocity();
        playerAnimation.setCenter(player.getPosition());
        if (velocity.x != 0f)
        {
            playerLastVelocity = velocity.x;
            playerAnimation.update();
            playerAnimation.drawDirectional(spriteBatch,
                                            (velocity.x > 0f) ?
                                            AnimatedSceneTile.AnimationDirection.ANIMATION_RIGHT :
                                            AnimatedSceneTile.AnimationDirection.ANIMATION_LEFT);
        } else
        {
            playerAnimation.setAnimationState(0f);
            playerAnimation.drawDirectional(spriteBatch,
                                            (playerLastVelocity >= 0f) ?
                                            AnimatedSceneTile.AnimationDirection.ANIMATION_RIGHT :
                                            AnimatedSceneTile.AnimationDirection.ANIMATION_LEFT);
        }

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
