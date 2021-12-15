package si.vilfa.junglechronicles.Utils;

import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import si.vilfa.junglechronicles.Component.Loggable;
import si.vilfa.junglechronicles.Gameplay.GameState;
import si.vilfa.junglechronicles.Level.GameStateEvent;
import si.vilfa.junglechronicles.Level.Level;
import si.vilfa.junglechronicles.Player.AI.AiPlayer;
import si.vilfa.junglechronicles.Player.AI.Enemy;
import si.vilfa.junglechronicles.Player.AI.Friend;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;
import si.vilfa.junglechronicles.Player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luka
 * @date 15/12/2021
 * @package si.vilfa.junglechronicles.Utils
 **/
public class PlayerFactory implements Loggable
{
    private static PlayerFactory INSTANCE;

    private PlayerFactory() { }

    public static PlayerFactory getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PlayerFactory();
        }
        return INSTANCE;
    }

    public <T extends Player> Player createPlayer(GameState gameState,
                                                  Class<T> clazz,
                                                  Vector2 position)
    {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        PolygonShape shape = shapeFactory.createPlayerShape(new Vector2(0.75f, 1f));
        Body body = bodyFactory.createWithShapeWithParams(shape,
                                                          BodyDef.BodyType.DynamicBody,
                                                          position,
                                                          65f,
                                                          0f,
                                                          0f);
        body.setFixedRotation(true);

        T player = gameObjectFactory.createWithBody(body, clazz, Body.class);
        body.getFixtureList().get(0).setUserData(player);
        gameState.getCurrentLevel().addItem(player);

        player.setGameState(gameState);
        if (player instanceof HumanPlayer)
        {
            gameState.setPlayer((HumanPlayer) player);
            player.registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getAudio())
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT,
                                         gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT, gameState.getAudio())
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                         gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                         gameState.getAudio());
        }


        return player;
    }

    public <T extends Player> Player createPlayerWithShape(GameState gameState,
                                                           Class<T> clazz,
                                                           Vector2 position,
                                                           Shape shape)
    {
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        Body body = bodyFactory.createWithShapeWithParams(shape,
                                                          BodyDef.BodyType.DynamicBody,
                                                          position,
                                                          65f,
                                                          0f,
                                                          0f);
        body.setFixedRotation(true);

        T player = gameObjectFactory.createWithBody(body, clazz, Body.class);
        body.getFixtureList().get(0).setUserData(player);
        gameState.getCurrentLevel().addItem(player);

        player.setGameState(gameState);
        if (player instanceof HumanPlayer)
        {
            gameState.setPlayer((HumanPlayer) player);
            player.registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getAudio())
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT,
                                         gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT, gameState.getAudio())
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                         gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                         gameState.getAudio());

        }


        return player;
    }

    public <T extends Player> Player createPlayerWithShapeWithExtra(GameState gameState,
                                                                    Class<T> clazz,
                                                                    Vector2 position,
                                                                    Shape shape,
                                                                    float density,
                                                                    float friction,
                                                                    float restitution)
    {
        BodyFactory bodyFactory = BodyFactory.getInstance(gameState);
        GameObjectFactory gameObjectFactory = GameObjectFactory.getInstance();

        Body body = bodyFactory.createWithShapeWithParams(shape,
                                                          BodyDef.BodyType.DynamicBody,
                                                          position,
                                                          density,
                                                          friction,
                                                          restitution);
        body.setFixedRotation(true);

        T player = gameObjectFactory.createWithBody(body, clazz, Body.class);
        body.getFixtureList().get(0).setUserData(player);
        gameState.getCurrentLevel().addItem(player);

        player.setGameState(gameState);
        if (player instanceof HumanPlayer)
        {
            gameState.setPlayer((HumanPlayer) player);
            player.registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_TRAP_CONTACT, gameState.getAudio())

                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT,
                                         gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_ENEMY_CONTACT, gameState.getAudio())

                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT, gameState)
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                         gameState.getPhysics())
                  .registerEventListener(GameStateEvent.PLAYER_COLLECTIBLE_CONTACT,
                                         gameState.getAudio());
        }

        return player;
    }

    public void setupPlayer(Player player,
                            GameState gameState,
                            HashMap<Level.Property, Object> props)
    {
        if (player instanceof HumanPlayer)
        {
            setupHumanPlayer((HumanPlayer) player, gameState, props);
        } else if (player instanceof AiPlayer)
        {
            setupAiPlayer((AiPlayer) player, gameState, props);
        } else
        {
            log("Error: unknown player type:" + player);
        }
    }

    private void setupHumanPlayer(HumanPlayer player,
                                  GameState gameState,
                                  HashMap<Level.Property, Object> props)
    {
        for (Map.Entry<Level.Property, Object> entry : props.entrySet())
        {
            if (Level.HumanPlayerProperty.HEALTH_POINTS.equals(entry.getKey()))
            {
                gameState.setPlayerHealth((Integer) entry.getValue());
            }
        }
    }

    private void setupAiPlayer(AiPlayer player,
                               GameState gameState,
                               HashMap<Level.Property, Object> props)
    {
        if (player instanceof Friend)
        {
            for (Map.Entry<Level.Property, Object> entry : props.entrySet())
            {
                if (Level.AiPlayerProperty.ARRIVE.equals(entry.getKey()))
                {
                    player.setBehaviour(new Arrive<>(player, gameState.getPlayer()));
                } else if (Level.AiPlayerProperty.PURSUE.equals(entry.getKey()))
                {
                    player.setBehaviour(new Pursue<>(player, gameState.getPlayer()));
                } else if (Level.AiPlayerProperty.EVADE.equals(entry.getKey()))
                {
                    player.setBehaviour(new Evade<>(player, gameState.getPlayer()));
                } else if (Level.AiPlayerProperty.FACE.equals(entry.getKey()))
                {
                    player.setBehaviour(new Face<>(player, gameState.getPlayer()));
                } else if (Level.AiPlayerProperty.WANDER.equals(entry.getKey()))
                {
                    player.setBehaviour(new Wander<>(player));
                }
            }
            for (Map.Entry<Level.Property, Object> entry : props.entrySet())
            {
                if (player.getBehaviour() instanceof Arrive)
                {
                    if (Level.AiPlayerProperty.ARRIVE_DECELERATION_RADIUS.equals(entry.getKey()))
                    {
                        ((Arrive<Vector2>) player.getBehaviour()).setDecelerationRadius((Float) entry.getValue());
                    }
                    if (Level.AiPlayerProperty.ARRIVE_TOLERANCE.equals(entry.getKey()))
                    {
                        ((Arrive<Vector2>) player.getBehaviour()).setArrivalTolerance((Float) entry.getValue());
                    }
                    if (Level.AiPlayerProperty.ARRIVE_TTT.equals(entry.getKey()))
                    {
                        ((Arrive<Vector2>) player.getBehaviour()).setTimeToTarget((Float) entry.getValue());
                    }
                }
            }
        } else if (player instanceof Enemy)
        {
            for (Map.Entry<Level.Property, Object> entry : props.entrySet())
            {
                if (Level.AiPlayerProperty.ENEMY_LEFT_BOUND.equals(entry.getKey()))
                {
                    ((Enemy) player).setLeftBound(player.getPosition(), (Float) entry.getValue());
                }
                if (Level.AiPlayerProperty.ENEMY_RIGHT_BOUND.equals(entry.getKey()))
                {
                    ((Enemy) player).setRightBound(player.getPosition(), (Float) entry.getValue());
                }
                if (Level.AiPlayerProperty.ENEMY_TOP_BOUND.equals(entry.getKey()))
                {
                    ((Enemy) player).setTopBound(player.getPosition(), (Float) entry.getValue());
                }
                if (Level.AiPlayerProperty.ENEMY_BOTTOM_BOUND.equals(entry.getKey()))
                {
                    ((Enemy) player).setBottomBound(player.getPosition(), (Float) entry.getValue());
                }
            }
        } else
        {
            log("Error: unknown AI player type:" + player);
        }
    }
}
