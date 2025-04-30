/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._cocos;

import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.Log;
import mc.questionnaire._ast.ASTIfGuard;

/**
 * CoCo for checking that a guard uses a boolean expression
 */
public class BooleanGuardCoCo implements QuestionnaireASTIfGuardCoCo {
  @Override
  public void check(ASTIfGuard node) {
    var sym = TypeCheck3.typeOf(node.getGuard()).printFullName();
    if (!"boolean".equals(sym)) {
      Log.error("0x001: Guard expression is not a boolean, found " + sym ,
          node.getGuard().get_SourcePositionStart(), node.getGuard().get_SourcePositionEnd());
    }
  }
}
