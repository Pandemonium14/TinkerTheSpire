package tinker.parts.platings;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class ReplicatorPlating extends AbstractPart {
    public static final String ID = TinkerMod.makeID("ReplicatorPlating");

    public ReplicatorPlating() {
        super(ID, PartType.PLATING);
    }

    @Override
    public int potencyChange() {
        return -1;
    }

    @Override
    public void useEffect(AbstractPlayer p, AbstractMonster m,ContraptionCard c) {
        addToBot(new MakeTempCardInDrawPileAction(c.makeStatEquivalentCopy(),1,true,true));
    }
}
