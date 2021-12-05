package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    protected final Vector2 position;
    protected final Vector2 center;
    protected final float width;
    protected final float height;
    protected final TextureRegion textureRegion;
    protected final Sprite sprite;

    public SceneTile(int x, int y, TiledMapTileLayer layer, TiledMapTile tile)
    {
        this.textureRegion = tile.getTextureRegion();
        this.width = PhysicsEngine.toUnits(layer.getTileWidth());
        this.height = PhysicsEngine.toUnits(layer.getTileHeight());
        this.position = new Vector2(x * width, y * height);
        this.center = new Vector2(position.x + width * 0.5f, position.y + height * 0.5f);
        this.sprite = new Sprite(tile.getTextureRegion());
        sprite.setSize(PhysicsEngine.toUnits(textureRegion.getRegionWidth()),
                       PhysicsEngine.toUnits(textureRegion.getRegionHeight()));
        sprite.setPosition(position.x, position.y);
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Vector2 getCenter()
    {
        return center;
    }
}
