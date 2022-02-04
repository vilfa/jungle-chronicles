package si.vilfa.junglechronicles.Player.AI;

import com.badlogic.gdx.physics.box2d.Body;
import si.vilfa.junglechronicles.Player.AI.Agents.SimpleAgent;

/**
 * @author luka
 * @date 12/12/2021
 * @package si.vilfa.junglechronicles.Player.AI
 **/
public class Friend extends SimpleAgent
{
    private FriendSprite friendSprite;

    public Friend(Body body)
    {
        super(body);
        body.setGravityScale(0.1f);
    }

    @Override
    protected void applySteering()
    {
        body.applyLinearImpulse(steeringOutput.linear, body.getLocalCenter(), true);
        body.applyAngularImpulse(steeringOutput.angular, true);
    }

    public Friend.FriendSprite getFriendSprite()
    {
        return friendSprite;
    }

    public void setFriendSprite(Integer friendSprite)
    {
        switch (friendSprite)
        {
            case 1:
                this.friendSprite = Friend.FriendSprite.FRIEND_ONE;
                break;
            default:
                this.friendSprite = Friend.FriendSprite.FRIEND_ONE;
                break;
        }
    }

    public enum FriendSprite {
        FRIEND_ONE
    }
}
