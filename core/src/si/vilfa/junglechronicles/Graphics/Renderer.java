package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Player.Human.Player;
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Scene.Objects.CollectibleBlock;
import si.vilfa.junglechronicles.Scene.Objects.WorldBlock;

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
    private final Level level;
    private final int screenWidthMax;
    private final int screenHeightMax;
    private final int screenRefreshRate;
    private int screenWidth;
    private int screenHeight;
    private float screenAspectRatio;

    private Array<Sprite> worldBackgroundSprites;
    private Sprite playerSprite;
    private Sprite terrainBlockSprite;
    private Sprite midgroundBlockSprite;
    private Sprite goldCollectibleSprite;

    // TODO Remove this after no longer needed for debugging.
    private float timer = 0f;

    public Renderer(Level gameLevel, float gameWorldWidth, float gameWorldHeight)
    {
        super(0, true, 0, true);

        Renderer.gameTime = new GameTime();
        initializeSprites();

        level = gameLevel;
        spriteBatch = new SpriteBatch();

        Graphics.DisplayMode displayMode
                = Gdx.graphics.getDisplayMode(Gdx.graphics.getPrimaryMonitor());
        screenWidth = displayMode.width;
        screenWidthMax = displayMode.width;
        screenHeight = displayMode.height;
        screenHeightMax = displayMode.height;
        screenRefreshRate = displayMode.refreshRate;
        screenAspectRatio = (float) screenWidth / (float) screenHeight;

        visibleWorldHeight = 13;
        visibleWorldWidth = visibleWorldHeight * screenAspectRatio;
        worldWidth = gameWorldWidth;
        worldHeight = gameWorldHeight;

        Gdx.graphics.setForegroundFPS(screenRefreshRate);

        viewport = new ExtendViewport(visibleWorldWidth,
                                      visibleWorldHeight,
                                      worldWidth,
                                      worldHeight);
        viewport.apply(true);
    }

    private void initializeSprites()
    {
        // TODO Make these sprites scene objects and draw them by draw order.
        worldBackgroundSprites = new Array<>();
        worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldForestBackground@2x.png")));
        worldBackgroundSprites.add(new Sprite(new Texture(
                "World@2x/WorldForestShadowsNight@2x" + ".png")));
        worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldForestNight@2x.png")));
        worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldForestTreetops@2x.png")));
        worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldGroundGrass@2x.png")));
        worldBackgroundSprites.add(new Sprite(new Texture("World@2x/WorldGroundBushes@2x.png")));

        playerSprite = new Sprite(new Texture("128/Player/PlayerR1@128x128.png"));
        terrainBlockSprite = new Sprite(new Texture("128/Terrain/TerrainTopCenter@128x128.png"));
        midgroundBlockSprite = new Sprite(new Texture("128/Midground/MidgroundCenter@128x128.png"));
        goldCollectibleSprite = new Sprite(new Texture("128/Gold/Gold6@128x128.png"));
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
        log("Resize: width=" + width + ",height=" + height);
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
    public void draw()
    {
        if (!isDrawable) { return; }
        // TODO Make draw calls use draw order.
        ScreenUtils.clear(1, 1, 1, 1);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        for (Sprite sprite : worldBackgroundSprites)
        {
            sprite.setBounds(0, 0, visibleWorldWidth, visibleWorldHeight);
            sprite.draw(spriteBatch);
        }
        for (Object item : level.getItems())
        {
            Vector2 position;
            if (item instanceof Player)
            {
                position = ((Player) item).getPosition();
                playerSprite.setSize(1.75f, 1.75f);
                playerSprite.setCenter(position.x, position.y);
                playerSprite.draw(spriteBatch);
            } else if (item instanceof WorldBlock)
            {
                position = ((WorldBlock) item).getPosition();
                terrainBlockSprite.setSize(1f, 1f);
                terrainBlockSprite.setCenter(position.x, position.y);
                terrainBlockSprite.draw(spriteBatch);
            } else if (item instanceof CollectibleBlock)
            {
                position = ((CollectibleBlock) item).getPosition();
                goldCollectibleSprite.setCenter(position.x, position.y);
                goldCollectibleSprite.draw(spriteBatch);
            }
        }
        spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        level.dispose();

        for (Sprite sprite : worldBackgroundSprites)
        {
            sprite.getTexture().dispose();
        }
        playerSprite.getTexture().dispose();
        terrainBlockSprite.getTexture().dispose();
        midgroundBlockSprite.getTexture().dispose();
        goldCollectibleSprite.getTexture().dispose();

        spriteBatch.dispose();
    }

    @Override
    public void update()
    {
        if (!isUpdatable) { return; }
        // TODO Remove this after no longer needed for debugging.
        timer += Renderer.gameTime.getDeltaTime();
        if (timer > 1f)
        {
            log("FPS:" + Gdx.graphics.getFramesPerSecond());
            timer = 0;
        }
    }
}
