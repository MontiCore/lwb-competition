/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._lsp.language_access;

import de.mclsg.lsp.document_management.DocumentManager;
import mc.QuestionCollector;
import mc.questionnaire.QuestionnaireMill;
import mc.questionnaire._ast.ASTForm;
import mc.questionnaire._ast.ASTQuestionnaireNode;
import mc.questionnaire._cocos.QuestionnaireCoCoChecker;

public class QuestionnaireLspCoCoRunner extends QuestionnaireLspCoCoRunnerTOP {
  
  public QuestionnaireLspCoCoRunner(DocumentManager documentManager) {
    super(documentManager);
  }
  
  @Override
  public void runAllCoCos(ASTForm ast) {
    var collector = new QuestionCollector();
    var traverser = QuestionnaireMill.inheritanceTraverser();
    traverser.add4Questionnaire(collector);
    ast.accept(traverser);
    
    new QuestionnaireCoCoChecker(collector).checkAll((ASTQuestionnaireNode) ast);
    
  }
}
