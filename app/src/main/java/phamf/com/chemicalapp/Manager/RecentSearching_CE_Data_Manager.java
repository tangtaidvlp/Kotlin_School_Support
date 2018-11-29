package phamf.com.chemicalapp.Manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.RO_Model.RO_ChemicalEquation;
import phamf.com.chemicalapp.RO_Model.Recent_SearchingCEs;

public class RecentSearching_CE_Data_Manager {

    private Recent_SearchingCEs recent_searchingCEs;

    private RealmList<RO_ChemicalEquation> recent_CEs;

    private OfflineDatabaseManager offline_DBManager;

    public RecentSearching_CE_Data_Manager(OfflineDatabaseManager offline_DBManager) {
        this.offline_DBManager = offline_DBManager;


        // Afraid of this code block is executed after the one beneath
        if (offline_DBManager.readOneOf(Recent_SearchingCEs.class) == null) {
            Recent_SearchingCEs recent_searchingCEs = new Recent_SearchingCEs();
            offline_DBManager.addOrUpdateDataOf(Recent_SearchingCEs.class, recent_searchingCEs);
        }

    }


    /**
     *  This function works as follow:
     *      Fisrt if recent ce data is null, add all chemical equation of database into it
     *      Then in uses process, the order of data of recent ce data would be changed
     *      when user search a chemical equation. Then when user exits app, that order will be saved in database
     *      The next time user uses this app. The app load that order and bind to search list
     */

    public void getData (OnGetDataSuccess onGetDataSuccess) {
        recent_searchingCEs = offline_DBManager.readAsyncOneOf(Recent_SearchingCEs.class
                , recent_searchingCEs ->
                {
                    recent_CEs = recent_searchingCEs.getRecent_searching_ces();

                    // Check if recen_Ces null, then add all default Chemical equations
                    if (recent_CEs.size() == 0) {
                        offline_DBManager.beginTransaction();
                        recent_CEs.addAll(offline_DBManager.readAllDataOf(RO_ChemicalEquation.class));
                        offline_DBManager.commitTransaction();
                    }

                    ArrayList<RO_ChemicalEquation> data = new ArrayList<>();
                    data.addAll(recent_CEs);

                    // This function is implemented by MainActivityPresenter.java
                    onGetDataSuccess.onLoadSuccess(data);
                });
    }

    public void bringToTop (RO_ChemicalEquation ro_ce) {

        if (recent_CEs.contains(ro_ce) && !recent_CEs.get(0).equals(ro_ce)) {

            offline_DBManager.beginTransaction();

            recent_CEs.remove(ro_ce);
            recent_CEs.add(0, ro_ce);

            offline_DBManager.commitTransaction();

        } else {
            Log.e("Error happened", "ro_ce not found in list, MainActivityPresenter.java, line 139 "  + recent_searchingCEs.getRecent_searching_ces().size() );
        }

    }

    public void updateDB () {
        offline_DBManager.addOrUpdateDataOf(Recent_SearchingCEs.class, recent_searchingCEs);
    }

    public interface OnGetDataSuccess {
        void onLoadSuccess (ArrayList<RO_ChemicalEquation> recent_Ces);
    }
}
