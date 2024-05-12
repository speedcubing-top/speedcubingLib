package top.speedcubing.lib.minecraft.packet;

import top.speedcubing.lib.utils.bytes.ByteArrayBuffer;

public class HandshakePacket {

    private int protocolVersion;
    private String serverAddress;
    private int serverPort;
    private int nextState;

    public HandshakePacket(int protocolVersion, String serverAddress, int serverPort, int nextState) {
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.nextState = nextState;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getNextState() {
        return nextState;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setNextState(int nextState) {
        this.nextState = nextState;
    }

    public byte[] toByteArray() {
        return new ByteArrayBuffer()
                .writeVarInt(protocolVersion)
                .writeString(serverAddress)
                .writeShort((short) serverPort)
                .writeVarInt(nextState).toByteArray();
    }

    @Override
    public String toString() {
        return "HandshakePacket[" + protocolVersion + "," + serverAddress + "," + serverPort + "," + nextState + "]";
    }
}
