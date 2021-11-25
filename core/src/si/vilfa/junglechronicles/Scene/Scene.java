package si.vilfa.junglechronicles.Scene;

import si.vilfa.junglechronicles.Component.Updatable;

import java.util.ArrayList;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Scene
 **/
public interface Scene extends Updatable
{
	void addItem(Object item);

	void removeItem(Object item);

	ArrayList<Object> getItems();

	void clear();
}
