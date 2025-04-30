/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire.type3;

import de.monticore.expressions.commonexpressions.types3.CommonExpressionsTypeVisitor;
import de.monticore.expressions.expressionsbasis.types3.ExpressionBasisTypeVisitor;
import de.monticore.literals.mccommonliterals.types3.MCCommonLiteralsTypeVisitor;
import de.monticore.types.mcbasictypes.types3.MCBasicTypesTypeVisitor;
import de.monticore.types3.Type4Ast;
import de.monticore.types3.generics.context.InferenceContext4Ast;
import de.monticore.types3.util.*;
import de.monticore.visitor.ITraverser;
import de.se_rwth.commons.logging.Log;
import mc.questionnaire.QuestionnaireMill;

public class QTypeCheck extends MapBasedTypeCheck3 {

  public static void init() {
    Log.trace("init TC3", "TypeCheck setup");

    // initialize static delegates
    QTypeVisitorOperatorCalculator.init();

    // traverser of your language
    // no inheritance traverser is used, as it is recommended
    // to create a new traverser / TC3 for each language.
    var traverser = QuestionnaireMill.traverser();
    // map to store the results
    Type4Ast type4Ast = new Type4Ast();


    // Literals


    {
      MCCommonLiteralsTypeVisitor visMCCommonLiterals = new MCCommonLiteralsTypeVisitor();
      visMCCommonLiterals.setType4Ast(type4Ast);
      traverser.add4MCCommonLiterals(visMCCommonLiterals);
    }

    // Expressions

    {
      CommonExpressionsTypeVisitor tV = new CommonExpressionsTypeVisitor();
      tV.setType4Ast(type4Ast);
      traverser.add4CommonExpressions(tV);
      traverser.setCommonExpressionsHandler(tV);
    }
    {
      var tV = new ExpressionBasisTypeVisitor();
      tV.setType4Ast(type4Ast);
      traverser.add4ExpressionsBasis(tV);
    }


    // MCTypes

    {
      MCBasicTypesTypeVisitor visMCBasicTypes = new MCBasicTypesTypeVisitor();
      visMCBasicTypes.setType4Ast(type4Ast);
      traverser.add4MCBasicTypes(visMCBasicTypes);
    }

    // create the TypeCheck3 delegate
    QTypeCheck oclTC3 = new QTypeCheck(traverser, type4Ast);
    oclTC3.setThisAsDelegate();
  }


  protected QTypeCheck (ITraverser typeTraverser, Type4Ast type4Ast) {
    super(typeTraverser, type4Ast, new InferenceContext4Ast());
  }
}

