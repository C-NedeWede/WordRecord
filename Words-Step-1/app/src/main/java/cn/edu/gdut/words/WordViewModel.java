package cn.edu.gdut.words;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
/*
* ViewModel is part of the Lifecycle library which was designed
* 1) to help you solve common Android Lifecycle challenges
* 2) to make your apps more maintainable and testable.  //更具可维护性和可测试性。

* A ViewModel holds your app's UI data in a lifecycle conscious way that survives configuration changes.
* Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle: // 单一职责原则
* Your Activities and Fragments are responsible for drawing data to the screen, // Activities和 Fragment负责将数据绘制到屏幕上
* while your ViewModel can take care of holding and processing all the data needed for the UI. // ViewModel可以保存和处理 UI所需的所有数据，管理界面数据，通过仓库类对数据进行获取和存储

Making a ViewModel is simple:
* Adding the Lifecycle library to your build.gradle file
* Extending the ViewModel class
* Use ViewModelProviders to associate your ViewModel with your UI controller

* Then you can move all of your UI related data into your new ViewModel.
* If you need to customize ViewModel construction, you can create a ViewModelProvider.NewInstanceFactory.
* ViewModels also work great with the LiveData class to create reactive UIs!

* A few words of warning: don't store Contexts in ViewModels and don't confuse ViewModels with the onSaveInstanceState method.
*/
public class WordViewModel extends AndroidViewModel {
    private final WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return wordRepository.getAllWordsLive();
    }

    void insertWords(Word... words) {
        wordRepository.insertWords(words);
    }

    void deleteWords(Word... words) {
        wordRepository.deleteWords(words);
    }

    void deleteAllWords() {
        wordRepository.deleteAllWords();
    }

    void updateWords(Word... words) {
        wordRepository.updateWords(words);
    }


    //Async Task
    //抽象类
    //生成他的子类
    // static 静态类  否则出现内存泄漏
}
