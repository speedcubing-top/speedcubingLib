package top.speedcubing.lib.utils;

import java.io.*;
import java.util.Arrays;

// new ByteArrayDataBuilder().write...write...write....toByteArray();
public class ByteArrayDataBuilder {
    public final ByteArrayOutputStream byteArrayOutputSteam;
    public final DataOutputStream dataOutputStream;

    public ByteArrayDataBuilder() {
        byteArrayOutputSteam = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream(byteArrayOutputSteam);
    }

    public byte[] toByteArray() {
        return byteArrayOutputSteam.toByteArray();
    }

    public ByteArrayDataBuilder write(int b) {
        try {
            dataOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder write(byte[] b, int off, int len) {
        try {
            dataOutputStream.write(b, off, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder write(byte[] b) {
        try {
            dataOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeInt(int v) {
        try {
            dataOutputStream.writeInt(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeUTF(String str) {
        try {
            dataOutputStream.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeChar(int v) {
        try {
            dataOutputStream.writeChar(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeByte(int v) {
        try {
            dataOutputStream.writeByte(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeBytes(String s) {
        try {
            dataOutputStream.writeBytes(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeChars(String s) {
        try {
            dataOutputStream.writeChars(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeLong(long v) {
        try {
            dataOutputStream.writeLong(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeFloat(float v) {
        try {
            dataOutputStream.writeFloat(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeDouble(double v) {
        try {
            dataOutputStream.writeDouble(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeShort(short v) {
        try {
            dataOutputStream.writeShort(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeBoolean(boolean v) {
        try {
            dataOutputStream.writeBoolean(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    //?
    public ByteArrayDataBuilder writeVarInt(int i) {
        try {
            int part;
            do {
                part = i & 0x7F;
                i >>>= 7;
                if (i != 0)
                    part |= 0x80;
                dataOutputStream.writeByte(part);
            } while (i != 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
