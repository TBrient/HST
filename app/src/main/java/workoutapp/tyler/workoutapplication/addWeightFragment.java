package workoutapp.tyler.workoutapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addWeightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addWeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addWeightFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public addWeightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addWeightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addWeightFragment newInstance(String param1, String param2) {
        addWeightFragment fragment = new addWeightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_weight, container, false);
        setupUI(view);
        TextView exerciseName = (TextView)view.findViewById(R.id.ExerciseName);
        final MainActivity activity = (MainActivity)getActivity();

        final Exercise exercise = activity.getUserData().getCardViewExercisePressed();
        exerciseName.setText(exercise.getExerciseName());

        EditText setsDefault = (EditText)view.findViewById(R.id.setsCheck);
        EditText repsDefault = (EditText)view.findViewById(R.id.repsCheck);

        setsDefault.setText(String.valueOf(exercise.getNumberOfSets()));
        int[] repRange = exercise.getRepRange();
        repsDefault.setText(String.valueOf(repRange[0]));

        final TextView dateText = (TextView)view.findViewById(R.id.dateTextView);

        final Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        dateText.setText(sdf.format(date));

        final DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(sdf.format(c.getTime()));
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity, dateClick, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Button saveButton = (Button)view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentView = view.getRootView();
                EditText setsDefault = (EditText)parentView.findViewById(R.id.setsCheck);
                EditText repsDefault = (EditText)parentView.findViewById(R.id.repsCheck);
                EditText weightInput = (EditText)parentView.findViewById(R.id.WeightInput);

                if (setsDefault.getText().toString().equals("") || repsDefault.getText().toString().equals("") || weightInput.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make((View)view.getParent(), "Please fill out all of the text fields", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPopup));
                    TextView textView = (TextView)snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                    snackbar.show();
                } else {
                    exercise.addSet(Integer.parseInt(weightInput.getText().toString()), Integer.parseInt(setsDefault.getText().toString()), Integer.parseInt(repsDefault.getText().toString()), c);

                    MainActivity mainActivity = (MainActivity)getActivity();

                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_bottom);
                    fragmentTransaction.replace(R.id.fragmentContainer, mainActivity.getExerciseCardsFragment(), "toCalc");
                    fragmentTransaction.addToBackStack("toCalc");
                    fragmentTransaction.commit();
                    InputMethodManager imm = (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                getActivity().setTitle("");
                getActivity().setTitle(R.string.main_exercise_cards_fragment_title);

            }
        });


        Button cancelButton = (Button)view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_bottom);
                fragmentTransaction.replace(R.id.fragmentContainer, mainActivity.getExerciseCardsFragment(), "toCalc");
                fragmentTransaction.addToBackStack("toCalc");
                fragmentTransaction.commit();
                mainActivity.setTitle("");
                mainActivity.setTitle(R.string.main_exercise_cards_fragment_title);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        getActivity().setTitle("");
        getActivity().setTitle(R.string.add_exercise_weight_fragment_title);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
