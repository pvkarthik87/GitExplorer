package com.karcompany.views.adapters;

/**
 * Created by pvkarthik on 2016-12-12.
 *
 * Loading view holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.karcompany.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {

	public TextView dataTxtView;

	public LoadingViewHolder(View itemView) {
		super(itemView);
		dataTxtView = (TextView) itemView.findViewById(R.id.data);
	}

}
