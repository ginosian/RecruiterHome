/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, certifications
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.recruiting.utils;

import org.springframework.oxm.Marshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Locale;

/**
 * Created by Martha on 5/6/2017.
 */

public class XmlViewResolver implements ViewResolver {

	private Marshaller marshaller;

	public XmlViewResolver(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MarshallingView marshallingView = new MarshallingView();
		marshallingView.setMarshaller(marshaller);
		return marshallingView;
	}
}
