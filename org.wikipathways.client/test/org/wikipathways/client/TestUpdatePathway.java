// WikiPathways Java library,
// Copyright 2014-2022 WikiPathways
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.wikipathways.client;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.pathvisio.libgpml.io.ConverterException;
import org.pathvisio.libgpml.model.type.DataNodeType;
import org.pathvisio.libgpml.model.type.ObjectType;
import org.pathvisio.libgpml.model.PathwayModel;
import org.pathvisio.libgpml.model.DataNode;
import org.pathvisio.libgpml.model.PathwayElement;
import org.pathvisio.wikipathways.webservice.WSPathway;
import org.wikipathways.client.test.utils.ConnectionSettings;
import org.wikipathways.client.test.utils.UserProperties;

/**
 * JUnit Test for webservice function: updatePathway
 * 
 * @author mkutmon, finterly
 */
public class TestUpdatePathway {

	WikiPathwaysClient client;

	// define username and password in properties file:
	// resources/user.properties
	UserProperties props;

	@Before
	public void setUp() throws Exception {
		client = ConnectionSettings.createClient();
		props = new UserProperties();
	}

	@Test
	public void test() throws ConverterException, IOException {
		client.login(props.getProperty("username"), props.getProperty("password"));

		String id = "WP4";
		WSPathway p = client.getPathway(id);
		PathwayModel pathway = new PathwayModel();
		pathway.readFromXml(IOUtils.toInputStream(p.getGpml(), "UTF-8"), true);

		DataNode pel = new DataNode("Test", DataNodeType.GENEPRODUCT);
		// pel.setGraphId(pathway.getUniqueGraphId());
		pel.setCenterX(65);
		pel.setCenterY(65);
		pel.setHeight(20);
		pel.setWidth(80);
		// pel.setElementId("12334"); TODO setElementId is protected method
		pathway.add(pel);

		String newRevision = client.updatePathway(p.getId(), pathway, "add data node test",
				Integer.parseInt(p.getRevision()));
		assertTrue(Integer.parseInt(newRevision) > Integer.parseInt(p.getRevision()));
	}

}
