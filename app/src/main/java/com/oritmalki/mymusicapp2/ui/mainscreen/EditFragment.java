package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.model.Measure;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {


//    @BindView(R.id.add_chord) Button addChordButt;

//    @BindView(R.id.c) RadioButton c_root;
//    @BindView(R.id.d) RadioButton d_root;
//    @BindView(R.id.e) RadioButton e_root;
//    @BindView(R.id.f) RadioButton f_root;
//    @BindView(R.id.g) RadioButton g_root;
//    @BindView(R.id.a) RadioButton a_root;
//    @BindView(R.id.b) RadioButton b_root;
//    @BindView(R.id.root_flat) RadioButton root_flat;
//    @BindView(R.id.root_sharp) RadioButton root_sharp;
//    @BindView(R.id.root_natural) RadioButton root_natural;

    @BindViews({R.id.minor, R.id.aug, R.id.dim, R.id.sus}) RadioButton[] triadTypeGroup;

    @BindViews({R.id.ten1_flat, R.id.ten1_sharp, R.id.ten1_natural}) RadioButton[] ten1AccGroup;

    @BindViews({R.id._2, R.id._3, R.id._4,R.id._5, R.id._6}) RadioButton[] chordPartsGroup;

    @BindViews({R.id._7, R.id.maj_7, R.id.diminished_7, R.id.half_diminished}) RadioButton[] squareTypeGroup;

    @BindViews({R.id.ten2_flat, R.id.ten2_sharp, R.id.ten2_natural}) RadioButton[] ten2AccGroup;

    @BindView(R.id._9) Button _9;
    @BindView(R.id._11) Button _11;
    @BindView(R.id._13) Button _13;


    @BindViews({R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.a, R.id.b}) RadioButton[] rootButtonsGroup;
    @BindViews({R.id.root_flat, R.id.root_sharp, R.id.root_natural}) RadioButton[] rootAccButtonsGroup;

    @BindView(R.id.next_beat_butt) Button nextBeatButt;
    @BindView(R.id.prev_beat_butt) Button prevBeatButt;
    @BindView(R.id.eraser_butt) Button eraserButt;
    @BindView(R.id.slash_butt) Button slashButt;


    private static final String ARG_MEASURE = "args_measure";
    public static String ARG_BEAT_POSITION = "args_beat_position";

    Measure measure;
    int beatPosition;


    private OnFragmentInteractionListener mListener;

    public EditFragment() {
        // Required empty public constructor
    }


    public static EditFragment newInstance(Measure measure, int beatPosition) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEASURE, measure);
        args.putInt(ARG_BEAT_POSITION, beatPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.measure = (Measure) getArguments().getSerializable(ARG_MEASURE);
            this.beatPosition = getArguments().getInt(ARG_BEAT_POSITION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
//        TextView measureNumEditorView = view.findViewById(R.id.measure_num_tv);
//        TextView beatNumEditorView = view.findViewById(R.id.beat_num);

        ButterKnife.bind(this, view);

        //set onclick listeners (TODO create a function for it)
        for (RadioButton rootButton : rootButtonsGroup) {

            rootButton.setOnClickListener(EditFragment.this::onButtonPressed);
        }
        for (RadioButton rootAccBtn : rootAccButtonsGroup) {
            rootAccBtn.setOnClickListener(EditFragment.this::onButtonPressed);
        }

        for (RadioButton triadTypBtn : triadTypeGroup) {
            triadTypBtn.setOnClickListener(EditFragment.this::onButtonPressed);
        }

        for (RadioButton squareTypeBtn : squareTypeGroup) {
            squareTypeBtn.setOnClickListener(EditFragment.this::onButtonPressed);
        }

        for (RadioButton ten1AccBtn : ten1AccGroup) {
            ten1AccBtn.setOnClickListener(EditFragment.this::onButtonPressed);
        }

        for (RadioButton ten2AccBtn : ten2AccGroup) {
            ten2AccBtn.setOnClickListener(EditFragment.this::onButtonPressed);
        }

        for (RadioButton chordPart : chordPartsGroup) {
            chordPart.setOnClickListener(EditFragment.this::onButtonPressed);
        }

        _9.setOnClickListener(EditFragment.this::onButtonPressed);
        _11.setOnClickListener(EditFragment.this::onButtonPressed);
        _13.setOnClickListener(EditFragment.this::onButtonPressed);

        nextBeatButt.setOnClickListener(EditFragment.this::onButtonPressed);
        prevBeatButt.setOnClickListener(EditFragment.this::onButtonPressed);
        eraserButt.setOnClickListener(EditFragment.this::onButtonPressed);
        slashButt.setOnClickListener(EditFragment.this::onButtonPressed);

//        measureNumEditorView.setText("M: " + String.valueOf(measure.getNumber()));
//        beatNumEditorView.setText("B: " + String.valueOf(beatPosition + 1));


        // Inflate the layout for this fragment
        return view;
    }

    // hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);

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

        void onFragmentInteraction(View view);

    }

}
