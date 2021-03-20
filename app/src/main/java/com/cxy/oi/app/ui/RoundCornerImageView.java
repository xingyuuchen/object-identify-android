package com.cxy.oi.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.cxy.oi.kernel.util.Log;

import java.util.Arrays;

public class RoundCornerImageView extends AppCompatImageView {
    private static final String TAG = "RoundImageView";

    private final PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
//    private final PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private final Paint paint = new Paint();
    float[] radii = new float[8];
    Path path = new Path();
    Path srcPath = new Path();

    public RoundCornerImageView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.i(TAG, "[onDraw] %d, %d", width, height);

        Arrays.fill(radii, width / 5f);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.WHITE);

        srcPath.addRect(0, 0, width, height, Path.Direction.CW);

        path.addRoundRect(0, 0, width, height, radii, Path.Direction.CW);
        srcPath.op(path, Path.Op.DIFFERENCE);

        paint.setXfermode(xfermode);
        canvas.drawPath(srcPath, paint);
        paint.setXfermode(null);
    }
}
