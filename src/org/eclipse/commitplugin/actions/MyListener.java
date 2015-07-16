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
	public MyListener(){
		//myList = new ArrayList<Integer>(7);
		//print();
	}
	
	public void print(){
		/*try {
			is = FileLocator.openStream(Activator.getDefault().getBundle(), new Path("arguments.txt"), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		re = new InputStreamReader(is);
		br = new BufferedReader(re);
		try {
			while(br.readLine() != null){
				argument = argument + br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String workingDir = System.getProperty("user.dir");
		JOptionPane.showMessageDialog(null, "The user dir is:" + argument);*/
	}

	@Override
	public void earlyStartup() {
		JOptionPane.showMessageDialog(null, "You are running commit plugin!");
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
