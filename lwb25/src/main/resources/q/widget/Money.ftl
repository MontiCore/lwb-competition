${tc.signature("args")}
${ast.getName()}
<input type="number" value="${args['default']!0}" step="0.01"  id="q_${ast.name}" onchange="onChange('${ast.name}', this.value )"
<#if ast.isPresentComputed()> readonly disabled </#if>/> &#8364;
