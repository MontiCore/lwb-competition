package mc;

import de.se_rwth.commons.logging.LogStub;
import mc.questionnaire.QuestionnaireMill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class CoCoTest {
  @BeforeEach
  public void prepare() {
    LogStub.clearFindings();
    LogStub.initPlusLog();
    LogStub.enableFailQuick(false);
    QuestionnaireMill.init();
    QuestionnaireMill.initializeTypes();
  }

  protected void check(File file) throws Exception {
    GuiGenerator gen = new GuiGenerator();
    gen.generate(file);
  }

  @Test
  public void testCycle() throws Exception {
    check(new File("src/test/resources/invalid/Cycle.q"));
    Assertions.assertEquals(1, LogStub.getFindings().size());
    Assertions.assertEquals("0x002", LogStub.getFindings().get(0).getMsg().substring(0, 5));
  }

  @Test
  public void testCycle2() throws Exception {
    check(new File("src/test/resources/invalid/Cycle2.q"));
    Assertions.assertEquals(1, LogStub.getFindings().size());
    Assertions.assertEquals("0x002", LogStub.getFindings().get(0).getMsg().substring(0, 5));
  }

  @Test
  public void testNames1() throws Exception {
    check(new File("src/test/resources/invalid/Names1.q"));
    Assertions.assertEquals(3, LogStub.getFindings().size());
    Assertions.assertEquals("0x003", LogStub.getFindings().get(2).getMsg().substring(0, 5));
  }

  @Test
  public void testNames2() throws Exception {
    check(new File("src/test/resources/invalid/Names2.q"));
    Assertions.assertEquals(3, LogStub.getFindings().size());
    Assertions.assertEquals("0x003", LogStub.getFindings().get(2).getMsg().substring(0, 5));
  }

  @Test
  public void testTypes1() throws Exception {
    check(new File("src/test/resources/invalid/Types1.q"));
    Assertions.assertEquals(1, LogStub.getFindings().size());
    Assertions.assertEquals("0x004", LogStub.getFindings().get(0).getMsg().substring(0, 5));
  }

  @Test
  public void testTypes2() throws Exception {
    check(new File("src/test/resources/invalid/Types2.q"));
    Assertions.assertEquals(1, LogStub.getFindings().size());
    Assertions.assertEquals("0x004", LogStub.getFindings().get(0).getMsg().substring(0, 5));
  }

  @Test
  public void testTypes3() throws Exception {
    check(new File("src/test/resources/invalid/Types3.q"));
    Assertions.assertEquals(2, LogStub.getFindings().size());
    Assertions.assertEquals("0xB0113", LogStub.getFindings().get(0).getMsg().substring(0, 7));
    Assertions.assertEquals("0x004", LogStub.getFindings().get(1).getMsg().substring(0, 5));
  }

  @Test
  public void testCorrect() throws Exception {
    check(new File("src/test/resources/Box1HouseOwning.q"));
    Assertions.assertEquals(0, LogStub.getFindings().size());
  }
  
  @Test
  public void testCorrectAllTypes() throws Exception {
    check(new File("src/test/resources/AllTypes.q"));
    Assertions.assertEquals(0, LogStub.getFindings().size());
  }
}
