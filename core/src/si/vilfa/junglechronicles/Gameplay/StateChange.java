package si.vilfa.junglechronicles.Gameplay;

/**
 * @author luka
 * @date 28/11/2021
 * @package si.vilfa.junglechronicles.Gameplay
 **/
public interface StateChange
{
    void notifyStateChange(Object object, boolean isActive);
}
