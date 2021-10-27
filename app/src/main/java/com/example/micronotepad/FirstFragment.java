package com.example.micronotepad;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.micronotepad.databinding.FragmentFirstBinding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private ActivityResultLauncher<String[]> mGetContent;
    private Uri OpenFileUri;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // getActivity().getContentResolver()

        mGetContent = registerForActivityResult(new ActivityResultContracts.OpenDocument(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        try (InputStream in = getActivity().getContentResolver().openInputStream(uri))
                        {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            String text = reader.lines().collect(Collectors.joining("\n"));
                            binding.editBox.setText(text);

                            OpenFileUri = uri;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        binding.buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch(new String[] {"text/*"});
            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (OpenFileUri == null)
                    return;

                try (OutputStream mOutputStream = getActivity().getContentResolver().openOutputStream(OpenFileUri)) {
                    try (PrintWriter p = new PrintWriter(mOutputStream)) {
                        String t = binding.editBox.getText().toString();
                        p.println(t);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}