package si.vilfa.junglechronicles.Scene.Objects;

import com.badlogic.gdx.utils.Array;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Scene.Objects
 **/
public enum PlayerBlockType implements BlockType
{
    PLAYER_LEFT_ONE,
    PLAYER_LEFT_TWO,
    PLAYER_LEFT_THREE,
    PLAYER_LEFT_FOUR,
    PLAYER_LEFT_FIVE,
    PLAYER_LEFT_SIX,
    PLAYER_LEFT_SEVEN,
    PLAYER_LEFT_EIGHT,
    PLAYER_RIGHT_ONE,
    PLAYER_RIGHT_TWO,
    PLAYER_RIGHT_THREE,
    PLAYER_RIGHT_FOUR,
    PLAYER_RIGHT_FIVE,
    PLAYER_RIGHT_SIX,
    PLAYER_RIGHT_SEVEN,
    PLAYER_RIGHT_EIGHT;

    public static Array<PlayerBlockType> getLeftKeyframes()
    {
        return new Array<>(new PlayerBlockType[]{ PLAYER_LEFT_ONE, PLAYER_LEFT_TWO,
                                                  PLAYER_LEFT_THREE, PLAYER_LEFT_FOUR,
                                                  PLAYER_LEFT_FIVE, PLAYER_LEFT_SIX,
                                                  PLAYER_LEFT_SEVEN, PLAYER_LEFT_EIGHT, });
    }

    public static Array<PlayerBlockType> getRightKeyframes()
    {
        return new Array<>(new PlayerBlockType[]{ PLAYER_RIGHT_ONE, PLAYER_RIGHT_TWO,
                                                  PLAYER_RIGHT_THREE, PLAYER_RIGHT_FOUR,
                                                  PLAYER_RIGHT_FIVE, PLAYER_RIGHT_SIX,
                                                  PLAYER_RIGHT_SEVEN, PLAYER_RIGHT_EIGHT, });
    }
}
