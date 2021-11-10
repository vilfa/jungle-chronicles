package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class KeyDownInputEvent extends InputEvent
{
	public KeyDownInputEvent(int keycode)
	{
		this.keycode = keycode;
	}

	public int getKeyCode()
	{
		return this.keycode;
	}

	@Override
	public InputEventType getInputEventType()
	{
		return InputEventType.KEY_DOWN;
	}
}
