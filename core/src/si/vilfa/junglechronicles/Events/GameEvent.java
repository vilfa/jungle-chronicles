package si.vilfa.junglechronicles.Events;

/**
 * @author luka
 * @date 05/12/2021
 * @package si.vilfa.junglechronicles.Events
 **/
public enum GameEvent implements EventType
{
    PLAYER_COLLECTIBLE_CONTACT,
    PLAYER_TRAP_CONTACT,
    PLAYER_ENEMY_CONTACT,
    PLAYER_HEALTH_CHANGE,
    PLAYER_SCORE_CHANGE,
    GAMEPLAY_START,
    GAMEPLAY_STOP,
    GAMEPLAY_RESET,
    GAME_SCREEN_CHANGE,
}
