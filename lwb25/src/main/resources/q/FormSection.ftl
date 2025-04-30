${tc.signature("section", "emptyArgs")}

<table>
    <#list section.getChildren() as qe>
      <#if qe.question.isPresent()>
        <#assign q = qe.question.get()>
			<tr id="row_${q.name}" style="${tc.include("q.QStyle", q)}">
        <td>
					<label for="q_${q.name}">${q.label}</label>
				</td>
				<td>
            ${tc.includeArgs("q.widget.Text", q, [emptyArgs])}
				</td>
			</tr>
        <#elseif qe.isPage()>
</table>
<div style="border: 1px black solid; display: none" id="page_${qe.hashCode()}" class="page">
  <h2>Page: ${qe.title}</h2>
  ${tc.includeArgs("FormSection", [qe, emptyArgs])}
</div>
<table>
        <#else>
</table>
<div style="border: 1px gray solid">
      <h3>${qe.title}</h3>
          ${tc.includeArgs("FormSection", [qe, emptyArgs])}
</div>
<table>
			</#if>
    </#list>
</table>

