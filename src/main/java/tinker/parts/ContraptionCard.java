package tinker.parts;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.parts.platings.SearingPlating;
import tinker.util.PartHelper;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ContraptionCard extends CustomCard {

    public static final String ID = TinkerMod.makeID("ContraptionCard");

    public int potency;

    public final ArrayList<AbstractPart> parts = new ArrayList<>();

    public ContraptionCard() {
        super(ID,"", TinkerMod.MISSING, -2, "Empty contraption Card", CardType.SKILL, CardColor.COLORLESS,CardRarity.SPECIAL, CardTarget.NONE);
    }

    public ContraptionCard(ArrayList<AbstractPart> parts, String name, String rawDesc, int cost, CardType type, String img, CardTarget target) {
        super(ID, name, img, cost, rawDesc, type, CardColor.COLORLESS, CardRarity.SPECIAL, target);
        this.parts.addAll(parts);
    }

    @Override
    public void upgrade() {
        upgradeName();
        forAllParts((p) -> p.onUpgradeCard(this));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        forAllParts((part) ->part.useEffect(abstractPlayer, abstractMonster, this));
    }

    @Override
    public boolean canUpgrade() {
        return super.canUpgrade() || parts.stream().anyMatch((part) -> SearingPlating.ID.equals(part.part_id));
    }

    @Override
    public void triggerWhenDrawn() {
        forAllParts((part) -> part.triggerWhenDrawn(this));
    }

    //util methods
    public void makeMultiDamage() {
        isMultiDamage = true;
    }

    @Override
    public AbstractCard makeCopy() {
        ContraptionCard newCard = PartHelper.assembleCard(parts);
        for (int i = 0 ; i<timesUpgraded; i++) {
            newCard.upgrade();
        }
        return newCard;
    }

    private void forAllParts(Consumer<AbstractPart> consumer) {
        for (AbstractPart part : parts) {
            consumer.accept(part);
        }
    }
}
