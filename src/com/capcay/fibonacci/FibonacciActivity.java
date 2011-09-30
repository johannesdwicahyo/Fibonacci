package com.capcay.fibonacci;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer.OnChronometerTickListener;

public class FibonacciActivity extends Activity {
    Chronometer chrono;
    Button startBtn;
    Button stopBtn;
    String currentTime;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(startFibo);
        chrono = (Chronometer) findViewById(R.id.chronometer1);
    }
    
    View.OnClickListener startFibo = new OnClickListener() {
    	public void onClick(View v) {
    		chrono.setBase(SystemClock.elapsedRealtime());
    		chrono.start();
    		chrono.setOnChronometerTickListener(new OnChronometerTickListener() {

				@Override
				public void onChronometerTick(Chronometer arg0) {
					long minutes = ((SystemClock.elapsedRealtime()-chrono.getBase())/1000)/60;
					long seconds = ((SystemClock.elapsedRealtime()-chrono.getBase())/1000)%60;
					currentTime = minutes+":"+seconds;
					arg0.setText(currentTime);
				}
    
    		});
    		FiboAsync task = new FiboAsync();
    		task.execute();
    		startBtn.setClickable(false);
    	}
    };
    
    private class FiboAsync extends AsyncTask<Void, String, String> {
    	TextView fiboNumber = (TextView) findViewById(R.id.fiboTxt);
    	
    	@Override
    	protected void onPreExecute() {
    		Toast.makeText(getBaseContext(), "Start counting Fibonacci number for 60 second", Toast.LENGTH_LONG).show();
    	}
    	
    	@Override
    	protected void onPostExecute(String result) {
    		Toast.makeText(getBaseContext(), "Finished counting Fibonacci number\n"+result, Toast.LENGTH_LONG).show();
    	}
    	
		@Override
		protected String doInBackground(Void... params) {
			double i = 0;
	    	double j = 1;
	    	String result = "";
	    	long coba = (((SystemClock.elapsedRealtime()-chrono.getBase())/1000)/60);
	    	Log.i("fibo2", "chronoNumber:"+coba);
	    	while ((SystemClock.elapsedRealtime()-chrono.getBase()) < 5001) {
	    		j = i+j;
	    		i = j-i;
	    		result = "Fibo Number:\n"+j;
	    		publishProgress(result);
	    		Log.i("fibo2", "i:"+i+" j:"+j);
	    	}
	    	chrono.stop();
			return result;
		}
		
		@Override
		protected void onProgressUpdate(String... progress) {
			fiboNumber.setText(progress[0]);
		}
    }
}