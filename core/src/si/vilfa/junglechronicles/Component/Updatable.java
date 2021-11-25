package si.vilfa.junglechronicles.Component;

/**
 * @author luka
 * @date 04/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public interface Updatable
{
	void update();

	int getUpdateOrder();

	void setUpdateOrder(int updateOrder);

	boolean getUpdatableEnabled();

	void setUpdatableEnabled(boolean isUpdatable);
}
