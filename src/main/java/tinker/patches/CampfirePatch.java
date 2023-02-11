package tinker.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import tinker.ui.TinkerCampfireOption;

import java.util.ArrayList;

@SpirePatch2(clz = CampfireUI.class, method = "initializeButtons")
public class CampfirePatch {

    @SpirePostfixPatch
    public static void modifyOptions(CampfireUI __instance) {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        ArrayList<AbstractCampfireOption> options = ReflectionHacks.getPrivate(__instance, CampfireUI.class, "buttons");
        options.add(new TinkerCampfireOption());
    }
}
