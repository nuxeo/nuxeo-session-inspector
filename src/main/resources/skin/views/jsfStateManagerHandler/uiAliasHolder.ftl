<@extends src="base.ftl"> <@block name="header_scripts"> </@block>

<@block name="body">

<div>
  <h1>UIAliasHolder</h1>

  <table>
    <tr>
      <td class="labelColumn">HashCode</td>
      <td>${viewId}</td>
    </tr>
    <tr>
      <td class="labelColumn">Path</td>
      <td style="white-space: pre-line">${path}</td>
    </tr>
    <tr>
      <td class="labelColumn">Size (KB)</td>
      <td style="white-space: pre-line">${size}</td>
    </tr>
  </table>

  <h2>Infos</h2>

</div>

</@block> </@extends>
