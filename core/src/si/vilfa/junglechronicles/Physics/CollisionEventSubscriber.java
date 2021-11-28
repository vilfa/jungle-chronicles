package si.vilfa.junglechronicles.Physics;

/**
 * @author luka
 * @date 25/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface CollisionEventSubscriber
{
    void handleBeginContact(Object contact);

    void handleEndContact(Object contact);
}
