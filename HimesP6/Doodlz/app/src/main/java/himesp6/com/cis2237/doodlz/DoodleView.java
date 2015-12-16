//Alan Himes
//ahimes1@cnm.edu
//DoodleView.java

package himesp6.com.cis2237.doodlz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class DoodleView extends View {
    // used to determine whether user moved a finger enough to draw again
    private static final float TOUCH_TOLERANCE = 10;
    private Bitmap bitmap; // drawing area for display or saving
    private Canvas bitmapCanvas; // used to draw on bitmap
    private final Paint paintScreen; // used to draw bitmap onto screen
    private final Paint paintLine; // used to draw lines onto bitmap

    // Maps of current Paths being drawn and Points in those Paths
    //pathMap maps each finger ID to a corresponding Path object for the lines currently being drawn.
    private final Map<Integer, Path> pathMap = new HashMap<Integer, Path>();

    //the previousPointMap maintains the last point for each finger.
    //As each finger moves, we draw a line from its previous point to its current point.
    private final Map<Integer, Point> previousPointMap = new HashMap<Integer, Point>();

    // used to hide/show system bars
    private GestureDetector singleTapDetector;

    private Path path;
    private Point point;

    private String fileName;

    public DoodleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setStrokeWidth(20);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        singleTapDetector = new GestureDetector(singleTapListener);
        bitmapCanvas = new Canvas();

        //onSizeChanged call from first hideSystemBars() erases the picture.-ARH
        hideSystemBars();
        showSystemBars();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);

        bitmapCanvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE); // erase the Bitmap with white
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the background screen
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);

        // for each path currently being drawn
        for (Integer key : pathMap.keySet()) {
            canvas.drawPath(pathMap.get(key), paintLine); // draw line
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // if a single tap event occurred on KitKat or higher device
        if (singleTapDetector.onTouchEvent(event))
            return true;

        // get the event type and the ID of the pointer that caused the event
        int action = event.getActionMasked(); // event type
        int actionIndex = event.getActionIndex(); // pointer (i.e., finger)

        // determine whether touch started, ended or is moving
        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN)
        {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex),
                    event.getPointerId(actionIndex));
        }
        else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP)
        {
            touchEnded(event.getPointerId(actionIndex));
        }
        else
        {
            touchMoved(event);
        }

        invalidate(); // redraw
        return true;
    }

    // clear the painting
    public void clear()
    {
        pathMap.clear(); // remove all paths
        previousPointMap.clear(); // remove all previous points
        bitmap.eraseColor(Color.WHITE); // clear the bitmap
        invalidate(); // refresh the screen
    }

    public void setDrawingColor(int color) {
        paintLine.setColor(color);
    }

    public int getDrawingColor() {
        return paintLine.getColor();
    }

    public void setLineWidth(int width) {
        paintLine.setStrokeWidth(width);
    }

    public int getLineWidth() {
        return (int)paintLine.getStrokeWidth();
    }

    public void setFileName(String fn) {
        fileName = fn;
    }

    public String getFileName() {
        return fileName;
    }

    // hide system bars and action bar
    public void hideSystemBars()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // show system bars and action bar
    public void showSystemBars()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private GestureDetector.SimpleOnGestureListener singleTapListener =
            new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if ((getSystemUiVisibility() & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0)
                hideSystemBars();
            else
                showSystemBars();
            return true;
        }
    };

    // called when the user touches the screen
    private void touchStarted(float x, float y, int lineID)
    {
//        Path path; // used to store the path for the given touch id
//        Point point; // used to store the last point in path

        // if there is already a path for lineID
        if (pathMap.containsKey(lineID))
        {
            path = pathMap.get(lineID); // get the Path
            path.reset(); // reset the Path because a new touch has started
            point = previousPointMap.get(lineID); // get Path's last point
        }
        else
        {
            path = new Path();
            pathMap.put(lineID, path); // add the Path to Map
            point = new Point(); // create a new Point
            previousPointMap.put(lineID, point); // add the Point to the Map
        }

        // move to the coordinates of the touch
        path.moveTo(x, y);
        point.x = (int) x;
        point.y = (int) y;

        Log.d("touchStarted", point.x+" "+point.y);
    }

    // called when the user drags along the screen
    private void touchMoved(MotionEvent event)
    {
        // for each of the pointers in the given MotionEvent
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            // get the pointer ID and pointer index
            int pointerID = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerID);

            // if there is a path associated with the pointer
            if (pathMap.containsKey(pointerID))
            {
                // get the new coordinates for the pointer
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                // get the Path and previous Point associated with
                // this pointer
//                Path path = pathMap.get(pointerID);
//                Point point = previousPointMap.get(pointerID);

                path = pathMap.get(pointerID);
                point = previousPointMap.get(pointerID);

                // calculate how far the user moved from the last update
                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                // if the distance is significant enough to matter
                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE)
                {
                    // move the path to the new location
                    path.quadTo(point.x, point.y, (newX + point.x) / 2,
                            (newY + point.y) / 2);

                    //These solved my problem. -ARH
                    path.moveTo(point.x, point.y);
                    bitmapCanvas.drawLine(point.x, point.y, newX, newY, paintLine);

                    // store the new coordinates
                    point.x = (int) newX;
                    point.y = (int) newY;
                }
            }
        }

    }

    // called when the user finishes a touch
    private void touchEnded(int lineID)
    {
        path = pathMap.get(lineID); // get the corresponding Path
        bitmapCanvas.drawPath(path, paintLine); // draw to bitmapCanvas
        path.reset(); // reset the Path
    }

    public void saveImage()
    {
        String name = getFileName();

        // insert the image in the device's gallery

        String location = MediaStore.Images.Media.insertImage(
                getContext().getContentResolver(), bitmap, name,
                "Doodlz Drawing");

        if (location != null) // image was saved
        {
            // display a message indicating that the image was saved
            Toast message = Toast.makeText(getContext(),
                    R.string.message_saved, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        }
        else
        {
            // display a message indicating that the image was saved
            Toast message = Toast.makeText(getContext(),
                    R.string.message_error_saving, Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        }
    }
}
