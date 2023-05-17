package cn.edu.hun.pisces.improved.others.basestruct.key;

import cn.edu.hun.pisces.utils.cryptography.Hash;

/**
 * @author: Leilei Du
 * @Date: 2018/7/15 13:20
 */
@Deprecated
public class ByteKey extends KeyAbstract<byte[]> {

    private static final int[] hashCodeParam = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311};
    private static final String PADDING = "ff";

    public ByteKey(byte[] value) {
        super(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ByteKey that = (ByteKey) obj;

        if (value == null) {
            return that.value == null;
        }

        if (that.value.length != this.value.length) {
            return false;
        }

        for (int i = 0; i < this.value.length; i++) {
            if (that.value[i] != this.value[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0, len_1 = this.value.length - 1;
        for (; i < len_1; ++i) {
            sb.append(this.value[i]).append(", ");
        }
        sb.append(this.value[i]).append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < this.value.length; i++) {
            result += this.value[i] * hashCodeParam[i%hashCodeParam.length];
        }
        return result;
    }

    @Override
    public String toHexByteString(int byteNum) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Hash.byte2Hex(this.value));
        if (this.value.length == byteNum) {
            return stringBuilder.toString();
        }
        if (this.value.length > byteNum) {
            stringBuilder.setLength(byteNum * 2);
            return stringBuilder.toString();
        }
        for (int i = this.value.length; i <= byteNum; i++) {
            stringBuilder.append(PADDING);
        }
        return stringBuilder.toString();
    }
}