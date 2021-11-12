package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public class ScrolledInputEvent extends InputEvent
{
	public ScrolledInputEvent(float amountX, float amountY)
	{
		this.amountX = amountX;
		this.amountY = amountY;
	}

	public float getAmountX()
	{
		return amountX;
	}

	public float getAmountY()
	{
		return amountY;
	}

	@Override
	public InputEventType getInputEventType()
	{
		return InputEventType.SCROLLED;
	}
}
