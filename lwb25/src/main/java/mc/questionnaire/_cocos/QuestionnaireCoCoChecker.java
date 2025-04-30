/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._cocos;

import mc.QuestionCollector;

/**
 * Prepares all Questionnaire CoCos
 */
public class QuestionnaireCoCoChecker  extends QuestionnaireCoCoCheckerTOP {
  public QuestionnaireCoCoChecker(QuestionCollector collector) {
    super();
    this.addCoCo(new BooleanGuardCoCo());
    this.addCoCo(new CircularCoCo(collector));
    this.addCoCo(new ReferencedQuestionCoCo());
    this.addCoCo(new ComputedTypeCoCo());
  }
}
