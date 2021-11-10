package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class KeyUpInputEvent extends InputEvent
{
	public KeyUpInputEvent(int keycode)
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
		return InputEventType.KEY_UP;
	}
}
