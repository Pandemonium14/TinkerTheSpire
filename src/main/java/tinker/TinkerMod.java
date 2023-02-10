package tinker;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rewards.RewardSave;
import tinker.cards.LevelIcon;
import tinker.cards.TestCard;
import tinker.parts.AbstractPart;
import tinker.parts.PartReward;
import tinker.patches.Enums;
import tinker.ui.PartPouch;
import tinker.util.PartHelper;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class TinkerMod implements PostInitializeSubscriber, EditStringsSubscriber, EditCardsSubscriber {

    public static final String modID = "tinkermod";

    public static final String MISSING = "tinkermodResources/images/ui/missing.png";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public TinkerMod() {
        BaseMod.subscribe(this);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static void initialize() {
        TinkerMod thismod = new TinkerMod();
    }

    @Override
    public void receivePostInitialize() {
        new AutoAdd(modID).packageFilter("tinker.parts.cores").any(AbstractPart.class, (info,part) -> PartHelper.coreList.add(part));
        new AutoAdd(modID).packageFilter("tinker.parts.frames").any(AbstractPart.class, (info,part) -> PartHelper.frameList.add(part));
        new AutoAdd(modID).packageFilter("tinker.parts.platings").any(AbstractPart.class, (info,part) -> PartHelper.platingList.add(part));

        BaseMod.addTopPanelItem(new PartPouch());

        BaseMod.registerCustomReward(Enums.TINKER_PART_REWARD,
                rewardSave -> new PartReward(PartReward.stringToType(rewardSave.type)),
                customReward -> new RewardSave(((PartReward)customReward).partType.toString(),null, 0,0));
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(UIStrings.class, makePath("localization/eng/UIStrings.json"));
    }

    @Override
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(LevelIcon.get());
        BaseMod.addCard(new TestCard());
    }
}
