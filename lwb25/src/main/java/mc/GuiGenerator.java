/*·(c)·https://github.com/MontiCore/monticore·*/
package mc;

import de.monticore.generating.GeneratorEngine;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import mc.qls.QLSMill;
import mc.qls._ast.ASTStyleFile;
import mc.questionnaire.QuestionnaireMill;
import mc.questionnaire._ast.ASTQuestion;
import mc.questionnaire._ast.ASTQuestionnaireNode;
import mc.questionnaire.type3.QTypeCompleter;
import mc.questionnaire._cocos.QuestionnaireCoCoChecker;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GuiGenerator {

  public static void main(String[] args) throws IOException {
    File input = new File(args.length == 0 ? "src/test/resources/Box1HouseOwning.q" : args[1]);
    new GuiGenerator().generate(input);
  }

  public void generate(File input) throws IOException {
    QLSMill.init();
    QuestionnaireMill.initializeTypes();

    var ast = QuestionnaireMill.parser().parse(input.getAbsolutePath());
    
    File styleFile = new File(input.getParentFile(), input.getName().substring(0, input.getName().length()-2) + ".qls");
    Optional<ASTStyleFile> styleAST = Optional.empty();
    if (styleFile.exists()) {
      System.err.println("With style");
      styleAST = QLSMill.parser().parse(styleFile.getAbsolutePath());
    }

    {
      QuestionnaireMill.scopesGenitorDelegator().createFromAST(ast.get());
      var t = QuestionnaireMill.inheritanceTraverser();
      t.add4Questionnaire(new QTypeCompleter());
      ast.get().accept(t);
    }

    var glex = new GlobalExtensionManagement();

    var collector = new QuestionCollector();
    {
      var traverser = QLSMill.inheritanceTraverser();
      traverser.add4Questionnaire(collector);
      ast.get().accept(traverser);
    }

    new QuestionnaireCoCoChecker(collector).checkAll((ASTQuestionnaireNode) ast.get());




    Map<String, Set<String>> question_to_effects = new HashMap<>();
    for (var e : collector.getQuestionToAffectedBy().entrySet()) {
      for (var k : e.getValue()) {
        question_to_effects.computeIfAbsent(k, k1 -> new HashSet<>()).add(e.getKey());
      }
    }
    
    
    { // Apply default styles
      var traverser = QLSMill.inheritanceTraverser();
      traverser.add4Questionnaire(new QuestionDefaultStyler(glex));
      ast.get().accept(traverser);
    }
    
    String title = "No Title Given";
    List<ASTQuestion> rootQuestions = new ArrayList<>(collector.getQuestions());
    List<PageSectionCollection.QElem> pages = new ArrayList<>();
    PageSectionCollection.QElem rootSection;
    if (styleAST.isPresent()) { // Apply custom styles
      var traverser = QLSMill.inheritanceTraverser();
      var styler = new QuestionStyler(glex, collector, ast.get());
      var sectionCollector = new PageSectionCollection(ast.get(), rootQuestions);
      traverser.add4QLS(styler);
      traverser.add4QLS(sectionCollector);
      styleAST.get().accept(traverser);
      if (styler.getTitleOpt().isPresent()) {
        title = styler.getTitleOpt().get();
      }
      rootSection = sectionCollector.stack.pop();
      pages = sectionCollector.pages;
    } else {
      rootSection = new PageSectionCollection.QElem(false, "");
    }
    // Add left-over questions
    rootQuestions.forEach(q->rootSection.children.add(new PageSectionCollection.QElem(q)));
    


    GeneratorSetup setup = new GeneratorSetup();
    setup.setTracing(false);
    setup.setGlex(glex);
    setup.setCommentStart("<!--");
    setup.setCommentEnd("-->");
    GeneratorEngine engine = new GeneratorEngine(setup);

    setup.setOutputDirectory(new File("genout"));

    engine.generate("q.Form", new File( ast.get().getName() + ".html").toPath(), ast.get(),
                    title,
                    rootSection,
                    collector.getQuestions(),
                    question_to_effects,
                    collector.getQuestionToGuard(),
                    collector.getQuestionToCompute(),
                    new HashMap<>(), // Empty arguments,
                    pages
        
    );

  }

}
