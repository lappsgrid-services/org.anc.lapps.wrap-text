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
import org.lappsgrid.api.WebService;
import org.lappsgrid.serialization.Data;
import org.lappsgrid.serialization.DataContainer;
import org.lappsgrid.serialization.Serializer;
import static org.lappsgrid.discriminator.Discriminators.*;

/**
 * @author Keith Suderman
 */
public class ServiceTests
{
	public static final String TEXT = "Goodbye cruel world.";

	public ServiceTests()
	{

	}

	@Test
	public void wrapLif()
	{
		WebService service = new WrapLif();
		String json = service.execute(TEXT);
		DataContainer data = Serializer.parse(json, DataContainer.class);
		assert Uri.LIF.equals(data.getDiscriminator());
		assert TEXT.equals(data.getPayload().getText());
	}

	@Test
	public void wrapText()
	{
		WebService service = new WrapText();
		String json = service.execute(TEXT);
		Data data = Serializer.parse(json, Data.class);
		assert Uri.TEXT.equals(data.getDiscriminator());
		assert TEXT.equals(data.getPayload().toString());
	}

	@Test
	public void wrapTcf()
	{
		WebService service = new WrapTcf();
		String json = service.execute(TEXT);
		Data data = Serializer.parse(json, Data.class);
		assert Uri.TCF.equals(data.getDiscriminator());
		String xml = data.getPayload().toString();

		// Don't bother with parsing the XML, just check for some strings.
		assert xml.contains(TEXT);
		assert xml.startsWith("<D-Spin");
		assert xml.endsWith("</D-Spin>");
	}
}
