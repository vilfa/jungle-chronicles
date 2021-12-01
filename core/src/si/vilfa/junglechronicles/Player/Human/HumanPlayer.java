package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Input.Events.KeyDownInputEvent;
import si.vilfa.junglechronicles.Input.Events.KeyUpInputEvent;
import si.vilfa.junglechronicles.Player.Player;
import si.vilfa.junglechronicles.Scene.Objects.CollectibleBlock;
import si.vilfa.junglechronicles.Scene.Objects.TrapBlock;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public class HumanPlayer extends Player
{
    public HumanPlayer(Body body)
    {
        super(body);
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void handleKeyDown(KeyDownInputEvent event)
    {
        if (!isUpdatable) return;

        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                setVelocity(new Vector2(-5f, 0f));
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                setVelocity(new Vector2(5f, 0f));
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                setVelocity(new Vector2(0f, 6.5f));
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, -6.5f));
                break;
        }
    }

    @Override
    public void handleKeyUp(KeyUpInputEvent event)
    {
        if (!isUpdatable) return;

        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
            case Input.Keys.RIGHT:
            case Input.Keys.D:
            case Input.Keys.UP:
            case Input.Keys.W:
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, 0f));
                break;
        }
    }

    @Override
    public void handleBeginContact(Object contact)
    {
        if (contact instanceof CollectibleBlock)
        {
            log("Begin collision:" + contact);
            int points = ((CollectibleBlock) contact).getBlockType().getPoints();
            gameState.setPlayerScore(gameState.getPlayerScore() + points);
            gameState.notifyStateChange(contact, false);
        } else if (contact instanceof TrapBlock)
        {
            log("Begin collision:" + contact);
            int healthPoints = ((TrapBlock) contact).getBlockType().getHealthPoints();
            gameState.setPlayerHealth(gameState.getPlayerHealth() - healthPoints);
        }
    }
}
