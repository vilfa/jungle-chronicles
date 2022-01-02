package si.vilfa.junglechronicles.Graphics.Gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Graphics.Renderer;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author luka
 * @date 02/01/2022
 * @package si.vilfa.junglechronicles.Graphics.Gui
 **/
public class HudGuiElement extends GuiElement implements EventListener
{
    private final TextureAtlas textureAtlas;
    private final HashMap<HudSprite, TextureRegion> elementTextures;
    private final HashMap<Integer, HudSprite> digitMap;

    private final Table actor;

    private HorizontalGroup scoreGroup;
    private HorizontalGroup healthGroup;
    private HorizontalGroup timeGroup;

    private float timer = 0f;

    public HudGuiElement(Game game, TextureAtlas textureAtlas)
    {
        super(game);
        this.textureAtlas = textureAtlas;
        elementTextures = new HashMap<>();
        digitMap = new HashMap<>();
        actor = new Table();

        initializeElement();
    }

    @Override
    public Actor getActor()
    {
        return actor;
    }

    @Override
    protected void initializeElement()
    {
        for (HudSprite hudSprite : HudSprite.values())
        {
            TextureRegion textureRegion = textureAtlas.findRegion(hudSprite.getRegionName());
            if (textureRegion != null)
            {
                elementTextures.put(hudSprite, textureRegion);
            } else
            {
                error("missing gui element:" + hudSprite.getRegionName());
            }
        }

        for (HudSprite hudDigitSprite : HudSprite.getDigits())
        {
            digitMap.put(hudDigitSprite.getNumericValue(), hudDigitSprite);
        }

        actor.align(Align.center);
        actor.defaults().expand().pad(12f);
        actor.setFillParent(true);

        scoreGroup = new HorizontalGroup();
        healthGroup = new HorizontalGroup();
        timeGroup = new HorizontalGroup();

        actor.add(scoreGroup).align(Align.topLeft).height(64f).colspan(3);
        actor.add(timeGroup).align(Align.top | Align.center).height(64f).colspan(3);
        actor.add(healthGroup).align(Align.topRight).height(64f).colspan(3);

        updateScoreIndicator(0f);
        updateHealthIndicator((float) HumanPlayer.MAX_LIVES + 1f);
        updateTimeIndicator(0f);
    }

    @Override
    public void update()
    {
        timer += Renderer.gameTime.getDeltaTime();
        if (timer > 1f)
        {
            updateTimeIndicator(game.getCurrentLevelDuration());
            timer = 0f;
        }
    }

    @Override
    public void dispose()
    {
        textureAtlas.dispose();
    }

    @Override
    public void handleEvent(Event event)
    {
        if (event.getType().equals(GameEvent.PLAYER_HEALTH_CHANGE))
        {
            if (event.getEventData().size == 2)
            {
                float health = (float) event.getEventData().get(0)
                               + (float) event.getEventData().get(1)
                                 / (float) HumanPlayer.MAX_HEALTH;
                updateHealthIndicator(health);
            }
        } else if (event.getType().equals(GameEvent.PLAYER_SCORE_CHANGE))
        {
            if (event.getEventData().size == 1)
            {
                updateScoreIndicator((float) event.getEventData().first());
            }
        }
    }

    private void updateScoreIndicator(float score)
    {
        scoreGroup.clear();
        Array<Image> images = numToImages(Math.round(score));
        actor.getCell(scoreGroup).width(images.size * 64f);
        images.forEach(v -> scoreGroup.addActor(v));
    }

    private void updateHealthIndicator(float health)
    {
        healthGroup.clear();
        Array<Image> images = healthToImages(health);
        actor.getCell(scoreGroup).width(images.size * 64f);
        images.forEach(v -> healthGroup.addActor(v));
    }

    private void updateTimeIndicator(float time)
    {
        timeGroup.clear();
        Array<Image> images = timeToImages(time);
        actor.getCell(timeGroup).width(images.size * 64f);
        images.forEach(v -> timeGroup.addActor(v));
    }

    private Array<Image> numToImages(int num)
    {
        Stack<Integer> digits = new Stack<>();
        if (num == 0)
        {
            digits.push(0);
        } else
        {
            while (num > 0)
            {
                digits.push(num % 10);
                num /= 10;
            }
        }

        Array<Image> images = new Array<>();
        while (!digits.isEmpty())
        {
            images.add(new Image(elementTextures.get(digitMap.get(digits.pop()))));
        }

        return images;
    }

    private Array<Image> healthToImages(float health)
    {
        Array<Image> images = new Array<>();

        int livesAdded = 0;
        float fFullLives = (float) Math.floor(health);
        for (int i = 0; i < Math.round(fFullLives); i++)
        {
            images.add(new Image(elementTextures.get(HudSprite.HEART_FULL)));
            livesAdded++;
        }

        if (health / 0.5f - (float) Math.floor(health / 0.5f) >= 0.5f)
        {
            images.add(new Image(elementTextures.get(HudSprite.HEART_HALF)));
            livesAdded++;
        }

        for (int i = 0; i < HumanPlayer.MAX_LIVES + 1 - livesAdded; i++)
        {
            images.add(new Image(elementTextures.get(HudSprite.HEART_EMPTY)));
        }

        return images;
    }

    private Array<Image> timeToImages(float time)
    {
        int tSeconds = Math.round(time);
        return numToImages(tSeconds);
    }
}
