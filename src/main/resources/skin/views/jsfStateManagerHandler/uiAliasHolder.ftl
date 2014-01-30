<@extends src="base.ftl"> <@block name="header_scripts"> </@block>

<@block name="title">UIALiasHolder ${aliasId}</@block>

<@block name="body">

<div>
  <h1>UIAliasHolder</h1>

  <table>
    <tr>
      <td class="labelColumn">Alias Id</td>
      <td>${aliasId}</td>
    </tr>
    <tr>
      <td class="labelColumn">Path</td>
      <td>${path}</td>
    </tr>
    <tr>
      <td class="labelColumn">Mapper Size</td>
      <td>${mapperSize?c}</td>
    </tr>
  </table>

  <h2>Infos</h2>
  <table id="aliasDetail" class="tablesorter componentList">
    <thead>
      <tr>
        <th>Key</th>
        <th>Value</th>
      </tr>
    </thead>
    <tbody>
      <#list variables as variable>
      <tr class="variableDetail">
        <td>${variable.key}</td>
        <td>${variable.value.getExpressionString()}</td>
      </tr>
      </#list>
    </tbody>
  </table>

</div>

<script>
  jQuery(document)
      .ready(
          function() {
            jQuery(".tablesorter").tablesorter();
          });
</script>

</@block> </@extends>
