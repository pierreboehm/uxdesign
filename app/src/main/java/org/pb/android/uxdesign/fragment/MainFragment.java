package org.pb.android.uxdesign.fragment;

import androidx.fragment.app.Fragment;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.pb.android.uxdesign.Demonstrator;
import org.pb.android.uxdesign.R;

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @ViewById(R.id.hpodView)
    HpodView hpodView;

    @Bean
    Demonstrator demonstrator;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
