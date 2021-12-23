package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 23/12/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public abstract class Renderer extends DrawableGameComponent implements WindowAdapter
{
    public static GameTime gameTime;
    protected final GameState gameState;
    protected final Viewport viewport;
    protected final float visibleWorldWidth;
    protected final float visibleWorldHeight;
    protected final float worldWidth;
    protected final float worldHeight;
    protected final int screenWidthMax;
    protected final int screenHeightMax;
    protected final int screenRefreshRate;
    protected int screenWidth;
    protected int screenHeight;
    protected float screenAspectRatio;
    protected float deltaTime;
    protected float fpsTimer;

    public Renderer(GameState gameState)
    {
        super(0, true, 0, true);

        GameRenderer.gameTime = new GameTime();
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
        //        Gdx.graphics.setForegroundFPS(screenRefreshRate);

        this.gameState = gameState;
        worldWidth = gameState.getPhysics().getWorldWidth();
        worldHeight = gameState.getPhysics().getWorldHeight();

        viewport = new ExtendViewport(visibleWorldWidth,
                                      visibleWorldHeight,
                                      worldWidth,
                                      worldHeight);
        viewport.apply(true);
    }

    public Matrix4 getCombined() { return viewport.getCamera().combined; }

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

    @Override
    public abstract void draw();

    @Override
    public abstract void dispose();

    @Override
    public abstract void update();
}
