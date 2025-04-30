/*·(c)·https://github.com/MontiCore/monticore·*/
package mc;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.expressionsbasis._ast.ASTNameExpression;
import de.monticore.expressions.expressionsbasis._visitor.ExpressionsBasisVisitor2;
import mc.questionnaire.QuestionnaireMill;
import mc.questionnaire._ast.ASTIfGuard;
import mc.questionnaire._ast.ASTQuestion;
import mc.questionnaire._visitor.QuestionnaireVisitor2;

import java.util.*;

public class QuestionCollector implements QuestionnaireVisitor2 {
  protected Map<String, Set<String>> questionToEffected = new HashMap<>();
  protected Map<String, String> questionToGuard = new HashMap<>();
  protected Map<String, String> questionToCompute = new HashMap<>();
  protected List<ASTQuestion> questions = new ArrayList<>();

  protected Stack<StackData> stack = new Stack<>();


  @Override
  public void visit(ASTIfGuard node) {
    String g = QuestionnaireMill.prettyPrint(node.getGuard(), false);
    var data = new StackData(stack.isEmpty() ? g : "(" + stack.peek().guard + ") && (" + g + ")");
    stack.push(data);
    collectQuestionNames(data.mentionedQuestionsInGuard, node.getGuard());
  }

  @Override
  public void endVisit(ASTIfGuard node) {
    stack.pop();
  }

  @Override
  public void visit(ASTQuestion node) {
    questions.add(node);
    Set<String> q = questionToEffected.computeIfAbsent(node.getName(), s -> new HashSet<>());
    this.stack.forEach(d -> q.addAll(d.mentionedQuestionsInGuard));
    if (node.isPresentComputed()) {
      collectQuestionNames(q, node.getComputed());
      questionToCompute.put(node.getName(), QuestionnaireMill.prettyPrint(node.getComputed(), false));
    }

    if (!stack.isEmpty()) {
      questionToGuard.put(node.getName(), stack.peek().guard);
    }
  }

  protected void collectQuestionNames(Collection<String> questions, ASTExpression expression) {
    var traverser = QuestionnaireMill.inheritanceTraverser();
    traverser.add4ExpressionsBasis(new ExpressionsBasisVisitor2() {
      @Override
      public void visit(ASTNameExpression node) {
        questions.add(node.getName());
      }
    });
    expression.accept(traverser);
  }

  public static class StackData {
    Set<String> mentionedQuestionsInGuard = new HashSet<>();
    String guard;

    public StackData(String guard) {
      this.guard = guard;
    }
  }

  public Map<String, Set<String>> getQuestionToAffectedBy() {
    return questionToEffected;
  }

  public Map<String, String> getQuestionToGuard() {
    return questionToGuard;
  }

  public List<ASTQuestion> getQuestions() {
    return questions;
  }

  public Map<String, String> getQuestionToCompute() {
    return questionToCompute;
  }
}
