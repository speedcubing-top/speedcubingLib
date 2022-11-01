package top.speedcubing.lib.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    public ByteArrayDataBuilder write(int... b) {
        try {
            for (int a : b)
                dataOutputStream.write(a);
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

    public ByteArrayDataBuilder write(byte[]... b) {
        try {
            for (byte[] a : b)
                dataOutputStream.write(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeInt(int... v) {
        try {
            for (int a : v)
                dataOutputStream.writeInt(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeUTF(String... str) {
        try {
            for (String a : str)
                dataOutputStream.writeUTF(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeChar(int... v) {
        try {
            for (int a : v)
                dataOutputStream.writeChar(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeByte(int... v) {
        try {
            for (int a : v)
                dataOutputStream.writeByte(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeBytes(String... s) {
        try {
            for (String a : s)
                dataOutputStream.writeBytes(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeChars(String... s) {
        try {
            for (String a : s)
                dataOutputStream.writeChars(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeLong(long... v) {
        try {
            for (long a : v)
                dataOutputStream.writeLong(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeFloat(float... v) {
        try {
            for (float a : v)
                dataOutputStream.writeFloat(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeDouble(double... v) {
        try {
            for (double a : v)
                dataOutputStream.writeDouble(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeShort(short... v) {
        try {
            for (short a : v)
                dataOutputStream.writeShort(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeBoolean(boolean... v) {
        try {
            for (boolean a : v)
                dataOutputStream.writeBoolean(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
