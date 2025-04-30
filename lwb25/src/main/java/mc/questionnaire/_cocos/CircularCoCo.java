/*·(c)·https://github.com/MontiCore/monticore·*/
package mc.questionnaire._cocos;

import de.se_rwth.commons.logging.Log;
import mc.QuestionCollector;
import mc.questionnaire._ast.ASTQuestion;

import java.util.HashSet;
import java.util.Set;

/**
 * CoCo forbidding circular question-dependencies
 */
public class CircularCoCo implements QuestionnaireASTQuestionCoCo {
  QuestionCollector collector;
  Set<String> visited = new HashSet<>(), currentStack = new HashSet<>();

  public CircularCoCo(QuestionCollector collector) {
    this.collector = collector;
  }

  @Override
  public void check(ASTQuestion node) {
    // Do a depth-first-search into the questions
    depthFirstSearch(node);
  }

  protected void depthFirstSearch(ASTQuestion node) {
    if (this.currentStack.contains(node.getName())) {
      Log.error("0x002: Cycle detected with questions " + currentStack, node.get_SourcePositionStart(), node.get_SourcePositionEnd());
    }
    if (this.visited.contains(node.getName())) {
      return;
    }

    visited.add(node.getName());


    if (collector.getQuestionToAffectedBy().containsKey(node.getName())) {
      currentStack.add(node.getName());

      for (String e : collector.getQuestionToAffectedBy().get(node.getName())) {
        for (var question : node.getEnclosingScope().resolveQuestionMany(e)) {
          // resolve multiple, as the name
          depthFirstSearch(question.getAstNode());
        }
      }

      currentStack.remove(node.getName());
    }


  }
}
