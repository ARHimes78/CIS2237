//Alan Himes
//ahimes1@cnm.edu
//SaveFragment.java

package himesp6.com.cis2237.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends DialogFragment {
    private EditText etxtFilename;

    public SaveFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().
                inflate(R.layout.fragment_save, null);

        etxtFilename = (EditText)view.findViewById(R.id.etxtFilename);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.title_save);
        builder.setMessage(R.string.message_save_filename);

        builder.setPositiveButton(R.string.button_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DoodleView doodleView = getDoodleFragment().getDoodleView();
                String filename = etxtFilename.getText().toString() + ".jpg";

                if (filename.equals(".jpg"))
                    filename = "Doodlz" + System.currentTimeMillis() + ".jpg";

                doodleView.setFileName(filename);
                doodleView.saveImage();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        DoodleFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DoodleFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }

    private DoodleFragment getDoodleFragment()
    {
        return (DoodleFragment)getFragmentManager().findFragmentById(R.id.doodleFragment);
    }
}
