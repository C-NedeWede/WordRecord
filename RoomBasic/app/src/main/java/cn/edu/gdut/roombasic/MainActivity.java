package cn.edu.gdut.roombasic;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//AsyncTask
//ViewModel LiveData
//Repository
public class MainActivity extends AppCompatActivity {

    //    Button buttonInsert, buttonClear;
    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,controller);
//        aSwitch = findViewById(R.id.switch1);
//        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                recyclerView.setAdapter(myAdapter2);
//            } else {
//                recyclerView.setAdapter(myAdapter1);
//            }
//        });





//        buttonInsert = findViewById(R.id.buttonInsert);
//        buttonInsert.setOnClickListener(v -> {
//            String[] English = {
//                    "manipulate",
//                    "common",
//                    "contact lists",
//                    "playlists",
//                    "saved games",
//                    "photo directories",
//                    "dictionaries",
//                    "shopping lists",
//                    "indexes of documents",
//                    "demonstrates",
//                    "sophisticated"
//            };
//            String[] Chinese = {
//                    "操作",
//                    "常见",
//                    "联系人列表",
//                    "播放列表",
//                    "保存的游戏",
//                    "图片目录",
//                    "字典",
//                    "购物列表",
//                    "文档索引",
//                    "证明",
//                    "复杂的"
//
//            };
//            for (int i = 0; i < English.length; i++) {
//                wordViewModel.insertWords(new Word(English[i], Chinese[i]));
//            }
//        });

//        buttonDelete = findViewById(R.id.buttonDelete);
//        buttonDelete.setOnClickListener(v -> {
//            //wordDao.deleteWords(wordDao.getAllWords().get(1));
//            Word englishWord = new Word("water", "水");
//            englishWord.setId(10);
//            wordViewModel.deleteWords(englishWord);
//        });

//        buttonClear = findViewById(R.id.buttonClear);
//        buttonClear.setOnClickListener(v -> wordViewModel.deleteAllWords());

//        buttonUpdate = findViewById(R.id.buttonUpdate);
//        buttonUpdate.setOnClickListener(v -> {
//            Word englishWord = new Word("cola", "可乐");
//            englishWord.setId(6);
//            wordViewModel.updateWords(englishWord);
//        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        if (controller.getCurrentDestination().getId()==R.id.insertWordFragment){
            controller.navigate(R.id.action_insertWordFragment_to_homeFragment);
        }else{
            finish();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}