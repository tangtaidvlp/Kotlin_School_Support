package phamf.com.chemicalapp.Presenter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.ILessonMenuActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.LessonMenuActivity;
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager;
import phamf.com.chemicalapp.RO_Model.RO_Chapter;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons;
import phamf.com.chemicalapp.Supporter.ROConverter;

import static phamf.com.chemicalapp.Supporter.ROConverter.toRO_Chapters_ArrayList;

/**
 *@see LessonMenuActivity
 */
public class LessonMenuActivityPresenter extends Presenter<LessonMenuActivity> implements ILessonMenuActivity.Presenter{

    private DataLoadListener onDataLoadListener;

    private OfflineDatabaseManager offlineDB_manager;

    private RecentLearningLessonDataManager recentLearningLessonDataManager;

    private RealmResults<RO_Chapter> data;

    public LessonMenuActivityPresenter(@NonNull LessonMenuActivity view) {
        super(view);
    }

    public void loadData() {

        offlineDB_manager = new OfflineDatabaseManager(context);

        recentLearningLessonDataManager = new RecentLearningLessonDataManager(offlineDB_manager);
        // Call this function to create some data for RecentLearningLessonDataManager class,
        // look inside this method to get more info
        recentLearningLessonDataManager.getData(null);
        data = offlineDB_manager.readAsyncAllDataOf(RO_Chapter.class, ro_chapters ->
                {
                    onDataLoadListener.onDataLoadedSuccess(toRO_Chapters_ArrayList(ro_chapters));
                }
        );

    }

    public void pushCachingDataToDB () {
        recentLearningLessonDataManager.updateDB();
    }

    public void clearAllListenerToDatabase () {
        data.removeAllChangeListeners();
    }

    /** Bring this lesson to top of recent learning lesson list in realm database  **/
    public void bringToTop (RO_Lesson ro_lesson) {
        recentLearningLessonDataManager.bringToTop(ro_lesson);
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    /** @see LessonMenuActivity
     */
    public interface DataLoadListener {

        void onDataLoadedSuccess (ArrayList<RO_Chapter> ro_chapters);

    }

}


