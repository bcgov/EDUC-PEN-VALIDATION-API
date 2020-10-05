package ca.bc.gov.educ.api.pen.validation.rules.impl;

import ca.bc.gov.educ.api.pen.validation.model.PENNameText;
import ca.bc.gov.educ.api.pen.validation.service.PENNameTextService;
import ca.bc.gov.educ.api.pen.validation.struct.v1.PenRequestStudentValidationPayload;
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

@RunWith(JUnitParamsRunner.class)
public class LegalMiddleNameRuleTest {
  private LegalMiddleNameRule rule;
  private static List<PENNameText> penNameTexts;
  @Mock
  PENNameTextService service;

  @Before
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);
    rule = new LegalMiddleNameRule(service);
    if (penNameTexts == null) {
      final File file = new File(
          Objects.requireNonNull(getClass().getClassLoader().getResource("pen_names_text_sample.json")).getFile()
      );
      penNameTexts = new ObjectMapper().readValue(file, new TypeReference<>() {
      });
    }
  }

  @Test
  @Parameters({
      "null, 0",
      ", 0",
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
      "DOESN'T HAVE ONE, 2",
      "DS TAM, 1",
      "FICTITIOUS, 1",
      "FANCYPANTS, 1",
      "ESTATE, 1",
      "DUMMY, 1",
      "DUPLICATE, 1"
  })
  public void testValidate_givenDifferentLegalMiddleName_shouldReturnResults(String legalMiddleName, int expectedErrors) {
    if ("null".equals(legalMiddleName)) {
      legalMiddleName = null;
    }
    when(service.getPenNameTexts()).thenReturn(penNameTexts);
    PenRequestStudentValidationPayload payload = PenRequestStudentValidationPayload.builder().isInteractive(false).transactionID(UUID.randomUUID().toString()).legalMiddleNames(legalMiddleName).build();
    var result = rule.validate(payload);
    assertThat(result).size().isEqualTo(expectedErrors);
  }

  @Test
  @Parameters({
      "null,XX,ahjks, 0",
      ",XX,ahjks, 0",
      "XX,XX,ahjks, 1",
      "ZZ,XX,ahjks, 1",
      "john,john,cox, 1",
      "yang,Mingwei,yang, 1",
      "Vel,Marco,Vel, 1",
      "Prakash,Om,Mishra,0",
      "Om Mishra,Om,Mishra,1",

  })
  public void testValidate_givenDifferentLegalMiddleNameAndFirstNameAndLastName_shouldReturnResults(String legalMiddleName, String legalFirstName, String legalLastName, int expectedErrors) {
    if ("null".equals(legalMiddleName)) {
      legalMiddleName = null;
    }
    when(service.getPenNameTexts()).thenReturn(penNameTexts);
    PenRequestStudentValidationPayload payload = PenRequestStudentValidationPayload.builder().isInteractive(false).transactionID(UUID.randomUUID().toString()).legalMiddleNames(legalMiddleName).legalLastName(legalLastName).legalFirstName(legalFirstName).build();
    var result = rule.validate(payload);
    assertThat(result).size().isEqualTo(expectedErrors);
  }
}