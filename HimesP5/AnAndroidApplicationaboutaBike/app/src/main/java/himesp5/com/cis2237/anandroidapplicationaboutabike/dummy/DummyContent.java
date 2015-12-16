//Alan Himes
//ahimes1@cnm.edu
//DummyContent.java

package himesp5.com.cis2237.anandroidapplicationaboutabike.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "Photo"));
        addItem(new DummyItem("2", "Info"));
        addItem(new DummyItem("3", "Website",
                "http://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Daps&field-keywords=bicycle"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;
        public String item_name;
        public String item_url;

        public DummyItem(String id, String content) {
            this.id = id;
            this.item_name = content;
            this.content = content;
        }

        public DummyItem(String id, String item_name, String item_url) {
            this(id, item_name);
            this.item_url = item_url;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
