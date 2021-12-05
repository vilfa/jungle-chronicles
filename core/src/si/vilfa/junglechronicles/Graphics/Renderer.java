package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

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
    private final float playerAnimationKeyframeDuration;
    private final Array<TextureRegion> playerLeftKeyframes;
    private final Array<TextureRegion> playerRightKeyframes;
    private int screenWidth;
    private int screenHeight;
    private float screenAspectRatio;
    private float playerAnimationState;
    private float playerLastVelocityX;
    private Vector2 playerLastPosition;
    private Animation<TextureRegion> playerLeftAnimation;
    private Animation<TextureRegion> playerRightAnimation;

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

        level = gameState.getCurrentLevel();
        spriteBatch = new SpriteBatch();

        playerAnimationKeyframeDuration = 1 / 15f;
        playerAnimationState = 0f;
        playerLastVelocityX = 0.1f;
        playerLastPosition = new Vector2();
        playerRightKeyframes = new Array<>();
        playerLeftKeyframes = new Array<>();

        visibleWorldHeight = 13;
        visibleWorldWidth = visibleWorldHeight * screenAspectRatio;
        worldWidth = gameState.getPhysics().getWorldWidth();
        worldHeight = gameState.getPhysics().getWorldHeight();

        Gdx.graphics.setForegroundFPS(screenRefreshRate);

        viewport = new ExtendViewport(visibleWorldWidth,
                                      visibleWorldHeight,
                                      worldWidth,
                                      worldHeight);
        viewport.apply(true);
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

    private void followPlayer()
    {
        Vector2 newCameraPos = new Vector2(playerLastPosition);
        if (playerLastPosition.y < visibleWorldHeight / 2f)
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

        MapLayer terrainLayer = level.getMap()
                                     .getLayers()
                                     .get(Level.LevelMapLayer.TERRAIN_LAYER.getLayerName());
        if (terrainLayer instanceof TiledMapTileLayer && terrainLayer.isVisible())
        {
            TiledMapTileLayer layer = ((TiledMapTileLayer) terrainLayer);
            for (int i = 0; i < layer.getHeight(); i++)
            {
                for (int j = 0; j < layer.getWidth(); j++)
                {
                    TiledMapTileLayer.Cell cell = layer.getCell(i, j);

                    if (cell == null) continue;

                    TiledMapTile tile = cell.getTile();
                    TextureRegion textureRegion = tile.getTextureRegion();
                    Vector2 position = PhysicsEngine.toUnits(new Vector2(i * layer.getTileWidth(),
                                                                         j
                                                                         * layer.getTileHeight()));


                    Sprite sprite = new Sprite(textureRegion);
                    sprite.setSize(PhysicsEngine.toUnits(textureRegion.getRegionWidth()),
                                   PhysicsEngine.toUnits(textureRegion.getRegionHeight()));
                    sprite.setPosition(position.x, position.y);
                    sprite.draw(spriteBatch);
                }
            }
        }

        MapLayer backgroundLayer = level.getMap()
                                        .getLayers()
                                        .get(Level.LevelMapLayer.BACKGROUND_LAYER.getLayerName());
        if (backgroundLayer instanceof TiledMapImageLayer && backgroundLayer.isVisible())
        {
            // TODO Implement background rendering.
            TiledMapImageLayer layer = ((TiledMapImageLayer) backgroundLayer);
        }

        // TODO Clean this up.
        for (Object item : level.getItems())
        {
            Vector2 position;
            Vector2 velocity;
            //            Sprite sprite;
            if (item instanceof HumanPlayer)
            {
                HumanPlayer p = ((HumanPlayer) item);
                if (p.isActive())
                {
                    position = p.getPosition();
                    velocity = p.getVelocity();
                    playerLastPosition = position;
                    //                    TextureRegion region;
                    if (velocity.x > 0f)
                    {
                        playerLastVelocityX = velocity.x;
                        playerAnimationState += deltaTime;
                        //                        region = playerRightAnimation.getKeyFrame
                        //                        (playerAnimationState, true);
                    } else if (velocity.x < 0f)
                    {
                        playerLastVelocityX = velocity.x;
                        playerAnimationState += deltaTime;
                        //                        region = playerLeftAnimation.getKeyFrame
                        //                        (playerAnimationState, true);
                    } else if (playerLastVelocityX > 0f)
                    {
                        //                        region = playerRightKeyframes.first();
                    } else
                    {
                        //                        region = playerLeftKeyframes.first();
                    }
                    //                    sprite = new Sprite(region);
                    //                    sprite.setSize(1.75f, 1.75f);
                    //                    sprite.setCenter(position.x, position.y);
                    //                    sprite.draw(spriteBatch);
                }
            }
        }
        // !TODO
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

        followPlayer();
    }
}
