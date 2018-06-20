// Generated code from Butter Knife. Do not modify!
package com.nazpowerchat.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nazpowerchat.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MenuUsersRecyclerAdapter$UsersViewHolder_ViewBinding implements Unbinder {
  private MenuUsersRecyclerAdapter.UsersViewHolder target;

  @UiThread
  public MenuUsersRecyclerAdapter$UsersViewHolder_ViewBinding(MenuUsersRecyclerAdapter.UsersViewHolder target,
      View source) {
    this.target = target;

    target.userImage = Utils.findRequiredViewAsType(source, R.id.user_image, "field 'userImage'", CircleImageView.class);
    target.userName = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'userName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MenuUsersRecyclerAdapter.UsersViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userImage = null;
    target.userName = null;
  }
}
