package com.nazpowerchat.interfaces;

import android.view.View;

import com.nazpowerchat.models.Group;
import com.nazpowerchat.models.User;

/**
 * Created by a_man on 5/10/2017.
 */

public interface OnUserGroupItemClick {
    void OnUserClick(User user, int position, View userImage);
    void OnGroupClick(Group group, int position, View userImage);
}
