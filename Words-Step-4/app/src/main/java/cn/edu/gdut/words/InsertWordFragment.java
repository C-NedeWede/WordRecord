package cn.edu.gdut.words;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class InsertWordFragment extends Fragment {

    private WordViewModel wordViewModel;
    private Button button;
    private TextView editTextEnglishWord, editTextChineseMeaning;

    public InsertWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        final FragmentInsertWordBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insert_word, container, false);
//        //方法一 :设置按钮不可用
//        binding.button.setEnabled(false);
//        if (!binding.textEnglishWord.getText().toString().equals("") && !binding.textChineseMeaning.getText().toString().equals("")) {
//            binding.button.setEnabled(true);
//        }
//        binding.button.setOnClickListener(v -> {
//            wordViewModel.insertWords(new Word(binding.textEnglishWord.getText().toString(), binding.textChineseMeaning.getText().toString()));
//            NavController controller = Navigation.findNavController(v);
//            controller.navigate(R.id.action_insertWordFragment_to_homeFragment);
//        });
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
        return inflater.inflate(R.layout.fragment_insert_word, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = requireActivity();
        wordViewModel = new ViewModelProvider(activity, new ViewModelProvider.AndroidViewModelFactory(activity.getApplication())).get(WordViewModel.class);
        button = activity.findViewById(R.id.button);
        editTextChineseMeaning = activity.findViewById(R.id.editTextChineseMeaning);
        editTextEnglishWord = activity.findViewById(R.id.editTextEnglishWord);
        button.setEnabled(false);
        // 调整键盘
        editTextEnglishWord.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextEnglishWord, 0);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String english = editTextEnglishWord.getText().toString().trim();
                String chinese = editTextChineseMeaning.getText().toString().trim();
                button.setEnabled(!english.isEmpty() && !chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextEnglishWord.addTextChangedListener(textWatcher);
        editTextChineseMeaning.addTextChangedListener(textWatcher);
//        if (editTextEnglishWord.getText().toString().equals("") && editTextChineseMeaning.getText().toString().equals("")) {
//            button.setEnabled(true);
//        }

        button.setOnClickListener(v -> {
            wordViewModel.insertWords(new Word(editTextEnglishWord.getText().toString(), editTextChineseMeaning.getText().toString()));
            NavController controller = Navigation.findNavController(v);
            controller.navigateUp();
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });
    }
}