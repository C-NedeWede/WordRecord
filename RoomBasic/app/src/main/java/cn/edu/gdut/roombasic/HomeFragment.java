package cn.edu.gdut.roombasic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.gdut.roombasic.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WordViewModel wordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(WordViewModel.class);
        final FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        RecyclerView recyclerView = binding.recyclerView.findViewById(R.id.recyclerView);
        MyAdapter myAdapter = new MyAdapter(wordViewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(myAdapter);

        wordViewModel.getAllWordsLive().observe(requireActivity(), words -> {
            int tmp = myAdapter.getItemCount();
            myAdapter.setAllWords(words);
            if (tmp != words.size()) {
                myAdapter.notifyDataSetChanged();
            }
        });

        binding.floatingActionButtonInsert.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_homeFragment_to_insertWordFragment);
        });

        binding.setLifecycleOwner(requireActivity());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}