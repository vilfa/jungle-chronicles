package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Graphics.GameRenderer;

import java.util.TreeMap;

/**
 * @author luka
 * @date 17/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class PlayerSceneTile extends SceneObject
{
    private final Animation<Sprite> animation;
    private final Vector2 scale;
    private Vector2 offset;
    private float animationState;

    public PlayerSceneTile(TreeMap<Integer, TextureAtlas.AtlasRegion> spec,
                           Animation.PlayMode playMode,
                           float frameDuration,
                           boolean flipX,
                           boolean flipY,
                           Vector2 scale)
    {
        this.offset = new Vector2();
        this.scale = scale;
        this.animationState = 0f;

        Array<Sprite> keyframes = new Array<>(spec.size());
        spec.forEach((key, value) ->
                     {
                         Sprite sprite = new Sprite(value);
                         sprite.flip(flipX, flipY);
                         keyframes.add(sprite);
                     });

        animation = new Animation<>(frameDuration, keyframes);
        animation.setPlayMode(playMode);
    }

    public PlayerSceneTile(TreeMap<Integer, TextureAtlas.AtlasRegion> spec,
                           Animation.PlayMode playMode,
                           float frameDuration,
                           boolean flipX,
                           boolean flipY,
                           Vector2 scale,
                           Vector2 offset)
    {
        this(spec, playMode, frameDuration, flipX, flipY, scale);
        this.offset = offset;
    }

    public void setOffset(Vector2 offset)
    {
        this.offset.set(offset);
    }

    @Override
    public void update()
    {
        this.animationState += GameRenderer.gameTime.getDeltaTime();
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
        if (isVisible)
        {
            Sprite sprite = animation.getKeyFrame(animationState);
            sprite.setScale(scale.x, scale.y);
            sprite.setCenter(center.x + offset.x, center.y + offset.y);
            sprite.draw(spriteBatch);
        }
    }
}
