
import files.Asset;
import shared_utils.SharedC;
import shared_utils.SharedC.Par.Sections.Assets;
import utils.C;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        FileOutputStream fos;
        try {
            byte[] raw = generateData();
            fos = new FileOutputStream(new File(C.OUTPUT_FILE_PATH));
            fos.write(raw, 0, raw.length);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] generateData() throws IOException {
        Par par = new Par(SharedC.Par.MainHeader.TYPE_MPAR, SharedC.Par.MainHeader.VERSION_VALUE);
        Section imagesSection = new Section(SharedC.Par.Sections.SectionType.IMG);
        Section txtSection = new Section(SharedC.Par.Sections.SectionType.TXT);
        Section mapsSection = new Section(SharedC.Par.Sections.SectionType.MAP);
        addSections(par, imagesSection, txtSection, mapsSection);
        addAllFromResDirectory(imagesSection, txtSection, mapsSection);
        byte[] raw = par.generateBytesArray();
        return raw;
    }

    private static void addAllFromResDirectory(Section imagesSection, Section txtSection, Section mapsSection)
            throws IOException {
        addAllFromDirectory(imagesSection, C.Folders.IMAGES);
        addAllFromDirectory(txtSection, C.Folders.TEXT);
        addAllFromDirectory(mapsSection, C.Folders.MAPS);
    }

    private static void addSections(Par par, Section imagesSection, Section txtSection, Section mapsSection) {
        par.addSection(imagesSection);
        par.addSection(txtSection);
        par.addSection(mapsSection);
    }

    private static void addAllFromDirectory(Section section, String folderName) throws IOException {
        File folder = new File(C.Folders.RESOURCES + File.separator + folderName);
        Asset asset;
        for (final File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf('.'));
            if (name.length() > Assets.ASSET_ID_SIZE) name = name.substring(0, Assets.ASSET_ID_SIZE);
            asset = new Asset(name, Files.readAllBytes(fileEntry.toPath()));
            section.addAsset(asset);
        }
    }

}
