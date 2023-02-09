package tinker.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import tinker.parts.AbstractPart;
import tinker.parts.PartDisplayCard;
import tinker.util.PartHelper;

import java.util.ArrayList;

public class AssembleFromRandomAction extends AbstractGameAction {

    public static boolean isActive = false;
    private static AbstractPart chosenFrame;
    private static AbstractPart chosenCore;
    private static AbstractPart chosenPlating;

    public AssembleFromRandomAction() {
        duration=startDuration= Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            chosenPlating = null;
            chosenFrame = null;
            chosenCore = null;
        }
        isActive = true;
        if (chosenFrame == null) {
            ArrayList<AbstractPart> partOptions = new ArrayList<>();
            while (partOptions.size() < 3) {
                AbstractPart p = PartHelper.getRandomFrame(AbstractDungeon.cardRandomRng);
                if (!partOptions.contains(p)) {
                    partOptions.add(p);
                }
            }
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (AbstractPart p : partOptions) {
                options.add(new PartDisplayCard(p));
            }
            AbstractDungeon.cardRewardScreen.chooseOneOpen(options);
            tickDuration();
            return;
        }
        if (chosenCore == null) {
            ArrayList<AbstractPart> partOptions = new ArrayList<>();
            while (partOptions.size() < 3) {
                AbstractPart p = PartHelper.getRandomCore(AbstractDungeon.cardRandomRng);
                if (!partOptions.contains(p)) {
                    partOptions.add(p);
                }
            }
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (AbstractPart p : partOptions) {
                options.add(new PartDisplayCard(p));
            }
            AbstractDungeon.cardRewardScreen.chooseOneOpen(options);
            tickDuration();
            return;
        }
        if (chosenPlating == null) {
            ArrayList<AbstractPart> partOptions = new ArrayList<>();
            while (partOptions.size() < 3) {
                AbstractPart p = PartHelper.getRandomPlating(AbstractDungeon.cardRandomRng);
                if (!partOptions.contains(p)) {
                    partOptions.add(p);
                }
            }
            ArrayList<AbstractCard> options = new ArrayList<>();
            for (AbstractPart p : partOptions) {
                options.add(new PartDisplayCard(p));
            }
            AbstractDungeon.cardRewardScreen.chooseOneOpen(options);
            tickDuration();
            return;
        }
        ArrayList<AbstractPart> parts = new ArrayList<>();
        parts.add(chosenFrame);
        parts.add(chosenCore);
        parts.add(chosenPlating);
        addToTop(new MakeTempCardInHandAction(PartHelper.assembleCard(parts)));
        isActive = false;
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
}
