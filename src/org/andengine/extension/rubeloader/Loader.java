package org.andengine.extension.rubeloader;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.rubeloader.def.ImageDef;
import org.andengine.extension.rubeloader.def.RubeDef;
import org.andengine.extension.rubeloader.parser.RubeParser;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.StreamUtils;
import org.andengine.util.adt.io.in.ResourceInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;

import android.content.res.Resources;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Simple example of a customr R.U.B.E. loader
 * @author Michal Stawinski (nazgee)
 */
public class Loader {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private RubeParser mRubeParser;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Loader() {
		this.mRubeParser = new RubeParser();
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	protected void loadImages(IEntity pSceneEntity, ITextureProvider pTextureProvider, VertexBufferObjectManager pVBOM, RubeDef pRubeDef) {
		Vector<ImageDef> imgs = pRubeDef.primitives.images;
		for (ImageDef image : imgs) {
			final float p2m = getPixelToMeterRatio(image);
			final ITextureRegion region = pTextureProvider.get(new File(image.file).getName());
			final float scale = image.scale * p2m / region.getHeight();
			final float w = region.getWidth() * scale;
			final float h = region.getHeight() * scale;
			final float x = image.center.x * p2m;
			final float y = image.center.y * p2m;

			IEntity entity = loadImage(pSceneEntity, pVBOM, pRubeDef, image, region, w, h, x, y);

			if (entity != null) {
				pSceneEntity.attachChild(entity);
			}
		}
	}

	protected IEntity loadImage(IEntity pSceneEntity, VertexBufferObjectManager pVBOM, RubeDef pRube, ImageDef image, final ITextureRegion region,
			final float w, final float h, final float x, final float y) {
		Sprite sprite = populateSprite(region, pVBOM, (int)image.renderOrder, image.angle, w, h, x, y);

		if (image.body != null) {
			sprite.setAnchorCenter(-x / sprite.getWidth() + 0.5f, -y / sprite.getHeight() + 0.5f);

			PhysicsConnector connector = populatePhysicsConnector(pRube, image.body, sprite);
			image.body.setUserData(connector);
			sprite.setUserData(connector);
			pRube.world.registerPhysicsConnector(connector);
		}

		return sprite;
	}

	/**
	 * Create sprite which will handle
	 * @param region
	 * @param pVBOM
	 * @param pZindex
	 * @param pAngle
	 * @param pWidth
	 * @param pHeight
	 * @param pX
	 * @param pY
	 * @return
	 */
	protected Sprite populateSprite(final ITextureRegion region, VertexBufferObjectManager pVBOM, final int pZindex, final float pAngle, final float pWidth,
			final float pHeight, final float pX, final float pY) {
		Sprite sprite = new UncoloredSprite(pX, pY, pWidth, pHeight, region, pVBOM);
		sprite.setRotationOffset(MathUtils.radToDeg(-pAngle));
		sprite.setCullingEnabled(true);
		sprite.setZIndex(pZindex);
		return sprite;
	}

	protected PhysicsConnector populatePhysicsConnector(RubeDef pRube, Body pBody, IEntity pEntity) {
		return new PhysicsConnector(pEntity, pBody);
	}

	protected float getPixelToMeterRatio(ImageDef pImage) {
		return PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public RubeDef loadMoreToExistingWorld(final Resources pResources, final IEntity pSceneEntity, final ITextureProvider pTextureProvider, final VertexBufferObjectManager pVBOM, int resId, final PhysicsWorld pWorld) {
		long startTime = System.currentTimeMillis();

		RubeDef rube;
		try {
			rube = mRubeParser.continueParse(pWorld, readResource(resId, pResources));
		} catch (ParseException e) {
			throw new RuntimeException("RUBE json parsing failed! ", e);
		}
		loadImages(pSceneEntity, pTextureProvider, pVBOM, rube);

		long elapseTime = System.currentTimeMillis() - startTime;
		Debug.w("RubeLoaderExtension LOAD_TIME=" + elapseTime/1000.f);

		return rube;
	}

	public RubeDef load(final Resources pResources, final IEntity pSceneEntity, final ITextureProvider pTextureProvider, final VertexBufferObjectManager pVBOM, int resId) {
		long startTime = System.currentTimeMillis();

		RubeDef rube;
		try {
			rube = mRubeParser.parse(readResource(resId, pResources));
		} catch (ParseException e) {
			throw new RuntimeException("RUBE json parsing failed! ", e);
		}
		loadImages(pSceneEntity, pTextureProvider, pVBOM, rube);

		long elapseTime = System.currentTimeMillis() - startTime;
		Debug.w("RubeLoaderExtension LOAD_TIME=" + elapseTime/1000.f);

		return rube;
	}

	public static String readResource(int resId, Resources pResources) {
		try {
			return StreamUtils.readFully(new ResourceInputStreamOpener(pResources, resId).open());
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static interface ITextureProvider {
		public ITextureRegion get(final String pFileName);
	}
}
