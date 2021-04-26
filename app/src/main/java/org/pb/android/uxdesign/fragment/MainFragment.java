package org.pb.android.uxdesign.fragment;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.data.Demonstrator;
import org.pb.android.uxdesign.R;
import org.pb.android.uxdesign.data.user.CurrentUser;
import org.pb.android.uxdesign.ui.view.HpodView;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @ViewById(R.id.hpodView)
    HpodView hpodView;

    @Bean
    Demonstrator demonstrator;

    @AfterViews
    public void initViews() {
        CurrentUser currentUser = demonstrator.getCurrentUser();
        hpodView.setupCurrentUser(currentUser);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
