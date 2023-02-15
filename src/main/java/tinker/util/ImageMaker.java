package tinker.util;

import basemod.BaseMod;
import basemod.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.compression.lzma.Base;
import tinker.TinkerMod;
import tinker.cards.TestCard;
import tinker.parts.AbstractPart;

import java.util.ArrayList;

public class ImageMaker {

    private static ShaderProgram shader;

    private static FrameBuffer largeBuffer;
    private static SpriteBatch largeSb;
    private static OrthographicCamera largeCam = new OrthographicCamera(500, 380);

    private static FrameBuffer smallBuffer;
    private static SpriteBatch smallSb;
    private static OrthographicCamera smallCam = new OrthographicCamera(250,190);

    private static void initShader() {
        if (shader == null) {
            shader = new ShaderProgram(Gdx.files.internal("tinkermodResources/shaders/splitWheelVertex.vs"),
                    Gdx.files.internal("tinkermodResources/shaders/splitWheelFragment2.fs"));
            if (!shader.isCompiled()) {
                System.err.println(shader.getLog());
            }
            if (shader.getLog().length() > 0) {
                System.out.println(shader.getLog());
            }
        }
    }

    private static void setupShader(Color rColor, Color lColor, Pair<Color, Color> anchors) {
        shader.setUniformf("rRed", rColor.r);
        shader.setUniformf("rGreen", rColor.g);
        shader.setUniformf("rBlue", rColor.b);
        shader.setUniformf("rLumFactor", rColor.a);
        shader.setUniformf("lRed", lColor.r);
        shader.setUniformf("lGreen", lColor.g);
        shader.setUniformf("lBlue", lColor.b);
        shader.setUniformf("lLumFactor", lColor.a);
        shader.setUniformf("anchorAR", anchors.getKey().r);
        shader.setUniformf("anchorAG", anchors.getKey().g);
        shader.setUniformf("anchorAB", anchors.getKey().b);
        shader.setUniformf("anchorBR", anchors.getValue().r);
        shader.setUniformf("anchorBG", anchors.getValue().g);
        shader.setUniformf("anchorBB", anchors.getValue().b);
    }

    public static Texture[] makeImage(ArrayList<AbstractPart> parts) {
        AbstractPart core = null;
        AbstractPart frame = null;
        AbstractPart plating = null;
        for (AbstractPart p : parts) {
            switch (p.type) {
                case PLATING: {
                    plating = p;
                    break;
                }
                case CORE: {
                    core = p;
                    break;
                }
                case FRAME: {
                    frame = p;
                    break;
                }
            }
        }
        if (core != null && frame != null && plating != null) {
            return makeImage(core,frame,plating);
        } else {
            return new Texture[]{TexLoader.getTexture(TinkerMod.MISSING),TexLoader.getTexture(TinkerMod.MISSING)};
        }
    }

    public static Texture[] makeImage(AbstractPart core, AbstractPart frame, AbstractPart plating) {
        initShader();
        beginLargeBuffer();
        largeSb.setShader(shader);
        setupShader(frame.colorToApply(),plating.colorToApply(), core.anchorColors());
        TextureRegion region = new TextureRegion(core.basePortrait());
        region.flip(false,true);
        largeSb.draw(region,-250,-190);
        TextureRegion large_image = new TextureRegion(largeBuffer.getColorBufferTexture());
        endLargeBuffer();

        BaseMod.logger.info(large_image.getRegionWidth() +" , "+ large_image.getRegionHeight());
        large_image.flip(false,true);

        beginSmallBuffer();
        smallSb.draw(large_image,-125,-95,250,190);
        endSmallBuffer();

        TextureRegion small_image = new TextureRegion(smallBuffer.getColorBufferTexture());
        small_image.flip(false,true);

        Texture[] textures = new Texture[2];
        textures[0] = large_image.getTexture();
        textures[1] = small_image.getTexture();
        return textures;
    }

    public static Texture makeLargeImage(ArrayList<AbstractPart> parts) {
        AbstractPart core = null;
        AbstractPart frame = null;
        AbstractPart plating = null;
        for (AbstractPart p : parts) {
            switch (p.type) {
                case PLATING: {
                    plating = p;
                    break;
                }
                case CORE: {
                    core = p;
                    break;
                }
                case FRAME: {
                    frame = p;
                    break;
                }
            }
        }
        if (core != null && frame != null && plating != null) {
            return makeLargeImage(core,frame,plating);
        } else {
            return TexLoader.getTexture(TinkerMod.MISSING);
        }
    }

    public static Texture makeLargeImage(AbstractPart core, AbstractPart frame, AbstractPart plating) {
        initShader();

        beginLargeBuffer();
        largeSb.setShader(shader);
        setupShader(frame.colorToApply(),plating.colorToApply(), core.anchorColors());
        TextureRegion region = new TextureRegion(core.basePortrait());
        region.flip(false,true);
        largeSb.draw(region,-250,-190);
        TextureRegion large_image = new TextureRegion(largeBuffer.getColorBufferTexture());
        endLargeBuffer();

        BaseMod.logger.info(large_image.getRegionWidth() +" , "+ large_image.getRegionHeight());

        return large_image.getTexture();
    }

    private static void beginLargeBuffer() {
        largeBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,500,380,false);

        if (largeSb == null) {
            largeSb = new SpriteBatch();
            largeSb.setProjectionMatrix(largeCam.combined);
            largeSb.setColor(Color.WHITE);
        }
        BaseMod.logger.info("Starting Buffer");
        largeSb.begin();
        largeBuffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
    }

    private static void endLargeBuffer() {
        BaseMod.logger.info("Ending buffer");
        largeSb.flush();
        largeSb.end();
        largeBuffer.end();
    }

    private static void beginSmallBuffer() {
        smallBuffer = new FrameBuffer(Pixmap.Format.RGBA8888,250,190,false);

        if (smallSb == null) {
            smallSb = new SpriteBatch();
            smallSb.setProjectionMatrix(smallCam.combined);
            smallSb.setColor(Color.WHITE);
        }
        smallSb.begin();
        smallBuffer.begin();
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glColorMask(true, true, true, true);
    }

    private static void endSmallBuffer() {
        smallSb.flush();
        smallSb.end();
        smallBuffer.end();

    }
}
