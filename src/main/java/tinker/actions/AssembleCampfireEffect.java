package tinker.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import tinker.parts.AbstractPart;
import tinker.parts.PartDisplayCard;
import tinker.util.PartHelper;

import java.util.ArrayList;

public class AssembleCampfireEffect extends AbstractGameEffect {



    public static boolean isActive = false;
    private static AbstractPart chosenFrame;
    private static AbstractPart chosenCore;
    private static AbstractPart chosenPlating;


    @Override
    public void update() {
        if (!isActive) {
            chosenPlating = null;
            chosenFrame = null;
            chosenCore = null;
        }
        isActive = true;
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.CARD_REWARD) return;
        if (chosenFrame == null) {
            ArrayList<AbstractPart> partOptions = new ArrayList<>();
            while (partOptions.size() < 3) {
                AbstractPart p = PartHelper.getRandomFrame(AbstractDungeon.cardRng);
                if (!partOptions.contains(p)) {
                    partOptions.add(p);
                }
            }
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (AbstractPart p : partOptions) {
                options.add(new PartDisplayCard(p));
            }
            AbstractDungeon.cardRewardScreen.chooseOneOpen(options);
            return;
        }
        if (chosenCore == null) {
            ArrayList<AbstractPart> partOptions = new ArrayList<>();
            while (partOptions.size() < 3) {
                AbstractPart p = PartHelper.getRandomCore(AbstractDungeon.cardRng);
                if (!partOptions.contains(p)) {
                    partOptions.add(p);
                }
            }
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (AbstractPart p : partOptions) {
                options.add(new PartDisplayCard(p));
            }
            AbstractDungeon.cardRewardScreen.chooseOneOpen(options);
            return;
        }
        if (chosenPlating == null) {
            ArrayList<AbstractPart> partOptions = new ArrayList<>();
            while (partOptions.size() < 3) {
                AbstractPart p = PartHelper.getRandomPlating(AbstractDungeon.cardRng);
                if (!partOptions.contains(p)) {
                    partOptions.add(p);
                }
            }
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (AbstractPart p : partOptions) {
                options.add(new PartDisplayCard(p));
            }
            AbstractDungeon.cardRewardScreen.chooseOneOpen(options);
            return;
        }
        ArrayList<AbstractPart> parts = new ArrayList<>();
        parts.add(chosenFrame);
        parts.add(chosenCore);
        parts.add(chosenPlating);
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(PartHelper.assembleCard(parts),Settings.WIDTH/2f, Settings.HEIGHT/2f));
        isActive = false;
        ((RestRoom)AbstractDungeon.getCurrRoom()).campfireUI.reopen();
        isDone = true;
    }

    public static void receivePart(AbstractPart part) {
        switch (part.type) {
            case CORE: {
                chosenCore = part;
                break;
            }
            case FRAME: {
                chosenFrame = part;
                break;
            }
            case PLATING: {
                chosenPlating = part;
                break;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
