package com.ntsikerdanos.butterknifeexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity {
	private GridLayout grid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		grid = (GridLayout) findViewById(R.id.grid);

		int size = (int) Math.sqrt(grid.getChildCount());
		grid.setColumnCount(size);
		grid.setRowCount(size);

		resetStatus();

		for (int i = 0; i < grid.getChildCount(); i++) {
			grid.getChildAt(i).setOnClickListener(new CheckBoxClickListener());
		}

		findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < grid.getChildCount(); i++) {
					CheckBox checkBox = (CheckBox) grid.getChildAt(i);
					checkBox.setChecked(false);
					checkBox.setEnabled(true);

					resetStatus();
				}
			}
		});
	}

	private int getUncheckedCount() {
		int unchecked = 0;

		for (int i = 0; i < grid.getChildCount(); i++) {
			if (!((CheckBox) grid.getChildAt(i)).isChecked()) {
				++unchecked;
			}
		}

		return unchecked;
	}

	private void resetStatus() {
		updateStatus(grid.getChildCount());
	}

	private void updateStatus(int count) {
		TextView status = (TextView) findViewById(R.id.status);

		if (count > 0) {
			status.setText(getString(R.string.more_to_go_count, count));
		} else {
			status.setText(R.string.completed);
		}
	}

	private class CheckBoxClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			invertCheckBox(v.getNextFocusDownId());
			invertCheckBox(v.getNextFocusLeftId());
			invertCheckBox(v.getNextFocusRightId());
			invertCheckBox(v.getNextFocusUpId());

			int uncheckedCount = getUncheckedCount();

			updateStatus(uncheckedCount);

			if (uncheckedCount == 0) {
				for (int i = 0; i < grid.getChildCount(); i++) {
					grid.getChildAt(i).setEnabled(false);
				}
			}
		}

		private void invertCheckBox(int viewId) {
			if (viewId == View.NO_ID) {
				return;
			}

			CheckBox box = (CheckBox) findViewById(viewId);
			box.setChecked(!box.isChecked());
		}
	}
}
