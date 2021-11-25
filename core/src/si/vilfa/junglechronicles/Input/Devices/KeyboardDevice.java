package si.vilfa.junglechronicles.Input.Devices;

/**
 * @author luka
 * @date 07/11/2021
 * @package si.vilfa.junglechronicles.Input.Devices
 **/
public interface KeyboardDevice
{
	boolean hasHardwareKeyboard();

	boolean hasOnScreenKeyboard();

	void showOnScreenKeyboard();

	void hideOnScreenKeyboard();
}
