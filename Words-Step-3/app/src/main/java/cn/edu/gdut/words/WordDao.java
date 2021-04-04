package cn.edu.gdut.words;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao  //DataBase Access Option
public interface WordDao {
    @Insert
    void insertWords(Word...words);

    @Delete
    void deleteWords(Word...words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordsLive();

    @Query("SELECT * FROM WORD WHERE english_word Like :pattern ORDER BY ID DESC")
    LiveData<List<Word>> searchWordWithPattern(String pattern);

    @Update
    void updateWords(Word...words);

}
