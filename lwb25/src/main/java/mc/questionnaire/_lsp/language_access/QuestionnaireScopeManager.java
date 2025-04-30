/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._lsp.language_access;

import de.monticore.io.paths.MCPath;
import de.se_rwth.commons.logging.Log;
import mc.questionnaire.QuestionnaireMill;
import mc.questionnaire._ast.ASTForm;
import mc.questionnaire._symboltable.IQuestionnaireArtifactScope;
import mc.questionnaire._visitor.QuestionnaireTraverser;
import mc.questionnaire.type3.QTypeCompleter;


public class QuestionnaireScopeManager extends QuestionnaireScopeManagerTOP {
  
  protected final QuestionnaireTraverser traverser;
  
  public QuestionnaireScopeManager() {
    this.traverser = QuestionnaireMill.inheritanceTraverser();
    traverser.add4Questionnaire(new QTypeCompleter());
  }
  
  @Override
  public void initGlobalScope(MCPath modelPath) {
    super.initGlobalScope(modelPath);
    syncAccessGlobalScope(gs -> QuestionnaireMill.initializeTypes());
  }
  
  public QuestionnaireArtifactScopeWithFindings createArtifactScope(ASTForm ast,
      IQuestionnaireArtifactScope oldArtifactScope) {
    var res = super.createArtifactScope(ast, oldArtifactScope);
    // 2nd step of STC
    Log.getFindings().clear();
    traverser.clearTraversedElements();
    ast.accept(traverser);
    res.findings.addAll(Log.getFindings());
    return res;
  }
  
}
