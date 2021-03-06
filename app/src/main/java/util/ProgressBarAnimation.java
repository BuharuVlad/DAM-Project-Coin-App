package util;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.proiect_buharu_vlad_gr_1149_id_csie.Activities.HelloActivity;


public class ProgressBarAnimation extends Animation {

    private final Context context;
    private final ProgressBar progressBar;
    private final TextView textView;
    private final float from;
    private final float to;

    public ProgressBarAnimation (Context context, ProgressBar progressBar, TextView textView, float from, float to){
        this.context = context;
        this.progressBar =progressBar;
        this.textView = textView;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int)value);
        textView.setText((int)value + " %");
        if(value == to){
            context.startActivities(new Intent[]{new Intent(context, HelloActivity.class)});
        }
    }
}
