package top.speedcubing.lib.minecraft.packet;

import top.speedcubing.lib.utils.ByteArrayDataBuilder;

public class MinecraftPacket {
    private int length;
    private int packetID;
    private byte[] data;

    public MinecraftPacket(int packetID, byte[] data) {
        this.packetID = packetID;
        this.data = data;
        this.length = new ByteArrayDataBuilder().writeVarInt(packetID).write(data).toByteArray().length;
    }

    public MinecraftPacket(int length, int packetID, byte[] data) {
        this.packetID = packetID;
        this.data = data;
        this.length = length;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPacketID() {
        return packetID;
    }

    public void setPacketID(int packetID) {
        this.packetID = packetID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] toByteArray() {
        return new ByteArrayDataBuilder()
                .writeVarInt(length)
                .writeVarInt(packetID)
                .write(data)
                .toByteArray();
    }
}
