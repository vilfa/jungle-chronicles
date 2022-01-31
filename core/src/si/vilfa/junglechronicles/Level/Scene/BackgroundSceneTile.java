package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.vilfa.junglechronicles.Gameplay.Game;

/**
 * @author luka
 * @date 17/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class BackgroundSceneTile extends SceneObject
{
    private final TextureRegion background;
    private final Game game;
    private final Vector2 position;
    private Viewport viewport;

    public BackgroundSceneTile(TiledMapImageLayer layer, Game game)
    {
        super();
        this.game = game;
        position = new Vector2();
        background = layer.getTextureRegion();
        background.getTexture()
                  .setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        background.setRegion(0,
                             0,
                             background.getTexture().getWidth() * 10,
                             background.getTexture().getHeight());
    }

    @Override
    public void update()
    {
        position.set(0f, game.getPlayer().getPosition().y - viewport.getWorldHeight() / 2f);
    }

    @Override
    public void dispose()
    {
        background.getTexture().dispose();
    }

    @Override
    public void draw(SpriteBatch spriteBatch)
    {
        if (isVisible)
        {
            spriteBatch.draw(background, position.x, position.y, 100, 20);
        }
    }

    public void setViewport(Viewport viewport)
    {
        this.viewport = viewport;
    }
}
