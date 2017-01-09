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
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
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
    private boolean firstTime;
    private GraphView graph;
    private View view;
    private GridLabelRenderer glr;

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
        view = inflater.inflate(R.layout.fragment_compare_graphs, container, false);

        final Spinner spin1 = (Spinner)view.findViewById(R.id.firstCompareSpinner);
        final Spinner spin2 = (Spinner) view.findViewById(R.id.secondCompareSpinner);

        final MainActivity mainActivity = (MainActivity)getActivity();

        ArrayList<Exercise> exercises = mainActivity.getUserData().getExercises();

        ArrayList<String> spinnerList = new ArrayList<>();

        spinnerList.add("Body Weight");

        for (int i = 0; i < exercises.size(); i++) {
            spinnerList.add(exercises.get(i).getExerciseName());
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner, spinnerList);
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spin2.setAdapter(dataAdapter);
        spin1.setAdapter(dataAdapter);

        graph = (GraphView) view.findViewById(R.id.graph);
        glr = graph.getGridLabelRenderer();
        glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        final ArrayList<Object[]>[] dataSets = new ArrayList[2];
        final String[] names = new String[2];

        final LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[]{});
        final LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{});


        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View views, int i, long l) {
                DataPoint[] datapoints;
                if (spin1.getSelectedItemPosition() == 0) {
                    ArrayList<Object[]> bodyWeight = mainActivity.getUserData().getBodyWeight();
                    dataSets[0] = bodyWeight;
                    datapoints = new DataPoint[bodyWeight.size()];
                    for (int j = 0; j < bodyWeight.size(); j++) {
                        datapoints[j] = new DataPoint((Date)(bodyWeight.get(j)[1]), (int)(bodyWeight.get(j)[0]));
                    }
                    if (bodyWeight.size() > 0) {
                        if (graph.getViewport().getMinX(true) > ((Date) (bodyWeight.get(0)[1])).getTime()) {
                            graph.getViewport().setMinX(((Date) (bodyWeight.get(0)[1])).getTime());
                        }
                        if (graph.getViewport().getMaxX(true) < ((Date) (bodyWeight.get(bodyWeight.size() - 1)[1])).getTime()) {
                            graph.getViewport().setMaxX(((Date) (bodyWeight.get(bodyWeight.size() - 1)[1])).getTime());
                        }
                    }
                    graph.getViewport().setXAxisBoundsManual(true);
                    names[0] = "Body Weight";
                } else {
                    Exercise secondDataSet = mainActivity.getUserData().getExercise(spin1.getSelectedItemPosition()-1);
                    datapoints = new DataPoint[secondDataSet.getCompletedWeights().size()];
                    dataSets[0] = secondDataSet.getCompletedWeights();
                    for (int j = 0; j < datapoints.length; j++) {
                        datapoints[j] = new DataPoint((Date)(secondDataSet.getCompletedWeights().get(j)[1]), (int)(secondDataSet.getCompletedWeights().get(j)[0]));
                    }
                    names[0] = secondDataSet.getExerciseName();
                }
                series1.resetData(datapoints);
                series1.setDrawDataPoints(true);
                series1.setTitle(names[0]);
                series1.setDataPointsRadius(20);
                series1.setThickness(15);
                series1.setColor(Color.RED); //TODO: Change to a more material color.
                graph.addSeries(series1);
                glr.setNumVerticalLabels(5);
                glr.setNumHorizontalLabels(6);
                if (dataSets[0] == null && dataSets[1] == null) {
                    graph.setVisibility(View.GONE);
                    TextView noData = (TextView)(view.findViewById(R.id.noDataText));
                    noData.setVisibility(View.VISIBLE);
                } else {
                    graph.setVisibility(View.VISIBLE);
                    TextView noData = (TextView)(view.findViewById(R.id.noDataText));
                    noData.setVisibility(View.INVISIBLE);
                }
//                if (dataSets[0] != null) {
//                    if (dataSets[1] != null) { //Only both datasets populated is populated
//                        if (dataSets[0].size() > 4 && dataSets[1].size() > 4) {
//                            glr.setNumHorizontalLabels(5);
//                        } else if (dataSets[0].size() > dataSets[1].size()){
//                            glr.setNumHorizontalLabels(dataSets[0].size());
//                        } else {
//                            glr.setNumHorizontalLabels(dataSets[1].size());
//                        }
//                    } else { //only dataset 0 populated
//                        if (dataSets[0].size() > 4) {
//                            glr.setNumHorizontalLabels(5);
//                        } else {
//                            glr.setNumHorizontalLabels(dataSets[0].size());
//                        }
//                    }
//                } else {
//                    if (dataSets[1] != null) { //only dataset 1 is populated
//                        if (dataSets[1].size() > 4) {
//                            glr.setNumHorizontalLabels(5);
//                        } else {
//                            glr.setNumHorizontalLabels(dataSets[1].size());
//                        }
//                    }
//                }

//                if (datapoints.length > 0) {
//                    if (graph.getViewport().getMinX(true) > datapoints[0].getX()) {
//                        graph.getViewport().setMinX(datapoints[0].getX());
//                    }
//                    if (graph.getViewport().getMaxX(true) < datapoints[datapoints.length-1].getX()) {
//                        graph.getViewport().setMaxX(datapoints[datapoints.length-1].getX());
//                    }
//                }


                if (dataSets[1] != null) {
                    resetGraphBounds(dataSets);
                    graph.getViewport().setXAxisBoundsManual(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View views, int i, long l) {
                DataPoint[] datapoints;
                if (spin2.getSelectedItemPosition() == 0) {
                    ArrayList<Object[]> bodyWeight = mainActivity.getUserData().getBodyWeight();
                    dataSets[1] = bodyWeight;
                    datapoints = new DataPoint[bodyWeight.size()];
                    for (int j = 0; j < bodyWeight.size(); j++) {
                        datapoints[j] = new DataPoint((Date)(bodyWeight.get(j)[1]), (int)(bodyWeight.get(j)[0]));
                    }
                    names[1] = "Body Weight";
                } else {
                    Exercise secondDataSet = mainActivity.getUserData().getExercise(spin2.getSelectedItemPosition()-1);
                    datapoints = new DataPoint[secondDataSet.getCompletedWeights().size()];
                    dataSets[1] = secondDataSet.getCompletedWeights();
                    for (int j = 0; j < datapoints.length; j++) {
                        datapoints[j] = new DataPoint((Date)(secondDataSet.getCompletedWeights().get(j)[1]), (int)(secondDataSet.getCompletedWeights().get(j)[0]));
                    }
                    names[1] = secondDataSet.getExerciseName();
                }
                series2.resetData(datapoints);
                series2.setDrawDataPoints(true);
                series2.setTitle(names[0]);
                series2.setDataPointsRadius(20);
                series2.setThickness(15);
                series2.setColor(Color.GREEN); //TODO: Change to a more material color.
                graph.addSeries(series2);

                glr.setNumHorizontalLabels(6);
                glr.setNumVerticalLabels(5);
                if (dataSets[0] == null && dataSets[1] == null) {
                    graph.setVisibility(View.GONE);
                    TextView noData = (TextView)(view.findViewById(R.id.noDataText));
                    noData.setVisibility(View.VISIBLE);
                } else {
                    graph.setVisibility(View.VISIBLE);
                    TextView noData = (TextView)(view.findViewById(R.id.noDataText));
                    noData.setVisibility(View.INVISIBLE);
                }
//                if (dataSets[0] != null) {
//                    if (dataSets[1] != null) { //Only dataset 0 is populated
//                        if (dataSets[0].size() > 4 && dataSets[1].size() > 4) {
//                            glr.setNumHorizontalLabels(5);
//                        } else if (dataSets[0].size() > dataSets[1].size()){
//                            glr.setNumHorizontalLabels(dataSets[0].size());
//                        } else {
//                            glr.setNumHorizontalLabels(dataSets[1].size());
//                        }
//                    } else { //Both datasets populated
//                        if (dataSets[0].size() > 4) {
//                            glr.setNumHorizontalLabels(5);
//                        } else {
//                            glr.setNumHorizontalLabels(dataSets[0].size());
//                        }
//                    }
//                } else {
//                    if (dataSets[1] != null) { //only dataset 1 is populated
//                        if (dataSets[1].size() > 4) {
//                            glr.setNumHorizontalLabels(5);
//                        } else {
//                            glr.setNumHorizontalLabels(dataSets[1].size());
//                        }
//                    }
//                }

                if (dataSets[0] != null) {
                    resetGraphBounds(dataSets);
                    graph.getViewport().setXAxisBoundsManual(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spin1.setSelection(0);
        spin1.performItemClick(spin1.getChildAt(0), 0, spin1.getAdapter().getItemId(0));

        spin2.setSelection(1);
        spin2.performItemClick(spin2.getChildAt(1), 1, spin2.getAdapter().getItemId(1));

        int whiteColor = ContextCompat.getColor(getActivity(), R.color.whiteText);

        glr.setPadding(100);
        glr.setHorizontalLabelsColor(whiteColor);
        glr.setVerticalLabelsColor(whiteColor);
        glr.setVerticalAxisTitle("Weight (lbs)");
        glr.setHorizontalAxisTitle("Date");
        glr.setVerticalLabelsColor(whiteColor);
        glr.setHorizontalAxisTitleColor(whiteColor);
        glr.setVerticalAxisTitleColor(whiteColor);
        glr.setHighlightZeroLines(true);
        glr.setNumVerticalLabels(5);
        glr.setNumHorizontalLabels(6);

        graph.getViewport().setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPopup));

        glr.setHighlightZeroLines(true);

        glr.setGridColor(whiteColor);
        glr.setHorizontalLabelsAngle(135);

        // Inflate the layout for this fragment
        return view;
    }

    private void resetGraphBounds(ArrayList<Object[]>[] dataSets){
        glr.setNumHorizontalLabels(6);
        if (((Date)(dataSets[0].get(0)[1])).getTime() < ((Date)(dataSets[1].get(0)[1])).getTime()) {
            graph.getViewport().setMinX(((Date)(dataSets[0].get(0)[1])).getTime());
        } else {
            graph.getViewport().setMinX(((Date)(dataSets[1].get(0)[1])).getTime());
        }
        if (((Date)(dataSets[0].get(dataSets[0].size()-1)[1])).getTime() > ((Date)(dataSets[1].get(dataSets[1].size()-1)[1])).getTime()) {
            graph.getViewport().setMaxX(((Date)(dataSets[0].get(dataSets[0].size()-1)[1])).getTime());
        } else {
            graph.getViewport().setMaxX(((Date)(dataSets[1].get(dataSets[1].size()-1)[1])).getTime());
        }
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
        getActivity().setTitle(R.string.compare_graphs_fragment_title);
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
