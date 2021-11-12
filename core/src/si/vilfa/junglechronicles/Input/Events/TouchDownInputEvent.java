package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class TouchDownInputEvent extends InputEvent
{
	public TouchDownInputEvent(int screenX, int screenY, int pointer, int button)
	{
		this.screenX = screenX;
		this.screenY = screenY;
		this.pointer = pointer;
		this.button = button;
	}

	public int getScreenX()
	{
		return screenX;
	}

	public int getScreenY()
	{
		return screenY;
	}

	public int getPointer()
	{
		return pointer;
	}

	public int getButton()
	{
		return button;
	}

	@Override
	public InputEventType getInputEventType()
	{
		return InputEventType.TOUCH_DOWN;
	}
}
