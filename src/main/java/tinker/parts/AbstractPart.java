package tinker.parts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractPart {

    public final String part_id;
    public final PartType type;
    public final UIStrings uiStrings;
    public final String[] previewStrings;
    public final String[] contraptionStrings;

    public AbstractPart(String ID, PartType type) {
        part_id = ID;
        this.type = type;
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        previewStrings = uiStrings.EXTRA_TEXT;
        contraptionStrings = uiStrings.TEXT;
    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


    //
    //hooks
    //
    //I wish I knew of a neater way to do it tbh
    //I just don't like how they're all in a mess
    //Guess I can just organize them but
    //

    //called during assembly, in that order
    public void onCreation(ContraptionCard c) {}
    public int potencyChange() {return 0;}
    public void applyStats(ContraptionCard c) {}
    //end

    //called to make basic properties of the card
    public int costChange() {return 0;}
    public AbstractCard.CardType leastRestrictedAcceptableType() {return AbstractCard.CardType.SKILL;}
    public AbstractCard.CardTarget minimalTarget() {return AbstractCard.CardTarget.NONE;}
    //



    //called after the card is created, can be during combat
    public void useEffect(AbstractPlayer p, AbstractMonster m, ContraptionCard c) {}
    public void onUpgradeCard(ContraptionCard c) {}
    public void triggerWhenDrawn(ContraptionCard c) {}
    //end

    //
    //end hooks
    //


    @Override
    public boolean equals(Object p) {
        return p instanceof AbstractPart && part_id.equals(((AbstractPart) p).part_id);
    }

    public enum PartType {
        FRAME,
        CORE,
        PLATING
    }
}
