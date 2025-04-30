${tc.signature("args")}
<input id="q_${ast.name}" min="${args['min']}" max="${args['max']}" value="${args['default']}"
       type="range" onchange="onChange('${ast.name}', this.value )"
<#if ast.isPresentComputed()> readonly disabled </#if>
/>
