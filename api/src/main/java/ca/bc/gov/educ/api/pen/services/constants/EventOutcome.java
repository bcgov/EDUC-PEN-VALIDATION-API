package ca.bc.gov.educ.api.pen.services.constants;

/**
 * The enum Event outcome.
 */
public enum EventOutcome {
  /**
   * Read from topic success event outcome.
   */
  VALIDATION_SUCCESS_NO_ERROR_WARNING,
  /**
   * Validation success with error event outcome.
   */
  VALIDATION_SUCCESS_WITH_ERROR,
  /**
   * Validation success with only warning event outcome.
   */
  VALIDATION_SUCCESS_WITH_ONLY_WARNING
}