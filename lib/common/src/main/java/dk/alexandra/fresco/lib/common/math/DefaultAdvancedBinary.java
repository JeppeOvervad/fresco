package dk.alexandra.fresco.lib.common.math;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.builder.binary.ProtocolBuilderBinary;
import dk.alexandra.fresco.framework.util.Pair;
import dk.alexandra.fresco.framework.value.SBool;
import dk.alexandra.fresco.lib.common.collections.sort.KeyedCompareAndSwap;
import dk.alexandra.fresco.lib.common.math.bool.add.BitIncrementer;
import dk.alexandra.fresco.lib.common.math.bool.add.FullAdder;
import dk.alexandra.fresco.lib.common.math.bool.add.OneBitFullAdder;
import dk.alexandra.fresco.lib.common.math.bool.add.OneBitHalfAdder;
import dk.alexandra.fresco.lib.common.math.bool.log.Logarithm;
import dk.alexandra.fresco.lib.common.math.bool.mult.BinaryMultiplication;
import dk.alexandra.fresco.lib.common.math.field.bool.ConditionalSelect;
import dk.alexandra.fresco.lib.common.math.field.bool.generic.AndFromPublicValue;
import dk.alexandra.fresco.lib.common.math.field.bool.generic.NandFromAndAndNot;
import dk.alexandra.fresco.lib.common.math.field.bool.generic.OrFromPublicValue;
import dk.alexandra.fresco.lib.common.math.field.bool.generic.OrFromXorAnd;
import dk.alexandra.fresco.lib.common.math.field.bool.generic.XnorFromXorAndNot;
import java.util.List;

public class DefaultAdvancedBinary implements AdvancedBinary {

  private final ProtocolBuilderBinary builder;

  DefaultAdvancedBinary(ProtocolBuilderBinary builder) {
    super();
    this.builder = builder;
  }

  @Override
  public DRes<SBool> or(DRes<SBool> left, DRes<SBool> right) {
    return builder.seq(new OrFromXorAnd(left, right));
  }

  @Override
  public DRes<SBool> or(DRes<SBool> left, boolean right) {
    return builder.seq(new OrFromPublicValue(left, right));
  }

  @Override
  public DRes<SBool> xnor(DRes<SBool> left, DRes<SBool> right) {
    return builder.seq(new XnorFromXorAndNot(left, right));
  }

  @Override
  public DRes<SBool> xnor(DRes<SBool> left, boolean right) {
    if (right) {
      return left;
    } else {
      return builder.binary().not(left);
    }
  }

  @Override
  public DRes<SBool> nand(DRes<SBool> left, DRes<SBool> right) {
    return builder.seq(new NandFromAndAndNot(left, right));
  }

  @Override
  public DRes<SBool> nand(DRes<SBool> left, boolean right) {
    if (right) {
      return builder.binary().not(left);
    } else {
      return builder.binary().known(true);
    }
  }

  @Override
  public DRes<Pair<SBool, SBool>> oneBitFullAdder(DRes<SBool> left,
      DRes<SBool> right, DRes<SBool> carry) {
    return builder.seq(new OneBitFullAdder(left, right, carry));
  }

  @Override
  public DRes<List<DRes<SBool>>> fullAdder(List<DRes<SBool>> lefts,
      List<DRes<SBool>> rights, DRes<SBool> inCarry) {
    return builder.seq(new FullAdder(lefts, rights, inCarry));
  }

  @Override
  public DRes<List<DRes<SBool>>> bitIncrement(List<DRes<SBool>> base,
      DRes<SBool> increment) {
    return builder.seq(new BitIncrementer(base, increment));
  }

  @Override
  public DRes<SBool> and(DRes<SBool> left, boolean right) {
    return builder.seq(new AndFromPublicValue(left, right));
  }

  @Override
  public DRes<SBool> condSelect(DRes<SBool> condition, DRes<SBool> left,
      DRes<SBool> right) {
    return builder.seq(new ConditionalSelect(condition, left, right));
  }

  @Override
  public DRes<Pair<SBool, SBool>> oneBitHalfAdder(DRes<SBool> left,
      DRes<SBool> right) {
    return builder.seq(new OneBitHalfAdder(left, right));
  }



  @Override
  public DRes<List<DRes<SBool>>> binaryMult(List<DRes<SBool>> lefts,
      List<DRes<SBool>> rights) {
    return builder.seq(new BinaryMultiplication(lefts, rights));
  }

  @Override
  public DRes<List<DRes<SBool>>> logProtocol(List<DRes<SBool>> number) {
    return builder.seq(new Logarithm(number));
  }

  @Override
  public DRes<List<Pair<List<DRes<SBool>>, List<DRes<SBool>>>>> keyedCompareAndSwap(
      Pair<List<DRes<SBool>>, List<DRes<SBool>>> leftKeyAndValue,
      Pair<List<DRes<SBool>>, List<DRes<SBool>>> rightKeyAndValue) {
    return builder.seq(KeyedCompareAndSwap.binary(leftKeyAndValue, rightKeyAndValue));
  }

}
