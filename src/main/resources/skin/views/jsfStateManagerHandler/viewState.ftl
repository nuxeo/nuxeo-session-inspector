<@extends src="base.ftl"> <@block name="header_scripts"> </@block>

<@block name="body">

<div>
  <h1>StateView</h1>

  <table>
    <tr>
      <td class="labelColumn">View</td>
      <td>${viewId}</td>
    </tr>
    <tr>
      <td class="labelColumn">Sequence Id</td>
      <td style="white-space: pre-line">${sequenceId}</td>
    </tr>
    <tr>
      <td class="labelColumn">Session Size (KB)</td>
      <td style="white-space: pre-line">${dSessionSize}</td>
    </tr>
    <!-- <tr>
      <td class="labelColumn">Fragment Size (HR)</td>
      <td style="white-space: pre-line">${stateSizeHR}</td>
    </tr>
    <tr>
      <td class="labelColumn">Fragment Size</td>
      <td style="white-space: pre-line">${stateSize}</td>
    </tr>

    <tr>
      <td class="labelColumn">cumulatedDepth</td>
      <td style="white-space: pre-line">${cumulatedDepth}</td>
    </tr> -->
    <tr>
      <td class="labelColumn">maxDepth</td>
      <td style="white-space: pre-line">${maxDepth}</td>
    </tr>
    <tr>
      <td class="labelColumn">nbBranch</td>
      <td style="white-space: pre-line">${nbBranch}</td>
    </tr>
  </table>

  <h2>Paths</h2>
  <table id="stateDetail">
    <tr>
     <th style="white-space: pre-line">depth</th>
     <th style="white-space: pre-line">class</th>
     <th style="white-space: pre-line">size (KB)</th>
     <th style="white-space: pre-line" class="nodePath">path</th>
    </tr>
     <#list nodeList as node>
    <tr class="nodeDetail">
     <td style="white-space: pre-line">${node.depth}</td>
     <td style="white-space: pre-line" class="nodeClass">${node.type}</td>
     <td style="white-space: pre-line">${node.size}</td>
     <td style="white-space: pre-line" class="nodePath">${node.path}</td>
      </tr>
     </#list>
  </table>

</div>

<script src="http://code.jquery.com/jquery-latest.min.js"
        type="text/javascript"></script>
<script>
jQuery(document).ready(
    function() {
      jQuery("#stateDetail tr.nodeDetail").each(function(index, value){
        var nodeClassText = this.find("td.nodeClass").text();
        if (nodeClassText.indexOf('UIAliasHolder') >= 0) {
          var nodePath = this.find("td.nodePath");
          var nodePathText = nodePath.text();
          nodePath.html("<a href='site/sessionInspector/jsfStateManager/uiAliasHolder/" + ${viewId}
              + "/" + ${sequenceId} + "/" + nodePath + "'  target='_blank' >" + nodePathText + "</a>");
        }
      }
      );
    });

</script>

</@block> </@extends>
