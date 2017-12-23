package com.training.leos.weatherforecast;

import com.training.leos.weatherforecast.data.model.Currently;
import com.training.leos.weatherforecast.data.model.Daily;
import com.training.leos.weatherforecast.data.model.Forecast;

import java.util.ArrayList;

/**
 * Created by Leo on 23/11/2017.
 */

public interface MainContract {
    // Presenter -> View
    interface View {
        //show message toast
        void showToast(String msg);
        void showCurrentWeather(Currently currently);
        void showDailyWeather(Daily daily);

        void showRecomendationResult(ArrayList<String> location);
    }

    //View -> Presenter
    interface Presenter {
        void onInitialize();
        void onShowCurrently(Forecast forecast);
        void onShowDaily(Forecast forecast);
        void onRequestFailure(String msg);
        void onCheckRecomendation(String query);
    }










    // Model -> Presenter
    interface RequiredPresenterOps {
        //void onNotaInserida(Nota novaNota);
        //void onNotaRemovida(Nota notaRemovida);
        void onError(String errorMsg);
        // Any other returning operation Model -> Presenter
    }

    // Presenter -> Model

    interface ModelOps {
        //void insereNota(Nota nota);
        //void removeNota(Nota nota);
        void onDestroy();
        // Any other data operation
    }
}
