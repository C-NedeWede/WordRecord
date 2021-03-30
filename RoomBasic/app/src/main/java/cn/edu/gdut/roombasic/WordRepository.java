package cn.edu.gdut.roombasic;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//对数据的直接操作，数据中介，数据仓库
class WordRepository {
    private final LiveData<List<Word>> allWordsLive;
    private final WordDao wordDao;

    WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getDataBase(context.getApplicationContext());
        wordDao = wordDatabase.WordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    void insertWords(Word... words) {
        new InsertAsyncTask(wordDao).execute(words);
    }

    void deleteWords(Word... words) {
        new DeleteAsyncTask(wordDao).execute(words);
    }

    void deleteAllWords() {
        new DeleteAllAsyncTask(wordDao).execute();
    }

    void updateWords(Word... words) {
        new UpdateAsyncTask(wordDao).execute(words);
    }


    //Async Task
    //抽象类
    //生成他的子类
    // static 静态类  否则出现内存泄漏
    public static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private final WordDao wordDao;

        public InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {

        private final WordDao wordDao;

        public DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private final WordDao wordDao;

        public DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {

        private final WordDao wordDao;

        public UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }
}
