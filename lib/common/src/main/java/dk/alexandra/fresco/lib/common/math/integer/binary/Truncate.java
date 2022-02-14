package dk.alexandra.fresco.lib.common.math.integer.binary;

import dk.alexandra.fresco.framework.DRes;
import dk.alexandra.fresco.framework.builder.Computation;
import dk.alexandra.fresco.lib.common.compare.MiscBigIntegerGenerators;
import dk.alexandra.fresco.lib.common.math.AdvancedNumeric;
import dk.alexandra.fresco.lib.common.math.AdvancedNumeric.RandomAdditiveMask;
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric;
import dk.alexandra.fresco.framework.util.Pair;
import dk.alexandra.fresco.framework.value.SInt;
import java.math.BigInteger;

/**
 * Returns a number which is approximately the input shifted a number of positions to the right. The
 * result will be one larger than the exact result with some non-negligible probability. If you need
 * the exact result you need to use {@link RightShift} instead, but this will be at a significant
 * performance cost.
 *
 * The protocol is similar to protocol 3.1 in Catrina O., Saxena A. (2010) Secure Computation with
 * Fixed-Point Numbers. In: Sion R. (eds) Financial Cryptography and Data Security. FC 2010. Lecture
 * Notes in Computer Science, vol 6052. Springer, Berlin, Heidelberg.
 */
public class Truncate implements Computation<SInt, ProtocolBuilderNumeric> {

  // Input
  private final DRes<SInt> input;
  private final int shifts;

  public Truncate(DRes<SInt> input, int shifts) {
    this.input = input;
    this.shifts = shifts;
  }

  @Override
  public DRes<SInt> buildComputation(ProtocolBuilderNumeric sequential) {
    return sequential.seq((builder) -> {
      /*
       * Generate random additive mask of the same length as the input + some extra to avoid
       * leakage.
       */

      AdvancedNumeric additiveMaskBuilder = AdvancedNumeric.using(builder);
      DRes<RandomAdditiveMask> mask =
          additiveMaskBuilder.additiveMask(sequential.getBasicNumericContext().getMaxBitLength());
      return mask;
    }).seq((parSubSequential, randomAdditiveMask) -> {
      DRes<SInt> result = parSubSequential.numeric().add(input, randomAdditiveMask.value);
      DRes<BigInteger> open = parSubSequential.numeric().open(result);
      return () -> new Pair<>(open, randomAdditiveMask);
    }).seq((seq, maskedInput) -> {
      BigInteger masked = maskedInput.getFirst().out();
      RandomAdditiveMask mask = maskedInput.getSecond();

      /*
       * rBottom = r (mod 2^shifts).
       */
      AdvancedNumeric advancedNumeric = AdvancedNumeric.using(seq);
      MiscBigIntegerGenerators bigIntegerGenerators = new MiscBigIntegerGenerators(seq.getBasicNumericContext().getModulus());
      final DRes<SInt> rBottom = advancedNumeric.innerProductWithPublicPart(
          bigIntegerGenerators.getTwoPowersList(shifts), mask.bits);

      BigInteger inverse =
          BigInteger.ONE.shiftLeft(shifts).modInverse(seq.getBasicNumericContext().getModulus());
      DRes<SInt> rTop = seq.numeric().sub(mask.value, rBottom);

      /*
       * rTop is r with the last shifts bits set to zero, and it is hence divisible by 2^shifts, so
       * multiplying with the inverse in the field corresponds to shifting.
       */
      DRes<SInt> rShifted = seq.numeric().mult(inverse, rTop);
      BigInteger mShifted = masked.shiftRight(shifts);
      DRes<SInt> result = seq.numeric().sub(mShifted, rShifted);
      return result;
    });
  }
}
