package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private Viewport viewport;
    private int xOffset;
    private int yOffset;

    public BackgroundSceneTile(TiledMapImageLayer layer)
    {
        super();
        this.layer = layer;
        xOffset = 0;
        yOffset = 0;
        sprite = new Sprite(layer.getTextureRegion());
        sprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        sprite.setSize(PhysicsEngine.toUnits((float) sprite.getRegionWidth()),
                       PhysicsEngine.toUnits((float) sprite.getRegionHeight()));
    }

    @Override
    public void update()
    {
        xOffset = (int) (viewport.getCamera().position.x * 2f);
        yOffset = (int) (viewport.getCamera().position.y * 2f);
    }

    @Override
    public void dispose()
    {
        layer.getTextureRegion().getTexture().dispose();
    }

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        //        sprite.setRegion(xOffset % sprite.getRegionWidth(),
        //                         yOffset % sprite.getRegionHeight(),
        //                         (int) viewport.getCamera().viewportWidth,
        //                         (int) viewport.getCamera().viewportHeight);
        sprite.draw(spriteBatch);
    }

    public void translate(Vector2 translation)
    {
        setCenter(getCenter().add(translation));
    }

    public void setViewport(Viewport viewport)
    {
        this.viewport = viewport;
    }
}
