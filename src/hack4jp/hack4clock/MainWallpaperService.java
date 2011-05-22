package hack4jp.hack4clock;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
		BitmapDrawable bitmap_drawable;
		Paint paint = new Paint ();
       Matrix matrix;

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			Rect rect = surfaceHolder.getSurfaceFrame();
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.japanmap);
			bitmap_drawable = new BitmapDrawable(bmp);
			matrix = new Matrix ();
			matrix.setScale(0.6f, 0.6f);
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
        
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);

            
            Canvas cvs = holder.lockCanvas();
            if (cvs != null) {
            	cvs.drawBitmap(bitmap_drawable.getBitmap(), matrix, paint);
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
