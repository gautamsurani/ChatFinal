package com.nazpowerchat.interfaces;

import com.nazpowerchat.models.Contact;
import com.nazpowerchat.models.User;

import java.util.ArrayList;

/**
 * Created by a_man on 01-01-2018.
 */

public interface HomeIneractor {
    User getUserMe();

    ArrayList<Contact> getLocalContacts();

}
