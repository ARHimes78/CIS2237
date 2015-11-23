//Alan Himes
//ahimes1@cnm.edu
//LineWidthDialogFragment.java

package himesp6.com.cis2237.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class LineWidthDialogFragment extends DialogFragment {
    private ImageView widthImageView;

    public LineWidthDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View lineWidthDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_line_width_dialog, null);
        builder.setView(lineWidthDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setTitle(R.string.title_line_width_dialog);
        builder.setCancelable(true);

        // get the ImageView
        widthImageView = (ImageView)lineWidthDialogView.findViewById(R.id.imageViewLine);

        // configure widthSeekBar
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        final SeekBar widthSeekBar = (SeekBar)
                lineWidthDialogView.findViewById(R.id.seekLine);
        widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged);
        widthSeekBar.setProgress(doodleView.getLineWidth());

        // add Set Line Width Button
        builder.setPositiveButton(R.string.button_set_line_width,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        doodleView.setLineWidth(widthSeekBar.getProgress());
                    }
                }
        ); // end call to setPositiveButton

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

    private SeekBar.OnSeekBarChangeListener lineWidthChanged = new SeekBar.OnSeekBarChangeListener() {
        Bitmap bitmap = Bitmap.createBitmap(
                500, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap); // associate with Canvas

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // configure a Paint object for the current SeekBar value
            Paint p = new Paint();
            p.setColor(
                    getDoodleFragment().getDoodleView().getDrawingColor());
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(progress);

            // erase the bitmap and redraw the line
            bitmap.eraseColor(getResources().
                    getColor(android.R.color.transparent));
            canvas.drawLine(80, 50, 420, 50, p);
            widthImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
