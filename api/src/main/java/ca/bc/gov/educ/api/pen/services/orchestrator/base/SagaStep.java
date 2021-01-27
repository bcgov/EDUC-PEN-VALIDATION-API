package ca.bc.gov.educ.api.pen.services.orchestrator.base;


import ca.bc.gov.educ.api.pen.services.model.Saga;
import ca.bc.gov.educ.api.pen.services.struct.v1.Event;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * The interface Saga step.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface SagaStep<T> {
  /**
   * Apply.
   *
   * @param event    the event
   * @param saga     the saga
   * @param sagaData the saga data
   * @throws InterruptedException the interrupted exception
   * @throws TimeoutException     the timeout exception
   * @throws IOException          the io exception
   */
  void apply(Event event, Saga saga, T sagaData) throws InterruptedException, TimeoutException, IOException;
}
