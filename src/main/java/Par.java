import files.Asset;
import shared_utils.SharedC;
import shared_utils.SharedC.Par.Sections.Assets;
import utils.C;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Par {
    private final byte[] type;
    private final short version;
    private ArrayList<Byte> data = new ArrayList<>();
    private ArrayList<Section> sections = new ArrayList<>();

    public Par(byte[] type, short version) {
        this.type = type;
        this.version = version;
    }

    public byte[] generateBytesArray() {
        addBytesFromArrayToArrayList(type);
        byte[] versionByteArray = ByteBuffer.allocate(SharedC.Par.MainHeader.VERSION_SIZE).putShort(version).array();
        addBytesFromArrayToArrayList(versionByteArray);
        sectionsToOutput();
        data.add(C.NUMBER_OF_SECTIONS_POSITION, (byte) sections.size());
        return convertArrayListToByteArray();
    }

    private void sectionsToOutput() {
        for (Section section : sections) {
            addBytesFromArrayToArrayList(section.getId().name().getBytes());
            byte[] sectionSize = ByteBuffer.allocate(SharedC.Par.Sections.SECTION_SIZE).putShort((short) section.getSize()).array();
            addBytesFromArrayToArrayList(sectionSize);
            ArrayList<Asset> assets = section.getAssets();
            for (Asset asset : assets) {
                addAssetToOutput(asset);
            }
        }
    }

    private void addAssetToOutput(Asset asset) {
        addBytesFromArrayToArrayList(asset.getIdAsByteArray());
        byte[] data = asset.getData();
        addBytesFromArrayToArrayList(ByteBuffer.allocate(Assets.ASSET_DATA_SIZE).putInt(data.length).array());
        addBytesFromArrayToArrayList(data);
    }

    private void addBytesFromArrayToArrayList(byte[] array) {
        for (byte b : array) {
            data.add(b);
        }
    }

    private byte[] convertArrayListToByteArray() {
        byte[] output = new byte[data.size()];
        for (int i = 0; i < data.size(); i++) {
            output[i] = data.get(i);
        }
        return output;
    }

    public void addSection(Section section) {
        sections.add(section);
    }
}
