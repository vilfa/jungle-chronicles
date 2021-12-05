package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public interface InputEventListener
{
    void handleKeyDown(KeyDownInputEvent event);

    void handleKeyUp(KeyUpInputEvent event);

    void handleKeyTyped(KeyTypedInputEvent event);

    void handleTouchDown(TouchDownInputEvent event);

    void handleTouchUp(TouchUpInputEvent event);

    void handleTouchDragged(TouchDraggedInputEvent event);

    void handleMouseMoved(MouseMovedInputEvent event);

    void handleScrolled(ScrolledInputEvent event);
}
