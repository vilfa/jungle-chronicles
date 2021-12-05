package si.vilfa.junglechronicles.Component;

import com.badlogic.gdx.Gdx;
import si.vilfa.junglechronicles.Events.EventDispatcher;

/**
 * @author luka
 * @date 03/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public abstract class DrawableGameComponent extends EventDispatcher implements DrawableComponent
{
    protected boolean isDrawable;
    protected boolean isUpdatable;
    protected int drawOrder;
    protected int updateOrder;

    public DrawableGameComponent(int updateOrder,
                                 boolean isUpdatable,
                                 int drawOrder,
                                 boolean isDrawable)
    {
        super();
        this.updateOrder = updateOrder;
        this.isUpdatable = isUpdatable;
        this.drawOrder = drawOrder;
        this.isDrawable = isDrawable;
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
    public abstract void draw();

    @Override
    public int getDrawOrder()
    {
        return drawOrder;
    }

    @Override
    public void setDrawOrder(int drawOrder)
    {
        this.drawOrder = drawOrder;
    }

    @Override
    public boolean isDrawable()
    {
        return isDrawable;
    }

    @Override
    public void setDrawable(boolean isDrawable)
    {
        this.isDrawable = isDrawable;
    }

    @Override
    public abstract void dispose();

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
}
