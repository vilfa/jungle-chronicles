package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
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
        state = State.IDLE_RIGHT;
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
                if (state == State.SLIDE_LEFT)
                {
                    setRotation((float) Math.toRadians(90f));
                    body.applyLinearImpulse(new Vector2(-500f, 0f), getPosition(), true);
                }
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                setVelocity(new Vector2(5f, 0f));
                state = (isPressedDown) ? State.SLIDE_RIGHT : State.RUN_RIGHT;
                if (state == State.SLIDE_RIGHT)
                {
                    setRotation((float) Math.toRadians(-90f));
                    body.applyLinearImpulse(new Vector2(500f, 0f), getPosition(), true);
                }
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                setVelocity(new Vector2(0f, 6.5f));
                state = (State.getRight().contains(state, false)) ? State.JUMP_RIGHT :
                        State.JUMP_LEFT;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, -6.5f));
                isPressedDown = true;
                state = (State.getRight().contains(state, false)) ? State.SLIDE_RIGHT :
                        State.SLIDE_LEFT;
                if (state == State.SLIDE_LEFT)
                {
                    setRotation((float) Math.toRadians(90f));
                    body.applyLinearImpulse(new Vector2(-500f, 0f), getPosition(), true);
                } else
                {
                    setRotation((float) Math.toRadians(-90f));
                    body.applyLinearImpulse(new Vector2(500f, 0f), getPosition(), true);
                }
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
            case Input.Keys.UP:
            case Input.Keys.W:
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                state = (State.getRight().contains(state, false)) ? State.IDLE_RIGHT :
                        State.IDLE_LEFT;
                setVelocity(new Vector2());
                setRotation(0f);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                state = (State.getRight().contains(state, false)) ? State.IDLE_RIGHT :
                        State.IDLE_LEFT;
                setVelocity(new Vector2());
                setRotation(0f);
                isPressedDown = false;
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
        IDLE_LEFT, IDLE_RIGHT, RUN_LEFT, RUN_RIGHT, JUMP_LEFT, JUMP_RIGHT, SLIDE_LEFT, SLIDE_RIGHT;

        private static final Array<State> RIGHT = new Array<>(new State[]{ IDLE_RIGHT, RUN_RIGHT,
                                                                           JUMP_RIGHT,
                                                                           SLIDE_RIGHT });
        private static final Array<State> LEFT = new Array<>(new State[]{ IDLE_LEFT, RUN_LEFT,
                                                                          JUMP_LEFT, SLIDE_LEFT });

        public static Array<State> getRight()
        {
            return RIGHT;
        }

        public static Array<State> getLeft()
        {
            return LEFT;
        }
    }
}
