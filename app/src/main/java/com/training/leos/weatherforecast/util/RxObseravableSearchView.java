package com.training.leos.weatherforecast.util;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Leo on 12/23/2017.
 */

public class RxObseravableSearchView {
    public static Observable<String> fromView(MaterialSearchView searchView){

        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                subject.onNext(newText);
                return true;
            }
        });
        return subject;
    }
}
