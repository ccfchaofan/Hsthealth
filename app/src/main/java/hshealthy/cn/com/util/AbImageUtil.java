package hshealthy.cn.com.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import hshealthy.cn.com.R;

/**
 * © 2012 amsoft.cn 名称：AbImageUtil.java 描述：图片处理类.
 */
public class AbImageUtil {

	/** 图片处理：裁剪. */
	public static final int CUTIMG = 0;

	/** 图片处理：下载后缩放. */
	public static final int SCALEIMG = 1;

	/** 图片处理：不处理. */
	public static final int ORIGINALIMG = 2;

	/** 图片处理：下载时缩放 */
	public static final int SCALEIMG2 = 3;

	public static final String FILES_PATH = "CompressHelper";


	private AbImageUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	/**
	 * 直接获取互联网上的图片.
	 * 
	 * @param imageUrl
	 *            要下载文件的网络地址
	 * @param type
	 *            图片的处理类型（剪切或者缩放到指定大小，参考AbConstant类）
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap getBitmapFormURL(String imageUrl, int type,
			int newWidth, int newHeight) {
		Bitmap bm = null;
		URLConnection con = null;
		InputStream is = null;
		try {
			URL url = new URL(imageUrl);
			con = url.openConnection();
			con.setDoInput(true);
			con.connect();
			is = con.getInputStream();
			// 获取资源图片
			if (type == SCALEIMG2) {
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(is, null, opt);
				opt.inSampleSize = calculateInSampleSize(opt, newWidth,
						newHeight);
				opt.inJustDecodeBounds = false;

				con = url.openConnection();
				con.setDoInput(true);
				con.connect();
				if (is != con.getInputStream()) {
					is = con.getInputStream();
				}

				bm = BitmapFactory.decodeStream(is, null, opt);
			} else {
				Bitmap wholeBm = BitmapFactory.decodeStream(is, null, null);
				if (type == CUTIMG) {
					bm = cutImg(wholeBm, newWidth, newHeight);
				} else if (type == SCALEIMG) {
					bm = scaleImg(wholeBm, newWidth, newHeight);
				} else {
					bm = wholeBm;
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bm;
	}

	/**
	 * 描述：获取原图.
	 * 
	 * @param file
	 *            File对象
	 * @return Bitmap 图片
	 */
	public static Bitmap originalImg(File file) {
		Bitmap resizeBmp = null;
		try {
			resizeBmp = BitmapFactory.decodeFile(file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resizeBmp;
	}

	/**
	 * 描述：缩放图片.压缩
	 * 
	 * @param file
	 *            File对象
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleImg(File file, int newWidth, int newHeight) {
		Bitmap resizeBmp = null;
		if (newWidth <= 0 || newHeight <= 0) {
			throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), opts);

		// 获取图片的原始宽度高度
		int srcWidth = opts.outWidth;
		int srcHeight = opts.outHeight;

		int destWidth = srcWidth;
		int destHeight = srcHeight;

		// 缩放的比例
		float scale = 0;
		// 计算缩放比例，使一个方向缩放后，另一方向不小与显示的大小的最合适比例
		float scaleWidth = (float) newWidth / srcWidth;
		float scaleHeight = (float) newHeight / srcHeight;
		if (scaleWidth > scaleHeight) {
			scale = scaleWidth;
		} else {
			scale = scaleHeight;
		}
		if (scale != 0) {
			destWidth = (int) (destWidth / scale);
			destHeight = (int) (destHeight / scale);
		}

		// 默认为ARGB_8888.
		opts.inPreferredConfig = Config.RGB_565;
		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;

		// inSampleSize=2 表示图片宽高都为原来的二分之一，即图片为原来的四分之一
		// 缩放的比例，SDK中建议其值是2的指数值，通过inSampleSize来进行缩放，其值表明缩放的倍数
		if (scale > 1) {
			// 缩小
			opts.inSampleSize = (int) scale;
		} else {
			// 不缩放
			opts.inSampleSize = 1;
		}

		// 设置大小
		opts.outHeight = destHeight;
		opts.outWidth = destWidth;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		// 缩小或者放大
		if (resizeBmp != null && scale != 1) {
			resizeBmp = scaleImg(resizeBmp, scale);
		}
		return resizeBmp;
	}

	/**
	 * 描述：缩放图片,不压缩的缩放.
	 *
	 * @param bitmap
	 *            the bitmap
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleImg(Bitmap bitmap, int newWidth, int newHeight) {

		Bitmap resizeBmp = null;
		if (bitmap == null) {
			return null;
		}
		if (newWidth <= 0 || newHeight <= 0) {
			throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
		}
		// 获得图片的宽高
		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();

		if (srcWidth <= 0 || srcHeight <= 0) {
			return null;
		}
		// 缩放的比例
		float scale = 0;
		// 计算缩放比例
		float scaleWidth = (float) newWidth / srcWidth;
		float scaleHeight = (float) newHeight / srcHeight;
		if (scaleWidth > scaleHeight) {
			scale = scaleWidth;
		} else {
			scale = scaleHeight;
		}
		// 缩小或者放大
		if (bitmap != null && scale != 1) {
			resizeBmp = scaleImg(bitmap, scale);
		}
		return resizeBmp;
	}

	/**
	 * 描述：根据等比例缩放图片.
	 *
	 * @param bitmap
	 *            the bitmap
	 * @param scale
	 *            比例
	 * @return Bitmap 新图片
	 */
	public static Bitmap scaleImg(Bitmap bitmap, float scale) {
		Bitmap resizeBmp = null;
		try {
			// 获取Bitmap资源的宽和高
			int bmpW = bitmap.getWidth();
			int bmpH = bitmap.getHeight();
			// 注意这个Matirx是android.graphics底下的那个
			Matrix mt = new Matrix();
			// 设置缩放系数，分别为原来的0.8和0.8
			mt.postScale(scale, scale);
			resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, mt, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}

	/**
	 * 描述：裁剪图片.
	 *
	 * @param file
	 *            File对象
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap cutImg(File file, int newWidth, int newHeight) {
		Bitmap resizeBmp = null;
		if (newWidth <= 0 || newHeight <= 0) {
			throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
		}

		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为true,decodeFile先不创建内存 只获取一些解码边界信息即图片大小信息
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getPath(), opts);
		// inSampleSize=2表示图片宽高都为原来的二分之一，即图片为原来的四分之一
		// 缩放可以将像素点打薄,裁剪前将图片缩放到目标图2倍大小
		int srcWidth = opts.outWidth; // 获取图片的原始宽度
		int srcHeight = opts.outHeight;// 获取图片原始高度
		int destWidth = 0;
		int destHeight = 0;

		int cutSrcWidth = newWidth * 2;
		int cutSrcHeight = newHeight * 2;

		// 缩放的比例,为了大图的缩小到2倍被裁剪的大小在裁剪
		double ratio = 0.0;
		// 任意一个不够长就不缩放
		if (srcWidth < cutSrcWidth || srcHeight < cutSrcHeight) {
			ratio = 0.0;
			destWidth = srcWidth;
			destHeight = srcHeight;
		} else if (srcWidth > cutSrcWidth) {
			ratio = (double) srcWidth / cutSrcWidth;
			destWidth = cutSrcWidth;
			destHeight = (int) (srcHeight / ratio);
		} else if (srcHeight > cutSrcHeight) {
			ratio = (double) srcHeight / cutSrcHeight;
			destHeight = cutSrcHeight;
			destWidth = (int) (srcWidth / ratio);
		}

		// 默认为ARGB_8888.
		opts.inPreferredConfig = Config.RGB_565;
		// 以下两个字段需一起使用：
		// 产生的位图将得到像素空间，如果系统gc，那么将被清空。当像素再次被访问，如果Bitmap已经decode，那么将被自动重新解码
		opts.inPurgeable = true;
		// 位图可以共享一个参考输入数据(inputstream、阵列等)
		opts.inInputShareable = true;
		// 缩放的比例，缩放是很难按准备的比例进行缩放的，通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
		if (ratio > 1) {
			opts.inSampleSize = (int) ratio;
		} else {
			opts.inSampleSize = 1;
		}
		// 设置大小
		opts.outHeight = destHeight;
		opts.outWidth = destWidth;
		// 创建内存
		opts.inJustDecodeBounds = false;
		// 使图片不抖动
		opts.inDither = false;
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
		if (bitmap != null) {
			resizeBmp = cutImg(bitmap, newWidth, newHeight);
		}
		return resizeBmp;
	}

	/**
	 * 描述：裁剪图片.
	 *
	 * @param bitmap
	 *            the bitmap
	 * @param newWidth
	 *            新图片的宽
	 * @param newHeight
	 *            新图片的高
	 * @return Bitmap 新图片
	 */
	public static Bitmap cutImg(Bitmap bitmap, int newWidth, int newHeight) {
		if (bitmap == null) {
			return null;
		}

		if (newWidth <= 0 || newHeight <= 0) {
			throw new IllegalArgumentException("裁剪图片的宽高设置不能小于0");
		}

		Bitmap resizeBmp = null;

		try {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			if (width <= 0 || height <= 0) {
				return null;
			}
			int offsetX = 0;
			int offsetY = 0;

			if (width > newWidth) {
				offsetX = (width - newWidth) / 2;
			} else {
				newWidth = width;
			}

			if (height > newHeight) {
				offsetY = (height - newHeight) / 2;
			} else {
				newHeight = height;
			}

			resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, newWidth,
					newHeight);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resizeBmp != bitmap) {
				bitmap.recycle();
			}
		}
		return resizeBmp;
	}

	/**
	 * Drawable转Bitmap.
	 *
	 * @param drawable
	 *            要转化的Drawable
	 * @return Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap对象转换Drawable对象.
	 * 
	 * @param bitmap
	 *            要转化的Bitmap对象
	 * @return Drawable 转化完成的Drawable对象
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable mBitmapDrawable = null;
		try {
			if (bitmap == null) {
				return null;
			}
			mBitmapDrawable = new BitmapDrawable(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * Bitmap对象转换TransitionDrawable对象.
	 * 
	 * @param bitmap
	 *            要转化的Bitmap对象 imageView.setImageDrawable(td);
	 *            td.startTransition(200);
	 * @return Drawable 转化完成的Drawable对象
	 */
	public static TransitionDrawable bitmapToTransitionDrawable(Context mContext, Bitmap bitmap) {
		TransitionDrawable mBitmapDrawable = null;
		try {
			if (bitmap == null) {
				return null;
			}
			mBitmapDrawable = new TransitionDrawable(new Drawable[] {
//					new ColorDrawable(android.R.color.transparent),
					new ColorDrawable(mContext.getResources().getColor(R.color.transparent)),
					new BitmapDrawable(bitmap) });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * Drawable对象转换TransitionDrawable对象.
	 * 
	 * @param drawable
	 *            要转化的Drawable对象 imageView.setImageDrawable(td);
	 *            td.startTransition(200);
	 * @return Drawable 转化完成的Drawable对象
	 */
	public static TransitionDrawable drawableToTransitionDrawable(Context mContext,
			Drawable drawable) {
		TransitionDrawable mBitmapDrawable = null;
		try {
			if (drawable == null) {
				return null;
			}
			mBitmapDrawable = new TransitionDrawable(new Drawable[] {
					new ColorDrawable(mContext.getResources().getColor(R.color.transparent)), drawable });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * 将Bitmap转换为byte[].
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param mCompressFormat
	 *            图片格式 Bitmap.CompressFormat.JPEG,CompressFormat.PNG
	 * @param needRecycle
	 *            是否需要回收
	 * @return byte[] 图片的byte[]
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap,
			Bitmap.CompressFormat mCompressFormat, final boolean needRecycle) {
		byte[] result = null;
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			bitmap.compress(mCompressFormat, 100, output);
			result = output.toByteArray();
			if (needRecycle) {
				bitmap.recycle();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 获取Bitmap大小.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param mCompressFormat
	 *            图片格式 Bitmap.CompressFormat.JPEG,CompressFormat.PNG
	 * @return 图片的大小
	 */
	public static int getByteCount(Bitmap bitmap,
			Bitmap.CompressFormat mCompressFormat) {
		int size = 0;
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			bitmap.compress(mCompressFormat, 100, output);
			byte[] result = output.toByteArray();
			size = result.length;
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return size;
	}

	/**
	 * 描述：将byte[]转换为Bitmap.
	 * 
	 * @param b
	 *            图片格式的byte[]数组
	 * @return bitmap 得到的Bitmap
	 */
	public static Bitmap bytes2Bimap(byte[] b) {
		Bitmap bitmap = null;
		try {
			if (b.length != 0) {
				bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将ImageView转换为Bitmap.
	 * 
	 * @param view
	 *            要转换为bitmap的View
	 * @return byte[] 图片的byte[]
	 */
	public static Bitmap imageView2Bitmap(ImageView view) {
		Bitmap bitmap = null;
		try {
			bitmap = Bitmap.createBitmap(view.getDrawingCache());
			view.setDrawingCacheEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将View转换为Drawable.需要最上层布局为Linearlayout
	 * 
	 * @param view
	 *            要转换为Drawable的View
	 * @return BitmapDrawable Drawable
	 */
	public static Drawable view2Drawable(View view) {
		BitmapDrawable mBitmapDrawable = null;
		try {
			Bitmap newbmp = view2Bitmap(view);
			if (newbmp != null) {
				mBitmapDrawable = new BitmapDrawable(newbmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmapDrawable;
	}

	/**
	 * 将View转换为Bitmap.需要最上层布局为Linearlayout
	 * 
	 * @param view
	 *            要转换为bitmap的View
	 * @return byte[] 图片的byte[]
	 */
	public static Bitmap view2Bitmap(View view) {
		Bitmap bitmap = null;
		try {
			if (view != null) {
				view.setDrawingCacheEnabled(true);
				view.measure(
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				view.layout(0, 0, view.getMeasuredWidth(),
						view.getMeasuredHeight());
				view.buildDrawingCache();
				bitmap = view.getDrawingCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 将View转换为byte[].
	 * 
	 * @param view
	 *            要转换为byte[]的View
	 * @param compressFormat
	 *            the compress format
	 * @return byte[] View图片的byte[]
	 */
	public static byte[] view2Bytes(View view,
			Bitmap.CompressFormat compressFormat) {
		byte[] b = null;
		try {
			Bitmap bitmap = AbImageUtil.view2Bitmap(view);
			b = AbImageUtil.bitmap2Bytes(bitmap, compressFormat, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 描述：旋转Bitmap为一定的角度.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param degrees
	 *            the degrees
	 * @return the bitmap
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
		Bitmap mBitmap = null;
		try {
			Matrix m = new Matrix();
			m.setRotate(degrees % 360);
			mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;
	}

	/**
	 * 描述：旋转Bitmap为一定的角度并四周暗化处理.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param degrees
	 *            the degrees
	 * @return the bitmap
	 */
	public static Bitmap rotateBitmapTranslate(Bitmap bitmap, float degrees) {
		Bitmap mBitmap = null;
		int width;
		int height;
		try {
			Matrix matrix = new Matrix();
			if ((degrees / 90) % 2 != 0) {
				width = bitmap.getWidth();
				height = bitmap.getHeight();
			} else {
				width = bitmap.getHeight();
				height = bitmap.getWidth();
			}
			int cx = width / 2;
			int cy = height / 2;
			matrix.preTranslate(-cx, -cy);
			matrix.postRotate(degrees);
			matrix.postTranslate(cx, cy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBitmap;
	}

	/**
	 * 转换图片转换成圆形.
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return the bitmap
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		return toRoundBitmap(bitmap, Integer.MAX_VALUE);
	}

	/**
	 * 转换图片成圆角
	 * 
	 * @author shaowei.ma
	 * @date 2014年10月24日
	 * @param bitmap
	 * @param pixel
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap, int pixel) {
		if (bitmap == null) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx = pixel;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			if (pixel >= width / 2)
				roundPx = width / 2;
			else
				roundPx = pixel;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			if (pixel >= height / 2)
				roundPx = width / 2;
			else
				roundPx = pixel;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 转换图片转换成镜面效果的图片.
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return the bitmap
	 */
	public static Bitmap toReflectionBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}

		try {
			int reflectionGap = 1;
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			// This will not scale but will flip on the Y axis
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);

			// Create a Bitmap with the flip matrix applied to it.
			// We only want the bottom half of the image
			Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
					width, height / 2, matrix, false);

			// Create a new bitmap with same width but taller to fit
			// reflection
			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 2), Config.ARGB_8888);

			// Create a new Canvas with the bitmap that's big enough for
			// the image plus gap plus reflection
			Canvas canvas = new Canvas(bitmapWithReflection);
			// Draw in the original image
			canvas.drawBitmap(bitmap, 0, 0, null);
			// Draw in the gap
			Paint deafaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap,
					deafaultPaint);
			// Draw in the reflection
			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
			// Create a shader that is a linear gradient that covers the
			// reflection
			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0, bitmap.getHeight(),
					0, bitmapWithReflection.getHeight() + reflectionGap,
					0x70ffffff, 0x00ffffff, TileMode.CLAMP);
			// Set the paint to use this shader (linear gradient)
			paint.setShader(shader);
			// Set the Transfer mode to be porter duff and destination in
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			// Draw a rectangle using the paint with our linear gradient
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);

			bitmap = bitmapWithReflection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 释放Bitmap对象.
	 * 
	 * @param bitmap
	 *            要释放的Bitmap
	 */
	public static void releaseBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			try {
				if (!bitmap.isRecycled()) {
					bitmap.recycle();
				}
			} catch (Exception e) {
			}
			bitmap = null;
		}
	}

	/**
	 * 释放Bitmap数组.
	 * 
	 * @param bitmaps
	 *            要释放的Bitmap数组
	 */
	public static void releaseBitmapArray(Bitmap[] bitmaps) {
		if (bitmaps != null) {
			try {
				for (Bitmap bitmap : bitmaps) {
					if (bitmap != null && !bitmap.isRecycled()) {
						bitmap.recycle();
					}
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 描述：简单的图像的特征值，用于缩略图找原图比较好.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return the hash code
	 */
	public static String getHashCode(Bitmap bitmap) {
		// 第一步，缩小尺寸。
		// 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，
		// 只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。

		Bitmap temp = Bitmap.createScaledBitmap(bitmap, 8, 8, false);

		int width = temp.getWidth();
		int height = temp.getHeight();

		// 第二步，第二步，简化色彩。
		// 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i * height + j] = rgbToGray(temp.getPixel(i, j));
			}
		}

		releaseBitmap(temp);

		// 第三步，计算平均值。
		// 计算所有64个像素的灰度平均值。
		int avgPixel = AbMathUtil.average(pixels);

		// 第四步，比较像素的灰度。
		// 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// 第五步，计算哈希值。
		// 将上一步的比较结果，组合在一起，就构成了一个64位的整数，
		// 这就是这张图片的指纹。
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1]
					* (int) Math.pow(2, 2) + comps[i + 2]
					* (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(AbMathUtil.binaryToHex(result));
		}
		String sourceHashCode = hashCode.toString();
		// 得到指纹以后，就可以对比不同的图片，看看64位中有多少位是不一样的。
		// 在理论上，这等同于计算"汉明距离"（Hamming distance）。
		// 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
		return sourceHashCode;
	}


	/**
	 * 描述：图像的特征值颜色分布 将颜色分4个区，0,1,2,3 区组合共64组，计算每个像素点属于哪个区.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return the color histogram
	 */
	public static int[] getColorHistogram(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 区颜色分布
		int[] areaColor = new int[64];

		// 获取色彩数组。
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixels = bitmap.getPixel(i, j);
				int alpha = (pixels >> 24) & 0xFF;
				int red = (pixels >> 16) & 0xFF;
				int green = (pixels >> 8) & 0xFF;
				int blue = (pixels) & 0xFF;
				int redArea = 0;
				int greenArea = 0;
				int blueArea = 0;
				// 0-63 64-127 128-191 192-255
				if (red >= 192) {
					redArea = 3;
				} else if (red >= 128) {
					redArea = 2;
				} else if (red >= 64) {
					redArea = 1;
				} else if (red >= 0) {
					redArea = 0;
				}

				if (green >= 192) {
					greenArea = 3;
				} else if (green >= 128) {
					greenArea = 2;
				} else if (green >= 64) {
					greenArea = 1;
				} else if (green >= 0) {
					greenArea = 0;
				}

				if (blue >= 192) {
					blueArea = 3;
				} else if (blue >= 128) {
					blueArea = 2;
				} else if (blue >= 64) {
					blueArea = 1;
				} else if (blue >= 0) {
					blueArea = 0;
				}
				int index = redArea * 16 + greenArea * 4 + blueArea;
				// 加入
				areaColor[index] += 1;
			}
		}
		return areaColor;
	}

	/**
	 * 计算"汉明距离"（Hamming distance）。
	 * 如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。.
	 * 
	 * @param sourceHashCode
	 *            源hashCode
	 * @param hashCode
	 *            与之比较的hashCode
	 * @return the int
	 */
	public static int hammingDistance(String sourceHashCode, String hashCode) {
		int difference = 0;
		int len = sourceHashCode.length();
		for (int i = 0; i < len; i++) {
			if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
				difference++;
			}
		}
		return difference;
	}

	/**
	 * 灰度值计算.
	 * 
	 * @param pixels
	 *            像素
	 * @return int 灰度值
	 */
	private static int rgbToGray(int pixels) {
		// int _alpha = (pixels >> 24) & 0xFF;
		int _red = (pixels >> 16) & 0xFF;
		int _green = (pixels >> 8) & 0xFF;
		int _blue = (pixels) & 0xFF;
		return (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
	}

	/**
	 * 获得BitmapFactory.Options的inSampleSize下载时图片缩放值
	 * 
	 * @author shaowei.ma
	 * @date 2014年9月19日
	 * @param options
	 *            携带图片尺寸信息的BitmapFactory.Options
	 * @param reqWidth
	 *            图片目标宽度
	 * @param reqHeight
	 *            图片目标高度
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		if (reqWidth <= 0 || reqHeight <= 0) {
			throw new IllegalArgumentException("缩放图片的宽高设置不能小于0");
		}
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	/**
	 * url转bitmap
	 */
	public static  Bitmap getBitmap(String url) {
		Bitmap bm = null;
		try {
			URL iconUrl = new URL(url);
			URLConnection conn = iconUrl.openConnection();
			HttpURLConnection http = (HttpURLConnection) conn;

			int length = http.getContentLength();

			conn.connect();
			// 获得图像的字符流
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is, length);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();// 关闭流
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return bm;
	}

	public static File compressImage(Context context, Uri imageUri, float maxWidth, float maxHeight,
							  Bitmap.CompressFormat compressFormat, Config bitmapConfig,
							  int quality, String parentPath, String prefix, String fileName) {
		FileOutputStream out = null;
		String filename = FileUtil.generateFilePath(context, parentPath, imageUri, compressFormat.name().toLowerCase(), prefix, fileName);
		try {
			out = new FileOutputStream(filename);
			// 通过文件名写入
			Bitmap newBmp = AbImageUtil.getScaledBitmap(context, imageUri, maxWidth, maxHeight, bitmapConfig);
			if (newBmp != null){
				newBmp.compress(compressFormat, quality, out);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ignored) {
			}
		}

		return new File(filename);
	}

	static Bitmap getScaledBitmap(Context context, Uri imageUri, float maxWidth, float maxHeight, Config bitmapConfig) {
		String filePath = FileUtil.getRealPathFromURI(context, imageUri);
		Bitmap scaledBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();

		//by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
		//you try the use the bitmap here, you will get null.
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
		if (bmp == null) {
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(filePath);
				BitmapFactory.decodeStream(inputStream, null, options);
				inputStream.close();
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

		if (actualHeight == -1 || actualWidth == -1){
			try {
				ExifInterface exifInterface = new ExifInterface(filePath);
				actualHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
				actualWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (actualWidth <= 0 || actualHeight <= 0) {
			Bitmap bitmap2 = BitmapFactory.decodeFile(filePath);
			if (bitmap2 != null){
				actualWidth = bitmap2.getWidth();
				actualHeight = bitmap2.getHeight();
			}else{
				return null;
			}
		}

		float imgRatio = (float) actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		//width and height values are set maintaining the aspect ratio of the image
		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;
			}
		}

		//setting inSampleSize value allows to load a scaled down version of the original image
		options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

		//inJustDecodeBounds set to false to load the actual bitmap
		options.inJustDecodeBounds = false;

		//this options allow android to claim the bitmap memory if it runs low on memory
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		try {
			// load the bitmap getTempFile its path
			bmp = BitmapFactory.decodeFile(filePath, options);
			if (bmp == null) {
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(filePath);
					BitmapFactory.decodeStream(inputStream, null, options);
					inputStream.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}
		if (actualHeight <= 0 || actualWidth <= 0){
			return null;
		}

		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, bitmapConfig);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, 0, 0);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

		// 采用 ExitInterface 设置图片旋转方向
		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
			} else if (orientation == 3) {
				matrix.postRotate(180);
			} else if (orientation == 8) {
				matrix.postRotate(270);
			}
			scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
					scaledBitmap.getWidth(), scaledBitmap.getHeight(),
					matrix, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return scaledBitmap;
	}

    /*
     * 获取图片大小单位"B", "KB", "MB", "GB", "TB"
     */
	public static String getReadableFileSize(long size) {
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	/**
	 * 获取图片类型
	 *
	 * @param filePath 文件路径
	 * @return 图片类型
	 */
	public static String getImageType(String filePath) {
		return getImageType(FileUtil.getFileByPath(filePath));
	}

	/**
	 * 获取图片类型
	 *
	 * @param file 文件
	 * @return 图片类型
	 */
	public static String getImageType(File file) {
		if (file == null)
			return null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			return getImageType(is);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			CloseUtils.closeIO(is);
		}
	}

	/**
	 * 流获取图片类型
	 *
	 * @param is 图片输入流
	 * @return 图片类型
	 */
	public static String getImageType(InputStream is) {
		if (is == null)
			return null;
		try {
			byte[] bytes = new byte[8];
			return is.read(bytes, 0, 8) != -1 ? getImageType(bytes) : null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取图片类型
	 *
	 * @param bytes bitmap的前8字节
	 * @return 图片类型
	 */
	public static String getImageType(byte[] bytes) {
		if (isJPEG(bytes))
			return "JPEG";
		if (isGIF(bytes))
			return "GIF";
		if (isPNG(bytes))
			return "PNG";
		if (isBMP(bytes))
			return "BMP";
		return null;
	}

	private static boolean isJPEG(byte[] b) {
		return b.length >= 2
				&& (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
	}

	private static boolean isGIF(byte[] b) {
		return b.length >= 6
				&& b[0] == 'G' && b[1] == 'I'
				&& b[2] == 'F' && b[3] == '8'
				&& (b[4] == '7' || b[4] == '9') && b[5] == 'a';
	}

	private static boolean isPNG(byte[] b) {
		return b.length >= 8
				&& (b[0] == (byte) 137 && b[1] == (byte) 80
				&& b[2] == (byte) 78 && b[3] == (byte) 71
				&& b[4] == (byte) 13 && b[5] == (byte) 10
				&& b[6] == (byte) 26 && b[7] == (byte) 10);
	}

	private static boolean isBMP(byte[] b) {
		return b.length >= 2
				&& (b[0] == 0x42) && (b[1] == 0x4d);
	}

	/**
	 * 根据图片的url路径获得Bitmap对象
	 * @param url
	 * @return
	 */
	public static Bitmap returnBitmap(String url) {
		URL fileUrl = null;
		Bitmap bitmap = null;
		try {
			fileUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
			conn.setDoInput(true);
			conn.setUseCaches(false); //设置不使用缓存
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				conn.connect();
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	/**
	 * 定义一个根据图片url获取InputStream的方法
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		// 关闭流一定要记得。
		return outstream.toByteArray();
	}

	//保存文件到指定路径
	public static boolean saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
		File appDir = new File(storePath);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			//通过io流的方式来压缩保存图片
			boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();

			//把文件插入到系统图库
			//保存图片后发送广播通知更新数据库
			Uri uri = Uri.fromFile(file);
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
			if (isSuccess) {
				ToastUtils.showToast("保存成功");
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;
	}

	public static boolean  saveImageToGallery11(Context context, Bitmap bmp) {
		 return saveImageToGallery11(context,bmp,context.getFilesDir().getPath() + "/hyz",System.currentTimeMillis() + ".jpg");
	}

	public static boolean fileIsExit(Context context,String dirPath,String url){
		File appDir = new File(dirPath);
		if (!appDir.exists()) {
			appDir.mkdirs();
		}
		String filePath = getNameFromUrl(url);

		if (TextUtils.isEmpty(filePath))
			return false;
		File file =new File(appDir,getNameFromUrl(url));
		if (file.exists() && file.isFile() ){
			return true;
		}
		return false;
	}

	/**
	 * @param url
	 * @return
	 * 从下载连接中解析出文件名
	 */
	@NonNull
	public static String getNameFromUrl(String url) {
		try {
			return url.substring(url.lastIndexOf("/") + 1);
		}catch (Exception w){

		}
		return "";
	}

	public static boolean saveImageToGallery11(Context context, Bitmap bmp,String dirPath,String filePath) {
		// 首先保存图片
		File appDir = new File(dirPath);
		if (!appDir.exists()) {
			appDir.mkdirs();
		}
		if (TextUtils.isEmpty(filePath))
		{
			return saveImageToGallery11(context,bmp);
		}
		String fileName =filePath;
		File file = new File(appDir, fileName);
		if(file.exists()){
			file.delete();
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			if(bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)){
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
//		ToastUtils.showToast("保存成功");
		Uri uri = Uri.fromFile(file);

		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
		return true;
	}
}
