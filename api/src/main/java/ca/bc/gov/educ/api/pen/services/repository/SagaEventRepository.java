package ca.bc.gov.educ.api.pen.services.repository;

import ca.bc.gov.educ.api.pen.services.model.Saga;
import ca.bc.gov.educ.api.pen.services.model.SagaEventStates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Saga event repository.
 */
@Repository
public interface SagaEventRepository extends CrudRepository<SagaEventStates, UUID> {
  /**
   * Find by saga list.
   *
   * @param saga the saga
   * @return the list
   */
  List<SagaEventStates> findBySaga(Saga saga);

  /**
   * Find by saga and saga event outcome and saga event state and saga step number optional.
   *
   * @param saga         the saga
   * @param eventOutcome the event outcome
   * @param eventState   the event state
   * @param stepNumber   the step number
   * @return the optional
   */
  Optional<SagaEventStates> findBySagaAndSagaEventOutcomeAndSagaEventStateAndSagaStepNumber(Saga saga, String eventOutcome, String eventState, int stepNumber);
}
