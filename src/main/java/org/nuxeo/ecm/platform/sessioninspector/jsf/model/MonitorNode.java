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
 *     <a href="mailto:grenard@nuxeo.com">Guillaume</a>
 */
package org.nuxeo.ecm.platform.sessioninspector.jsf.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.nuxeo.ecm.platform.sessioninspector.jsf.StateReferenceHelper;
import org.nuxeo.ecm.platform.ui.web.binding.alias.UIAliasHolder;
import org.nuxeo.runtime.javaagent.AgentLoader;

/**
 *
 *
 * @since 5.9.2
 */
public class MonitorNode {

    private MonitorNode parent = null;

    private String path = null;

    private String id = null;

    public String getId() {
        return id;
    }

    private List<MonitorNode> children = null;

    private Object stateReference = null;

    private Integer depth = null;

    private Long size = null;

    private final String type;

    private Map<String, ObjectStatistics> objMapStat;

    public MonitorNode(Object rawHierarchy, Object[] rawState)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        this(null, rawHierarchy, rawState);
    }

    public MonitorNode(MonitorNode parent, Object rawHierarchy,
            Object[] rawState) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        super();
        this.parent = parent;
        this.id = StateReferenceHelper.getIdForNode(rawHierarchy);
        this.stateReference = rawState[0];
        this.type = StateReferenceHelper.getTypeForNode(rawHierarchy);
        children = new ArrayList<MonitorNode>();
        List<?> childrenOfCurrent = StateReferenceHelper.getChildrenForNode(rawHierarchy);

        if (childrenOfCurrent == null) {

        } else {
            children = new ArrayList<MonitorNode>(childrenOfCurrent.size());
            for (int i = 0; i < childrenOfCurrent.size(); i++) {
                children.add(new MonitorNode(this, childrenOfCurrent.get(i),
                        (Object[]) ((Object[]) rawState[1])[i]));
            }
        }

    }

    public String getType() {
        return type;
    }

    public String getPath() {
        if (path == null) {
            if (parent == null) {
                path = id;
            } else {
                path = parent.getPath() + ":" + id;
            }
        }
        return path;
    }

    public int getDepth() {
        if (depth == null) {
            if (parent == null) {
                depth = 1;
            } else {
                depth = parent.getDepth() + 1;
            }
        }
        return depth;
    }

    public List<MonitorNode> toList() {
        List<MonitorNode> result = new ArrayList<MonitorNode>();
        result.add(this);
        for (MonitorNode child : children) {
            result.addAll(child.toList());
        }
        return result;
    }

    public int getMaxDepth() {
        int max = 1;
        for (MonitorNode child : children) {
            int temp = 1 + child.getMaxDepth();
            if (temp > max) {
                max = temp;
            }
        }
        return max;
    }

    public int getCumulatedDepth() {
        int count = 1;
        for (MonitorNode child : children) {
            count += child.getCumulatedDepth();
        }
        return count;
    }

    public Object getState(String[] path) {
        if (path.length == 1 && path[0] == id) {
            return stateReference;
        } else {
            return getChild(id).getState(
                    Arrays.copyOfRange(path, 1, path.length));
        }
    }

    public MonitorNode getChild(String id) {
        for (MonitorNode child : children) {
            if (child.getId().equals(id)) {
                return child;
            }
        }
        return null;
    }

    public Long getSize() {
        if (size == null) {
            Long temp = AgentLoader.INSTANCE.getSizer().deepSizeOf(
                    stateReference) / 1024 / 8;
            size = temp;
        }
        return size;
    }

    public Long getCumulatedSize() {
        Long count = getSize();
        for (MonitorNode child : children) {
            if (child.getId().equals(id)) {
                count += child.getCumulatedSize();
            }
        }
        return count;
    }

    public Object getStateReference() {
        return stateReference;
    }

    public String toString() {
        if (stateReference != null) {
            if (stateReference instanceof UIAliasHolder) {
                // TODO
            }
            return stateReference.toString();
        }
        return null;
    }

}
