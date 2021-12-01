package si.vilfa.junglechronicles.Input.Processors;

import si.vilfa.junglechronicles.Input.Events.*;
import si.vilfa.junglechronicles.Input.TargetInputProcessor;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

/**
 * @author luka
 * @date 09/11/2021
 * @package si.vilfa.junglechronicles.Input.Processors
 **/
public class PlayerInputProcessor extends TargetInputProcessor<HumanPlayer>
{
    public PlayerInputProcessor(HumanPlayer target)
    {
        super(target);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        target.handleKeyDown(new KeyDownInputEvent(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        target.handleKeyUp(new KeyUpInputEvent(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        target.handleKeyTyped(new KeyTypedInputEvent(character));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        target.handleTouchDown(new TouchDownInputEvent(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        target.handleTouchUp(new TouchUpInputEvent(screenX, screenY, pointer, button));
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        target.handleTouchDragged(new TouchDraggedInputEvent(screenX, screenY, pointer));
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        target.handleMouseMoved(new MouseMovedInputEvent(screenX, screenY));
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        target.handleScrolled(new ScrolledInputEvent(amountX, amountY));
        return true;
    }

    @Override
    public HumanPlayer getTarget()
    {
        return target;
    }
}
