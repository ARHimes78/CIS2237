//Alan Himes
//ahimes1@cnm.edu
//BikePhotoItemDetailFragment.java

package himesp5.com.cis2237.anandroidapplicationaboutabike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import himesp5.com.cis2237.anandroidapplicationaboutabike.dummy.DummyContent;

/**
 * A fragment representing a single BikePhotoItem detail screen.
 * This fragment is either contained in a {@link BikePhotoItemListActivity}
 * in two-pane mode (on tablets) or a {@link BikePhotoItemDetailActivity}
 * on handsets.
 */
public class BikePhotoItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BikePhotoItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        TextView txtInfo;

        // Show the dummy content as text in a TextView.
        if (mItem.id.equals("1")) {
            rootView = inflater.inflate(R.layout.photo, container, false);
        }
        if (mItem.id.equals("2")) {
            rootView = inflater.inflate(R.layout.info, container, false);
            txtInfo = (TextView)rootView.findViewById(R.id.txtInfo);

            try {
                txtInfo.setText(getBikeInfo(getResources().openRawResource(R.raw.bike_info)));
            } catch (IOException IOe) {
                txtInfo.setText("Info not available.");
            }
        }
        if (mItem.id.equals("3")) {
            rootView = inflater.inflate(R.layout.fragment_bikephotoitem_detail, container, false);
            ((WebView) rootView.findViewById(R.id.bikephotoitem_detail)).loadUrl(mItem.item_url);
        }

        return rootView;
    }

    //
    private String getBikeInfo(InputStream iStr) throws IOException {
        StringBuilder sb = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(iStr);
        BufferedReader br = new BufferedReader(isr);

        String line = null;

        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }

        return sb.toString();
    }
}
