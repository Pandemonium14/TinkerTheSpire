package tinker.parts.cores;

import basemod.Pair;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class HolographicCore extends AbstractPart {

    public static final String ID = TinkerMod.makeID("HolographicCore");

    public HolographicCore() {
        super(ID, PartType.CORE);
    }

    @Override
    public void applyStats(ContraptionCard c) {
        c.baseMagicNumber = c.magicNumber = c.potency;
    }

    @Override
    public void onUpgradeCard(ContraptionCard c) {
        c.baseMagicNumber += c.potency;
        c.magicNumber = c.baseMagicNumber;
    }


    @Override
    public void useEffect(AbstractPlayer p, AbstractMonster m, ContraptionCard c) {
        addToBot(new BetterDiscardPileToHandAction(c.magicNumber));
    }

    @Override
    public AbstractCard.CardTarget minimalTarget() {
        return AbstractCard.CardTarget.SELF;
    }

    @Override
    public Texture basePortrait() {
        return getBaseGamePortrait("blue/skill/hologram");
    }

    @Override
    public float splitAngle() {
        return 40f;
    }

    @Override
    public Pair<Color, Color> anchorColors() {
        return new Pair<>(
                new Color(176f/255f,165f/255f,130f/255f, 1f),
                new Color(2f/255f,15f/255f,31f/255f, 1f));
    }
}
