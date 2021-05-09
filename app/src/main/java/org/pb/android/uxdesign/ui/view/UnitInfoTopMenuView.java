package org.pb.android.uxdesign.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewsById;
import org.pb.android.uxdesign.R;

import java.util.List;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_unit_info_menue_top)
public class UnitInfoTopMenuView extends RelativeLayout {

    @ViewsById({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5})
    List<ImageButton> buttonGroup1List;

    @ViewsById({R.id.btn_1_1, R.id.btn_1_2})
    List<ImageButton> buttonGroup2_1List;

    @ViewsById({R.id.btn_2_2})
    List<ImageButton> buttonGroup2_2List;

    @ViewsById({R.id.btn_4_2})
    List<ImageButton> buttonGroup2_4List;

    @ViewsById({R.id.btn_5_2})
    List<ImageButton> buttonGroup2_5List;

    public UnitInfoTopMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5})
    public void onButtonGroup1Click(ImageButton button) {
        performSelection(button);
    }

    @Click({R.id.btn_1_1, R.id.btn_1_2, R.id.btn_2_2, R.id.btn_4_2, R.id.btn_5_2})
    public void onButtonGroup2Click(ImageButton button) {
        performSelection(button);
    }

    private void performSelection(ImageButton button) {
        unselectAll(button.getId());
        button.setSelected(!button.isSelected());
    }

    private void unselectAll(int skipId) {
        unselect(buttonGroup1List, skipId);
        unselect(buttonGroup2_1List, skipId);
        unselect(buttonGroup2_2List, skipId);
        unselect(buttonGroup2_4List, skipId);
        unselect(buttonGroup2_5List, skipId);
    }

    private void unselect(List<ImageButton> imageButtonList, int skipId) {
        for (ImageButton imageButton : imageButtonList) {
            if (imageButton.getId() != skipId) {
                imageButton.setSelected(false);
            }
        }
    }
}
