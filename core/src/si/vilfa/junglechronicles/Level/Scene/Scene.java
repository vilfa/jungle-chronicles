package si.vilfa.junglechronicles.Level.Scene;

import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Component.Updatable;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Level.Scene
 **/
public interface Scene extends Updatable
{
    void addItem(Object item);

    void addItems(Array<Object> items);

    void removeItem(Object item);

    Array<Object> getItems();

    void clear();
}
