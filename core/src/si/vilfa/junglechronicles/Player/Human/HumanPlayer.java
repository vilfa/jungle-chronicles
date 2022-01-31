package si.vilfa.junglechronicles.Player.Human;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Events.PlayerEvent;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Input.Events.KeyDownInputEvent;
import si.vilfa.junglechronicles.Input.Events.KeyUpInputEvent;
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
    public static int MAX_LIVES = 2;
    public static int MAX_HEALTH = 100;
    private State state;
    private boolean isPressedDown;

    private float timer = 0f;
    private boolean jumpLock = false;
    private int jumpCount = 0;
    private final float jumpTimeout = 0.5f;

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

        if (jumpCount >= 3)
        {
            jumpLock = true;
        }

        if (jumpLock)
        {
            timer += Renderer.gameTime.getDeltaTime();
            if (timer >= jumpTimeout)
            {
                jumpLock = false;
                jumpCount = 0;
                timer = 0f;
            }
        }
    }

    @Override
    public void dispose() { }

    @Override
    public void handleBeginContact(Object contact)
    {
        if (contact instanceof GameBlock)
        {
            GameBlock gameBlock = (GameBlock) contact;
            if (gameBlock.isCollectible())
            {
                ((GameBlock) contact).setActive(false);
                dispatchEvent(GameEvent.PLAYER_COLLECTIBLE_CONTACT, contact);
            } else if (gameBlock.isTrap())
            {
                dispatchEvent(GameEvent.PLAYER_TRAP_CONTACT, contact);
            } else if (gameBlock.isLevelEnd())
            {
                dispatchEvent(GameEvent.PLAYER_LEVEL_END_CONTACT, contact);
            }
        } else if (contact instanceof Enemy)
        {
            dispatchEvent(GameEvent.PLAYER_ENEMY_CONTACT, contact);
        }
    }

    @Override
    public void handleKeyDown(KeyDownInputEvent event)
    {
        if (!isUpdatable || game.isPaused()) return;

        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                setVelocity(new Vector2(-5f, 0f));

                if (isPressedDown)
                {
                    setState(State.SLIDE_LEFT);
                } else
                {
                    setState(State.RUN_LEFT);
                }

                if (state == State.SLIDE_LEFT)
                {
                    setRotation((float) Math.toRadians(90f));
                    body.applyLinearImpulse(new Vector2(-500f, 0f), getPosition(), true);
                }
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                setVelocity(new Vector2(5f, 0f));

                if (isPressedDown)
                {
                    setState(State.SLIDE_RIGHT);
                } else
                {
                    setState(State.RUN_RIGHT);
                }

                if (state == State.SLIDE_RIGHT)
                {
                    setRotation((float) Math.toRadians(-90f));
                    body.applyLinearImpulse(new Vector2(500f, 0f), getPosition(), true);
                }
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                if (jumpLock) break;

                setVelocity(new Vector2(0f, 6.5f));

                if (State.getRight().contains(state, false))
                {
                    setState(State.JUMP_RIGHT);
                } else
                {
                    setState(State.JUMP_LEFT);
                }

                jumpCount++;
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                setVelocity(new Vector2(0f, -6.5f));
                isPressedDown = true;

                if (State.getRight().contains(state, false))
                {
                    setState(State.SLIDE_RIGHT);
                } else
                {
                    setState(State.SLIDE_LEFT);
                }

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
        if (!isUpdatable || game.isPaused()) return;

        switch (event.getKeyCode())
        {
            case Input.Keys.LEFT:
            case Input.Keys.A:
            case Input.Keys.UP:
            case Input.Keys.W:
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                if (State.getRight().contains(state, false))
                {
                    setState(State.IDLE_RIGHT);
                } else
                {
                    setState(State.IDLE_LEFT);
                }
                setVelocity(new Vector2());
                setRotation(0f);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                if (State.getRight().contains(state, false))
                {
                    setState(State.IDLE_RIGHT);
                } else
                {
                    setState(State.IDLE_LEFT);
                }
                setVelocity(new Vector2());
                setRotation(0f);
                isPressedDown = false;
                break;
        }
    }

    public State getState()
    {
        return state;
    }

    private void setState(State state)
    {
        this.state = state;
        switch (this.state)
        {
            case RUN_LEFT:
            case RUN_RIGHT:
                dispatchEvent(PlayerEvent.PLAYER_RUN);
                break;
            case IDLE_LEFT:
            case IDLE_RIGHT:
                dispatchEvent(PlayerEvent.PLAYER_IDLE);
                break;
        }
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
