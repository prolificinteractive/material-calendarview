package com.prolificinteractive.materialcalendarview;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * With this in place, we don't have to have the user install 310Abp by himself.
 */
public final class MaterialCalendarViewInitProvider extends ContentProvider {

  private static final String MCV_AUTHORITY =
      "com.prolificinteractive.materialcalendarview.materialcalendarviewinitprovider";

  public MaterialCalendarViewInitProvider() { }

  @Override public boolean onCreate() {
    // The interesting piece here.
    AndroidThreeTen.init(getContext());
    return true;
  }

  @Override public void attachInfo(final Context context, final ProviderInfo providerInfo) {
    if (providerInfo == null) {
      throw new NullPointerException("MaterialCalendarViewInitProvider ProviderInfo cannot be null.");
    }
    // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
    if (MCV_AUTHORITY.equals(providerInfo.authority)) {
      throw new IllegalStateException(
          "Incorrect provider authority in manifest. Most likely due to a "
              + "missing applicationId variable in application\'s build.gradle.");
    }
    super.attachInfo(context, providerInfo);
  }

  @Nullable @Override public Cursor query(
      @NonNull final Uri uri,
      @Nullable final String[] projection,
      @Nullable final String selection,
      @Nullable final String[] selectionArgs,
      @Nullable final String sortOrder) {
    return null;
  }

  @Nullable @Override public String getType(@NonNull final Uri uri) {
    return null;
  }

  @Nullable @Override public Uri insert(
      @NonNull final Uri uri,
      @Nullable final ContentValues values) {
    return null;
  }

  @Override public int delete(
      @NonNull final Uri uri,
      @Nullable final String selection,
      @Nullable final String[] selectionArgs) {
    return 0;
  }

  @Override public int update(
      @NonNull final Uri uri,
      @Nullable final ContentValues values,
      @Nullable final String selection,
      @Nullable final String[] selectionArgs) {
    return 0;
  }
}
