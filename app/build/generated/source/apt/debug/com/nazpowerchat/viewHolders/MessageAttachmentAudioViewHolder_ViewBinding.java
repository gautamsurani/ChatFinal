// Generated code from Butter Knife. Do not modify!
package com.nazpowerchat.viewHolders;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.nazpowerchat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageAttachmentAudioViewHolder_ViewBinding extends BaseMessageViewHolder_ViewBinding {
  private MessageAttachmentAudioViewHolder target;

  @UiThread
  public MessageAttachmentAudioViewHolder_ViewBinding(MessageAttachmentAudioViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
    target.durationOrSize = Utils.findRequiredViewAsType(source, R.id.duration, "field 'durationOrSize'", TextView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.container, "field 'll'", LinearLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.playPauseToggle = Utils.findRequiredViewAsType(source, R.id.playPauseToggle, "field 'playPauseToggle'", ImageView.class);
  }

  @Override
  public void unbind() {
    MessageAttachmentAudioViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.text = null;
    target.durationOrSize = null;
    target.ll = null;
    target.progressBar = null;
    target.playPauseToggle = null;

    super.unbind();
  }
}
