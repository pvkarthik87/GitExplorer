package com.karcompany.views.adapters;

/**
 * Created by pvkarthik on 2016-12-05.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.R;

public class UserListItemViewHolder extends RecyclerView.ViewHolder {

	public TextView nameTxtView;
	public ImageView profileImgView;

	public UserListItemViewHolder(View itemView) {
		super(itemView);
		nameTxtView = (TextView) itemView.findViewById(R.id.data);
		profileImgView = (ImageView) itemView.findViewById(R.id.userProfileImage);
	}

}
