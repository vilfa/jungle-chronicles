package si.vilfa.junglechronicles.Input.Events;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Events
 **/
public interface InputEventListener
{
    default void handleKeyDown(KeyDownInputEvent event) { }

    default void handleKeyUp(KeyUpInputEvent event) { }

    default void handleKeyTyped(KeyTypedInputEvent event) { }

    default void handleTouchDown(TouchDownInputEvent event) { }

    default void handleTouchUp(TouchUpInputEvent event) { }

    default void handleTouchDragged(TouchDraggedInputEvent event) { }

    default void handleMouseMoved(MouseMovedInputEvent event) { }

    default void handleScrolled(ScrolledInputEvent event) { }
}
