package com.tripdazzle.daycation.ui.splash;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tripdazzle.daycation.DataModel;
import com.tripdazzle.daycation.R;

public class SplashFragment extends Fragment {

    private DataModel mModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        final TextView textView = root.findViewById(R.id.splash_text_logo);

        new InitializeModelsTask(this.getContext()).execute(mModel);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataModel.DataManager) {
            mModel = ((DataModel.DataManager) context).getModel();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataModel.DataManager");
        }
    }

    private class InitializeModelsTask extends AsyncTask<DataModel, Void, Boolean> {
        /** Application Context*/
        private Context context;

        private InitializeModelsTask(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(DataModel ... models) {
            if(models.length > 1){
                return false;
            }
            models[0].initialize(context);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // Do something with result?
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(SplashFragmentDirections.actionNavHomeToSplash());
        }
    }
}
