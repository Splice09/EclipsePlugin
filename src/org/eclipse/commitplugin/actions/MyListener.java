package org.eclipse.commitplugin.actions;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.python.pydev.debug.pyunit.IPyUnitServer;
import org.python.pydev.debug.pyunit.IPyUnitServerListener;
import org.python.pydev.debug.ui.launching.PythonRunner;
import org.python.pydev.shared_core.callbacks.ICallbackListener;


public class MyListener {
	public MyListener(){
		PythonRunner.onPyUnitServerCreated
		.registerListener(new ICallbackListener<IPyUnitServer>() {
			
			@Override
			public Object call(IPyUnitServer obj) {
				System.out.println("Server has been created.");
				obj.registerOnNotifyTest(new IPyUnitServerListener() {
					
					@Override
					public void notifyTestsCollected(
							String totalTestsCount) {
						System.out.println("Tests are collected.");
					}

					@Override
					public void notifyTest(String status,
							String location, String test,
							String capturedOutput,
							String errorContents, String time) {
					}
			
					@Override
					public void notifyStartTest(String location, String test) {
						
						//testStarted = true;
						System.out.println("We have started a pyunit test!");
						
						long mainThreadId = Thread.currentThread().getId();
						System.out.println(mainThreadId);
						Display.getDefault().asyncExec(new Runnable() {
					        public void run() {
					        	long mainThreadId = Thread.currentThread().getId();
								System.out.println(mainThreadId);
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
