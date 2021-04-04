package cn.edu.gdut.words;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.media.MediaCodec.MetricsConstants.MODE;

public class HomeFragment extends Fragment {

    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter1, myAdapter2;
    private FloatingActionButton floatingActionButton;
    private LiveData<List<Word>> filteredWords;
    private static final String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USE_CARD_VIEW = "is_use_card_view";

    public HomeFragment() {
        // 默认是 false
        setHasOptionsMenu(true);
        // Required empty public constructor
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearData:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("是否清空数据");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wordViewModel.deleteAllWords();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            case R.id.switchView:
                SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                boolean viewType = shp.getBoolean(IS_USE_CARD_VIEW,false);
                SharedPreferences.Editor editor = shp.edit();
                if (viewType){
                    recyclerView.setAdapter(myAdapter1);
                    editor.putBoolean(IS_USE_CARD_VIEW,false);
                }else {
                    recyclerView.setAdapter(myAdapter2);
                    editor.putBoolean(IS_USE_CARD_VIEW,true);
                }
                editor.apply();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(780);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                filteredWords = wordViewModel.searchWordWithPattern(pattern);
                // 将原有的观察移除，防止喷撞。
                filteredWords.removeObservers(requireActivity());
                filteredWords.observe(requireActivity(), words -> {
                    int tmp = myAdapter1.getItemCount();
                    myAdapter1.setAllWords(words);
                    myAdapter2.setAllWords(words);
                    if (tmp != words.size()) {
                        myAdapter1.notifyDataSetChanged();
                        myAdapter2.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter1 = new MyAdapter(wordViewModel, false);
        myAdapter2 = new MyAdapter(wordViewModel, true);
        SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
        boolean viewType = shp.getBoolean(IS_USE_CARD_VIEW,false);
        if (viewType){
            recyclerView.setAdapter(myAdapter2);
        }else {
            recyclerView.setAdapter(myAdapter1);
        }

        wordViewModel.getAllWordsLive().observe(requireActivity(), words -> {
            int tmp = myAdapter1.getItemCount();
            myAdapter1.setAllWords(words);
            myAdapter2.setAllWords(words);
            if (tmp != words.size()) {
                myAdapter1.notifyDataSetChanged();
                myAdapter2.notifyDataSetChanged();
            }
        });

        floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_homeFragment_to_insertWordFragment);
        });
    }
}