<@extends src="base.ftl"> <@block name="header_scripts">

<@block name="title">${viewId} State</@block>

</@block> <@block name="body">

<div>
  <h1>StateView</h1>

  <table id="stateInfo" class="info">
    <tr>
      <td class="labelColumn">View</td>
      <td>${viewId}</td>
    </tr>
    <tr>
      <td class="labelColumn">Sequence Id</td>
      <td>${sequenceId}</td>
    </tr>
    <tr>
      <td class="labelColumn">Session Size</td>
      <td>${dSessionSize?c}</td>
    </tr>
    <tr>
      <td class="labelColumn">Cumulated Size</td>
      <td>${cumulatedSize?c}</td>
    </tr>
    <tr>
      <td class="labelColumn">Max Depth</td>
      <td>${maxDepth?c}</td>
    </tr>
    <tr>
      <td>Nb Branch</td>
      <td>${nbBranch?c}</td>
    </tr>
  </table>

  <h2>Paths</h2>
  <table id="stateDetail" class="tablesorter componentList">
    <thead>
      <tr>
        <th class="depth">Depth</th>
        <th>Class</th>
        <th class="size">Size</th>
        <th class="nodePath">Path</th>
      </tr>
    </thead>
    <tbody>
      <#list nodeList as node>
      <tr class="nodeDetail">
        <td class="depth">${node.depth}</td>
        <td class="nodeClass">${node.type}</td>
        <td class="size">${node.size?c}</td>
        <td class="nodePath">${node.path}</td>
      </tr>
      </#list>
    </tbody>
  </table>

</div>

<script>
  jQuery(document)
      .ready(
          function() {
            var idx = document.location.href.indexOf("/viewState/");
            var url = document.location.href.substring(0, idx);
            jQuery("#stateDetail tr.nodeDetail")
                .each(
                    function(index, value) {
                      var nodeClassText = jQuery(this).find("td.nodeClass")
                          .text();
                      if (nodeClassText.indexOf('UIAliasHolder') >= 0) {
                        var nodePath = jQuery(this).find("td.nodePath");
                        var nodePathText = nodePath.text();
                        nodePath
                            .html("<a href='" + url + "/uiAliasHolder/${viewId}/${sequenceId}/" + nodePathText + "'  target='_blank' >"
                                + nodePathText + "</a>");
                      }
                    });
            jQuery(".tablesorter").tablesorter();
          });
</script>

</@block> </@extends>
