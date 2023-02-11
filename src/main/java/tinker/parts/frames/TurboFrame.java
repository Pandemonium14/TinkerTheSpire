package tinker.parts.frames;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class TurboFrame extends AbstractPart {

    public static final String ID = TinkerMod.makeID("TurboFrame");

    public TurboFrame() {
        super(ID, PartType.FRAME);
    }

    @Override
    public int potencyChange() {
        return 3;
    }

    @Override
    public int costChange() {
        return 0;
    }

    @Override
    public void onCreation(ContraptionCard c) {
        c.cardsToPreview = new VoidCard();
    }

    @Override
    public void useEffect(AbstractPlayer p, AbstractMonster m, ContraptionCard c) {
        addToBot(new MakeTempCardInDiscardAction(new VoidCard(),1));
    }

    @Override
    public Color colorToApply() {
        return new Color(0.2f,1f,1f,1f);
    }
}
