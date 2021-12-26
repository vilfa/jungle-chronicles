package si.vilfa.junglechronicles.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import si.vilfa.junglechronicles.Events.Event;
import si.vilfa.junglechronicles.Events.EventListener;
import si.vilfa.junglechronicles.Events.GameEvent;
import si.vilfa.junglechronicles.Gameplay.Game;
import si.vilfa.junglechronicles.Player.Human.HumanPlayer;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author luka
 * @date 23/12/2021
 * @package si.vilfa.junglechronicles.Graphics
 **/
public class GuiRenderer extends Renderer implements EventListener
{
    private final ScreenViewport guiViewport;

    private final TextureAtlas textureAtlas;
    private final HashMap<GuiElement, TextureRegion> elementTextures;

    private final FreeTypeFontGenerator fontGenerator;
    private final BitmapFont font;

    private final Stage stage;
    private final Table table;

    private final HashMap<Integer, GuiElement> digitMap;

    private HorizontalGroup scoreGroup;
    private HorizontalGroup healthGroup;
    private HorizontalGroup timeGroup;

    private float timer = 0f;

    public GuiRenderer(Game game)
    {
        super(game);

        guiViewport = new ScreenViewport();

        textureAtlas = new TextureAtlas("Graphics/HUD.atlas");
        elementTextures = new HashMap<>();
        digitMap = new HashMap<>();
        initializeGuiElements();

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(
                "Graphics/Fonts/OpenSans/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 24;
        font = fontGenerator.generateFont(fontParameter);

        stage = new Stage(guiViewport);
        table = new Table();
        stage.addActor(table);

        initializeLayout();
        initializeIndicators();
    }

    private void initializeGuiElements()
    {
        for (GuiElement guiElement : GuiElement.values())
        {
            TextureRegion textureRegion = textureAtlas.findRegion(guiElement.getRegionName());
            if (textureRegion != null)
            {
                elementTextures.put(guiElement, textureRegion);
            } else
            {
                error("missing gui element:" + guiElement.getRegionName());
            }
        }

        for (GuiElement guiDigitElement : GuiElement.getDigits())
        {
            digitMap.put(guiDigitElement.getNumericValue(), guiDigitElement);
        }
    }

    private void initializeLayout()
    {
        table.align(Align.center);
        table.defaults().expand().pad(12f);
        table.setFillParent(true);

        scoreGroup = new HorizontalGroup();
        healthGroup = new HorizontalGroup();
        timeGroup = new HorizontalGroup();

        table.add(scoreGroup).align(Align.topLeft).height(64f).colspan(3);
        table.add(timeGroup).align(Align.top | Align.center).height(64f).colspan(3);
        table.add(healthGroup).align(Align.topRight).height(64f).colspan(3);
    }

    private void initializeIndicators()
    {
        updateScoreIndicator(0f);
        updateHealthIndicator((float) HumanPlayer.MAX_LIVES + 1f);
        updateTimeIndicator(0f);
    }

    private void updateScoreIndicator(float score)
    {
        scoreGroup.clear();
        Array<Image> images = numToImages(Math.round(score));
        table.getCell(scoreGroup).width(images.size * 64f);
        images.forEach(v -> scoreGroup.addActor(v));
    }

    private void updateHealthIndicator(float health)
    {
        healthGroup.clear();
        Array<Image> images = healthToImages(health);
        table.getCell(scoreGroup).width(images.size * 64f);
        images.forEach(v -> healthGroup.addActor(v));
    }

    private void updateTimeIndicator(float time)
    {
        timeGroup.clear();
        Array<Image> images = timeToImages(time);
        table.getCell(timeGroup).width(images.size * 64f);
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
            images.add(new Image(elementTextures.get(GuiElement.HEART_FULL)));
            livesAdded++;
        }

        if (health / 0.5f - (float) Math.floor(health / 0.5f) >= 0.5f)
        {
            images.add(new Image(elementTextures.get(GuiElement.HEART_HALF)));
            livesAdded++;
        }

        for (int i = 0; i < HumanPlayer.MAX_LIVES + 1 - livesAdded; i++)
        {
            images.add(new Image(elementTextures.get(GuiElement.HEART_EMPTY)));
        }

        return images;
    }

    private Array<Image> timeToImages(float time)
    {
        int tSeconds = Math.round(time);
        return numToImages(tSeconds);
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

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
        guiViewport.update(width, height, true);
    }

    @Override
    public void draw()
    {
        stage.draw();
    }

    @Override
    public void dispose()
    {
        super.dispose();
        stage.dispose();
        textureAtlas.dispose();
        fontGenerator.dispose();
        font.dispose();
    }

    @Override
    public void update()
    {
        timer += gameTime.getDeltaTime();
        if (timer > 1f)
        {
            updateTimeIndicator(game.getCurrentLevelDuration());
            timer = 0f;
        }
        stage.act();
    }

    private enum GuiElement
    {
        ZERO(0, "hud0"),
        ONE(1, "hud1"),
        TWO(2, "hud2"),
        THREE(3, "hud3"),
        FOUR(4, "hud4"),
        FIVE(5, "hud5"),
        SIX(6, "hud6"),
        SEVEN(7, "hud7"),
        EIGHT(8, "hud8"),
        NINE(9, "hud9"),
        HEART_EMPTY(0, "hudHeart_empty"),
        HEART_HALF(50, "hudHeart_half"),
        HEART_FULL(100, "hudHeart_full"),
        JEWEL_BLUE_FULL(-1, "hudJewel_blue"),
        JEWEL_BLUE_EMPTY(-1, "hudJewel_blue_empty"),
        JEWEL_GREEN_FULL(-1, "hudJewel_green"),
        JEWEL_GREEN_EMPTY(-1, "hudJewel_green_empty"),
        JEWEL_RED_FULL(-1, "hudJewel_red"),
        JEWEL_RED_EMPTY(-1, "hudJewel_red_empty"),
        JEWEL_YELLOW_FULL(-1, "hudJewel_yellow"),
        JEWEL_YELLOW_EMPTY(-1, "hudJewel_yellow_empty"),
        KEY_BLUE_FULL(-1, "hudKey_blue"),
        KEY_BLUE_EMPTY(-1, "hudKey_blue_empty"),
        KEY_GREEN_FULL(-1, "hudKey_green"),
        KEY_GREEN_EMPTY(-1, "hudKey_green_empty"),
        KEY_RED_FULL(-1, "hudKey_red"),
        KEY_RED_EMPTY(-1, "hudKey_red_empty"),
        KEY_YELLOW_FULL(-1, "hudKey_yellow"),
        KEY_YELLOW_EMPTY(-1, "hudKey_yellow_empty");

        private final int numericValue;
        private final String regionName;

        GuiElement(int numericValue, String regionName)
        {
            this.numericValue = numericValue;
            this.regionName = regionName;
        }

        public static GuiElement[] getDigits()
        {
            return new GuiElement[]{ ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE };
        }

        public static GuiElement[] getLives()
        {
            return new GuiElement[]{ HEART_FULL, HEART_HALF, HEART_EMPTY };
        }

        public int getNumericValue()
        {
            return numericValue;
        }

        public String getRegionName()
        {
            return regionName;
        }
    }
}
