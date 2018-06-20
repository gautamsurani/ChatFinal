package com.nazpowerchat.viewHolders;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nazpowerchat.R;
import com.nazpowerchat.activities.ImageViewerActivity;
import com.nazpowerchat.interfaces.OnMessageItemClick;
import com.nazpowerchat.models.Message;
import com.nazpowerchat.models.User;
import com.nazpowerchat.utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentImageViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.container)
    LinearLayout ll;

    public MessageAttachmentImageViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView, itemClickListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(true);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick(false);
                return true;
            }
        });
    }

    @Override
    public void setData(final Message message, int position, HashMap<String, User> myUsers) {
        super.setData(message, position, myUsers);

        if (message.isSelected()) {
            if (isMine()) {
                ll.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_balloon_outgoing_normal));
            } else {
                ll.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_balloon_incoming_normal));
            }
        } else {
            if (isMine()) {
                ll.setBackground(ContextCompat.getDrawable(context, R.drawable.balloon_outgoing_normal));
            } else {
                ll.setBackground(ContextCompat.getDrawable(context, R.drawable.balloon_incoming_normal));
            }
        }

        //cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        //ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));

        Glide.with(context).load(message.getAttachment().getUrl())
                .apply(new RequestOptions().placeholder(R.drawable.image_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.CHAT_CAB)
                    context.startActivity(ImageViewerActivity.newInstance(context, message.getAttachment().getUrl()));
            }
        });

    }
}
