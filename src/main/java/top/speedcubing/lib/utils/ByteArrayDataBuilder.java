package top.speedcubing.lib.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayDataBuilder {
    private final OutputStream outputStream;
    private final DataOutputStream dataOutputStream;

    public ByteArrayDataBuilder() {
        this(32);
    }

    public ByteArrayDataBuilder(int size) {
        this(new ByteArrayOutputStream(size));
    }

    public ByteArrayDataBuilder(OutputStream outputStream) {
        this.outputStream = outputStream;
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public byte[] toByteArray() {
        if (outputStream instanceof ByteArrayOutputStream)
            return ((ByteArrayOutputStream) outputStream).toByteArray();
        throw new IllegalStateException();
    }

    //default
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

    //self implementation
    public ByteArrayDataBuilder writeVarInt(int i) {
        try {
            IOUtils.writeVarInt(dataOutputStream, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayDataBuilder writeString(String s) {
        writeVarInt(s.length());
        write(s.getBytes());
        return this;
    }
}
