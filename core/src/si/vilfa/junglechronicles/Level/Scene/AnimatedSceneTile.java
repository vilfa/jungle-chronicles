package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class AnimatedSceneTile extends SceneObject
{
    private float frameDuration;
    private float animationState;
    private final Animation<Sprite> animation;

    private final float worldWidth;
    private final float worldHeight;

    private final boolean flipX;
    private final boolean flipY;

    public AnimatedSceneTile(Texture texture,
                             TreeMap<Integer, AnimationRegion> spec,
                             Animation.PlayMode playMode,
                             float frameDuration,
                             boolean flipX,
                             boolean flipY)
    {
        width = PhysicsEngine.toUnits(spec.firstEntry().getValue().width);
        height = PhysicsEngine.toUnits(spec.firstEntry().getValue().height);
        worldWidth = spec.firstEntry().getValue().worldWidth;
        worldHeight = spec.firstEntry().getValue().worldHeight;

        this.frameDuration = frameDuration;
        this.animationState = 0f;
        this.flipX = flipX;
        this.flipY = flipY;

        Array<Sprite> keyframes = new Array<>(spec.size());
        for (Map.Entry<Integer, AnimationRegion> entry : spec.entrySet())
        {
            AnimationRegion animationRegion = entry.getValue();
            keyframes.add(new Sprite(new TextureRegion(texture,
                                                       animationRegion.x,
                                                       animationRegion.y,
                                                       animationRegion.width,
                                                       animationRegion.height)));
        }

        animation = new Animation<>(frameDuration, keyframes);
        animation.setPlayMode(playMode);
    }

    @Override
    public void update()
    {
        this.animationState += Renderer.gameTime.getDeltaTime();
    }

    @Override
    public void dispose()
    {
        for (Sprite sprite : animation.getKeyFrames())
        {
            sprite.getTexture().dispose();
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        Sprite sprite = animation.getKeyFrame(animationState);
        sprite.setSize(worldWidth, worldHeight);
        sprite.setCenter(center.x, center.y);
        sprite.flip(flipX, flipY);
        sprite.draw(spriteBatch);
    }

    public float getFrameDuration()
    {
        return frameDuration;
    }

    public void setFrameDuration(float frameDuration)
    {
        this.frameDuration = frameDuration;
    }

    public float getAnimationState()
    {
        return animationState;
    }

    public void setAnimationState(float animationState)
    {
        this.animationState = animationState;
    }

    public static class AnimationRegion
    {
        public int x;
        public int y;
        public int width;
        public int height;
        public float worldWidth;
        public float worldHeight;

        public AnimationRegion(int x,
                               int y,
                               int width,
                               int height,
                               float worldWidth,
                               float worldHeight)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.worldWidth = worldWidth;
            this.worldHeight = worldHeight;
        }

        public AnimationRegion(int x, int y, int eqWh, float eqWorldWh)
        {
            this.x = x;
            this.y = y;
            this.width = this.height = eqWh;
            this.worldWidth = this.worldHeight = eqWorldWh;
        }
    }
}
