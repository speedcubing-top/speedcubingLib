package top.speedcubing.lib.utils.bytes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import top.speedcubing.lib.utils.Preconditions;

public class ByteArrayBuffer {
    private final OutputStream outputStream;
    private final DataOutputStream dataOutputStream;

    public ByteArrayBuffer() {
        this(32);
    }

    public ByteArrayBuffer(int size) {
        this(new ByteArrayOutputStream(size));
    }

    public ByteArrayBuffer(OutputStream outputStream) {
        this.outputStream = outputStream;
        dataOutputStream = new DataOutputStream(outputStream);
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public byte[] toByteArray() {
        Preconditions.checkArgument(outputStream instanceof ByteArrayOutputStream);

        return ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    //default
    public ByteArrayBuffer write(int b) {
        try {
            dataOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer write(byte[] b, int off, int len) {
        try {
            dataOutputStream.write(b, off, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer write(byte[] b) {
        try {
            dataOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeInt(int v) {
        try {
            dataOutputStream.writeInt(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeUTF(String str) {
        try {
            dataOutputStream.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeChar(int v) {
        try {
            dataOutputStream.writeChar(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeByte(int v) {
        try {
            dataOutputStream.writeByte(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeBytes(String s) {
        try {
            dataOutputStream.writeBytes(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeChars(String s) {
        try {
            dataOutputStream.writeChars(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeLong(long v) {
        try {
            dataOutputStream.writeLong(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeFloat(float v) {
        try {
            dataOutputStream.writeFloat(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeDouble(double v) {
        try {
            dataOutputStream.writeDouble(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeShort(short v) {
        try {
            dataOutputStream.writeShort(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeBoolean(boolean v) {
        try {
            dataOutputStream.writeBoolean(v);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    //self implementation
    public ByteArrayBuffer writeVarInt(int i) {
        try {
            IOUtils.writeVarInt(dataOutputStream, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ByteArrayBuffer writeString(String s) {
        writeVarInt(s.length());
        write(s.getBytes());
        return this;
    }
}
