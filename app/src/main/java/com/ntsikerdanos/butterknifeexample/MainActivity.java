package com.ntsikerdanos.butterknifeexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	CheckBoxGridLayout grid;
	TextView status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		grid = (CheckBoxGridLayout) findViewById(R.id.grid);
		status = (TextView) findViewById(R.id.status);

		resetStatus();

		findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				reset();
			}
		});

		grid.setOnChangedListener(new CheckBoxGridLayout.OnChangedListener() {
			@Override
			public void onChanged(int uncheckedCount) {
				updateStatus(uncheckedCount);
			}
		});
	}

	public void reset() {
		grid.reset();

		resetStatus();
	}

	private void resetStatus() {
		updateStatus(grid.getSize());
	}

	private void updateStatus(int count) {
		if (count > 0) {
			status.setText(getString(R.string.more_to_go_count, count));
		} else {
			status.setText(R.string.completed);
		}
	}
}
