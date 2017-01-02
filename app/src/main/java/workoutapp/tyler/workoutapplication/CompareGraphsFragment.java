package workoutapp.tyler.workoutapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompareGraphsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompareGraphsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompareGraphsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CompareGraphsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompareGraphsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompareGraphsFragment newInstance(String param1, String param2) {
        CompareGraphsFragment fragment = new CompareGraphsFragment();
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
        View view = inflater.inflate(R.layout.fragment_compare_graphs, container, false);

        final Spinner spin1 = (Spinner)view.findViewById(R.id.firstCompareSpinner);
        final Spinner spin2 = (Spinner) view.findViewById(R.id.secondCompareSpinner);

        final MainActivity mainActivity = (MainActivity)getActivity();

        ArrayList<Exercise> exercises = mainActivity.getUserData().getExercises();

        ArrayList<String> spinnerList = new ArrayList<>();

        spinnerList.add("Body Weight");

        for (int i = 0; i < exercises.size(); i++) {
            spinnerList.add(exercises.get(i).getExerciseName());
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(dataAdapter);
        spin1.setAdapter(dataAdapter);

        final GraphView graph = (GraphView) view.findViewById(R.id.graph);
        final GridLabelRenderer glr = graph.getGridLabelRenderer();
        final ArrayList<Object[]>[] dataSets = new ArrayList[2];
        final String[] names = new String[2];

        spin1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataPoint[] datapoints;
                if (spin1.getSelectedItemPosition() == 0) {
                    ArrayList<Object[]> bodyWeight = mainActivity.getUserData().getBodyWeight();
                    dataSets[0] = bodyWeight;
                    datapoints = new DataPoint[bodyWeight.size()];
                    for (int j = 0; j < bodyWeight.size(); j++) {
                        datapoints[j] = new DataPoint((Date)(bodyWeight.get(i)[1]), (int)(bodyWeight.get(i)[0]));
                    }
                    if (graph.getViewport().getMinX(true) > ((Date)(bodyWeight.get(0)[1])).getTime()) {
                        graph.getViewport().setMinX(((Date)(bodyWeight.get(0)[1])).getTime());
                    }
                    if (graph.getViewport().getMaxX(true) < ((Date)(bodyWeight.get(bodyWeight.size()-1)[1])).getTime()) {
                        graph.getViewport().setMaxX(((Date)(bodyWeight.get(bodyWeight.size()-1)[1])).getTime());
                    }
                    graph.getViewport().setXAxisBoundsManual(true);
                    names[0] = "Body Weight";
                } else {
                    Exercise secondDataSet = mainActivity.getUserData().getExercise(spin1.getSelectedItemPosition()-1);
                    datapoints = new DataPoint[secondDataSet.getCompletedWeights().size()];
                    dataSets[0] = secondDataSet.getCompletedWeights();
                    for (int j = 0; j < datapoints.length; j++) {
                        datapoints[j] = new DataPoint((Date)(secondDataSet.getCompletedWeights().get(i)[1]), (int)(secondDataSet.getCompletedWeights().get(i)[0]));
                    }
                    if (graph.getViewport().getMinX(true) > ((Date)(secondDataSet.getCompletedWeights().get(0)[1])).getTime()) {
                        graph.getViewport().setMinX(((Date)(secondDataSet.getCompletedWeights().get(0)[1])).getTime());
                    }
                    if (graph.getViewport().getMaxX(true) < ((Date)(secondDataSet.getCompletedWeights().get(secondDataSet.getCompletedWeights().size()-1)[1])).getTime()) {
                        graph.getViewport().setMaxX(((Date)(secondDataSet.getCompletedWeights().get(secondDataSet.getCompletedWeights().size()-1)[1])).getTime());
                    }
                    graph.getViewport().setXAxisBoundsManual(true);
                    names[0] = secondDataSet.getExerciseName();
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapoints);
                series.setDrawDataPoints(true);
                series.setTitle(names[0]);
                series.setDataPointsRadius(20);
                series.setThickness(15);
                series.setColor(Color.RED); //TODO: Change to a more material color.
                graph.addSeries(series);
            }
        });

        spin2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataPoint[] datapoints;
                if (spin2.getSelectedItemPosition() == 0) {
                    ArrayList<Object[]> bodyWeight = mainActivity.getUserData().getBodyWeight();
                    dataSets[1] = bodyWeight;
                    datapoints = new DataPoint[bodyWeight.size()];
                    for (int j = 0; j < bodyWeight.size(); j++) {
                        datapoints[j] = new DataPoint((Date)(bodyWeight.get(i)[1]), (int)(bodyWeight.get(i)[0]));
                    }
                    if (graph.getViewport().getMinX(true) > ((Date)(bodyWeight.get(0)[1])).getTime()) {
                        graph.getViewport().setMinX(((Date)(bodyWeight.get(0)[1])).getTime());
                    }
                    if (graph.getViewport().getMaxX(true) < ((Date)(bodyWeight.get(bodyWeight.size()-1)[1])).getTime()) {
                        graph.getViewport().setMaxX(((Date)(bodyWeight.get(bodyWeight.size()-1)[1])).getTime());
                    }
                    graph.getViewport().setXAxisBoundsManual(true);
                    names[1] = "Body Weight";
                } else {
                    Exercise secondDataSet = mainActivity.getUserData().getExercise(spin2.getSelectedItemPosition()-1);
                    datapoints = new DataPoint[secondDataSet.getCompletedWeights().size()];
                    dataSets[1] = secondDataSet.getCompletedWeights();
                    for (int j = 0; j < datapoints.length; j++) {
                        datapoints[j] = new DataPoint((Date)(secondDataSet.getCompletedWeights().get(i)[1]), (int)(secondDataSet.getCompletedWeights().get(i)[0]));
                    }
                    if (graph.getViewport().getMinX(true) > ((Date)(secondDataSet.getCompletedWeights().get(0)[1])).getTime()) {
                        graph.getViewport().setMinX(((Date)(secondDataSet.getCompletedWeights().get(0)[1])).getTime());
                    }
                    if (graph.getViewport().getMaxX(true) < ((Date)(secondDataSet.getCompletedWeights().get(secondDataSet.getCompletedWeights().size()-1)[1])).getTime()) {
                        graph.getViewport().setMaxX(((Date)(secondDataSet.getCompletedWeights().get(secondDataSet.getCompletedWeights().size()-1)[1])).getTime());
                    }
                    graph.getViewport().setXAxisBoundsManual(true);
                    names[1] = secondDataSet.getExerciseName();
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapoints);
                series.setDrawDataPoints(true);
                series.setTitle(names[1]);
                series.setDataPointsRadius(20);
                series.setThickness(15);
                series.setColor(Color.GREEN); //TODO: Change to a more material color.
                graph.addSeries(series);
            }
        });


        int whiteColor = ContextCompat.getColor(getActivity(), R.color.whiteText);


        glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        glr.setHorizontalLabelsColor(whiteColor);
        glr.setVerticalLabelsColor(whiteColor);
        glr.setVerticalAxisTitle("Weight (lbs)");
        glr.setHorizontalAxisTitle("Date");
        glr.setVerticalLabelsColor(whiteColor);
        glr.setHorizontalAxisTitleColor(whiteColor);
        glr.setVerticalAxisTitleColor(whiteColor);
        if (dataSets[0].size() > 4 && dataSets[1].size() > 4) {
            glr.setNumHorizontalLabels(5);
        } else if (dataSets[0].size() > dataSets[1].size()){
            glr.setNumHorizontalLabels(dataSets[0].size());
        } else {
            glr.setNumHorizontalLabels(dataSets[1].size());
        }
        graph.getViewport().setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPopup));

        glr.setHumanRounding(false);
        glr.setGridColor(whiteColor);

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
