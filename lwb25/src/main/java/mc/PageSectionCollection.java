package mc;

import de.monticore.ast.ASTNode;
import de.se_rwth.commons.logging.Log;
import mc.qls._ast.*;
import mc.qls._visitor.QLSVisitor2;
import mc.questionnaire._ast.ASTForm;
import mc.questionnaire._ast.ASTQuestion;
import mc.questionnaire._symboltable.QuestionSymbol;

import java.util.*;

/**
 */
public class PageSectionCollection implements QLSVisitor2 {
  
  
  protected final ASTForm form;
  protected final Stack<QElem> stack = new Stack<>();
  protected final List<ASTQuestion> rootQuestions;
  protected final List<QElem> pages = new ArrayList<>();
  
  public PageSectionCollection(ASTForm form, List<ASTQuestion> rootQuestions) {
    this.form = form;
    this.rootQuestions = rootQuestions;
    this.stack.push(new QElem(false, ""));
  }
  
  @Override
  public void visit(ASTPage node) {
    var page = new QElem(true, node.getString());
    stack.peek().children.add(page);
    stack.push(page);
    pages.add(page);
  }
  
  @Override
  public void endVisit(ASTPage node) {
    stack.pop();
  }
  
  @Override
  public void visit(ASTSection node) {
    var page = new QElem(false, node.getString());
    stack.peek().children.add(page);
    stack.push(page);
  }
  
  @Override
  public void endVisit(ASTSection node) {
    stack.pop();
  }
  
  @Override
  public void visit(ASTStyleQuestion node) {
    for (var q : findOrError(node.getName(), node)) {
      stack.peek().children.add(new QElem(q.getAstNode()));
      this.rootQuestions.remove(q.getAstNode());
    }
  }
  
  public static class QElem {
    final boolean isPage;
    final String title;
    final Optional<ASTQuestion> question;
    final List<QElem> children = new ArrayList<>();
    
    public QElem(boolean isPage, String title) {
      this.isPage = isPage;
      this.title = title;
      this.question = Optional.empty();
    }
    
    public QElem(ASTQuestion question) {
      this.isPage = false;
      this.title = "";
      this.question = Optional.of(question);
    }
    
    public boolean isPage() {
      return isPage;
    }
    
    public String getTitle() {
      return title;
    }
    
    public Optional<ASTQuestion> getQuestion() {
      return question;
    }
    
    public List<QElem> getChildren() {
      return children;
    }
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
