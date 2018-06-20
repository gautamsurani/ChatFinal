package com.nazpowerchat.viewHolders;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nazpowerchat.R;
import com.nazpowerchat.adapters.ContactsAdapter;
import com.nazpowerchat.interfaces.OnMessageItemClick;
import com.nazpowerchat.models.AttachmentTypes;
import com.nazpowerchat.models.DownloadFileEvent;
import com.nazpowerchat.models.Message;
import com.nazpowerchat.models.User;
import com.nazpowerchat.utils.Helper;
import com.nazpowerchat.utils.MyFileProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import ezvcard.Ezvcard;
import ezvcard.VCard;

/**
 * Created by mayank on 11/5/17.
 */

public class MessageAttachmentContactViewHolder extends BaseMessageViewHolder {
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.container)
    LinearLayout ll;
    private VCard vcard;

    private Dialog myDialog1;
    private ImageView contactImage;
    private TextView contactName, addToContactText;
    private RecyclerView contactPhones, contactEmails;

    private Message message;

    public MessageAttachmentContactViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView, itemClickListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put under some check
                if (!Helper.CHAT_CAB)
                    dialogVCardDetail();
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
        vcard = Ezvcard.parse(message.getAttachment().getData()).first();
        text.setText(vcard.getFormattedName() != null ? vcard.getFormattedName().getValue() : "Contact");
    }

    private void dialogVCardDetail() {
        if (myDialog1 == null) {
            myDialog1 = new Dialog(context, R.style.DialogBox);
            myDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            myDialog1.setCancelable(true);
            myDialog1.setContentView(R.layout.dialog_v_card_detail);

            contactImage = (ImageView) myDialog1.findViewById(R.id.contactImage);
            contactName = (TextView) myDialog1.findViewById(R.id.contactName);
            addToContactText = (TextView) myDialog1.findViewById(R.id.addToContactText);
            contactPhones = (RecyclerView) myDialog1.findViewById(R.id.recyclerPhone);
            contactEmails = (RecyclerView) myDialog1.findViewById(R.id.recyclerEmail);

            contactPhones.setLayoutManager(new LinearLayoutManager(context));
            contactEmails.setLayoutManager(new LinearLayoutManager(context));

            myDialog1.findViewById(R.id.contactAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (message != null) {
                        File file = new File(Environment.getExternalStorageDirectory() + "/"
                                +
                                context.getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(message.getAttachmentType()) + (isMine() ? "/.sent/" : "")
                                , message.getAttachment().getName());
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

//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(file), "text/x-vcard"); //storage path is path of your vcf file and vFile is name of that file.
//                        context.startActivity(intent);
                    }
                }
            });

            myDialog1.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog1.dismiss();
                }
            });
        }

        if (vcard.getPhotos().size() > 0)
            Glide.with(context).load(vcard.getPhotos().get(0).getData()).apply(new RequestOptions().dontAnimate()).into(contactImage);

        contactName.setText(vcard.getFormattedName().getValue());

        contactPhones.setAdapter(new ContactsAdapter(context, vcard.getTelephoneNumbers(), null));
        contactEmails.setAdapter(new ContactsAdapter(context, null, vcard.getEmails()));

        myDialog1.show();
    }
}
