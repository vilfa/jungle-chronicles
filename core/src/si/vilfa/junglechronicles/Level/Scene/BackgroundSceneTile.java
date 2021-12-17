package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

/**
 * @author luka
 * @date 17/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class BackgroundSceneTile extends SceneObject
{
    private final TiledMapImageLayer layer;
    private final Sprite sprite;

    public BackgroundSceneTile(TiledMapImageLayer layer)
    {
        super();
        this.layer = layer;
        sprite = new Sprite(layer.getTextureRegion());
        sprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        sprite.setSize(PhysicsEngine.toUnits((float) sprite.getRegionWidth()),
                       PhysicsEngine.toUnits((float) sprite.getRegionHeight()));
    }

    @Override
    public void dispose()
    {
        layer.getTextureRegion().getTexture().dispose();
    }

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        sprite.setPosition(position.x, position.y);
        sprite.draw(spriteBatch);
    }

    @Override
    public void update()
    { }

    public void translate(Vector2 translation)
    {
        setCenter(getCenter().add(translation));
    }
}
