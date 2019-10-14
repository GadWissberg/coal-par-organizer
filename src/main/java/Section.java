import files.Asset;
import shared_utils.SharedC;

import java.util.ArrayList;

public class Section {
    private final SharedC.Par.Sections.SectionType id;
    private ArrayList<Asset> assets = new ArrayList<>();

    public Section(SharedC.Par.Sections.SectionType identifier) {
        this.id = identifier;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public SharedC.Par.Sections.SectionType getId() {
        return id;
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public int getSize() {
        return assets.size();
    }
}
