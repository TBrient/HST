package workoutapp.tyler.workoutapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link graphFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link graphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class graphFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public graphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment graphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static graphFragment newInstance(String param1, String param2) {
        graphFragment fragment = new graphFragment();
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
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        GraphView graph = (GraphView)view.findViewById(R.id.graph);
        MainActivity activity = (MainActivity)getActivity();
        Exercise exercisePressed = activity.getUserData().getCardViewExercisePressed();
        DataPoint[] dataPoints = new DataPoint[exercisePressed.getCompletedWeights().size()];
        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = new DataPoint((Date)(exercisePressed.getCompletedWeights().get(i)[1]), (int)(exercisePressed.getCompletedWeights().get(i)[0]));
        }
        int whiteColor = ContextCompat.getColor(activity, R.color.whiteText);
        int accentColor = ContextCompat.getColor(activity, R.color.colorAccent);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20);
        series.setThickness(15);
        series.setColor(accentColor);

        graph.addSeries(series);

        GridLabelRenderer glr = graph.getGridLabelRenderer();

        glr.setPadding(100);

        glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        glr.setHorizontalLabelsColor(whiteColor);
        glr.setVerticalLabelsColor(whiteColor);
        glr.setVerticalAxisTitle("Weight (lbs)");
        glr.setVerticalLabelsColor(whiteColor);
        glr.setHorizontalAxisTitleColor(whiteColor);
        glr.setVerticalAxisTitleColor(whiteColor);
        if (dataPoints.length > 4) {
            glr.setNumHorizontalLabels(5);
        } else {
            glr.setNumHorizontalLabels(dataPoints.length);
        }
        graph.getViewport().setBorderColor(ContextCompat.getColor(activity, R.color.colorPopup));

        graph.getViewport().setMinX(((Date)(exercisePressed.getCompletedWeights().get(0)[1])).getTime());
        graph.getViewport().setMaxX(((Date)(exercisePressed.getCompletedWeights().get(exercisePressed.getCompletedWeights().size()-1)[1])).getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        glr.setHumanRounding(false);
        glr.setGridColor(whiteColor);
        graph.getViewport().setMaxXAxisSize(300);

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
        getActivity().setTitle(R.string.view_graph_exercise_fragment_title);
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
