package tinker.parts.cores;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class BeamCore extends AbstractPart {

    public static final String ID = TinkerMod.makeID("BeamCore");

    public BeamCore() {
        super(ID, PartType.CORE);
    }

    @Override
    public void applyStats(ContraptionCard c) {
        c.baseDamage = c.potency * 5;
        c.makeMultiDamage();
    }

    @Override
    public void onUpgradeCard(ContraptionCard c) {
        c.baseDamage += c.potency * 2;
    }

    @Override
    public void useEffect(AbstractPlayer p, AbstractMonster m, ContraptionCard c) {
        addToBot(new DamageAllEnemiesAction(p, c.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCard.CardTarget minimalTarget() {
        return AbstractCard.CardTarget.ALL_ENEMY;
    }

    @Override
    public AbstractCard.CardType leastRestrictedAcceptableType() {
        return AbstractCard.CardType.ATTACK;
    }
}
