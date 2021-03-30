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

import cn.edu.gdut.roombasic.databinding.FragmentInsertWordBinding;

public class InsertWordFragment extends Fragment {

    public InsertWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WordViewModel wordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(WordViewModel.class);
        final FragmentInsertWordBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insert_word, container, false);
        //方法一 :设置按钮不可用
        binding.button.setEnabled(false);
        if (!binding.textEnglishWord.getText().toString().equals("") && !binding.textChineseMeaning.getText().toString().equals("")) {
            binding.button.setEnabled(true);
        }
        binding.button.setOnClickListener(v -> {
            wordViewModel.insertWords(new Word(binding.textEnglishWord.getText().toString(), binding.textChineseMeaning.getText().toString()));
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_insertWordFragment_to_homeFragment);
        });
        //方法二：设置一个Toast提醒
//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.textEnglishWord.getText().toString().equals("") || binding.textChineseMeaning.getText().toString().equals("")) {
//                    Toast.makeText(requireActivity(), "请正确输入", Toast.LENGTH_SHORT).show();
//                } else {
//                    wordViewModel.insertWords(new Word(binding.textEnglishWord.getText().toString(), binding.textChineseMeaning.getText().toString()));
//                    NavController controller = Navigation.findNavController(v);
//                    controller.navigate(R.id.action_insertWordFragment_to_homeFragment);
//                }
//            }
//        });


        // Inflate the layout for this fragment
        binding.setLifecycleOwner(requireActivity());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}