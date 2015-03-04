package com.ntsikerdanos.butterknifeexample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {
	@InjectView(R.id.grid)
	CheckBoxGridLayout grid;

	@InjectView(R.id.status)
	TextView status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		ButterKnife.inject(this);

		resetStatus();

		grid.setOnChangedListener(new CheckBoxGridLayout.OnChangedListener() {
			@Override
			public void onChanged(int uncheckedCount) {
				updateStatus(uncheckedCount);
			}
		});
	}

	@OnClick(R.id.reset)
	protected void reset() {
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
