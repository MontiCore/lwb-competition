/*·(c)·https://github.com/MontiCore/monticore·*/

package mc.questionnaire;

import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symboltable.modifiers.AccessModifier;
import mc.questionnaire.type3.QTypeCheck;

public class QuestionnaireMill extends QuestionnaireMillTOP {
  public  static  void initMe (QuestionnaireMill a) {
    QuestionnaireMillTOP.initMe(a);
    QTypeCheck.init();
  }
  
  
  
  public static void initializeTypes() {
    BasicSymbolsMill.initializePrimitives();
    BasicSymbolsMill.initializeString();
    // Add special types (money + date)
    // add money symbol
    BasicSymbolsMill.globalScope().add(BasicSymbolsMill.typeSymbolBuilder()
        .setName("money")
        // this is not Java's String
        .setFullName("money")
        .setEnclosingScope(BasicSymbolsMill.globalScope())
        .setSpannedScope(BasicSymbolsMill.scope())
        .setAccessModifier(AccessModifier.ALL_INCLUSION)
        .build()
    );
    
    // add date symbol
    // Note: We are skipping calculations of dates on purpose,
    // as [date] - [date] = [date interval ⊂ number ]
    BasicSymbolsMill.globalScope().add(BasicSymbolsMill.typeSymbolBuilder()
        .setName("date")
        // this is not Java's String
        .setFullName("date")
        .setEnclosingScope(BasicSymbolsMill.globalScope())
        .setSpannedScope(BasicSymbolsMill.scope())
        .setAccessModifier(AccessModifier.ALL_INCLUSION)
        .build()
    );
  }
}
