/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     <a href="mailto:grenard@nuxeo.com">Guillaume</a>
 */
package org.nuxeo.ecm.platform.sessioninspector.jsf.model;

import java.util.Map;

import javax.el.ValueExpression;

import org.nuxeo.ecm.platform.ui.web.binding.alias.AliasVariableMapper;
import org.nuxeo.runtime.javaagent.AgentLoader;

/**
 * @since 5.9.2
 */
public class UIAliasHolderWrapper extends UIComponentWrapper {

    protected final AliasVariableMapper mapper;

    public UIAliasHolderWrapper(String id, Object[] object) {
        super(id, object);
        mapper = (AliasVariableMapper) object[2];
    }

    @SuppressWarnings("boxing")
    public Long getAliasVariableMapperSize() {
        return AgentLoader.INSTANCE.getSizer().deepSizeOf(mapper.getVariables()) / 8;
    }

    public String getAliasId() {
        if (mapper != null) {
            return mapper.getId();
        }
        return null;
    }

    public Map<String, ValueExpression> getVariables() {
        if (mapper != null) {
            return mapper.getVariables();
        }
        return null;
    }

}
