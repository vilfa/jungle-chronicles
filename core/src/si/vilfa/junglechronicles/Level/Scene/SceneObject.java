package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public abstract class SceneObject extends GameComponent
{
    protected GameState gameState;
    protected Vector2 position;
    protected Vector2 center;
    protected float width;
    protected float height;

    public SceneObject()
    {
        super(0, true);
        width = 0f;
        height = 0f;
        position = new Vector2();
        center = new Vector2();
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
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

    public Vector2 getCenter()
    {
        return center;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
        this.center.set(position.x + 0.5f * width, position.y + 0.5f * height);
    }

    public void setCenter(Vector2 center)
    {
        this.center.set(center);
        this.position.set(center.x - 0.5f * width, position.y - 0.5f * height);
    }
}
