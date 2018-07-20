package com.oritmalki.mymusicapp2.ui.mainscreen;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.Callback;
import android.support.v7.widget.RecyclerView;
import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.oritmalki.mymusicapp2.R;
import com.oritmalki.mymusicapp2.model.Beat;
import com.oritmalki.mymusicapp2.model.Measure;
import com.oritmalki.mymusicapp2.model.TimeSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Orit on 16.12.2017.
 */

public class MeasuresAdapter extends RecyclerView.Adapter<MeasuresAdapter.MeasureHolder> {

    private List<? extends Measure> measures = new ArrayList<>();
    private List<? extends Beat> beats;
    private TimeSignature timeSignature;
    private Context context;
    private RecyclerView mRecyclerView;
    private Measure currentMeasure;
    private boolean isBeatSelected;
    private View selectedBeatView;

    private boolean beatInit = false;
    private IAdapterPosition listener;
    private int aPosition;


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Nullable
    private final BeatClickCallback mBeatClickCallback;

//    @Nullable
//    private final MeasureClickCallback measureClickCallback;

    public MeasuresAdapter(Context context, @Nullable BeatClickCallback beatClickCallback) {
        this.context = context;
        this.mBeatClickCallback = beatClickCallback;
    }


//new Architecture components accomodation
    public void setMeasuresList(final List<? extends Measure> measuresList, Context context) {
       if (this.measures == null) {
           this.measures = measuresList;
           this.context = context;

           notifyItemRangeInserted(0, measuresList.size());
//           notifyItemRangeRemoved(0, measuresList.size());
       }
       else {
           DiffUtil.DiffResult result = DiffUtil.calculateDiff(new Callback() {
               @Override
               public int getOldListSize() {
                   return MeasuresAdapter.this.measures.size();
               }

               @Override
               public int getNewListSize() {
                   return measuresList.size();
               }

               @Override
               public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                   return MeasuresAdapter.this.measures.get(oldItemPosition).getNumber() == measuresList.get(newItemPosition).getNumber();
               }

               @Override
               public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                   Measure measure = measuresList.get(newItemPosition);
                   Measure old = MeasuresAdapter.this.measures.get(oldItemPosition);
                   return measure.getNumber() == old.getNumber() && Objects.equals(measure.getBeats(), old.getBeats());
               }
           });
           this.measures = measuresList;
           result.dispatchUpdatesTo(this);
       }
    }


    @Override
    public MeasureHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.measure_view, parent, false);
        MeasureHolder measureHolder = new MeasureHolder(viewGroup);
//        if (beatInit) {
//                    measureHolder.measure.removeAllViews();
//                    addAndBindBeatsAndTimeSig(measures, measureHolder, measureHolder.getAdapterPosition());
//                }

        return measureHolder;

    }

    @Override
    public void onBindViewHolder(MeasureHolder holder, int position) {


//            if (!beatInit) {
        //TODO make it only on initialization of adapter, debug and make required changes on calls.


                addAndBindBeatsAndTimeSig(measures, holder, position);
//                beatInit = true;
//           }


        measures.get(position);








    }


    @Override
    public int getItemCount() {
        return measures.size();
    }


    public void addAndBindBeatsAndTimeSig(List<? extends Measure> measures, MeasureHolder measureHolder, int position) {


        measureHolder.measure.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup timeSigView = (ViewGroup)layoutInflater.inflate(R.layout.time_signature_layout, measureHolder.measure, false);


            beats = new ArrayList<>(measures.get(position).getBeats());
            timeSignature = new TimeSignature(measures.get(position).getTimeSignature().getNumerator(), measures.get(position).getTimeSignature().getDenominator());

            TextView numerator = timeSigView.findViewById(R.id.numerator);
            numerator.setText(String.valueOf(measures.get(position).getTimeSignature().getNumerator()));
            TextView denomerator = timeSigView.findViewById(R.id.denominator);
            denomerator.setText(String.valueOf(measures.get(position).getTimeSignature().getDenominator()));


            for (int i = 1; i < measures.size(); i++) {


                measureHolder.measure.removeAllViews();
                measureHolder.measure.addView(timeSigView, 0);
                measureHolder.measure.setTag(String.valueOf(position));

                if (measures.get(i).getTimeSignature().compare(measures.get(i - 1).getTimeSignature())) {
                    measures.get(i).setShowTimeSig(false);
                }

                for (Beat beat : beats) {

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 20, 10, 20);
                    lp.setLayoutDirection(LayoutDirection.LTR);
                    lp.weight = 1.0f;
                    TextView newBeat = new TextView(context);
                    newBeat.setLayoutParams(lp);
                    newBeat.setSingleLine(true);
                    newBeat.setTextSize(20);
                    newBeat.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    //save beat position in measure into new created beat
                    newBeat.setTag(beats.indexOf(beat));


                    newBeat.setText(beat.getChordName());
                    newBeat.setOnClickListener(new OnClickListener() {

                        //on beat touched by user
                        @Override
                        public void onClick(View v) {
                            //retrieve measure and beat position
                            mBeatClickCallback.onBeatClicked(measures.get(position), v, (int) newBeat.getTag());
                            v.setSelected(true);
                            //initialize
                            if (selectedBeatView != null) {
                                selectedBeatView.setSelected(false);
                                selectedBeatView.setBackgroundResource(R.color.paper);
                            }
                            //set color for selected
                            selectedBeatView = v;
                            if (selectedBeatView.isSelected()) {
                                selectedBeatView.setBackgroundResource(R.color.preference_fallback_accent_color);
                            } else {
                                v.setBackgroundResource(R.color.paper);
                            }
                        }
                    });

                    measureHolder.measure.addView(newBeat);


                }


            }

        //hide timeSig
        hideTimeSig(position, measureHolder);


    }


    class MeasureHolder extends RecyclerView.ViewHolder {
        LinearLayout measure;
        TextView measureNumEditorView;


        public MeasureHolder(View itemView) {
            super(itemView);
            measure = itemView.findViewById(R.id.measure);
            measureNumEditorView = itemView.findViewById(R.id.measure_num_tv);


            ViewGroup.LayoutParams lp =
                    (ViewGroup.LayoutParams) measure.getLayoutParams();
            if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                FlexboxLayoutManager.LayoutParams flmp = (FlexboxLayoutManager.LayoutParams) lp;
                flmp.setFlexGrow(1.0f);
            }

        }

    }

    private void hideTimeSig(int position, MeasureHolder measureHolder) {
        //hide timeSig
        if (measures.get(position) != null) {
            if (measures.get(position).isShowTimeSig() == false) {
                measureHolder.measure.getChildAt(0).setVisibility(View.GONE);
            }
        }
    }

    private onRecyclerViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(Measure position);
    }


}
