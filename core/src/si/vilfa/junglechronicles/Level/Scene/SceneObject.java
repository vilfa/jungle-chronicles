package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Level.Level;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public abstract class SceneObject extends GameComponent
{
    protected Level.MapLayer sourceLayer;
    protected Game game;
    protected Vector2 position;
    protected Vector2 center;
    protected float width;
    protected float height;
    protected boolean isVisible;

    public SceneObject()
    {
        super(0, true);
        width = 0f;
        height = 0f;
        position = new Vector2();
        center = new Vector2();
        isVisible = true;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void dispose();

    public abstract void draw(SpriteBatch spriteBatch);

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
        this.center.set(position.x + 0.5f * width, position.y + 0.5f * height);
    }

    public Vector2 getCenter()
    {
        return center;
    }

    public void setCenter(Vector2 center)
    {
        this.center.set(center);
        this.position.set(center.x - 0.5f * width, position.y - 0.5f * height);
    }

    public Level.MapLayer getSourceLayer()
    {
        return sourceLayer;
    }

    public void setSourceLayer(Level.MapLayer sourceLayer)
    {
        this.sourceLayer = sourceLayer;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }
}
