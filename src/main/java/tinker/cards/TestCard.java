package tinker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tinker.TinkerMod;
import tinker.actions.AssembleFromRandomAction;
import tinker.parts.AbstractPart;
import tinker.parts.ContraptionCard;
import tinker.parts.cores.BeamCore;
import tinker.parts.frames.HeavyFrame;
import tinker.parts.platings.GlassPlating;
import tinker.util.PartHelper;

import java.util.ArrayList;

public class TestCard extends CustomCard {

    public TestCard() {
        super("TestCard","", TinkerMod.MISSING, 0, "Assemble a Contraption from random parts.", CardType.SKILL, CardColor.COLORLESS,CardRarity.SPECIAL, CardTarget.NONE);
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AssembleFromRandomAction());
    }
}
