package si.vilfa.junglechronicles.Component;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public interface Component
{
	void initialize(int updateOrder, boolean isUpdatable);

	String getId();

	void log(String message);
}
