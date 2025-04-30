package mc;

import de.se_rwth.commons.logging.LogStub;
import mc.questionnaire.QuestionnaireMill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QParseTest {
  @BeforeEach
  public void prepare() {
    LogStub.initPlusLog();
    LogStub.enableFailQuick(false);
    QuestionnaireMill.init();
  }

  @Test
  public void testExample() throws Exception {
    var ast = QuestionnaireMill.parser().parse("src/test/resources/Box1HouseOwning.q");
    Assertions.assertTrue(ast.isPresent());
    Assertions.assertTrue(LogStub.getFindings().isEmpty());
    Assertions.assertEquals("Box1HouseOwning", ast.get().getName());
  }
}
