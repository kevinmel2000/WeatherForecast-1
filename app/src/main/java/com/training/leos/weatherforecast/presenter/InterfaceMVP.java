package com.training.leos.weatherforecast.presenter;

import com.training.leos.weatherforecast.model.Currently;
import com.training.leos.weatherforecast.model.Daily;

import java.util.ArrayList;

/**
 * Created by Leo on 23/11/2017.
 */

public interface InterfaceMVP {
    // MainPresenter -> MainActivity
    interface MvpMainActivity{
        void showToast(String msg);
        void showCurrentWeather(Currently currently);
        void showDailyWeather(ArrayList<Daily> dailies);
    }

    //MainActivity -> MainPresenter
    interface MvpMainPresenter{
        void onInitialize();
        void onRequestFailure(String msg);
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
