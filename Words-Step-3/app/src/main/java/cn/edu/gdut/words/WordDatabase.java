package cn.edu.gdut.words;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/*
下面的代码定义了一个 AppDatabase 类来保存数据库。AppDatabase 定义数据库配置，并作为应用程序对持久化数据的主要访问点。
数据库类必须满足以下条件:
                        1)该类必须使用 @Database 注释，包括一个列出与数据库关联的所有数据实体的 entities
                        2)该类必须是扩展 RoomDatabase 的抽象类
                        3)对于与数据库关联的每个 DAO 类，数据库类必须定义一个具有零个参数的抽象方法，并返回 DAO 类的一个实例

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
 */
//版本迁移保证原有数据不丢失
//singleton
@Database(entities = {Word.class}, version = 4, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;

    // synchronized 强化singleton强度
    // 如果有多个线程的客户端同时申请INSTANCE,保证不会同时去碰撞，不会冲突，采用排队的机制，保证只有一个INSTANCE生成
    static synchronized WordDatabase getDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
//                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_3_4)
                    .build();
        }
        return INSTANCE;
    }

    public abstract WordDao WordDao();

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN foo_data INTEGER NOT NULL DEFAULT 1");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE word_temp(id INTEGER PRIMARY KEY NOT NULL,english_word TEXT," +
                    "chinese_meaning TEXT)");
            database.execSQL("INSERT INTO word_temp(id,english_word,chinese_meaning) " +
                    "SELECT id,english_word,Chinese_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN chinese_invisible INTEGER NOT NULL DEFAULT 1");
        }
    };
}
