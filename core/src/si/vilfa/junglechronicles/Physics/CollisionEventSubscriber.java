package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author luka
 * @date 25/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface CollisionEventSubscriber
{
    void handleBeginContact(Contact contact);

    void handleEndContact(Contact contact);

    void handlePreSolve(Contact contact, Manifold oldManifold);

    void handlePostSolve(Contact contact, ContactImpulse impulse);
}
