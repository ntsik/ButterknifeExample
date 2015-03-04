package com.ntsikerdanos.butterknifeexample;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

public class CheckBoxGridLayout extends GridLayout {
	// TODO: Make this configurable in XML and code
	private static final int WIDTH = 3;
	private static final int HEIGHT = 3;

	// TODO: This should be in a style
	private static final float CHECKBOX_SCALE = 1.5f;

	private List<CheckBox> checkBoxes = Collections.emptyList();
	private OnChangedListener listener;

	public CheckBoxGridLayout(Context context) {
		this(context, null);
	}

	public CheckBoxGridLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CheckBoxGridLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setOrientation(HORIZONTAL);

		fillGrid();

		setColumnCount(WIDTH);
		setRowCount(HEIGHT);
	}

	public int getSize() {
		return checkBoxes.size();
	}

	public int getUncheckedCount() {
		int unchecked = 0;

		for (CheckBox checkBox : checkBoxes) {
			if (!checkBox.isChecked()) {
				++unchecked;
			}
		}

		return unchecked;
	}

	public void reset() {
		ButterKnife.apply(checkBoxes, RESET);
	}

	public void setOnChangedListener(OnChangedListener listener) {
		this.listener = listener;
	}

	private void fillGrid() {
		for (CheckBox checkBox : checkBoxes) {
			removeView(checkBox);
		}

		checkBoxes = generateCheckBoxes();

		for (int i = 0; i < checkBoxes.size(); i++) {
			CheckBox checkBox = checkBoxes.get(i);

			if (i + WIDTH < checkBoxes.size()) {
				checkBox.setNextFocusDownId(checkBoxes.get(i + WIDTH).getId());
			}

			if (i % WIDTH != 0 && i - 1 >= 0) {
				checkBox.setNextFocusLeftId(checkBoxes.get(i - 1).getId());
			}

			if (i % WIDTH != WIDTH - 1 && i + 1 < checkBoxes.size()) {
				checkBox.setNextFocusRightId(checkBoxes.get(i + 1).getId());
			}

			if (i - WIDTH >= 0) {
				checkBox.setNextFocusUpId(checkBoxes.get(i - WIDTH).getId());
			}

			checkBox.setOnClickListener(checkBoxClickListener);

			addView(checkBox);
		}
	}

	private List<CheckBox> generateCheckBoxes() {
		int size = WIDTH * HEIGHT;

		List<CheckBox> checkBoxes = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			CheckBox checkBox = new CheckBox(getContext());
			checkBox.setId(View.generateViewId());
			checkBox.setScaleX(CHECKBOX_SCALE);
			checkBox.setScaleY(CHECKBOX_SCALE);

			checkBoxes.add(checkBox);
		}

		return checkBoxes;
	}

	private View.OnClickListener checkBoxClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			invertCheckBox(v.getNextFocusDownId());
			invertCheckBox(v.getNextFocusLeftId());
			invertCheckBox(v.getNextFocusRightId());
			invertCheckBox(v.getNextFocusUpId());

			int uncheckedCount = getUncheckedCount();

			if (listener != null) {
				listener.onChanged(uncheckedCount);
			}

			if (uncheckedCount == 0) {
				ButterKnife.apply(checkBoxes, DISABLED);
			}
		}

		private void invertCheckBox(int viewId) {
			if (viewId == View.NO_ID) {
				return;
			}

			CheckBox box = ButterKnife.findById(CheckBoxGridLayout.this, viewId);
			box.setChecked(!box.isChecked());
		}
	};

	public interface OnChangedListener {
		void onChanged(int uncheckedCount);
	}

	private static final ButterKnife.Action<View> DISABLED = new ButterKnife.Action<View>() {
		@Override
		public void apply(View view, int index) {
			view.setEnabled(false);
		}
	};

	private static final ButterKnife.Action<CheckBox> RESET = new ButterKnife.Action<CheckBox>() {
		@Override
		public void apply(CheckBox view, int index) {
			view.setChecked(false);
			view.setEnabled(true);
		}
	};
}
