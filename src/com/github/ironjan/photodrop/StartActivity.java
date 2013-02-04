package com.github.ironjan.photodrop;

import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.github.ironjan.photodrop.crouton.CroutonW;
import com.github.ironjan.photodrop.dbwrap.SessionKeeper;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

import de.keyboardsurfer.android.widget.crouton.Crouton;

@EActivity(R.layout.act_authentification)
public class StartActivity extends SherlockActivity {

	private static final String TAG = StartActivity.class.getSimpleName();
	
	@Bean
	SessionKeeper sessionKeeper;
	
	@Click(R.id.btnLink)
	void startAuthentication() {
		try {
			sessionKeeper.startAuthentication(StartActivity.this);
		} catch (IllegalStateException e) {
			CroutonW.showAlert(this, e);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		try {
			sessionKeeper.finishAuthentication();
		} catch (IllegalStateException e) {
			Log.e(TAG,
					"The user is not authentificated. This exception can normally be ignored.", //$NON-NLS-1$
					e);
		}

		if (sessionKeeper.isLinked()) {
			StreamActivity_.intent(this).start();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Crouton.cancelAllCroutons();
	}

}
