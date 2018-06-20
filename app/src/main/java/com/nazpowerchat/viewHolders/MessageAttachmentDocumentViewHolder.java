package com.nazpowerchat.viewHolders;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
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

import butterknife.BindView;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentDocumentViewHolder extends BaseMessageViewHolder {

    @BindView(R.id.file_extention)
    TextView fileExtention;
    @BindView(R.id.file_name)
    TextView fileName;
    @BindView(R.id.file_size)
    TextView fileSize;
    @BindView(R.id.container)
    LinearLayout ll;
    private Message message;
    private File file;

    public MessageAttachmentDocumentViewHolder(View itemView, OnMessageItemClick itemClickListener) {
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
        this.message = message;
        file = new File(Environment.getExternalStorageDirectory() + "/" +
                context.getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(message.getAttachmentType()) + (isMine() ? "/.sent/" : "")
                , message.getAttachment().getName());
        //ll.setBackgroundColor(isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));
        Attachment attachment = message.getAttachment();
        fileName.setText(attachment.getName());
        fileSize.setText(FileUtils.getReadableFileSize(attachment.getBytesCount()));
        fileExtention.setText(FileUtils.getExtension(attachment.getName()));
    }

    //@OnClick(R.id.download)
    private void downloadFile() {
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
