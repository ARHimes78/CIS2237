//Alan Himes
//ahimes1@cnm.edu
//EraseImageDialogFragment.java

package himesp6.com.cis2237.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EraseImageDialogFragment extends DialogFragment {


    public EraseImageDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set the AlertDialog's message
        builder.setMessage(R.string.message_erase);
        builder.setCancelable(false);

        // add Erase Button
        builder.setPositiveButton(R.string.button_erase,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getDoodleFragment().getDoodleView().clear(); // clear image
                    }
                }
        ); // end call to setPositiveButton

        // add Cancel Button
        builder.setNegativeButton(R.string.button_cancel, null);

        return builder.create(); // return dialog
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
