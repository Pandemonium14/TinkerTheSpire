package tinker.parts.cores;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class ShockwaveCore extends AbstractPart {

    public static final String ID = TinkerMod.makeID("ShockwaveCore");

    public ShockwaveCore() {
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
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, c.magicNumber, false)));
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, c.magicNumber, false)));
        }
    }

    @Override
    public AbstractCard.CardTarget minimalTarget() {
        return AbstractCard.CardTarget.ALL_ENEMY;
    }
}
