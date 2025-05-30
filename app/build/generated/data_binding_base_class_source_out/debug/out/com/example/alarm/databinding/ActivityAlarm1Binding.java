// Generated by view binder compiler. Do not edit!
package com.example.alarm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.alarm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAlarm1Binding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final BottomNavigationView bottomNavigationview;

  @NonNull
  public final Button cancelalarm;

  @NonNull
  public final TextView selecttime;

  @NonNull
  public final Button setalarm;

  private ActivityAlarm1Binding(@NonNull RelativeLayout rootView,
      @NonNull BottomNavigationView bottomNavigationview, @NonNull Button cancelalarm,
      @NonNull TextView selecttime, @NonNull Button setalarm) {
    this.rootView = rootView;
    this.bottomNavigationview = bottomNavigationview;
    this.cancelalarm = cancelalarm;
    this.selecttime = selecttime;
    this.setalarm = setalarm;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAlarm1Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAlarm1Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_alarm1, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAlarm1Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigationview;
      BottomNavigationView bottomNavigationview = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationview == null) {
        break missingId;
      }

      id = R.id.cancelalarm;
      Button cancelalarm = ViewBindings.findChildViewById(rootView, id);
      if (cancelalarm == null) {
        break missingId;
      }

      id = R.id.selecttime;
      TextView selecttime = ViewBindings.findChildViewById(rootView, id);
      if (selecttime == null) {
        break missingId;
      }

      id = R.id.setalarm;
      Button setalarm = ViewBindings.findChildViewById(rootView, id);
      if (setalarm == null) {
        break missingId;
      }

      return new ActivityAlarm1Binding((RelativeLayout) rootView, bottomNavigationview, cancelalarm,
          selecttime, setalarm);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
