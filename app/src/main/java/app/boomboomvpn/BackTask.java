package app.boomboomvpn;

import android.content.Context;
import android.os.AsyncTask;

import app.boomboomvpn.core.ProfileManager;
import app.boomboomvpn.fragments.FeedbackFragment;

public class BackTask extends AsyncTask<Void,Void,Boolean> {
    Context c;

    public BackTask(Context context){
        c = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ProfileManager.mProfiles.clear();
        DataManager.Server_UUIDS = new String[DataManager.Server_IPS.length];
        for ( int j=0; j<DataManager.Server_IPS.length; j++) {
            FeedbackFragment.recordProfileAdd(c);
            String s = ProfileManager.create(DataManager.Server_IPS[j]).getUUID().toString();
            DataManager.Server_UUIDS[j] = s;
        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

//        if(aBoolean)
//            MainActivity.mainActivity.StopWelcome();
    }
}
