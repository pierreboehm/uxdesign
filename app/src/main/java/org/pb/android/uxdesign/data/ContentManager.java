package org.pb.android.uxdesign.data;

import android.content.Context;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.ui.view.ContentItemKeyValueView;
import org.pb.android.uxdesign.ui.view.ContentItemKeyValueView_;
import org.pb.android.uxdesign.ui.view.ContentItemTextView;
import org.pb.android.uxdesign.ui.view.ContentItemTextView_;

@EBean
public class ContentManager {

    @RootContext
    Context context;

    @Bean
    Demonstrator demonstrator;

    public void setApplicationInfo(ContentProvider contentProvider) {
        ContentItemTextView contentItemText = ContentItemTextView_.build(context);
        contentItemText.setBoldText(R.string.application_info_headline);
        contentProvider.setContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setText(R.string.application_info_text_1);
        contentProvider.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setText(R.string.application_info_text_2);
        contentProvider.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setText(R.string.application_info_text_3);
        contentProvider.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setBoldText(R.string.application_info_text_4);
        contentProvider.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setText(R.string.application_info_text_5);
        contentProvider.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setText(R.string.application_info_text_6);
        contentProvider.addContent(contentItemText);

        contentItemText = ContentItemTextView_.build(context);
        contentItemText.setBoldText(R.string.application_info_text_7);
        contentProvider.addContent(contentItemText);
    }

    public void setUserInfo(ContentProvider contentProvider) {
        ContentItemKeyValueView contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_1, Integer.toString(demonstrator.getUserListCount()));
        contentProvider.setContent(contentItemKeyValue);

        CurrentUser currentUser = demonstrator.getCurrentUser();

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_2, null);
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_3, currentUser.getUserData().getName());
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_4, currentUser.getUserData().getId());
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_5, currentUser.getUserData().getProfession());
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_6, currentUser.getUserData().getCountry());
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.user_info_key_7, currentUser.getUserData().getLocality());
        contentProvider.addContent(contentItemKeyValue);

        ContentItemTextView contentItemText = ContentItemTextView_.build(context);
        contentItemText.setText(R.string.application_info_text_4);
        contentProvider.addContent(contentItemText);
    }

    public void setPowerInfo(ContentProvider contentProvider) {
        PowerManagerInfo powerManagerInfo = demonstrator.getPowerManagerInfo();

        ContentItemKeyValueView contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_1, powerManagerInfo.isPluggedIn()
                ? context.getString(R.string.yes)
                : context.getString(R.string.no));
        contentProvider.setContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_2,
                powerManagerInfo.getVoltageInMilliVolt() + context.getString(R.string.unit_voltage_milli));
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_3, powerManagerInfo.isCharging()
                ? context.getString(R.string.yes)
                : context.getString(R.string.no));
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_4,
                powerManagerInfo.getBatteryStatusInPercent() + context.getString(R.string.unit_percent));
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_5,
                powerManagerInfo.getCurrentConsumptionInMilliAmpere() + context.getString(R.string.unit_current_milli));
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_6,
                powerManagerInfo.getTemperatureInCelsius() + context.getString(R.string.unit_temperature));
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_7, powerManagerInfo.getBatteryTyp());
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_8, powerManagerInfo.getBatteryModel());
        contentProvider.addContent(contentItemKeyValue);

        contentItemKeyValue = ContentItemKeyValueView_.build(context);
        contentItemKeyValue.bind(R.string.power_info_key_9,
                (int) powerManagerInfo.getBatteryCapacity() + context.getString(R.string.unit_capacity_milli));
        contentProvider.addContent(contentItemKeyValue);
    }
}