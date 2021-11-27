package si.vilfa.junglechronicles.Component;

/**
 * @author luka
 * @date 04/11/2021
 * @package si.vilfa.junglechronicles.Component
 **/
public interface DrawableComponent extends Component
{
    void draw();

    int getDrawOrder();

    void setDrawOrder(int drawOrder);

    boolean getDrawableEnabled();

    void setDrawableEnabled(boolean isDrawable);
}
