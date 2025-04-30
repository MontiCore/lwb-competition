/*·(c)·https://github.com/MontiCore/monticore·*/
package mc;

import de.monticore.ast.ASTNode;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.generating.templateengine.QTemplateHookpoint;
import de.monticore.generating.templateengine.StringHookPoint;
import de.monticore.generating.templateengine.TemplateHookPoint;
import de.se_rwth.commons.logging.Log;
import mc.qls.QLSMill;
import mc.qls._ast.*;
import mc.qls._visitor.QLSVisitor2;
import mc.questionnaire._ast.ASTForm;
import mc.questionnaire._ast.ASTQuestion;
import mc.questionnaire._symboltable.QuestionSymbol;

import java.util.*;

/**
 * This class maps a type to a widget template
 */
public class QuestionStyler implements QLSVisitor2 {
  
  protected final GlobalExtensionManagement glex;
  protected final QuestionCollector questionCollector;
  protected final ASTForm form;
  
  protected Optional<String> titleOpt = Optional.empty();
  
  public QuestionStyler(GlobalExtensionManagement glex, QuestionCollector questionCollector,
      ASTForm form) {
    this.glex = glex;
    this.questionCollector = questionCollector;
    this.form = form;
  }
  
  @Override
  public void visit(ASTQuestionStyle node) {
    for (var question : findOrError(node.getName(), node)) {
      questions.add(question.getAstNode());
    }
  }
  
  @Override
  public void endVisit(ASTQuestionStyle node) {
    questions.clear();
  }
  
  @Override
  public void visit(ASTTitleElement node) {
    this.titleOpt = Optional.of(node.getString());
  }
  
  List<ASTQuestion> questions = new ArrayList<>();
  
  @Override
  public void visit(ASTStyleQuestion node) {
    for (var question : findOrError(node.getName(), node)) {
      questions.add(question.getAstNode());
    }
  }
  
  @Override
  public void endVisit(ASTStyleQuestion node) {
    questions.clear();
  }
  
  @Override
  public void visit(ASTQuestionElementColorStyle node) {
    this.questions.forEach(q -> glex.addAfterTemplate("q.QStyle", q,
        new StringHookPoint("color: " + node.getColor() + ";")));
  }
  
  @Override
  public void visit(ASTQuestionElementFontStyle node) {
    this.questions.forEach(q -> glex.addAfterTemplate("q.QStyle", q,
        new StringHookPoint("font-family: " + node.getFont() + ";")));
  }
  
  @Override
  public void visit(ASTQuestionElementWidget node) {
    for (var q : this.questions) {
      Map<String, Object> args = new HashMap<>();
      System.err.println("Widget " + q.getName());
      for (var p : node.getWidgetParamList()) {
        if (p.isPresentValue()) {
          args.put(p.getKey(), QLSMill.prettyPrint(p.getValue(), false));
        }
        else {
          args.put(p.getKey(), p.getKey());
        }
      }
      glex.replaceTemplate(QuestionDefaultStyler.DEFAULT_TEMPLATE, q,
          new QTemplateHookpoint("q.widget." + node.getWidget(), args));
    }
  }
  
  public Optional<String> getTitleOpt() {
    return titleOpt;
  }
  
  protected Collection<QuestionSymbol> findOrError(String name, ASTNode node) {
    var ret = form.getSpannedScope().resolveQuestionMany(name);
    if (ret.isEmpty()) {
      Log.error("Failed to find question with name " + name, node.get_SourcePositionStart(),
          node.get_SourcePositionEnd());
    }
    return ret;
  }
}
