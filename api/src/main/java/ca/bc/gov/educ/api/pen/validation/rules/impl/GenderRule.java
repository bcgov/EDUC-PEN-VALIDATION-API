package ca.bc.gov.educ.api.pen.validation.rules.impl;

import ca.bc.gov.educ.api.pen.validation.rest.RestUtils;
import ca.bc.gov.educ.api.pen.validation.rules.BaseRule;
import ca.bc.gov.educ.api.pen.validation.struct.v1.PenRequestStudentValidationIssue;
import ca.bc.gov.educ.api.pen.validation.struct.v1.PenRequestStudentValidationPayload;
import com.google.common.base.Stopwatch;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ca.bc.gov.educ.api.pen.validation.constants.PenRequestStudentValidationFieldCode.GENDER;
import static ca.bc.gov.educ.api.pen.validation.constants.PenRequestStudentValidationIssueSeverityCode.ERROR;
import static ca.bc.gov.educ.api.pen.validation.constants.PenRequestStudentValidationIssueTypeCode.GENDER_ERR;
import static lombok.AccessLevel.PRIVATE;

/**
 * The type Gender rule.
 */
@Slf4j
public class GenderRule extends BaseRule {

  @Getter(PRIVATE)
  private final RestUtils restUtils;

  /**
   * Instantiates a new Gender rule.
   *
   * @param restUtils the rest utils
   */
  public GenderRule(RestUtils restUtils) {
    this.restUtils = restUtils;
  }

  /**
   * Validates the student record for the given rule.
   *
   * @param validationPayload the validation payload
   * @return the validation result as a list.
   */
  @Override
  public List<PenRequestStudentValidationIssue> validate(PenRequestStudentValidationPayload validationPayload) {
    var stopwatch = Stopwatch.createStarted();
    final List<PenRequestStudentValidationIssue> results = new LinkedList<>();
    var genderCodes = restUtils.getGenderCodes();
    String genderCode = validationPayload.getGenderCode();
    if (StringUtils.isBlank(genderCode)) {
      results.add(createValidationEntity(ERROR, GENDER_ERR, GENDER));
    } else {
      String finalGenderCode = genderCode.trim();
      long filteredCount = genderCodes.stream().filter(gender -> LocalDateTime.now().isAfter(gender.getEffectiveDate())
          && LocalDateTime.now().isBefore(gender.getExpiryDate())
          && finalGenderCode.equalsIgnoreCase(gender.getGenderCode())).count();
      if (filteredCount < 1) {
        results.add(createValidationEntity(ERROR, GENDER_ERR, GENDER));
      }
    }
    log.debug("transaction ID :: {} , returning results size :: {}", validationPayload.getTransactionID(), results.size());
    stopwatch.stop();
    log.info("Completed for {} in {} milli seconds",validationPayload.getTransactionID(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
    return results;
  }
}