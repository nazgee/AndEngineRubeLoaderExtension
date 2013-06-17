package org.andengine.extension.rubeloader.factory;

import java.io.File;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.UncoloredSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.rubeloader.Loader.ITextureProvider;
import org.andengine.extension.rubeloader.def.ImageDef;
import org.andengine.extension.rubeloader.json.AutocastMap;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Simple implementation of IEntityFactory. It will try to create Sprite instance for
 * all the ImageDef provided.
 * 
 * @author nazgee
 *
 */
public class EntityFactory implements IEntityFactory {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private IEntity mSceneEntity;
	private ITextureProvider mTextureProvider;
	private VertexBufferObjectManager mVBOM;
	public boolean isConfigured = false;
	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * @note when using this constructor you MUST call configure() before this factory is used!
	 */
	public EntityFactory() {
		super();
		configure(null, null, null);
	}

	/**
	 * 
	 * @param pSceneEntity Every created {@link IEntity} will be attached to this object
	 * @param pTextureProvider Will be used to obtain {@link ITextureRegion} from a given {@link String} texture region name
	 * @param pVBOM Every {@link IEntity} will be created using this {@link VertexBufferObjectManager}
	 */
	public EntityFactory(IEntity pSceneEntity, ITextureProvider pTextureProvider, VertexBufferObjectManager pVBOM) {
		super();
		configure(pSceneEntity, pTextureProvider, pVBOM);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void configure(IEntity pSceneEntity, ITextureProvider pTextureProvider, VertexBufferObjectManager pVBOM) {
		this.mSceneEntity = pSceneEntity;
		this.mTextureProvider = pTextureProvider;
		this.mVBOM = pVBOM;
		isConfigured = true;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public IEntity produce(PhysicsWorld pWorld, ImageDef image, AutocastMap pAutocastMap) {
		if (!isConfigured) {
			throw new RuntimeException("configure() was not called on this instance of " + getClass().getSimpleName());
		}
	
		final float p2m = getPixelToMeterRatio(image);
		final ITextureRegion region = this.mTextureProvider.get(new File(image.file).getName());
		final float scale = image.scale * p2m / region.getHeight();
		final float w = region.getWidth() * scale;
		final float h = region.getHeight() * scale;

		final float x = image.center.x * p2m;
		final float y = image.center.y * p2m;

		return produceImpl(x, y, w, h, region, this.mVBOM, this.mSceneEntity, this.mTextureProvider, pWorld, image, pAutocastMap);
	}


	protected IEntity produceImpl(float x, float y, float w, float h, ITextureRegion region, VertexBufferObjectManager pVBOM, IEntity pSceneEntity, ITextureProvider pTextureProvider, PhysicsWorld pWorld, ImageDef pImageDef, AutocastMap pAutocastMap) {
		IEntity entity = createSprite(x, y, w, h, region, pVBOM, (int)pImageDef.renderOrder, pImageDef.angle);

		if (entity != null) {
			if (pImageDef.body != null) {
				entity.setAnchorCenter(-x / entity.getWidth() + 0.5f, -y / entity.getHeight() + 0.5f);

				PhysicsConnector connector = createPhysicsConnector(pWorld, pImageDef, entity);
				pImageDef.body.setUserData(connector);
				entity.setUserData(connector);
				pWorld.registerPhysicsConnector(connector);
			}

			pSceneEntity.attachChild(entity);
		}

		return entity;
	}

	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param pWidth
	 * @param pHeight
	 * @param region
	 * @param pVBOM
	 * @param pZindex
	 * @param pAngle
	 * @return
	 */
	protected Sprite createSprite(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion region, VertexBufferObjectManager pVBOM, final int pZindex, final float pAngle) {
		Sprite sprite = new UncoloredSprite(pX, pY, pWidth, pHeight, region, pVBOM);
		sprite.setRotationOffset(MathUtils.radToDeg(-pAngle));
		sprite.setCullingEnabled(true);
		sprite.setZIndex(pZindex);
		return sprite;
	}

	protected PhysicsConnector createPhysicsConnector(PhysicsWorld pWorld, ImageDef pImageDef, IEntity pEntity) {
		return new PhysicsConnector(pEntity, pImageDef.body);
	}

	protected float getPixelToMeterRatio(ImageDef pImage) {
		return PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	}
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
