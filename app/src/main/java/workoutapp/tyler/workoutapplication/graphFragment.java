package workoutapp.tyler.workoutapplication;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import java.text.SimpleDateFormat;
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
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        GraphView graph = (GraphView)view.findViewById(R.id.graph);
        MainActivity activity = (MainActivity)getActivity();
        final Exercise exercisePressed = activity.getUserData().getCardViewExercisePressed();

        final DataPoint[] completePoints = new DataPoint[exercisePressed.getCompletedWeights().size()];
        for (int i = 0; i < completePoints.length; i++) {
            completePoints[i] = new DataPoint((Date)(exercisePressed.getCompletedWeights().get(i)[2]), (int)(exercisePressed.getCompletedWeights().get(i)[0]));
        }
        int whiteColor = ContextCompat.getColor(activity, R.color.whiteText);
        int accentColor = ContextCompat.getColor(activity, R.color.colorAccent);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(completePoints);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20);
        series.setThickness(15);
        series.setColor(accentColor);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPointInterface) {
                int index = findIndex(completePoints, dataPointInterface);

                Object[] info = exercisePressed.getCompletedWeights().get(index);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Point Info");
                builder.setMessage("Message");
                AlertDialog alertDialog = builder.show();
                String line1 = "Weight: " + info[0];
                String line2;
                if ((int)info[1] > 1) {
                    line2 = info[1] + " Sets";
                } else {
                    line2 = info[1] + " Set";
                }
                String line3;
                if (exercisePressed.getNumberOfSets() > 1) {
                    line3 = exercisePressed.getNumberOfSets() + " Sets";
                } else {
                    line3 = exercisePressed.getNumberOfSets() + " Set";
                }
                TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
                String line4 = new SimpleDateFormat("MM/dd/yyyy").format((Date)info[2]);
                messageView.setText(line1 + "\n" +
                        line2 + "\n" +
                        line3 + "\n" +
                        line4 + "\n");
                alertDialog.show();
                alertDialog.getWindow().setLayout(800, 700);

                //TODO: Create a custom popup so that it changes to the right color/doesn't look so bad

            }
        });

        graph.addSeries(series);

        final DataPoint[] incompletePoints = new DataPoint[exercisePressed.getIncompleteWeights().size()];
        for (int i = 0; i < incompletePoints.length; i++) {
            incompletePoints[i] = new DataPoint((Date)(exercisePressed.getIncompleteWeights().get(i)[3]), (int)(exercisePressed.getIncompleteWeights().get(i)[0]));
        }
        final int secondarySeriesColor = ContextCompat.getColor(activity, R.color.extraGraphRed);

        PointsGraphSeries<DataPoint> incompleteSeries = new PointsGraphSeries<>(incompletePoints);
        incompleteSeries.setShape(PointsGraphSeries.Shape.POINT);
        incompleteSeries.setSize(20);
        incompleteSeries.setColor(secondarySeriesColor);

//        final ToolTipRelativeLayout toolTipRelativeLayout = (ToolTipRelativeLayout)view.findViewById(R.id.toolTipLayout);
//        final RelativeLayout backgroundLayout = (RelativeLayout)view.findViewById(R.id.graphRelativeLayout);

        incompleteSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPointInterface) {
                int index = findIndex(incompletePoints, dataPointInterface);

                Object[] info = exercisePressed.getIncompleteWeights().get(index);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Point Info");
                builder.setMessage("Message");
                AlertDialog alertDialog = builder.show();
                String line1 = "Weight: " + info[0];
                String line2;
                if ((int)info[1] > 1) {
                    line2 = info[1] + " Sets";
                } else {
                    line2 = info[1] + " Set";
                }
                String line3;
                if ((int)info[2] > 1) {
                    line3 = info[2] + " Reps";
                } else {
                    line3 = info[2] + " Rep";
                }
                TextView messageView = (TextView) alertDialog.findViewById(android.R.id.message);
                String line4 = new SimpleDateFormat("MM/dd/yyyy").format((Date)info[3]);
                messageView.setText(line1 + "\n" +
                        line2 + "\n" +
                        line3 + "\n" +
                        line4 + "\n");
                alertDialog.show();
                alertDialog.getWindow().setLayout(800, 700);

                //TODO: Create a custom popup so that it changes to the right color/doesn't look so bad

            }
        });

        graph.addSeries(incompleteSeries);

        GridLabelRenderer glr = graph.getGridLabelRenderer();

        glr.setPadding(100);

        glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        glr.setHorizontalLabelsColor(whiteColor);
        glr.setVerticalLabelsColor(whiteColor);
        glr.setVerticalAxisTitle("Weight (lbs)");
        glr.setVerticalLabelsColor(whiteColor);
        glr.setHorizontalAxisTitleColor(whiteColor);
        glr.setVerticalAxisTitleColor(whiteColor);

        if (completePoints.length > 4 || incompletePoints.length > 4) {
            glr.setNumHorizontalLabels(5);
        } else {
            if (completePoints.length > incompletePoints.length) {
                glr.setNumHorizontalLabels(completePoints.length);
            } else {
                glr.setNumHorizontalLabels(incompletePoints.length);
            }
        }
        graph.getViewport().setBorderColor(ContextCompat.getColor(activity, R.color.colorPopup));

        if (exercisePressed.getIncompleteWeights().size() > 0) {
            if ((int) exercisePressed.getIncompleteWeights().get(exercisePressed.getIncompleteWeights().size() - 1)[0] > graph.getViewport().getMaxY(true)) {
                graph.getViewport().setMaxY((int) exercisePressed.getIncompleteWeights().get(exercisePressed.getIncompleteWeights().size() - 1)[0]);
                graph.getViewport().setYAxisBoundsManual(true);
            }

            if ((int) exercisePressed.getIncompleteWeights().get(0)[0] < graph.getViewport().getMinY(true)) {
                graph.getViewport().setMinY((int) exercisePressed.getIncompleteWeights().get(0)[0]);
                graph.getViewport().setYAxisBoundsManual(true);
            }
        }

        graph.getViewport().setMinX(((Date)(exercisePressed.getCompletedWeights().get(0)[2])).getTime());
        graph.getViewport().setMaxX(((Date)(exercisePressed.getCompletedWeights().get(exercisePressed.getCompletedWeights().size()-1)[2])).getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        glr.setHumanRounding(false);
        glr.setGridColor(whiteColor);
        graph.getViewport().setMaxXAxisSize(300);

        return view;
    }

    private int findIndex(DataPoint[] datapoints, DataPointInterface dataPointInterface) {
        for (int i = 0; i < datapoints.length; i++) {
            if (datapoints[i] == dataPointInterface) {
                return i;
            }
        }
        return -1;
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
