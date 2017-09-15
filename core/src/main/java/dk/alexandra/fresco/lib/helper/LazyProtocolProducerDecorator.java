package dk.alexandra.fresco.lib.helper;

import dk.alexandra.fresco.framework.ProtocolCollection;
import dk.alexandra.fresco.framework.ProtocolProducer;
import java.util.function.Supplier;

/**
 * Creates a lazy protocol builder that wait until called upon before creating
 * it's own inner producer. It is decorating the original, inner producer by
 * guarding the creation based on the Supplier.
 */
public class LazyProtocolProducerDecorator implements ProtocolProducer {

  private ProtocolProducer innerProtocolProducer;
  private Supplier<ProtocolProducer> child;

  public LazyProtocolProducerDecorator(Supplier<ProtocolProducer> supplier) {
    this.child = supplier;
  }

  @Override
  public void getNextProtocols(
      @SuppressWarnings("rawtypes") ProtocolCollection protocolCollection) {
    getInnerProtocolProducer().getNextProtocols(protocolCollection);
  }

  @Override
  public boolean hasNextProtocols() {
    return getInnerProtocolProducer().hasNextProtocols();
  }

  ProtocolProducer getInnerProtocolProducer() {
    if (innerProtocolProducer == null) {
      innerProtocolProducer = child.get();
      child = null;
    }
    return innerProtocolProducer;
  }
}
