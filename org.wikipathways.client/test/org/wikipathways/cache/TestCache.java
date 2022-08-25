package org.wikipathways.cache;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.pathvisio.libgpml.io.ConverterException;
import org.pathvisio.libgpml.model.PathwayModel;
import org.wikipathways.client.WikiPathwaysCache;

/**
 * Testing WikiPathwaysCache generation
 * 
 * @author martina, finterly
 *
 */
public class TestCache {

	@Test
	public void test() throws ConverterException, IOException {

		// setting up a new cache will take quite some time!!
		// change to existing cache directory if you already have one
		WikiPathwaysCache cache = new WikiPathwaysCache(new File("cache"));
		cache.update();

		File f = cache.getPathwayGpml("abc");
		if (f != null) {
			PathwayModel p = new PathwayModel();
			p.readFromXml(f, false);
			assertTrue(p.getPathway().getTitle().equals("Sandbox pathway test")); //was "Sandbox Pathway"
		}
	}

}
