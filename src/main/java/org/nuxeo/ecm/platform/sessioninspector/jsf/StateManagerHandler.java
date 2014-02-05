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
package org.nuxeo.ecm.platform.sessioninspector.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.platform.sessioninspector.jsf.model.MonitorNode;
import org.nuxeo.ecm.platform.sessioninspector.jsf.model.UIAliasHolderWrapper;
import org.nuxeo.ecm.platform.sessioninspector.jsf.model.UIComponentWrapper;
import org.nuxeo.ecm.platform.ui.web.application.NuxeoConversationStateHolder;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;
import org.nuxeo.runtime.javaagent.AgentLoader;

/**
 * @since 5.9.2
 */
@WebObject(type = "jsfStateManagerHandler")
public class StateManagerHandler extends DefaultObject {

    private static final Log log = LogFactory.getLog(StateManagerHandler.class);

    private final static String STATE_ORDER_CLASS = "org.nuxeo.ecm.platform.ui.web.application.NuxeoConversationStateHolder";

    @GET
    @Produces("text/html")
    @Path(value = "viewState/{viewId}/{sequenceId}")
    @SuppressWarnings("boxing")
    public Object viewState(@PathParam("viewId")
    String viewId, @PathParam("sequenceId")
    String sequenceId, @PathParam("computeSize")
    boolean computeSize) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        MonitorNode rootNode = getMonitorNode(viewId, sequenceId);

        long dSessionSize = -1;
        long cumulatedSize = -1;

        if (false) {
            // disabled, too heavy to compute for now
            try {
                dSessionSize = AgentLoader.INSTANCE.getSizer().deepSizeOf(
                        getStateHolder()) / 1024 / 8;
            } catch (Exception e) {
                log.error("Could not compute size", e);
                dSessionSize = -1;
            }
        }

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("viewId", viewId);
        args.put("sequenceId", sequenceId);
        args.put("dSessionSize", dSessionSize);
        args.put("cumulatedSize", rootNode.getCumulatedSize());
        args.put("cumulatedDepth", rootNode.getCumulatedDepth());
        args.put("maxDepth", rootNode.getMaxDepth());

        List<MonitorNode> nodeList = rootNode.toList();
        args.put("nodeList", nodeList);
        args.put("nbBranch", nodeList.size());
        return getView("viewState").args(args);

    }

    @GET
    @Produces("text/html")
    @Path(value = "uiComponent/{viewId}/{sequenceId}/{path}")
    public Object viewUIComponent(@PathParam("viewId")
    String viewId, @PathParam("sequenceId")
    String sequenceId, @PathParam("path")
    String path) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        MonitorNode rootNode = getMonitorNode(viewId, sequenceId);
        MonitorNode childNode = rootNode.getChild(path.split(":"));
        UIComponentWrapper comp = new UIComponentWrapper(childNode.getId(),
                (Object[]) childNode.getStateReference());

        Map<String, Object> args = getArguments(childNode, comp, path);
        return getView("uiComponent").args(args);
    }

    @SuppressWarnings("boxing")
    protected Map<String, Object> getArguments(MonitorNode node,
            UIComponentWrapper comp, String path) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", comp.getId());
        args.put("path", path);
        args.put("type", node.getType());
        args.put("children", comp.getFlatState());
        args.put("depth", node.getDepth());
        args.put("size", node.getSize());
        return args;
    }

    @GET
    @Produces("text/html")
    @Path(value = "uiAliasHolder/{viewId}/{sequenceId}/{path}")
    public Object viewUIAliasHolder(@PathParam("viewId")
    String viewId, @PathParam("sequenceId")
    String sequenceId, @PathParam("path")
    String path) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        MonitorNode rootNode = getMonitorNode(viewId, sequenceId);
        MonitorNode childNode = rootNode.getChild(path.split(":"));
        UIAliasHolderWrapper alias = new UIAliasHolderWrapper(
                childNode.getId(), (Object[]) childNode.getStateReference());

        Map<String, Object> args = getArguments(childNode, alias, path);
        args.put("aliasId", alias.getAliasId());
        args.put("mapperSize", alias.getAliasVariableMapperSize());
        args.put("variables", alias.getVariables().entrySet());
        return getView("uiAliasHolder").args(args);
    }

    private MonitorNode getMonitorNode(String viewId, String sequenceId)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        NuxeoConversationStateHolder h = getStateHolder();
        String computedViewId = "/" + viewId + ".xhtml";
        Object[] o = h.getState(null, computedViewId, sequenceId);
        return new MonitorNode(o[0], (Object[]) ((Object[]) o[1])[0]);
    }

    private NuxeoConversationStateHolder getStateHolder() {
        HttpSession s = ctx.getRequest().getSession();
        NuxeoConversationStateHolder h = (NuxeoConversationStateHolder) s.getAttribute(STATE_ORDER_CLASS);
        return h;
    }

}
