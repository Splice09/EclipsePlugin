package org.eclipse.commitplugin.actions;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.commitplugin.Activator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.python.pydev.debug.pyunit.IPyUnitServer;
import org.python.pydev.debug.pyunit.IPyUnitServerListener;
import org.python.pydev.debug.ui.launching.PythonRunner;
import org.python.pydev.shared_core.callbacks.ICallbackListener;
import org.eclipse.ui.IStartup;

import javax.swing.JOptionPane;


public class MyListener implements IStartup {
	private InputStream is;
	private InputStreamReader re;
	private BufferedReader br;
	private BufferedInputStream bis;
	private DataInputStream dis;
	private String argument;
	private ArrayList<Integer> myList;
	
	/* Constructor */
	public MyListener(){
		
	}
	
	/* This method sets up a listener that runs in the background of eclipse for PyUnit */
	@Override
	public void earlyStartup() {
		JOptionPane.showMessageDialog(null, "You are running commit plugin!");
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
