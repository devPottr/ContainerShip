import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface RandomNumberGenerator {
    public boolean stateEquals(Object object);
    public void readState(DataInputStream stream) throws IOException;
    public void writeState(DataOutputStream stream) throws IOException;
    public void setSeed(long seed);
    public void setSeed(int[] array);
    public int nextInt();
    public short nextShort();
    public char nextChar();
    public boolean nextBoolean();
    public boolean nextBoolean(float probability);
    public boolean nextBoolean(double probability);
    public byte nextByte();
    public void nextBytes(byte[] bytes);
    public long nextLong();
    public long nextLong(long n);
    public double nextDouble();
    public double nextDouble(boolean includeZero, boolean includeOne);
    public void clearGaussian();
    public double nextGaussian();
    public float nextFloat();
    public float nextFloat(boolean includeZero, boolean includeOne);
    public int nextInt(int n);
}
