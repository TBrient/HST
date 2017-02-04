package workoutapp.tyler.workoutapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link shareFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link shareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class shareFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ShareRVAdapter adapter;

    private ArrayList<Exercise> selectedExercises = new ArrayList<>();


    public shareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment shareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static shareFragment newInstance(String param1, String param2) {
        shareFragment fragment = new shareFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity)getActivity();
        ArrayList<Exercise> exercises = new ArrayList<>(activity.getUserData().getExercises());
        ArrayList<Object[]> bodyWeight = activity.getUserData().getBodyWeight();
        Exercise BWExercise = new Exercise("Body Weight", -1, "0");
        for (int i = 0; i < bodyWeight.size(); i++) {
            BWExercise.addSet((int)(bodyWeight.get(i)[0]), -1, 0, (Date)(bodyWeight.get(i)[1]));
        }
        exercises.add(BWExercise);

        adapter = new ShareRVAdapter(exercises, new ShareRVAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Exercise item) {
                selectedExercises.add(item);
            }

            @Override
            public void onItemUncheck(Exercise item) {
                selectedExercises.remove(item);
            }
        });

        //Creates a view
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<ArrayList<String>> ExerciseText = new ArrayList<ArrayList<String>>();

                ArrayList<Date> dates = new ArrayList<Date>();
                for (int i = 0; i < selectedExercises.size(); i++) {
                    ArrayList<Object[]> currentExercise1 = selectedExercises.get(i).getCompletedWeights();
                    for (int j = 0; j < currentExercise1.size(); j++) {
                        Date tempDate = (Date)currentExercise1.get(j)[2];
                        if (!dates.contains(tempDate)) {
                            dates.add(getIndexOfDate(dates, tempDate),tempDate);
                        }
                    }
                    ArrayList<Object[]> currentExercise2 = selectedExercises.get(i).getIncompleteWeights();
                    for (int j = 0; j < currentExercise2.size(); j++) {
                        Date tempDate = (Date)currentExercise2.get(j)[3];
                        if (!dates.contains(tempDate)) {
                            dates.add(getIndexOfDate(dates, tempDate),tempDate);
                        }
                    }
                }
                for (int i = 0; i < selectedExercises.size(); i++) {
                    ArrayList<Object[]> currentExercise = selectedExercises.get(i).getIncompleteWeights();
                    for (int j = 0; j < currentExercise.size(); j++) {
                        Date tempDate = (Date)currentExercise.get(j)[2];
                        if (!dates.contains(tempDate)) {
                            dates.add(getIndexOfDate(dates, tempDate),tempDate);
                        }
                    }
                }
                for (int i = 0; i < selectedExercises.size(); i++) {
                    ArrayList<Object[]> currentExercise1 = selectedExercises.get(i).getCompletedWeights();
                    ArrayList<String> tempArrayList = new ArrayList<String>();
                    tempArrayList.add(selectedExercises.get(i).getExerciseName());
                    ExerciseText.add(i, tempArrayList);
                    for (int j = 0; j < currentExercise1.size(); j++) {
                        Date tempDate = (Date)currentExercise1.get(j)[2];
                        String setsString = String.valueOf(selectedExercises.get(i).getNumberOfSets()) + " Sets, ";
                        String repsString = String.valueOf((int)(currentExercise1.get(j)[1])) + " Reps";
                        String weightString = String.valueOf((int)(currentExercise1.get(j)[0])) + " lbs, ";
                        ExerciseText.get(i).add(dates.indexOf(tempDate), weightString + repsString + setsString); //TODO: java.lang.IndexOutOfBoundsException: Index: 2, Size: 1
                    }
                    ArrayList<Object[]> currentExercise2 = selectedExercises.get(i).getIncompleteWeights();
                    for (int j = 0; j < currentExercise2.size(); j++) {
                        Date tempDate = (Date)currentExercise2.get(j)[3];
                        if (!dates.contains(tempDate)) {
                            dates.add(getIndexOfDate(dates, tempDate),tempDate);
                        }
                        String setsString = String.valueOf((int)(currentExercise2.get(j)[1])) + " Sets, ";
                        String repsString = String.valueOf((int)(currentExercise2.get(j)[2])) + " Reps";
                        String weightString = String.valueOf((int)(currentExercise2.get(j)[0])) + " lbs, ";
                        ExerciseText.get(i).add(dates.indexOf(tempDate), weightString + repsString + setsString);
                    }
                }
                String dateString = createCSSStringFromDateArrayList(dates);
                String combinedString = dateString;
                for (int i = 0; i < ExerciseText.size(); i++) {
                    combinedString = combinedString + "\n" + createCSSStringFromStringArrayList(ExerciseText.get(i));
                }

                //TODO: Finish creating column and data strings

                File file = null;
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    File dir = new File(root.getAbsolutePath() + "/PersonData");
                    dir.mkdirs();
                    file = new File(dir, "Workout.csv");
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Uri u1 = Uri.fromFile(file);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Workout Details");
                sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                sendIntent.setType("text/html");
                startActivity(sendIntent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private String createCSSStringFromStringArrayList(ArrayList<String> array){
        String finalString = "\"";
        for (int i = 0; i < array.size(); i++) {
            if (i != 0 && i != array.size()-1) {
                finalString = finalString + "\",\"" + array.get(i);
            } else {
                finalString = finalString + array.get(i);
            }
        }
        finalString = finalString + "\"";
        return finalString;
    }

    private String createCSSStringFromDateArrayList(ArrayList<Date> array) {
        String finalString = "\"Exercise Name";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (int i = 0; i < array.size(); i++) {
            if (i != array.size()-1) {
                finalString = finalString + "\",\"" + sdf.format(array.get(i));
            } else {
                finalString = finalString + sdf.format(array.get(i));
            }
        }
        finalString = finalString + "\"";
        return finalString;
    }

    private int getIndexOfDate(ArrayList<Date> dates, Date date){
        for (int i = 0; i < dates.size(); i++) {
            if (date.getTime() > dates.get(i).getTime()) {
                return i;
            }
        }
        return dates.size();
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
