// This file was generated by PermissionsDispatcher. Do not modify!
package com.nazpowerchat.activities;

import android.support.v4.app.ActivityCompat;
import com.nazpowerchat.models.DownloadFileEvent;
import java.lang.Override;
import java.lang.String;
import java.lang.ref.WeakReference;
import permissions.dispatcher.GrantableRequest;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;

final class ChatActivityPermissionsDispatcher {
  private static final int REQUEST_OPENCONTACTPICKER = 0;

  private static final String[] PERMISSION_OPENCONTACTPICKER = new String[] {"android.permission.READ_CONTACTS","android.permission.WRITE_EXTERNAL_STORAGE"};

  private static final int REQUEST_OPENAUDIOPICKER = 1;

  private static final String[] PERMISSION_OPENAUDIOPICKER = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"};

  private static final int REQUEST_SELECTPIC = 2;

  private static final String[] PERMISSION_SELECTPIC = new String[] {"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};

  private static final int REQUEST_SELECTDOCUMENT = 3;

  private static final String[] PERMISSION_SELECTDOCUMENT = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"};

  private static final int REQUEST_DISPATCHTAKEPICTUREINTENT = 4;

  private static final String[] PERMISSION_DISPATCHTAKEPICTUREINTENT = new String[] {"android.permission.READ_EXTERNAL_STORAGE","android.permission.CAMERA"};

  private static final int REQUEST_DOWNLOADFILE = 5;

  private static final String[] PERMISSION_DOWNLOADFILE = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};

  private static GrantableRequest PENDING_DOWNLOADFILE;

  private ChatActivityPermissionsDispatcher() {
  }

  static void openContactPickerWithCheck(ChatActivity target) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_OPENCONTACTPICKER)) {
      target.openContactPicker();
    } else {
      ActivityCompat.requestPermissions(target, PERMISSION_OPENCONTACTPICKER, REQUEST_OPENCONTACTPICKER);
    }
  }

  static void openAudioPickerWithCheck(ChatActivity target) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_OPENAUDIOPICKER)) {
      target.openAudioPicker();
    } else {
      if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_OPENAUDIOPICKER)) {
        target.showRationaleForDownloadingFile(new OpenAudioPickerPermissionRequest(target));
      } else {
        ActivityCompat.requestPermissions(target, PERMISSION_OPENAUDIOPICKER, REQUEST_OPENAUDIOPICKER);
      }
    }
  }

  static void selectPicWithCheck(ChatActivity target) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_SELECTPIC)) {
      target.selectPic();
    } else {
      ActivityCompat.requestPermissions(target, PERMISSION_SELECTPIC, REQUEST_SELECTPIC);
    }
  }

  static void selectDocumentWithCheck(ChatActivity target) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_SELECTDOCUMENT)) {
      target.selectDocument();
    } else {
      if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SELECTDOCUMENT)) {
        target.showRationaleForDownloadingFile(new SelectDocumentPermissionRequest(target));
      } else {
        ActivityCompat.requestPermissions(target, PERMISSION_SELECTDOCUMENT, REQUEST_SELECTDOCUMENT);
      }
    }
  }

  static void dispatchTakePictureIntentWithCheck(ChatActivity target) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_DISPATCHTAKEPICTUREINTENT)) {
      target.dispatchTakePictureIntent();
    } else {
      ActivityCompat.requestPermissions(target, PERMISSION_DISPATCHTAKEPICTUREINTENT, REQUEST_DISPATCHTAKEPICTUREINTENT);
    }
  }

  static void downloadFileWithCheck(ChatActivity target, DownloadFileEvent downloadFileEvent) {
    if (PermissionUtils.hasSelfPermissions(target, PERMISSION_DOWNLOADFILE)) {
      target.downloadFile(downloadFileEvent);
    } else {
      PENDING_DOWNLOADFILE = new DownloadFilePermissionRequest(target, downloadFileEvent);
      ActivityCompat.requestPermissions(target, PERMISSION_DOWNLOADFILE, REQUEST_DOWNLOADFILE);
    }
  }

  static void onRequestPermissionsResult(ChatActivity target, int requestCode, int[] grantResults) {
    switch (requestCode) {
      case REQUEST_OPENCONTACTPICKER:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.openContactPicker();
      }
      break;
      case REQUEST_OPENAUDIOPICKER:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.openAudioPicker();
      } else {
        if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_OPENAUDIOPICKER)) {
          target.showNeverAskForDownloadingFile();
        } else {
          target.showDeniedForDownloadingFile();
        }
      }
      break;
      case REQUEST_SELECTPIC:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.selectPic();
      }
      break;
      case REQUEST_SELECTDOCUMENT:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.selectDocument();
      } else {
        if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SELECTDOCUMENT)) {
          target.showNeverAskForDownloadingFile();
        } else {
          target.showDeniedForDownloadingFile();
        }
      }
      break;
      case REQUEST_DISPATCHTAKEPICTUREINTENT:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.dispatchTakePictureIntent();
      }
      break;
      case REQUEST_DOWNLOADFILE:
      if (PermissionUtils.verifyPermissions(grantResults)) {
        if (PENDING_DOWNLOADFILE != null) {
          PENDING_DOWNLOADFILE.grant();
        }
      }
      PENDING_DOWNLOADFILE = null;
      break;
      default:
      break;
    }
  }

  private static final class OpenAudioPickerPermissionRequest implements PermissionRequest {
    private final WeakReference<ChatActivity> weakTarget;

    private OpenAudioPickerPermissionRequest(ChatActivity target) {
      this.weakTarget = new WeakReference<ChatActivity>(target);
    }

    @Override
    public void proceed() {
      ChatActivity target = weakTarget.get();
      if (target == null) return;
      ActivityCompat.requestPermissions(target, PERMISSION_OPENAUDIOPICKER, REQUEST_OPENAUDIOPICKER);
    }

    @Override
    public void cancel() {
      ChatActivity target = weakTarget.get();
      if (target == null) return;
      target.showDeniedForDownloadingFile();
    }
  }

  private static final class SelectDocumentPermissionRequest implements PermissionRequest {
    private final WeakReference<ChatActivity> weakTarget;

    private SelectDocumentPermissionRequest(ChatActivity target) {
      this.weakTarget = new WeakReference<ChatActivity>(target);
    }

    @Override
    public void proceed() {
      ChatActivity target = weakTarget.get();
      if (target == null) return;
      ActivityCompat.requestPermissions(target, PERMISSION_SELECTDOCUMENT, REQUEST_SELECTDOCUMENT);
    }

    @Override
    public void cancel() {
      ChatActivity target = weakTarget.get();
      if (target == null) return;
      target.showDeniedForDownloadingFile();
    }
  }

  private static final class DownloadFilePermissionRequest implements GrantableRequest {
    private final WeakReference<ChatActivity> weakTarget;

    private final DownloadFileEvent downloadFileEvent;

    private DownloadFilePermissionRequest(ChatActivity target,
        DownloadFileEvent downloadFileEvent) {
      this.weakTarget = new WeakReference<ChatActivity>(target);
      this.downloadFileEvent = downloadFileEvent;
    }

    @Override
    public void proceed() {
      ChatActivity target = weakTarget.get();
      if (target == null) return;
      ActivityCompat.requestPermissions(target, PERMISSION_DOWNLOADFILE, REQUEST_DOWNLOADFILE);
    }

    @Override
    public void cancel() {
    }

    @Override
    public void grant() {
      ChatActivity target = weakTarget.get();
      if (target == null) return;
      target.downloadFile(downloadFileEvent);
    }
  }
}
