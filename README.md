nuxeo-session-inspector
=======================

This module allows to browse the http session java object through webengine api.

## Installation

1. Install <https://github.com/nuxeo/nuxeo-javaagent>
2. Run mvn clean install.
3. Copy nuxeo-session-inspector-{version}.jar to $NUXEO_HOME/nxserver/bundles

## Current Features

### Inspect JSF StateHandler

Dump the JSF component tree with all node size. It is possible to further inspect the UIAliasHolder nodes.

Access point is /site/sessionInspector/jsfStateManager/viewState/${viewId}/${sequenceId} where:

 - ${viewId} is the faces view (e.g view_documents,view_admin, etc.)
 - ${sequenceId} is the sequence id i.e. the state view id found in the hidden input of the rendered html page (e.g j_id1, j_id42, etc.)

The module appends a link in the top menu bar of Nuxeo JSF/UI to directly inspect the stateView of the current page.

### Inspect UIAliasHolder

Access point is /site/sessionInspector/jsfStateManager/uiAliasHolder/${viewId}/${sequenceId}/${path} where:

 - ${viewId} is the faces view (e.g view_documents,view_admin, etc.)
 - ${sequenceId} is the sequence id i.e. the state view id found in the hidden input of the rendered html page (e.g j_id1, j_id42, etc.)
 - ${path} the JSF component path (e.g _viewRoot:j_id613:j_id614:j_id615:j_id616:j_id617:j_id618:j_id619:nxw_documentTabs_clickedActionIdHolder)

