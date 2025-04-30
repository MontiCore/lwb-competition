/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire.type3;

import de.monticore.types3.TypeCheck3;
import mc.questionnaire._ast.ASTQuestion;
import mc.questionnaire._visitor.QuestionnaireVisitor2;

/**
 * Complete the ST by setting types
 */
public class QTypeCompleter implements QuestionnaireVisitor2 {
  @Override
  public void visit(ASTQuestion node) {
    node.getSymbol().setType(TypeCheck3.symTypeFromAST(node.getMCType()));
  }
}
