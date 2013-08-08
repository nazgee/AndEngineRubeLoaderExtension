package org.andengine.extension.rubeloader;

import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * To create Sprites, RUBE loader neeeds ITextureRegion, and this is an
 * interface that it uses to get what it wants. Implement this interface and
 * hook it up with your current textures/regions manager to provide requested
 * ITextureRegion
 * 
 * @author nazgee
 */
public interface ITextureProvider {
	/**
	 * Called by EntityFactory to get ITextureRegion of a given file.
	 * 
	 * @param pFileName filename of the graphics file (*.png or *.jpg) that was used when RUBE level was created.
	 * @return ITextureRegion that matches given file
	 */
	public ITextureRegion get(final String pFileName);
}