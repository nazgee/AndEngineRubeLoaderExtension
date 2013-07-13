package org.andengine.extension.rubeloader;

import org.andengine.opengl.texture.region.ITextureRegion;

public interface ITextureProvider {
	public ITextureRegion get(final String pFileName);
}