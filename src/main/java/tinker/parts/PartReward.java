package tinker.parts;

import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import jdk.javadoc.internal.doclets.formats.html.PackageUseWriter;
import tinker.TinkerMod;
import tinker.patches.Enums;
import tinker.util.TexLoader;

public class PartReward extends CustomReward {

    private static final Texture CIRCLE = TexLoader.getTexture(TinkerMod.makeImagePath("icon/rewardCircle.png"));
    private static final Texture SQUARE = TexLoader.getTexture(TinkerMod.makeImagePath("icon/rewardSquare.png"));
    private static final Texture TRIANGLE = TexLoader.getTexture(TinkerMod.makeImagePath("icon/rewardTriangle.png"));

    private static final UIStrings strings = CardCrawlGame.languagePack.getUIString(TinkerMod.makeID("PartReward"));

    public AbstractPart.PartType partType;

    public PartReward(AbstractPart.PartType type) {
        super(CIRCLE, strings.TEXT[0], Enums.TINKER_PART_REWARD);
        partType = type;
        switch (type) {
            case PLATING: {
                icon = TRIANGLE;
                text = strings.TEXT[2];
                break;
            }
            case CORE: {
                icon = CIRCLE;
                text = strings.TEXT[1];
                break;
            }
            case FRAME: {
                icon = SQUARE;
                text = strings.TEXT[0];
                break;
            }
        }
    }

    @Override
    public boolean claimReward() {
        return false;
    }





    public static AbstractPart.PartType stringToType(String str) {
        switch (str) {
            case "PLATING": {
                return AbstractPart.PartType.PLATING;
            }
            case "CORE": {
                return AbstractPart.PartType.CORE;
            }
            case "FRAME": {
                return AbstractPart.PartType.FRAME;
            }
            default: return AbstractPart.PartType.FRAME;
        }
    }
}
