package com.nazpowerchat.viewHolders;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nazpowerchat.R;
import com.nazpowerchat.interfaces.OnMessageItemClick;
import com.nazpowerchat.models.Attachment;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentAudioViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.duration)
    TextView durationOrSize;
    @BindView(R.id.container)
    LinearLayout ll;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.playPauseToggle)
    ImageView playPauseToggle;
    private Message message;
    private File file;

    public MessageAttachmentAudioViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView, itemClickListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.CHAT_CAB)
                    downloadFile();
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
        //cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        //ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));
        Attachment attachment = message.getAttachment();
        this.message = message;

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
        playPauseToggle.setVisibility(loading ? View.GONE : View.VISIBLE);

        file = new File(Environment.getExternalStorageDirectory() + "/"
                +
                context.getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(message.getAttachmentType()) + (isMine() ? "/.sent/" : "")
                , message.getAttachment().getName());
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            try {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(context, uri);
                String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int millis = Integer.parseInt(durationStr);
                durationOrSize.setText(TimeUnit.MILLISECONDS.toMinutes(millis) + ":" + TimeUnit.MILLISECONDS.toSeconds(millis));
                mmr.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            durationOrSize.setText(FileUtils.getReadableFileSize(attachment.getBytesCount()));
        text.setText(message.getAttachment().getName());
    }

    //@OnClick(R.id.playPauseToggle)
    public void downloadFile() {
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = MyFileProvider.getUriForFile(context,
                    context.getString(R.string.authority),
                    file);
            intent.setDataAndType(uri, Helper.getMimeType(context, uri)); //storage path is path of your vcf file and vFile is name of that file.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } else if (!isMine() && !message.getAttachment().getUrl().equals("loading"))
            EventBus.getDefault().post(new DownloadFileEvent(message.getAttachmentType(), message.getAttachment(), getAdapterPosition()));
        else
            Toast.makeText(context, "File unavailable", Toast.LENGTH_SHORT).show();
    }
}
