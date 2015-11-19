package himesp6.com.cis2237.doodlz;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoodleFragment extends DialogFragment {
    private DoodleView doodleView;

    //These variables are used to calculate changes in the acceleration to
    //determine whether a shake event has taken place.
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;

    //Use this to prevent multiple dialogs on screen at once.  For example,
    //if the ChooseColor dialog is displayed and the user accidentally shakes the device,
    //the dialog for erasing the image should not be displayed.
    private boolean dialogOnScreen = false;

    //Value used to determine whether the user shook the device to erase
    private static final int ACCELERATION_THRESHOLD = 100000;

    public DoodleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doodle, container, false);
        setHasOptionsMenu(true);

        doodleView = (DoodleView)view.findViewById(R.id.doodleView);

        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        enableAccelerometerListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableAccelerometerListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.doodle_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.color:
                ColorDialogFragment colorDialog = new ColorDialogFragment();
                colorDialog.show(getFragmentManager(), "color dialog");
                return true; // consume the menu event
            case R.id.lineWidth:
                LineWidthDialogFragment widthDialog = new LineWidthDialogFragment();
                widthDialog.show(getFragmentManager(), "line width dialog");
                return true; // consume the menu event
            case R.id.eraser:
                doodleView.setDrawingColor(Color.WHITE); // line color white
                return true; // consume the menu event
            case R.id.clear:
                confirmErase(); // confirm before erasing image
                return true; // consume the menu event
            case R.id.save:
                doodleView.saveImage(); // save the current image
                return true; // consume the menu event
        } // end switch

        return super.onOptionsItemSelected(item);
    }

    private void enableAccelerometerListening() {
        //get the Sensormanager
        SensorManager sensorManager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);

        //register to listen for accelerometer events
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void disableAccelerometerListening() {
        // get the SensorManager
        SensorManager sensorManager = (SensorManager)
                getActivity().getSystemService(Context.SENSOR_SERVICE);

        // stop listening for accelerometer events
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (!dialogOnScreen) {
                // get x, y, and z values for the SensorEvent
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // save previous acceleration value
                lastAcceleration = currentAcceleration;

                // calculate the current acceleration
                currentAcceleration = x * x + y * y + z * z;

                // calculate the change in acceleration
                acceleration = currentAcceleration *
                        (currentAcceleration - lastAcceleration);

                // if the acceleration is above a certain threshold
                if (acceleration > ACCELERATION_THRESHOLD)
                    confirmErase();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void confirmErase() {
        EraseImageDialogFragment fragment = new EraseImageDialogFragment();
        fragment.show(getFragmentManager(), "erase dialog");
    }

    public DoodleView getDoodleView() {
        return doodleView;
    }

    public void setDialogOnScreen(boolean dialogOnScreen) {
        this.dialogOnScreen = dialogOnScreen;
    }
}
