/*·(c)·https://github.com/MontiCore/monticore·*/
package mc;

import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.generating.templateengine.QTemplateHookpoint;
import de.monticore.generating.templateengine.TemplateHookPoint;
import de.se_rwth.commons.logging.Log;
import mc.questionnaire._ast.ASTQuestion;
import mc.questionnaire._visitor.QuestionnaireVisitor2;

import java.util.Collections;
import java.util.HashMap;

/**
 * This class maps a type to a widget template
 */
public class QuestionDefaultStyler implements QuestionnaireVisitor2 {
  protected final GlobalExtensionManagement glex;
  public static final String DEFAULT_TEMPLATE = "q.widget.Text";

  public QuestionDefaultStyler(GlobalExtensionManagement glex) {
    this.glex = glex;
  }

  @Override
  public void visit(ASTQuestion node) {
    // Replace the question template
    String typePrinted = node.getMCType().printType();
    if (typePrinted.equals("boolean")) {
      glex.replaceTemplate(DEFAULT_TEMPLATE, node, new QTemplateHookpoint("q.widget.Boolean", new HashMap<>()));
    } else if (typePrinted.equals("money")) {
      glex.replaceTemplate(DEFAULT_TEMPLATE, node, new QTemplateHookpoint("q.widget.Money", new HashMap<>()));
    } else if (typePrinted.equals("int")) {
      glex.replaceTemplate(DEFAULT_TEMPLATE, node, new QTemplateHookpoint("q.widget.Integer", new HashMap<>()));
    } else if (typePrinted.equals("date")) {
      glex.replaceTemplate(DEFAULT_TEMPLATE, node, new QTemplateHookpoint("q.widget.Date", new HashMap<>()));
    } else if (typePrinted.equals("double")) {
      glex.replaceTemplate(DEFAULT_TEMPLATE, node, new QTemplateHookpoint("q.widget.Double", new HashMap<>()));
    } else if (!typePrinted.equals("String")) {
      // Strings are
      Log.warn("no special handling of  " + typePrinted);
    }
  }

}
