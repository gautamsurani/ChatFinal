package com.nazpowerchat.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nazpowerchat.R;
import com.nazpowerchat.fragments.ChatDetailFragment;
import com.nazpowerchat.fragments.UserMediaFragment;
import com.nazpowerchat.interfaces.OnUserDetailFragmentInteraction;
import com.nazpowerchat.models.Attachment;
import com.nazpowerchat.models.AttachmentTypes;
import com.nazpowerchat.models.Chat;
import com.nazpowerchat.models.Group;
import com.nazpowerchat.models.Message;
import com.nazpowerchat.models.MyString;
import com.nazpowerchat.models.User;
import com.nazpowerchat.utils.GeneralUtils;
import com.nazpowerchat.utils.Helper;
import com.nazpowerchat.utils.ImageCompressorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChatDetailActivity extends BaseActivity implements OnUserDetailFragmentInteraction {
    private Handler handler;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout coordinatorLayout;
    private View userDetailsSummaryContainer, pickImage;
    private ImageView userImage;
    private EditText userStatus, userName;
    private ArrayList<Message> mediaMessages;

    private static final String TAG_DETAIL = "TAG_DETAIL", TAG_MEDIA = "TAG_MEDIA";
    private static String EXTRA_DATA_USER = "extradatauser";
    private static String EXTRA_DATA_GROUP = "extradatagroup";
    private static final int REQUEST_CODE_PICKER = 4321;
    private static final int REQUEST_CODE_MEDIA_PERMISSION = 999;
    private ChatDetailFragment fragmentUserDetail;
    private View done;

    @Override
    void userAdded(User valueUser) {
        //doNothing
    }

    @Override
    void groupAdded(Group valueGroup) {
        //doNothing
    }

    @Override
    void userUpdated(User valueUser) {
        if (user != null && user.getId().equals(valueUser.getId())) {
            valueUser.setNameInPhone(user.getNameInPhone());
            user = valueUser;
            setUserData();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_DATA_USER, user);
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    @Override
    void groupUpdated(Group valueGroup) {
        if (group != null && group.getId().equals(valueGroup.getId())) {
            group = valueGroup;
            if (fragmentUserDetail != null) {
                fragmentUserDetail.notifyGroupUpdated(group);
            }
            if (!group.getUserIds().contains(new MyString(userMe.getId()))) {
                userStatus.setText("You have been removed from this group..!!");
                userName.setEnabled(false);
                userStatus.setEnabled(false);
                done.setVisibility(View.GONE);
            } else {
                setUserData();
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_DATA_GROUP, group);
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        userDetailsSummaryContainer = findViewById(R.id.userDetailsSummaryContainer);
        pickImage = findViewById(R.id.pickImage);
        setSupportActionBar(((Toolbar) findViewById(R.id.toolbar)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);
        userImage = findViewById(R.id.expandedImage);
        userName = findViewById(R.id.user_name);
        userStatus = findViewById(R.id.emotion);
        done = findViewById(R.id.done);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_DATA_USER)) {
            user = intent.getParcelableExtra(EXTRA_DATA_USER);
        } else if (intent.hasExtra(EXTRA_DATA_GROUP)) {
            group = intent.getParcelableExtra(EXTRA_DATA_GROUP);
        } else {
            finish();
        }
        handler = new Handler();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.closeKeyboard(ChatDetailActivity.this, view);
                updateGroupNameAndStatus(userName.getText().toString().trim(), userStatus.getText().toString().trim());
            }
        });
        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> permissions = Helper.mediaPermissions(ChatDetailActivity.this);
                if (permissions.isEmpty()) {
                    ImagePicker.create(ChatDetailActivity.this)
                            .folderMode(true)
                            .showCamera(true)
                            .theme(R.style.AppTheme)
                            .single()
                            .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
                } else {
                    ActivityCompat.requestPermissions(ChatDetailActivity.this, permissions.toArray(new String[permissions.size()]), REQUEST_CODE_MEDIA_PERMISSION);
                }
            }
        });

        setupViews();
        getMediaInfo();
        loadFragment(TAG_DETAIL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_MEDIA_PERMISSION:
                if (Helper.mediaPermissions(this).isEmpty()) {
                    ImagePicker.create(this)
                            .folderMode(true)
                            .showCamera(true)
                            .theme(R.style.AppTheme)
                            .single()
                            .returnAfterFirst(true).start(REQUEST_CODE_PICKER);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    String groupImagePath = images.get(0).getPath();
                    Glide.with(this).load(groupImagePath).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder).optionalCenterCrop()).into(userImage);

                    File fileToUpload = new File(groupImagePath);
                    fileToUpload = ImageCompressorUtil.compressImage(this, fileToUpload);
                    userImageUploadTask(fileToUpload, AttachmentTypes.IMAGE, null);
                }
                break;
        }
    }

    private void userImageUploadTask(final File fileToUpload, @AttachmentTypes.AttachmentType final int attachmentType, final Attachment attachment) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(Helper.REF_STORAGE)
                .child(getString(R.string.app_name))
                .child("ProfileImage")
                .child(group.getId());
        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(fileToUpload));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageMetadata storageMetadata = taskSnapshot.getMetadata();
                group.setImage(storageMetadata.getDownloadUrl().toString());
                groupRef.child(group.getId()).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChatDetailActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatDetailActivity.this, "Unable to upload image.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateGroupNameAndStatus(String updatedName, String updatedStatus) {
        if (TextUtils.isEmpty(updatedName)) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(updatedStatus)) {
            Toast.makeText(this, "Status cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!group.getName().equals(updatedName) || !group.getStatus().equals(updatedStatus)) {
            group.setName(updatedName);
            group.setStatus(updatedStatus);
            groupRef.child(group.getId()).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ChatDetailActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getMediaInfo() {
        String myId = userMe.getId();
        Chat query = Helper.getChat(rChatDb, myId, user != null ? user.getId() : group.getId()).notEqualTo("messages.attachmentType", 6).findFirst();

        mediaMessages = new ArrayList<>();
        if (query != null) {
            for (Message m : query.getMessages()) {
                if (m.getAttachmentType() == AttachmentTypes.AUDIO
                        ||
                        m.getAttachmentType() == AttachmentTypes.IMAGE
                        ||
                        m.getAttachmentType() == AttachmentTypes.VIDEO
                        ||
                        m.getAttachmentType() == AttachmentTypes.DOCUMENT) {
                    if (m.getAttachmentType() != AttachmentTypes.IMAGE && !new File(Environment.getExternalStorageDirectory() + "/"
                            +
                            getString(R.string.app_name) + "/" + AttachmentTypes.getTypeName(m.getAttachmentType()) + (myId.equals(m.getSenderId()) ? "/.sent/" : "")
                            , m.getAttachment().getName()).exists()) {
                        continue;
                    }
                    mediaMessages.add(m);
                }
            }
        }
    }

    private void setupViews() {
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                setAppBarOffset(GeneralUtils.getDisplayMetrics().widthPixels / 2);
            }
        });

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(user != null ? user.getNameToDisplay() : group.getName());
                    isShow = true;
                } else if (isShow) {
                    userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        userName.setText(user != null ? user.getNameToDisplay() : group.getName());
        setUserData();
    }

    private void setUserData() {
        if (user != null) {
            userName.setCompoundDrawablesWithIntrinsicBounds(user.isOnline() ? R.drawable.ring_green : 0, 0, 0, 0);
        }
        userStatus.setText(user != null ? user.getStatus() : group.getStatus());
        Glide.with(this).load(user != null ? user.getImage() : group.getImage()).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);

        if (group != null) {
            userName.setEnabled(true);
            userStatus.setEnabled(true);
            done.setVisibility(View.VISIBLE);
            pickImage.setVisibility(View.VISIBLE);
        } else {
            userName.setEnabled(false);
            userStatus.setEnabled(false);
            done.setVisibility(View.GONE);
            pickImage.setVisibility(View.GONE);
        }
    }

    private void loadFragment(final String fragmentTag) {
        if (getSupportFragmentManager().findFragmentByTag(fragmentTag) != null)
            return;

        handler.post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = null;
                switch (fragmentTag) {
                    case TAG_DETAIL:
                        if (user != null)
                            fragmentUserDetail = ChatDetailFragment.newInstance(user);
                        else
                            fragmentUserDetail = ChatDetailFragment.newInstance(group);
                        fragment = fragmentUserDetail;
                        break;
                    case TAG_MEDIA:
                        fragment = new UserMediaFragment();
                        break;
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frameLayout, fragment, fragmentTag);
                if (fragmentTag.equals(TAG_MEDIA)) {
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().findFragmentByTag(TAG_DETAIL) == null)
//            loadFragment(TAG_DETAIL);
//        else
//            super.onBackPressed();
//    }

    private void setAppBarOffset(int offsetPx) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, offsetPx, new int[]{0, 0});
    }

    @Override
    public void getAttachments() {
        ChatDetailFragment fragment = ((ChatDetailFragment) getSupportFragmentManager().findFragmentByTag(TAG_DETAIL));
        if (fragment != null) {
            fragment.setupMediaSummary(mediaMessages);
        }
    }

    @Override
    public ArrayList<Message> getAttachments(int tabPos) {
        if (getSupportFragmentManager().findFragmentByTag(TAG_MEDIA) != null) {
            ArrayList<Message> toReturn = new ArrayList<>();
            switch (tabPos) {
                case 0:
                    for (Message msg : mediaMessages)
                        if (msg.getAttachmentType() == AttachmentTypes.IMAGE || msg.getAttachmentType() == AttachmentTypes.VIDEO)
                            toReturn.add(msg);
                    break;
                case 1:
                    for (Message msg : mediaMessages)
                        if (msg.getAttachmentType() == AttachmentTypes.AUDIO)
                            toReturn.add(msg);
                    break;
                case 2:
                    for (Message msg : mediaMessages)
                        if (msg.getAttachmentType() == AttachmentTypes.DOCUMENT)
                            toReturn.add(msg);
                    break;
            }
            return toReturn;
        } else
            return null;
    }

    @Override
    public void switchToMediaFragment() {
        appBarLayout.setExpanded(false, true);
        loadFragment(TAG_MEDIA);
    }

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtra(EXTRA_DATA_USER, user);
        return intent;
    }

    public static Intent newIntent(Context context, Group group) {
        Intent intent = new Intent(context, ChatDetailActivity.class);
        intent.putExtra(EXTRA_DATA_GROUP, group);
        return intent;
    }
}
