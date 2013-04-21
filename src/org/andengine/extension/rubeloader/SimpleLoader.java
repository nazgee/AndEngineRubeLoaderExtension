package org.andengine.extension.rubeloader;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.StreamUtils;
import org.andengine.util.adt.io.in.ResourceInputStreamOpener;
import org.andengine.util.math.MathUtils;

import android.content.res.Resources;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Simple example of a customr R.U.B.E. loader
 * @author Michal Stawinski (nazgee)
 */
public class SimpleLoader extends Loader {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private final static SimpleLoader sHelperLoader = new SimpleLoader();
	// ===========================================================
	// Constructors
	// ===========================================================
	public SimpleLoader() {
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	protected void handleImage(Scene pScene, VertexBufferObjectManager pVBOM, PhysicsWorld world, Image image, final ITextureRegion region,
			final float w, final float h, final float x, final float y) {
		Sprite sprite = populateSprite(region, pVBOM, (int)image.renderOrder, image.angle, w, h, x, y);

		if (image.body != null) {
			IEntity entity = prepareForPhysics(x, y, sprite);

			PhysicsConnector connector = populatePhysicsConnector(image.body, entity);
			image.body.setUserData(connector);
			world.registerPhysicsConnector(connector);

			pScene.attachChild(entity);
		} else {
			pScene.attachChild(sprite);
		}
	}

	protected IEntity prepareForPhysics(final float pX, final float pY, Sprite pSprite) {
		Entity entity = new Entity(pX, pY);
		entity.attachChild(pSprite);
		pSprite.setPosition(pX, pY);
		return entity;
	}

	protected Sprite populateSprite(final ITextureRegion region, VertexBufferObjectManager pVBOM, final int pZindex, final float pAngle, final float pWidth,
			final float pHeight, final float pX, final float pY) {
		Sprite sprite = new UncoloredSprite(pX, pY, pWidth, pHeight, region, pVBOM);
		sprite.setCullingEnabled(true);
		sprite.setZIndex(pZindex);
		sprite.setRotation(MathUtils.radToDeg(-pAngle));
		return sprite;
	}

	protected PhysicsConnector populatePhysicsConnector(Body pBody, IEntity pEntity) {
		return new PhysicsConnector(pEntity, pBody);
	}

	protected float getPixelToMeterRatio(Image pImage) {
		return PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public PhysicsWorld load(Resources pResources, Scene pScene, ITextureProvider pTextureProvider, VertexBufferObjectManager pVBOM,  int resId, final int ... pExtraResId) {

		StringBuilder errorMsg = new StringBuilder();
		PhysicsWorld world = readFromString(readResource(resId, pResources), errorMsg);

		for (int i = 0; i < pExtraResId.length; i++) {
			overload(pResources, errorMsg, world, pExtraResId[i]);
		}

		Vector<Image> imgs = getAllImages();
		for (Image image : imgs) {
			final float p2m = getPixelToMeterRatio(image);
			final ITextureRegion region = pTextureProvider.get(new File(image.file).getName());
			final float scale = image.scale * p2m / region.getHeight();
			final float w = region.getWidth() * scale;
			final float h = region.getHeight() * scale;
			final float x = image.center.x * p2m;
			final float y = image.center.y * p2m;

			handleImage(pScene, pVBOM, world, image, region, w, h, x, y);
		}

		return world;
	}

	private void mergeWithOtherLoader(SimpleLoader pLoader) {
		{
			int i = 0;
			int newBodies = pLoader.m_bodies.size();
			int oldBodies = m_bodies.size();
			for (i = 0; i < newBodies; i++) {
				m_indexToBodyMap.put(i + oldBodies, pLoader.m_bodies.get(i));
			}
		}
		m_bodies.addAll(pLoader.m_bodies);
		m_joints.addAll(pLoader.m_joints);
		m_images.addAll(pLoader.m_images);

		m_bodyToNameMap.putAll(pLoader.m_bodyToNameMap);
		m_fixtureToNameMap.putAll(pLoader.m_fixtureToNameMap);
		m_jointToNameMap.putAll(pLoader.m_jointToNameMap);
		m_imageToNameMap.putAll(pLoader.m_imageToNameMap);

		m_customPropertiesMap.putAll(pLoader.m_customPropertiesMap);

		m_bodiesWithCustomProperties.addAll(pLoader.m_bodiesWithCustomProperties);
		m_fixturesWithCustomProperties.addAll(pLoader.m_fixturesWithCustomProperties);
		m_jointsWithCustomProperties.addAll(pLoader.m_jointsWithCustomProperties);
		m_imagesWithCustomProperties.addAll(pLoader.m_imagesWithCustomProperties);
		m_worldsWithCustomProperties.addAll(pLoader.m_worldsWithCustomProperties);
	}

	private void overload(Resources pResources, StringBuilder errorMsg, PhysicsWorld world, int pResourceId) {
		sHelperLoader.clear();
		sHelperLoader.continueReadingFromString(readResource(pResourceId, pResources), errorMsg, world);
		mergeWithOtherLoader(sHelperLoader);
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
