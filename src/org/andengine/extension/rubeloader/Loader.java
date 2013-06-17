package org.andengine.extension.rubeloader;

import java.io.IOException;

import net.minidev.json.parser.ParseException;

import org.andengine.entity.IEntity;
import org.andengine.extension.rubeloader.def.RubeDef;
import org.andengine.extension.rubeloader.factory.EntityFactory;
import org.andengine.extension.rubeloader.factory.IPhysicsWorldProvider;
import org.andengine.extension.rubeloader.parser.RubeParser;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.StreamUtils;
import org.andengine.util.adt.io.in.ResourceInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.content.res.Resources;

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
	private EntityFactory mEntityFactory;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Loader() {
		this(new EntityFactory());
	}

	public Loader(final EntityFactory pEntityFactory) {
		this.mEntityFactory = pEntityFactory;
		this.mRubeParser = new RubeParser(this.mEntityFactory);
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public RubeDef loadMoreToExistingWorld(final Resources pResources, final IEntity pSceneEntity, final ITextureProvider pTextureProvider, final VertexBufferObjectManager pVBOM, int resId, final IPhysicsWorldProvider pPhysicsWorldProvider) {
		long startTime = System.currentTimeMillis();

		this.mEntityFactory.configure(pSceneEntity, pTextureProvider, pVBOM);

		RubeDef rube;
		try {
			rube = mRubeParser.continueParse(pPhysicsWorldProvider, readResource(resId, pResources));
		} catch (ParseException e) {
			throw new RuntimeException("RUBE json parsing failed! ", e);
		}

		long elapseTime = System.currentTimeMillis() - startTime;
		Debug.w("RubeLoaderExtension LOAD_TIME=" + elapseTime/1000.f);

		return rube;
	}

	public RubeDef load(final Resources pResources, final IEntity pSceneEntity, final ITextureProvider pTextureProvider, final VertexBufferObjectManager pVBOM, int resId) {
		long startTime = System.currentTimeMillis();

		this.mEntityFactory.configure(pSceneEntity, pTextureProvider, pVBOM);

		RubeDef rube;
		try {
			rube = mRubeParser.parse(readResource(resId, pResources));
		} catch (ParseException e) {
			throw new RuntimeException("RUBE json parsing failed! ", e);
		}

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

//	protected void handleImages(VertexBufferObjectManager pVBOM, IEntity pSceneEntity, ITextureProvider pTextureProvider, RubeDef pRubeDef) {
//		Vector<ImageDef> imgs = pRubeDef.primitives.images;
//		for (ImageDef image : imgs) {
//			final float p2m = getPixelToMeterRatio(image);
//			final ITextureRegion region = pTextureProvider.get(new File(image.file).getName());
//			final float scale = image.scale * p2m / region.getHeight();
//			final float w = region.getWidth() * scale;
//			final float h = region.getHeight() * scale;
//			final float x = image.center.x * p2m;
//			final float y = image.center.y * p2m;
//
//			handleImage(x, y, w, h, region, pVBOM, pSceneEntity, pTextureProvider, pRubeDef, image);
//		}
//	}
//
//	protected void handleImage(float x, float y, float w, float h, ITextureRegion region, VertexBufferObjectManager pVBOM, IEntity pSceneEntity, ITextureProvider pTextureProvider, RubeDef pRubeDef, ImageDef pImageDef) {
//		IEntity entity = populateEntity(x, y, w, h, region, pVBOM, (int)pImageDef.renderOrder, pImageDef.angle);
//
//		if (entity != null) {
//			if (pImageDef.body != null) {
//				entity.setAnchorCenter(-x / entity.getWidth() + 0.5f, -y / entity.getHeight() + 0.5f);
//
//				PhysicsConnector connector = populatePhysicsConnector(pRubeDef, pImageDef.body, entity);
//				pImageDef.body.setUserData(connector);
//				entity.setUserData(connector);
//				pRubeDef.worldProvider.getWorld().registerPhysicsConnector(connector);
//			}
//
//			pSceneEntity.attachChild(entity);
//		}
//	}
//
//	/**
//	 * 
//	 * @param pX
//	 * @param pY
//	 * @param pWidth
//	 * @param pHeight
//	 * @param region
//	 * @param pVBOM
//	 * @param pZindex
//	 * @param pAngle
//	 * @return
//	 */
//	protected Sprite populateEntity(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion region,
//			VertexBufferObjectManager pVBOM, final int pZindex, final float pAngle) {
//		Sprite sprite = new UncoloredSprite(pX, pY, pWidth, pHeight, region, pVBOM);
//		sprite.setRotationOffset(MathUtils.radToDeg(-pAngle));
//		sprite.setCullingEnabled(true);
//		sprite.setZIndex(pZindex);
//		return sprite;
//	}
//
//	protected PhysicsConnector populatePhysicsConnector(RubeDef pRube, Body pBody, IEntity pEntity) {
//		return new PhysicsConnector(pEntity, pBody);
//	}
//
//	protected float getPixelToMeterRatio(ImageDef pImage) {
//		return PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
//	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static interface ITextureProvider {
		public ITextureRegion get(final String pFileName);
	}
}
