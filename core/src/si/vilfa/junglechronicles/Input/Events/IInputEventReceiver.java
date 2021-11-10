package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public interface IInputEventReceiver
{
	void handleKeyDown(InputEvent event);

	void handleKeyUp(InputEvent event);

	void handleKeyTyped(InputEvent event);

	void handleTouchDown(InputEvent event);

	void handleTouchUp(InputEvent event);

	void handleTouchDragged(InputEvent event);

	void handleMouseMoved(InputEvent event);

	void handleScrolled(InputEvent event);
}
