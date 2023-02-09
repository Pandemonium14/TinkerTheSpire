package tinker.cards;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import tinker.TinkerMod;
import tinker.util.TexLoader;

public class LevelIcon extends AbstractCustomIcon {

    public static final String ID = TinkerMod.makeID("Level");
    private static LevelIcon singleton;

    public LevelIcon() {
        super(ID, TexLoader.getTexture(TinkerMod.makeImagePath("icon/smallLevelIcon.png")));
    }

    public static LevelIcon get()
    {
        if (singleton == null) {
            singleton = new LevelIcon();
        }
        return singleton;
    }
}
