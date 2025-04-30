${tc.signature("title", "section", "questions", "question_to_effects",
"questions_to_guard", "question_to_compute", "emptyArgs", "pages")}
<html lang="en">
<head>
	<title>Questionnaire ${title}</title>
</head>
<body>
<h1>Questionnaire: ${title}</h1>

<#--A note on pages: -->
<#if pages??>
  <nav style="display: inline-flex">
    <#list pages as page>
      <a onclick="showPage('page_${page.hashCode()}')">${page.title}</a>
      <#sep> |
    </#list>
  </nav>
</#if>

${tc.includeArgs("FormSection", [section, emptyArgs])}

<br/>
<hr/>
<button onClick="save()">Save</button>


<script>
    <#list questions as q>
    ${q.getName()} = undefined;
    </#list>

    function onChange(id, val) {
        switch (id) {
          <#list questions as q>
            case '${q.getName()}':
                window.${q.getName()} = val; // use global (window) variables
            <#if question_to_effects[q.getName()]??>
            <#list question_to_effects[q.getName()] as e>
                triggerCheck('${e}');
            </#list>
            </#if>

                break;

          </#list>
        }
    }

    function triggerCheck(id) {
        switch (id) {
          <#list questions as q>
            case '${q.getName()}':
            <#if questions_to_guard[q.getName()]??>
            <#assign guard = questions_to_guard[q.getName()]>
                if (${guard}) {
                    document.getElementById("row_${q.getName()}").style.display = 'table-row';
                } else {
                    document.getElementById("row_${q.getName()}").style.display = 'none';
                }
            </#if>
            <#if question_to_compute[q.getName()]??>
                // Update affected questions
                document.getElementById("q_${q.getName()}").value = ${question_to_compute[q.getName()]};
            </#if>
                break;
          </#list>
        }
    }

    // Trigger at load
    <#list questions as q>
    triggerCheck('${q.getName()}');
    </#list>

    function save() {
        const data = {
            <#list questions as q>
            '${q.getName()}': ${q.getName()},
            </#list>
        };
        console.log(JSON.stringify(data));
        // Could be submitted via fetch/a form/etc.
    }
    function showPage(id) {
      [...document.querySelectorAll(".page")].forEach(e => e.style.display = 'none');
      document.getElementById(id).style.display = 'block';
    }
    // Show initial page
    showPage('page_${pages[0].hashCode()}');

</script>

</body>
</html>
