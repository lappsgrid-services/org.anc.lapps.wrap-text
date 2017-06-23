/*
 * Copyright (c) 2017 The American National Corpus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.anc.lapps.wrapping;

import org.junit.Test;
import static org.junit.Assert.*;

import org.lappsgrid.api.WebService;
import static org.lappsgrid.discriminator.Discriminators.*;
import org.lappsgrid.metadata.ServiceMetadata;
import org.lappsgrid.serialization.Serializer;

/**
 * @author Keith Suderman
 */
public class MetadataTests
{
	public MetadataTests()
	{

	}


	@Test
	public void wrapLifMetadata()
	{
		testService(new WrapLif(), Uri.LIF);
	}

	@Test
	public void wrapTextMetadata()
	{
		testService(new WrapText(), Uri.TEXT);
	}

	@Test
	public void wrapTcfMetadata()
	{
		testService(new WrapTcf(), Uri.TCF);
	}

	private void testService(WebService service, String type)
	{
		String json = service.getMetadata();
		ServiceMetadata metadata = Serializer.parse(json, ServiceMetadata.class);
		testCommon(metadata);
		assertEquals(service.getClass().getName(), metadata.getName());
		assertEquals(type, metadata.getProduces().getFormat().get(0));
	}

	private void testCommon(ServiceMetadata metadata)
	{
		assert Version.getVersion().equals(metadata.getVersion());
		assert Uri.ALL.equals(metadata.getAllow());
		assert "text/plain".equals(metadata.getRequires().getFormat().get(0));
	}
}
