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

import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.metadata.ServiceMetadataBuilder;
import org.lappsgrid.serialization.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author Keith Suderman
 */
public class WrapTcf extends AbstractWrappingService
{
	private String template = null;

	public WrapTcf()
	{

	}

	protected void addMetadata(ServiceMetadataBuilder builder)
	{
		builder.name(this.getClass().getName())
				.produceFormat(Discriminators.Uri.TCF)
				.description("Wraps plain text in a TCF Data object.");
	}

	public String execute(String input)
	{
		if (template == null) {
			template = loadTempate();
		}

		Data<String> data = new Data();
		data.setDiscriminator(Discriminators.Uri.TCF);
		data.setPayload(String.format(template, input));
		return data.asJson();
	}

	protected String loadTempate()
	{
		ClassLoader loader = this.getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream("tcf.xml");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines().collect(Collectors.joining("\n"));
	}
}
