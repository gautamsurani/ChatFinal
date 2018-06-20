package com.nazpowerchat.viewHolders;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nazpowerchat.R;
import com.nazpowerchat.interfaces.OnMessageItemClick;
import com.nazpowerchat.models.AttachmentTypes;
import com.nazpowerchat.models.DownloadFileEvent;
import com.nazpowerchat.models.Message;
import com.nazpowerchat.models.User;
import com.nazpowerchat.utils.FileUtils;
import com.nazpowerchat.utils.Helper;
import com.nazpowerchat.utils.MyFileProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentVideoViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.videoSize)
    TextView durationOrSize;
    @BindView(R.id.videoThumbnail)
    ImageView videoThumbnail;
    @BindView(R.id.videoPlay)
    ImageView videoPlay;
    @BindView(R.id.container)
    LinearLayout ll;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Message message;
    private File file;

    public MessageAttachmentVideoViewHolder(View itemView, OnMessageItemClick itemClickListener) {
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
    public void setData(Message message, int position, HashMap<String, User> myUsers) {
        super.setData(message, position, myUsers);
        this.message = message;
        //cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        //ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));

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

        boolean loading = message.getAttachment().getUrl().equals("loading");
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        videoPlay.setVisibility(loading ? View.GONE : View.VISIBLE);

        file = new File(Environment.getExternalStorageDirectory() + "/"
                +
                context.getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(message.getAttachmentType()) + (isMine() ? "/.sent/" : "")
                , message.getAttachment().getName());

        if (!file.exists()) {
            durationOrSize.setText(FileUtils.getReadableFileSize(message.getAttachment().getBytesCount()));
        } else {
//            Uri uri = Uri.fromFile(file);
//            try {
//                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//                mmr.setDataSource(context, uri);
//                String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//                long millis = Long.parseLong(durationStr);
//                durationOrSize.setText(TimeUnit.MILLISECONDS.toMinutes(millis) + ":" + TimeUnit.MILLISECONDS.toSeconds(millis));
//                Log.e("CHECK", String.valueOf(millis));
//                mmr.release();
//            } catch (RuntimeException e) {
//                Cursor cursor = MediaStore.Video.query(context.getContentResolver(), uri, new
//                        String[]{MediaStore.Video.VideoColumns.DURATION});
//                long duration = 0;
//                if (cursor != null && cursor.moveToFirst()) {
//                    duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
//                    Log.e("CHECK", String.valueOf(duration));
//                    durationOrSize.setText(TimeUnit.MILLISECONDS.toMinutes(duration) + ":" + TimeUnit.MILLISECONDS.toSeconds(duration));
//                }
//                if (cursor != null && !cursor.isClosed())
//                    cursor.close();
//            }
        }
        text.setText(message.getAttachment().getName());
        Glide.with(context).load(message.getAttachment().getData()).apply(new RequestOptions().placeholder(R.drawable.ic_video_24dp).centerCrop()).into(videoThumbnail);
        videoPlay.setImageDrawable(ContextCompat.getDrawable(context, file.exists() ? R.drawable.ic_play_circle_outline : R.drawable.ic_file_download_40dp));
    }

    @OnClick(R.id.videoPlay)
    public void downloadFile() {
        if (!Helper.CHAT_CAB)
            if (file.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = MyFileProvider.getUriForFile(context,
                        context.getString(R.string.authority),
                        file);
                intent.setDataAndType(uri, Helper.getMimeType(context, uri)); //storage path is path of your vcf file and vFile is name of that file.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(intent);
            } else if (!isMine())
                EventBus.getDefault().post(new DownloadFileEvent(message.getAttachmentType(), message.getAttachment(), getAdapterPosition()));
            else
                Toast.makeText(context, "File unavailable", Toast.LENGTH_SHORT).show();
    }
}
