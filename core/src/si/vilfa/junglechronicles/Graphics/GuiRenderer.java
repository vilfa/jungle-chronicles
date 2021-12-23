package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 23/12/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class GuiRenderer extends Renderer
{
    private final SpriteBatch spriteBatch;

    public GuiRenderer(GameState gameState)
    {
        super(gameState);

        spriteBatch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    @Override
    public void draw()
    {

    }

    @Override
    public void dispose()
    {

    }

    @Override
    public void update()
    {

    }
}
