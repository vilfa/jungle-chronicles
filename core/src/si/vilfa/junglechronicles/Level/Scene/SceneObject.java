package si.vilfa.junglechronicles.Level.Scene;

import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public abstract class SceneObject extends GameComponent
{
    protected boolean isActive;
    protected GameState gameState;

    public SceneObject()
    {
        super(0, true);
        this.isActive = true;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    @Override
    public void update() { }

    @Override
    public void dispose() { }
}
