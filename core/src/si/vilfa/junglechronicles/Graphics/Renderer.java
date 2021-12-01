package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Component.DrawableGameComponent;
import si.vilfa.junglechronicles.Config.GlobalConfig;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Scene.Levels.Level;
import si.vilfa.junglechronicles.Scene.Objects.*;

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
    private final int textureScale;
    private final int textureScalePixels;
    private final Texture atlasTexture;
    private final TextureAtlas atlas;
    private final float playerAnimationKeyframeDuration;
    private final Array<TextureRegion> playerLeftKeyframes;
    private final Array<TextureRegion> playerRightKeyframes;
    private int screenWidth;
    private int screenHeight;
    private float screenAspectRatio;
    private float playerAnimationState;
    private float playerLastVelocityX;
    private Animation<TextureRegion> playerLeftAnimation;
    private Animation<TextureRegion> playerRightAnimation;

    private float deltaTime;
    private float fpsTimer;

    public Renderer(Level gameLevel, float gameWorldWidth, float gameWorldHeight)
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
        screenAspectRatio = (float) screenWidth / (float) screenHeight;

        level = gameLevel;
        spriteBatch = new SpriteBatch();

        playerAnimationKeyframeDuration = 1 / 15f;
        playerAnimationState = 0f;
        playerLastVelocityX = 0.1f;
        playerRightKeyframes = new Array<>();
        playerLeftKeyframes = new Array<>();

        if (screenWidth > 1920 && screenHeight > 1080)
        {
            textureScale = 2;
            textureScalePixels = GlobalConfig.RESOLUTION_SCALE_TWO;
            atlasTexture = new Texture("2x/Atlas@2x.png");
        } else
        {
            textureScale = 1;
            textureScalePixels = GlobalConfig.RESOLUTION_SCALE_ONE;
            atlasTexture = new Texture("1x/Atlas@1x.png");
        }
        atlas = new TextureAtlas();
        initializeTextureAtlas();

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

    private void initializeTextureAtlas()
    {
        GlobalConfig.ConfigEntry configEntry;
        Vector2 textureLoc;
        for (TerrainBlockType key : GlobalConfig.TERRAIN_BLOCK_CONFIG.keySet())
        {
            configEntry = GlobalConfig.TERRAIN_BLOCK_CONFIG.get(key);
            textureLoc = configEntry.getAtlasLocation(textureScale);
            atlas.addRegion(key.name(),
                            new TextureRegion(atlasTexture,
                                              (int) textureLoc.x,
                                              (int) textureLoc.y,
                                              textureScalePixels,
                                              textureScalePixels));
        }

        for (CollectibleBlockType key : GlobalConfig.COLLECTIBLE_BLOCK_CONFIG.keySet())
        {
            configEntry = GlobalConfig.COLLECTIBLE_BLOCK_CONFIG.get(key);
            textureLoc = configEntry.getAtlasLocation(textureScale);
            atlas.addRegion(key.name(),
                            new TextureRegion(atlasTexture,
                                              (int) textureLoc.x,
                                              (int) textureLoc.y,
                                              textureScalePixels,
                                              textureScalePixels));
        }

        for (PlayerBlockType key : GlobalConfig.PLAYER_BLOCK_CONFIG.keySet())
        {
            configEntry = GlobalConfig.PLAYER_BLOCK_CONFIG.get(key);
            textureLoc = configEntry.getAtlasLocation(textureScale);
            TextureRegion textureRegion = new TextureRegion(atlasTexture,
                                                            (int) textureLoc.x,
                                                            (int) textureLoc.y,
                                                            textureScalePixels,
                                                            textureScalePixels);
            atlas.addRegion(key.name(), textureRegion);
        }
        for (PlayerBlockType keyframe : PlayerBlockType.getLeftKeyframes())
        {
            playerLeftKeyframes.add(atlas.findRegion(keyframe.name()));
        }
        for (PlayerBlockType keyframe : PlayerBlockType.getRightKeyframes())
        {
            playerRightKeyframes.add(atlas.findRegion(keyframe.name()));
        }
        playerLeftAnimation = new Animation<>(playerAnimationKeyframeDuration, playerLeftKeyframes);
        playerRightAnimation = new Animation<>(playerAnimationKeyframeDuration,
                                               playerRightKeyframes);

        for (TrapBlockType key : GlobalConfig.TRAP_BLOCK_CONFIG.keySet())
        {
            configEntry = GlobalConfig.TRAP_BLOCK_CONFIG.get(key);
            textureLoc = configEntry.getAtlasLocation(textureScale);
            TextureRegion textureRegion = new TextureRegion(atlasTexture,
                                                            (int) textureLoc.x,
                                                            (int) textureLoc.y,
                                                            textureScalePixels,
                                                            textureScalePixels);
            atlas.addRegion(key.name(), textureRegion);
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
        if (!isDrawable) return;
        // TODO Make draw calls use draw order.
        deltaTime = gameTime.getDeltaTime();
        ScreenUtils.clear(1, 1, 1, 1);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        // TODO Add background rendering.
        for (Object item : level.getItems())
        {
            Vector2 position;
            Vector2 velocity;
            Sprite sprite;
            if (item instanceof HumanPlayer)
            {
                HumanPlayer p = ((HumanPlayer) item);
                if (p.isActive())
                {
                    position = p.getPosition();
                    velocity = p.getVelocity();
                    TextureRegion region;
                    if (velocity.x > 0f)
                    {
                        playerLastVelocityX = velocity.x;
                        playerAnimationState += deltaTime;
                        region = playerRightAnimation.getKeyFrame(playerAnimationState, true);
                    } else if (velocity.x < 0f)
                    {
                        playerLastVelocityX = velocity.x;
                        playerAnimationState += deltaTime;
                        region = playerLeftAnimation.getKeyFrame(playerAnimationState, true);
                    } else if (playerLastVelocityX > 0f)
                    {
                        region = playerRightKeyframes.first();
                    } else
                    {
                        region = playerLeftKeyframes.first();
                    }
                    sprite = new Sprite(region);
                    sprite.setSize(1.75f, 1.75f);
                    sprite.setCenter(position.x, position.y);
                    sprite.draw(spriteBatch);
                }
            } else if (item instanceof TerrainBlock)
            {
                TerrainBlock b = ((TerrainBlock) item);
                if (b.isActive())
                {
                    position = b.getPosition();
                    sprite = atlas.createSprite(b.getBlockType().name());
                    sprite.setSize(1f, 1f);
                    sprite.setCenter(position.x, position.y);
                    sprite.draw(spriteBatch);
                }
            } else if (item instanceof CollectibleBlock)
            {
                CollectibleBlock b = ((CollectibleBlock) item);
                if (b.isActive())
                {
                    position = b.getPosition();
                    sprite = atlas.createSprite(b.getBlockType().name());
                    sprite.setSize(1f, 1f);
                    sprite.setCenter(position.x, position.y);
                    sprite.draw(spriteBatch);
                }
            } else if (item instanceof TrapBlock)
            {
                TrapBlock b = ((TrapBlock) item);
                if (b.isActive())
                {
                    position = b.getPosition();
                    sprite = atlas.createSprite(b.getBlockType().name());
                    sprite.setSize(1f, 1f);
                    sprite.setCenter(position.x, position.y);
                    sprite.draw(spriteBatch);
                }
            }
        }
        spriteBatch.end();
    }

    @Override
    public void dispose()
    {
        atlas.dispose();
        atlasTexture.dispose();
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
    }
}
