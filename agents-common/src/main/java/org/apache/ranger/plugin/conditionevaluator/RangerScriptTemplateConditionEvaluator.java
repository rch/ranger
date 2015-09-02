/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ranger.plugin.conditionevaluator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

public class RangerScriptTemplateConditionEvaluator extends RangerScriptConditionEvaluator {
	private static final Log LOG = LogFactory.getLog(RangerScriptTemplateConditionEvaluator.class);

	protected String scriptTemplate;
	protected String script;

	@Override
	public void init() {

		if (LOG.isDebugEnabled()) {
			LOG.debug("==> RangerScriptTemplateConditionEvaluator.init(" + condition + ")");
		}

		super.init();

		Map<String, String> evalOptions = conditionDef. getEvaluatorOptions();

		if (MapUtils.isNotEmpty(evalOptions)) {
			scriptTemplate = evalOptions.get("scriptTemplate");
		}

		script = formatScript();

		if (LOG.isDebugEnabled()) {
			LOG.debug("<== RangerScriptTemplateConditionEvaluator.init(" + condition + ")");
		}
	}

	@Override
	protected String getScript() {
		return script;
	}

	private String formatScript() {

		String ret = null;

		if (LOG.isDebugEnabled()) {
			LOG.debug("==> RangerScriptTemplateConditionEvaluator.formatScript()");
		}
		List<String> values = condition.getValues();

		if (CollectionUtils.isNotEmpty(values)) {

			String value = values.get(0);

			if (StringUtils.isNotBlank(value)) {

				String s = value.trim().toLowerCase();

				if (s.equals("no") || s.equals("false")) {
					ret = null;
				} else {
					ret = scriptTemplate == null ? null : scriptTemplate.trim();
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("<== RangerScriptTemplateConditionEvaluator.formatScript(), ret=" + ret);
		}

		return ret;
	}
}
