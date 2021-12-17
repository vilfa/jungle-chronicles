package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Input.Events.KeyDownInputEvent;
import si.vilfa.junglechronicles.Input.Events.KeyUpInputEvent;
import si.vilfa.junglechronicles.Level.GameStateEvent;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.Player;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Player.Human
 **/
public class HumanPlayer extends Player
{
    private State state;
    private boolean isPressedDown;

    public HumanPlayer(Body body)
    {
        super(body);
        state = State.IDLE;
        isPressedDown = false;
    }

    @Override
    public void update()
    {
        if (!isUpdatable) return;
    }

    @Override
    public void dispose() { }

    @Override
    public void handleKeyDown(KeyDownInputEvent event)
    {
        if (!isUpdatable) return;

        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                setVelocity(new Vector2(-5f, 0f));
                state = (isPressedDown) ? State.SLIDE_LEFT : State.RUN_LEFT;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                setVelocity(new Vector2(5f, 0f));
                state = (isPressedDown) ? State.SLIDE_RIGHT : State.RUN_RIGHT;
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                setVelocity(new Vector2(0f, 6.5f));
                state = State.JUMP;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, -6.5f));
                isPressedDown = true;
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
                isPressedDown = false;
                state = State.IDLE;
                break;
        }
    }

    @Override
    public void handleBeginContact(Object contact)
    {
        if (contact instanceof GameBlock)
        {
            GameBlock gameBlock = (GameBlock) contact;
            if (gameBlock.isCollectible())
            {
                ((GameBlock) contact).setActive(false);
                dispatchEvent(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT, contact);
            } else if (gameBlock.isTrap())
            {
                dispatchEvent(GameStateEvent.PLAYER_TRAP_CONTACT, contact);
            }
        } else if (contact instanceof Enemy)
        {
            dispatchEvent(GameStateEvent.PLAYER_ENEMY_CONTACT, contact);
        }
    }

    public State getState()
    {
        return state;
    }

    public enum State
    {
        IDLE, RUN_LEFT, RUN_RIGHT, JUMP, SLIDE_LEFT, SLIDE_RIGHT
    }
}
