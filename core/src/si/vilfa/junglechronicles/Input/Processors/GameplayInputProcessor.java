package si.vilfa.junglechronicles.Input.Processors;

import si.vilfa.junglechronicles.Gameplay.Gameplay;
import si.vilfa.junglechronicles.Input.Events.KeyDownInputEvent;
import si.vilfa.junglechronicles.Input.Events.KeyUpInputEvent;
import si.vilfa.junglechronicles.Input.Events.TouchDownInputEvent;
import si.vilfa.junglechronicles.Input.Events.TouchUpInputEvent;
import si.vilfa.junglechronicles.Input.TargetInputProcessor;

/**
 * @author luka
 * @date 10/11/2021
 * @package si.vilfa.junglechronicles.Input.Processors
 **/
public class GameplayInputProcessor extends TargetInputProcessor<Gameplay>
{
    public GameplayInputProcessor(Gameplay target)
    {
        super(target);
    }

    @Override
    public boolean keyDown(int keycode)
    {
        target.handleKeyDown(new KeyDownInputEvent(keycode));
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        target.handleKeyUp(new KeyUpInputEvent(keycode));
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        target.handleTouchDown(new TouchDownInputEvent(screenX, screenY, pointer, button));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        target.handleTouchUp(new TouchUpInputEvent(screenX, screenY, pointer, button));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY)
    {
        return true;
    }

    @Override
    public Gameplay getTarget()
    {
        return target;
    }
}
