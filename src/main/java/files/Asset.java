package files;

import shared_utils.SharedC;

public class Asset {
    protected final byte[] data;
    protected String id;

    public Asset(String id, byte[] bytes) {
        this.id = id;
        this.data = bytes;
    }

    public byte[] getIdAsByteArray() {
        byte[] bytes = id.getBytes();
        if (bytes.length == SharedC.Par.Sections.Assets.ASSET_ID_SIZE) return bytes;
        byte[] output = new byte[SharedC.Par.Sections.Assets.ASSET_ID_SIZE];
        int length = bytes.length;
        int begin = Math.max(0, SharedC.Par.Sections.Assets.ASSET_ID_SIZE - length);
        int end = Math.max(begin, SharedC.Par   .Sections.Assets.ASSET_ID_SIZE);
        for (int i = begin; i < end; i++) output[i] = bytes[i - begin];
        return output;
    }

    public byte[] getData() {
        return data;
    }
}
