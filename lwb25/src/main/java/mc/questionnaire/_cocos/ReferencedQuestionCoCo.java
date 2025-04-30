/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._cocos;

import de.monticore.expressions.expressionsbasis._ast.ASTNameExpression;
import de.monticore.expressions.expressionsbasis._cocos.ExpressionsBasisASTNameExpressionCoCo;
import de.se_rwth.commons.logging.Log;
import mc.questionnaire._symboltable.IQuestionnaireScope;

/**
 * CoCo ensuring the references question exists
 */
public class ReferencedQuestionCoCo implements ExpressionsBasisASTNameExpressionCoCo {
  
  @Override
  public void check(ASTNameExpression node) {
    if (((IQuestionnaireScope) node.getEnclosingScope()).resolveQuestionMany(node.getName())
        .isEmpty()) {
      Log.error("0x003: Could not find question " + node.getName(), node.get_SourcePositionStart(),
          node.get_SourcePositionEnd());
    }
  }
}
