package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import si.vilfa.junglechronicles.Physics.PhysicsEngine;

/**
 * @author luka
 * @date 24/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public class GuiSceneTile extends SceneObject
{
    protected final Sprite sprite;

    public GuiSceneTile(TextureRegion textureRegion)
    {
        super();

        width = PhysicsEngine.toUnits(textureRegion.getRegionWidth());
        height = PhysicsEngine.toUnits(textureRegion.getRegionHeight());
        this.sprite = new Sprite(textureRegion);
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
        // TODO: 24/12/2021 Set the position based on one of the 9 possible positions
        sprite.draw(spriteBatch);
    }
}
