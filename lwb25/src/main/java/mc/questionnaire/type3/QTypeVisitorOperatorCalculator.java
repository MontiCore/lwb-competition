/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire.type3;

import de.monticore.types.check.SymTypeExpression;
import de.monticore.types3.util.TypeVisitorOperatorCalculator;

/**
 * Add our custom (money) type to the type check as an alias to a number
 */
public class QTypeVisitorOperatorCalculator extends TypeVisitorOperatorCalculator {
  public static void init() {
    TypeVisitorOperatorCalculator.setDelegate(new QTypeVisitorOperatorCalculator());
  }

  @Override
  protected SymTypeExpression calculatePlusMinusModulo(SymTypeExpression left, SymTypeExpression right) {
    var res = super.calculatePlusMinusModulo(left, right);
    if (res.isObscureType()) {
      if (left.printFullName().equals("money") && right.printFullName().equals("money")) {
        return left;
      }
    }
    return res;
  }
}
