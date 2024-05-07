package top.speedcubing.lib.utils.bytes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArayBuffer {
    private final OutputStream outputStream;
    private final DataOutputStream dataOutputStream;

    public ByteArayBuffer() {
        this(32);
    }

    public ByteArayBuffer(int size) {
        this(new ByteArrayOutputStream(size));
    }

    public ByteArayBuffer(OutputStream outputStream) {
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
    public ByteArayBuffer write(int b) {
        try {
            dataOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer write(byte[] b, int off, int len) {
        try {
            dataOutputStream.write(b, off, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer write(byte[] b) {
        try {
            dataOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeInt(int v) {
        try {
            dataOutputStream.writeInt(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeUTF(String str) {
        try {
            dataOutputStream.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeChar(int v) {
        try {
            dataOutputStream.writeChar(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeByte(int v) {
        try {
            dataOutputStream.writeByte(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeBytes(String s) {
        try {
            dataOutputStream.writeBytes(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeChars(String s) {
        try {
            dataOutputStream.writeChars(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeLong(long v) {
        try {
            dataOutputStream.writeLong(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeFloat(float v) {
        try {
            dataOutputStream.writeFloat(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeDouble(double v) {
        try {
            dataOutputStream.writeDouble(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeShort(short v) {
        try {
            dataOutputStream.writeShort(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeBoolean(boolean v) {
        try {
            dataOutputStream.writeBoolean(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    //self implementation
    public ByteArayBuffer writeVarInt(int i) {
        try {
            IOUtils.writeVarInt(dataOutputStream, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArayBuffer writeString(String s) {
        writeVarInt(s.length());
        write(s.getBytes());
        return this;
    }
}
