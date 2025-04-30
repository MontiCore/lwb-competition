/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._cocos;

import de.monticore.types.check.SymTypeExpression;
import de.monticore.types3.SymTypeRelations;
import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.Log;
import mc.questionnaire._ast.ASTQuestion;

/**
 * CoCo ensuring the correct computed type is used
 */
public class ComputedTypeCoCo implements QuestionnaireASTQuestionCoCo {

  @Override
  public void check(ASTQuestion node) {
    if (node.isPresentComputed()) {
      SymTypeExpression sym = TypeCheck3.typeOf(node.getComputed());

      if (sym.isObscureType()) {
        Log.error(String.format("0x004: Unable to derive type: %s ", sym.printFullName()),
            node.getComputed().get_SourcePositionStart(), node.getComputed().get_SourcePositionEnd());
        
        return;
      }
      if (node.getSymbol().getType().isObscureType()) {
        Log.error(String.format("0x004: Unable to use type of question: %s ", node.getSymbol().getType().printFullName()),
            node.getMCType().get_SourcePositionStart(), node.getMCType().get_SourcePositionEnd());
        
        return;
      }
      if (!SymTypeRelations.isCompatible(node.getSymbol().getType(), sym))
        Log.error(String.format("0x004: Question type %s is not compatible with computed type %s ",
                                node.getSymbol().getType().printFullName(), sym.printFullName()),
            node.getComputed().get_SourcePositionStart(), node.getComputed().get_SourcePositionEnd());
    }


  }

}
