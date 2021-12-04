package si.vilfa.junglechronicles.Physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * @author luka
 * @date 25/11/2021
 * @package si.vilfa.junglechronicles.Physics
 **/
public interface CollisionEventProvider extends ContactListener
{
    @Override
    void beginContact(Contact contact);

    @Override
    void endContact(Contact contact);

    @Override
    void preSolve(Contact contact, Manifold oldManifold);

    @Override
    void postSolve(Contact contact, ContactImpulse impulse);
}
