// Generated code from Butter Knife. Do not modify!
package com.nazpowerchat.viewHolders;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nazpowerchat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseMessageViewHolder_ViewBinding implements Unbinder {
  private BaseMessageViewHolder target;

  @UiThread
  public BaseMessageViewHolder_ViewBinding(BaseMessageViewHolder target, View source) {
    this.target = target;

    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", TextView.class);
    target.container = Utils.findRequiredViewAsType(source, R.id.container, "field 'container'", LinearLayout.class);
    target.senderName = Utils.findRequiredViewAsType(source, R.id.senderName, "field 'senderName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseMessageViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.time = null;
    target.container = null;
    target.senderName = null;
  }
}
