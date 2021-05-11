package org.pb.android.uxdesign.data;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public abstract class ContentProvider extends RelativeLayout {

    public ContentProvider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void clearContent();

    public abstract void setContent(View view);

    public abstract void addContent(View view);

}
