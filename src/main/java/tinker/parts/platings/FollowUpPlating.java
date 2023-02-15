package tinker.parts.platings;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.FollowUpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class FollowUpPlating extends AbstractPart {

    public static final String ID = TinkerMod.makeID("FollowUpPlating");

    public FollowUpPlating() {
        super(ID, PartType.PLATING);
    }

    @Override
    public int potencyChange() {
        return 0;
    }

    @Override
    public void useEffect(AbstractPlayer p, AbstractMonster m, ContraptionCard c) {
        addToBot(new FollowUpAction());
    }

    @Override
    public Color colorToApply() {
        return new Color(0.6f,0f,0.7f,0.55f);
    }
}
