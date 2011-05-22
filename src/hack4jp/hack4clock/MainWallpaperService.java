package hack4jp.hack4clock;

import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainWallpaperService extends WallpaperService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new ImageSwitcherEngine();
    }

	class ImageSwitcherEngine extends Engine {
		BitmapDrawable bitmap_drawable_map;
		BitmapDrawable bitmap_drawable_photo;
		BitmapDrawable bitmap_drawable_scaler;
		BitmapDrawable bitmap_drawable_text;
		Paint paint = new Paint ();
       Matrix matrix_map, matrix_photo, matrix_scaler, matrix_text;

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			Rect rect = surfaceHolder.getSurfaceFrame();
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.japanmap);
			bitmap_drawable_map = new BitmapDrawable(bmp);
			matrix_map = new Matrix ();
			matrix_map.setTranslate(-200, 0);
			matrix_map.setScale(0.6f, 0.6f);
			matrix_scaler = new Matrix ();
			matrix_scaler.setTranslate(0, 600);
			matrix_text = new Matrix ();
			matrix_text.setTranslate(0, 300);

			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.timeline);
			bitmap_drawable_scaler = new BitmapDrawable(bmp);

			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.text);
			bitmap_drawable_text = new BitmapDrawable(bmp);
			
			bmp = BitmapFactory.decodeFile("/sdcard/photo001.jpg");
			matrix_photo = new Matrix ();
//			matrix_photo.setScale(rect.width() / bmp.getWidth(), rect.height() / bmp.getHeight());
			bitmap_drawable_photo = new BitmapDrawable(bmp);
		}
        @Override
        public void onDestroy() {
            super.onDestroy();
        }
        @Override
        public void onVisibilityChanged(boolean visible) {
        }
        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
        
        SimpleDateFormat date_format = new SimpleDateFormat ("yyyy/M/d E");
        SimpleDateFormat time_format = new SimpleDateFormat ("HH:mm");
        
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);

            
            Canvas cvs = holder.lockCanvas();
            if (cvs != null) {
            	Paint p = new Paint ();
    			Rect rect = holder.getSurfaceFrame();
            	cvs.drawBitmap(bitmap_drawable_photo.getBitmap(), matrix_photo, paint);

            	cvs.drawBitmap(bitmap_drawable_map.getBitmap(), matrix_map, paint);
            	cvs.drawBitmap(bitmap_drawable_text.getBitmap(), matrix_text, p);
            	cvs.drawBitmap(bitmap_drawable_scaler.getBitmap(), matrix_scaler, p);
            	paint.setColor(Color.WHITE);
            	paint.setStrokeWidth(3.0f);
            	paint.setTextSize(50.0f);
            	cvs.drawText(time_format.format (new java.util.Date ()), rect.width() - 150, 500, paint);
            	paint.setTextSize(30.0f);
            	cvs.drawText(date_format.format (new java.util.Date ()), rect.width() - 230, 550, paint);
            	holder.unlockCanvasAndPost(cvs);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                float xStep, float yStep, int xPixels, int yPixels) {
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
        }
    }
}
