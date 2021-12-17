package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

import java.util.TreeMap;

/**
 * @author luka
 * @date 17/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class PlayerSceneTile extends SceneObject
{
    private float animationState;
    private final Animation<Sprite> animation;

    public PlayerSceneTile(TreeMap<Integer, TextureAtlas.AtlasRegion> spec,
                           Animation.PlayMode playMode,
                           float frameDuration,
                           boolean flipX,
                           boolean flipY)
    {
        width = PhysicsEngine.toUnits(spec.firstEntry().getValue().getRegionWidth());
        height = PhysicsEngine.toUnits(spec.firstEntry().getValue().getRegionHeight());

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
        sprite.setSize(width, height);
        sprite.setCenter(center.x, center.y);
        sprite.draw(spriteBatch);
    }
}
