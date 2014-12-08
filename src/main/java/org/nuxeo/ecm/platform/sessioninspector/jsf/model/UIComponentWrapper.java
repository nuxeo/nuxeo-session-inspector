/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
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
 *     Anahide Tchertchian
 */
package org.nuxeo.ecm.platform.sessioninspector.jsf.model;

import java.util.ArrayList;
import java.util.List;

import org.nuxeo.ecm.platform.ui.web.binding.alias.AliasVariableMapper;

/**
 * Wrapper for a generic UIComponent state.
 *
 * @since 5.9.2
 */
public class UIComponentWrapper {

    protected final String id;

    protected final Object[] state;

    public UIComponentWrapper(String id, Object[] object) {
        this.id = id;
        this.state = object;
    }

    public String getId() {
        return id;
    }

    public List<String> getFlatState() {
        List<String> res = new ArrayList<String>();
        if (state != null) {
            for (Object item : state) {
                if (item == null) {
                    continue;
                }
                if (item instanceof Object[]) {
                    res.addAll(new UIComponentWrapper(id, (Object[]) item).getFlatState());
                } else if (item instanceof AliasVariableMapper) {
                    AliasVariableMapper vm = (AliasVariableMapper) item;
                    res.add(String.format("AliasVariableMapper(%s, %s)", vm.getId(), vm.getVariables()));
                } else {
                    res.add(item.toString());
                }
            }
        }
        return res;
    }
}
