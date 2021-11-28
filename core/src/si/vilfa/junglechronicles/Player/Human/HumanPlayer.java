package si.vilfa.junglechronicles.Player.Human;

import si.vilfa.junglechronicles.Component.GameComponent;
import si.vilfa.junglechronicles.Gameplay.GameState;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public abstract class HumanPlayer extends GameComponent
{
    protected GameState gameState;
    protected boolean isActive;

    public HumanPlayer()
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
    public abstract void update();

    @Override
    public abstract void dispose();
}
