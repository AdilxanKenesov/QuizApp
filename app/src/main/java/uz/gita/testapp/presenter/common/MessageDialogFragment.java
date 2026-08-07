package uz.gita.testapp.presenter.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;

import uz.gita.testapp.R;

public class MessageDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "arg_message";
    private static final String TAG = "MessageDialogFragment";

    public static void display(FragmentManager fragmentManager, String message) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        fragment.show(fragmentManager, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String message = getArguments() != null ? getArguments().getString(ARG_MESSAGE) : "";

        TextView tvMessage = view.findViewById(R.id.tvDialogMessage);
        tvMessage.setText(message);

        MaterialButton btnOk = view.findViewById(R.id.btnDialogOk);
        btnOk.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        setCancelable(true);
    }
}