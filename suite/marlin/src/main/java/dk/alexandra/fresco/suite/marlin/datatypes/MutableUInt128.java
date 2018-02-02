package dk.alexandra.fresco.suite.marlin.datatypes;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Unsigned 128-bit integer with support for in-place operations.
 */
public class MutableUInt128 implements BigUInt<MutableUInt128> {

  private long high;
  private int mid;
  private int low;

  /**
   * Creates new {@link MutableUInt128}.
   *
   * @param bytes bytes interpreted in big-endian order.
   */
  public MutableUInt128(byte[] bytes) {
    byte[] padded = pad(bytes);
    ByteBuffer buffer = ByteBuffer.wrap(padded);
    buffer.order(ByteOrder.BIG_ENDIAN);
    this.high = buffer.getLong();
    this.mid = buffer.getInt();
    this.low = buffer.getInt();
  }

  public MutableUInt128(BigInteger value) {
    this(value.toByteArray());
  }

  public MutableUInt128(long value) {
    this.high = 0;
    this.mid = (int) (value >>> 32);
    this.low = (int) value;
  }

  public MutableUInt128(MutableUInt128 other) {
    this.high = other.high;
    this.mid = other.mid;
    this.low = other.low;
  }

  public void addInPlace(MutableUInt128 other) {
    long newLow = Integer.toUnsignedLong(this.low) + Integer.toUnsignedLong(other.low);
    long lowOverflow = newLow >>> 32;
    long newMid = Integer.toUnsignedLong(this.mid)
        + Integer.toUnsignedLong(other.mid)
        + lowOverflow;
    long midOverflow = newMid >>> 32;
    long newHigh = this.high + other.high + midOverflow;
    this.low = (int) newLow;
    this.mid = (int) newMid;
    this.high = newHigh;
  }

  public MutableUInt128 add(MutableUInt128 other) {
    MutableUInt128 clone = new MutableUInt128(this);
    clone.addInPlace(other);
    return clone;
  }

  public void multiplyInPlace(MutableUInt128 other) {
    long thisLowAsLong = Integer.toUnsignedLong(this.low);
    long thisMidAsLong = Integer.toUnsignedLong(this.mid);
    long otherLowAsLong = Integer.toUnsignedLong(other.low);
    long otherMidAsLong = Integer.toUnsignedLong(other.mid);

    // low
    long t1 = thisLowAsLong * otherLowAsLong;
    long t2 = thisLowAsLong * otherMidAsLong;
    long t3 = thisLowAsLong * other.high;

    // mid
    long t4 = thisMidAsLong * otherLowAsLong;
    long t5 = thisMidAsLong * otherMidAsLong;
    int t6 = (int) (this.mid * other.high);

    // high
    long t7 = this.high * otherLowAsLong;
    int t8 = (int) (this.high * other.mid);
    // we don't need the product of this.high and other.high since those overflow 2^128

    long m1 = (t1 >>> 32) + (t2 & 0xffffffffL);
    int m2 = (int) m1;
    long newMid = Integer.toUnsignedLong(m2) + (t4 & 0xffffffffL);

    long newHigh = (t2 >>> 32)
        + t3
        + (t4 >>> 32)
        + t5
        + (Integer.toUnsignedLong(t6) << 32)
        + t7
        + (Integer.toUnsignedLong(t8) << 32)
        + (m1 >>> 32)
        + (newMid >>> 32);

    this.low = (int) t1;
    this.mid = (int) newMid;
    this.high = newHigh;
  }

  public MutableUInt128 multiply(MutableUInt128 other) {
    MutableUInt128 clone = new MutableUInt128(this);
    clone.multiplyInPlace(other);
    return clone;
  }

  public BigInteger toBigInt() {
    BigInteger low = new BigInteger(Integer.toUnsignedString(this.low));
    BigInteger mid = new BigInteger(Integer.toUnsignedString(this.mid)).shiftLeft(32);
    BigInteger high = new BigInteger(Long.toUnsignedString(this.high)).shiftLeft(64);
    return low.add(mid).add(high);
  }

  private byte[] pad(byte[] bytes) {
    byte[] padded = new byte[getBitLength() / 8];
    // potentially drop byte containing sign bit
    boolean dropSignBitByte = (bytes[0] == 0x00);
    int bytesLen = dropSignBitByte ? bytes.length - 1 : bytes.length;
    if (bytesLen > padded.length) {
      throw new IllegalArgumentException("Exceeds capacity");
    }
    int srcPos = dropSignBitByte ? 1 : 0;
    System.arraycopy(bytes, srcPos, padded, padded.length - bytesLen, bytesLen);
    return padded;
  }

  @Override
  public String toString() {
    return toBigInt().toString();
  }

  @Override
  public int getBitLength() {
    return 128;
  }

  @Override
  public byte[] toByteArray() {
    ByteBuffer buffer = ByteBuffer.allocate(getBitLength() / 8);
    buffer.order(ByteOrder.BIG_ENDIAN);
    buffer.putLong(high);
    buffer.putInt(mid);
    buffer.putInt(low);
    buffer.flip();
    return buffer.array();
  }

}
