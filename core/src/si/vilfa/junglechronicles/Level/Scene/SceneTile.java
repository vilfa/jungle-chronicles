package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class SceneTile extends SceneObject
{
    protected final Sprite sprite;

    public SceneTile(int x, int y, TiledMapTileLayer layer, TiledMapTile tile)
    {
        super();

        width = PhysicsEngine.toUnits(layer.getTileWidth());
        height = PhysicsEngine.toUnits(layer.getTileHeight());
        position = new Vector2(x * width, y * height);
        center = new Vector2(position.x + width * 0.5f, position.y + height * 0.5f);

        sprite = new Sprite(tile.getTextureRegion());
        sprite.setSize(PhysicsEngine.toUnits(tile.getTextureRegion().getRegionWidth()),
                       PhysicsEngine.toUnits(tile.getTextureRegion().getRegionHeight()));
        sprite.setPosition(position.x, position.y);
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    @Override
    public void update() { }

    @Override
    public void dispose()
    {
        sprite.getTexture().dispose();
    }

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        if (isVisible)
        {
            sprite.draw(spriteBatch);
        }
    }
}
