package org.andengine.extension.rubeloader;

import java.io.IOException;

import net.minidev.json.parser.ParseException;

import org.andengine.entity.IEntity;
import org.andengine.extension.rubeloader.def.RubeDef;
import org.andengine.extension.rubeloader.factory.EntityFactory;
import org.andengine.extension.rubeloader.factory.IEntityFactory;
import org.andengine.extension.rubeloader.factory.IPhysicsWorldProvider;
import org.andengine.extension.rubeloader.parser.RubeParser;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.StreamUtils;
import org.andengine.util.adt.io.in.ResourceInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.content.res.Resources;

/**
 * Simple example of a customr R.U.B.E. loader
 * @author Michal Stawinski (nazgee)
 */
public class RubeLoader {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private RubeParser mRubeParser;
	private IEntityFactory mEntityFactory;

	// ===========================================================
	// Constructors
	// ===========================================================
	public RubeLoader() {
		this(new EntityFactory());
	}

	public RubeLoader(final IEntityFactory pEntityFactory) {
		this.mRubeParser = new RubeParser(pEntityFactory);
		this.mEntityFactory = pEntityFactory;
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

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
