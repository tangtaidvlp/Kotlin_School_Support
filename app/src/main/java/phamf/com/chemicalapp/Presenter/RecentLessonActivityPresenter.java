package phamf.com.chemicalapp.Presenter;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import io.realm.RealmList;
import phamf.com.chemicalapp.Abstraction.AbstractClass.Presenter;
import phamf.com.chemicalapp.Abstraction.Interface.IRecentLessonActivity;
import phamf.com.chemicalapp.Database.OfflineDatabaseManager;
import phamf.com.chemicalapp.Manager.RecentLearningLessonDataManager;
import phamf.com.chemicalapp.RO_Model.RO_Lesson;
import phamf.com.chemicalapp.RO_Model.Recent_LearningLessons;
import phamf.com.chemicalapp.RecentLessonsActivity;


/**
 * @see RecentLessonsActivity
 */
public class RecentLessonActivityPresenter extends Presenter<RecentLessonsActivity> implements IRecentLessonActivity {

    private DataLoadListener onDataLoadListener;

    OfflineDatabaseManager offline_DBManager;

    RecentLearningLessonDataManager recentLearningLessonDataManager;


    public RecentLessonActivityPresenter(@NonNull RecentLessonsActivity view) {
        super(view);
        offline_DBManager = new OfflineDatabaseManager(view);
    }

    public void loadData() {
        recentLearningLessonDataManager = new RecentLearningLessonDataManager(offline_DBManager);
        recentLearningLessonDataManager.getData(recent_Ces -> {
            onDataLoadListener.onDataLoadSuccess(recent_Ces);
        });
    }

    public void setOnDataLoadListener(DataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
    }

    public void bringToTop(RO_Lesson item) {
        recentLearningLessonDataManager.bringToTop(item);
    }

    public void pushCachingData () {
        recentLearningLessonDataManager.updateDB();
    }


    public interface DataLoadListener {
        void onDataLoadSuccess(ArrayList<RO_Lesson> ro_lessons);
    }
}
