package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.Updatable;
import si.vilfa.junglechronicles.Level.Objects.GameBlock;
import si.vilfa.junglechronicles.Player.Player;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public interface Scene extends Updatable
{
    void addItem(Object item);

    void removeItem(Object item);

    Array<GameBlock> getObjects();

    Array<SceneTile> getTiles();

    Array<Player> getPlayers();

    void clear();
}
