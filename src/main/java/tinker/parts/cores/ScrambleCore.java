package tinker.parts.cores;

import basemod.Pair;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;

public class ScrambleCore extends AbstractPart {

    public static final String ID = TinkerMod.makeID("ScrambleCore");

    public ScrambleCore() {
        super(ID, PartType.CORE);
    }

    @Override
    public int potencyChange() {
        return -1;
    }

    @Override
    public void applyStats(ContraptionCard c) {
        c.magicNumber = c.baseMagicNumber = c.potency;
    }

    @Override
    public void onUpgradeCard(ContraptionCard c) {
        c.baseMagicNumber += c.potency;
        c.magicNumber = c.baseMagicNumber;;
    }


    @Override
    public void useEffect(AbstractPlayer p, AbstractMonster m, ContraptionCard c) {
        addToBot(new ApplyPowerAction(m,p,new StrengthPower(m, -c.magicNumber)));
    }

    @Override
    public AbstractCard.CardTarget minimalTarget() {
        return AbstractCard.CardTarget.ENEMY;
    }

    @Override
    public Texture basePortrait() {
        return getBaseGamePortrait("red/skill/intimidate");
    }

    @Override
    public float splitAngle() {
        return 110f;
    }

    @Override
    public Pair<Color, Color> anchorColors() {
        return new Pair<>(
                new Color(106f/255f,128f/255f,46f/255f, 1f),
                new Color(34f/255f,31f/255f,51f/255f, 1f));
    }
}
