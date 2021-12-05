package si.vilfa.junglechronicles.Component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import si.vilfa.junglechronicles.Events.EventDispatcher;

/**
 * @author luka
 * @date 04/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public abstract class GameComponent extends EventDispatcher
        implements Component, Updatable, Disposable
{
    protected boolean isUpdatable;
    protected int updateOrder;

    public GameComponent(int updateOrder, boolean isUpdatable)
    {
        super();
        this.updateOrder = updateOrder;
        this.isUpdatable = isUpdatable;
    }

    @Override
    public String getId()
    {
        return getClass().getSimpleName() + "#" + hashCode();
    }

    @Override
    public void log(String message)
    {
        Gdx.app.debug(getId(), message);
    }

    @Override
    public abstract void update();

    @Override
    public int getUpdateOrder()
    {
        return updateOrder;
    }

    @Override
    public void setUpdateOrder(int updateOrder)
    {
        this.updateOrder = updateOrder;
    }

    @Override
    public boolean isUpdatable()
    {
        return isUpdatable;
    }

    @Override
    public void setUpdatable(boolean isUpdatable)
    {
        this.isUpdatable = isUpdatable;
    }

    @Override
    public abstract void dispose();
}
