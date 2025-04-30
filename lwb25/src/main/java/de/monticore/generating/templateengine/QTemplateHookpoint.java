/*·(c)·https://github.com/MontiCore/monticore·*/
package de.monticore.generating.templateengine;

import de.monticore.ast.ASTNode;

import java.util.List;

/**
 * Template hookpoint overriding arguments
 */
public class QTemplateHookpoint extends TemplateHookPoint {
  
  public QTemplateHookpoint(String templateName, Object... templateArguments) {
    super(templateName, templateArguments);
  }
  
  @Override
  public String processValue(TemplateController controller, ASTNode node, List<Object> args) {
    return controller.processTemplate(templateName, node, this.templateArguments);
  }
}
