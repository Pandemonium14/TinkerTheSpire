package tinker.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import tinker.TinkerMod;
import tinker.parts.AbstractPart;
import tinker.parts.PartReward;
import tinker.util.PartHelper;
import tinker.util.TexLoader;

import java.util.ArrayList;

public class PartDropPatches {

    @SpirePatch(clz = AbstractRoom.class, method = SpirePatch.CLASS)
    public static class PartToDrop {
        public static SpireField<AbstractPart.PartType> type = new SpireField<AbstractPart.PartType>(() -> null);
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "generateMap")
    public static class AddPartDropsToMap {
        @SpirePostfixPatch
        public static void addPartDrops() {
            for (ArrayList<MapRoomNode> floor : AbstractDungeon.map) {
                for (MapRoomNode node : floor) {
                    if (  node.room instanceof MonsterRoom &&
                        !(node.room instanceof MonsterRoomElite) &&
                        !(node.room instanceof MonsterRoomBoss)) {

                        AbstractPart.PartType type = PartHelper.rollTypeForMap();
                        PartToDrop.type.set(node.room, type);
                    }
                }
            }
        }
    }

    @SpirePatch2(clz = MapRoomNode.class, method = "render")
    public static class NodeRenderPatch {

        private static final TextureRegion CIRCLE = new TextureRegion(TexLoader.getTexture(TinkerMod.makeImagePath("icon/mapCircle.png")));
        private static final TextureRegion SQUARE = new TextureRegion(TexLoader.getTexture(TinkerMod.makeImagePath("icon/mapSquare.png")));
        private static final TextureRegion TRIANGLE =new TextureRegion(TexLoader.getTexture(TinkerMod.makeImagePath("icon/mapTriangle.png")));

        @SpirePrefixPatch
        public static void renderDroppedPart(MapRoomNode __instance, SpriteBatch sb) {
            if (__instance.room != null) {
                AbstractPart.PartType type = PartToDrop.type.get(__instance.room);
                if (type != null) {
                    TextureRegion tex = CIRCLE;
                    switch (type) {
                        case PLATING: {
                            tex = TRIANGLE;
                            break;
                        }
                        case CORE: {
                            tex = CIRCLE;
                            break;
                        }
                        case FRAME: {
                            tex = SQUARE;
                            break;
                        }
                    }
                    float scale = ReflectionHacks.getPrivate(__instance, MapRoomNode.class, "scale");
                    float SPACING_X = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "SPACING_X");
                    float OFFSET_X = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_X");
                    float OFFSET_Y = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_Y");

                    float x = __instance.x * SPACING_X + OFFSET_X - 64f + __instance.offsetX;
                    float y = __instance.y * Settings.MAP_DST_Y + OFFSET_Y + DungeonMapScreen.offsetY - 64f + __instance.offsetY;

                    sb.setColor(Color.WHITE);
                    sb.draw(tex,x,y,tex.getRegionWidth()/2f,tex.getRegionHeight()/2f,tex.getRegionWidth(),tex.getRegionHeight(),scale,scale,0f);

                }
            }
        }
    }

    @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class RewardPatch {
        @SpirePostfixPatch
        public static void addModifiersRewards(CombatRewardScreen __instance) {
            AbstractRoom room = AbstractDungeon.getCurrRoom();
            if (room == null) return;

            if (PartToDrop.type.get(room) != null) {
                __instance.rewards.add(new PartReward(PartToDrop.type.get(room)));
            }
        }
    }
}
