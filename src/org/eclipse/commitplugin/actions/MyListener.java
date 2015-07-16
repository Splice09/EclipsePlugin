package org.eclipse.commitplugin.actions;

import org.eclipse.swt.widgets.Display;
import org.python.pydev.debug.pyunit.IPyUnitServer;
import org.python.pydev.debug.pyunit.IPyUnitServerListener;
import org.python.pydev.debug.ui.launching.PythonRunner;
import org.python.pydev.shared_core.callbacks.ICallbackListener;
import org.eclipse.ui.IStartup;

import javax.swing.JOptionPane;

/**
 * Author: Patrick Fleming
 * Date:   7/15/2015
 * Email:  jpf0005@auburn.edu
 */

public class MyListener implements IStartup {

	/* Constructor */
	public MyListener(){
		
	}
	
	/* This method sets up a listener that runs in the background of eclipse for PyUnit */
	@Override
	public void earlyStartup() {
		JOptionPane.showMessageDialog(null, "You are running the Software Process Git Commit Plugin!");
		PythonRunner.onPyUnitServerCreated
		.registerListener(new ICallbackListener<IPyUnitServer>() {
			
			@Override
			public Object call(IPyUnitServer obj) {
				obj.registerOnNotifyTest(new IPyUnitServerListener() {
					
					@Override
					public void notifyTestsCollected(String totalTestsCount) {
						Display.getDefault().asyncExec(new Runnable() {
					        public void run() {
					        	SampleAction action = new SampleAction();
								action.initialize();
					        }
					    });
					}

					@Override
					public void notifyTest(String status,
							String location, String test,
							String capturedOutput,
							String errorContents, String time) {
					}
			
					@Override
					public void notifyStartTest(String location, String test) {
						
						
					}
	
					@Override
					public void notifyFinished(
							String totalTimeInSecs) {
					}

					@Override
					public void notifyDispose() {
					}
				});
				return null;
			}
		});
		
	}
}
