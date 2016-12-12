package com.karcompany.utils;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.karcompany.R;
import com.karcompany.logging.DefaultLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sammyer on 2016-08-12.
 */
public class GlideUtils {

	public static String TAG = DefaultLogger.makeLogTag(GlideUtils.class);
	private static int mColorIndex = 0;

	public static final List<Integer> mColourList;

	static {
		List<Integer> colourList = new ArrayList<>(4);
		colourList.add(R.color.accent_brand);
		colourList.add(R.color.neutral_medium_soft_dark);
		colourList.add(R.color.light_brand);
		colourList.add(R.color.medium_brand);
		mColourList = Collections.unmodifiableList(colourList);
	}

	public static void loadImageWithNoCache(Fragment fragment, String url, ImageView gifView) {
		if (TextUtils.isEmpty(url)) return;
		Glide
				.with(fragment)
				.load(url)
				.asBitmap()
				.placeholder(getColorIndex())
				.skipMemoryCache(true)
				.error(getColorIndex())
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.into(gifView);
	}

	private static int getColorIndex() {
		int colour = mColourList.get(mColorIndex);
		mColorIndex++;
		if (mColorIndex == mColourList.size()) mColorIndex = 0;
		return colour;
	}

}
