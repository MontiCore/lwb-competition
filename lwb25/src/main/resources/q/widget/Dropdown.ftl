${tc.signature("args")}
<#-- For a perfect assignment, one should add the type-"casting" to its specific type (bool, string, ...) -->
<#-- For this example, we are happy with just bools -->
<select id="q_${ast.name}" onchange="onChange('${ast.name}', this.value === 'true' )"
>
  <#list args?keys as k>
    <option value="${args[k]}">${k}</option>
  </#list>

</select>
