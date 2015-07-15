package org.eclipse.commitplugin.actions;


import org.eclipse.swt.widgets.Display;
import org.python.pydev.debug.pyunit.IPyUnitServer;
import org.python.pydev.debug.pyunit.IPyUnitServerListener;
import org.python.pydev.debug.ui.launching.PythonRunner;
import org.python.pydev.shared_core.callbacks.ICallbackListener;
import org.eclipse.ui.IStartup;

public class MyListener implements IStartup {
	
	public MyListener(){
		
	}

	@Override
	public void earlyStartup() {
		PythonRunner.onPyUnitServerCreated
		.registerListener(new ICallbackListener<IPyUnitServer>() {
			
			@Override
			public Object call(IPyUnitServer obj) {
				obj.registerOnNotifyTest(new IPyUnitServerListener() {
					
					@Override
					public void notifyTestsCollected(
							String totalTestsCount) {
					}

					@Override
					public void notifyTest(String status,
							String location, String test,
							String capturedOutput,
							String errorContents, String time) {
					}
			
					@Override
					public void notifyStartTest(String location, String test) {
						Display.getDefault().asyncExec(new Runnable() {
					        public void run() {
					        	SampleAction action = new SampleAction();
								action.initialize();
					        }
					    });
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
