package tinker.parts;

import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.actions.AssembleFromRandomAction;
import tinker.parts.frames.HeavyFrame;


@NoCompendium
public class PartDisplayCard extends CustomCard {

    public static final String ID = TinkerMod.makeID("PartDisplayCard");
    public AbstractPart part;

    public PartDisplayCard() {
        super(ID,"", TinkerMod.MISSING, -2, "Part display card with no part (Does nothing, but defaulted to Heavy Frame for null safety)", CardType.SKILL, CardColor.COLORLESS,CardRarity.SPECIAL, CardTarget.NONE);
        part = new HeavyFrame();
    }

    public PartDisplayCard(AbstractPart part) {
        super(ID,part.previewStrings[0], TinkerMod.MISSING, -2, part.previewStrings[1], CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.part = part;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void onChoseThisOption() {
        if (AssembleFromRandomAction.isActive) {
            AssembleFromRandomAction.receivePart(part);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new PartDisplayCard(part);
    }
}
