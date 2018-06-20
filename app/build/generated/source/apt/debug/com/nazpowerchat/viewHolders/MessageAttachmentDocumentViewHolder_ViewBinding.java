// Generated code from Butter Knife. Do not modify!
package com.nazpowerchat.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.nazpowerchat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentDocumentViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentDocumentViewHolder target;

  @UiThread
  public MessageAttachmentDocumentViewHolder_ViewBinding(MessageAttachmentDocumentViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.fileExtention = Utils.findRequiredViewAsType(source, R.id.file_extention, "field 'fileExtention'", TextView.class);
    target.fileName = Utils.findRequiredViewAsType(source, R.id.file_name, "field 'fileName'", TextView.class);
    target.fileSize = Utils.findRequiredViewAsType(source, R.id.file_size, "field 'fileSize'", TextView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentDocumentViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fileExtention = null;
    target.fileName = null;
    target.fileSize = null;
    target.ll = null;

    super.unbind();
  }
}
