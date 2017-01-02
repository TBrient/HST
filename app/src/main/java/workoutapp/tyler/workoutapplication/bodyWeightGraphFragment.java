package workoutapp.tyler.workoutapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * {@link BodyWeightGraphFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BodyWeightGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BodyWeightGraphFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BodyWeightGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BodyWeightGraphFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BodyWeightGraphFragment newInstance(String param1, String param2) {
        BodyWeightGraphFragment fragment = new BodyWeightGraphFragment();
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
        View view = inflater.inflate(R.layout.fragment_body_weight_graph, container, false);

        GraphView graph = (GraphView)view.findViewById(R.id.graph);
        MainActivity activity = (MainActivity)getActivity();
        ArrayList<Object[]> bodyWeight = activity.getUserData().getBodyWeight();
        DataPoint[] dataPoints = new DataPoint[bodyWeight.size()];
        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = new DataPoint((Date)(bodyWeight.get(i)[1]), (int)(bodyWeight.get(i)[0]));
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

        glr.setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        glr.setHorizontalLabelsColor(whiteColor);
        glr.setVerticalLabelsColor(whiteColor);
        glr.setVerticalAxisTitle("Body Weight (lbs)");
        glr.setHorizontalAxisTitle("Date");
        glr.setVerticalLabelsColor(whiteColor);
        glr.setHorizontalAxisTitleColor(whiteColor);
        glr.setVerticalAxisTitleColor(whiteColor);
        if (dataPoints.length > 4) {
            glr.setNumHorizontalLabels(5);
        } else {
            glr.setNumHorizontalLabels(dataPoints.length);
        }
        graph.getViewport().setBorderColor(ContextCompat.getColor(activity, R.color.colorPopup));

        if (bodyWeight.size()  > 0) {
            graph.getViewport().setMinX(((Date) (bodyWeight.get(0)[1])).getTime());
            graph.getViewport().setMaxX(((Date) (bodyWeight.get(bodyWeight.size() - 1)[1])).getTime());
            graph.getViewport().setXAxisBoundsManual(true);
        }

        glr.setHumanRounding(false);
        glr.setGridColor(whiteColor);

        TextView lastWeight = (TextView)view.findViewById(R.id.lastWeight);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);

        if (bodyWeight.size() > 0) {
            lastWeight.setText("Last Weight: " + Integer.toString((int)(bodyWeight.get(bodyWeight.size()-1)[0])));
        } else {
            lastWeight.setText("No data");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_top, R.anim.exit_to_bottom);
                fragmentTransaction.replace(R.id.fragmentContainer, new addBodyWeightFragment(), "toAddBW");
                fragmentTransaction.addToBackStack("toAddBW");
                fragmentTransaction.commit();
            }
        });

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
