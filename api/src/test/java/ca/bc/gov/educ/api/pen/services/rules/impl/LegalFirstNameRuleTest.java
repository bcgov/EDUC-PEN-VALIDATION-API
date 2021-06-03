package ca.bc.gov.educ.api.pen.services.rules.impl;

import ca.bc.gov.educ.api.pen.services.model.PENNameText;
import ca.bc.gov.educ.api.pen.services.service.PENNameTextService;
import ca.bc.gov.educ.api.pen.services.struct.v1.PenRequestStudentValidationPayload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * The type Legal first name rule test.
 */
@RunWith(JUnitParamsRunner.class)
public class LegalFirstNameRuleTest {
  private LegalFirstNameRule rule;
  private static List<PENNameText> penNameTexts;
  /**
   * The Service.
   */
  @Mock
  PENNameTextService service;

  /**
   * Sets .
   *
   * @throws IOException the io exception
   */
  @Before
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);
    this.rule = new LegalFirstNameRule(this.service);
    if (penNameTexts == null) {
      final File file = new File(
          Objects.requireNonNull(this.getClass().getClassLoader().getResource("pen_names_text_sample.json")).getFile()
      );
      penNameTexts = new ObjectMapper().readValue(file, new TypeReference<>() {
      });
    }
  }

  /**
   * Test validate given different legal first name should return results.
   *
   * @param legalFirstName the legal first name
   * @param expectedErrors the expected errors
   */
  @Test
  @Parameters({
    "LLLLL, 1",
    "null, 1",
    "JJ, 1",
    "JJXXY, 0",
    "JJJ, 1",
    "JJJJ, 1",
    ", 1",
    "XX, 1",
    "ZZ, 1",
    "BLANK, 1",
    "AVAILABLE, 1",
    "ABC, 1",
    "mishra, 0",
    "ALIAS, 1",
    "ASD, 1",
    "BITCH,1",
    "BRING, 1",
    "CASH ONLY, 1",
    "DATA, 1",
    "DOE, 1",
    "DOESN'T HAVE ONE, 1",
    "DS TAM, 1",
    "FICTITIOUS, 1",
    "FANCYPANTS, 1",
    "ESTATE, 1",
    "DUMMY, 1",

    "DUPLICATE, 1"
  })
  public void testValidate_givenDifferentLegalFirstName_shouldReturnResults(String legalFirstName, final int expectedErrors) {
    if ("null".equals(legalFirstName)) {
      legalFirstName = null;
    }
    when(this.service.getPenNameTexts()).thenReturn(penNameTexts);
    final PenRequestStudentValidationPayload payload = PenRequestStudentValidationPayload.builder().isInteractive(false).transactionID(UUID.randomUUID().toString()).legalFirstName(legalFirstName).build();
    final var result = this.rule.validate(payload);
    assertThat(result).size().isEqualTo(expectedErrors);
  }

  @Test
  @Parameters({"true, WARNING",
    "false, ERROR"})
  public void testValidate_givenLegalFirstNameBlankInDifferentMode_shouldReturnResultsWithWarningOrError(boolean isInteractive, String issueSeverityCode) {
    when(this.service.getPenNameTexts()).thenReturn(penNameTexts);
    final PenRequestStudentValidationPayload payload = PenRequestStudentValidationPayload.builder().isInteractive(isInteractive).transactionID(UUID.randomUUID().toString()).legalFirstName("").build();
    final var result = this.rule.validate(payload);
    assertThat(result).size().isEqualTo(1);
    assertThat(result.get(0).getPenRequestBatchValidationIssueSeverityCode()).isEqualTo(issueSeverityCode);
  }
}
