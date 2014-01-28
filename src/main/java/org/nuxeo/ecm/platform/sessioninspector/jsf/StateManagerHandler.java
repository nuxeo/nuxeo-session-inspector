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

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.platform.sessioninspector.jsf.model.MonitorNode;
import org.nuxeo.ecm.platform.ui.web.application.NuxeoConversationStateHolder;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;
import org.nuxeo.runtime.javaagent.AgentLoader;

/**
 *
 *
 * @since 5.9.2
 */
@WebObject(type = "jsfStateManagerHandler")
public class StateManagerHandler extends DefaultObject {

    private static final Log log = LogFactory.getLog(StateManagerHandler.class);

    @GET
    @Produces("text/html")
    @Path(value = "viewState/{viewId}/{sequenceId}")
    public Object viewState(@PathParam("viewId")
    String viewId, @PathParam("sequenceId")
    String sequenceId, @PathParam("computeSize")
    boolean computeSize) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        HttpSession s = ctx.getRequest().getSession();
        NuxeoConversationStateHolder h = (NuxeoConversationStateHolder) s.getAttribute("org.nuxeo.ecm.platform.ui.web.application.NuxeoConversationStateHolder");

        String root = null;
        if (sequenceId != null) {
            String[] split = sequenceId.split(":");
            if (split != null && split.length > 0) {
                root = split[0];
            }
        }
        String computedViewId = "/" + viewId + ".xhtml";
        Object[] o = h.getState(null, computedViewId, root);

        long dSessionSize = 0;
        long cumulatedSize = 0;
        String sizeHR = "";
        try {
            dSessionSize = AgentLoader.INSTANCE.getSizer().deepSizeOf(
                    s) / 1024 / 8;
        } catch (Exception e) {
            log.error("Could not compute size", e);
            dSessionSize = -1;
        }

        MonitorNode rootNode = new MonitorNode(o[0],
                (Object[]) ((Object[]) o[1])[0]);
        cumulatedSize = rootNode.getCumulatedSize();

        Integer maxDepth = rootNode.getMaxDepth();
        Integer cumulatedDepth = rootNode.getCumulatedDepth();

        List<MonitorNode> nodeList = rootNode.toList();

        return getView("viewState").arg("cumulatedSize", cumulatedSize).arg(
                "dSessionSize", dSessionSize).arg("nbBranch", nodeList.size()).arg(
                "cumulatedDepth", cumulatedDepth).arg("maxDepth", maxDepth).arg(
                "stateSizeHR", sizeHR).arg("viewId", viewId).arg("sequenceId",
                sequenceId).arg("nodeList", nodeList);
    }

    @GET
    @Produces("text/html")
    @Path(value = "uiAliasHolder/{viewId}/{sequenceId}/{path}")
    public Object viewUIAliasHolder(@PathParam("viewId")
    String viewId, @PathParam("sequenceId")
    String sequenceId, @PathParam("path") String path) {

        return getView("uiAliasHolder");
    }

}
